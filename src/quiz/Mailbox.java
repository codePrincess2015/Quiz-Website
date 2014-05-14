package quiz;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.Statement;

public class Mailbox {
	
	private static final int FRIEND_REQUEST =0;
	private static final int CHALLENGE = 1;
	private static final int NOTE = 2;
	private ArrayList<Message> mail;
	
	
	public void addMessage(Message message){
		mail.add(message);
	}
	
	public void deleteMessage(int index){
		mail.remove(index);
	}
	
	public ArrayList<Message> getAllMessages(){
		return mail;
	}
	
	public Message getMessage(int index){
		return mail.get(index);
	}
	
	public ArrayList<Message> getAllRequests(){
		return getAllTypes(FRIEND_REQUEST);
	}
	
	public ArrayList<Message> getAllChallenges(){
		return getAllTypes(CHALLENGE);
	}
	
	public ArrayList<Message> getAllNotes(){
		return getAllTypes(NOTE);
	}
	
	private ArrayList<Message> getAllTypes(int type){
		ArrayList<Message> allTypes = new ArrayList<Message>();
		Message currentMessage;
		for(int i=0;i<mail.size();i++){
			currentMessage = mail.get(i);
			if(currentMessage.getMessageType()==type) allTypes.add(currentMessage);
		}
		return allTypes;
	}
	
	public void updateDatabase(Connection con){
		Message current;
		for(int i=0;i<mail.size();i++){
			current = mail.get(i);
			if(current.changed()){
				updateSingleMessage(current,con);
			}
		}
	}
	
	public void updateSingleMessage(Message mess,Connection con){
		try {
			Statement stmt = (Statement) con.createStatement(); 
			stmt.executeQuery("USE " + MyDBInfo.MYSQL_DATABASE_NAME);
			if(mess.getAnswered()){
				stmt.executeQuery("DELETE FROM mailbox WHERE creator = \""+mess.getCreator()+"\";");
			} else{
				stmt.executeQuery("UPDATE mailbox SET messagetext = \""+mess.getMessageText()+"\" WHERE creator = \""+mess.getCreator()+"\";");
				stmt.executeQuery("UPDATE mailbox SET seen = \""+mess.getReadStatus()+"\" WHERE creator = \""+mess.getCreator()+"\";");
				stmt.executeQuery("UPDATE mailbox SET sent = \""+mess.isSent()+"\" WHERE creator = \""+mess.getCreator()+"\";");
				stmt.executeQuery("UPDATE mailbox SET timesent = \""+System.currentTimeMillis()+"\" WHERE creator = \""+mess.getCreator()+"\";");
				stmt.executeQuery("UPDATE mailbox SET messagetype = \""+mess.getMessageType()+"\" WHERE creator = \""+mess.getCreator()+"\";");
				stmt.executeQuery("UPDATE mailbox SET destination = \""+mess.getDestination()+"\" WHERE creator = \""+mess.getCreator()+"\";");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
}
