package collection;

import java.util.List;
import java.util.Optional;

/**
 * User: starblood
 * Date: 2014. 4. 11.
 * Time: 오전 9:30
 */
import static collection.Folks.friends;

public class PickAnElementElegant {
    public static void pickName(final List<String> names, final String startingLetter) {
        final Optional<String> foundName = names.stream().filter(name -> name.startsWith(startingLetter)).findFirst();
        System.out.println(String.format("%s %s", startingLetter,  foundName.orElse("Not found")));
    }
    public static void main(String[] args) {
        pickName(friends, "J");
        pickName(friends, "N");
        pickName(friends, "F");
    }
}
