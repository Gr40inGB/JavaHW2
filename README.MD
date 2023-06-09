[Task1and2.java](src%2FTask1and2.java)
Дана json строка
```json
 [{"фамилия":"Иванов","оценка":"5","предмет":"Математика"}, 
  {"фамилия":"Петрова","оценка":"4","предмет":"Информатика"},
  {"фамилия":"Краснов","оценка":"5","предмет":"Физика"}]
```
Задача написать метод(ы), который распарсит строку и выдаст ответ вида:
```
Студент Иванов получил 5 по предмету Математика.
Студент Петрова получил 4 по предмету Информатика.
Студент Краснов получил 5 по предмету Физика.
```
Используйте StringBuilder для подготовки ответа
Исходная json строка это просто String !!! Для работы используйте методы String,
 такие как replace, split, substring и т.д. по необходимости
 Создать метод, который запишет результат работы в файл. Обработайте исключения и запишите ошибки в лог файл.
2. *Получить исходную json строку из файла, используя FileReader или Scanner
```java
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Task1and2 {
    public static void main(String[] args) {
        String json = readFile("textJSON.txt");
        safeToFile(parseWithCharArray(json), "ParsedJSON.txt");
    }

    public static void safeToFile(String s, String path) {
        Logger logger = Logger.getAnonymousLogger();
        FileHandler fileHandler = null;
        try {
            fileHandler = new FileHandler("ErrorTask1.log");
            SimpleFormatter formatter = new SimpleFormatter();
            fileHandler.setFormatter(formatter);
            logger.addHandler(fileHandler);
            logger.log(Level.INFO, "Логирование записи начато. Переданный путь:" + path);
        } catch (IOException e) {
            logger.log(Level.WARNING, "ошибка");
            throw new RuntimeException(e);
        }

        try {
            FileWriter fw = new FileWriter(path);
            fw.append(s);
            fw.flush();
            fw.close();
            logger.log(Level.INFO, "Файл записан успешно");

        } catch (IOException e) {
            logger.log(Level.WARNING, "Произошла ошибка записи: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static String parseWithCharArray(String s) {
        StringBuilder currentWord = new StringBuilder();
        StringBuilder result = new StringBuilder();

        char[] chars = s.toCharArray();
        for (char c : chars) {
            if (Character.isLetterOrDigit(c)) {
                currentWord.append(c);
            } else {
                if (c == ':') {
                    switch (currentWord.toString()) {
                        case "фамилия" -> result.append(result.isEmpty() ? "Студент " : "\nСтудент ");
                        case "оценка" -> result.append(" получил ");
                        case "предмет" -> result.append(" по предмету ");
                    }
                    currentWord.delete(0, currentWord.length());
                } else if (c == ',') {
                    result.append(currentWord);
                    currentWord.delete(0, currentWord.length());
                } else if (c == '}') {
                    result.append(currentWord);
                    result.append(".");
                    currentWord.delete(0, currentWord.length());
                }
            }
        }
        return result.toString();
    }

    public static String parseStringMetods(String s) {
        return (s.replaceAll(" ", "")
                .replaceAll("\\[\\{\"ф.+?\":\"", "Студент ")
                .replaceAll("\"\\},\\{\"ф.+?\":\"", ".\nСтудент ")
                .replaceAll("\",\"о.+?\":\"", " получил ")
                .replaceAll("\",\"п.+?\":\"", " по предмету ")
                .replaceAll("\"}]", "."));
    }

    public static String readFile(String path) {
        StringBuilder sb = new StringBuilder();
        try (FileReader reader = new FileReader(path)) {
            Scanner scanner = new Scanner(reader);
            while (scanner.hasNextLine()) {
                sb.append(scanner.nextLine());
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return sb.toString();
    }
}
```

[Task3.java](src%2FTask3.java)
 3. Реализуйте алгоритм сортировки пузырьком числового массива,
// результат после каждой итерации запишите в лог-файл.
```java
import java.io.IOException;
import java.util.*;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Task3 {
    // 3. *Реализуйте алгоритм сортировки пузырьком числового массива,
    // результат после каждой итерации запишите в лог-файл.
    public static void main(String[] args) {

        int[] arr = getArr(15, 1, 1000);
        System.out.println(Arrays.toString(arr));
        bubble(arr);
    }

    public static int[] getArr(int l, int min, int max) {
        int[] arr = new int[l];
        Random r = new Random();
        for (int i = 0; i < l; i++) {
            arr[i] = r.nextInt(min, max);
        }
        return arr;
    }

    public static void bubble(int[] arr) {
        Logger logger = Logger.getAnonymousLogger();
        FileHandler fileHandler = null;
        try {
            fileHandler = new FileHandler("bubble.log");
            SimpleFormatter formatter = new SimpleFormatter();
            fileHandler.setFormatter(formatter);
            logger.addHandler(fileHandler);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        logger.log(Level.INFO, "Получен массив:\n" + Arrays.toString(arr));

        for (int j = 0; j < arr.length; j++) {
            boolean find_bubble = false;
            for (int i = 1; i < arr.length - j; i++) {
                if (arr[i] < arr[i - 1]) {
                    int temp = arr[i];
                    arr[i] = arr[i - 1];
                    arr[i - 1] = temp;
                    find_bubble = true;
                }
            }
//            System.out.println(Arrays.toString(arr));
            logger.log(Level.INFO, "итерация " + (j + 1) + ": " + Arrays.toString(arr) + (find_bubble ? " Есть пузырёк " : " Нет пузырька"));
        }
        fileHandler.close();
    }
}
```