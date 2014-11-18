package Client;

public interface MyServerObserver 
{

	public void updatePlayerTurn(int playerNo);
	
	public void updateColFull(int col);
	
	public void updateMatchNul();
	
	public void updateMatchWinBy(int playerNo);
	
	public void updateTokens(int col, int row, int color);
	
	public void initBoard(int col, int row, int playerId);
	
	public void updateClearBoard(int nbColumns, int nbRows);
	
}
