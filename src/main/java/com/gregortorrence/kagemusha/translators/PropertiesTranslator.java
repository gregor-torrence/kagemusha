package com.gregortorrence.kagemusha.translators;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.Properties;

/**
 * Translates the values in the supplied properties file. Leaves keys unchanged.
 */
public class PropertiesTranslator implements Translator {

    @Override
    public String getFileExtension() {
        return "properties";
    }

    @Override
    public void translate(File inputFile, File outputFile) throws IOException {
        Properties properties = new Properties();
        properties.load(new InputStreamReader(new FileInputStream(inputFile)));

        Collections.list(properties.keys()).stream()
                .map(key -> (String) key)
                .forEach(key ->
                        properties.setProperty(key, Kagemusha.translate(properties.getProperty(key)))
                );

        try (FileOutputStream outputStream = new FileOutputStream(outputFile)) {
            properties.store(outputStream, "Kagemusha translation");
        }
    }

}
