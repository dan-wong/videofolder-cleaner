import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VideoFolderCleaner {
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
            System.out.println("Please enter a filename for phrases to omit. java -jar videofoldercleaner.jar phrases.txt");
            return;
        }

        System.out.printf("Welcome to Video Folder Cleaner!\n\nPlease select a task below:\n" +
                "  [1] Clean File Names\n\n" +
                "Enter task number: ");

        String inputLine = _input.getInput();
        while (!_input.validTaskInput(inputLine)) {
            System.out.println("Invalid task option. Please try again.");
            inputLine = _input.getInput();
        }

        Task task = null;
        switch (inputLine) {
            case "1":
                task = new TaskCleanFileName();
                break;
            default:
                return;
        }
        task.execute();
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
        String path = VideoFolderCleaner.class.getProtectionDomain().getCodeSource().getLocation().getPath();
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

    public static String getNameOfJar() {
        return NAME_OF_JAR;
    }

    public static String getNameOfPhrasesFile() {
        return NAME_OF_PHRASES_FILE;
    }

    public static String getFilePath() {
        return _filePath;
    }

    public static List<String> getPhrases() {
        return _phrases;
    }
}
