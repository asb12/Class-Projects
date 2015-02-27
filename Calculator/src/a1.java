import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

@SuppressWarnings("serial")
public class a1 extends JFrame implements ActionListener, KeyListener
{
	private JTextField textField;
	private JPanel buttonPanel;
	private JPanel clearPanel;
	private JPanel textFieldPanel;
	private double result;
	double curTotal;
	private char com;
	boolean test=false;

	public a1()
	{

		textFieldPanel=new JPanel();
		textFieldPanel.setLayout(new GridLayout(1,1));
		textField=new JTextField("0");
		textFieldPanel.add(textField);
		Font font1 = new Font("SansSerif", Font.BOLD, 20);
		textField.setFont(font1);	
		textField.setFocusable(false);

		clearPanel = new JPanel();
		clearPanel.setLayout(new GridLayout(1,1));
		addClearButton("CE");
		addClearButton("<==");
		buttonPanel=new JPanel();
		buttonPanel.setLayout(new GridLayout(4,4,2,3)); 
		addButton("7");
		addButton("8");
		addButton("9");
		addButton("/");
		addButton("4");
		addButton("5");
		addButton("6");
		addButton("*");
		addButton("1");
		addButton("2");
		addButton("3");
		addButton("-");
		addButton(".");
		addButton("0");		
		addButton("=");
		addButton("+");		
		setTitle("Calculator");

		Dimension gap = new Dimension(0,5);
		JPanel main = new JPanel();
		main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
		main.add(Box.createRigidArea(gap));
		main.add(textFieldPanel);
		main.add(Box.createRigidArea(gap));
		main.add(clearPanel);
		main.add(Box.createRigidArea(gap));
		main.add(buttonPanel);
		main.add(Box.createRigidArea(gap));
		this.add(main);
		this.pack();
	}	
	void addClearButton(String type)
	{
		JButton button=new JButton(type);
		button.addActionListener(this);
		button.addKeyListener(this);
		clearPanel.add(button);
	}

	void addButton(String type)
	{
		JButton button=new JButton(type);
		button.addActionListener(this);
		button.addKeyListener(this);
		buttonPanel.add(button);
	}


	public void actionPerformed (ActionEvent event)
	{
		String command = event.getActionCommand();
		char value;
		value = command.charAt(0);
		if(command.equals("CE"))
		{
			textField.setText("0");
		}

		else if(command.equals("<=="))
		{
			if(textField.getText().length() > 0)
				textField.setText(textField.getText().substring(0, textField.getText().length()-1));
				
		}
		else if( Character.isDigit(value) || value == '.')
		{
			if(test){
				result = Double.parseDouble(textField.getText());
				textField.setText("");
				test = false;
			}

			textField.setText(textField.getText() + value);
		}
		else {
			//if(Character.isDigit(value))
			
			calculate(value);
		}
	}

	private void calculate(char value)
	{
		
		double total = 0;	    

		curTotal = Double.parseDouble(textField.getText());

		if( com == 0 ) {
			test = true;
			com = value;
		}
		else
		{
			switch(com)
			{
			case '/':
				total = result / curTotal;
				break;
			case '*':
				total = result * curTotal;
				break;
			case '-':
				total = result - curTotal;
				break;
			case '+':
				total = result + curTotal;
				break;                  
			}

			textField.setText(""+ total);
			test = true;

			if(value == '=') {
				com = 0;
			}
			else {
				com = value;
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent key) {

		char value;
		value = key.getKeyChar();
		if(key.getKeyCode() == KeyEvent.VK_BACK_SPACE)
		{
			if(textField.getText().length() > 0)
				textField.setText(textField.getText().substring(0, textField.getText().length()-1));
		}
		else if( Character.isDigit(value) || value == '.')
		{
			if(test){
				result = Double.parseDouble(textField.getText());
				textField.setText("");
				test = false;
			}

			textField.setText(textField.getText() + value);
		}
		else {
			//if(!(command.matches("[a-z]+")))
				if(value == '+' || value == '-' || value == '/' || value == '*' || value == '=')
				calculate(value);		
	}
	}
	@Override
	public void keyReleased(KeyEvent key) {
		// TODO Auto-generated method stub

	}
	@Override
	public void keyTyped(KeyEvent key) {
	
	}
	public static void main(String args[])
	{

		a1 calc = new a1();
		calc.setVisible(true);
	}
}