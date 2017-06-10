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
			System.out.print("----- MY ADDRESS BOOK -----\n" + "1. 주소록 보기\n"
					+ "2. 연락처 검색\n" + "3. 연락처 추가\n" + "4. 종료\n" + "선택 > ");
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
					System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
					break;
				}
			}
			else
				System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
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
			System.out.println("----- 주소록 보기 <" + nowPage + "/" + numPages
					+ "> " + address.getSizeOfNameList() + "개 연락처 -----");
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
			System.out.print("< 6. 이전 / 7. 다음 / 8. 정렬방식 변경 / 9. 상위 >\n"
					+ "선택 > ");

			scanNumber = scanner.next();
			if(scanNumber.length()<2){
				switch (scanNumber.charAt(0)) {
				case '1':
				case '2':
				case '3':
				case '4':
				case '5':
					if ((int)scanNumber.charAt(0)-48 > userOfNowPage) {
						System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
					} else {
						nowPage = (int) Math.ceil((double) (viewUser(isNameSort,
								(nowPage - 1) * 5 + ((int)scanNumber.charAt(0)-48) - 1) + 1) / 5);
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
					if (isNameSort == true)
						isNameSort = false;
					else
						isNameSort = true;
					break;
				case '9':
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

	public int viewUser(boolean isNameList, int listIndex) {
		boolean isQuit = false;
		while (isQuit == false) {
			System.out.println("----- 연락처 정보 -----");
			if (isNameList == true) {
				System.out.println("이름: "
						+ address.getNameByNameIndex(listIndex) + " / 전화번호: "
						+ address.getNumberByNameIndex(listIndex));
			} else {
				System.out.println("이름: "
						+ address.getNameByNumberIndex(listIndex) + " / 전화번호: "
						+ address.getNumberByNumberIndex(listIndex));
			}
			System.out.print("< 1. 이름 수정 / 2. 전화번호 수정 / 3. 연락처 삭제 / 4. 상위 >\n"
					+ "선택 > ");
			if (isNameList) {
				scanNumber=scanner.next();
				if(scanNumber.length()<2){
					switch (scanNumber.charAt(0)) {
					case '1':
						System.out.println("수정할 이름을 입력하십시오.");
						String Name = scanner.next();
						listIndex = address.modifyNameByNameIndex(listIndex, Name);
						break;
					case '2':
						System.out.println("수정할 번호를 입력하십시오.");
						String Number = scanner.next();
						int isModify = address.modifyNumberByNameIndex(listIndex,
								Number);
						if (isModify == -1) {
							System.out.println("이미 등록된 번호입니다.");
						}
						break;
					case '3':
						address.removeUserByNameIndex(listIndex);
						System.out.println("정보가 삭제 되었습니다.");
						break;
					case '4':
						isQuit = true;
						break;
					default:
						System.out.println("1에서 4까지의 번호를 입력하십시오.");
						break;
					}
				}
				else
					System.out.println("1에서 4까지의 번호를 입력하십시오.");
			}

			else{
				scanNumber=scanner.next();
				if(scanNumber.length()<2){
					switch (scanNumber.charAt(0)) {
					case '1':
						System.out.println("수정할 이름을 입력하십시오.");
						String Name = scanner.next();
						listIndex = address.modifyNameByNameIndex(listIndex, Name);
						break;
					case '2':
						System.out.println("수정할 번호를 입력하십시오.");
						String Number = scanner.next();
						int isModify = address.modifyNumberByNameIndex(listIndex,
								Number);
						if (isModify == -1) {
							System.out.println("이미 등록된 번호입니다.");
						}
						break;
					case '3':
						address.removeUserByNameIndex(listIndex);
						System.out.println("정보가 삭제 되었습니다.");
						break;
					case '4':
						isQuit = true;
						break;
					default:
						System.out.println("1에서 4까지의 번호를 입력하십시오.");
						break;
					}
				}
				else
					System.out.println("1에서 4까지의 번호를 입력하십시오.");
			}
		}

		return listIndex;
	}

	private void addAddress() {// 연락처 추가
		System.out.println("----- 연락처 추가 -----");
		System.out.print("이름을 입력하세요 > ");
		String inputName = scanner.next();
		System.out.print("전화번호를 입력하세요 > ");
		String inputNumber = scanner.next();
		int isPlus = address.addUser(inputName, inputNumber);
		if (isPlus == -1) {
			System.out.println("이미 등록된 번호입니다.");
		} else {
			System.out.println("연락처가 등록되었습니다.");
		}
	}

	private void searchAddress() {
		boolean isQuit = false;
		int[] result;
		while (isQuit == false) {
			System.out.print("----- 연락처 검색 -----\n"
					+ "< 1. 이름으로 검색 / 2. 전화번호로 검색 / 3. 상위 >\n" + "선택 > ");
			scanNumber = scanner.next();
			if(scanNumber.length()<2){
				switch (scanNumber.charAt(0)) {
				case '1':
					System.out.print("검색할 이름 입력 > ");
					result = address.searchName(scanner.next());
					if (result.length == 0) {
						System.out.println("검색 결과가 없습니다.");
					} else {
						System.out.println(result.length + "개의 연락처가 검색되었습니다.");
						searchList(true, result);
					}
					break;
				case '2':
					System.out.print("검색할 전화번호 입력 > ");
					result = address.searchNumber(scanner.next());
					if (result.length == 0) {
						System.out.println("검색 결과가 없습니다.");
					} else {
						System.out.println(result.length + "개의 연락처가 검색되었습니다.");
						(false, result);
					}
					break;
				case '3':
					isQuit = true;
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

			System.out.println("----- 검색된 주소록 보기 <" + nowPage + "/" + numPages + "> " + result.length + "개 연락처 -----");
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
			System.out.print("< 6. 이전 / 7. 다음 / 8. 상위 >\n" + "선택 > ");
			scanNumber = scanner.next();
			if(scanNumber.length()<2){
				switch (scanNumber.charAt(0)) {
				case '1':
				case '2':
				case '3':
				case '4':
				case '5':
					if ((int)scanNumber.charAt(0)-48 > userOfNowPage) {
						System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
					} else {
						viewUser(isNameSearch, result[(nowPage - 1) * 5
								+ ((int)scanNumber.charAt(0)-48) - 1]);
						nowPage = 0;
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
}