import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class AddLog {

    public static void main(String[] args) {
        writeInFile("");
    }

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