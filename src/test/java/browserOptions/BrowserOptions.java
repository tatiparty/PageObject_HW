package browserOptions;

public enum BrowserOptions {
    CHROME("CHROME"),
    OPERA("OPERA"),
    FIREFOX("FIREFOX");

    public final String opt;

    BrowserOptions(String opt) {
        this.opt = opt;
    }

    public String getOpt(){
        return opt;
    }
}
