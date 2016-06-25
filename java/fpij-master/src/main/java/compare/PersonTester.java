package compare;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.reducing;
import static java.util.stream.Collectors.toList;

/**
 * User: starblood
 * Date: 2014. 4. 14.
 * Time: 오전 9:26
 */
public class PersonTester {
    public static void main(String[] args) {
        final List<Person> people = Arrays.asList(
                new Person("John", 20),
                new Person("Sara", 21),
                new Person("Jane", 21),
                new Person("Greg", 35));

        System.out.println(
                people.stream().sorted((person1, person2) -> person1.ageDifference(person2)).collect(toList())
        );

        // sort ascending order
        System.out.println(
                people.stream().sorted(Person::ageDifference).collect(toList())
        );

        Comparator<Person> comparatorAsc = (person1, person2) -> person1.ageDifference(person2);
        Comparator<Person> comparatorDesc = comparatorAsc.reversed();

        // sort ascending order again
        System.out.println(
                people.stream().sorted(comparatorAsc).collect(toList())
        );

        // sort descending order
        System.out.println(
                people.stream().sorted(comparatorDesc).collect(toList())
        );

        // print youngest
        people.stream().min(Person::ageDifference).ifPresent(youngest -> System.out.println(youngest));
        // print oldest
        people.stream().max(Person::ageDifference).ifPresent(eldest -> System.out.println(eldest));

        // multiple and fluent comparison
        final Function<Person, Integer> byAge = person -> person.getAge();
        final Function<Person, String> byName = person -> person.getName();
        // sort ascending age and then name
        System.out.println(
                people.stream().sorted(comparing(byAge).thenComparing(byName)).collect(toList())
        );

        // collect age more than 20
        System.out.println(
                people.stream().filter(person -> person.getAge() > 20).collect(ArrayList::new, ArrayList::add, ArrayList::addAll)
        );
        // same statement
        System.out.println(
                people.stream().filter(person -> person.getAge() > 20).collect(toList())
        );

        // people by age
        System.out.println(
                people.stream().collect(Collectors.groupingBy(person -> person.getAge()))
        );
        // equivalent
        System.out.println(
                people.stream().collect(Collectors.groupingBy(Person::getAge))
        );

        Comparator<Person> byAge2 = Comparator.comparing(Person::getAge);
        Map<Character, Optional<Person>> oldestPersonOfEachLetter =
                people.stream().collect(Collectors.groupingBy(person -> person.getName().charAt(0),
                        reducing(BinaryOperator.maxBy(byAge2))));
        System.out.println("Oldest person of each letter");
        System.out.println(oldestPersonOfEachLetter);

    }
}
