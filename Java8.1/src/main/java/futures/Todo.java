package futures;

public class Todo {
    private int userId;
    private int id;
    private String title;
    private Boolean completed;

    @Override
    public String toString() {
        return "Todo{" +
                "userId=" + userId +
                ", id=" + id +
                ", title='" + title + '\'' +
                ", completed=" + completed +
                '}';
    }

    public Todo(int userId, int id, String title, Boolean completed) {
        this.userId = userId;
        this.id = id;
        this.title = title;
        this.completed = completed;
    }

    public String getTitle() {
        return title;
    }

    public int getId() { return id; }

    public int getUserId() { return userId; }

    public Boolean getCompleted() {
        return completed;
    }
}