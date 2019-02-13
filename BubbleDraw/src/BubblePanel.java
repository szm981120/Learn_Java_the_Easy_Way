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
	ArrayList<Bubble> bubbleList;	// �ÿɱ䳤�ȵ�ArrayList���洢Bubble����
	int size = 25;	// ����Ĭ��ֱ��
	// BubblePanel��Ĺ��캯��
	public BubblePanel() {
		bubbleList = new ArrayList<BubblePanel.Bubble>();	// ��ʼ���������������
		setBackground(Color.BLACK);	// ��������ɫΪ��ɫ
//		testBubbles();	// �������
		addMouseListener(new BubbleListner());	// ���÷���ָ��ʹ��BubbleListner������������¼�
		addMouseMotionListener(new BubbleListner());	// ���÷���ָ��ʹ��BubbleListner����������ƶ��¼�
		addMouseWheelListener(new BubbleListner());	// ���÷���ָ��ʹ��BubbleListner�������������¼�
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
	
	/*
	 * �������Ǵ����¼��������������¼��������������ڲ��಻ͬ�����Ǹ�Ӧ����Ҫ��Ӧ����������
	 * ����������ڲ��࣬ÿ���¼���Ҫ����������������������������������Ĵ�������ͬ�ģ�
	 * ��֮��������ķ�ʽ�������¼����������Ǹ��õ�ѡ��
	 * Ҫ�����¼����������������ļ���ͷ����һ����java.awt.event.*
	 * 
	 * ��дһ��˽���ڲ���BubbleListner����������¼�����չ�̳���MouseAdapter��
	 */
	private class BubbleListner extends MouseAdapter{
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
	}
	// �ڲ�������Bubble����Ϊprivate�����÷�װ���Է���������ֱ�ӷ�������
	private class Bubble{
		// ����һЩ���ݵ����ԣ�����private
		private int x;	// x����
		private int y;	// y����
		private int size;	// ����ֱ��
		private Color color;	// ������ɫ
		// public Bubble��Bubble��Ĺ��캯�������ù��캯����������ʱ�����ǳ�ʼ������
		public Bubble(int newX, int newY, int newSize) {
			x = newX;	// ��ʼ��x����
			y = newY;	// ��ʼ��y����
			size = newSize;	// ��ʼ������ֱ��
			color = new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));	// �����ʼ��һ����ɫ����
		}
		// ����draw����һ��Graphics���͵Ĳ�������ʾ������
		public void draw(Graphics canvas) {
			canvas.setColor(color);	// ��������ɫ����Ϊ������ɫ
			canvas.fillOval(x - size/2, y - size/2, size, size);	// ������Բ��x��y��������Ӿ������Ͻǵ�����
		}
	}
	
}
