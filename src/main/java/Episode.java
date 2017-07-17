import java.util.List;

public class Episode {
    private String _seriesName = "";
    private String _episodeNumber = "";
    private String _ext = "";

    public Episode(List<String> episodeName) {
        _seriesName = episodeName.get(0);
        _episodeNumber = episodeName.get(1);
        _ext = episodeName.get(2);
    }

    public Episode(String seriesName, String episodeNumber, String ext) {
        _seriesName = seriesName;
        _episodeNumber = episodeNumber;
        _ext = ext;
    }

    public String getCleanFileName() {
        return _seriesName + " " + _episodeNumber + _ext;
    }

    public String getSeriesName() {
        if (_episodeNumber.equals("") && _ext.equals("")) {
            return "";
        }
        return _seriesName;
    }

    public String getEpisodeNumber() {
        return _episodeNumber;
    }

    public String getExt() {
        return _ext;
    }
}
