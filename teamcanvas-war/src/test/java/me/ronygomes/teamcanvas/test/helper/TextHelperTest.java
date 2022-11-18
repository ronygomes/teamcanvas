package me.ronygomes.teamcanvas.test.helper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TextHelperTest {

    @Test
    void testGenerateText() {
        Assertions.assertThrows(IllegalArgumentException.class, () ->TextHelper.generateText('a', -1));
        Assertions.assertEquals("", TextHelper.generateText('a', 0));
        Assertions.assertEquals("a", TextHelper.generateText('a', 1));
        Assertions.assertEquals("aa", TextHelper.generateText('a', 2));
        Assertions.assertEquals("aaaaaaaaaa", TextHelper.generateText('a', 10));
    }
}
