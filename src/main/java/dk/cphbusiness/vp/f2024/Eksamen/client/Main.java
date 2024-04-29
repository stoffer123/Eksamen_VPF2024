package dk.cphbusiness.vp.f2024.Eksamen.client;

public class Main {
    public static void main(String[] args) {
        Client client1 = new ClientConsole("127.0.0.1", 23456);
        client1.run();

        Client client2 = new ClientConsole("127.0.0.1", 23456);
        client2.run();

        Client client3 = new ClientConsole("127.0.0.1", 23456);
        client3.run();

        Client client4 = new ClientConsole("127.0.0.1", 23456);
        client4.run();

    }
}
