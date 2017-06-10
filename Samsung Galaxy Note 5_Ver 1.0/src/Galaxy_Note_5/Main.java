package Galaxy_Note_5;

import java.util.*;

public class Main {
	private static Scanner scanner;

	public static void main(String[] args) {
		String scanNumber;
		scanner = new Scanner(System.in);
		boolean isQuit = false;
		while (isQuit == false) {
			System.out.print("----- Samsung Galaxy Note 5 -----\n"
					+ "1. 통화기록\n" + "2. 메시지\n" + "3. 주소록\n" + "4. 종료\n" + "선택 > ");
			scanNumber=scanner.next();
			if(scanNumber.length()<2){
				switch (scanNumber.charAt(0)) {
				case '1':
					new CallMenu();
					break;
				case '2':
					new MessageMenu();
					break;
				case '3':
					new AddressMenu();
					break;
				case '4':
					isQuit = true;
					break;
				default:
					System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
					break;
				}
			}
			else{
				System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
			}
				
		}
		System.out.println("Galaxy Note가 종료되었습니다.");
	}
}
