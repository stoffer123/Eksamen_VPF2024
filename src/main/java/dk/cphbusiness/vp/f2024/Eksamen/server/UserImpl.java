package dk.cphbusiness.vp.f2024.Eksamen.server;

import dk.cphbusiness.vp.f2024.Eksamen.server.interfaces.User;

import java.net.Socket;

public class UserImpl implements User {
    private Socket socket;
    public UserImpl(Socket socket) {
        this.socket = socket;
    }
}
