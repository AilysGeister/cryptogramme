package main.controller;

import java.io.IOException;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Class that allow multi-language.
 */
public class PropertiesReader {

    private static final String BUNDLE_NAME = "main.localization.strings";

    /**
     * Read the key when the view load, and show the corresponding string.
     * @param key
     * @return
     */
	public static String getString(String key) {
        String language;

        //We get the language from the settings controller:
        try {
            language = Settings.getStrParameter("lang");
        } catch (IOException e) {
            language = "en"; //If the controller can't read the language it set english by default.
        }

        //Once we got the language we search the key in the corresponding file:
        try {
            @SuppressWarnings("deprecation")
			ResourceBundle resourceBundle = ResourceBundle.getBundle(BUNDLE_NAME, new Locale(language));
            return resourceBundle.getString(key);
        } catch (MissingResourceException e) {
            return '!' + key + '!';
        }
    }
}
