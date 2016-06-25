import java.util.ArrayList;
import java.util.List;

/**
 * Created by starblood on 2014. 4. 9..
 */
public class SumTest {
    private static List<Integer> list = new ArrayList<>();

    static {
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        list.add(6);
    }

    public static int sumByImperativeWay() {
        int sum = 0; // mutable variable
        for (int i : list) { // iteration
            sum += i;
        }
        return sum;
    }

    public static int sumByFunctionalWay() {
        // no iteration, concise
        return list.stream().mapToInt(Integer::intValue).sum();
    }

    public static void main(String[] args) {
        System.out.println("imperative: " + sumByImperativeWay());
        System.out.println("imperative: " + sumByFunctionalWay());
    }
}
