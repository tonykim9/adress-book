package Galaxy_Note_5;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class CallData {
	private Document document;

	CallData() {
		doXML();
	}

	private void doXML() { /* XML문서를 파싱하기위해 SAX파서를 사용했고, 예외처리를 하였습니다. */
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			document = builder.parse(new File("call.xml"));
		} catch (ParserConfigurationException pce) {

		} catch (IOException ioe) {

		} catch (SAXException saxe) {

		}

		/* XML문서에서 하위 앨리먼트를 확인하기 위해서 개행할때에 Text객체가 포함되게되는데, 이 객체는 필요가 없으므로 지워줘야한다. #text 노드를 삭제하기위해서 짝수번째 노드를 제거함 */
		for (int i = getCallLength() - 1; i >= 0; i -= 2) {
			document.getDocumentElement().removeChild(
					document.getDocumentElement().getChildNodes().item(i));
		}
		for (int i = 0; i < getCallLength(); i++) {
			for (int j = 6; j >= 0; j -= 2) {
				document.getDocumentElement()
						.getChildNodes()
						.item(i)
						.removeChild(
								document.getDocumentElement().getChildNodes()
										.item(i).getChildNodes().item(j));
			}
		}
	}

	public int getCallLength() { // call 노드 갯수 추출
		return document.getDocumentElement().getChildNodes().getLength();
	}

	public String getCalltypeByNodeIndex(int nodeIndex) {
		return document.getDocumentElement().getChildNodes().item(nodeIndex)
				.getChildNodes().item(0).getTextContent();
	}

	public String getNumberByNodeIndex(int nodeIndex) {
		return document.getDocumentElement().getChildNodes().item(nodeIndex)
				.getChildNodes().item(1).getTextContent();
	}

	public String getTimeByNodeIndex(int nodeIndex) {
		return document.getDocumentElement().getChildNodes().item(nodeIndex)
				.getChildNodes().item(2).getTextContent();
	}

	public void state() {
		for (int i = getCallLength() - 1; i >= 0; i--) {
		}
	}
}
