import java.util.Scanner;
public class SecretMessages {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scan = new Scanner(System.in);
		System.out.println("������һ����Ϣ���ڱ���ͽ��룺");
		String message = scan.nextLine();	// Ҫ���ܵ���Ϣ
		String output = "";	// ���ܽ��
		/* �ð汾SecretMessage֮������Ҫ��Կ��-25��25֮�䣬����Ϊ�����˷�Χ�������ַ����ܲ�����������ĸ�� */
		/* ��������֣�������Կ��-9��9֮�䣬��Ϊ����ֻ��10���������˷�Χ�����ֶ�Ӧ�����ַ����ܲ��������� */
		System.out.println("��������Կ��-25��25֮�䣬����-9��9֮�䣩��");
		int keyVal = scan.nextInt();	// ��Կ
		char key = (char)keyVal;	// Ϊ�˶���Ϣ�ı��������ܣ�����Կת���ַ�����Ϊƫ����
		/* ���������� */
		for(int x=message.length()-1; x>=0; x--) {
			/*
			 * ����������
			 * �����ķ���ȡ�ַ���Ϊ���ĵ����������ͬʱ������Կƫ�Ƶõ������ַ�
			 * ��ε����ģ���Ҫ�ж��ַ��Ǵ�Сд����������
			 */
			char input = message.charAt(x);
			// ��д�ַ�
			if(input >= 'A' && input <= 'Z') {
				input += key;
				if(input > 'Z')
					input -= 26;
				if(input < 'A')
					input += 26;
			}
			// Сд�ַ�
			else if(input >= 'a' && input <= 'z') {
				input += key;
				if(input > 'z')
					input -= 26;
				if(input < 'a')
					input += 26;
			}
			// ����
			else if(input >= '0' && input <= '9') {
				input += keyVal;
				if(input > '9')
					input -= 10;
				if(input < '0')
					input += 10;
			}
			output += input;	// ��������
		}
		System.out.println(output);	//�������
		scan.close();	// �ر�Scanner������Դ
	}

}
