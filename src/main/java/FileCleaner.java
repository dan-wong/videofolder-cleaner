import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileCleaner {
    private String _fileName;
    private Episode _episode;

    public FileCleaner(String fileName) {
        _fileName = fileName;

        generateEpisode();
    }

    public String getCleanFileName() {
        return _episode.getCleanFileName();
    }

    public String getSeriesName() {
        return _episode.getSeriesName();
    }

    private void generateEpisode() {
        List<String> listFileName = splitNameAndExtension();
        String ext = listFileName.get(1);

        if (invalidFileExtension(ext)) {
            _episode = new Episode(_fileName, "", "");
            return;
        }

        List<String> seriesName = new ArrayList<>();
        String episodeNumber = "";

        List<String> episodeNameList = Arrays.asList(listFileName.get(0).split("[ .]"));
        for (String word : episodeNameList) {
            if (word.length() == 6 && identifyEpisodeString(word)) {
                episodeNumber = formatEpisodeString(word);
                break;
            }
            seriesName.add(capitalize(word));
        }

        _episode = new Episode(String.join(" ", seriesName), episodeNumber, ext);
    }

    private boolean invalidFileExtension(String ext) {
        return ext.equals(".jar") || ext.equals(".txt") || ext.equals(".nfo");
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
            return season.toUpperCase() + episode.toUpperCase();
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
    private List<String> splitNameAndExtension() {
        List<String> listFileName = new ArrayList<>();
        listFileName.add(_fileName.substring(0, _fileName.length() - 4));
        listFileName.add(_fileName.substring(_fileName.length() - 4));

        return listFileName;
    }
}
