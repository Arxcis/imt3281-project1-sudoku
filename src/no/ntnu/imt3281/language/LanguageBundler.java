package no.ntnu.imt3281.language;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Manage Language strings. Access from anywhere in Sudoku package.
 */
public class LanguageBundler {
    /*
     * Resource bundle differs based on default locale
     */
    private static ResourceBundle bundle;

    public static void init() {

        LanguageBundler.bundle = ResourceBundle.getBundle("no.ntnu.imt3281.language.Bundle");
    }

    /**
     * @return bundle containing all language strings
     */
    public static ResourceBundle getBundle() {
        return LanguageBundler.bundle;
    }

}
