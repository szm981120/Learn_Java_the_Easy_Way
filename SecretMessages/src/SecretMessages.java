import java.util.Scanner;
public class SecretMessages {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scan = new Scanner(System.in);
		System.out.println("请输入一条信息用于编码和解码：");
		String message = scan.nextLine();	// 要加密的信息
		String output = "";	// 加密结果
		/* 该版本SecretMessage之所以需要密钥是-25到25之间，是因为超出此范围后，密文字符可能不再是拉丁字母了 */
		/* 如果有数字，建议密钥在-9到9之间，因为数字只有10个，超出此范围，数字对应密闻字符可能不是数字了 */
		System.out.println("请输入密钥（-25到25之间，建议-9到9之间）：");
		int keyVal = scan.nextInt();	// 密钥
		char key = (char)keyVal;	// 为了对信息文本凯撒加密，将密钥转成字符型作为偏移量
		/* 反向凯撒加密 */
		for(int x=message.length()-1; x>=0; x--) {
			/*
			 * 反向凯撒加密
			 * 对明文反向取字符作为密文的正向输出，同时根据密钥偏移得到加密字符
			 * 大段的明文，还要判断字符是大小写，还是数字
			 */
			char input = message.charAt(x);
			// 大写字符
			if(input >= 'A' && input <= 'Z') {
				input += key;
				if(input > 'Z')
					input -= 26;
				if(input < 'A')
					input += 26;
			}
			// 小写字符
			else if(input >= 'a' && input <= 'z') {
				input += key;
				if(input > 'z')
					input -= 26;
				if(input < 'a')
					input += 26;
			}
			// 数字
			else if(input >= '0' && input <= '9') {
				input += keyVal;
				if(input > '9')
					input -= 10;
				if(input < '0')
					input += 10;
			}
			output += input;	// 构造密文
		}
		System.out.println(output);	//输出密文
		scan.close();	// 关闭Scanner对象资源
	}

}
