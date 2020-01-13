package saving;

import crawling.Response;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.util.NavigableSet;

public class SaveToXml{

    /**
     * trasform method is from built in class Document is to transform the response of the url to .xml
     *
     * @param response
     * @return
     * @throws ParserConfigurationException
     */
    public Document transform(Response response) throws ParserConfigurationException {

        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

        // root elements
        Document doc = docBuilder.newDocument();
        Element root = doc.createElement("response");

        root.appendChild(
                buildXmlUrlList(doc, response.pageLinks, "pageLinks")
        );

        root.appendChild(
                buildXmlUrlList(doc, response.externalLinks, "externalLinks")
        );

        doc.appendChild(root);
        return doc;
    }

    /**
     * buildXmlUrlList is a method from built in class Element is to make a list of all the url that will be shown
     *
     * @param doc
     * @param list
     * @param name
     * @return
     */
    private Element buildXmlUrlList(Document doc, NavigableSet<String> list, String name) {

        Element el = doc.createElement(name);
        for (String link : list) {
            el.appendChild(buildUrlXmlItem(doc, link));
        }
        return el;
    }

    /**
     * buildXmlUrlItem is a method from built in class Element is to make a list of all the items that will be shown
     *
     * @param doc
     * @param link
     * @return
     */
    private Element buildUrlXmlItem(Document doc, String link) {
        Element url = doc.createElement("url");
        url.setTextContent(link);
        return url;
    }
}


