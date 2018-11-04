import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;





public class FriendDAO {
	
	JDBCUtil db=new JDBCUtil();
	
	public List<Friend> getFriends(String id){
		
		String sql="select id,name from user,friendship where owner = ? and friend = id";
		ResultSet rs=db.query(sql,id);
		List<Friend> friends=new ArrayList<Friend>();
		try {
			while(rs.next()){
				Friend friend=new Friend(rs.getString("id"),rs.getString("name"));
			
				friends.add(friend);
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		return friends;
		
	}

}
