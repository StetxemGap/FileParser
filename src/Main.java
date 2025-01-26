import java.io.*;
import java.util.ArrayList;

public class Main {
    private static boolean optionO = false; //путь для результатов
    private static boolean optionP = false; //префикс выходных файлов
    private static boolean optionA = false; //добавить новые элементы к существующим
    private static boolean optionS = false; //краткая статистика(сколько было добавлено в каждый из выходных файлов)
    private static boolean optionF = false; //полная статистика(числа: min, max, sum, mu; строки: min, max)

    private static String inputFile;
    private static String resultPath;
    private static String resultPrefix;

    public static void main(String[] args) {
        checkOptions(args);
        if (inputFile == null) {
            System.out.println("Ошибка: не указаны входные файлы");
            return;
        }
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
                if (optionS) {System.out.println("\nКоличество добавленных в " + resultPrefix + "integer.txt элементов: " + integers.size());}
                if (optionF) {fullStatistics(integers, 1);}

                fileCreator(filename + "integers.txt");
                writeInFile(integers, filename + "integers.txt");
            }

            if (!floats.isEmpty()) {
                if (optionS) {System.out.println("\nКоличество добавленных в " + resultPrefix + "floats.txt элементов: " + floats.size());}
                if (optionF) {fullStatistics(floats, 2);}

                fileCreator(filename + "floats.txt");
                writeInFile(floats, filename + "floats.txt");
            }

            if (!strings.isEmpty()) {
                if (optionS) {System.out.println("\nКоличество добавленных в " + resultPrefix + "strings.txt элементов: " + strings.size());}
                if (optionF) {fullStatistics(strings, 3);}

                fileCreator(filename + "strings.txt");
                writeInFile(strings, filename + "strings.txt");
            }

        } catch (IOException e) {
            System.err.println("Ошибка чтения файла: " + e.getMessage());
        }
    }

    public static boolean isInteger(String line){
        try{
            Long.parseLong(line);
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

    public static void fullStatistics(ArrayList<String> lines, Integer type) {
        switch (type) {
            case 1:
                ArrayList<Long> integers = new ArrayList<>();
                for (String line : lines) {
                    integers.add(Long.parseLong(line));
                }
                Long maxInt = 0L;
                Long minInt = integers.getFirst();
                Long sumInt = 0L, muInt;
                for (Long elem : integers) {
                    sumInt += elem;
                    maxInt = Math.max(maxInt, elem);
                    minInt = Math.min(minInt, elem);
                }
                muInt = sumInt / integers.size();

                System.out.println("\nПолная статистика для " + resultPrefix + "integers.txt:" +
                        "\n\tДобавлено элементов: " + integers.size() +
                        "\n\tМаксимальное значение: " + maxInt +
                        "\n\tМинимальное значение: " + minInt +
                        "\n\tСумма элементов: " + sumInt +
                        "\n\tСреднее значение: " + muInt);
                break;
            case 2:
                ArrayList<Float> floats = new ArrayList<>();
                for (String line : lines) {
                    floats.add(Float.parseFloat(line));
                }
                Float maxFloat = 0.0F;
                Float minFloat = floats.getFirst();
                Float sumFloat = 0.0F, muFloat;
                for (Float elem : floats) {
                    sumFloat += elem;
                    maxFloat = Math.max(maxFloat, elem);
                    minFloat = Math.min(minFloat, elem);
                }
                muFloat = sumFloat / floats.size();

                System.out.println("\nПолная статистика для " + resultPrefix + "floats.txt:" +
                        "\n\tДобавлено элементов: " + floats.size() +
                        "\n\tМаксимальное значение: " + maxFloat +
                        "\n\tМинимальное значение: " + minFloat +
                        "\n\tСумма элементов: " + sumFloat +
                        "\n\tСреднее значение: " + muFloat);
                break;
            case 3:
                Integer maxStr = 0;
                Integer minStr = lines.getFirst().length();
                for (String line : lines) {
                    maxStr = Math.max(maxStr, line.length());
                    minStr = Math.min(minStr, line.length());
                }

                System.out.println("\nПолная статистика для " + resultPrefix + "strings.txt:" +
                        "\n\tДобавлено элементов: " + lines.size() +
                        "\n\tРазмер самой короткой строки: " + maxStr +
                        "\n\tРазмер самой короткой строки: " + minStr);
                break;
            default:
                break;
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
                    if (args[i].length() > 4 && args[i].substring(args[i].length() - 4) .equals(".txt")) {
                        inputFile = args[i];
                    } else {
                        System.err.println("Несуществующая опция: " + args[i]);
                        System.exit(1);
                    }
            }
        }
    }
}