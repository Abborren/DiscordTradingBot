package discordBot.bot.botIO.input.commands;

import java.util.ArrayList;

class TradingInput {
    String[] returnItems(String[] message) {
        ArrayList<String> returnList = new ArrayList<String>();
        for (String thisMessage : message) {
            if (thisMessage.startsWith("<:")) {
                returnList.add(thisMessage);
            }
        }
        return returnList.toArray(new String[0]);
    }
    String[] returnRemoveItems(String[] message, boolean bool) {
        ArrayList<String> returnList = new ArrayList<String>();
        for (String thisMessage : message) {
            if (thisMessage.startsWith("<:")) {
                returnList.add(thisMessage);
            } else if (bool){
                returnList.add(null);
            }
        }
        return returnList.toArray(new String[0]);
    }
    String[] returnAmount(String[] message) {
        ArrayList<String> returnList = new ArrayList<String>();
        for (String thisMessage : message) {
            if (!thisMessage.startsWith(":") && canParse(thisMessage)) {
                returnList.add(thisMessage);
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
