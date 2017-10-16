package com.gregortorrence.kagemusha.translators

import spock.lang.Specification

class PropertiesTranslatorSpec extends Specification {

    def 'should have correct file extension'() {
        expect:
        new PropertiesTranslator().getFileExtension() == 'properties'
    }

    def 'should translate properties file'() {
        given:
        File inputFile = new File(getClass().getClassLoader().getResource('LocalizedStrings.properties').getFile())
        File outputFile = File.createTempFile('unitTest', '.properties')
        def translator = new PropertiesTranslator()

        when:
        translator.translate(inputFile, outputFile)
        def properties = new Properties()
        properties.load(new FileInputStream(outputFile))

        then:
        properties.get('contextMenuItemTagIgnoreGrammar') == 'Ｉｇｎｏｒｅ　Ｇｒａｍｍａｒ'
        properties.get('contextMenuItemTagBold') == 'Ｂｏｌｄ'
        properties.get('AXWebAreaText') == 'ＨＴＭＬ　ｃｏｎｔｅｎｔ'

        cleanup:
        outputFile.delete()
    }

}
