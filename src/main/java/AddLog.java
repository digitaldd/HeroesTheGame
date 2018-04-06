import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Class contains one method, whom execute write in file (log)
 */
public class AddLog {

    public static void main(String[] args) {
        writeInFile("");
    }

    /**
     * write in text file
     *
     * @param text - what need write in file
     */
    static void writeInFile(String text) {
        String nameFile = "LogGameHeroes.txt";
        String path = "D:\\Heroes\\";
        String pathFile = path + nameFile;
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(pathFile, true));
            String lineSeparator = System.getProperty("line.separator");
            writer.write(text + lineSeparator);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}