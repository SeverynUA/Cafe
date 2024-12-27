package brainacad.org.Models;

public enum Position {
    Barista("Barista"),
    Waiter("Waiter"),
    Confectioner("Confectioner");

    private final String status;

    Position(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}