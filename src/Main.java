import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    private static boolean optionO = false; //путь для результатов
    private static boolean optionP = false; //префикс выходных файлов
    private static boolean optionA = false; //добавить новые элементы к существующим
    private static boolean optionS = false; //краткая статистика(сколько было добавлено в каждый из выходных файлов)
    private static boolean optionF = false; //полная статистика(числа: min, max, sum, mu; строки: min, max)

    public static void main(String[] args) {
        checkOptions(args);
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
                writeToFile(line, 1);
            } else if (isFloat(line)) {
                System.out.println(line + " - вещественное число");
                writeToFile(line, 2);
            } else {
                System.out.println(line + " - строка");
                writeToFile(line, 3);
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

    public static void writeToFile(String content, Integer Type) {

    }

    public static void checkOptions(String[] args){
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-o":
                    optionO = true;
                    System.out.println("Опция о применена");
                    break;
                case "-p":
                    optionP = true;
                    System.out.println("Опция p применена");
                    break;
                case "-a":
                    optionA = true;
                    System.out.println("Опция a применена");
                    break;
                case "-s":
                    optionS = true;
                    System.out.println("Опция s применена");
                    break;
                case "-f":
                    optionF = true;
                    System.out.println("Опция f применена");
                    break;
                default:
                    System.out.println("Несуществующая опция: " + args[i]);
                    break;
            }
        }
    }
}