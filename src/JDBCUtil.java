import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class JDBCUtil {

	private final static String NAME="root";
	private final static String PWD="12345678";
	private final static String DRIVER="com.mysql.jdbc.Driver";
	private final static String PATH="jdbc:mysql://127.0.0.1:3306/im001";
	private Connection con;
	private PreparedStatement ps;
	private ResultSet rs;
	public Connection getCon(){
		try {
			Class.forName(DRIVER);
			con=DriverManager.getConnection(PATH, NAME, PWD);		
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		return con;
	}
	
	public void closeAll(){	
		try {
			if(rs!=null){
				rs.close();
			}	
			if(ps!=null){
				ps.close();
			}
			if(con!=null){
				con.close();
			}
		} catch (Exception e2) {
		e2.printStackTrace();
		}	
	}
	
	public int update(String sql,Object...obj){
		int result=0;
		try {
			con=getCon();
			ps=con.prepareStatement(sql);
			if(obj!=null)
				for (int i = 0; i < obj.length; i++) 
					ps.setObject(i+1, obj[i]);					
			result=ps.executeUpdate();		
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			closeAll();
		}	
		return result;
	}
		
	public ResultSet query(String sql,Object...obj){
		try {
			con=getCon();
			ps=con.prepareStatement(sql);
			if(obj!=null)
				for (int i = 0; i < obj.length; i++) 
					ps.setObject(i+1, obj[i]);	
			rs=ps.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return rs;
	}
}
