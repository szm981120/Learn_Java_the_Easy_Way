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
	ArrayList<Bubble> bubbleList;	// �ÿɱ䳤�ȵ�ArrayList���洢Bubble����
	int size = 25;	// ����Ĭ��ֱ��
	javax.swing.Timer timer;	// ��ʱ��
	int delay = 33;	// �ӳ�33ms����Լ30֡
	JSlider slider;	// �����������
	
	// BubblePanel��Ĺ��캯��
	public BubblePanel() {
		/*
		 * ������ʱ���Ĺ��캯��Timer()��������������
		 * ��һ�����Ժ���Ϊ��λ���ӳ٣�
		 * �ڶ����Ǽ�����ʱ���¼����¼��������
		 * ��ʱ�����ں󴥷�actionPerformed()�¼��������ڰ�ť�����¼�actionPerformed()
		 * ��ʱ���е���ÿ��һ��ʱ��͵����Լ��İ�ť
		 */
		timer = new javax.swing.Timer(delay, new BubbleListner());	// ʵ������ʱ��
		bubbleList = new ArrayList<BubblePanel.Bubble>();	// ��ʼ���������������
		setBackground(Color.BLACK);	// ��������ɫΪ��ɫ
		
		/* �����GUI�����Design�����ʱ�Զ���Ӵ��� */
		JPanel panel = new JPanel();
		add(panel);
		
		JButton btnClear = new JButton("\u6E05\u7A7A");
		// ������հ�ť
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bubbleList = new ArrayList<BubblePanel.Bubble>();	// ��ձ���bubbleList�����ó�һ���µ�ArrayList
				repaint();	// �ػ�
			}
		});
		
		JLabel lblNewLabel = new JLabel("\u52A8\u753B\u901F\u5EA6");
		panel.add(lblNewLabel);
		
		slider = new JSlider();
//		���������仯
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				int speed = slider.getValue() + 1;	// ����Ư���ٶ�
				int delay = 1000 / speed;	// ��λ���룬�ٶ�Խ�죬�ӳ�Խ�̣�֡��Խ��
				timer.setDelay(delay);	// ���¶�ʱ���ӳ�ֵ
			}
		});
		// ������ز���
		slider.setValue(30);	// Ĭ��ֵ30
		slider.setPaintTicks(true);	// ��߿ɼ�
		slider.setPaintLabels(true);	// �̶ȿɼ�
		slider.setMinorTickSpacing(5);	// С�̶ȿ��5
		slider.setMajorTickSpacing(30);	// ��̶ȿ��30
		slider.setMaximum(120);	// ���ֵ120
		panel.add(slider);
		panel.add(btnClear);
		
		JButton btnPause = new JButton("\u6682\u505C");
		// ������ͣ��ʼ��ť
		btnPause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JButton btn = (JButton)e.getSource();	// ȷ�����������ĸ���ť������ת��Ϊ����JButton
				// ���ݵ�����ť�ı�ǩ����ȷ����������ʲô��ť��ִ����Ӧ����
				if(btn.getText().equals("��ͣ")) {	// ��ͣ
					timer.stop();	// ��ʱ����ͣ
					btn.setText("��ʼ");	// ��ǩ��Ϊ��ʼ
				}else {	// ��ʼ
					timer.start();	// ��ʱ����ʼ
					btn.setText("��ͣ");	// ��ǩ��Ϊ��ͣ
				}
			}
		});
		panel.add(btnPause);
//		testBubbles();	// �������
		addMouseListener(new BubbleListner());	// ���÷���ָ��ʹ��BubbleListner������������¼�
		addMouseMotionListener(new BubbleListner());	// ���÷���ָ��ʹ��BubbleListner����������ƶ��¼�
		addMouseWheelListener(new BubbleListner());	// ���÷���ָ��ʹ��BubbleListner�������������¼�
		timer.start();
	}
	/*
	 * ����paintComponent��javax.swing���߰��е�GUI��������еģ�����������Ļ�ϻ������
	 * ��дĬ�ϵ�paintComponent������������bubbleList�е���������
	 * Ҫ��д����ķ���������ǩ��������ȫ��ͬ�������������������븸��һ��������ǿ��Ҫ��
	 */
	public void paintComponent(Graphics canvas) {
		// super��Java����JPanel��ķ���paintComponent�������������������д��붼���뵽�µ�paintComponent��
		super.paintComponent(canvas);
		// ����bubbleList�е�����Bubble���󣬲����Ƶ�������
		for(Bubble b : bubbleList) {
			b.draw(canvas);
		}
	}
	
	/*
	 * ���Դ���
	 * ����100�����ݣ�ÿ�����ݵ������ֱ����������ģ���ɫҲ�������
	 * public void testBubbles() {
		for(int n=0; n<100; n++) {
			int x = rand.nextInt(600);
			int y = rand.nextInt(400);
			int size = rand.nextInt(50);
			bubbleList.add(new Bubble(x, y, size));
		}
		// ����repaint�����ػ汳��������paintComponent
		repaint();
		}
	 */
	
	/* ����BubbleListner��Ӧ����ActionListner�࣬�Ա����actionPerformed�¼� */
	private class BubbleListner extends MouseAdapter implements ActionListener{
		/* �������������������MouseAdapter�ķ�������������д */
		public void mousePressed(MouseEvent event) {
			// ����������һ�����ݣ��ػ�
			bubbleList.add(new Bubble(event.getX(), event.getY(), size));
			repaint();
		}
		public void mouseDragged(MouseEvent event) {
			// �����ק�����һ�����ݣ��ػ�
			bubbleList.add(new Bubble(event.getX(), event.getY(), size));
			repaint();
		}
		public void mouseWheelMoved(MouseWheelEvent event) {
			// �����ֹ���
			size += event.getUnitsToScroll();	// ��������������������Եõ����򸺵ķ�ֵ����Ϊ����ֱ���ı仯��С
			if(size <= 3)
				size = 3;
		}
		/* actionPerformed��ActionListner�еķ�������д */
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			
			// ÿ�ζ�ʱ������ʱ����ÿ�����ݶ�����״̬
			for(Bubble bubble : bubbleList)
				bubble.update();
			repaint();	// �ػ�
		}
	}
	private class Bubble{
		// ����һЩ���ݵ����ԣ�����private
		private int x;	// x����
		private int y;	// y����
		private int size;	// ����ֱ��
		private Color color;	// ������ɫ
		// xspeed���ڴ洢ÿ�θ�����Ļʱ������ˮƽ�����ƶ�������������ʾˮƽ�ٶ�
		// yspeed���ڴ洢ÿ�θ�����Ļʱ�����ش�ֱ�����ƶ�������������ʾ��ֱ�ٶ�
		private int xspeed, yspeed;
		private final int MAX_SPEED = 5;
		public Bubble(int newX, int newY, int newSize) {
			x = newX;	// ��ʼ��x����
			y = newY;	// ��ʼ��y����
			size = newSize;	// ��ʼ������ֱ��
			color = new Color(rand.nextInt(256),
					rand.nextInt(256),
					rand.nextInt(256),
					rand.nextInt(256));		// �����ʼ��һ����ɫ͸������
			xspeed = yspeed = 3;	// ˮƽ��ֱ�ٶ���ͬ��ʹ���Ƶ����ݳ���ͬ�ķ���һ���˶�
		}
		// ����draw����һ��Graphics���͵Ĳ�������ʾ������
		public void draw(Graphics canvas) {
			canvas.setColor(color);	// ��������ɫ����Ϊ������ɫ
			canvas.fillOval(x - size/2, y - size/2, size, size);	// ������Բ��x��y��������Ӿ������Ͻǵ�����
		}
		// ��������״̬
		public void update() {
			x += xspeed;	// ���º����꣬����ˮƽ�ٶ�
			y += yspeed;	// ���������꣬����ˮƽ�ٶ�
			// ��Ե��⣬�ٶȷ��򣬱�ʾ��ײ����
			if(x - size/2 <= 0 || x + size/2 >= getWidth()) {
				xspeed = -xspeed;
			}
			if(y - size/2 <= 0 || y + size/2 >= getHeight()) {
				yspeed = -yspeed;
			}
		}
	}
	
}
