package no.ntnu.imt3281.sudoku;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Manage Language strings. Access from anywhere.
 */
public class Language {
    
    /*
     * ISO Locale language
     */
    private static final String language = "nb"; // @TODO Get from config file
    
    /*
     * ISO Locale Country
     */
    private static final String country = "NO"; // @TODO Get from config file
    
    /*
     * Path to bundle - a bunch of files starting with same name, ending on locale string
     */
    private static final String bundlePath = "no.ntnu.imt3281.sudoku.LanguageBundle"; // @TODO Get from config file
    
    /*
     * Resource bundle differs based on the configured locale
     */
    private static ResourceBundle bundle;

    public static void init() {
        Language.bundle = ResourceBundle.getBundle(Language.bundlePath, new Locale(Language.language, Language.country) );
    }
    
    /**
     * @return bundle containing all language strings
     */
    public static ResourceBundle getBundle() {
        return Language.bundle;
    }
    
}
