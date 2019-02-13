import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeListener;

import BubblePanel.Bubble;
import BubblePanel.BubbleListner;

import javax.swing.event.ChangeEvent;

public class BubblePanel extends JPanel {
	Random rand = new Random();
	ArrayList<Bubble> bubbleList;	// 用可变长度的ArrayList来存储Bubble对象
	int size = 25;	// 气泡默认直径
	javax.swing.Timer timer;	// 定时器
	int delay = 33;	// 延迟33ms，即约30帧
	JSlider slider;	// 滑条组件声明
	
	// BubblePanel类的构造函数
	public BubblePanel() {
		/*
		 * 创建定时器的构造函数Timer()接受两个参数：
		 * 第一个是以毫秒为单位的延迟，
		 * 第二个是监听定时器事件的事件处理程序
		 * 定时器到期后触发actionPerformed()事件，类似于按钮单击事件actionPerformed()
		 * 定时器有点像每隔一段时间就单击自己的按钮
		 */
		timer = new javax.swing.Timer(delay, new BubbleListner());	// 实例化定时器
		bubbleList = new ArrayList<BubblePanel.Bubble>();	// 初始化，清空所有气泡
		setBackground(Color.BLACK);	// 画布背景色为黑色
		
		/* 下面的GUI组件在Design中设计时自动添加代码 */
		JPanel panel = new JPanel();
		add(panel);
		
		JButton btnClear = new JButton("\u6E05\u7A7A");
		// 监听清空按钮
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bubbleList = new ArrayList<BubblePanel.Bubble>();	// 清空变量bubbleList，设置成一个新的ArrayList
				repaint();	// 重绘
			}
		});
		
		JLabel lblNewLabel = new JLabel("\u52A8\u753B\u901F\u5EA6");
		panel.add(lblNewLabel);
		
		slider = new JSlider();
//		监听滑条变化
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				int speed = slider.getValue() + 1;	// 气泡漂浮速度
				int delay = 1000 / speed;	// 单位毫秒，速度越快，延迟越短，帧数越大
				timer.setDelay(delay);	// 更新定时器延迟值
			}
		});
		// 滑条相关参数
		slider.setValue(30);	// 默认值30
		slider.setPaintTicks(true);	// 标尺可见
		slider.setPaintLabels(true);	// 刻度可见
		slider.setMinorTickSpacing(5);	// 小刻度跨度5
		slider.setMajorTickSpacing(30);	// 大刻度跨度30
		slider.setMaximum(120);	// 最大值120
		panel.add(slider);
		panel.add(btnClear);
		
		JButton btnPause = new JButton("\u6682\u505C");
		// 监听暂停开始按钮
		btnPause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JButton btn = (JButton)e.getSource();	// 确定单击的是哪个按钮并将其转换为类型JButton
				// 根据单击按钮的标签，来确定单击的是什么按钮，执行相应操作
				if(btn.getText().equals("暂停")) {	// 暂停
					timer.stop();	// 定时器暂停
					btn.setText("开始");	// 标签设为开始
				}else {	// 开始
					timer.start();	// 定时器开始
					btn.setText("暂停");	// 标签设为暂停
				}
			}
		});
		panel.add(btnPause);
//		testBubbles();	// 测试语句
		addMouseListener(new BubbleListner());	// 调用方法指定使用BubbleListner来处理鼠标点击事件
		addMouseMotionListener(new BubbleListner());	// 调用方法指定使用BubbleListner来处理鼠标移动事件
		addMouseWheelListener(new BubbleListner());	// 调用方法指定使用BubbleListner来处理鼠标滚轮事件
		timer.start();
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
	
	/* 这里BubbleListner类应用了ActionListner类，以便监听actionPerformed事件 */
	private class BubbleListner extends MouseAdapter implements ActionListener{
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
		/* actionPerformed是ActionListner中的方法，重写 */
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			
			// 每次定时器到期时，对每个气泡都更新状态
			for(Bubble bubble : bubbleList)
				bubble.update();
			repaint();	// 重绘
		}
	}
	private class Bubble{
		// 声明一些气泡的属性，都是private
		private int x;	// x坐标
		private int y;	// y坐标
		private int size;	// 气泡直径
		private Color color;	// 气泡颜色
		// xspeed用于存储每次更新屏幕时气泡沿水平方向移动的像素数，表示水平速度
		// yspeed用于存储每次更新屏幕时气泡沿垂直方向移动的像素数，表示垂直速度
		private int xspeed, yspeed;
		private final int MAX_SPEED = 5;
		public Bubble(int newX, int newY, int newSize) {
			x = newX;	// 初始化x坐标
			y = newY;	// 初始化y坐标
			size = newSize;	// 初始化气泡直径
			color = new Color(rand.nextInt(256),
					rand.nextInt(256),
					rand.nextInt(256),
					rand.nextInt(256));		// 随机初始化一个彩色透明气泡
			xspeed = yspeed = 3;	// 水平垂直速度相同，使绘制的气泡朝相同的方向一起运动
		}
		// 方法draw接受一个Graphics类型的参数（表示画布）
		public void draw(Graphics canvas) {
			canvas.setColor(color);	// 将画笔颜色设置为气泡颜色
			canvas.fillOval(x - size/2, y - size/2, size, size);	// 绘制椭圆，x、y参数是外接矩形左上角的坐标
		}
		// 更新气泡状态
		public void update() {
			x += xspeed;	// 更新横坐标，加上水平速度
			y += yspeed;	// 更新纵坐标，加上水平速度
			// 边缘检测，速度方向，表示碰撞反弹
			if(x - size/2 <= 0 || x + size/2 >= getWidth()) {
				xspeed = -xspeed;
			}
			if(y - size/2 <= 0 || y + size/2 >= getHeight()) {
				yspeed = -yspeed;
			}
		}
	}
	
}
