package collection;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Created by starblood on 2014. 4. 10..
 */
import static collection.Folks.friends;
import static collection.Folks.comrades;
import static collection.Folks.editors;

public class PickElement {
    public static void main(String[] args) {
        final Function<String, Predicate<String>> startsWithLetter =
                (String letter) -> (String input) -> input.startsWith(letter);

        final long countFriendsStartN = friends.stream().filter(checkIfStartsWith("N")).count();
        System.out.println(countFriendsStartN);

        final long countFriendsStartN2 = friends.stream().filter(startsWithLetter.apply("N")).count();
        System.out.println(countFriendsStartN2);
    }

    public static Predicate<String> checkIfStartsWith(final String letter) {
        return name -> name.startsWith(letter);
    }
}
