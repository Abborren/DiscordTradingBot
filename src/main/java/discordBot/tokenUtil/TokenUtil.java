package discordBot.tokenUtil;
import java.io.*;

public class TokenUtil {
    public String loadToken() {
        String fileName = "Config/Token/DiscordToken.txt";
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String  line = bufferedReader.readLine();
            bufferedReader.close();
            return line;
        }
        catch(FileNotFoundException ex) {
            System.out.println("Unable To Read Token, quitting");
            System.exit(0);
        }
        catch(IOException ex) {
            System.out.println("Error reading file '" + fileName + "'");
            System.exit(0);
        }
        return null;
    }
}
