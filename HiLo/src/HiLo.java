import java.util.Scanner;

public class HiLo {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		String playAgain = "";
		do {
			// ����Ҫ���û�ȥ�µ������
			// theNumber �� [1,101)
			int theNumber = (int)(Math.random() * 100 + 1);
	//		System.out.println(theNumber);
			int guess = 0;	// ���ڱ����û��Ĳ²�
			while(guess != theNumber) {
				System.out.println("Guess a number between 1 and 100:");
				guess = scan.nextInt();	// ��ȡһ������
	//			System.out.println("You entered " + guess + ".");
				if(guess < theNumber)	// �²�̫С
					System.out.println(guess + " is too low. Try again.");
				else if(guess > theNumber)	// �²�̫��
					System.out.println(guess + " is too high. Try again.");
				else	// ������
					System.out.println(guess + " is correct. You win!");
			}// ����ѭ�����˽���
			System.out.println("Would you like to play again (y/n)?");
			playAgain = scan.next();	// ��ȡһ���ַ���
		}while(playAgain.equalsIgnoreCase("y"));//qualsIgnoreCase�ǲ����ִ�Сд���ַ������
		System.out.println("Thank you for playing! Goodbye.");
		scan.close();	// �ֶ��ر�Scanner������Դ
	}
	
}
