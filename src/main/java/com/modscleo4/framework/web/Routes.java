package com.modscleo4.framework.web;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.servlet.ServletContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;

public class Routes {
    public static RouteCollection getRoutes(ServletContext application) throws ParserConfigurationException, IOException, SAXException {
        RouteCollection routes = new RouteCollection();

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        DocumentBuilder docBuilder = dbf.newDocumentBuilder();
        InputStream xmlFile = application.getResourceAsStream("/WEB-INF/routes.xml");
        Document document = docBuilder.parse(xmlFile);

        Element root = document.getDocumentElement();
        if (root.getTagName().equals("routes")) {
            for (int i = 0; i < root.getChildNodes().getLength(); i++) {
                if (root.getChildNodes().item(i).getNodeType() == Node.ELEMENT_NODE) {
                    Element child = (Element) root.getChildNodes().item(i);
                    String httpMethod = child.getTagName();
                    String pattern = child.getAttribute("pattern");
                    String className = child.getAttribute("call").split("@")[0];
                    String method = child.getAttribute("call").split("@")[1];
                    String name = child.getAttribute("name");

                    Route r = new Route(httpMethod, pattern, className, method, name);
                    routes.add(r);
                }
            }
        }

        return routes;
    }
}
