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

package com.modscleo4.ui.xml;

import com.modscleo4.ui.Dialog;
import com.modscleo4.ui.Window;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class XMLUI {
    private static Component componentFromXML(Element xml) {
        String componentName = xml.getTagName();

        try {
            Component component = (Component) Class.forName(componentName).getDeclaredConstructor().newInstance();

            if (xml.getAttribute("id") != null) {
                String id = xml.getAttribute("id");
                component.setName(id);
            }

            int x = -1;
            if (xml.getAttribute("x") != null) {
                x = Integer.parseInt(xml.getAttribute("x"));
            }

            int y = -1;
            if (xml.getAttribute("y") != null) {
                y = Integer.parseInt(xml.getAttribute("y"));
            }

            if (x != -1 && y != -1) {
                component.setLocation(x, y);
            }

            int width = -1;
            if (xml.getAttribute("width") != null) {
                width = Integer.parseInt(xml.getAttribute("width"));
            }

            int height = -1;
            if (xml.getAttribute("height") != null) {
                height = Integer.parseInt(xml.getAttribute("height"));
            }

            if (width != -1 && height != -1) {
                component.setSize(width, height);
            }

            if (xml.getAttribute("background") != null) {
                String background = xml.getAttribute("background");
                component.setBackground(Color.decode(background));
            }

            if (xml.getAttribute("foreground") != null) {
                String foreground = xml.getAttribute("foreground");
                component.setForeground(Color.decode(foreground));
            }

            if (xml.getAttribute("enabled") != null) {
                component.setEnabled(Boolean.parseBoolean(xml.getAttribute("enabled")));
            }

            if (xml.getAttribute("focusable") != null) {
                component.setFocusable(Boolean.parseBoolean(xml.getAttribute("focusable")));
            }

            if (xml.getAttribute("visible") != null) {
                component.setVisible(Boolean.parseBoolean(xml.getAttribute("visible")));
            }

            if (xml.getAttribute("fontFamily") != null) {
                String name = xml.getAttribute("fontFamily");
                component.setFont(new Font(name, component.getFont().getStyle(), component.getFont().getSize()));
            }

            if (xml.getAttribute("fontStyle") != null) {
                String[] s = xml.getAttribute("fontStyle").split("\\|");
                int style = Font.PLAIN;
                for (String st : s) {
                    st = st.toUpperCase();

                    switch (st) {
                        case "PLAIN":
                            style = style | Font.PLAIN;
                            break;
                        case "BOLD":
                            style = style | Font.BOLD;
                            break;
                        case "ITALIC":
                            style = style | Font.ITALIC;
                            break;
                    }
                }

                component.setFont(new Font(component.getFont().getName(), style, component.getFont().getSize()));
            }

            if (xml.getAttribute("fontSize") != null) {
                int size = Integer.parseInt(xml.getAttribute("fontSize"));
                component.setFont(new Font(component.getFont().getName(), component.getFont().getStyle(), size));
            }

            if (component instanceof Container) {
                component = setupContainer(component, xml);
            }

            switch (componentName) {
                case "JPanel":
                    component = setupPanel(component, xml);
                    break;
                case "Window":
                    component = setupWindow(component, xml);
                    break;
                case "Dialog":
                    component = setupDialog(component, xml);
                    break;
            }

            return component;
        } catch (Exception e) {
            return null;
        }
    }

    private static LayoutManager layoutFromXML(Element xml) {
        String layoutName = xml.getTagName();

        try {
            LayoutManager layout = (LayoutManager) Class.forName(layoutName).getDeclaredConstructor().newInstance();
            if (layout instanceof GridBagLayout) {
                layout = setupGridLayout(xml);
            } else if (layout instanceof BorderLayout) {
                layout = setupBorderLayout(xml);
            }

            return layout;
        } catch (Exception e) {
            return null;
        }
    }

    private static GridBagConstraints getConstraints(Element xml) {
        GridBagConstraints constraints = new GridBagConstraints();

        if (xml.getAttribute("gridx") != null) {
            constraints.gridx = Integer.parseInt(xml.getAttribute("gridx"));
        }

        if (xml.getAttribute("gridy") != null) {
            constraints.gridy = Integer.parseInt(xml.getAttribute("gridy"));
        }

        if (xml.getAttribute("gridheight") != null) {
            constraints.gridheight = Integer.parseInt(xml.getAttribute("gridheight"));
        }

        if (xml.getAttribute("gridwidth") != null) {
            constraints.gridwidth = Integer.parseInt(xml.getAttribute("gridwidth"));
        }

        if (xml.getAttribute("weightx") != null) {
            constraints.weightx = Double.parseDouble(xml.getAttribute("weightx"));
        }

        if (xml.getAttribute("weighty") != null) {
            constraints.weighty = Double.parseDouble(xml.getAttribute("weighty"));
        }

        if (xml.getAttribute("ipadx") != null) {
            constraints.ipadx = Integer.parseInt(xml.getAttribute("ipadx"));
        }

        if (xml.getAttribute("ipady") != null) {
            constraints.ipady = Integer.parseInt(xml.getAttribute("ipady"));
        }

        if (xml.getAttribute("fill") != null) {
            constraints.fill = Integer.parseInt(xml.getAttribute("fill"));
        }

        if (xml.getAttribute("anchor") != null) {
            constraints.anchor = Integer.parseInt(xml.getAttribute("anchor"));
        }

        if (xml.getAttribute("insets") != null) {
            String[] vals = xml.getAttribute("insets").split(",");
            int top = Integer.parseInt(vals[0]);
            int left = Integer.parseInt(vals[1]);
            int bottom = Integer.parseInt(vals[2]);
            int right = Integer.parseInt(vals[3]);

            constraints.insets = new Insets(top, left, bottom, right);
        }

        return constraints;
    }

    private static GridLayout setupGridLayout(Element xml) {
        if (xml.getTagName().equals("GridLayout")) {
            GridLayout layout = new GridLayout();

            if (xml.getAttribute("rows") != null) {
                layout.setRows(Integer.parseInt(xml.getAttribute("rows")));
            }

            if (xml.getAttribute("columns") != null) {
                layout.setColumns(Integer.parseInt(xml.getAttribute("columns")));
            }

            if (xml.getAttribute("hgap") != null) {
                layout.setHgap(Integer.parseInt(xml.getAttribute("hgap")));
            }

            if (xml.getAttribute("vgap") != null) {
                layout.setVgap(Integer.parseInt(xml.getAttribute("vgap")));
            }

            return layout;
        }

        return null;
    }

    private static BorderLayout setupBorderLayout(Element xml) {
        if (xml.getTagName().equals("BorderLayout")) {
            BorderLayout layout = new BorderLayout();

            if (xml.getAttribute("hgap") != null) {
                layout.setHgap(Integer.parseInt(xml.getAttribute("hgap")));
            }

            if (xml.getAttribute("vgap") != null) {
                layout.setVgap(Integer.parseInt(xml.getAttribute("vgap")));
            }

            return layout;
        }

        return null;
    }

    private static Container setupContainer(Component rootComponent, Element xml) {
        if (xml.getTagName().equals("JPanel")) {
            Container container = (Container) rootComponent;

            NodeList childs = xml.getChildNodes();
            for (int i = 0, len = childs.getLength(); i < len; i++) {
                if (childs.item(i).getNodeType() == Node.ELEMENT_NODE) {
                    Element child = (Element) childs.item(i);

                    if (container.getLayout() instanceof GridBagLayout) {
                        GridBagConstraints constraints = getConstraints(child);
                        container.add(Objects.requireNonNull(componentFromXML(child)), constraints);
                    } else if (container.getLayout() instanceof BoxLayout) {
                        container.add(Objects.requireNonNull(componentFromXML(child)), xml.getAttribute("alignment"));
                    } else {
                        container.add(Objects.requireNonNull(componentFromXML(child)));
                    }
                }
            }

            return container;
        }

        return null;
    }

    private static JPanel setupPanel(Component rootComponent, Element xml) {
        if (xml.getTagName().equals("JPanel")) {
            JPanel panel = (JPanel) rootComponent;
            assert panel != null;

            return panel;
        }

        return null;
    }

    private static Window setupWindow(Component rootComponent, Element xml) {
        if (xml.getTagName().equals("Window")) {
            Window window = (Window) rootComponent;
            assert window != null;

            if (xml.getAttribute("title") != null) {
                window.setTitle(xml.getAttribute("title"));
            }

            return window;
        }

        return null;
    }

    private static Dialog setupDialog(Component rootComponent, Element xml) {
        if (xml.getTagName().equals("Dialog")) {
            Dialog dialog = (Dialog) rootComponent;
            assert dialog != null;

            if (xml.getAttribute("title") != null) {
                dialog.setTitle(xml.getAttribute("title"));
            }

            return dialog;
        }

        return null;
    }

    public static Window windowFromResource(String xml) throws ParserConfigurationException, IOException, SAXException {
        return windowFromResource(xml, null);
    }

    public static Window windowFromResource(String xml, Window window) throws ParserConfigurationException, IOException, SAXException {
        File schemaFile = new File("/windows/window.xsd");

        String constant = XMLConstants.W3C_XML_SCHEMA_NS_URI;
        SchemaFactory xsdFactory = SchemaFactory.newInstance(constant);
        Schema schema = xsdFactory.newSchema(schemaFile);

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setSchema(schema);
        dbf.setNamespaceAware(true);
        DocumentBuilder docBuilder = dbf.newDocumentBuilder();
        InputStream xmlFile = new Object() {

        }.getClass().getResourceAsStream(String.format("/windows/%s", xml));
        Document document = docBuilder.parse(xmlFile);

        Element root = document.getDocumentElement();
        Component rootComponent = componentFromXML(root);
        if (root.getTagName().equals("Window")) {
            window = (Window) rootComponent;
            return window;
        }

        return null;
    }

    public static Dialog dialogFromResource(String xml) throws ParserConfigurationException, IOException, SAXException {
        return dialogFromResource(xml, null);
    }

    public static Dialog dialogFromResource(String xml, Dialog dialog) throws ParserConfigurationException, IOException, SAXException {
        File schemaFile = new File("/windows/window.xsd");

        String constant = XMLConstants.W3C_XML_SCHEMA_NS_URI;
        SchemaFactory xsdFactory = SchemaFactory.newInstance(constant);
        Schema schema = xsdFactory.newSchema(schemaFile);

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setSchema(schema);
        dbf.setNamespaceAware(true);
        DocumentBuilder docBuilder = dbf.newDocumentBuilder();
        InputStream xmlFile = new Object() {

        }.getClass().getResourceAsStream(String.format("/dialogs/%s", xml));
        Document document = docBuilder.parse(xmlFile);

        Element root = document.getDocumentElement();
        Component rootComponent = componentFromXML(root);
        if (root.getTagName().equals("Dialog")) {
            dialog = (Dialog) rootComponent;
            return dialog;
        }

        return null;
    }
}
