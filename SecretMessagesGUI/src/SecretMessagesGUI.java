import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Color;
import javax.swing.UIManager;
import javax.swing.JSlider;
import java.awt.SystemColor;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.JScrollPane;

public class SecretMessagesGUI extends JFrame {
	/*
	 * 组件变量声明
	 * 不要忘记把eclipse在SecretMessagesGUI方法中自动生成的一些变量声明删掉
	 */
	private JTextField txtKey;
	private JTextArea txtIn;
	private JTextArea txtOut;
	private JButton btnEncodeDecode;
	private JSlider slider;
	private JScrollPane scrollPane;
	private JScrollPane scrollPane_1;
	
	public String encode(String message, int keyVal) {
		String output = "";	// 加密结果
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
		return output;
	}
	public SecretMessagesGUI() {
		getContentPane().setBackground(UIManager.getColor("InternalFrame.inactiveTitleGradient"));
		setTitle("Shen Ziming\u7684\u5BC6\u7801App");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(14, 13, 554, 130);
		getContentPane().add(scrollPane);
		
		txtIn = new JTextArea();
		scrollPane.setViewportView(txtIn);
		txtIn.setWrapStyleWord(true);
		txtIn.setLineWrap(true);
		txtIn.setBackground(new Color(255, 255, 255));
		txtIn.setFont(new Font("Lucida Console", Font.PLAIN, 18));
		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(14, 210, 554, 130);
		getContentPane().add(scrollPane_1);
		
		txtOut = new JTextArea();
		scrollPane_1.setViewportView(txtOut);
		txtOut.setWrapStyleWord(true);
		txtOut.setLineWrap(true);
		txtOut.setFont(new Font("Lucida Console", Font.PLAIN, 18));
		
		JLabel label = new JLabel("\u5BC6\u94A5\uFF1A");
		label.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		label.setBounds(301, 166, 57, 24);
		getContentPane().add(label);
		
		txtKey = new JTextField();
		txtKey.setHorizontalAlignment(SwingConstants.CENTER);
		txtKey.setText("0");
		txtKey.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		txtKey.setBounds(361, 166, 42, 24);
		getContentPane().add(txtKey);
		txtKey.setColumns(10);
		
		btnEncodeDecode = new JButton("\u7F16\u7801/\u89E3\u7801");
		btnEncodeDecode.setForeground(new Color(0, 0, 0));
		btnEncodeDecode.setBackground(UIManager.getColor("Button.background"));
		btnEncodeDecode.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		// 编码/解码按钮监听
		btnEncodeDecode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					String message = txtIn.getText();	// 获取源文本
					int keyVal = Integer.parseInt(txtKey.getText());	// 获取密钥
					String output = encode(message, keyVal);	// 得到目标文本
					txtOut.setText(output);	// 输出
				} catch (Exception e) {
					// TODO: handle exception
					// 输入不合法处理
					JOptionPane.showMessageDialog(null, "输入的密钥必须是整数！"); 
					// 光标重置并全选有利于提升用户体验
					txtKey.requestFocus();
					txtKey.selectAll();
				}
			}
		});
		btnEncodeDecode.setBounds(417, 166, 114, 24);
		getContentPane().add(btnEncodeDecode);
		
		// 构造一个滑条，便于调整密钥
		slider = new JSlider();
		// 滑条值改变监听
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				txtKey.setText("" + slider.getValue());	// 密钥文本区中的值随滑条改变而改变
				String message = txtIn.getText();	// 获取源文本
				int keyVal = slider.getValue();	// 获取密钥
				String output = encode(message, keyVal);	// 得到目标文本
				txtOut.setText(output);	// 输出
			}
		});
		// 以下参数设置可以在Design选项卡中设置
		slider.setValue(0);	// 滑条初值
		slider.setMajorTickSpacing(6);	// 大刻度跨度	
		slider.setMinorTickSpacing(1);	// 小刻度跨度
		slider.setMinimum(-24);	// 最小值
		slider.setMaximum(24);	// 最大值
		slider.setPaintLabels(true);	// 刻度可见
		slider.setForeground(SystemColor.window);
		slider.setBackground(UIManager.getColor("InternalFrame.inactiveTitleBackground"));
		slider.setBounds(24, 153, 263, 44);
		getContentPane().add(slider);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SecretMessagesGUI theApp = new SecretMessagesGUI();	// GUI实例化
		theApp.setSize(new java.awt.Dimension(600,400));	// 设置截面大小
		theApp.setVisible(true);	// GUI可见
	}
}
