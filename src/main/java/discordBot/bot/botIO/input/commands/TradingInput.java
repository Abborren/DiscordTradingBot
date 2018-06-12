package discordBot.bot.botIO.input.commands;

import java.util.ArrayList;

class TradingInput {
    /**
     *  this returns a String array of items
     * @param message input message
     * @return returns a list of items that were inputted on discord
     */
    String[] returnItems(String[] message) {
        ArrayList<String> returnList = new ArrayList<String>();
        for (String thisMessage : message) {
            if (!thisMessage.startsWith("<:") && thisMessage.contains("n") && !thisMessage.equals(message[0])) {
                returnList.add(thisMessage);
            }
            else if (thisMessage.startsWith("<:")) {
                returnList.add(thisMessage);
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
        for (String thisMessage : message) {
            if (!thisMessage.startsWith(":") && thisMessage.contains("n")) {
                returnList.add(thisMessage);
            } else if (thisMessage.startsWith("<:")) {
                returnList.add(thisMessage);
            } else if (removeAll){
                returnList.add(null);
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
        for (String thisMessage : message) {
            if (!thisMessage.startsWith(":") && canParse(thisMessage)) {
                returnList.add(thisMessage);
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
