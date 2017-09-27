package com.gregortorrence.kagemusha.translators

import spock.lang.Specification

class TextTranslatorSpec extends Specification {

    def 'should translate a TXT file'() {
        given:
        File inputFile = new File(getClass().getClassLoader().getResource('hamlet.txt').getFile())
        File outputFile = File.createTempFile('unitTest', '.txt')
        def translator = new TextTranslator()

        when:
        translator.translate(inputFile, outputFile)
        String translation = outputFile.text

        then:
        translation.contains('Ｔｏ　ｂｅ、　ｏｒ　ｎｏｔ　ｔｏ　ｂｅーーｔｈａｔ　ｉｓ　ｔｈｅ　ｑｕｅｓｔｉｏｎ')
        translation.contains('Ｔｏ　ｇｒｕｎｔ　ａｎｄ　ｓｗｅａｔ　ｕｎｄｅｒ　ａ　ｗｅａｒｙ　ｌｉｆｅ')
        translation.contains('Ｂｅ　ａｌｌ　ｍｙ　ｓｉｎｓ　ｒｅｍｅｍｂｅｒｅｄ。')

        cleanup:
        outputFile.delete()
    }

}
