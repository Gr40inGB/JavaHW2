
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

