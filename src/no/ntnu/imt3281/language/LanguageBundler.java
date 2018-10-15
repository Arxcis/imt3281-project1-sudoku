package no.ntnu.imt3281.language;

import java.util.ResourceBundle;

/**
 * Manage Language strings. Access from anywhere in Sudoku package.
 */
public class LanguageBundler {
    /**
     * Load and create language bundle
     */
    public static void init() {

        LanguageBundler.bundle = ResourceBundle.getBundle("no.ntnu.imt3281.language.Bundle");
    }

    /**
     * @return bundle containing all language strings
     */
    public static ResourceBundle getBundle() {
        return LanguageBundler.bundle;
    }

    static final String bundlePath = "no.ntnu.imt3281.sudoku.LanguageBundle";
    static ResourceBundle bundle;
    
    Language() {}
}
