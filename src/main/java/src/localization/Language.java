package src.localization;

import java.util.Locale;
import java.util.ResourceBundle;

public class Language {

    public static final ResourceBundle sv = ResourceBundle.getBundle("src.localization.GUILabels", new Locale("sv", "SV"));

    public static final ResourceBundle ru = ResourceBundle.getBundle("src.localization.GUILabels", new Locale("ru", "RU"));

    public static final ResourceBundle et = ResourceBundle.getBundle("src.localization.GUILabels", new Locale("et", "ET"));

    public static final ResourceBundle es = ResourceBundle.getBundle("src.localization.GUILabels", new Locale("es", "ES"));

}
