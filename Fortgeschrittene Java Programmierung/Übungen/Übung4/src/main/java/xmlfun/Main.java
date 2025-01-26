package xmlfun;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class Main {

    public static void main(String[] args) {
        try {
            JAXBContext context = JAXBContext.newInstance(Person.class);

            Unmarshaller um = context.createUnmarshaller();
            Person larry = (Person) um.unmarshal(new File("page.xml"));
            larry.name = "Larry Ellison";
            larry.firma.name = "Oracle";
            larry.firma.boersennotiert = true;
            larry.position = Position.CEO;
            Marshaller ms = context.createMarshaller();
            ms.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            ms.marshal(larry, new File("ellison.xml"));
        }catch(Exception e){
            e.printStackTrace();
        }

    }
}
