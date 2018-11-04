import javax.servlet.ServletContext; 
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class SocketServiceLoader implements ServletContextListener {

	private SocketThread socketThread; 
	
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		if(null!=socketThread && !socketThread.isInterrupted()) 
		{ 
		   socketThread.closeSocketServer(); 
		   socketThread.interrupt(); 
		} 
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		if(null==socketThread) 
		{ 		 
		  socketThread=new SocketThread(null);		
		  socketThread.start(); 
		} 
	} 
}