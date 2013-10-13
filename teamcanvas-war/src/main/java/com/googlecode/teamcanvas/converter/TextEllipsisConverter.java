package com.googlecode.teamcanvas.converter;


import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("com.googlecode.teamcanvas.TextEllipsisConverter")
public class TextEllipsisConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String text) {
        return null;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent component, Object o) {
        int MAX_SIZE_OF_TEXT = 100;
        String largeText = o.toString();

        if(largeText.length() < MAX_SIZE_OF_TEXT){
            return largeText;
        }

        String smallText = largeText.substring(0, MAX_SIZE_OF_TEXT);
        int lastIndexOfSpace = smallText.lastIndexOf(" ");
        smallText = smallText.substring(0, lastIndexOfSpace);

        smallText += " ...";
        return smallText;

    }
}
