public class JavaEquality {

    public static boolean isEqual(Integer i, Integer j) {
        return i == j;
    }

    public static boolean test1() {
        return isEqual(421, 421);
    }

    public static boolean test2() {
        String x = "abcd".substring(2);
        String y = "abcd".substring(2);
        return x == y;
    }

    public static void main(String[] array) {
        System.out.println(test1());
        System.out.println(test2());
    }

    public static Integer g() {
        return 3;
    }
}

