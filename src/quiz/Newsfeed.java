package quiz;

public class Newsfeed{
	String name;
	String typeNews;
	String deets;
	
	public Newsfeed(String username,String type, String details){
		name = username;
		typeNews = type;
		deets = details;
	}
	public String getUser(){
		return name;
	}
	public String getType(){
		return typeNews;
	}
	public String getDetails(){
		return deets;
	}
}