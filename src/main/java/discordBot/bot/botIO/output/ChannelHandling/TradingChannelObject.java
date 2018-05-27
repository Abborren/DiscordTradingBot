package discordBot.bot.botIO.output.ChannelHandling;


import discordBot.bot.fileUtil.FileManager;

import java.io.File;

public class TradingChannelObject {
    public String name;
    public int id;
    public String[][] items = new String[10][2];
    public String callSign;
    private String[][] itemPairs = new FileManager().loadString2dArray(new File("Config/Variables/Items/TradingItems.txt"));

    TradingChannelObject(String name, String callSign, int id) {
        this.name = name;
        this.id = id;
        this.callSign = callSign;
        resetItemAmount();
    }
    private void resetItemAmount() {
        for (int i= 0; i < items.length; i++) {
            items[i][1] = "N/A";
        }
    }

    public void removeItem(String tradingItem) {
        for (int i = 0; i < itemPairs.length; i++) {

            if (itemPairs[i][0].equalsIgnoreCase(tradingItem) || itemPairs[i][1].equalsIgnoreCase(tradingItem)) {
                items[((i+1)*2)-2][0] = null;
                items[((i+1)*2)-1][0] = null;
                items[((i+1)*2)-2][1] = "N/A";
                items[((i+1)*2)-1][1] = "N/A";
                break;
            }

        }
        if (tradingItem == null) {
            for (int i=0; i < itemPairs.length*2;i++) {
                items[i][0] = null;
                items[i][1] = "N/A";
            }
        }
        //System.out.println("Channel now contains "+ Arrays.deepToString(items)); // for debug
    }

    public void addItem(String tradingItem, String amount) {

        for (int i = 0; i < itemPairs.length; i++) {
            boolean b = false;
            if (itemPairs[i][0].equalsIgnoreCase(tradingItem) || itemPairs[i][1].equalsIgnoreCase(tradingItem)) {
                items[((i + 1) * 2) - 2][0] = itemPairs[i][0];
                items[((i + 1) * 2) - 1][0] = itemPairs[i][1];
                b = true;
           }
           if (amount != null && itemPairs[i][0].equalsIgnoreCase(tradingItem)) {
                items[(i+1)*2-2][1] = amount;
           } else if (amount != null && itemPairs[i][1].equalsIgnoreCase(tradingItem)) {
                items[(i+1)*2-1][1] = amount;
           }
           if (b) {
                break;
           }
        }
        //System.out.println("Channel now contains "+ Arrays.deepToString(items)); // for debug
    }
}
