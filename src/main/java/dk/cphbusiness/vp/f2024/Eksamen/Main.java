package dk.cphbusiness.vp.f2024.Eksamen;

import dk.cphbusiness.vp.f2024.Eksamen.textio.Textio;
import dk.cphbusiness.vp.f2024.Eksamen.textio.TextioImpl;

public class Main {
    public static void main(String[] args) {
        Textio io = new TextioImpl();
        String[] options = new String[] {"Test1", "test2", "test3"};

        while(true) {
            int index = io.choose("Vælg din test", options, "Vælg: ");
            System.out.println(options[index]);
        }

    }
}