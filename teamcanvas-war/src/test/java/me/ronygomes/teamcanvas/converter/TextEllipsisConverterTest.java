package me.ronygomes.teamcanvas.converter;

import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import me.ronygomes.teamcanvas.test.helper.TextHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class TextEllipsisConverterTest {

    private static final String SMALL_INPUT = "Hello";

    Converter<String> converter;

    @Mock
    FacesContext facesContext;

    @Mock
    UIComponent uiComponent;

    @BeforeEach
    void setup() {
        this.converter = new TextEllipsisConverter();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAsObject() {
        Assertions.assertNull(converter.getAsObject(facesContext, uiComponent, null));
        Mockito.verifyNoInteractions(facesContext, uiComponent);
    }

    @Test
    void testGetAsStringNullInput() {

        Assertions.assertNull(converter.getAsString(facesContext, uiComponent, null));
        Mockito.verifyNoInteractions(facesContext, uiComponent);
    }

    @Test
    void testGetAsStringReturnsSameSmallInput() {

        Assertions.assertEquals(SMALL_INPUT, converter.getAsString(facesContext, uiComponent, SMALL_INPUT));

        String length100Input = TextHelper.generateText('a', 100);
        Assertions.assertEquals(length100Input, converter.getAsString(facesContext, uiComponent, length100Input));
        Mockito.verifyNoInteractions(facesContext, uiComponent);
    }

    @Test
    void testGetAsStringBlankInput() {
        String input01 = TextHelper.generateText(' ', 101);
        Assertions.assertEquals("", converter.getAsString(facesContext, uiComponent, input01));

        String input02 = input01 + 'a' + input01;
        Assertions.assertEquals("a", converter.getAsString(facesContext, uiComponent, input02));
    }

    @Test
    void testGetAsStringReturnsTrimmedLargeInput() {

        String input01 = TextHelper.generateText('a', 101);
        String output01 = TextHelper.generateText('a', 96) + " ...";

        Assertions.assertEquals(output01, converter.getAsString(facesContext, uiComponent, input01));

        String input02 = TextHelper.generateText('a', 99);
        String input02Full = input02 + " " + input02;
        String output02 = input02 + " ...";

        Assertions.assertEquals(output02, converter.getAsString(facesContext, uiComponent, input02Full));

        Mockito.verifyNoInteractions(facesContext, uiComponent);
    }
}
