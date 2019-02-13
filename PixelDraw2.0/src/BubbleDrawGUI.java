import javax.swing.JFrame;

public class BubbleDrawGUI extends JFrame {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame frame = new JFrame("Shen Ziming's PixelDraw2.0 GUI App");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(new BubblePanel());
		frame.setSize(new java.awt.Dimension(1024,768));
		frame.setVisible(true);
	}

}
