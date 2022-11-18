package me.ronygomes.teamcanvas.converter;

import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;

import java.util.Objects;

/*
    Rewrite the ellipsis converter differently. Invalid use of Converter
 */
@FacesConverter("me.ronygomes.teamcanvas.TextEllipsisConverter")
public class TextEllipsisConverter implements Converter<String> {

    private static final int MAX_SIZE_OF_TEXT = 100;
    private static final int ELLIPSIS_CHAR_COUNT = 4;

    @Override
    public String getAsObject(FacesContext facesContext, UIComponent component, String text) {
        return null;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent component, String largeText) {

        if (Objects.isNull(largeText)) {
            return null;

        }

        largeText = largeText.trim();

        if (largeText.length() <= MAX_SIZE_OF_TEXT) {
            return largeText;
        }

        String smallText = largeText.substring(0, MAX_SIZE_OF_TEXT);
        int lastIndexOfSpace = smallText.lastIndexOf(" ");

        if (lastIndexOfSpace == -1) {
            smallText = largeText.substring(0, MAX_SIZE_OF_TEXT - ELLIPSIS_CHAR_COUNT);
        } else {
            smallText = smallText.substring(0, lastIndexOfSpace);
        }

        smallText += " ...";
        return smallText;
    }
}
