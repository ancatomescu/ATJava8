import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamExamples {
    public static void main(String[] args) {
        //1
        User user1 = User.createUser();
        User user2 = User.createUser();
        User user3 = User.createUser();
        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        users.add(user3);
        users.add(user1);
        Stream<User> myStream = users.stream();
        System.out.println(users);

        //2
        Stream<User> anotherStream = Stream.generate(User::createUser).limit(3);

        myStream.filter(val -> val.getAge() > 50).forEach(System.out::println);


        users.stream().map(val -> {val.setAge(val.getAge() * 2);
            return val;
        })
                .forEach((System.out::println));

        Optional<User> last = users.stream().reduce((a, b) -> b);
        last.ifPresent(System.out::println);

        Comparator<User> comparator = (a, b) -> a.getAge() - b.getAge();

        Optional<User> youngest = users.stream().min(comparator);
        youngest.ifPresent(System.out::println);

        Optional<User> oldest = users.stream().max(comparator);
        oldest.ifPresent(System.out::println);

        List<User> noDupes = users.stream().distinct().collect(Collectors.toList());
        System.out.println(noDupes);

        users.stream().filter(val -> val.getAge() > 30).map(val -> {val.setName(val.getName().toUpperCase());
        return val;}).sorted(comparator.reversed()).forEach(System.out::println);
    }
}
