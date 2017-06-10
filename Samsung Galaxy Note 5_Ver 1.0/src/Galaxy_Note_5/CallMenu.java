package Galaxy_Note_5;

import java.util.*;

public class CallMenu {
	CallData call = new CallData();
	AddressData address = new AddressData();
	Scanner scanner = new Scanner(System.in);
	String scanNumber;

	public CallMenu() {
		callList();
	}

	private void callList() {
		int nowPage = 1;
		int numPages = (int) Math
				.ceil((((double) call.getCallLength()) / 5));
		int callOfNowPage;
		while (nowPage != 0) {
			if (nowPage == numPages) {
				callOfNowPage = call.getCallLength() % 5;
				if(callOfNowPage == 0){
					callOfNowPage = 5;
				}
			} else {
				callOfNowPage = 5;
			}
			System.out.println("----- 통화기록 보기 <" + nowPage + "/" + numPages
					+ "> " + call.getCallLength() + "개 기록 -----");
			for (int i = 1; i <= callOfNowPage; i++) {
				int nodeIndex = call.getCallLength() - (nowPage - 1) * 5
						- i;
				int numberIndex = address.searchEqualNumber(call
						.getNumberByNodeIndex(nodeIndex));
				String numberOrName;
				if (numberIndex == -1) {
					numberOrName = call.getNumberByNodeIndex(nodeIndex);
				} else {
					numberOrName = address.getNameByNumberIndex(numberIndex);
				}
				String SNR;
				if (call.getCalltypeByNodeIndex(nodeIndex).equals("sent") == true) {
					SNR = " 발신";
				} else if (call.getCalltypeByNodeIndex(nodeIndex).equals(
						"received") == true) {
					SNR = " 수신";
				} else if (call.getCalltypeByNodeIndex(nodeIndex).equals(
						"absent") == true) {
					SNR = " 부재";
				} else if (call.getCalltypeByNodeIndex(nodeIndex).equals(
						"blocked") == true) {
					SNR = " 차단";
				} else {
					SNR = "？";
				}

				System.out.print(i + SNR + " ");
				System.out.println(numberOrName + " / "
						+ call.getTimeByNodeIndex(nodeIndex));
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
					if(scanNumber.length()<2){
						if ((int)scanNumber.charAt(0)- 48 > callOfNowPage) {
							System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
						} else {
							viewCall(call.getCallLength() - (nowPage - 1) * 5
									- ((int)scanNumber.charAt(0)- 48));
						}
					}
					else{
						if ((int)scanNumber.charAt(0)- 48 > callOfNowPage||scanNumber.charAt(1)!=0) {
							System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
						} else {
							viewCall(call.getCallLength() - (nowPage - 1) * 5
									- ((int)scanNumber.charAt(0)- 48));
						}
					}
					break;
				case '6':
					if (nowPage == 1) {
						System.out.println("첫 페이지입니다. 다시 선택해 주십시오.");
					} else {
						nowPage--;
					}
					break;
				case '7':
					if (nowPage == numPages) {
						System.out.println("마지막 페이지입니다. 다시 선택해 주십시오.");
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

	private void viewCall(int nodeIndex) {
		int numberIndex = address.searchEqualNumber(call
				.getNumberByNodeIndex(nodeIndex));
		String numberOrName;
		String sentOrReceived;
		if (numberIndex == -1) {
			numberOrName = "미등록";
		} else {
			numberOrName = address.getNameByNumberIndex(numberIndex);
		}
		if (call.getCalltypeByNodeIndex(nodeIndex).equals("sent") == true) {
			sentOrReceived = "에게 보낸 통화";
		} else if (call.getCalltypeByNodeIndex(nodeIndex).equals(
				"received") == true) {
			sentOrReceived = "에게 받은 통화";
		} else if (call.getCalltypeByNodeIndex(nodeIndex).equals(
				"absent") == true) {
			sentOrReceived = "에게서 온 부재중 통화";
		} else if (call.getCalltypeByNodeIndex(nodeIndex).equals(
				"blocked") == true) {
			sentOrReceived = "에게 받은 수신 거절된 통화";
		} else {
			sentOrReceived = "";
		}
		System.out.print(numberOrName + sentOrReceived + "\n전화번호 : "
				+ call.getNumberByNodeIndex(nodeIndex) + "\n"
				+ call.getTimeByNodeIndex(nodeIndex) + "\n"
				+ "< 1. 이전 / 2. 다음 / 3. 상위 >\n" + "선택 > ");
		scanNumber = scanner.next();
		if(scanNumber.length()<2){
			switch (scanNumber.charAt(0)) {
			case '1':
				if (nodeIndex == 0) {
					System.out.println("처음 통화기록입니다.");
					viewCall(nodeIndex);
				} else {
					viewCall(nodeIndex - 1);
				}
				break;
			case '2':
				if (nodeIndex == call.getCallLength() - 1) {
					System.out.println("마지막 통화기록입니다.");
					viewCall(nodeIndex);
				} else {
					viewCall(nodeIndex + 1);
				}
				break;
			case '3':
				break;
			default:
				System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
				viewCall(nodeIndex);
				break;
			}
		}
		else{
			System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
			viewCall(nodeIndex);
		}
	}
}
