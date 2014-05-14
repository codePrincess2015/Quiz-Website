package quiz;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


public class AchievementsList {

	class Achievement{
		
		
		
		
		public boolean checkAchievement(UserProfile profile,String achievement){
			if(achievement.equals("Amateur Author")){
				return authorAchieved(profile,0);
			}else if(achievement.equals("Prolific Author")){
				return authorAchieved(profile,1);
			}else if(achievement.equals("Prodigious Author")){
				return authorAchieved(profile,2);
			}else if(achievement.equals("Quiz Machine")){
				return quizzesAchieved(profile,0);
			}else if(achievement.equals("I am the Greatest")){
				return highest(profile);
			}else if(achievement.equals("Practice Makes Perfect")){
				return practice(profile);
			}
			return false;
		}
		
		private boolean authorAchieved(UserProfile profile,int x){
			int numCreated = profile.getNumQuizzesCreated();
			switch(x){
			case 0: return(numCreated>0);
			case 1: return(numCreated>4);
			case 2: return(numCreated>9);
			default: return false;
			}
		}
		private boolean quizzesAchieved(UserProfile profile,int x){
			int quizzesTaken = profile.getNumQuizzesTaken();
			switch(x){
			case 0: return (quizzesTaken>4);
			default: return false;
			}
		}
		private boolean highest(UserProfile profile){
			return profile.getTopScore();
		}
		private boolean practice(UserProfile profile){
			return profile.getPracticeQuizTaken();
		}
		
	}

	private static final Set<String> allAchievements;
	static {
		Set<String> temp = new HashSet<String>();
		temp.add("Amateur Author");
		temp.add("Prolific Author");
		temp.add("Prodigious Author");
		temp.add("Quiz Machine");
		temp.add("I am the Greatest");
		temp.add("Practice Makes Perfect");
		allAchievements = temp;
	}
	
	
	private ArrayList<String> playerAchievements = new ArrayList<String>();
	
	public AchievementsList(UserMySQLAccess con, String user){
		try {
			ResultSet rs = con.getIndividualAchievements(user);  //remember to order them when we determine how we want to order them.
			while(rs.next()){
				playerAchievements.add(rs.getString("achievement"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	}
	
	public void updatePlayerAchievements(UserProfile profile,UserMySQLAccess con){
		//create a Set that contains all achievements that playerAchievements does not contain.
		ArrayList<String> unAchieved = new ArrayList<String>();
		unAchieved.addAll(allAchievements);
		if(!playerAchievements.isEmpty()){
			unAchieved.removeAll(playerAchievements);
		}else{
			System.out.print("empty achievements");
		}
		//Create iterator of all unachieved achievements.
		for(int i=0;i<unAchieved.size();i++){
			updateIndividualAchievement(profile,unAchieved.get(i),con);
		}
		
	}
	public void addPlayerAchievement(String name, String achievement,UserMySQLAccess con){
		playerAchievements.add(achievement);
		con.addAchievement(name,achievement);
	}
	
	public void addPlayerAchievement(String name,Set<String> achievements,UserMySQLAccess con){
		Iterator<String> iterator = achievements.iterator();
		while(iterator.hasNext()){
			addPlayerAchievement(name,iterator.next(),con);
		}
	}
	
	private void updateIndividualAchievement(UserProfile profile,String achievement,UserMySQLAccess con){
		Achievement achieve = new Achievement();
		if(achieve.checkAchievement(profile, achievement)) addPlayerAchievement(profile.getName(),achievement,con);
	}
	
	
	public ArrayList<String> getPlayerAchievements(){
		return playerAchievements;
	}
}
