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
 * 扩展引用类JFrame，使GuessingGame类可以使用父类中的一些GUI插件功能
 */
public class GuessingGame extends JFrame {
	// 需要调用的插件变量声明
	private JTextField txtGuess;
	private JLabel lblOutput;
	private JButton btnPlayAgain;
	
	private int theNumber;	// 答案
	private int numberOfTries;	// 猜测次数
	
	// 新一轮游戏
	public void newGame() {
		theNumber = (int)(Math.random() * 100 + 1);	// 随机生成[0,101)的整数作为答案
		btnPlayAgain.setVisible(false);	// 再玩一次的按钮设为不可见
		numberOfTries = 0;	// 重置本轮游戏的猜测次数
	}
	// 猜数
	public void checkGuess() {
		numberOfTries++;	// 每执行一次猜数，猜测次数加一
		String guessText = txtGuess.getText();	// 获取猜测框中的文本内容
		String message = "";	// 输出信息
		try {
			int guess = Integer.parseInt(guessText);	// 利用转换获得猜测框中的int
			if(guess < theNumber)	// 猜小了
				message = guess + "太小了，再试试吧！";
			else if(guess > theNumber)	// 猜大了
				message = guess + "太大了，再试试吧！";
			else {	// 猜对了
				message = guess + "猜对了，你赢啦！你用了 " + numberOfTries + "次。再玩一次吧！";
				btnPlayAgain.setVisible(true);	// 猜对了之后使再玩一次按钮可见，以开启下轮游戏
			}
		}catch(Exception e) {
			message = "请输入1到100的整数！";	// 输入不合法
		}finally {
			lblOutput.setText(message);	// 总是设置输出信息
			txtGuess.requestFocus();	// 总是在猜完之后把光标定在输入框
			txtGuess.selectAll();	// 输入框全选，改善用户体验
		}
	}
	public GuessingGame() {
		// 之前声明好的插件变量，在该方法中无需再次声明变量类型
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("\u6C88\u5B50\u9E23\u7684\u731C\u6570\u6E38\u620F");
		getContentPane().setLayout(null);
		
		JLabel label = new JLabel("\u6C88\u5B50\u9E23\u7684\u731C\u6570\u6E38\u620F");
		label.setFont(new Font("微软雅黑", Font.BOLD, 20));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(100, 11, 231, 39);
		getContentPane().add(label);
		
		JLabel label_1 = new JLabel("\u8BF7\u731C\u4E00\u4E2A1\u5230100\u4E4B\u95F4\u7684\u6574\u6570\uFF1A");
		label_1.setFont(new Font("微软雅黑 Light", Font.BOLD, 17));
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setBounds(58, 61, 241, 39);
		getContentPane().add(label_1);
		
		// 创建一个文本框，并添加监听，实现回车即可执行猜数
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
		
		// 创建一个有标签的猜数按钮，并添加监听，实现点击即可执行猜数
		JButton btnGuess = new JButton("\u5C31\u731C\u8FD9\u4E2A\uFF01");
		btnGuess.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				checkGuess();
			}
		});
		btnGuess.setFont(new Font("微软雅黑 Light", Font.BOLD, 15));
		btnGuess.setBounds(154, 111, 124, 39);
		getContentPane().add(btnGuess);
		
		// 输出信息文本
		lblOutput = new JLabel("\u5728\u4E0A\u9762\u586B\u4E0A\u8981\u731C\u7684\u6570\uFF0C\u7136\u540E\u70B9\u201C\u5C31\u731C\u8FD9\u4E2A\uFF01\u201D\u6216\u8005\u56DE\u8F66");
		lblOutput.setFont(new Font("微软雅黑 Light", Font.PLAIN, 16));
		lblOutput.setHorizontalAlignment(SwingConstants.CENTER);
		lblOutput.setBounds(0, 211, 432, 31);
		getContentPane().add(lblOutput);
		
		// 创建有标签的再玩一次按钮，并添加监听，实现点击即可执行新一轮游戏
		btnPlayAgain = new JButton("\u518D\u73A9\u4E00\u6B21\uFF01");
		btnPlayAgain.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				newGame();
			}
		});
		btnPlayAgain.setFont(new Font("微软雅黑 Light", Font.BOLD, 15));
		btnPlayAgain.setBounds(157, 161, 117, 43);
		getContentPane().add(btnPlayAgain);
	}
	
	// 主函数
	public static void main(String[] args) {
		GuessingGame theGame = new GuessingGame();	// 创建一个类的实例对象
		theGame.newGame();	// 开始游戏
		theGame.setSize(new Dimension(450, 300));	// 设置界面大小
		theGame.setVisible(true);	// 界面可见
	}
}
