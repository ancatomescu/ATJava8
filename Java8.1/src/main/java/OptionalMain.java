import java.util.Optional;

public class OptionalMain {
    public static void main(String[] args) throws Exception {
//        Optional<String> opt = Optional.ofNullable(null);
        Optional<String> opt = Optional.ofNullable("Cat");

        opt.ifPresent( (value) -> System.out.println(value));

        String value = opt.orElse("Dog");

        System.out.println(value);

        String value2 = opt.orElseThrow(Exception::new);
        System.out.println(value2);

        System.out.println(opt.get());
    }
}
