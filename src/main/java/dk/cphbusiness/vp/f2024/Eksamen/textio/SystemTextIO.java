package dk.cphbusiness.vp.f2024.Eksamen.textio;

import java.util.Scanner;

public class SystemTextIO implements TextIO {
    private static final Scanner scanner = new Scanner(System.in);

    @Override
    public void put(String message) {
        System.out.println(message);
    }

    @Override
    public String get() {
        String input = scanner.nextLine();
        return input;
    }

    @Override
    public int getInt() {
        while (true) {
            String input = get();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.print("Please enter an integer: ");
            }
        }
    }


}
