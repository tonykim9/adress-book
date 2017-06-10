package Galaxy_Note_5;

import java.util.*;

public class MessageMenu {
	MessageData message = new MessageData();
	AddressData address = new AddressData();
	Scanner scanner = new Scanner(System.in);
	String scanNumber;

	public MessageMenu() {
		messageList();
	}

	private void messageList() {
		int nowPage = 1;
		int numPages = (int) Math
				.ceil((((double) message.getMessageLength()) / 5));
		int messageOfNowPage;
		while (nowPage != 0) {
			if (nowPage == numPages) {
				messageOfNowPage = message.getMessageLength() % 5;
				if(messageOfNowPage == 0){
					messageOfNowPage = 5;
				}
			} else {
				messageOfNowPage = 5;
			}
			System.out.println("----- �޽��� ���� <" + nowPage + "/" + numPages
					+ "> " + message.getMessageLength() + "�� �޽��� -----");
			for (int i = 1; i <= messageOfNowPage; i++) {
				int nodeIndex = message.getMessageLength() - (nowPage - 1) * 5
						- i;
				int numberIndex = address.searchEqualNumber(message
						.getNumberByNodeIndex(nodeIndex));
				String numberOrName;
				if (numberIndex == -1) {
					numberOrName = message.getNumberByNodeIndex(nodeIndex);
				} else {
					numberOrName = address.getNameByNumberIndex(numberIndex);
				}
				int subLength = 18;
				String content = message.getContentByNodeIndex(nodeIndex);
				String subContent;
				String SNR;
				if (message.getMessagetypeByNodeIndex(nodeIndex).equals("sent") == true) {
					SNR = "��";
				} else if (message.getMessagetypeByNodeIndex(nodeIndex).equals(
						"received") == true) {
					SNR = "��";
				} else {
					SNR = "��";
				}
				if (content.indexOf(10) == -1) {
					if (content.length() > subLength) {
						subContent = content.substring(0, subLength - 1) + "��";
					} else {
						subContent = content;
					}
				} else {
					content = content.substring(0, content.indexOf(10));
					if (content.length() > subLength) {
						subContent = content.substring(0, subLength - 1) + "��";
					} else {
						subContent = content + "��";
					}
				}

				System.out.print(i + SNR + " ");
				System.out.println(numberOrName + " / "
						+ message.getTimeByNodeIndex(nodeIndex) + "\n    "
						+ subContent);
			}
			System.out.print("< 6. ���� / 7. ���� / 8. ���� >\n" + "���� > ");

			scanNumber = scanner.next();
			if(scanNumber.length()<2){
				switch (scanNumber.charAt(0)) {
				case '1':
				case '2':
				case '3':
				case '4':
				case '5':
					if ((int)scanNumber.charAt(0)-48 > messageOfNowPage) {
						System.out.println("�߸��� �Է��Դϴ�. �ٽ� �Է����ּ���.");
					} else {
						viewMessage(message.getMessageLength() - (nowPage - 1) * 5
								- ((int)scanNumber.charAt(0)-48));
					}
					break;
				case '6':
					if (nowPage == 1) {
						System.out.println("���� �������� ù �������Դϴ�. �ٽ� ������ �ֽʽÿ�.");
					} else {
						nowPage--;
					}
					break;
				case '7':
					if (nowPage == numPages) {
						System.out.println("���� �������� ������ �������Դϴ�. �ٽ� ������ �ֽʽÿ�.");
					} else {
						nowPage++;
					}
					break;
				case '8':
					nowPage = 0;
					break;
				default:
					System.out.println("�߸��� �Է��Դϴ�. �ٽ� �Է����ּ���.");
					break;
				}
			}
			else
				System.out.println("�߸��� �Է��Դϴ�. �ٽ� �Է����ּ���.");
		}
	}

	private void viewMessage(int nodeIndex) {
		int numberIndex = address.searchEqualNumber(message
				.getNumberByNodeIndex(nodeIndex));
		String numberOrName;
		String sentOrReceived;
		if (numberIndex == -1) {
			numberOrName = "�̵��";
		} else {
			numberOrName = address.getNameByNumberIndex(numberIndex);
		}
		if (message.getMessagetypeByNodeIndex(nodeIndex).equals("sent") == true) {
			sentOrReceived = "���� ���� ����";
		} else if (message.getMessagetypeByNodeIndex(nodeIndex).equals(
				"received") == true) {
			sentOrReceived = "���� ���� ����";
		} else {
			sentOrReceived = "";
		}
		System.out.print(numberOrName + sentOrReceived + "\n��ȭ��ȣ : "
				+ message.getNumberByNodeIndex(nodeIndex) + "\n"
				+ message.getTimeByNodeIndex(nodeIndex) + "\n"
				+ message.getContentByNodeIndex(nodeIndex)
				+ "\n < 1. ���� / 2. ���� / 3. ���� >\n" + "���� > ");
		scanNumber = scanner.next();
		if(scanNumber.length()<2){
			switch (scanNumber.charAt(0)) {
			case '1':
				if (nodeIndex == 0) {
					System.out.println("ó�� �޽����Դϴ�.");
					viewMessage(nodeIndex);
				} else {
					viewMessage(nodeIndex - 1);
				}
				break;
			case '2':
				if (nodeIndex == message.getMessageLength() - 1) {
					System.out.println("������ �޽����Դϴ�.");
					viewMessage(nodeIndex);
				} else {
					viewMessage(nodeIndex + 1);
				}
				break;
			case '3':
				break;
			default:
				System.out.println("�߸��� �Է��Դϴ�. �ٽ� �Է����ּ���.");
				viewMessage(nodeIndex);
				break;
			}
		}
		else{
			System.out.println("�߸��� �Է��Դϴ�. �ٽ� �Է����ּ���.");
			viewMessage(nodeIndex);
		}
	}
}

