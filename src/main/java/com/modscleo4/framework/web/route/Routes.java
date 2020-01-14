/*
 * Copyright 2019 Dhiego Cassiano Fogaça Barbosa
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.modscleo4.framework.web.route;

import com.modscleo4.framework.web.Main;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.servlet.ServletContext;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class Routes {
    private static RouteCollection processGroup(String prefix, String name, Element group) {
        RouteCollection routes = new RouteCollection();
        if (!prefix.endsWith("/")) {
            prefix += "/";
        }

        for (int i = 0; i < group.getChildNodes().getLength(); i++) {
            if (group.getChildNodes().item(i).getNodeType() == Node.ELEMENT_NODE) {
                Element child = (Element) group.getChildNodes().item(i);

                if (child.getTagName().equals("group")) {
                    routes.addAll(processGroup(prefix + child.getAttribute("prefix"),name + child.getAttribute("name"), child));
                } else {
                    String httpMethod = child.getTagName();
                    String pattern = prefix + child.getAttribute("pattern");
                    if (pattern.endsWith("/") && !pattern.equals("/")) {
                        pattern = pattern.substring(0, pattern.length() - 1);
                    }

                    String className = child.getAttribute("call").split("@")[0];
                    String method = child.getAttribute("call").split("@")[1];

                    String routeName = null;
                    if (child.hasAttribute("name")) {
                        routeName = name + child.getAttribute("name");
                    }

                    Route r = new Route(httpMethod, pattern, className, method, routeName);
                    routes.add(r);
                }
            }
        }

        return routes;
    }

    public static RouteCollection getRoutes() throws ParserConfigurationException, IOException, SAXException {
        ServletContext application = Main.application;
        RouteCollection routes = new RouteCollection();

        URL schemaFile = application.getResource("/WEB-INF/routes.xsd");

        String constant = XMLConstants.W3C_XML_SCHEMA_NS_URI;
        SchemaFactory xsdFactory = SchemaFactory.newInstance(constant);
        Schema schema = xsdFactory.newSchema(schemaFile);

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setSchema(schema);
        dbf.setNamespaceAware(true);
        DocumentBuilder docBuilder = dbf.newDocumentBuilder();
        InputStream xmlFile = application.getResourceAsStream("/WEB-INF/routes.xml");
        Document document = docBuilder.parse(xmlFile);

        Element root = document.getDocumentElement();
        if (root.getTagName().equals("routes")) {
            routes.addAll(processGroup("", "", root));
        }

        return routes;
    }
}
