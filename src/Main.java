import java.io.*;
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

            String filename = "";

            if (optionO) {filename = resultPath;}
            if (optionP) {filename += resultPrefix;}

            if (!integers.isEmpty()) {
                fileCreator(filename + "integers.txt");
                writeInFile(integers, filename + "integers.txt");
            }

            if (!floats.isEmpty()) {
                fileCreator(filename + "floats.txt");
                writeInFile(floats, filename + "floats.txt");
            }

            if (!strings.isEmpty()) {
                fileCreator(filename + "strings.txt");
                writeInFile(strings, filename + "strings.txt");
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

    public static void fileCreator(String filename) {
        try {
            File file = new File(filename);
            if (file.createNewFile()) {
                System.out.println("Файл " + filename + " создан");
            }
        } catch (IOException e) {
            System.err.println("Ошибка при создании файла: " + e.getMessage());
        }

    }

    public static void writeInFile(ArrayList<String> lines, String filename){
        try {
            FileWriter fileWriter = new FileWriter(filename, optionA);
            for (String line : lines) {
                fileWriter.write(line + "\n");
                fileWriter.flush();
            }
        } catch (IOException e) {
            System.err.println("Ошибка при открытии файла: " + e.getMessage());
        }
    }

    public static void checkOptions(String[] args){
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-o":
                    optionO = true;
                    System.out.println("Опция о применена");
                    resultPath = args[++i];
                    if (resultPath.charAt(0) == '-') {
                        System.err.println("Ошибка: после опции -o должен быть введен путь");
                        System.exit(1);
                    }
                    if (resultPath.charAt(resultPath.length() - 1) != '/') {resultPath += '/';}
                    System.out.println("Путь: " + resultPath);
                    break;
                case "-p":
                    optionP = true;
                    System.out.println("Опция p применена");
                    resultPrefix = args[++i];
                    System.out.println("Префикс: " + resultPrefix);
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
                    System.err.println("Несуществующая опция: " + args[i]);
                    System.exit(1);
            }
        }
    }
}