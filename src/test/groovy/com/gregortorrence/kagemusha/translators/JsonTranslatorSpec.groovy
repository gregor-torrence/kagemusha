package com.gregortorrence.kagemusha.translators

import spock.lang.Specification

class JsonTranslatorSpec extends Specification {

    def 'should have correct file extension'() {
        expect:
        new JsonTranslator().getFileExtension() == 'json'
    }

    def 'should translate simple json file'() {
        given:
        File inputFile = new File(getClass().getClassLoader().getResource('simple.json').getFile())
        File outputFile = File.createTempFile('unitTest', '.json')
        def translator = new JsonTranslator()

        when:
        translator.translate(inputFile, outputFile)
        String translation = outputFile.text

        then:
        translation.contains('"string":')
        translation.contains('"integer":')
        translation.contains('"double":')
        translation.contains('"ｖａｌｕｅ"')
        translation.contains('42')
        translation.contains('3.141592653589793')

        cleanup:
        outputFile.delete()
    }


    def 'should translate json lists file'() {
        given:
        File inputFile = new File(getClass().getClassLoader().getResource('global.json').getFile())
        File outputFile = File.createTempFile('unitTest', '.json')
        def translator = new JsonTranslator()

        when:
        translator.translate(inputFile, outputFile)
        String translation = outputFile.text

        then:
        translation.contains('"title":')
        translation.contains('"search":')
        translation.contains('"copyright":')
        translation.contains('"Ｍｏｖｉｅ　Ｒｅｖｉｅｗｓ"')
        translation.contains('"<a href=\\"http://www.imdb.com/\\">Ｓｅａｒｃｈ　ＩＭＤｂ</a>"')
        translation.contains('"ｃｏｐｙｒｉｇｈｔ　${ year }"')

        cleanup:
        outputFile.delete()
    }

    def 'should translate json maps file'() {
        given:
        File inputFile = new File(getClass().getClassLoader().getResource('movie.json').getFile())
        File outputFile = File.createTempFile('unitTest', '.json')
        def translator = new JsonTranslator()

        when:
        translator.translate(inputFile, outputFile)
        String translation = outputFile.text

        then:
        translation.contains('"movie":')
        translation.contains('"title":')
        translation.contains('"desc":')
        translation.contains('"stars":')
        translation.contains('Ｊｕｒａｓｓｉｃ　Ｐａｒｋ')
        translation.contains('Ｌｏｔｓ　ｏｆ　ｄｉｎｏｓ！')
        translation.contains('${ stars }　ｏｕｔ　ｏｆ　５')

        cleanup:
        outputFile.delete()
    }

}
