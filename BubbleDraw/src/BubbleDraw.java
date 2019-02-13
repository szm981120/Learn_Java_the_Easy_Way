import javax.swing.JFrame;

public class BubbleDraw extends JFrame {

	/* 这是我们BubbleDraw的主程序，而非BubblePanel，虽然大多数工作都是在BubblePanel中做的 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame frame = new JFrame("Shen Ziming's BubbleDraw App");	// 实例化一个包含标题栏的JFrame对象
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	// 设置默认关闭操作，关闭窗口时退出应用
		frame.getContentPane().add(new BubblePanel());	//	新建一个BubblePanel对象并添加到框架中，这个对象是实现功能的主力
		frame.setSize(new java.awt.Dimension(600,400));	// 设置窗口尺寸
		frame.setVisible(true);	// 窗口可见
	}

}
