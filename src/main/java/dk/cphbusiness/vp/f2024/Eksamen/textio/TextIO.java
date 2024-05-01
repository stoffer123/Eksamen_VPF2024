package dk.cphbusiness.vp.f2024.Eksamen.textio;

import java.util.List;

public interface TextIO {
    void put(String message);
    String get();
    int getInt();
    int choose(String question, String[] options, String choose);
    int choose(String question, List<String> options, String choose);

}
