package ObserverPattern;

public interface Observer {

	public void update();
	public void updateTokens(int col, int row, int color);
	public void updateColFull(int i);
	public void updateMatchNul();
	public void	updateMatchWinBy(int i);
}
