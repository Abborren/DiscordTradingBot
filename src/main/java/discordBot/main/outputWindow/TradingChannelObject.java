package discordBot.main.outputWindow;

public class TradingChannelObject {
    public String name;
    public int id;
    public String[][] items = new String[10][2];
    public String callCommand;
    public TradingChannelObject(String name, String callCommand, int id) {
        this.name = name;
        this.id = id;
        this.callCommand = callCommand;
        resetItemAmount();
    }
    private void resetItemAmount() {
        for (int i= 0; i < items.length; i++) {
            items[i][1] = "N/A";
        }
    }
}
