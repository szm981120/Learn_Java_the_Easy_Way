import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
/*
 * ��չ������JFrame��ʹGuessingGame�����ʹ�ø����е�һЩGUI�������
 */
public class GuessingGame extends JFrame {
	// ��Ҫ���õĲ����������
	private JTextField txtGuess;
	private JLabel lblOutput;
	private JButton btnPlayAgain;
	
	private int theNumber;	// ��
	private int numberOfTries;	// �²����
	
	// ��һ����Ϸ
	public void newGame() {
		theNumber = (int)(Math.random() * 100 + 1);	// �������[0,101)��������Ϊ��
		btnPlayAgain.setVisible(false);	// ����һ�εİ�ť��Ϊ���ɼ�
		numberOfTries = 0;	// ���ñ�����Ϸ�Ĳ²����
	}
	// ����
	public void checkGuess() {
		numberOfTries++;	// ÿִ��һ�β������²������һ
		String guessText = txtGuess.getText();	// ��ȡ�²���е��ı�����
		String message = "";	// �����Ϣ
		try {
			int guess = Integer.parseInt(guessText);	// ����ת����ò²���е�int
			if(guess < theNumber)	// ��С��
				message = guess + "̫С�ˣ������԰ɣ�";
			else if(guess > theNumber)	// �´���
				message = guess + "̫���ˣ������԰ɣ�";
			else {	// �¶���
				message = guess + "�¶��ˣ���Ӯ���������� " + numberOfTries + "�Ρ�����һ�ΰɣ�";
				btnPlayAgain.setVisible(true);	// �¶���֮��ʹ����һ�ΰ�ť�ɼ����Կ���������Ϸ
			}
		}catch(Exception e) {
			message = "������1��100��������";	// ���벻�Ϸ�
		}finally {
			lblOutput.setText(message);	// �������������Ϣ
			txtGuess.requestFocus();	// �����ڲ���֮��ѹ�궨�������
			txtGuess.selectAll();	// �����ȫѡ�������û�����
		}
	}
	public GuessingGame() {
		// ֮ǰ�����õĲ���������ڸ÷����������ٴ�������������
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("\u6C88\u5B50\u9E23\u7684\u731C\u6570\u6E38\u620F");
		getContentPane().setLayout(null);
		
		JLabel label = new JLabel("\u6C88\u5B50\u9E23\u7684\u731C\u6570\u6E38\u620F");
		label.setFont(new Font("΢���ź�", Font.BOLD, 20));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(100, 11, 231, 39);
		getContentPane().add(label);
		
		JLabel label_1 = new JLabel("\u8BF7\u731C\u4E00\u4E2A1\u5230100\u4E4B\u95F4\u7684\u6574\u6570\uFF1A");
		label_1.setFont(new Font("΢���ź� Light", Font.BOLD, 17));
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setBounds(58, 61, 241, 39);
		getContentPane().add(label_1);
		
		// ����һ���ı��򣬲���Ӽ�����ʵ�ֻس�����ִ�в���
		txtGuess = new JTextField();
		txtGuess.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				checkGuess();
			}
		});
		txtGuess.setHorizontalAlignment(SwingConstants.RIGHT);
		txtGuess.setBounds(293, 74, 66, 24);
		getContentPane().add(txtGuess);
		txtGuess.setColumns(10);
		
		// ����һ���б�ǩ�Ĳ�����ť������Ӽ�����ʵ�ֵ������ִ�в���
		JButton btnGuess = new JButton("\u5C31\u731C\u8FD9\u4E2A\uFF01");
		btnGuess.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				checkGuess();
			}
		});
		btnGuess.setFont(new Font("΢���ź� Light", Font.BOLD, 15));
		btnGuess.setBounds(154, 111, 124, 39);
		getContentPane().add(btnGuess);
		
		// �����Ϣ�ı�
		lblOutput = new JLabel("\u5728\u4E0A\u9762\u586B\u4E0A\u8981\u731C\u7684\u6570\uFF0C\u7136\u540E\u70B9\u201C\u5C31\u731C\u8FD9\u4E2A\uFF01\u201D\u6216\u8005\u56DE\u8F66");
		lblOutput.setFont(new Font("΢���ź� Light", Font.PLAIN, 16));
		lblOutput.setHorizontalAlignment(SwingConstants.CENTER);
		lblOutput.setBounds(0, 211, 432, 31);
		getContentPane().add(lblOutput);
		
		// �����б�ǩ������һ�ΰ�ť������Ӽ�����ʵ�ֵ������ִ����һ����Ϸ
		btnPlayAgain = new JButton("\u518D\u73A9\u4E00\u6B21\uFF01");
		btnPlayAgain.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				newGame();
			}
		});
		btnPlayAgain.setFont(new Font("΢���ź� Light", Font.BOLD, 15));
		btnPlayAgain.setBounds(157, 161, 117, 43);
		getContentPane().add(btnPlayAgain);
	}
	
	// ������
	public static void main(String[] args) {
		GuessingGame theGame = new GuessingGame();	// ����һ�����ʵ������
		theGame.newGame();	// ��ʼ��Ϸ
		theGame.setSize(new Dimension(450, 300));	// ���ý����С
		theGame.setVisible(true);	// ����ɼ�
	}
}
