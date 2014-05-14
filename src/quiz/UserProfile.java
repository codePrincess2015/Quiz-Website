package quiz;

/* Discuss how achievements are represented internally. */

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class UserProfile {

	/* Instance Variables */

	private String profileName;
	//private int profileIdNumber;
	private String password;
	private String profilePicFile;
	private int numQuizzesCreated;
	private int numQuizzesTaken;
	private Set<String> friendsSet;  //discuss how this is stored.
	private ArrayList<String> quizzesTakenList;
	private ArrayList<String> quizzesCreatedList;
	private ArrayList<String> achievements;
	private boolean topScore;	//do we want or need this?
	private boolean administrator = false;
	private boolean tookPracticeQuiz;
	private int ranking;

	//private int totalScore;

	/** Constants */
	private final int SHORT_HISTORY = 10;

	/*public UserProfile(String name, boolean isAdministrator){
		profileName = name;
		//profileIdNumber = idNumber;
		//friendsIds = new HashSet<Integer>();
		quizzesTakenList = new ArrayList<String>();
		quizzesCreatedList = new ArrayList<String>();
		achievements = new ArrayList<String>();
		topScore = false;
		administrator = isAdministrator;
		ranking = 0;
		//totalScore = 0;
		password = "";
	}*/

	public UserProfile(String name) {
		UserMySQLAccess con = new UserMySQLAccess();
		//FriendsConnection friendsCon = new FriendsConnection();
		// FriendsConnection methods have been moved to UserMySQLAccess

		ResultSet userResultSet = con.getUser(name);

		profileName = name;
		password = con.getHashedPassword(userResultSet);
		numQuizzesCreated = con.getNumQuizzesCreated(profileName);
		numQuizzesTaken = con.getNumQuizzesTaken(profileName);
		tookPracticeQuiz = con.hasTakenPracticeQuiz(profileName);
		friendsSet = con.getUserFriends(name);
		quizzesTakenList = con.getQuizHistory(name); // make sure this is sorted by time (descending)
		quizzesCreatedList = con.getQuizzesCreatedHistory(name);
		achievements = null; // Temporary hack until stub of getAchievements is implemented
				//achievements.addPlayerAchievement(allAchievements);
				//topScore=false;
				administrator = con.getAdmin(name);
		ranking = con.getRank(name);
		profilePicFile = con.getUserImage(name);

		//totalScore = score;

		//ArrayList<String> achievements = con.getAchievements(userResultSet);
		con.closeConnection();
	}

	public void setNewName(String name){
		profileName = name;
	}

	public void setAdmin(){
		administrator = true;
	}

	public boolean getAdmin(){
		return administrator;
	}

	public String getName(){
		return profileName;
	}
	public String getPassword(){
		return password;
	}

	public void setPassword(String password){
		this.password = AccountManager.computePasswordHash(password);
	}

	public int getNumQuizzesCreated(){
		return numQuizzesCreated;
	}

	public void setNumQuizzesCreated(int n){
		numQuizzesCreated = n;
	}

	public int getNumQuizzesTaken(){
		return numQuizzesTaken;
	}

	public void setNumQuizzesTaken(int n){
		numQuizzesTaken = n;
	}

	public boolean getPracticeQuizTaken(){
		return tookPracticeQuiz;
	}

	public void setTookPracticeQuiz(boolean n){
		tookPracticeQuiz = n;
	}

	public ArrayList<String> getQuizzesTaken(){
		return quizzesTakenList;
	}

	public ArrayList<String> getShortHistory(){
		ArrayList<String> shortList = new ArrayList<String>();
		int size = quizzesTakenList.size();
		String id;
		if (quizzesTakenList.size() >= SHORT_HISTORY) { // added if check to avoid AOBE
			for(int i=0;i<SHORT_HISTORY;i++){ //10 hardcoded as a default history length.
				id = quizzesTakenList.get(size - SHORT_HISTORY + i);
				shortList.add(id);
			} 
		}
		else {
			for (int i = 0; i < quizzesTakenList.size(); i++) {
				id = quizzesTakenList.get(size - SHORT_HISTORY + i);
				shortList.add(id);
			}
		}
		return shortList;
	}

	public ArrayList<String> getQuizzesCreated(){
		return quizzesCreatedList;
	}

	public void setTopScore(boolean top){
		topScore = top;
	}

	public boolean getTopScore(){
		return topScore;
	}

	public void addAchievement(String name){
		//	achievements.addPlayerAchievement(name);
	}

	public void updateAchievements(){
		//	achievements.updatePlayerAchievements(this);
	}

	//public Set<String> getAchievements(){
	//	return achievements.getPlayerAchievements();
	//}

	public boolean promoteToAdministrator(String username){
		boolean success = administrator;
		UserMySQLAccess con = new UserMySQLAccess();
		con.setAdmin(username);
		con.closeConnection();
		return success;
	}

	public void setRanking(int rank){
		ranking = rank;
	}

	public int getRanking(){
		return ranking;
	}

	public static String getURL(String name) {
		return "UserProfile.jsp?id=" + name;
	}

	public String getProfilePic() {
		return profilePicFile;
	}

	public void setProfilePic(String newPic) {
		profilePicFile = newPic;
	}

	public void removeFriend(String friend) {
		friendsSet.remove(friend);
	}
	public void addFriend(String friend){
		friendsSet.add(friend);
	}

	public Set<String> getFriends() {
		return friendsSet;
	}

	/*public void setTotalScore(int score){
		totalScore = score;
	}*/

	/*public int getTotalScore(){
		return totalScore;
	}*/
}
