package Galaxy_Note_5;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public class AddressData {
	private Document document;
	private LinkedList<Integer> nameList;
	private LinkedList<Integer> numberList;

	AddressData() {
		doXML();
	}

	private void doXML() { /* XML������ �Ľ��ϱ����� SAX�ļ��� ����߰�, ����ó���� �Ͽ����ϴ�. */
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			document = builder.parse(new File("address.xml"));
			
		} catch (ParserConfigurationException pce) {

		} catch (IOException ioe) {

		} catch (SAXException saxe) {

		}

		/* XML�������� ���� �ٸ���Ʈ�� Ȯ���ϱ� ���ؼ� �����Ҷ��� Text��ü�� ���ԵǰԵǴµ�, �� ��ü�� �ʿ䰡 �����Ƿ� ��������Ѵ�. #text ��带 �����ϱ����ؼ� ¦����° ��带 ������ */
		for (int i = getUserLength() - 1; i >= 0; i -= 2) {
			document.getDocumentElement().removeChild(
					document.getDocumentElement().getChildNodes().item(i));
		}
		for (int i = 0; i < getUserLength(); i++) {
			for (int j = 4; j >= 0; j -= 2) {
				document.getDocumentElement().getChildNodes().item(i).removeChild(
								document.getDocumentElement().getChildNodes()
										.item(i).getChildNodes().item(j));
			}
		}

		initName();
		initNumber();
	}
	
	private void saveXML() { // XML ���Ͽ� ����� ���� ������. 
		try {
			TransformerFactory transformerFactory = TransformerFactory
					.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.ENCODING, "euc-kr");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			DOMSource source = new DOMSource(document);
			StreamResult result = new StreamResult(new FileOutputStream(
					new File("address.xml")));
			transformer.transform(source, result);
		} catch (TransformerConfigurationException tce) {

		} catch (FileNotFoundException fnfe) {

		} catch (TransformerException te) {

		}
	}

	private int getUserLength() { // User ��� ������ �����ϴ� �޼ҵ� 
		return document.getDocumentElement().getChildNodes().getLength();
	}

	private String getNameByUserIndex(int UserIndex) { // ����ȣ�κ��� �̸� �����ϴ� �޼ҵ�
		return document.getDocumentElement().getChildNodes().item(UserIndex)
				.getChildNodes().item(0).getTextContent();
	}

	private String getNumberByUserIndex(int UserIndex) { // ����ȣ�κ��� ��ȭ��ȣ �����ϴ� �޼ҵ�
		return document.getDocumentElement().getChildNodes().item(UserIndex)
				.getChildNodes().item(1).getTextContent();
	}

	private int addName(int nodeIndex) {
		/*
		�̸� ����Ʈ�� ���ĵ� ���·� ������ ���ְ�, ���Ե� ����Ʈ�� index�� ��ȯ�Ѵ�.
		*/
		for (int i = 0; i < nameList.size(); i++) {
			if (getNameByUserIndex(nodeIndex).compareTo(
					getNameByUserIndex(nameList.get(i))) < 0) {
				nameList.add(i, nodeIndex);
				return i;
			}
		}
		nameList.add(nodeIndex);
		return nameList.size() - 1;
	}

	private int addNumber(int nodeIndex) {
		/* ��ȭ��ȣ ����Ʈ ���� ����. ���Ե� ����Ʈ�� index�� ��ȯ�Ѵ�. */
		for (int i = 0; i < numberList.size(); i++) {
			if (getNumberByUserIndex(nodeIndex).compareTo(
					getNumberByUserIndex(numberList.get(i))) < 0) {
				numberList.add(i, nodeIndex);
				return i;
			}
		}
		numberList.add(nodeIndex);
		return numberList.size() - 1;
	}

	private void initName() { // �̸� ����Ʈ �ʱ�ȭ ����
		nameList = new LinkedList<Integer>();
		for (int i = 0; i < getUserLength(); i++) {
			addName(i);
		}
	}

	private void initNumber() { // ��ȭ��ȣ ����Ʈ �ʱ�ȭ ����
		numberList = new LinkedList<Integer>();
		for (int i = 0; i < getUserLength(); i++) {
			addNumber(i);
		}
	}

	public int searchEqualNumber(String inputNumber) {
		/*
		��ȣ�ε����� ���� ��ȣã��. ��ġ�ϴ� ��ȭ��ȣ�� ��ȣ�ε����� ��ȯ�Ѵ�. ��ġ�ϴ� ��찡 ������ -1�� ��ȯ�Ѵ�.
		*/
		for (int i = 0; i < numberList.size(); i++) {
			if (getNumberByNumberIndex(i).equals(inputNumber)) {
				return i;
			}
		}

		return -1;
	}

	public int getSizeOfNameList() { // nameList ���� ����
		return nameList.size();
	}

	public int getSizeOfNumberList() { // numberList ���� ����
		return numberList.size();
	}

	public int addUser(String inputName, String inputNumber) {
		/*
		�̸��� ��ȭ��ȣ�� �޾� �� user ��带 �����. ��带 ������ �Ŀ��� XML ���Ͽ� ������ �� �̸��� ��ȭ��ȣ ����Ʈ��
		���� �����ϰ�, �̸� ����Ʈ�� index�� ��ȯ�Ѵ�. �ߺ��Ǵ� ��ȣ�� ������ -1�� ��ȯ�Ѵ�.
		*/

		if (searchEqualNumber(inputNumber) != -1) {
			return -1;
		}

		Element newUser = document.createElement("user");
		Element newName = document.createElement("name");
		Element newNumber = document.createElement("number");

		document.getDocumentElement().appendChild(newUser);
		newUser.appendChild(newName);
		newName.appendChild(document.createTextNode(inputName));
		newUser.appendChild(newNumber);
		newNumber.appendChild(document.createTextNode(inputNumber));

		saveXML();

		int nameListIndex;

		nameListIndex = addName(getUserLength() - 1);
		addNumber(getUserLength() - 1);

		return nameListIndex;
	}

	public void removeUserByNameIndex(int nameIndex) { // nameIndex�� ���� User����
		int nodeIndex = nameList.get(nameIndex);

		document.getDocumentElement().removeChild(
				document.getDocumentElement().getChildNodes().item(nodeIndex));
		saveXML();

		initName();
		initNumber();
	}

	public void removeUserByNumberIndex(int numberIndex) { // numberIndex�� ����
															// User����
		int nodeIndex = numberList.get(numberIndex);

		document.getDocumentElement().removeChild(
				document.getDocumentElement().getChildNodes().item(nodeIndex));
		saveXML();

		initName();
		initNumber();
	}

	public int modifyNameByNameIndex(int nameIndex, String inputName) {
		/* nameIndex�� ���� �̸� ����. �� nameIndex�� ��ȯ�Ѵ�. */
		int nodeIndex = nameList.get(nameIndex);

		document.getDocumentElement().getChildNodes().item(nodeIndex)
				.getChildNodes().item(0).setTextContent(inputName);
		saveXML();

		initName();

		for (int i = 0; i < nameList.size(); i++) {
			if (nameList.get(i) == nodeIndex) {
				return i;
			}
		}

		return -1;
	}

	public int modifyNumberByNameIndex(int nameIndex, String inputNumber) {
		/*
		nameIndex�� ���� ��ȭ��ȣ ����. �ߺ��Ǵ� ��ȣ�� ������ -1�� ��ȯ�ϰ�, ���������� ��ϵǾ����� nameIndex��
		��ȯ�Ѵ�.
		*/

		if (searchEqualNumber(inputNumber) != -1) {
			return -1;
		}

		int nodeIndex = nameList.get(nameIndex);

		document.getDocumentElement().getChildNodes().item(nodeIndex)
				.getChildNodes().item(1).setTextContent(inputNumber);
		saveXML();

		initNumber();

		return nameIndex;
	}

	public int modifyNameByNumberIndex(int numberIndex, String inputName) {
		/* numberIndex�� ���� �̸� ����. nameIndex�� ��ȯ�Ѵ�. */
		int nodeIndex = numberList.get(numberIndex);

		document.getDocumentElement().getChildNodes().item(nodeIndex)
				.getChildNodes().item(0).setTextContent(inputName);
		saveXML();

		initName();

		return numberIndex;
	}

	public int modifyNumberByNumberIndex(int numberIndex, String inputNumber) {
		/*
		numberIndex�� ���� ��ȭ��ȣ ����. �ߺ��Ǵ� ��ȣ�� ������ -1�� ��ȯ�ϰ�, ���������� ��ϵǾ����� ��
		numberIndex�� ��ȯ�Ѵ�.
		*/

		if (searchEqualNumber(inputNumber) != -1) {
			return -1;
		}

		int nodeIndex = numberList.get(numberIndex);

		document.getDocumentElement().getChildNodes().item(nodeIndex)
				.getChildNodes().item(1).setTextContent(inputNumber);
		saveXML();

		initNumber();

		for (int i = 0; i < numberList.size(); i++) {
			if (numberList.get(i) == nodeIndex) {
				return i;
			}
		}

		return -1;
	}

	public String getNameByNameIndex(int nameIndex) { // �̸��ε����� ���� �̸��� �����ϴ� �޼ҵ�
		return getNameByUserIndex(nameList.get(nameIndex));
	}

	public String getNumberByNameIndex(int nameIndex) { // �̸��ε����� ���� ��ȣ�� �����ϴ� �޼ҵ�
		return getNumberByUserIndex(nameList.get(nameIndex));
	}

	public String getNameByNumberIndex(int numberIndex) { // ��ȣ�ε����� ���� �̸��� �����ϴ� �޼ҵ�
		return getNameByUserIndex(numberList.get(numberIndex));
	}

	public String getNumberByNumberIndex(int numberIndex) { // ��ȣ�ε����� ���� ��ȣ�� �����ϴ� �޼ҵ�
		return getNumberByUserIndex(numberList.get(numberIndex));
	}

	public int[] searchName(String inputPattern) {
		/*
		�̸��ε����� ���� �̸����� ã��. �Է��� �̸������� ������ ����� �迭�� ��ȯ�Ѵ�.
		*/

		/* ������ ��ġ�ϴ� ���� ���� ����Ͽ� �迭 ���� */
		int users = 0;
		for (int i = 0; i < nameList.size(); i++) {
			if (getNameByNameIndex(nameList.get(i)).contains(inputPattern) == true) {
				users++;
			}
		}
		int[] result = new int[users];

		/* ������ ��ġ�ϴ� ������ �迭�� ��� */
		users = 0;
		for (int i = 0; i < nameList.size(); i++) {
			if (getNameByNameIndex(nameList.get(i)).contains(inputPattern) == true) {
				result[users++] = nameList.get(i);
			}
		}

		return result;
	}

	public int[] searchNumber(String inputPattern) {
		/*
		��ȭ��ȣ�ε����� ���� ��ȭ��ȣ���� ã��. �Է��� ��ȭ��ȣ������ ������ ����� �迭�� ��ȯ�Ѵ�.
		*/

		/* ������ ��ġ�ϴ� ���� ���� ����Ͽ� �迭 ���� */
		int users = 0;
		for (int i = 0; i < numberList.size(); i++) {
			if (getNumberByNumberIndex(numberList.get(i))
					.contains(inputPattern) == true) {
				users++;
			}
		}
		int[] result = new int[users];

		/* ������ ��ġ�ϴ� ������ �迭�� ��� */
		users = 0;
		for (int i = 0; i < numberList.size(); i++) {
			if (getNumberByNumberIndex(numberList.get(i))
					.contains(inputPattern) == true) {
				result[users++] = numberList.get(i);
			}
		}

		return result;
	}
}
