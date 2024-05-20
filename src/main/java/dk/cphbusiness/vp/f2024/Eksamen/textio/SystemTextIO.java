package dk.cphbusiness.vp.f2024.Eksamen.textio;

import java.util.List;
import java.util.Scanner;

public class SystemTextIO implements TextIO {
    private Scanner scanner;

    public SystemTextIO() {
        scanner = new Scanner(System.in);
    }


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

    @Override
    public int choose(String question, String[] options, String choose) {
        System.out.println(question);
        for (int i = 0; i < options.length; i++) {
            int index = i + 1;
            System.out.println("[" + index  + "] " + options[i]);
        }
        System.out.print(choose);

        while (true) {
            int input = getInt();
            if(input <= 0 || input > options.length) {
                System.out.print("Please enter an integer between 1 and " + (options.length) + ": ");

            } else {
                return input - 1;
            }
        }
    }

    @Override
    public int choose(String question, List<String> options, String choose) {
        return 0;
    }

}
