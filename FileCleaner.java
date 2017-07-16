import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileCleaner {
    private String _fileName;

    public FileCleaner(String fileName) {
        _fileName = fileName;

        cleanFileName();
    }

    public String getCleanFileName() {
        return _fileName;
    }

    private void cleanFileName() {
        List<String> listFileName = getFileExtension();

        _fileName = listFileName.get(0);
        String ext = listFileName.get(1);

        _fileName = replaceDotsWithSpaces() + ext;
    }

    private String removePhrases() {
        List<String> listFileName = Arrays.asList(_fileName.split(" "));
        return "";
    }

    /**
     * Returns a list. The first element is the filename without the extension and the second is the extension.
     *
     * @return listFileName
     */
    private List<String> getFileExtension() {
        List<String> listFileName = new ArrayList<>();
        listFileName.add(_fileName.substring(0, _fileName.length() - 4));
        listFileName.add(_fileName.substring(_fileName.length() - 4));

        return listFileName;
    }

    private String replaceDotsWithSpaces() {
        return _fileName.replaceAll("\\.", " ");
    }
}
