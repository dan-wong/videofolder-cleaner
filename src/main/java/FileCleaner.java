import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileCleaner {
    private String _fileName;
    private List<String> _phrases;

    public FileCleaner(String fileName, List<String> phrases) {
        _fileName = fileName;
        _phrases = phrases;
    }

    public String getCleanFileName() {
        cleanFileName();
        return _fileName;
    }

    public String getSeriesName() {
        List<String> listFileName = getFileExtension();

        _fileName = listFileName.get(0);
        String ext = listFileName.get(1);

        if (invalidFileExtension(ext)) {
            return "";
        }

        listFileName = Arrays.asList(_fileName.split("[ .]"));
        List<String> fileNameCleaned = new ArrayList<>();

        for (String word : listFileName) {
            if (word.length() == 6) {
                if (identifyEpisodeString(word)) {
                    break;
                }
            }
            fileNameCleaned.add(capitalize(word));
        }

        return String.join(" ", fileNameCleaned);
    }

    private void cleanFileName() {
        List<String> listFileName = getFileExtension();

        _fileName = listFileName.get(0);
        String ext = listFileName.get(1);

        if (invalidFileExtension(ext)) {
            _fileName += ext;
            return;
        }

        _fileName = removePhrases() + ext;
    }

    private boolean invalidFileExtension(String ext) {
        return ext.equals(".jar") || ext.equals(".txt") || ext.equals(".nfo");
    }

    /**
     * Tidies up the file name using one of two methods.
     * If a phrase list has been supplied, remove phrases contained in the list
     * If not, remove everything after the episode string
     *
     * @return
     */
    private String removePhrases() {
        List<String> listFileName = Arrays.asList(_fileName.split("[ .]"));
        List<String> fileNameCleaned = new ArrayList<>();

        if (_phrases != null) {
            for (String word : listFileName) {
                if (!_phrases.contains(word.toUpperCase())) {
                    if (word.length() == 6) {
                        word = formatEpisodeString(word);
                    }
                    fileNameCleaned.add(capitalize(word));
                }
            }
        } else {
            for (String word : listFileName) {
                if (word.length() == 6) {
                    if (identifyEpisodeString(word)) {
                        fileNameCleaned.add(formatEpisodeString(word));
                        break;
                    }
                }
                fileNameCleaned.add(capitalize(word));
            }
        }

        return String.join(" ", fileNameCleaned);
    }

    /**
     * Verify a word is a episode string (S01E01)
     * @param word
     * @return
     */
    private boolean identifyEpisodeString(String word) {
        String wordUppercase = word.toUpperCase();
        if (wordUppercase.contains("S") && wordUppercase.contains("E")) {
            String season = wordUppercase.substring(0, 3);
            String episode = wordUppercase.substring(3);

            if (season.substring(1).matches(".*\\d+.*") && episode.substring(1).matches(".*\\d+.*")) {
                return true;
            }
        }
        return false;
    }

    /**
     * Format episode string to S01E01
     * @param word
     * @return
     */
    private String formatEpisodeString(String word) {
        if (identifyEpisodeString(word)) {
            String season = word.substring(0, 3);
            String episode = word.substring(3, 6);
            return "- " + season.toUpperCase() + episode.toUpperCase();
        }

        return word;
    }

    /**
     * Capitalize the first letter of the supplied word
     * @param word
     * @return capitalized word
     */
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
