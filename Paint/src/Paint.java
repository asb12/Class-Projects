import java.awt.*;
import java.awt.event.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.*;
import java.io.*;

@SuppressWarnings("serial")
public class Paint extends JPanel {
	int stroke;
	private enum Tool {pencil, LINE,ERASE ,BRUSH}
	private Tool line = Tool.LINE;
	private Tool currentTool = Tool.pencil;
	private Color currentColour = Color.BLACK;
	private Color fill = Color.WHITE;
	private BufferedImage Image;
	private boolean mouseHold;
	private int startX, startY;
	private int cX, cY;
	JLabel label;

	public Paint() {
		setPreferredSize(new Dimension(1280,720));
		MouseHandler mouseHandler = new MouseHandler();
		addMouseListener(mouseHandler);
		addMouseMotionListener(mouseHandler);
		ActionListener listener = new MenuHandler();
		JToolBar toolbar = new JToolBar();
		ImageIcon icon1 = new ImageIcon(getClass().getResource("8.png"));
		ImageIcon icon2 = new ImageIcon(getClass().getResource("2.png"));
		ImageIcon icon3 = new ImageIcon(getClass().getResource("4.png"));
		ImageIcon icon4 = new ImageIcon(getClass().getResource("9.png"));
		ImageIcon icon5 = new ImageIcon(getClass().getResource("5.png"));

		JButton Button5 = new JButton(icon1);	
		JButton Button1 = new JButton(icon2);
		JButton Button4 = new JButton(icon3);
		JButton Button2 = new JButton(icon4);
		JButton Button3 = new JButton(icon5);

		Button1.setText("Brush");
		Button4.setText("Erase");
		Button3.setText("Pencil");
		Button5.setText("Colour");
		Button2.setText("Line");

		Button1.addActionListener(listener);
		Button2.addActionListener(listener);
		Button3.addActionListener(listener);
		Button4.addActionListener(listener);
		Button5.addActionListener(listener);

		Button1.setVerticalTextPosition(AbstractButton.BOTTOM);
		Button2.setVerticalTextPosition(AbstractButton.BOTTOM);
		Button3.setVerticalTextPosition(AbstractButton.BOTTOM);
		Button4.setVerticalTextPosition(AbstractButton.BOTTOM);
		Button5.setVerticalTextPosition(AbstractButton.BOTTOM);

		toolbar.add(Button1);
		toolbar.add(Button2);
		toolbar.add(Button3);
		toolbar.add(Button4);
		toolbar.add(Button5);			

		add(toolbar, BorderLayout.NORTH);
	}

	public void paintComponent(Graphics g) {
		if (Image == null)
			createImage();

		g.drawImage(Image,0,0,null);

		if (mouseHold && line.equals(currentTool)) {
			g.setColor(currentColour);
			putCurrentShape(g);
		}
	}

	private void createImage() {
		Image = new BufferedImage(getWidth(),getHeight(),BufferedImage.TYPE_INT_RGB);
		Graphics g = Image.getGraphics();
		g.setColor(fill);
		g.fillRect(0,0,getWidth(),getHeight());
		g.dispose();
	}

	private void putCurrentShape(Graphics g) {
		g.drawLine(startX, startY, cX, cY);
	}

	private void paintLine(int x1, int y1, int x2, int y2) {
		if (x2 < x1) {
			int temp = x1;
			x1 = x2;
			x2 = temp;
		}
		if (y2 < y1) {
			int temp = y1;
			y1 = y2;
			y2 = temp;
		}
		x1--;
		x2++;
		y1--;
		y2++;
		repaint(x1,y1,x2-x1,y2-y1);
	}

	public JMenuBar getMenuBar() {

		JMenuBar menubar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		JMenu toolMenu = new JMenu("Tool");
		toolMenu.setSelected(true);
		menubar.add(fileMenu);
		menubar.add(toolMenu);

		ActionListener listener = new MenuHandler();
		JMenuItem item;
		item = new JMenuItem("New");
		item.addActionListener(listener);
		fileMenu.add(item);
		fileMenu.addSeparator();	
		item = new JMenuItem("Open");
		item.addActionListener(listener);
		fileMenu.add(item);
		fileMenu.addSeparator();
		item = new JMenuItem("Exit");
		item.addActionListener(listener);
		fileMenu.add(item);

		return menubar;
	}

	private class MenuHandler implements ActionListener {
		public void actionPerformed(ActionEvent evt) {
			String command = evt.getActionCommand();
			if (command.equals("Colour")) {
				Color newColour = JColorChooser.showDialog(Paint.this, 
						"Select Drawing Color", currentColour);
				if (newColour != null)
					currentColour = newColour;
			}

			else if (command.equals("Pencil"))
				currentTool = Tool.pencil;
			else if (command.equals("Line"))
				currentTool = Tool.LINE;
			else if (command.equals("Erase"))
				currentTool = Tool.ERASE;
			else if(command.equals("Brush"))
			{
				currentTool=Tool.BRUSH;
				stroke=2;
			}
			else if (command.equals("New"))
			{ 

				fill = Color.WHITE;
				Graphics g = Image.getGraphics();
				g.setColor(fill);
				g.fillRect(0,0,Image.getWidth(),Image.getHeight());
				g.dispose();
				Paint.this.repaint();
			}
			else if (command.equals("Exit"))
				System.exit(0);

			else if(command.equals("Open"))
			{
				JFileChooser fc = new JFileChooser();
				int result = fc.showOpenDialog(null);
				if (result == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					try {
						label.setIcon(new ImageIcon(ImageIO.read(file)));
					} catch (IOException e) {
						e.printStackTrace();
					}
				}			  
			}
		}

	} 

	private class MouseHandler implements MouseListener, MouseMotionListener {

		int pX, pY;  // Previous position of mouse during a drag.

		void applyToolAlongLine(int x1, int y1, int x2, int y2) {
			Graphics g = Image.getGraphics();
			g.setColor(fill);
			int dist = Math.max(Math.abs(x2-x1),Math.abs(y2-y1));
			double dx = (double)(x2-x1)/dist;
			double dy = (double)(y2-y1)/dist;
			for (int d = 1; d <= dist; d++) {
				int x = (int)Math.round(x1 + dx*d);
				int y = (int)Math.round(y1 + dy*d);
				if (currentTool == Tool.ERASE) {
					g.fillRect(x-5,y-5,10,10);
					repaint(x-5,y-5,10,10);
				}
			}
			g.dispose();
		}

		public void mousePressed(MouseEvent evt) {
			startX = pX = cX = evt.getX();
			startY = pY = cY = evt.getY();
			mouseHold = true;
			if (currentTool == Tool.ERASE) {
				Graphics g = Image.getGraphics();
				g.setColor(fill);
				g.fillRect(startX-5,startY-5,10,10);
				g.dispose();
				repaint(startX-5,startY-5,10,10);
			}
		}

		public void mouseDragged(MouseEvent evt) {
			cX = evt.getX();
			cY = evt.getY();
			if (currentTool == Tool.pencil) {
				Graphics g = Image.getGraphics();
				g.setColor(currentColour);
				g.drawLine(pX,pY,cX,cY);
				g.dispose();
				paintLine(pX,pY,cX,cY);
			}
			else if (line.equals(currentTool)) {
				paintLine(startX,startY,pX,pY);
				paintLine(startX,startY,cX,cY);
			}

			else if(currentTool==Tool.BRUSH)
			{
				Graphics g = Image.getGraphics();
				Graphics2D gc= (Graphics2D) g;
				gc.setColor(currentColour);
				g.setColor(currentColour);
				gc.setStroke(new BasicStroke(8));
				gc.drawLine(pX,pY,cX,cY);
				g.dispose();
				paintLine(pX,pY,cX,cY);

			}

			else {
				applyToolAlongLine(pX,pY,cX,cY);
			}
			pX = cX;
			pY = cY;
		}

		public void mouseReleased(MouseEvent evt) {
			mouseHold = false;
			if (line.equals(currentTool)) {
				Graphics g = Image.getGraphics();
				g.setColor(currentColour);
				putCurrentShape(g);
				g.dispose();
				repaint();
			}
		}

		public void mouseMoved(MouseEvent evt) { }
		public void mouseClicked(MouseEvent evt) { }
		public void mouseEntered(MouseEvent evt) { }
		public void mouseExited(MouseEvent evt) { }

	} 

	public static void main(String[] args) 
	{
		JFrame  window = new JFrame("Paint");
		Paint content = new Paint();
		window.setContentPane(content);
		window.setJMenuBar(content.getMenuBar());
		window.pack();  
		window.setResizable(true); 
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
	}
}
