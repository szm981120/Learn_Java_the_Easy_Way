import javax.swing.JFrame;

public class BubbleDrawGUI extends JFrame {

	/* ��������FlexiDrawGUI�������򣬶���BubblePanel����Ȼ���������������BubblePanel������ */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame frame = new JFrame("Shen Ziming's FlexiDraw GUI App");	// ʵ����һ��������������JFrame����
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	// ����Ĭ�Ϲرղ������رմ���ʱ�˳�Ӧ��
		frame.getContentPane().add(new BubblePanel());	//	�½�һ��BubblePanel������ӵ�����У����������ʵ�ֹ��ܵ�����
		frame.setSize(new java.awt.Dimension(1024,768));	// ���ô��ڳߴ�
		frame.setVisible(true);	// ���ڿɼ�
	}

}
