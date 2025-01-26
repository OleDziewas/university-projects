package xmlfun;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;

@XmlEnum(String.class)
public enum Position {

    @XmlEnumValue("CEO") CEO,
    @XmlEnumValue("CIO") CIO,
    @XmlEnumValue("CFO") CFO,
    @XmlEnumValue("ARCHIVE") ARCHIVE;

}
