package quiz;

import java.sql.Connection;
import java.sql.SQLException;

import com.mysql.jdbc.Statement;

public class Message {
	public static final int FRIEND_REQUEST = 0;
	public static final int CHALLENGE = 1;
	public static final int NOTE = 2;
	
	private String creator;
	String destination;
	private int messageType;
	private String messageText;
	private boolean read;
	private boolean sent = false;
	private boolean answered;
	private String quiz;  //either Quiz class or the quiz id.
	private int quizScore;
	private String messageId;
	private boolean changed;
	
	public Message(String Creator,String Destination,String text, int type,String quiz,String id, boolean newMessage){
		creator = Creator;
		destination = Destination;
		messageText = text;
		messageType = type;
		this.quiz = quiz;
		messageId = id;
		changed = newMessage;
	}

	public String getCreator(){
		return creator;
	}
	
	public String getDestination(){
		return destination;
	}
	
	public void editMessageText(String text){
		if(sent) return;
		if(!messageText.equals(text)) changed = true;
		messageText = text;
	}
	
	public void sendMessage(Connection con){
		try {
			Statement stmt = (Statement) con.createStatement(); 
			stmt.executeQuery("USE " + MyDBInfo.MYSQL_DATABASE_NAME);
			stmt.executeQuery("INSERT INTO mailbox (creator) VALUES \""+creator+"\";");
			stmt.executeQuery("UPDATE mailbox SET messagetext = \""+messageText+"\" WHERE creator = \""+creator+"\";");
			stmt.executeQuery("UPDATE mailbox SET seen = \""+read+"\" WHERE creator = \""+creator+"\";");
			stmt.executeQuery("UPDATE mailbox SET sent = \""+1+"\" WHERE creator = \""+creator+"\";");
			stmt.executeQuery("UPDATE mailbox SET timesent = \""+System.currentTimeMillis()+"\" WHERE creator = \""+creator+"\";");
			stmt.executeQuery("UPDATE mailbox SET messagetype = \""+messageType+"\" WHERE creator = \""+creator+"\";");
			stmt.executeQuery("UPDATE mailbox SET destination = \""+destination+"\" WHERE creator = \""+creator+"\";");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		sent = true;
		changed = true;
	}
	
	public String getMessageText(){
		return messageText;
	}
	
	public int getMessageType(){
		return messageType;
	}
	
	public void setReadStatus(boolean status){
		if(read!=status) changed = true;
		read = status;
	}
	
	public boolean getReadStatus(){
		return read;
	}
	
	public boolean isSent(){
		return sent;
	}
	
	public void answer(Connection con,boolean answer){
		try {
			if(answer){
				Statement stmt = (Statement) con.createStatement(); 
				stmt.executeQuery("USE " + MyDBInfo.MYSQL_DATABASE_NAME);
				stmt.executeQuery("INSERT INTO friends VALUES (\""+creator+"\", \""+destination+"\");");
				stmt.executeQuery("INSERT INTO friends VALUES (\""+destination+"\", \""+creator+"\");");

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		changed = true;
		answered = true;
	}
	
	public boolean getAnswered(){
		if(messageType!=FRIEND_REQUEST) return false;
		return answered;
	}
	
	//probably will change the type of this when I make the jsp.
	public String getQuiz(){
		if(messageType!=CHALLENGE) return null;
		return quiz;
	}
	
	public int getQuizScore(){
		if(messageType!=CHALLENGE) return 0;
		return quizScore;
	}
	
	public String getMessageId(){
		return messageId;
	}
	
	public boolean changed(){
		return changed;
	}
	
}