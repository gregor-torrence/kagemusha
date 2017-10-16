package com.gregortorrence.kagemusha.model.xliff

import com.fasterxml.jackson.dataformat.xml.XmlMapper
import spock.lang.Specification

class XliffSpec extends Specification {

    def 'should de-serialize an XLF file'() {
        given:
        File file = new File(getClass().getClassLoader().getResource('de.messages.xlf').getFile())

        when:
        def xliff = new XmlMapper().readValue(file, Xliff)

        then:
        xliff != null
        xliff.getVersion() == '1.0'
        xliff.getFile().getHeader().getNote() == 'note'
        xliff.getFile().getSourceLanguage() == 'en'
        xliff.getFile().getTargetLanguage() == 'de'
        xliff.getFile().getDataType() == 'plaintext'
        xliff.getFile().getBody().getTransUnits().size() == 2
        xliff.getFile().getBody().getTransUnits().get(0).getId() == 'headerComment'
        xliff.getFile().getBody().getTransUnits().get(0).getSpace() == 'preserve'
        xliff.getFile().getBody().getTransUnits().get(0).getSource().getValue() == 'The default Header Comment.'
        xliff.getFile().getBody().getTransUnits().get(0).getTarget().getValue() == 'Der Standard-Header-Kommentar.'
        xliff.getFile().getBody().getTransUnits().get(1).getId() == 'generator'
        xliff.getFile().getBody().getTransUnits().get(1).getSpace() == 'preserve'
        xliff.getFile().getBody().getTransUnits().get(1).getSource().getValue() == 'The "Generator" Meta Tag.'
        xliff.getFile().getBody().getTransUnits().get(1).getTarget().getValue() == 'Der "Generator"-Meta-Tag.'
    }

}
