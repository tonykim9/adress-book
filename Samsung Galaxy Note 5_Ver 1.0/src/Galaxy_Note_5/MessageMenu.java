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
			System.out.println("----- 메시지 보기 <" + nowPage + "/" + numPages
					+ "> " + message.getMessageLength() + "개 메시지 -----");
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
					SNR = "↑";
				} else if (message.getMessagetypeByNodeIndex(nodeIndex).equals(
						"received") == true) {
					SNR = "↓";
				} else {
					SNR = "？";
				}
				if (content.indexOf(10) == -1) {
					if (content.length() > subLength) {
						subContent = content.substring(0, subLength - 1) + "…";
					} else {
						subContent = content;
					}
				} else {
					content = content.substring(0, content.indexOf(10));
					if (content.length() > subLength) {
						subContent = content.substring(0, subLength - 1) + "…";
					} else {
						subContent = content + "…";
					}
				}

				System.out.print(i + SNR + " ");
				System.out.println(numberOrName + " / "
						+ message.getTimeByNodeIndex(nodeIndex) + "\n    "
						+ subContent);
			}
			System.out.print("< 6. 이전 / 7. 다음 / 8. 종료 >\n" + "선택 > ");

			scanNumber = scanner.next();
			if(scanNumber.length()<2){
				switch (scanNumber.charAt(0)) {
				case '1':
				case '2':
				case '3':
				case '4':
				case '5':
					if ((int)scanNumber.charAt(0)-48 > messageOfNowPage) {
						System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
					} else {
						viewMessage(message.getMessageLength() - (nowPage - 1) * 5
								- ((int)scanNumber.charAt(0)-48));
					}
					break;
				case '6':
					if (nowPage == 1) {
						System.out.println("현재 페이지가 첫 페이지입니다. 다시 선택해 주십시오.");
					} else {
						nowPage--;
					}
					break;
				case '7':
					if (nowPage == numPages) {
						System.out.println("현재 페이지가 마지막 페이지입니다. 다시 선택해 주십시오.");
					} else {
						nowPage++;
					}
					break;
				case '8':
					nowPage = 0;
					break;
				default:
					System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
					break;
				}
			}
			else
				System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
		}
	}

	private void viewMessage(int nodeIndex) {
		int numberIndex = address.searchEqualNumber(message
				.getNumberByNodeIndex(nodeIndex));
		String numberOrName;
		String sentOrReceived;
		if (numberIndex == -1) {
			numberOrName = "미등록";
		} else {
			numberOrName = address.getNameByNumberIndex(numberIndex);
		}
		if (message.getMessagetypeByNodeIndex(nodeIndex).equals("sent") == true) {
			sentOrReceived = "에게 보낸 문자";
		} else if (message.getMessagetypeByNodeIndex(nodeIndex).equals(
				"received") == true) {
			sentOrReceived = "에게 받은 문자";
		} else {
			sentOrReceived = "";
		}
		System.out.print(numberOrName + sentOrReceived + "\n전화번호 : "
				+ message.getNumberByNodeIndex(nodeIndex) + "\n"
				+ message.getTimeByNodeIndex(nodeIndex) + "\n"
				+ message.getContentByNodeIndex(nodeIndex)
				+ "\n < 1. 이전 / 2. 다음 / 3. 상위 >\n" + "선택 > ");
		scanNumber = scanner.next();
		if(scanNumber.length()<2){
			switch (scanNumber.charAt(0)) {
			case '1':
				if (nodeIndex == 0) {
					System.out.println("처음 메시지입니다.");
					viewMessage(nodeIndex);
				} else {
					viewMessage(nodeIndex - 1);
				}
				break;
			case '2':
				if (nodeIndex == message.getMessageLength() - 1) {
					System.out.println("마지막 메시지입니다.");
					viewMessage(nodeIndex);
				} else {
					viewMessage(nodeIndex + 1);
				}
				break;
			case '3':
				break;
			default:
				System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
				viewMessage(nodeIndex);
				break;
			}
		}
		else{
			System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
			viewMessage(nodeIndex);
		}
	}
}

