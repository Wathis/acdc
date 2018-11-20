package exception;

public class UnopenedSession extends Exception {

    public UnopenedSession() {
        super("You must open a session before using the methods");
    }

}
