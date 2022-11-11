import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class Task4 {
    public static void main(String[] args) {
        ArrayList<Integer> numbers = new ArrayList<>();

        try {
            Scanner scannerPoints = new Scanner(new File(args[0]));
            {
                while (scannerPoints.hasNextLine()) {
                    numbers.add(Integer.parseInt(scannerPoints.nextLine()));
                }

            }
            scannerPoints.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        numbers.sort(Comparator.comparingInt(o -> o));
        int median = numbers.get((int) Math.floor(numbers.size() / 2.0));
        int sum = numbers.stream().mapToInt((number) -> Math.abs(number - median)).sum();

        System.out.println(sum);
    }
}
