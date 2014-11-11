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
	
	ClientController()
	{
		View view = new View(this);
		
		this.myView = view;
		
		CallHandler callHandler = new CallHandler();
		
		try 
		{
			callHandler.registerGlobal(IServer.class, this);
			
			Client client = new Client("127.0.0.1", ServerController.SERVER_PORT, callHandler);
			
			this.stub = client.getGlobal(IServer.class);
			
			this.stub.registerObserver(this);
			
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		catch (LipeRMIException e1) 
		{
			e1.printStackTrace();
		}
		
	}
	
	
	public static void main(String... args)
	{
		new ClientController();
	}
	
	
	
	public void restartGame()
	{
		this.stub.restartGame();
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

	
}
