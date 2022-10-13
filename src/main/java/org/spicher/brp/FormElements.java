package org.spicher.brp;

import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;

public class FormElements {

    public static TextField getTextField(String label) {
        TextField tField = new TextField();
        tField.setLabel(label);
        tField.setRequiredIndicatorVisible(true);
        return tField;
    }

    public static IntegerField getIntegerField(String label) {
        IntegerField iField = new IntegerField();
        iField.setLabel(label);
        iField.setRequiredIndicatorVisible(true);
        return iField;
    }
}
