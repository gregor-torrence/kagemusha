package com.gregortorrence.kagemusha.translators;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.gregortorrence.kagemusha.model.xliff.Xliff;
import com.gregortorrence.kagemusha.model.xliff.XliffTarget;

import java.io.File;
import java.io.IOException;

/**
 * Translates the "source" strings into "target" strings in an XLIFF.
 */
public class XliffTranslator implements Translator {

    @Override
    public void translate(File inputFile, File outputFile) throws IOException {
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);

        Xliff xliff = xmlMapper.readValue(inputFile, Xliff.class);

        xliff.getFile().getBody().getTransUnits().forEach(unit -> {
            unit.setTarget(new XliffTarget().setValue(Kagemusha.translate(unit.getSource().getValue())));
        });

        xmlMapper.writeValue(outputFile, xliff);
    }

}
