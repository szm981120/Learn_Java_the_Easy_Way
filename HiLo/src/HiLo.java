import java.util.Scanner;

public class HiLo {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		String playAgain = "";
		do {
			// 生成要让用户去猜的随机数
			// theNumber ∈ [1,101)
			int theNumber = (int)(Math.random() * 100 + 1);
	//		System.out.println(theNumber);
			int guess = 0;	// 用于保存用户的猜测
			while(guess != theNumber) {
				System.out.println("Guess a number between 1 and 100:");
				guess = scan.nextInt();	// 读取一个整型
	//			System.out.println("You entered " + guess + ".");
				if(guess < theNumber)	// 猜测太小
					System.out.println(guess + " is too low. Try again.");
				else if(guess > theNumber)	// 猜测太大
					System.out.println(guess + " is too high. Try again.");
				else	// 猜中了
					System.out.println(guess + " is correct. You win!");
			}// 猜数循环到此结束
			System.out.println("Would you like to play again (y/n)?");
			playAgain = scan.next();	// 读取一段字符串
		}while(playAgain.equalsIgnoreCase("y"));//qualsIgnoreCase是不区分大小写的字符串相等
		System.out.println("Thank you for playing! Goodbye.");
		scan.close();	// 手动关闭Scanner对象资源
	}
	
}
