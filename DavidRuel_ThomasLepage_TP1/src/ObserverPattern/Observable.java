package ObserverPattern;
import java.util.LinkedList;

public class Observable {

	private LinkedList<Observer> observers;
	
	public void addListener(Observer listener)
	{
		this.observers = new LinkedList<Observer>();
		this.observers.add(listener);
	}
	
	public void removeListener(Observer observer)
	{
		this.observers.remove(observer);
	}
	
	public void notifyChangeTurn(int turn)
	{
		for(Observer observer : this.observers)
		{
			observer.updatePlayerTurn(turn);
		}
	}
	
	public void notifyObserversTokenAdded(int x, int y, int color)
	{
		for(Observer observer : this.observers)
		{
			observer.updateTokens(x, y, color);
		}
	}
	
	public void notifyObserversColFull(int i)
	{
		for(Observer observer : this.observers)
		{
			observer.updateColFull(i);
		}
	}
	
	public void notifyMatchNul()
	{
		for(Observer observer : this.observers)
		{
			observer.updateMatchNul();
		}
	}
	
	public void notifyMatchWin(int i)
	{
		for(Observer observer : this.observers)
		{
			observer.updateMatchWinBy(i);
		}
	}
	
}
