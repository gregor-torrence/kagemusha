package com.gregortorrence.kagemusha.model.xliff;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class XliffHeader {
    @JacksonXmlProperty(isAttribute = true)
    private String note;
}
