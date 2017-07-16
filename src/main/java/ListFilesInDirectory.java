import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ListFilesInDirectory implements Task {
    @Override
    public void execute() throws IOException {
        Files.walk(Paths.get(VideoFolderCleaner.getFilePath()))
                .filter(Files::isRegularFile)
                .forEach(System.out::println);
    }
}
