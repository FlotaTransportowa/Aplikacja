package validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {
        public static boolean regexChecker(String theRegex, String checkStr){
            Pattern checkRegex;
            checkRegex = Pattern.compile(theRegex, Pattern.CASE_INSENSITIVE);

            Matcher regexMatcher = checkRegex.matcher(checkStr);

            return regexMatcher.find();
        }

        public static boolean isInteger(String s) {
            try {
                Integer.parseInt(s);
            } catch(NumberFormatException e) {
                return false;
            } catch(NullPointerException e) {
                return false;
            }
            return true;
        }

        public static boolean isDouble(String s) {
            try {
                Double.parseDouble(s);
            } catch(NumberFormatException e) {
                return false;
            } catch(NullPointerException e) {
                return false;
            }
            return true;
        }
}
