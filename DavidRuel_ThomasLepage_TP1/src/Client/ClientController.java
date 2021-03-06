package Client;

import java.io.IOException;

import Server.IServer;
import Server.ServerController;
import net.sf.lipermi.exception.LipeRMIException;
import net.sf.lipermi.handler.CallHandler;
import net.sf.lipermi.net.Client;


public class ClientController implements MyServerObserver
{
	View myView;
	
	ServerController serverController;
	IServer stub;
	private boolean isRunning = true;

	ClientController()
	{
		View view = new View(this);
		
		this.myView = view;
		
		CallHandler callHandler = new CallHandler();
		try 
		{		
			callHandler.registerGlobal(MyServerObserver.class, this);

			Client client = new Client("127.0.0.1", ServerController.SERVER_PORT, callHandler);
			stub = client.getGlobal(IServer.class);
			stub.registerObserver(this);
			synchronized (this)
			{
				while(isRunning)
				{}
				try 
				{
					Thread.sleep(1000);
				} 
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
				
				client.close();
			}
		}
		catch (LipeRMIException e) 
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	
	public static void main(String... args)
	{
		new ClientController();
	}

	public void restartGame()
	{
		System.out.println("restart 2");
		this.stub.restartGame();
	}
	
	public void resign(int i)
	{
		this.stub.resign(i);
	}
	
	
	public void addTokenToCol(int col)
	{
			this.stub.addTokenToCol(col);	
	}
	 
	@Override
	public void updatePlayerTurn(int playerNo) 
	{
		myView.updatePlayerTurn(playerNo);
	}

	@Override
	public void updateColFull(int col) 
	{
		myView.updateColFull(col);	
	}

	@Override
	public void updateMatchNul() 
	{
		myView.updateMatchNul();
	}

	@Override
	public void updateMatchWinBy(int playerNo) 
	{
		myView.updateMatchWinBy(playerNo);	
	}

	@Override
	public void updateTokens(int col, int row, int color) 
	{
		myView.updateTokens(col, row, color);	
	}

	@Override
	public void initBoard(int col, int row, int playerId)
	{
		this.myView.initBoard(col, row, playerId);
	}
	
	@Override
	public void updateClearBoard(int nbColumns, int nbRows)
	{
		this.myView.clearBoard(nbColumns, nbRows);
	}

	public void stopClient()
	{
		this.isRunning = false;
		System.exit(0);
	}
		
}
