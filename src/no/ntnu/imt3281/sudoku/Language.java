package no.ntnu.imt3281.sudoku;

import java.util.ResourceBundle;

/**
 * Manage Language strings. Access from anywhere in Sudoku package.
 */
public class Language {
    
    /**
     * Load and create language bundle
     */
    public static void init() {
        Language.bundle = ResourceBundle.getBundle(Language.bundlePath);
    }

    /**
     * @return bundle containing all language strings
     */
    public static ResourceBundle getBundle() {
        return Language.bundle;
    }
        
    static final String bundlePath = "no.ntnu.imt3281.sudoku.LanguageBundle";
    static ResourceBundle bundle;
    
    Language() {}
}
