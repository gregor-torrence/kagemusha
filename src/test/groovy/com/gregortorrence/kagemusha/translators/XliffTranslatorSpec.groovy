package com.gregortorrence.kagemusha.translators

import spock.lang.Specification

class XliffTranslatorSpec extends Specification {

    def 'should translate furniture XLIFF file'() {
        given:
        File inputFile = new File(getClass().getClassLoader().getResource('messages.xlf').getFile())
        File outputFile = File.createTempFile('unitTest', '.xlf')
        def translator = new XliffTranslator()

        when:
        translator.translate(inputFile, outputFile)
        String translation = outputFile.text

        then:
        translation.contains('<target>Ｔｈｅ　ｄｅｆａｕｌｔ　Ｈｅａｄｅｒ　Ｃｏｍｍｅｎｔ。</target>')
        translation.contains('<target>Ｔｈｅ　゛Ｇｅｎｅｒａｔｏｒ゛　Ｍｅｔａ　Ｔａｇ。</target>')

        cleanup:
        outputFile.delete()
    }

}
