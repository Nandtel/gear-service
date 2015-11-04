package com.gearservice.model.cheque.samples;

/**
 * Enum Models contains samples of models
 *
 * @version 1.0
 * @author Dmitry
 * @since 04.09.2015
 */
public enum Models {
    model1("Asus.T100TA-DK003H"),
    model2("Apple.MacBook Pro MGXA2"),
    model3("Lenovo.IdeaPad Y5070"),
    model4("Dell.Inspiron 3542"),
    model5("Asus.X555LN-XO030D"),
    model6("HP.470 G0 H6R01ES"),
    model7("HP.250 G3 K9L19ES"),
    model8("Acer.Aspire E1-570G-33214G75MNII"),
    model9("HP.17-e152sr F7S67EA");

    private final String model;
    Models(String s) {model = s;}
    public boolean equalsName(String otherModel) {return (otherModel != null) && model.equals(otherModel);}
    public String toString() {return this.model;}
}
