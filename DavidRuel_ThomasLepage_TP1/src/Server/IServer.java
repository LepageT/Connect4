package Server;

import Client.MyServerObserver;

public interface IServer
{
	public void restartGame();
	public void addTokenToCol(int col);
	public void resign(int i);
	public void registerObserver(MyServerObserver observer);

}
