package quiz;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpSession;

import com.mysql.jdbc.Statement;

/*
 * This file supports access for MySQL requests about the users on our site.
 */

public class UserMySQLAccess { 
	/* Instance Variables */
	static String account = MyDBInfo.MYSQL_USERNAME; 
	static String password = MyDBInfo.MYSQL_PASSWORD; 
	static String server = MyDBInfo.MYSQL_DATABASE_SERVER; 
	static String database = MyDBInfo.MYSQL_DATABASE_NAME; 

	public static Connection con;
	private static ResultSet rs;
	private static Statement stmt;

	/* Constants */


	public UserMySQLAccess() {
		try { 
			Class.forName("com.mysql.jdbc.Driver"); 
			con = DriverManager.getConnection( "jdbc:mysql://" + server, account ,password);
			stmt = (Statement) con.createStatement();
			stmt.executeQuery("USE " + database); 
			rs = stmt.executeQuery("SELECT * FROM users");
		} catch (SQLException e) { 
			// Auto-generated catch block 
			e.printStackTrace();
		} 
		catch (ClassNotFoundException e) { 
			// Auto-generated catch block 
			e.printStackTrace(); 
		} 
	}

	public ResultSet getResultSet() {
		ResultSet set = null;
		try {
			stmt = (Statement) con.createStatement();
			stmt.executeQuery("USE " + database);
			set = stmt.executeQuery("SELECT * FROM users");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return set;
	}

	public String getFirstName(ResultSet set) {
		String result = null;
		try {
			result = set.getString("firstName");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public String getLastName(ResultSet set) {
		String result = null;
		try {
			result = set.getString("lastName");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public String getImageFile(ResultSet set) {
		String result = null;
		try {
			result = set.getString("imageFile");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	// Returns the row in the users table corresponding to the desired userName (which is unique).
	public ResultSet getUser(String userName) {
		ResultSet result = null;
		try {
			result = stmt.executeQuery("SELECT * FROM users WHERE userName = \"" + userName + "\"");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	public String getUsername(ResultSet set) {
		String result = null;
		try {
			stmt = (Statement) con.createStatement();
			result = set.getString("userName");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public String getHashedPassword(ResultSet set) {
		String result = null;
		try {
			while (set.next()){
				result = (String) set.getString("hashedPassword");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public void setHashedPassword(String hashedPassword, String username) {
		try {
			stmt = (Statement) con.createStatement();
			stmt.executeUpdate("UPDATE users SET hashedPassword = \"" + hashedPassword +"\" WHERE userName = \"" + username +"\"");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void addUser(String firstName, String lastName, String userName, String hashedPassword) {
		try {
			stmt = (Statement) con.createStatement();
			stmt.executeUpdate("INSERT INTO users VALUES(\"" + firstName + "\",\"" + lastName + "\",\"" + userName + "\",\"" + hashedPassword + "\",\""+""+"\"," + 0 + "," + 0 + ","+0+","+0+ ")");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean userNameInUse(String proposedName) {
		ResultSet result = null;
		try {
			result = stmt.executeQuery("SELECT * FROM users WHERE userName = \"" + proposedName + "\"");
			if (result.next()) {
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public void closeConnection() {
		try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean hasTakenPracticeQuiz(String username) {
		boolean result = false;
		try {
			Statement stmt = (Statement) con.createStatement();
			ResultSet set = stmt.executeQuery("SELECT * FROM users WHERE username = \"" + username +"\"");
			while (set.next()) {
				result = set.getBoolean("tookPracticeQuiz");// Interprets 0 as false, nonzero as true
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public Statement returnStatement(){
		Statement stmt = null;
		try {
			stmt = (Statement) con.createStatement();
			stmt.executeQuery("USE " + MyDBInfo.MYSQL_DATABASE_NAME); 	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return stmt;
	}
	public ResultSet returnQuizzes(){
		ResultSet set = null;
		try {
			Statement stmt = (Statement) con.createStatement(); 
			stmt.executeQuery("USE " + MyDBInfo.MYSQL_DATABASE_NAME);
			set = stmt.executeQuery("SELECT * FROM quizzes");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

		return set;

	}
	public ResultSet returnQuizAttributes(String quizName){
		ResultSet set = null;
		try {
			Statement stmt = (Statement) con.createStatement(); 
			stmt.executeQuery("USE " + MyDBInfo.MYSQL_DATABASE_NAME);
			set = stmt.executeQuery("SELECT * FROM quizzes WHERE quizName = " + "\""+quizName +"\"");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

		return set;
	}
	public ResultSet returnQuestions(String quizName){
		ResultSet set = null;
		try {
			Statement stmt = (Statement) con.createStatement(); 
			stmt.executeQuery("USE " + MyDBInfo.MYSQL_DATABASE_NAME);
			set = stmt.executeQuery("SELECT * FROM quizItems WHERE quizName = " + "\""+quizName +"\"");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return set;
	}

	public ArrayList<String> getQuizHistory(String username) {
		ResultSet set = null;
		ArrayList<String> quizHistory = new ArrayList<String>();
		try {
			stmt = (Statement) con.createStatement();
			set = stmt.executeQuery("SELECT * FROM quizzesTaken WHERE userName = \"" + username + "\"");
			String quiz = null;
			while (set.next()) {
				quiz = set.getString("quizName");
				quizHistory.add(quiz);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return quizHistory;
	}

	public ArrayList<String> getQuizzesCreatedHistory(String username) {
		ResultSet set = null;
		ArrayList<String> quizHistory = new ArrayList<String>();
		try {
			stmt = (Statement) con.createStatement();
			set = stmt.executeQuery("SELECT * FROM quizzes WHERE username = \""+username+"\"");
			String quiz = null;
			while (set.next()) {
				quiz = set.getString("quizName");
				quizHistory.add(quiz);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return quizHistory;

	}

	public void updateHasTakenPracticeQuiz(String username) {
		try {
			stmt = (Statement) con.createStatement();
			if (!hasTakenPracticeQuiz(username)) {
				stmt.executeUpdate("UPDATE users SET tookPracticeQuiz = " + true + " WHERE userName = \"" + username + "\"");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}


	public boolean getAdmin(String username) {
		ResultSet set = null;
		boolean isAdmin = false;
		try {
			set = getUser(username);
			while (set.next()) {
				isAdmin = set.getBoolean("isAdmin");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return isAdmin;
	}

	public void setAdmin(String username) {
		ResultSet set = null;
		try {
			stmt = (Statement) con.createStatement();
			set = getUser(username);
			while (set.next()) {
				stmt.executeUpdate("UPDATE users WHERE username = \"" + username + "\" SET isAdmin = 1");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public int getRank(String username) {
		ResultSet set = null;
		int rank = 1;
		try {
			int numQuizzesTaken = getNumQuizzesTaken(username);
			set = stmt.executeQuery("SELECT * FROM users WHERE numQuizzesTaken > " + numQuizzesTaken);
			while (set.next()) {
				rank++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rank;
	}

	/* Formerly FriendsConnection code: */

	public Set<String> getUserFriends(String name) {
		Set<String> friendsSet = new HashSet<String>();
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery("SELECT * FROM friends WHERE friendOne = \"" + name + "\"");
			String friend = "";
			while (rs.next()) {
				friend = (String) rs.getString("friendTwo");
				friendsSet.add(friend);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return friendsSet;
	}

	/* Inserts a new friendship into the friends table. Represents this as one line stating that userOne is friends
	 * with userTwo, and another line stating that userTwo is friends with userOne. Because a user cannot be friends with
	 * himself, the total number of rows in the friends table is twice the total number of friendships.
	 */
	public void addFriends(String userOne, String userTwo) {
		try {
			stmt = (Statement) con.createStatement();
			stmt.executeUpdate("INSERT INTO friends VALUES(\"" + userOne + "\",\"" + userTwo + "\")");
			stmt.executeUpdate("INSERT INTO friends VALUES(\"" + userTwo + "\",\"" + userOne + "\")");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	public void removeFriends(String userOne, String userTwo) {
		try {
			stmt = (Statement) con.createStatement();
			stmt.executeUpdate("DELETE FROM friends WHERE friendOne = \"" + userOne + "\" AND friendTwo = \"" + userTwo + "\"");
			stmt.executeUpdate("DELETE FROM friends WHERE friendOne = \"" + userTwo + "\" AND friendTwo = \"" + userOne + "\"");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/* Checks to see if two users are friends. Only checks one half of the friendship, because both should be present.
	 */
	public boolean areFriends(String userOne, String userTwo) {
		ResultSet result = null;
		try {
			stmt = (Statement) con.createStatement();
			result = stmt.executeQuery("SELECT * FROM friends WHERE friendOne = \"" + userOne + "\" AND friendTwo = \"" + userTwo + "\"");
			if (result.next()) {
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public int updateQuizzesTaken(String username, String quizName, int score, double timeElapsed, double time){
		int num = 0;
		try {
			Statement stmt = (Statement) con.createStatement();
			stmt.executeQuery("USE " + database);;
			stmt.executeUpdate("INSERT INTO quizzesTaken VALUES(\"" + username+"\",\""+quizName+"\","+score+","+timeElapsed+ ","+time+")");
			ResultSet set = stmt.executeQuery("SELECT * FROM users WHERE username = \""+username+"\"");
			if(set.next()){
				int numQuizzes = set.getInt("numQuizzesTaken")+1;
				num = numQuizzes;
				stmt.executeUpdate("UPDATE users SET numQuizzesTaken = " + numQuizzes + " WHERE username = \""+username+"\"");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return num;
	}


	/* Updates the newsFeed table */
	public void updateNewsfeed(String username,String type,String quiz,double time){
		try {
			Statement stmt = (Statement) con.createStatement();
			stmt.executeQuery("USE " + database);
			stmt.executeUpdate("INSERT INTO newsfeed VALUES(\"" + username+"\",\""+type+"\",\""+quiz+"\","+time+")");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public ResultSet getNewsfeed(){
		ResultSet rs = null;
		try {
			Statement stmt = (Statement) con.createStatement();
			stmt.executeQuery("USE " + database);
			rs = stmt.executeQuery("SELECT * FROM newsfeed ORDER BY time DESC");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}
	public ResultSet getMessageAttributes(String receiver){
		ResultSet set = null;
		try {
			stmt = (Statement) con.createStatement(); 
			stmt.executeQuery("USE " + MyDBInfo.MYSQL_DATABASE_NAME);
			set = stmt.executeQuery("SELECT * FROM mailbox WHERE destination = " + "\""+receiver +"\" ORDER BY timesent DESC");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return set;
	}

	public void addMessage(Message message){
		try {
			stmt = (Statement) con.createStatement();
			stmt.executeQuery("USE " + MyDBInfo.MYSQL_DATABASE_NAME);
			String creator = message.getCreator();
			String messagetext = message.getMessageText();
			int seen = 0;
			int sent = 0;
			String destination = message.getDestination();
			String messageId = message.getMessageId();
			String messagetype = Integer.toString(message.getMessageType());
			String quizName = message.getQuiz();
			stmt.executeUpdate("INSERT INTO mailbox VALUES (\""+creator+"\",\""+messagetext+"\","+seen+","+sent+","+System.currentTimeMillis()+",\""+destination+"\",\""+messageId+"\",\""+messagetype+"\",\""+quizName+"\")"); //order matters.

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void removeMessage(String id){
		try {
			stmt = (Statement) con.createStatement();
			stmt.executeQuery("USE " + MyDBInfo.MYSQL_DATABASE_NAME);
			ResultSet rs = stmt.executeQuery("SELECT * FROM mailbox WHERE messageId = " + id);
			if(rs.next()){
				String destination = rs.getString("destination");
				if(destination.equals("null")){
					stmt.executeUpdate("DELETE destination FROM mailbox WHERE messageId = \""+id+"\"");
				}else{
					stmt.executeUpdate("UPDATE mailbox SET destination = \"null\" WHERE messageId = \""+id+"\"");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public String getNewMessageId(){
		return Long.toString(System.currentTimeMillis());
	}

	public ResultSet getIndividualAchievements(String name){
		ResultSet set = null;
		try {
			stmt = (Statement) con.createStatement(); 
			stmt.executeQuery("USE " + MyDBInfo.MYSQL_DATABASE_NAME);
			set = stmt.executeQuery("SELECT * FROM achievements WHERE user = \""+name +"\" ORDER BY time DESC");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return set;
	}

	public ResultSet getAllAchievements(){
		ResultSet set = null;
		try {
			stmt = (Statement) con.createStatement(); 
			stmt.executeQuery("USE " + MyDBInfo.MYSQL_DATABASE_NAME);
			set = stmt.executeQuery("SELECT * FROM achievementsDescriptions");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return set;
	}

	public ResultSet searchForUser(String username) {
		ResultSet set = null;
		try {
			stmt = (Statement) con.createStatement();
			stmt.executeQuery("USE " + MyDBInfo.MYSQL_DATABASE_NAME);
			set = stmt.executeQuery("SELECT * FROM users WHERE username LIKE \"%" + username + "%\"");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return set;
	}

	public ResultSet searchForQuiz(String quizName) {
		ResultSet set = null;
		try {
			stmt = (Statement) con.createStatement();
			stmt.executeQuery("USE " + MyDBInfo.MYSQL_DATABASE_NAME);
			set = stmt.executeQuery("SELECT * FROM quizzes WHERE quizName LIKE \"%" + quizName + "%\"");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return set;
	}

	public String getAchievementDescription(String achievement){
		String description = "";
		try{
			stmt.executeQuery("USE " + MyDBInfo.MYSQL_DATABASE_NAME);
			ResultSet rs = stmt.executeQuery("SELECT * FROM achievementDescriptions WHERE achievement = \"" + achievement + "\"");
			description = rs.getString("description");
		} catch (SQLException e){
			e.printStackTrace();
		}
		return description;
	}

	public String getUserImage(String username) {

		ResultSet set = null;
		String image = "";
		try {
			set = getUser(username);
			while (set.next()) {
				image = set.getString("userImage");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return image;
	}
	public ResultSet returnQuizOrder(){
		ResultSet set = null;
		try{
			Statement stmt = (Statement) con.createStatement();
			stmt.executeQuery("USE " + MyDBInfo.MYSQL_DATABASE_NAME);
			set = stmt.executeQuery("SELECT * FROM quizzes ORDER BY timesTaken DESC");
		} catch (SQLException e){
			e.printStackTrace();
		}
		return set;
	}
	public ResultSet returnUserRanks(String quizName){
		ResultSet set = null;
		try{
			Statement stmt = (Statement) con.createStatement();
			stmt.executeQuery("USE " + MyDBInfo.MYSQL_DATABASE_NAME);
			set = stmt.executeQuery("SELECT * FROM quizzesTaken WHERE quizName = \""+ quizName +"\" ORDER BY score DESC, time ASC");
		} catch (SQLException e){
			e.printStackTrace();
		}
		return set;
	}

	public void deleteQuiz(String quizName){
		try{
			Statement stmt = (Statement) con.createStatement();
			stmt.executeUpdate("DELETE FROM quizzes WHERE quizName = \"" + quizName +"\"");
			stmt.executeUpdate("DELETE FROM quizItems WHERE quizName = \"" + quizName+"\"");
			stmt.executeUpdate("DELETE FROM newsfeed WHERE details = \"" + quizName+"\"");
		} catch (SQLException e){
			e.printStackTrace();
		}
	}

	public int getNumQuizzesCreated(String username) {
		int num = 0;
		try {
			Statement stmt = (Statement) con.createStatement();
			ResultSet set = stmt.executeQuery("SELECT * FROM users WHERE username = \"" + username +"\"");
			while(set.next()){
				num = set.getInt("numQuizzesCreated");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return num;
	}
	public int getNumQuizzesTaken(String username) {
		int num = 0;
		try {
			Statement stmt = (Statement) con.createStatement();
			ResultSet set = stmt.executeQuery("SELECT * FROM users WHERE username = \"" + username +"\"");
			while(set.next()){
				num = set.getInt("numQuizzesTaken");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return num;
	}
	public ResultSet returnMyQuizHistory( String username,String quizName){
		ResultSet rs = null;
		try {
			Statement stmt = (Statement) con.createStatement();
			rs = stmt.executeQuery("SELECT * FROM quizzesTaken WHERE username = \"" + username +"\" AND quizName = \"" +quizName+"\" ORDER BY currentTime DESC");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return rs;
	}
	//	public ResultSet returnLastDayHistory(String quizName){
	//		
	//	}
	public void addAchievement(String name,String achievement){
		try {
			Statement stmt = (Statement) con.createStatement();
			stmt.executeQuery("USE " + database);
			stmt.executeUpdate("INSERT INTO achievements VALUES(\"" + name+"\",\""+achievement+"\",\""+System.currentTimeMillis()+"\")");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public ResultSet returnAchievements(){
		ResultSet rs = null;
		try {
			Statement stmt = (Statement) con.createStatement();
			stmt.executeQuery("USE " + database);
			rs = stmt.executeQuery("SELECT * FROM achievements ORDER BY time DESC"); 

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

	public void addAnnouncement(String text){
		try {
			Statement stmt = (Statement) con.createStatement();
			stmt.executeQuery("USE " + database);
			stmt.executeUpdate("INSERT INTO announcements VALUES(\""+text+"\","+System.currentTimeMillis()+")");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public ResultSet getAnnouncements(){
		ResultSet set = null;
		try {
			stmt = (Statement) con.createStatement();
			stmt.executeQuery("USE " + MyDBInfo.MYSQL_DATABASE_NAME);
			set = stmt.executeQuery("SELECT * FROM announcements ORDER BY time DESC");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return set;
	}
	public String getQuizScore(String userName,String quizName){
		ResultSet set = null;
		String string = "";
		try {
			stmt = (Statement) con.createStatement();
			stmt.executeQuery("USE " + MyDBInfo.MYSQL_DATABASE_NAME);
			set = stmt.executeQuery("SELECT * FROM quizzesTaken WHERE username = \""+userName+"\" AND quizname= \""+quizName+"\"  ORDER BY score DESC");
			if(set.next()){
				int score = set.getInt("score");
				double time = set.getDouble("time");
				string = ""+score+" My Time: "+time+" sec";
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return string;
	}
	public boolean newMessage(String username){
		boolean bool = false;
		ResultSet set = null;
		try {
			stmt = (Statement) con.createStatement();
			stmt.executeQuery("USE " + MyDBInfo.MYSQL_DATABASE_NAME);
			set = stmt.executeQuery("SELECT * FROM mailbox WHERE destination = \""+username+"\" AND seen = 0");
			if(set.next()){
				bool = true; 
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return bool;
	
	}
	
}
