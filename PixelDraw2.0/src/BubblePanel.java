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
import javax.swing.event.ChangeEvent;

public class BubblePanel extends JPanel {
	Random rand = new Random();
	ArrayList<Bubble> bubbleList;
	int size = 25;
	javax.swing.Timer timer;
	int delay = 33;
	JSlider slider;
	public BubblePanel() {
		timer = new javax.swing.Timer(delay, new BubbleListner());
		bubbleList = new ArrayList<BubblePanel.Bubble>();
		setBackground(Color.BLACK);
		
		JPanel panel = new JPanel();
		add(panel);
		
		JButton btnClear = new JButton("\u6E05\u7A7A");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bubbleList = new ArrayList<BubblePanel.Bubble>();
				repaint();
			}
		});
		
		JLabel lblNewLabel = new JLabel("\u52A8\u753B\u901F\u5EA6");
		panel.add(lblNewLabel);
		
		slider = new JSlider();
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				int speed = slider.getValue() + 1;
				int delay = 1000 / speed;
				timer.setDelay(delay);
			}
		});
		slider.setValue(30);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		slider.setMinorTickSpacing(5);
		slider.setMajorTickSpacing(30);
		slider.setMaximum(120);
		panel.add(slider);
		panel.add(btnClear);
		
		JButton btnPause = new JButton("\u6682\u505C");
		btnPause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JButton btn = (JButton)e.getSource();
				if(btn.getText().equals("ÔÝÍ£")) {
					timer.stop();
					btn.setText("¿ªÊ¼");
				}else {
					timer.start();
					btn.setText("ÔÝÍ£");
				}
			}
		});
		panel.add(btnPause);
//		testBubbles();
		addMouseListener(new BubbleListner());
		addMouseMotionListener(new BubbleListner());
		addMouseWheelListener(new BubbleListner());
		timer.start();
	}
	public void paintComponent(Graphics canvas) {
		super.paintComponent(canvas);
		for(Bubble b : bubbleList) {
			b.draw(canvas);
		}
	}
	/*
	 * ²âÊÔ´úÂë
	 * public void testBubbles() {
		for(int n=0; n<100; n++) {
			int x = rand.nextInt(600);
			int y = rand.nextInt(400);
			int size = rand.nextInt(50);
			bubbleList.add(new Bubble(x, y, size));
		}
		repaint();
		}
	 */
	private class BubbleListner extends MouseAdapter implements ActionListener{
		public void mousePressed(MouseEvent event) {
			bubbleList.add(new Bubble(event.getX(), event.getY(), size));
			repaint();
		}
		public void mouseDragged(MouseEvent event) {
			bubbleList.add(new Bubble(event.getX(), event.getY(), size));
			repaint();
		}
		public void mouseWheelMoved(MouseWheelEvent event) {
			size += event.getUnitsToScroll();
			if(size <= 3)
				size = 3;
		}
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			for(Bubble bubble : bubbleList)
				bubble.update();
			repaint();
		}
	}
	private class Bubble{
		private int x;
		private int y;
		private int size;
		private Color color;
		private int xspeed, yspeed;
		private final int MAX_SPEED = 5;
		public Bubble(int newX, int newY, int newSize) {
			x = (newX / newSize) * newSize + newSize/2;
			y = (newY / newSize) * newSize + newSize/2;
			size = newSize;
			color = new Color(rand.nextInt(256),
					rand.nextInt(256),
					rand.nextInt(256),
					rand.nextInt(256));
			xspeed = yspeed = 4;
		}
		public void draw(Graphics canvas) {
			canvas.setColor(color);
			canvas.fillRect(x - size/2, y - size/2, size, size);
		}
		public void update() {
			x += xspeed;
			y += yspeed;
			if(x - size/2 <= 0 || x + size/2 >= getWidth()) {
				xspeed = -xspeed;
			}
			if(y - size/2 <= 0 || y + size/2 >= getHeight()) {
				yspeed = -yspeed;
			}
		}
	}
	
}
