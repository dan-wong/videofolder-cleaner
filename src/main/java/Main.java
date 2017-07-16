import java.io.*;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    private final static String NAME_OF_JAR = "videofoldercleaner-1.0-SNAPSHOT.jar";
    private static String NAME_OF_PHRASES_FILE;
    private static String _filePath;
    private static Input _input = new Input();
    private static List<String> _phrases = new ArrayList<>();

    public static void main(String args[]) throws IOException {
        _filePath = getCurrentLocation();

        try {
            NAME_OF_PHRASES_FILE = args[0];
            if (NAME_OF_PHRASES_FILE.length() > 0) {
                _phrases = getPhrases(args[0]);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
            return;
        }

        System.out.println(_filePath);
        taskCleanFileName();
    }

    private static List<String> getPhrases(String arg) throws IOException {
        FileReader fileReader = new FileReader(arg);
        BufferedReader reader = new BufferedReader(fileReader);

        List<String> phrases = new ArrayList<>();

        String line = reader.readLine();
        while (line != null) {
            phrases.addAll(Arrays.asList(line.toUpperCase().split(",")));
            line = reader.readLine();
        }

        return phrases;
    }

    private static void taskCleanFileName() throws IOException {
        System.out.printf("Task: Clean file name.\nAre you sure you want to continue? [Y/N] ");
        if (_input.getYesNo()) {
            Files.walk(Paths.get(_filePath))
                    .filter(Files::isRegularFile)
                    .filter(f -> !f.getFileName().toString().equals(NAME_OF_PHRASES_FILE) && !f.getFileName().toString().equals(NAME_OF_JAR))
                    .forEach(f -> {
                        String fileName = f.getFileName().toString();
                        FileCleaner fileCleaner = new FileCleaner(fileName, _phrases);
                        f.toFile().renameTo(new File(_filePath + "/" + fileCleaner.getCleanFileName()));
                    });
        } else {
            System.out.println("Task aborted.");
        }
    }

    private static void listFilesForFolder() throws IOException {
        Files.walk(Paths.get(_filePath))
                .filter(Files::isRegularFile)
                .forEach(System.out::println);
    }

    /**
     * Get the current directory without the jar appended to the end
     * Also removes the initial forward slash
     *
     * @return decodedPath
     */
    private static String getCurrentLocation() {
        String path = Main.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        String decodedPath = "";
        try {
            decodedPath = URLDecoder.decode(path, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        decodedPath = decodedPath.substring(1);
        int finalIndex = decodedPath.length() - NAME_OF_JAR.length();
        decodedPath = decodedPath.substring(0, finalIndex);

        return decodedPath;
    }
}
