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

	private void doXML() { /* XML문서를 파싱하기위해 SAX파서를 사용했고, 예외처리를 하였습니다. */
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			document = builder.parse(new File("address.xml"));
			
		} catch (ParserConfigurationException pce) {

		} catch (IOException ioe) {

		} catch (SAXException saxe) {

		}

		/* XML문서에서 하위 앨리먼트를 확인하기 위해서 개행할때에 Text객체가 포함되게되는데, 이 객체는 필요가 없으므로 지워줘야한다. #text 노드를 삭제하기위해서 짝수번째 노드를 제거함 */
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
	
	private void saveXML() { // XML 파일에 변경된 것을 저장함. 
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

	private int getUserLength() { // User 노드 갯수를 추출하는 메소드 
		return document.getDocumentElement().getChildNodes().getLength();
	}

	private String getNameByUserIndex(int UserIndex) { // 노드번호로부터 이름 추출하는 메소드
		return document.getDocumentElement().getChildNodes().item(UserIndex)
				.getChildNodes().item(0).getTextContent();
	}

	private String getNumberByUserIndex(int UserIndex) { // 노드번호로부터 전화번호 추출하는 메소드
		return document.getDocumentElement().getChildNodes().item(UserIndex)
				.getChildNodes().item(1).getTextContent();
	}

	private int addName(int nodeIndex) {
		/*
		이름 리스트를 정렬된 상태로 삽입을 해주고, 삽입된 리스트의 index를 반환한다.
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
		/* 전화번호 리스트 정렬 삽입. 삽입된 리스트의 index를 반환한다. */
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

	private void initName() { // 이름 리스트 초기화 정렬
		nameList = new LinkedList<Integer>();
		for (int i = 0; i < getUserLength(); i++) {
			addName(i);
		}
	}

	private void initNumber() { // 전화번호 리스트 초기화 정렬
		numberList = new LinkedList<Integer>();
		for (int i = 0; i < getUserLength(); i++) {
			addNumber(i);
		}
	}

	public int searchEqualNumber(String inputNumber) {
		/*
		번호인덱스를 통한 번호찾기. 일치하는 전화번호의 번호인덱스를 반환한다. 일치하는 경우가 없으면 -1을 반환한다.
		*/
		for (int i = 0; i < numberList.size(); i++) {
			if (getNumberByNumberIndex(i).equals(inputNumber)) {
				return i;
			}
		}

		return -1;
	}

	public int getSizeOfNameList() { // nameList 갯수 추출
		return nameList.size();
	}

	public int getSizeOfNumberList() { // numberList 갯수 추출
		return numberList.size();
	}

	public int addUser(String inputName, String inputNumber) {
		/*
		이름과 전화번호를 받아 새 user 노드를 만든다. 노드를 생성한 후에는 XML 파일에 저장한 뒤 이름과 전화번호 리스트에
		삽입 정렬하고, 이름 리스트의 index를 반환한다. 중복되는 번호가 있으면 -1을 반환한다.
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

	public void removeUserByNameIndex(int nameIndex) { // nameIndex를 통한 User삭제
		int nodeIndex = nameList.get(nameIndex);

		document.getDocumentElement().removeChild(
				document.getDocumentElement().getChildNodes().item(nodeIndex));
		saveXML();

		initName();
		initNumber();
	}

	public void removeUserByNumberIndex(int numberIndex) { // numberIndex를 통한
															// User삭제
		int nodeIndex = numberList.get(numberIndex);

		document.getDocumentElement().removeChild(
				document.getDocumentElement().getChildNodes().item(nodeIndex));
		saveXML();

		initName();
		initNumber();
	}

	public int modifyNameByNameIndex(int nameIndex, String inputName) {
		/* nameIndex를 통한 이름 수정. 새 nameIndex를 반환한다. */
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
		nameIndex를 통한 전화번호 수정. 중복되는 번호가 있으면 -1을 반환하고, 정상적으로 등록되었으면 nameIndex를
		반환한다.
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
		/* numberIndex를 통한 이름 수정. nameIndex를 반환한다. */
		int nodeIndex = numberList.get(numberIndex);

		document.getDocumentElement().getChildNodes().item(nodeIndex)
				.getChildNodes().item(0).setTextContent(inputName);
		saveXML();

		initName();

		return numberIndex;
	}

	public int modifyNumberByNumberIndex(int numberIndex, String inputNumber) {
		/*
		numberIndex를 통한 전화번호 수정. 중복되는 번호가 있으면 -1을 반환하고, 정상적으로 등록되었으면 새
		numberIndex를 반환한다.
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

	public String getNameByNameIndex(int nameIndex) { // 이름인덱스를 통한 이름을 추출하는 메소드
		return getNameByUserIndex(nameList.get(nameIndex));
	}

	public String getNumberByNameIndex(int nameIndex) { // 이름인덱스를 통한 번호를 추출하는 메소드
		return getNumberByUserIndex(nameList.get(nameIndex));
	}

	public String getNameByNumberIndex(int numberIndex) { // 번호인덱스를 통한 이름을 추출하는 메소드
		return getNameByUserIndex(numberList.get(numberIndex));
	}

	public String getNumberByNumberIndex(int numberIndex) { // 번호인덱스를 통한 번호를 추출하는 메소드
		return getNumberByUserIndex(numberList.get(numberIndex));
	}

	public int[] searchName(String inputPattern) {
		/*
		이름인덱스를 통한 이름패턴 찾기. 입력한 이름패턴을 가지는 목록을 배열로 반환한다.
		*/

		/* 패턴이 일치하는 유저 숫자 계산하여 배열 선언 */
		int users = 0;
		for (int i = 0; i < nameList.size(); i++) {
			if (getNameByNameIndex(nameList.get(i)).contains(inputPattern) == true) {
				users++;
			}
		}
		int[] result = new int[users];

		/* 패턴이 일치하는 유저를 배열에 등록 */
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
		전화번호인덱스를 통한 전화번호패턴 찾기. 입력한 전화번호패턴을 가지는 목록을 배열로 반환한다.
		*/

		/* 패턴이 일치하는 유저 숫자 계산하여 배열 선언 */
		int users = 0;
		for (int i = 0; i < numberList.size(); i++) {
			if (getNumberByNumberIndex(numberList.get(i))
					.contains(inputPattern) == true) {
				users++;
			}
		}
		int[] result = new int[users];

		/* 패턴이 일치하는 유저를 배열에 등록 */
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
