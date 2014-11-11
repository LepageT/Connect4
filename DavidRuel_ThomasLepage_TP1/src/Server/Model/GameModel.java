package Server.Model;

import java.util.LinkedList;

import Client.MyServerObserver;
import ObserverPattern.Observable;

public class GameModel extends Observable
{
	private Token[][] tokens;
	
	private int winConditionSequence;
	
	private int width;
	private int height;
	
	private int turn;
	
	private LinkedList<MyServerObserver> observers;

	public GameModel(int r, int c, int v) throws Exception
	{
		this.observers = new LinkedList<MyServerObserver>();

		if(c < 2)
		{
			throw new Exception("Le nombre de colones doit être supérieur à 1.");
		}
		if(r < 2)
		{
			throw new Exception("Le nombre de rangées doit être supérieur à 1.");
		}
		if(v < 3)
		{
			throw new Exception("La condition pour gagner doit être supérieur à 2.");
		}
		this.tokens = new Token[c][r];
		
		this.height = r;
		this.width = c;
		this.winConditionSequence = v;

		this.turn = 1;
	}
	
	public void registerObserver(MyServerObserver observer)
	{
		this.observers.add(observer);
	}
	
	public void addToken(int col)
	{
		if(col < this.width && col >= 0)
		{	
			Token token = new Token(this.turn);
			int row = this.addTokenToCol(token, col);
			
			notifyObserversTokenAdded(col, row, this.turn);
			
			if(this.checkWin(col, row, token))
			{
				notifyMatchWin(this.turn);		
			}
			else
			{
				this.turn = this.turn==1?2:1;
			}
			
			if(this.checkBoardFull())
			{
				notifyMatchNul();
			}
			notifyChangeTurn(this.turn);
			
			if(row >= this.height - 1)
			{
				notifyObserversColFull(col);
			}
		}
		else
		{
			throw new IndexOutOfBoundsException();
		}

	}
	
	private int addTokenToCol(Token token, int col)
	{
		int row = 0;
		
		for(int i = 0; i < this.height; i++)
		{
			if(this.tokens[col][i] == null)
			{
				this.tokens[col][i] = token;
				row = i;
				return row;
			}
		}
		
		return row;
	}
	
	private boolean checkWin(int col, int row, Token t)
	{
		int lineSize = countLineSize(col, row, Direction.B, t); //Verticale
		if(lineSize >= this.winConditionSequence)
		{
			return true;
		}
		
		lineSize = countLineSize(col, row, Direction.D, t) + countLineSize(col - 1, row, Direction.G, t); //Horizontale
		if(lineSize >= this.winConditionSequence)
		{
			return true;
		}
		
		lineSize = countLineSize(col, row, Direction.HG, t) + countLineSize(col + 1, row - 1, Direction.BD, t); //Diagonale \
		if(lineSize >= this.winConditionSequence)
		{
			return true;
		}
		
		lineSize = countLineSize(col, row, Direction.HD, t) + countLineSize(col - 1, row - 1, Direction.BG, t); //Diagonale /
		if(lineSize >= this.winConditionSequence)
		{
			return true;
		}
		
		return false;
	}
	
	private int countLineSize(int col, int row, Direction direction, Token t)
	{
		if(this.positionInBoard(col, row))
		{
			if(this.tokens[col][row] != null)
			{
				if(this.tokens[col][row].getId() == t.getId())
				{
					switch (direction)
					{
						case B:
							return 1 + countLineSize(col, row - 1, direction, t);	
						case D:
							return 1 + countLineSize(col + 1, row, direction, t);
						case G:
							return 1 + countLineSize(col - 1, row, direction, t);
						case HD:
							return 1 + countLineSize(col + 1, row + 1, direction, t);
						case HG:
							return 1 + countLineSize(col - 1, row + 1, direction, t);
						case BD:
							return 1 + countLineSize(col + 1, row - 1, direction, t);
						case BG:
							return 1 + countLineSize(col - 1, row - 1, direction, t);
						default:
							return 0;
					}
				}
				else
				{
					return 0;
				}
			}
			else
			{
				return 0;
			}
		}
		else
		{
			return 0;
		}
	}
	 
	private boolean positionInBoard(int col, int row)
	{
		if(col < this.width && col > -1 && row < this.height  && row > -1)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public Token getToken(int col, int row)
	{
		if(col > this.width || row > this.height)
		{
			throw new ArrayIndexOutOfBoundsException();
		}
		return this.tokens[col][row];
	}
	
	private boolean checkBoardFull()
	{
		for(int i =0; i < this.width; i++)
		{
			for(int j = 0; j < this.height; j++)
			{
				if(this.tokens[i][j] == null)
				{
					return false;
				}
			}
		}
		
		return true;
	}
	
	public int getWidth()
	{
		return this.width;
	}
	
	public int getHeight()
	{
		return this.height;
	}
	
	public int getWinCondition()
	{
		return this.winConditionSequence;
	}
}
