package com.gregortorrence.kagemusha.translators;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

/**
 * Translates the entire supplied .TXT file.
 */
public class TextTranslator implements Translator {

    @Override
    public String getFileExtension() {
        return "txt";
    }

    @Override
    public void translate(File inputFile, File outputFile) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile)));
                PrintWriter writer = new PrintWriter(outputFile))
        {
            String line;
            while ((line = reader.readLine()) != null) {
                writer.println(Kagemusha.translate(line));
            }
        }
    }

}
