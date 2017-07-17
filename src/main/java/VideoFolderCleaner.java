import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class VideoFolderCleaner {
    private final static String NAME_OF_JAR = "videofoldercleaner-1.0-SNAPSHOT.jar";
    private static String NAME_OF_PHRASES_FILE;
    private static String _filePath;

    public static void main(String args[]) throws IOException {
        _filePath = getCurrentLocation();

        System.out.printf("Welcome to Video Folder Cleaner!\n\nPlease select a task below:\n" +
                "  [1] Clean File Names\n" +
                "  [2] Sort Files into Folders\n" +
                "  [3] List Files in Directory\n" +
                "Enter task number: ");

        Input input = new Input();
        String inputLine = input.getInput();

        Task task = null;
        switch (inputLine) {
            case "1":
                task = new TaskCleanFileName();
                break;
            case "2":
                task = new SortFilesIntoFolders();
                break;
            case "3":
                task = new ListFilesInDirectory();
                break;
            default:
                return;
        }
        task.execute();
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
}
