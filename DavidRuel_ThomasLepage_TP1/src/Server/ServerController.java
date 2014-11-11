package Server;

import java.io.IOException;

import Client.MyServerObserver;
import net.sf.lipermi.exception.LipeRMIException;
import net.sf.lipermi.handler.CallHandler;
import net.sf.lipermi.net.Server;
import ObserverPattern.Observer;
import Server.Model.GameModel;

public class ServerController extends Server implements IServer 
{
	public static final int SERVER_PORT = 12345;

	private GameModel game;
	
	public ServerController()
	{
		CallHandler callHandler = new CallHandler();
		try 
		{
			callHandler.registerGlobal(IServer.class, this);
			this.bind(SERVER_PORT, callHandler);
			this.addServerListener(new ServerListner());
			
			while(true)
			{
				try 
				{
					Thread.sleep(1000);
				} 
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		} 
		catch (LipeRMIException e) 
		{
			e.printStackTrace();
		}
		catch (IOException ioe)
		{
			ioe.printStackTrace();
		}
	}
	
	public static void main(String[] args)
	{
		new ServerController();
	}
	
	public void initGame(int r, int c, int v)
	{
		try
		{
			this.game = new GameModel(r, c, v);
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void restartGame() 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addTokenToCol(int col)
	{
		// TODO Auto-generated method stub
		game.addToken(col);
	}

	@Override
	public void resign() 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void registerObserver(MyServerObserver observer) 
	{
		// TODO Auto-generated method stub
		this.game.registerObserver(observer);
	}
}
