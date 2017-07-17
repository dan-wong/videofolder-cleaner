import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


public class TaskCleanFileName implements Task {
    private Input _input = new Input();

    @Override
    public void execute() throws IOException {
        System.out.printf("Task: Clean file name.\nAre you sure you want to continue? [Y/N] ");
        if (_input.getYesNo()) {
            Files.walk(Paths.get(VideoFolderCleaner.getFilePath()))
                    .filter(Files::isRegularFile)
                    .filter(f -> !f.getFileName().toString().equals(VideoFolderCleaner.getNameOfPhrasesFile()) && !f.getFileName().toString().equals(VideoFolderCleaner.getNameOfJar()))
                    .forEach(f -> {
                        String fileName = f.getFileName().toString();
                        FileCleaner fileCleaner = new FileCleaner(fileName);

                        String path = f.toAbsolutePath().toString();
                        path = path.substring(0, path.length() - (f.getFileName().toString().length()));
                        System.out.println(path);
                        f.toFile().renameTo(new File(path + fileCleaner.getCleanFileName()));
                    });

            System.out.println("Task Successfully Executed");
        } else {
            System.out.println("Task aborted.");
        }
    }
}
