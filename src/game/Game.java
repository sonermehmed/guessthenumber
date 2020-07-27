/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.CyclicBarrier;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Lenovo-ideaPad-15isk
 */
public class Game extends Players {

    /**
     * @param args the command line arguments
     */
    public static int numberOfPlayers, x, a, b = 0;

    public static void main(String[] args) throws FileNotFoundException, IOException, ParserConfigurationException, TransformerConfigurationException, TransformerException, SAXException, InterruptedException {

        File file = new File("src\\game\\data.xml");
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

        Document document = documentBuilder.parse(file);
        document.getDocumentElement().normalize();
        NodeList list = document.getElementsByTagName("Input");

        do {

            for (int i = 0; i < 1; i++) {
                Node node = list.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    x = Integer.parseInt(element.getElementsByTagName("x").item(i).getTextContent());
                    a = Integer.parseInt(element.getElementsByTagName("a").item(i).getTextContent());
                    b = Integer.parseInt(element.getElementsByTagName("b").item(i).getTextContent());
                    numberOfPlayers = Integer.parseInt(element.getElementsByTagName("numberOfPlayers").item(i).getTextContent());
                }
            }

        } while (!((a < x) && (x < b) && (99 < a) && (99 < b) && (1 < numberOfPlayers) && (numberOfPlayers < 11)));

        Players[] allPlayers = new Players[numberOfPlayers];
        CyclicBarrier barrier = new CyclicBarrier(numberOfPlayers);
        list = document.getElementsByTagName("InputPlayers");

        for (int i = 0; i < list.getLength(); i++) {
            Node node = list.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                String name = element.getElementsByTagName("Name").item(0).getTextContent();
                String strategy = element.getElementsByTagName("Strategy").item(0).getTextContent();
                allPlayers[i] = new Players(name, strategy, i, barrier);
                allPlayers[i].start();

            }

        }

    }

    public Game(String name, String strategy, int ID, CyclicBarrier barrier) {
        super(name, strategy, ID, barrier);
    }
}
