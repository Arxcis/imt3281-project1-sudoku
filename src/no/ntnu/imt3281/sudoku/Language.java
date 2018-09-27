package no.ntnu.imt3281.sudoku;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Manage Language strings. Access from anywhere in Sudoku package.
 */
public class Language {
    /*
     * Path to language bundle
     */
    private static final String bundlePath = "no.ntnu.imt3281.sudoku.LanguageBundle";
    
    /*
     * Resource bundle differs based on default locale
     */
    private static ResourceBundle bundle;

    public static void init() {
        Language.bundle = ResourceBundle.getBundle(Language.bundlePath);
    }
    
    /**
     * @return bundle containing all language strings
     */
    public static ResourceBundle getBundle() {
        return Language.bundle;
    }
    
}
