import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<String> lines = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("src/in1.txt"))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                lines.add(line);
            }
            cheсkType(lines);
        } catch (IOException e) {
            System.err.println("Ошибка чтения файла: " + e.getMessage());
        }
    }

    public static void cheсkType(ArrayList<String> lines) {
        for (String line : lines) {
            if (isInteger(line)) {
                System.out.println(line + " - целое число");
            } else if (isFloat(line)) {
                System.out.println(line + " - вещественное число");
            } else {
                System.out.println(line + " - строка");
            }
        }
    }

    public static boolean isInteger(String line){
        try{
            Integer.parseInt(line);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isFloat(String line){
        try{
            Float.parseFloat(line);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}