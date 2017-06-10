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
			System.out.println("----- ��ȭ��� ���� <" + nowPage + "/" + numPages
					+ "> " + call.getCallLength() + "�� ��� -----");
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
					SNR = " �߽�";
				} else if (call.getCalltypeByNodeIndex(nodeIndex).equals(
						"received") == true) {
					SNR = " ����";
				} else if (call.getCalltypeByNodeIndex(nodeIndex).equals(
						"absent") == true) {
					SNR = " ����";
				} else if (call.getCalltypeByNodeIndex(nodeIndex).equals(
						"blocked") == true) {
					SNR = " ����";
				} else {
					SNR = "��";
				}

				System.out.print(i + SNR + " ");
				System.out.println(numberOrName + " / "
						+ call.getTimeByNodeIndex(nodeIndex));
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
					if(scanNumber.length()<2){
						if ((int)scanNumber.charAt(0)- 48 > callOfNowPage) {
							System.out.println("�߸��� �Է��Դϴ�. �ٽ� �Է����ּ���.");
						} else {
							viewCall(call.getCallLength() - (nowPage - 1) * 5
									- ((int)scanNumber.charAt(0)- 48));
						}
					}
					else{
						if ((int)scanNumber.charAt(0)- 48 > callOfNowPage||scanNumber.charAt(1)!=0) {
							System.out.println("�߸��� �Է��Դϴ�. �ٽ� �Է����ּ���.");
						} else {
							viewCall(call.getCallLength() - (nowPage - 1) * 5
									- ((int)scanNumber.charAt(0)- 48));
						}
					}
					break;
				case '6':
					if (nowPage == 1) {
						System.out.println("ù �������Դϴ�. �ٽ� ������ �ֽʽÿ�.");
					} else {
						nowPage--;
					}
					break;
				case '7':
					if (nowPage == numPages) {
						System.out.println("������ �������Դϴ�. �ٽ� ������ �ֽʽÿ�.");
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

	private void viewCall(int nodeIndex) {
		int numberIndex = address.searchEqualNumber(call
				.getNumberByNodeIndex(nodeIndex));
		String numberOrName;
		String sentOrReceived;
		if (numberIndex == -1) {
			numberOrName = "�̵��";
		} else {
			numberOrName = address.getNameByNumberIndex(numberIndex);
		}
		if (call.getCalltypeByNodeIndex(nodeIndex).equals("sent") == true) {
			sentOrReceived = "���� ���� ��ȭ";
		} else if (call.getCalltypeByNodeIndex(nodeIndex).equals(
				"received") == true) {
			sentOrReceived = "���� ���� ��ȭ";
		} else if (call.getCalltypeByNodeIndex(nodeIndex).equals(
				"absent") == true) {
			sentOrReceived = "���Լ� �� ������ ��ȭ";
		} else if (call.getCalltypeByNodeIndex(nodeIndex).equals(
				"blocked") == true) {
			sentOrReceived = "���� ���� ���� ������ ��ȭ";
		} else {
			sentOrReceived = "";
		}
		System.out.print(numberOrName + sentOrReceived + "\n��ȭ��ȣ : "
				+ call.getNumberByNodeIndex(nodeIndex) + "\n"
				+ call.getTimeByNodeIndex(nodeIndex) + "\n"
				+ "< 1. ���� / 2. ���� / 3. ���� >\n" + "���� > ");
		scanNumber = scanner.next();
		if(scanNumber.length()<2){
			switch (scanNumber.charAt(0)) {
			case '1':
				if (nodeIndex == 0) {
					System.out.println("ó�� ��ȭ����Դϴ�.");
					viewCall(nodeIndex);
				} else {
					viewCall(nodeIndex - 1);
				}
				break;
			case '2':
				if (nodeIndex == call.getCallLength() - 1) {
					System.out.println("������ ��ȭ����Դϴ�.");
					viewCall(nodeIndex);
				} else {
					viewCall(nodeIndex + 1);
				}
				break;
			case '3':
				break;
			default:
				System.out.println("�߸��� �Է��Դϴ�. �ٽ� �Է����ּ���.");
				viewCall(nodeIndex);
				break;
			}
		}
		else{
			System.out.println("�߸��� �Է��Դϴ�. �ٽ� �Է����ּ���.");
			viewCall(nodeIndex);
		}
	}
}
