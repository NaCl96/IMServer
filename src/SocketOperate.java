import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;


public class SocketOperate extends Thread {
	
	public static Map<String,Socket> clients=new HashMap<String,Socket>();
	
	private Socket socket; 
	
	String id;

	public SocketOperate(Socket socket) { 
	this.socket=socket; 
	} 
	 
	public void run() 
	{ 
	try{ 

	InputStream in= socket.getInputStream(); 

	
	
	BufferedReader br=new BufferedReader(new InputStreamReader(in,"UTF-8"));

	String line;

	while((line=br.readLine())!=null){ 
	
		
		System.out.println("client:"+line);
		
		if(line!=null&&line.startsWith("Login:"))
		{
			id = line.substring(6,line.length());
			clients.put(id, socket);
			System.out.println("Login:"+id);
		}
		
		else if(line!=null&&line.startsWith("Message:")) {
			System.out.println(""+id+":"+line);
			String msg= line.substring(8,line.length());
			JSONArray jsa=new JSONArray(msg);
			JSONObject jo=jsa.getJSONObject(0);
			String to=jo.getString("to");
			Socket tosocket = clients.get(to);
			//PrintWriter topw = new PrintWriter(tosocket.getOutputStream(),true);
			//topw.println(msg);
			if(tosocket!=null)
			{
				PrintWriter topw = new PrintWriter(new OutputStreamWriter(tosocket.getOutputStream(),"UTF-8"));
			
				topw.write(msg+"\n");
			
				topw.flush();
			}
			else {
				System.out.println("Not EXISTS:"+to);
			}
			
		}	 
	
	
	
	  


	} 
	
	System.out.println(""+id+":循环退出");
	
	if(socket.isClosed())
		System.out.println(""+id+":socket.isClosed");
	
	clients.remove("id");
	
	socket.close(); 
	//System.out.println("socket stop....."); 

	}catch(IOException ex){ 

	}finally{ 
		
	} 
	} 

}
