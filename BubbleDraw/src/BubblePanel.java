import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;

public class BubblePanel extends JPanel {
	Random rand = new Random();
	ArrayList<Bubble> bubbleList;	// 用可变长度的ArrayList来存储Bubble对象
	int size = 25;	// 气泡默认直径
	// BubblePanel类的构造函数
	public BubblePanel() {
		bubbleList = new ArrayList<BubblePanel.Bubble>();	// 初始化，清空所有气泡
		setBackground(Color.BLACK);	// 画布背景色为黑色
//		testBubbles();	// 测试语句
		addMouseListener(new BubbleListner());	// 调用方法指定使用BubbleListner来处理鼠标点击事件
		addMouseMotionListener(new BubbleListner());	// 调用方法指定使用BubbleListner来处理鼠标移动事件
		addMouseWheelListener(new BubbleListner());	// 调用方法指定使用BubbleListner来处理鼠标滚轮事件
	}
	/*
	 * 方法paintComponent是javax.swing工具包中的GUI组件所共有的，它用来在屏幕上绘制组件
	 * 重写默认的paintComponent方法，来绘制bubbleList中的所有气泡
	 * 要重写父类的方法，方法签名必须完全相同，即方法的声明必须与父类一样，这是强制要求
	 */
	public void paintComponent(Graphics canvas) {
		// super让Java调用JPanel类的方法paintComponent，父类的这个方法的所有代码都加入到新的paintComponent中
		super.paintComponent(canvas);
		// 遍历bubbleList中的所有Bubble对象，并绘制到画布上
		for(Bubble b : bubbleList) {
			b.draw(canvas);
		}
	}
	/*
	 * 测试代码
	 * 生成100个气泡，每个气泡的坐标和直径都是随机的，颜色也是随机的
	 * public void testBubbles() {
		for(int n=0; n<100; n++) {
			int x = rand.nextInt(600);
			int y = rand.nextInt(400);
			int size = rand.nextInt(50);
			bubbleList.add(new Bubble(x, y, size));
		}
		// 方法repaint负责重绘背景并调用paintComponent
		repaint();
		}
	 */
	
	/*
	 * 接下来是创建事件监听器，这种事件监听器与匿名内部类不同，但是该应用需要响应三个动作，
	 * 如果用匿名内部类，每个事件都要添加三个监听器，并且这里有两个动作的处理是相同的，
	 * 总之，用下面的方式来创建事件监听器，是更好的选择
	 * 要创建事件监听器，必须在文件开头导入一个库java.awt.event.*
	 * 
	 * 编写一个私有内部类BubbleListner来监听鼠标事件，扩展继承了MouseAdapter类
	 */
	private class BubbleListner extends MouseAdapter{
		/* 这里面的三个方法都是MouseAdapter的方法，这里是重写 */
		public void mousePressed(MouseEvent event) {
			// 鼠标点击，添加一个气泡，重绘
			bubbleList.add(new Bubble(event.getX(), event.getY(), size));
			repaint();
		}
		public void mouseDragged(MouseEvent event) {
			// 鼠标拖拽，添加一个气泡，重绘
			bubbleList.add(new Bubble(event.getX(), event.getY(), size));
			repaint();
		}
		public void mouseWheelMoved(MouseWheelEvent event) {
			// 鼠标滚轮滚动
			size += event.getUnitsToScroll();	// 滚轮两个方向滚动，可以得到正或负的幅值，作为气泡直径的变化大小
			if(size <= 3)
				size = 3;
		}
	}
	// 内部助手类Bubble声明为private，做好封装，以防其他程序直接访问它们
	private class Bubble{
		// 声明一些气泡的属性，都是private
		private int x;	// x坐标
		private int y;	// y坐标
		private int size;	// 气泡直径
		private Color color;	// 气泡颜色
		// public Bubble是Bubble类的构造函数，利用构造函数创建对象时，就是初始化属性
		public Bubble(int newX, int newY, int newSize) {
			x = newX;	// 初始化x坐标
			y = newY;	// 初始化y坐标
			size = newSize;	// 初始化气泡直径
			color = new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));	// 随机初始化一个彩色气泡
		}
		// 方法draw接受一个Graphics类型的参数（表示画布）
		public void draw(Graphics canvas) {
			canvas.setColor(color);	// 将画笔颜色设置为气泡颜色
			canvas.fillOval(x - size/2, y - size/2, size, size);	// 绘制椭圆，x、y参数是外接矩形左上角的坐标
		}
	}
	
}
