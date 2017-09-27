package com.gregortorrence.kagemusha.model.xliff;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class XliffSource {

    @JacksonXmlText
    private String value;

}
