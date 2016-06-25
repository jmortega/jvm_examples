package collection;

/**
 * User: starblood
 * Date: 2014. 4. 11.
 * Time: 오전 9:38
 */

import java.util.function.BinaryOperator;

import static collection.Folks.friends;

public class PickALongest {
    public static void main(String[] args) {
        System.out.println(friends.stream().mapToInt(name -> name.length()).sum());

        friends.stream().reduce((name1, name2) -> name1.length() >= name2.length() ? name1 : name2);
    }
}
