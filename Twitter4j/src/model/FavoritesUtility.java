package model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
/**
 * Class that helps with favorites implementation.
 *
 */
public class FavoritesUtility {
	/**
	 * Arraylist of favorites.
	 */
	private ArrayList<String> stringList;
	/**
	 * Current user name.
	 */
	private String curUser;
	/**
	 * Constructor.
	 */
	public FavoritesUtility() {
		setStringList(new ArrayList<String>());
	}
	/**
	 * Write favorites to file.
	 * @throws ParserConfigurationException 
	 * @throws TransformerException 
	 */
	private void writeToFile() throws ParserConfigurationException,
			TransformerException {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory
				.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

		// root elements
		Document doc = docBuilder.newDocument();
		Element rootElement = doc.createElement("Favorites");
		doc.appendChild(rootElement);

		// user elements
		Element user = doc.createElement("User");
		rootElement.appendChild(user);

		// set attribute to user element
		Attr attr = doc.createAttribute("id");
		attr.setValue(curUser);
		user.setAttributeNode(attr);

		// write the content into xml file
		TransformerFactory transformerFactory = TransformerFactory
				.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new File(""
				+ ".\\Twitter4j\\favorites\\" + curUser + ".xml"));

		transformer.transform(source, result);

		System.out.println("File saved!");

	}
	/**
	 * Read favorites from file.
	 * @param username name of user to get favorites list of.
	 * @throws ParserConfigurationException 
	 * @throws SAXException 
	 * @throws IOException 
	 */
	private void readFromFile(final String username)
			throws ParserConfigurationException, SAXException, IOException {
		File fXmlFile = new File("..\\Twitter4j\\favorites\\" + username
				+ ".xml");
		curUser = username;
		if (fXmlFile.exists()) {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			doc.getDocumentElement().normalize();
			System.out.println("Root element: "
					+ doc.getDocumentElement().getNodeName());

			NodeList nList = doc.getElementsByTagName("staff");

			System.out.println("----------------------------");

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					addFavoriteUser(eElement.getAttribute("id"));

				}
			}
		}
	}
	/**
	 * Saves favorites to file.
	 */
	public final void saveFavorites() {
		try {
			writeToFile();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Loads favorites of user from file.
	 * @param username user to get favorites of
	 */
	public final void loadFavorites(final String username) {
		try {
			readFromFile(username);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Adds favorite user to list.
	 * @param name name of user to add
	 */
	public final void addFavoriteUser(final String name) {
		stringList.add(name);
	}
	/**
	 * Remove favorite user from list.
	 * @param name name of user to remove
	 */
	public final void removeFavorite(final String name) {
		stringList.remove(name);
	}
	/**
	 * List of favorite users.
	 * @return list of favorite users
	 */
	public final ArrayList<String> getStringList() {
		return stringList;
	}
	/**
	 * Sets the list of favorites.
	 * @param strList list of favorite users.
	 */
	public final void setStringList(final ArrayList<String> strList) {
		this.stringList = strList;
	}
	/**
	 * 
	 */
	public final void clearList() {
		stringList.clear();
	}
}