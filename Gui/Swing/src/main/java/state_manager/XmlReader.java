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
            return null;
        }

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory
                .newInstance();
        DocumentBuilder documentBuilder = null;
        Document document = null;
        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
            document = documentBuilder.parse(input);
            document.normalize();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
        e.printStackTrace();
        } catch (IOException e) {
        e.printStackTrace();
        }

        return document;
    }
}
