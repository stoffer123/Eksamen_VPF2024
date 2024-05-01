package dk.cphbusiness.vp.f2024.Eksamen.server;

public class Message {
    private ClientThread clientThread;
    private String message;

    public Message(ClientThread clientThread, String message) {
        this.clientThread = clientThread;
        this.message = message;
    }

    public String getClientThreadName() {
        return clientThread.getName();
    }

    public String getMessage() {
        return message;
    }
}
