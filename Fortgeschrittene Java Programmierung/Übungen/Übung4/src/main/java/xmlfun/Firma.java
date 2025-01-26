package xmlfun;


import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

public class Firma {
    @XmlValue
    String name;
    @XmlAttribute
    boolean boersennotiert;
}
