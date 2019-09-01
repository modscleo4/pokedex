package com.modscleo4.ui.xml;

import com.modscleo4.ui.Window;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;

public class XMLUI {
    public static Window fromResource(String xml) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        DocumentBuilder docBuilder = dbf.newDocumentBuilder();
        InputStream xmlFile = new Object() {
        }.getClass().getResourceAsStream(String.format("/windows/%s", xml));
        Document document = docBuilder.parse(xmlFile);

        Element root = document.getDocumentElement();
        if (root.getTagName().equals("Window")) {
            Window window = new Window();
            if (root.getAttribute("title") != null) {
                window.setTitle(root.getAttribute("title"));
            }

            int width = 0;
            if (root.getAttribute("width") != null) {
                width = Integer.parseInt(root.getAttribute("width"));
            }

            int height = 0;
            if (root.getAttribute("height") != null) {
                height = Integer.parseInt(root.getAttribute("height"));
            }

            window.setSize(width, height);

            return window;
        }

        return null;
    }
}
