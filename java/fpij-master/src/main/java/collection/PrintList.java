package collection;

/**
 * User: starblood
 * Date: 2014. 4. 11.
 * Time: 오전 10:09
 */
import static collection.Folks.friends;
import static java.util.stream.Collectors.joining;

public class PrintList {
    public static void main(String[] args) {

        // 아래의 수식들은 동일하다.
        System.out.println(String.join(", ", friends)); // more concise
        System.out.println(friends.stream().reduce((name1, name2) -> name1 + ", " + name2).orElse(""));
        System.out.println(friends.stream().collect(joining(", ")));
    }
}
