package bg.webapp.shop.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PasswordGenerator {
    public static String generatePassword() {
        StringBuilder password = new StringBuilder();
        int range = 25;
        List<Integer> generatedNumbers = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            int randomAsciiNumberSmall = (int) (Math.random() * range + 97);
            generatedNumbers.add(randomAsciiNumberSmall);
        }

        for (int u = 0; u < 5; u++) {
            int randomAsciiNumberBig = (int)(Math.random()*range+65);
            generatedNumbers.add(randomAsciiNumberBig);
        }
        System.out.println(generatedNumbers);
        Collections.shuffle(generatedNumbers);
        for (Integer generatedNumber : generatedNumbers) {
            char x = (char)generatedNumber.intValue();
            password.append(x);
        }
        return password.toString();
    }
}
