import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileCleaner {
    private String _fileName;
    private List<String> _phrases;

    public FileCleaner(String fileName, List<String> phrases) {
        _fileName = fileName;
        _phrases = phrases;

        cleanFileName();
    }

    public String getCleanFileName() {
        return _fileName;
    }

    private void cleanFileName() {
        List<String> listFileName = getFileExtension();

        _fileName = listFileName.get(0);
        String ext = listFileName.get(1);

        if (ext.equals(".jar")) {
            _fileName += ext;
            return;
        }

        _fileName = removePhrases() + ext;
    }

    private String removePhrases() {
        List<String> listFileName = Arrays.asList(_fileName.split("[ .]"));
        List<String> fileNameCleaned = new ArrayList<>();

        for (String word : listFileName) {
            if (!_phrases.contains(word.toUpperCase())) {
                if (word.length() == 6) {
                    word = formatEpisodeString(word);
                }

                fileNameCleaned.add(capitalize(word));
            }
        }

        return String.join(" ", fileNameCleaned);
    }

    private boolean identifyEpisodeString(String word) {
        String wordUppercase = word.toUpperCase();
        if (wordUppercase.contains("S") && wordUppercase.contains("E")) {
            String season = wordUppercase.substring(0, 2);
            String episode = wordUppercase.substring(3);

            if (season.substring(1).matches(".*\\d+.*") && episode.substring(1).matches(".*\\d+.*")) {
                return true;
            }
        }
        return false;
    }

    private String formatEpisodeString(String word) {
        String wordUppercase = word.toUpperCase();
        if (wordUppercase.contains("S") && wordUppercase.contains("E")) {
            String season = wordUppercase.substring(0, 3);
            String episode = wordUppercase.substring(4);

            if (season.substring(1).matches(".*\\d+.*") && episode.substring(1).matches(".*\\d+.*")) {
                return season.toUpperCase() + episode.toUpperCase();
            }
        }

        return word;
    }

    private String capitalize(String word) {
        String firstLetter = word.substring(0, 1);
        String restOfWord = word.substring(1);

        return firstLetter.toUpperCase() + restOfWord.toLowerCase();
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
}
