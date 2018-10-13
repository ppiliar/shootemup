package state_manager;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

public class XmlReader {

    private static final Logger LOGGER = Logger.getLogger( XmlReader.class.getName() );

    public static Document readFile(String filename)  {
        InputStream input = XmlReader.class.getClassLoader().getResourceAsStream(filename);
        //InputStream input = XmlReader.class.getResourceAsStream(filename);
        if (input == null) {
            LOGGER.severe("Unable to find "+ filename);
            //System.out.println("Sorry, unable to find " + filename);
        }

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory
                .newInstance();
        DocumentBuilder documentBuilder = null;
        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        Document document = null;
        try {
            document = documentBuilder.parse(input);
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        document.normalize();
        return document;
    }
}
