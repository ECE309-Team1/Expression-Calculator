//Testing Jarvik's Commit
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ExpressionCalculator implements ActionListener {

	// GUI declarations
JFrame calcWindow = new JFrame();	
JLabel errorLabelField = new JLabel(); // displays errors here
JTextField inputArea = new JTextField("Input goes here");	// numerical input here
JTextField forX = new JTextField("for X =");
JTextField toX = new JTextField("to X =");
JTextArea totalDisplay = new JTextArea(); // displays total
JTextArea logDisplay = new JTextArea();
ButtonGroup group = new ButtonGroup();
JRadioButton accumulatorMode = new JRadioButton("Accumulator");
JRadioButton expressionMode = new JRadioButton("Expression");
JRadioButton testMode = new JRadioButton("Test");
JRadioButton graphMode = new JRadioButton("Graph");    
JButton clearButton = new JButton("Clear"); // clear button
JScrollPane logScrollPane = new JScrollPane(logDisplay);
// Construct the Accumulating Calculator and that's it.
JPanel topPanel = new JPanel();
JPanel centerPanel = new JPanel();
JPanel bottomPanel = new JPanel();

	
	public static void main(String[] args) 
	{
		ExpressionCalculator ec = new ExpressionCalculator();
	}
	
	public ExpressionCalculator() // constructor 
	{
		// GUI build
		
				//topPanel.setSize(100,100);
				topPanel.setLayout(new GridLayout(2,1));
				centerPanel.setLayout(new GridLayout(2,1));
				bottomPanel.setLayout(new GridLayout(2,2));
				
				
				
				topPanel.add(errorLabelField);
				topPanel.add(totalDisplay);
				
				centerPanel.add(logScrollPane);
				centerPanel.add(inputArea);
				
				group.add(accumulatorMode);
				group.add(expressionMode);
				group.add(testMode);
				group.add(graphMode);
				accumulatorMode.setSelected(true);
			
				bottomPanel.add(accumulatorMode);
				bottomPanel.add(expressionMode);
				bottomPanel.add(testMode);
				bottomPanel.add(graphMode);
				
				bottomPanel.add(forX);
				bottomPanel.add(toX);
				bottomPanel.add(clearButton);
				
				clearButton.setBackground(Color.ORANGE);
				errorLabelField.setForeground(Color.BLUE);
				
				calcWindow.getContentPane().add(topPanel,"North");
				calcWindow.getContentPane().add(centerPanel, "Center");
				calcWindow.getContentPane().add(bottomPanel, "South");
				
				calcWindow.setTitle("Expression Calculator");
				
				totalDisplay.setEditable(false);
				logDisplay.setEditable(false);
				totalDisplay.setFont (new Font("default",Font.BOLD,14));
				logDisplay.setFont (new Font("default",Font.BOLD,14));
				inputArea.setFont (new Font("default",Font.BOLD,14));
				toX.setFont (new Font("default",Font.BOLD,14));
				forX.setFont (new Font("default",Font.BOLD,14));
				calcWindow.setFont(new Font("default", Font.BOLD, 16));
				totalDisplay.setLineWrap(true);
				logDisplay.setLineWrap(true);
				
				
				totalDisplay.setWrapStyleWord(true);
				logDisplay.setWrapStyleWord(true);
				
				
				calcWindow.setSize(850,850);
				calcWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				calcWindow.setVisible(true);
				
				
				clearButton.addActionListener(this);
				graphMode.addActionListener(this);
				testMode.addActionListener(this);
				expressionMode.addActionListener(this);
				accumulatorMode.addActionListener(this);
				// Making the input field as an action listener
				inputArea.addActionListener(this);
				forX.addActionListener(this);
				toX.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent ae)
	{
		errorLabelField.setText("");
		
		// clear button implementation
		if (ae.getSource() == clearButton)
		{
			inputArea.setText("");
			totalDisplay.setText("");
			errorLabelField.setText("");
		}
		if (ae.getSource() == accumulatorMode )
		{
			System.out.println("Accumulator Mode was Selected");
		}
		if (ae.getSource() == expressionMode )
		{
			System.out.println("Expression Mode was Selected");
		}
		if (ae.getSource() == testMode )
		{
			System.out.println("Test Mode was Selected");
		}
		if (ae.getSource() == graphMode )
		{
			System.out.println("Graph Mode was Selected");
		}
	}

	

}
