package work.vincentchu.libs.spellvnnumber.type;

public class SpecificText implements ISpecificText {
    private String oddText = "lẻ";
    private String tenText = "mười";
    private String oneToneText = "mốt";
    private String fourToneText = "tư";
    private String fiveToneText = "lăm";

    public String getOddText() {
        return oddText;
    }

    public void setOddText(String oddText) {
        this.oddText = oddText;
    }

    public String getTenText() {
        return tenText;
    }

    public void setTenText(String tenText) {
        this.tenText = tenText;
    }

    public String getOneToneText() {
        return oneToneText;
    }

    public void setOneToneText(String oneToneText) {
        this.oneToneText = oneToneText;
    }

    public String getFourToneText() {
        return fourToneText;
    }

    public void setFourToneText(String fourToneText) {
        this.fourToneText = fourToneText;
    }

    public String getFiveToneText() {
        return fiveToneText;
    }

    public void setFiveToneText(String fiveToneText) {
        this.fiveToneText = fiveToneText;
    }
}
