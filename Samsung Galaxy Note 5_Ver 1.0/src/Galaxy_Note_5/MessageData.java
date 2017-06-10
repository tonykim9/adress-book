package Galaxy_Note_5;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class MessageData {
	private Document document;

	MessageData() {
		doXML();
	}

	private void doXML() { /* XML������ �Ľ��ϱ����� SAX�ļ��� ����߰�, ����ó���� �Ͽ����ϴ�. */
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			document = builder.parse(new File("message.xml"));
		} catch (ParserConfigurationException pce) {

		} catch (IOException ioe) {

		} catch (SAXException saxe) {

		}

		/* XML�������� ���� �ٸ���Ʈ�� Ȯ���ϱ� ���ؼ� �����Ҷ��� Text��ü�� ���ԵǰԵǴµ�, �� ��ü�� �ʿ䰡 �����Ƿ� ��������Ѵ�. #text ��带 �����ϱ����ؼ� ¦����° ��带 ������ */
		for (int i = getMessageLength() - 1; i >= 0; i -= 2) {
			document.getDocumentElement().removeChild(document.getDocumentElement().getChildNodes().item(i));
		}
		for (int i = 0; i < getMessageLength(); i++) {
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

	public int getMessageLength() { // Message ��� ���� ����
		return document.getDocumentElement().getChildNodes().getLength();
	}

	public String getMessagetypeByNodeIndex(int nodeIndex) {
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

	public String getContentByNodeIndex(int nodeIndex) {
		return document.getDocumentElement().getChildNodes().item(nodeIndex)
				.getChildNodes().item(3).getTextContent();
	}

	public void state() {
		for (int i = getMessageLength() - 1; i >= 0; i--) {
		}
	}
}

