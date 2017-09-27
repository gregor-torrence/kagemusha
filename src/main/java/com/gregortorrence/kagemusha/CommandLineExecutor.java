package com.gregortorrence.kagemusha;

import com.google.common.collect.ImmutableMap;
import com.google.common.io.Files;
import com.gregortorrence.kagemusha.translators.JsonTranslator;
import com.gregortorrence.kagemusha.translators.Translator;
import com.gregortorrence.kagemusha.translators.PropertiesTranslator;
import com.gregortorrence.kagemusha.translators.TextTranslator;
import com.gregortorrence.kagemusha.translators.XliffTranslator;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Parses supplied arguments and executes the correct Translator
 */
public class CommandLineExecutor {

    private static final int SUCCESSFUL_TRANSLATION = 0;
    private static final int ERROR_WRONG_NUMBER_OF_ARGUMENTS = 1;
    private static final int ERROR_UNSUPPORTED_FILE_TYPE = 2;
    private static final int ERROR_EXCEPTION_DURING_TRANSLATION = -1;

    // This defines all supported file types
    private static Map<String, Translator> translators = ImmutableMap.of(
            "txt",        new TextTranslator(),
            "json",       new JsonTranslator(),
            "xlf",        new XliffTranslator(),
            "properties", new PropertiesTranslator()
    );

    /**
     * Executes the Translation, and returns the right exit code.
     */
    public int execute(List<String> args) {
        try {
            if (args.size() != 2) {
                System.err.println("USAGE: java -jar kagemusha.jar inputFile outputFile");
                return ERROR_WRONG_NUMBER_OF_ARGUMENTS;
            }
            String inputFilename  = args.get(0);
            String outputFilename = args.get(1);

            String fileExtension = Files.getFileExtension(inputFilename.toLowerCase());
            Translator translator = translators.get(fileExtension);
            if (translator == null) {
                System.err.printf("\"%s\" is not a supported file type. Supported types are: %s\n",
                        fileExtension,
                        translators.keySet().stream().collect(Collectors.joining(", ")));
                return ERROR_UNSUPPORTED_FILE_TYPE;
            } else {
                translator.translate(new File(inputFilename), new File(outputFilename));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ERROR_EXCEPTION_DURING_TRANSLATION;
        }

        return SUCCESSFUL_TRANSLATION;
    }

}
