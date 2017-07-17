import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SortFilesIntoFolders implements Task {
    @Override
    public void execute() throws IOException {
        List<File> files = Arrays.asList(new File(VideoFolderCleaner.getFilePath()).listFiles());
        List<File> folders = generateListOfFolders(files);

        files.stream()
                .filter(f -> !f.isDirectory())
                .forEach(f -> {
                    FileCleaner fc = new FileCleaner(f.getName());
                    String seriesName = fc.getSeriesName();
                    if (seriesName != "") {
                        if (!folders.contains(seriesName)) {
                            new File(VideoFolderCleaner.getFilePath() + "/" + seriesName).mkdir();
                        }

                        Path source = Paths.get(f.getAbsolutePath());
                        Path newdir = Paths.get(VideoFolderCleaner.getFilePath() + "/" + seriesName);
                        try {
                            Files.move(source, newdir.resolve(source.getFileName()));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private List<File> generateListOfFolders(List<File> files) {
        List<File> folders = files.stream()
                .filter(File::isDirectory)
                .collect(Collectors.toList());

        return folders.stream()
                .filter(File::isDirectory)
                .sorted(Comparator.comparing(File::getName))
                .collect(Collectors.toList());
    }
}
