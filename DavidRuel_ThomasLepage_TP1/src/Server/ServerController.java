package Server;

import java.io.IOException;

import net.sf.lipermi.exception.LipeRMIException;
import net.sf.lipermi.handler.CallHandler;
import net.sf.lipermi.net.Server;

public class ServerController extends Server implements IServer 
{
	public static final int SERVER_PORT = 12345;

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
}
