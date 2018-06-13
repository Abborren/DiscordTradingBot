package discordBot.bot.botIO.input.commands;

import discordBot.bot.fileUtil.FileManager;

import java.io.File;
import java.util.ArrayList;

class TradingInput {
    /**
     *  this returns a String array of items
     * @param message input message
     * @return returns a list of items that were inputted on discord
     */
    String[] returnItems(String[] message) {
        ArrayList<String> returnList = new ArrayList<String>();
        String[] tradingArea = new FileManager().loadStringArray(new File("Config/Variables/Items/TradingAreas.txt"),false);
        String[] firstItems = new FileManager().loadStringArray(new File("Config/Variables/Items/TradingItems.txt"),false);
        for (String thisMessage : message) {
            if ( !thisMessage.equals(message[0])){
                if (!thisMessage.startsWith("<:") && thisMessage.contains("n")) {
                    returnList.add("n");
                } else if (thisMessage.startsWith("<:")) {
                    returnList.add(thisMessage);
                }

                for (int i = 0; i < tradingArea.length; i++) {
                    if (thisMessage.equals(tradingArea[i])) {
                        returnList.add(firstItems[i]);
                    }
                }
            }
        }
        return returnList.toArray(new String[0]);
    }

    /**
     * this returns a
     * @param message the input message
     * @param removeAll decides if all amount should be removed
     * @return returns the amount of a all inputted items (if any amount is specified)
     */
    String[] returnRemoveItems(String[] message, boolean removeAll) {
        ArrayList<String> returnList = new ArrayList<String>();
        String[] tradingArea = new FileManager().loadStringArray(new File("Config/Variables/Items/TradingAreas.txt"),false);
        String[] firstItems = new FileManager().loadStringArray(new File("Config/Variables/Items/TradingItems.txt"),true);
        for (String thisMessage : message) {

                if (!thisMessage.startsWith(":") && thisMessage.contains("n")) {
                    returnList.add(thisMessage);
                } else if (thisMessage.startsWith("<:")) {
                    returnList.add(thisMessage);
                } else if (removeAll) {
                    returnList.add(null);
                }
                for (int i = 0; i < tradingArea.length; i++) {
                    if (thisMessage.equals(tradingArea[i])) {
                        returnList.add(firstItems[i]);
                    }
                }

        }
        return returnList.toArray(new String[0]);
    }

    /**
     * @param message the inputted message
     * @return  returns amount of the inputted items (if any amount is specified)
     */
    String[] returnAmount(String[] message) {
        ArrayList<String> returnList = new ArrayList<String>();
        int indexOffset = 1;
        for (int i = 1; i < message.length; i++) {
            if (!message[i].startsWith("<:") && canParse(message[i])) {
                returnList.remove(i-indexOffset);
                returnList.add(message[i]);
            } else  {
                returnList.add(null);
                indexOffset++;
            }

        }
        return returnList.toArray(new String[0]);
    }

    /**
     * checks if a specific String can be integer parsed
     * @param s the input string that needs checking
     * @return returns true or false if the input can be parsed or not
     */
    private boolean canParse(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }

    }
}
