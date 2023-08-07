package bg.webapp.shop.util;

import java.util.regex.Pattern;

public class Validator {

    public static boolean patternMatchesEmail(String emailAddress) {
        return Pattern.compile("^(.+)@(\\S+)$")
                .matcher(emailAddress)
                .matches();
    }

    public static boolean lastNameNotNullCheck(String lastName) {
        return lastName != null;
    }
}
