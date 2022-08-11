import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamMain {
    public static void main(String[] args) {
        Stream<Integer> stream = Stream.of(1,2,3,4,5,6,7,8,9,10);
        stream.filter(val -> val > 8).forEach(System.out::println);


        stream = Stream.of(1,2,3,4,5,6,7,8,9,10);

        List<Integer> collected = stream
                .map(val -> val * 2) // aplica val*2 pe fiecare element din stream
                .filter(val -> val > 5) // filtrare de valori
                .collect(Collectors.toList()); // agregare intr-o colectie

        System.out.println(collected);

        stream = Stream.of(1,2,3,4,5,6,7,8,9,10);
        long counter = stream
                .map(val -> val * 2) // aplica val*2 pe fiecare element din stream
                .filter(val -> val > 5) // filtrare de valori
                .count();
        System.out.println(counter);

        stream = Stream.of(1,2,3,4,5,6,7,8,9,10);
        Optional<Integer> sum = stream.reduce((sumAux, val) -> sumAux + val);
        sum.ifPresent(val -> System.out.println(val));
//        System.out.println(sum);
        
    }
}
