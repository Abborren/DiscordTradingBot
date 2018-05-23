package discordBot.main.outputWindow;

public class TradingChannel {
    public String name;
    public String[][] items = new String[10][2];
    public String callCommand;
    public TradingChannel(String name, String[][] items, String callCommand) {
        this.name = name;
        this.items = items;
        this.callCommand = callCommand;
        resetItemAmount();
    }
    private void resetItemAmount() {
        for (int i= 0; i < items.length; i++) {
            items[i][1] = "N/A";
        }
    }
}
