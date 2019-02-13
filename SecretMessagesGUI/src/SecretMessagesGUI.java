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
	 * �����������
	 * ��Ҫ���ǰ�eclipse��SecretMessagesGUI�������Զ����ɵ�һЩ��������ɾ��
	 */
	private JTextField txtKey;
	private JTextArea txtIn;
	private JTextArea txtOut;
	private JButton btnEncodeDecode;
	private JSlider slider;
	private JScrollPane scrollPane;
	private JScrollPane scrollPane_1;
	
	public String encode(String message, int keyVal) {
		String output = "";	// ���ܽ��
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
		label.setFont(new Font("΢���ź�", Font.PLAIN, 16));
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		label.setBounds(301, 166, 57, 24);
		getContentPane().add(label);
		
		txtKey = new JTextField();
		txtKey.setHorizontalAlignment(SwingConstants.CENTER);
		txtKey.setText("0");
		txtKey.setFont(new Font("΢���ź�", Font.PLAIN, 16));
		txtKey.setBounds(361, 166, 42, 24);
		getContentPane().add(txtKey);
		txtKey.setColumns(10);
		
		btnEncodeDecode = new JButton("\u7F16\u7801/\u89E3\u7801");
		btnEncodeDecode.setForeground(new Color(0, 0, 0));
		btnEncodeDecode.setBackground(UIManager.getColor("Button.background"));
		btnEncodeDecode.setFont(new Font("΢���ź�", Font.PLAIN, 16));
		// ����/���밴ť����
		btnEncodeDecode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					String message = txtIn.getText();	// ��ȡԴ�ı�
					int keyVal = Integer.parseInt(txtKey.getText());	// ��ȡ��Կ
					String output = encode(message, keyVal);	// �õ�Ŀ���ı�
					txtOut.setText(output);	// ���
				} catch (Exception e) {
					// TODO: handle exception
					// ���벻�Ϸ�����
					JOptionPane.showMessageDialog(null, "�������Կ������������"); 
					// ������ò�ȫѡ�����������û�����
					txtKey.requestFocus();
					txtKey.selectAll();
				}
			}
		});
		btnEncodeDecode.setBounds(417, 166, 114, 24);
		getContentPane().add(btnEncodeDecode);
		
		// ����һ�����������ڵ�����Կ
		slider = new JSlider();
		// ����ֵ�ı����
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				txtKey.setText("" + slider.getValue());	// ��Կ�ı����е�ֵ�滬���ı���ı�
				String message = txtIn.getText();	// ��ȡԴ�ı�
				int keyVal = slider.getValue();	// ��ȡ��Կ
				String output = encode(message, keyVal);	// �õ�Ŀ���ı�
				txtOut.setText(output);	// ���
			}
		});
		// ���²������ÿ�����Designѡ�������
		slider.setValue(0);	// ������ֵ
		slider.setMajorTickSpacing(6);	// ��̶ȿ��	
		slider.setMinorTickSpacing(1);	// С�̶ȿ��
		slider.setMinimum(-24);	// ��Сֵ
		slider.setMaximum(24);	// ���ֵ
		slider.setPaintLabels(true);	// �̶ȿɼ�
		slider.setForeground(SystemColor.window);
		slider.setBackground(UIManager.getColor("InternalFrame.inactiveTitleBackground"));
		slider.setBounds(24, 153, 263, 44);
		getContentPane().add(slider);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SecretMessagesGUI theApp = new SecretMessagesGUI();	// GUIʵ����
		theApp.setSize(new java.awt.Dimension(600,400));	// ���ý����С
		theApp.setVisible(true);	// GUI�ɼ�
	}
}
