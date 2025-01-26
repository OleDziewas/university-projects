package xmlfun;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Person {



    @XmlElement
    String name;
    @XmlElement
    Position position;
    @XmlElement
    Firma firma;

}


