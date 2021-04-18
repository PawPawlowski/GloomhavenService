package gloomhavenService.util;

public class NumberUtil {

    public static int tryParse(String s) {
        return tryParse(s, 0);
    }

    public static int tryParse(String s, int defVal) {
        int result = defVal;
        try {
            result = Integer.parseInt(s);
        } catch (NumberFormatException e) {
            // do nothing. Caller to handle this
        }
        return result;
    }

}
