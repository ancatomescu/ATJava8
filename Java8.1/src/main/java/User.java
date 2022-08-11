import java.util.Random;
import java.util.UUID;

public class User {
    private int id;
    private String name;
    private int age;
    private static int index = 0;

    public static User createUser(){
        User user = new User();
        index ++;
        user.id = index;
        UUID nameRandom = UUID.randomUUID();
        user.name = nameRandom.toString();
        Random random = new Random();
        user.age = random.nextInt(100);
       return user;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public static int getIndex() {
        return index;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public static void setIndex(int index) {
        User.index = index;
    }
}
