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
					+ "1. ��ȭ���\n" + "2. �޽���\n" + "3. �ּҷ�\n" + "4. ����\n" + "���� > ");
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
					System.out.println("�߸��� �Է��Դϴ�. �ٽ� �Է����ּ���.");
					break;
				}
			}
			else{
				System.out.println("�߸��� �Է��Դϴ�. �ٽ� �Է����ּ���.");
			}
				
		}
		System.out.println("Galaxy Note�� ����Ǿ����ϴ�.");
	}
}
