package com.gregortorrence.kagemusha.translators;

import java.io.File;
import java.io.IOException;

public interface Translator {

    void translate(File inputFile, File outputFile) throws IOException;

}
