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

 
public class FavoritesUtility {
 
	private ArrayList<String> stringList;
	
	public FavoritesUtility(){ 
		setStringList(new ArrayList<String>());
	}
	private void writeToFile(String username) throws ParserConfigurationException, TransformerException{
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
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
		attr.setValue(username);
		user.setAttributeNode(attr);
		
		// write the content into xml file
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new File("..\\favorites\\"+username+".xml"));
		 
		// Output to console for testing
		// StreamResult result = new StreamResult(System.out);
		 
		transformer.transform(source, result);
		 
		System.out.println("File saved!");
		
	}
	@SuppressWarnings("unused")
	private void readFromFile(String username) throws ParserConfigurationException, SAXException, IOException{
		File fXmlFile = new File("..\\favorites\\"+username+".xml");
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(fXmlFile);
	 
		doc.getDocumentElement().normalize();
		System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
		 
		NodeList nList = doc.getElementsByTagName("staff");
	 
		System.out.println("----------------------------");
	 
		for (int temp = 0; temp < nList.getLength(); temp++) {
	 
			Node nNode = nList.item(temp);
	 
			System.out.println("\nCurrent Element :" + nNode.getNodeName());
	 
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	 
				Element eElement = (Element) nNode;
				
				addFavoriteUser(eElement.getAttribute("id"));
				System.out.println("User id : " + eElement.getAttribute("id"));
	 
			}
		}
	}
	public void saveFavorites(String username){
		try {
			writeToFile(username);
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
	public void loadFavorites(String username){
		try {
			writeToFile(username);
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void addFavoriteUser(String name){
		stringList.add(name);
	}
	public void removeFavorite(String name){
		stringList.remove(name);
	}
	public ArrayList<String> getStringList() {
		return stringList;
	}
	public void setStringList(ArrayList<String> stringList) {
		this.stringList = stringList;
	}
	public void clearList(){
		stringList.clear();
	}
}