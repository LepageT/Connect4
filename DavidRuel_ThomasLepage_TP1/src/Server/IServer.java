package Server;

import root.client.MyServerObserver;

public interface IServer
{
	public void restartGame();
	public void addTokenToCol(int col);
	public void resign();
	public void registerObserver(MyServerObserver observer);

}
