package discordBot.main.botIO;

import java.util.ArrayList;

public class TradingInput {
    public String[] returnItems(String[] message) {
        ArrayList<String> returnList = new ArrayList<String>();
        for (String thisMessage : message) {
            if (thisMessage.startsWith("<:")) {
                returnList.add(thisMessage);
            } else {
                returnList.add(null);
            }
        }
        return returnList.toArray(new String[0]);
    }
    public String[] returnAmount(String[] message) {
        ArrayList<String> returnList = new ArrayList<String>();
        for (String thisMessage : message) {
            if (!thisMessage.startsWith(":") && canParse(thisMessage)) {
                returnList.add(thisMessage);
            } else {
                returnList.add("N/A");
            }
        }
        return returnList.toArray(new String[0]);
    }
    private boolean canParse(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }

    }
}
