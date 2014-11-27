package Client;

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

public class View extends JFrame
{
	private static final String IMAGE_PATH = "./IMAGES/Connect4/";

	private static final long serialVersionUID = 1L;

	private JButton[] controlButtons;

	private MyImageContainer[][] placeHolders;

	private final JTextField message = new JTextField(20);
	private final JPanel centerPane = new JPanel();
	
	private int playerTurn = 1;
	private int playerId;
	
	ImageIcon red;
	ImageIcon green;
	
	//private GameController controller;
	private ClientController controller;

	public View(ClientController controller)
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

	public void initBoard(int nbColumns, int nbRows, int playerId)
	{
		this.playerId = playerId;
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
	
	public void clearBoard(int nbColumns, int nbRows)
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
			if(playerId < 3)
			{
				if(playerId == playerTurn)
				{
					controller.addTokenToCol(this.columnIndex);
				}
				else 
				{
					JOptionPane.showMessageDialog(View.this, "Ce n'est pas a votre tour de jouer.", "Attention", JOptionPane.INFORMATION_MESSAGE);
				}
			}
			else 
			{
				JOptionPane.showMessageDialog(View.this, "Vous êtes un simple spectateur. Vous ne pouvez pas jouer.", "Attention", JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}

	private class ResignActionHandler implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			if(playerId < 3)
			{
				System.out.println("Action on menu");
				int click = JOptionPane.showConfirmDialog(null, "Joueur " + playerId + " voulez-vous vraiment abandonner?","Abandon",JOptionPane.YES_NO_OPTION);
				if(click == JOptionPane.YES_OPTION)
				{
					controller.resign(playerId==1?2:1);
				}
			}
			else 
			{
				JOptionPane.showMessageDialog(View.this, "Vous ne pouvez pas faire cette action.", "Attention", JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}
	
	private class RestartActionHandler implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent arg0)
		{			
			if(playerId < 3)
			{				
				System.out.println("Action on menu");
				int click = JOptionPane.showConfirmDialog(null, "Voulez-vous recommencer?","Recommencer?",JOptionPane.YES_NO_OPTION);
				if(click == JOptionPane.YES_OPTION)
				{
					System.out.println("restart");
					controller.restartGame();
				}	
			}
			else 
			{
				JOptionPane.showMessageDialog(View.this, "Vous ne pouvez pas faire cette action.", "Attention", JOptionPane.INFORMATION_MESSAGE);
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

	public void updatePlayerTurn(int turn) 
	{
		playerTurn = turn;
		this.message.setText("Tour du joueur " + turn);
	}

	public void updateTokens(int col, int row, int color)
	{
		((MyImageContainer) placeHolders[row][col]).setImageIcon(color==1?this.green:this.red);
	}

	public void updateColFull(int i) 
	{
		this.controlButtons[i].setEnabled(false);
	} 
	
	public void updateMatchNul() 
	{		
		int click = JOptionPane.showConfirmDialog(null, "Partie nulle. Voulez-vous recommencer?","Partie nulle",JOptionPane.YES_NO_OPTION);
		restartOptionPane(click);
	}

	public void updateMatchWinBy(int i)
	{			

		if(playerId < 3)
		{				
			int click = JOptionPane.showConfirmDialog(null, "Voulez-vous recommencer?","Recommencer?",JOptionPane.YES_NO_OPTION);
			restartOptionPane(click);
		}
		
	}
	
	public void restartOptionPane(int click)
	{
		if(click == JOptionPane.YES_OPTION)
		{
			System.out.println("restart");
				controller.restartGame();
		}
		else
		{
			this.dispose();
			controller.stopClient();
		}	
	}
	
	
	public int getPlayerTurn() {
		return playerTurn;
	}


}
