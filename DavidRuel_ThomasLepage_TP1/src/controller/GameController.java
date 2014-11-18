package controller;

import Server.Model.GameModel;
import Client.View;

public class GameController
{
	private GameModel game;
	private View view;
	private GameController(int r, int c, int v)
	{
		this.startGame(r, c, v);
	}
	
	public void addTokenToCol(int col)
	{
		this.game.addToken(col);
	}
	
	public static void main(String[] args)
	{
		int row = 6;
		int col = 7;
		int vic = 4;
		if(args.length >= 3)
		{
			row = Integer.parseInt(args[0])>=0?Integer.parseInt(args[0]):6;
			col = Integer.parseInt(args[1])>=0?Integer.parseInt(args[1]):7;
			vic = Integer.parseInt(args[2])>=0?Integer.parseInt(args[2]):4;
		}

		@SuppressWarnings("unused")
		GameController controller = new GameController(row,col,vic);
	}
	
	public void startGame(int r, int c, int v)
	{
		try
		{
			this.game = new GameModel(r, c, v);
			if(this.view != null)
			{
				this.view.dispose();
			}
			
			//this.view = new View(this);
			this.view.initBoard(r, c);
			
			//this.game.addListener(this.view);
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		
	}
	
	public void restartGame()
	{
		this.startGame(this.game.getWidth(), this.game.getHeight(), this.game.getWinCondition());
	}
}
