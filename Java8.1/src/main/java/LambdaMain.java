import java.util.function.Function;

public class LambdaMain {
    public static void main(String[] args) {
        // clase anonime
        Animal dog = new Animal() {
            @Override
            public String jump(String value) {
                return "Animal jumps " + value;
            }
        };

        Animal cat = new Animal() {
            @Override
            public String jump(String value) {
                return "Cat likes to jump " + value;
            }
        };

        System.out.println(dog.jump("low"));
        System.out.println(cat.jump("always"));

        // lambda
        Animal pet = (value) -> "Pet likes to jump " + value;
        System.out.println(pet.jump("high"));

        // fara Animal
        Function<String, String> cow = new Function<String, String>() {
            @Override
            public String apply(String s) {
                return "Cow likes to jump " + s;
            }
        };

        System.out.println(cow.apply("never"));

        Function<String, String> bird = (s) -> "Bird likes to jump " + s;
        System.out.println(bird.apply("sometimes"));
    }
}
