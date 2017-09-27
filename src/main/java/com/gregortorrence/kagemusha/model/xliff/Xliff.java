package com.gregortorrence.kagemusha.model.xliff;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Root Object for de-serializing XLF files.
 */
@Data
@Accessors(chain = true)
@JacksonXmlRootElement(localName = "xliff")
public class Xliff {

    @JacksonXmlProperty(isAttribute = true)
    private String version;

    @JacksonXmlProperty(isAttribute = true)
    private String creator;

    @JacksonXmlProperty(isAttribute = true, localName = "xmlns:t3")
    private String xmlnsT3;

    @JacksonXmlProperty(isAttribute = true)
    private String schemaLocation;

    private XliffFile file;

}
