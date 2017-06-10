package Galaxy_Note_5;

import java.util.*;

public class AddressMenu {
	AddressData address = new AddressData();
	Scanner scanner = new Scanner(System.in);
	String scanNumber;

	AddressMenu() {
		mainMenu();
	}

	private void mainMenu() {
		boolean isQuit = false;
		while (isQuit == false) {
			System.out.print("----- MY ADDRESS BOOK -----\n" + "1. �ּҷ� ����\n"
					+ "2. ����ó �˻�\n" + "3. ����ó �߰�\n" + "4. ����\n" + "���� > ");
			scanNumber=scanner.next();
			if(scanNumber.length()<2){
				switch (scanNumber.charAt(0)) {
				case '1':
					viewAddress();
					break;
				case '2':
					searchAddress();
					break;
				case '3':
					addAddress();
					break;
				case '4':
					isQuit = true;
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

	private void viewAddress() {
		boolean isNameSort = true;
		int nowPage = 1;
		int numPages;
		int userOfNowPage;
		String scanNumber;
		while (nowPage != 0) {
			if (isNameSort == true) {
				numPages = (int) Math.ceil((((double) address
						.getSizeOfNameList()) / 5));
				userOfNowPage = address.getSizeOfNameList()
						- ((nowPage - 1) * 5);
			} else {
				numPages = (int) Math.ceil((((double) address
						.getSizeOfNumberList()) / 5));
				userOfNowPage = address.getSizeOfNumberList()
						- ((nowPage - 1) * 5);
			}
			if (userOfNowPage > 5) {
				userOfNowPage = 5;
			}
			System.out.println("----- �ּҷ� ���� <" + nowPage + "/" + numPages
					+ "> " + address.getSizeOfNameList() + "�� ����ó -----");
			for (int i = 1; i <= userOfNowPage; i++) {
				System.out.print(i + ". ");
				if (isNameSort == true) {
					System.out.println(address.getNameByNameIndex((nowPage - 1)
							* 5 + i - 1)
							+ " "
							+ address.getNumberByNameIndex((nowPage - 1) * 5
									+ i - 1));
				} else {
					System.out.println(address
							.getNumberByNumberIndex((nowPage - 1) * 5 + i - 1)
							+ " "
							+ address.getNameByNumberIndex((nowPage - 1) * 5
									+ i - 1));
				}
			}
			System.out.print("< 6. ���� / 7. ���� / 8. ���Ĺ�� ���� / 9. ���� >\n"
					+ "���� > ");

			scanNumber = scanner.next();
			if(scanNumber.length()<2){
				switch (scanNumber.charAt(0)) {
				case '1':
				case '2':
				case '3':
				case '4':
				case '5':
					if ((int)scanNumber.charAt(0)-48 > userOfNowPage) {
						System.out.println("�߸��� �Է��Դϴ�. �ٽ� �Է����ּ���.");
					} else {
						nowPage = (int) Math.ceil((double) (viewUser(isNameSort,
								(nowPage - 1) * 5 + ((int)scanNumber.charAt(0)-48) - 1) + 1) / 5);
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
					if (isNameSort == true)
						isNameSort = false;
					else
						isNameSort = true;
					break;
				case '9':
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

	public int viewUser(boolean isNameList, int listIndex) {
		boolean isQuit = false;
		while (isQuit == false) {
			System.out.println("----- ����ó ���� -----");
			if (isNameList == true) {
				System.out.println("�̸�: "
						+ address.getNameByNameIndex(listIndex) + " / ��ȭ��ȣ: "
						+ address.getNumberByNameIndex(listIndex));
			} else {
				System.out.println("�̸�: "
						+ address.getNameByNumberIndex(listIndex) + " / ��ȭ��ȣ: "
						+ address.getNumberByNumberIndex(listIndex));
			}
			System.out.print("< 1. �̸� ���� / 2. ��ȭ��ȣ ���� / 3. ����ó ���� / 4. ���� >\n"
					+ "���� > ");
			if (isNameList) {
				scanNumber=scanner.next();
				if(scanNumber.length()<2){
					switch (scanNumber.charAt(0)) {
					case '1':
						System.out.println("������ �̸��� �Է��Ͻʽÿ�.");
						String Name = scanner.next();
						listIndex = address.modifyNameByNameIndex(listIndex, Name);
						break;
					case '2':
						System.out.println("������ ��ȣ�� �Է��Ͻʽÿ�.");
						String Number = scanner.next();
						int isModify = address.modifyNumberByNameIndex(listIndex,
								Number);
						if (isModify == -1) {
							System.out.println("�̹� ��ϵ� ��ȣ�Դϴ�.");
						}
						break;
					case '3':
						address.removeUserByNameIndex(listIndex);
						System.out.println("������ ���� �Ǿ����ϴ�.");
						break;
					case '4':
						isQuit = true;
						break;
					default:
						System.out.println("1���� 4������ ��ȣ�� �Է��Ͻʽÿ�.");
						break;
					}
				}
				else
					System.out.println("1���� 4������ ��ȣ�� �Է��Ͻʽÿ�.");
			}

			else{
				scanNumber=scanner.next();
				if(scanNumber.length()<2){
					switch (scanNumber.charAt(0)) {
					case '1':
						System.out.println("������ �̸��� �Է��Ͻʽÿ�.");
						String Name = scanner.next();
						listIndex = address.modifyNameByNameIndex(listIndex, Name);
						break;
					case '2':
						System.out.println("������ ��ȣ�� �Է��Ͻʽÿ�.");
						String Number = scanner.next();
						int isModify = address.modifyNumberByNameIndex(listIndex,
								Number);
						if (isModify == -1) {
							System.out.println("�̹� ��ϵ� ��ȣ�Դϴ�.");
						}
						break;
					case '3':
						address.removeUserByNameIndex(listIndex);
						System.out.println("������ ���� �Ǿ����ϴ�.");
						break;
					case '4':
						isQuit = true;
						break;
					default:
						System.out.println("1���� 4������ ��ȣ�� �Է��Ͻʽÿ�.");
						break;
					}
				}
				else
					System.out.println("1���� 4������ ��ȣ�� �Է��Ͻʽÿ�.");
			}
		}

		return listIndex;
	}

	private void addAddress() {// ����ó �߰�
		System.out.println("----- ����ó �߰� -----");
		System.out.print("�̸��� �Է��ϼ��� > ");
		String inputName = scanner.next();
		System.out.print("��ȭ��ȣ�� �Է��ϼ��� > ");
		String inputNumber = scanner.next();
		int isPlus = address.addUser(inputName, inputNumber);
		if (isPlus == -1) {
			System.out.println("�̹� ��ϵ� ��ȣ�Դϴ�.");
		} else {
			System.out.println("����ó�� ��ϵǾ����ϴ�.");
		}
	}

	private void searchAddress() {
		boolean isQuit = false;
		int[] result;
		while (isQuit == false) {
			System.out.print("----- ����ó �˻� -----\n"
					+ "< 1. �̸����� �˻� / 2. ��ȭ��ȣ�� �˻� / 3. ���� >\n" + "���� > ");
			scanNumber = scanner.next();
			if(scanNumber.length()<2){
				switch (scanNumber.charAt(0)) {
				case '1':
					System.out.print("�˻��� �̸� �Է� > ");
					result = address.searchName(scanner.next());
					if (result.length == 0) {
						System.out.println("�˻� ����� �����ϴ�.");
					} else {
						System.out.println(result.length + "���� ����ó�� �˻��Ǿ����ϴ�.");
						searchList(true, result);
					}
					break;
				case '2':
					System.out.print("�˻��� ��ȭ��ȣ �Է� > ");
					result = address.searchNumber(scanner.next());
					if (result.length == 0) {
						System.out.println("�˻� ����� �����ϴ�.");
					} else {
						System.out.println(result.length + "���� ����ó�� �˻��Ǿ����ϴ�.");
						(false, result);
					}
					break;
				case '3':
					isQuit = true;
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

	private void searchList(boolean isNameSearch, int[] result) {
		int nowPage = 1;
		int numPages;
		int userOfNowPage;

		numPages = (int) Math.ceil((((double) result.length) / 5));

		while (nowPage != 0) {
			userOfNowPage = result.length - ((nowPage - 1) * 5);
			if (userOfNowPage > 5) {
				userOfNowPage = 5;
			}

			System.out.println("----- �˻��� �ּҷ� ���� <" + nowPage + "/" + numPages + "> " + result.length + "�� ����ó -----");
			for (int i = 1; i <= userOfNowPage; i++) {
				System.out.print(i + ". ");
				if (isNameSearch == true) {
					System.out.println(address
							.getNameByNameIndex(result[(nowPage - 1) * 5 + i
									- 1]));
				} else {
					System.out.println(address
							.getNameByNumberIndex(result[(nowPage - 1) * 5 + i
									- 1]));
				}
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
					if ((int)scanNumber.charAt(0)-48 > userOfNowPage) {
						System.out.println("�߸��� �Է��Դϴ�. �ٽ� �Է����ּ���.");
					} else {
						viewUser(isNameSearch, result[(nowPage - 1) * 5
								+ ((int)scanNumber.charAt(0)-48) - 1]);
						nowPage = 0;
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
}