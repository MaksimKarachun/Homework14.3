import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.text.ParseException;

public class StaxParser {

    public void parseFile(String filePath) throws FileNotFoundException, XMLStreamException, ParseException, SQLException {
        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        XMLEventReader reader = xmlInputFactory.createXMLEventReader(new FileInputStream(filePath));

        while (reader.hasNext()) {
            XMLEvent nextEvent = reader.nextEvent();
            if (nextEvent.isStartElement()) {
                StartElement startElement = nextEvent.asStartElement();

                if (startElement.getName().getLocalPart().equals("voter")) {
                        DBConnection.countVoter(startElement.getAttributeByName(new QName("name")).getValue(),
                                startElement.getAttributeByName(new QName("birthDay")).getValue());
                }
            }
            if (nextEvent.isEndElement()) {
                EndElement endElement = nextEvent.asEndElement();
                if (endElement.getName().getLocalPart().equals("voters")) {
                    System.out.println("end file");
                }
            }

        }
        DBConnection.executeMultiInsert();
    }

}
