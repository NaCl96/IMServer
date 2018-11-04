import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class SocketThread extends Thread {

	private ServerSocket serverSocket = null; 
	
	

	public SocketThread(ServerSocket serverScoket){ 
	try { 
	if(null == serverSocket){ 
	this.serverSocket = new ServerSocket(12306); 
	System.out.println("serversocket new"); 
	} 
	} catch (Exception e) { 
	System.out.println("SocketThread error"); 
	e.printStackTrace(); 
	} 

	} 

	public void run(){ 
	System.out.println("socket run"); 
	while(!this.isInterrupted()){ 
	try { 
	Socket socket = serverSocket.accept(); 
	System.out.println("client++");

	if(null != socket && !socket.isClosed()){ 
	
	new SocketOperate(socket).start(); 
	} 
	 

	}catch (Exception e) { 
	e.printStackTrace(); 
	} 
	} 
	} 

	public void closeSocketServer(){ 
	try { 
	if(null!=serverSocket && !serverSocket.isClosed()) 
	{ 
	serverSocket.close(); 
	} 
	} catch (IOException e) { 
	 
	e.printStackTrace(); 
	} 
	} 
	
	
}
