package com.gregortorrence.kagemusha.model.xliff;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 *    <file source-language="en" target-language="de" datatype="plaintext" original="messages" date="2011-10-18T18:20:51Z" product-name="my-ext">
 */
@Data
@Accessors(chain = true)
public class XliffFile {

    @JacksonXmlProperty(isAttribute = true, localName = "source-language")
    private String sourceLanguage;

    @JacksonXmlProperty(isAttribute = true, localName = "target-language")
    private String targetLanguage;

    @JacksonXmlProperty(isAttribute = true, localName = "datatype")
    private String dataType;

    @JacksonXmlProperty(isAttribute = true, localName = "original")
    private String original;

    @JacksonXmlProperty(isAttribute = true, localName = "date")
    private String date;

    @JacksonXmlProperty(isAttribute = true, localName = "product-name")
    private String productName;

    private XliffHeader header;
    private XliffBody body;

}
