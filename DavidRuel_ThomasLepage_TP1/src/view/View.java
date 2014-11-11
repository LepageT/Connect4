package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controller.GameController;
import ObserverPattern.Observer;

public class View extends JFrame implements Observer
{
	private static final String IMAGE_PATH = "./IMAGES/Connect4/";

	private static final long serialVersionUID = 1L;

	private JButton[] controlButtons;

	private MyImageContainer[][] placeHolders;

	private final JTextField message = new JTextField(20);
	private final JPanel centerPane = new JPanel();
	
	private int playerTurn;
	
	ImageIcon red;
	ImageIcon green;
	
	private GameController controller;

	public View(GameController controller)
	{
		this.red = new ImageIcon(IMAGE_PATH + "red.jpg");
		this.green = new ImageIcon(IMAGE_PATH + "green.jpg");

		this.controller = controller;
		
		this.setTitle("Connect4");

		this.configureWindow();

		this.setLayout(new BorderLayout());
		JPanel panelNorth = new JPanel();
		panelNorth.setLayout(new FlowLayout());
		panelNorth.add(this.message);
		this.message.setEditable(false);
		this.message.setText("Bienvenue!");
		this.message.setHorizontalAlignment(JTextField.CENTER);
		this.add(panelNorth, BorderLayout.NORTH);
		this.createMenu();
		this.setVisible(true);
	}
	
	private void createMenu()
	{
		JMenuBar menuBar = new JMenuBar();
		JMenu gameMenu = new JMenu("Jeu");
		JMenuItem resignMenuItem = new JMenuItem("Abandon");
		JMenuItem restartMenuItem = new JMenuItem("Recommencer");
		resignMenuItem.addActionListener(new ResignActionHandler());
		restartMenuItem.addActionListener(new RestartActionHandler());
		gameMenu.add(resignMenuItem);
		gameMenu.add(restartMenuItem);
		menuBar.add(gameMenu);

		JMenu helpMenu = new JMenu("Aide");
		JMenuItem aboutMenuItem = new JMenuItem("À propos");
		aboutMenuItem.addActionListener(new AboutActionHandler());
		helpMenu.add(aboutMenuItem);
		menuBar.add(helpMenu);

		this.setJMenuBar(menuBar);
	}

	public void initBoard(int nbRows, int nbColumns)
	{
		this.centerPane.removeAll();
		this.placeHolders = new MyImageContainer[nbRows][nbColumns];
		this.controlButtons = new JButton[nbColumns];

		centerPane.setLayout(new GridLayout(nbRows + 1, nbColumns));

		ImageIcon imgIcon = new ImageIcon(IMAGE_PATH + "arrow.png");
		
		Image img = imgIcon.getImage();  
		Image newimg = img.getScaledInstance(50, 50,  java.awt.Image.SCALE_SMOOTH);  
		imgIcon = new ImageIcon(newimg);  
		for (int i = 0; i < nbColumns; i++)
		{
			JButton button = new JButton(imgIcon);
			this.controlButtons[i] = button;
			button.setOpaque(false);
			button.setContentAreaFilled(false);
			button.setBorderPainted(false);
			button.addActionListener(new ButtonHandler(i));
			centerPane.add(button);
		}

		for (int row = nbRows - 1; row >= 0; row--)
		{
			for (int column = 0; column < nbColumns; column++)
			{
				MyImageContainer button = new MyImageContainer();
				button.setOpaque(true);
				placeHolders[row][column] = button;
				centerPane.add(button);
			}
		}
		this.add(centerPane, BorderLayout.CENTER);
		this.revalidate();
	}

	private void configureWindow()
	{
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenSize = toolkit.getScreenSize();
		;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(((screenSize.width * 3) / 6), ((screenSize.height * 4) / 7));
		setLocation(((screenSize.width - getWidth()) / 2), ((screenSize.height - getHeight()) / 2));
	}

	private class ButtonHandler implements ActionListener
	{
		private final int columnIndex;

		private ButtonHandler(int columnIndex)
		{
			this.columnIndex = columnIndex;
		}

		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			System.out.println("Action on button: " + columnIndex);
			controller.addTokenToCol(this.columnIndex);
		}
	}

	private class ResignActionHandler implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			System.out.println("Action on menu");
			int click = JOptionPane.showConfirmDialog(null, "Joueur " + playerTurn + " voulez-vous vraiment abandonner?","Abandon",JOptionPane.YES_NO_OPTION);
			if(click == JOptionPane.YES_OPTION)
			{
				updateMatchWinBy(playerTurn==1?2:1);
			}
		}
	}
	
	private class RestartActionHandler implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			int click = JOptionPane.showConfirmDialog(null, "Voulez-vous recommencer?","Recommencer?",JOptionPane.YES_NO_OPTION);
			if(click == JOptionPane.YES_OPTION)
			{
				controller.restartGame();
			}
		}
	}

	private class AboutActionHandler implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			JOptionPane.showMessageDialog(View.this, "GUI for Connect4\n420-520-SF TP1\n\nAuthor: François Gagnon", "About", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	@Override
	public void updatePlayerTurn(int turn) {
		// TODO Auto-generated method stub
		playerTurn = turn;
		this.message.setText("Tour du joueur " + turn);
	}

	@Override
	public void updateTokens(int col, int row, int color) {
		// TODO Auto-generated method stub
		((MyImageContainer) placeHolders[row][col]).setImageIcon(color==1?this.green:this.red);
	}

	@Override
	public void updateColFull(int i) {
		// TODO Auto-generated method stub
		this.controlButtons[i].setEnabled(false);
	} 
	

	@Override
	public void updateMatchNul() 
	{
		//JOptionPane.showMessageDialog(null, "Match nul");
		
		int click = JOptionPane.showConfirmDialog(null, "Partie nulle. Voulez-vous recommencer?","Partie nulle",JOptionPane.YES_NO_OPTION);
		if(click == JOptionPane.YES_OPTION)
		{
			this.controller.restartGame();
		}
		else
		{
			this.dispose();
		}
	}

	@Override
	public void updateMatchWinBy(int i)
	{
		Object[] options = {"Recommencer","Quitter"};
		int click = JOptionPane.showOptionDialog(null, "Joueur " + i + " à gagné!!!","Partie terminée.",JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE,null,options,options[0]);
		if(click == JOptionPane.YES_OPTION)
		{
			this.controller.restartGame();
		}
		else
		{
			this.dispose();
		}
	}
	


}
