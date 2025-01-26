import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    private static boolean optionO = false; //путь для результатов
    private static boolean optionP = false; //префикс выходных файлов
    private static boolean optionA = false; //добавить новые элементы к существующим
    private static boolean optionS = false; //краткая статистика(сколько было добавлено в каждый из выходных файлов)
    private static boolean optionF = false; //полная статистика(числа: min, max, sum, mu; строки: min, max)

    private static String resultPath;
    private static String resultPrefix;

    public static void main(String[] args) {
        checkOptions(args);
        ArrayList<String> integers = new ArrayList<>();
        ArrayList<String> floats = new ArrayList<>();
        ArrayList<String> strings = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("src/in1.txt"))) {
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                if (isInteger(line)) {
                    integers.add(line);
                } else if (isFloat(line)) {
                    floats.add(line);
                } else {
                    strings.add(line);
                }
            }

            if (!integers.isEmpty()) {
                fileCreator("integers.txt");
            }

            if (!floats.isEmpty()) {
                fileCreator("floats.txt");
            }

            if (!strings.isEmpty()) {
                fileCreator("strings.txt");
            }

        } catch (IOException e) {
            System.err.println("Ошибка чтения файла: " + e.getMessage());
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

    public static void fileCreator(String filename) throws IOException {
        if (optionP) {filename = resultPrefix + filename;}
        if (optionO) {filename = resultPath + filename;}

        try {
            File file = new File(filename);
            if (file.createNewFile()) {
                System.out.println("Файл" + filename + "создан");
            } else {
                System.out.println("Файл" + filename + "уже существует");
            }
        } catch (IOException e) {
            System.out.println("Ошибка при создании файла");
            e.printStackTrace();
        }

    }

    public static void checkOptions(String[] args){
        System.out.println("\n*************************** Анализ операций ***************************\n");
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-o":
                    optionO = true;
                    System.out.println("Опция о применена");
                    resultPath = args[++i];
                    System.out.println("Путь для результатов: " + resultPath);
                    break;
                case "-p":
                    optionP = true;
                    System.out.println("Опция p применена");
                    resultPrefix = args[++i];
                    System.out.println("Префикс для файлов с результатом: " + resultPrefix);
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