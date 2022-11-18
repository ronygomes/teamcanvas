package me.ronygomes.teamcanvas.test.helper;

public class TextHelper {

    public static String generateText(char c, int length) {
        if (length < 0) {
            throw new IllegalArgumentException("Length can not be negative");
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(c);
        }

        return sb.toString();
    }
}
