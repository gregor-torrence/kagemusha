package com.gregortorrence.kagemusha.model.xliff;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class XliffBody {

    @JacksonXmlProperty(localName = "trans-unit")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<XliffTransUnit> transUnits;

}
