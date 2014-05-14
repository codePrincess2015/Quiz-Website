package quiz;

import java.util.ArrayList;

public class QuizItem{

	/* Instance Variables */
	private String question;
	private String picture;
	private ArrayList<String> multipleChoice = null;
	private ArrayList<Answer> answers = null;

	/* Constants */

	public QuizItem(){

	}
	public void editPicture(String fileName){
		picture = fileName;
	}

	public void editQuestion(String prompt){
		question = prompt;
	}
	public void addChoiceAnswer(String answer){
		if(multipleChoice == null)multipleChoice = new ArrayList<String>();
		multipleChoice.add(answer);
	}
	//	public void removeChoiceAnswer(String answer){
	//		multipleChoice.remove(answer);
	//	}

	public Answer addAnswer(){
		if(answers == null) answers= new ArrayList<Answer>();
		Answer answer = new Answer();
		answers.add(answer);
		return answer;
	}
	public String getQuestion(){
		return question;
	}
	public String getPicture(){
		return picture;
	}
	public String getAnswers(){
		if(answers != null){
			String string = "";
			for(int i = 0; i < answers.size(); i++){
				Answer a = answers.get(i);
				ArrayList<String> list = a.getPossibleAnswers();
				for( int j = 0; j < list.size(); j++){
					string+= list.get(j);
					if(j < (list.size()-1))string+=",";
				}
				if(i < (answers.size()-1))string+=";";

			}
			return string;
		}
		return null;

	}
	public String getMultipleChoiceAnswers(){
		if(multipleChoice != null){
			String string = "";
			for(int i = 0; i < multipleChoice.size(); i++){
				string+=multipleChoice.get(i);
				if(i < (multipleChoice.size() -1)) string+=",";
			}
			return string;
		}
		return null;
	}
	
	public ArrayList<Answer> getAnswersArray(){
		return answers;
	}
	public ArrayList<String> getMultipleChoiceArray(){
		return multipleChoice;
	}
	//	
	//	public boolean isValid(){
	//		boolean pictureQuestionCheck = (picture != null && possibleAnswers != null);
	//		boolean freeResponseCheck = (question != null && possibleAnswers != null);
	//		boolean multipleChoiceCheck = (question != null && answer!=null && multipleChoice !=null);
	//		return(pictureQuestionCheck || freeResponseCheck || multipleChoiceCheck);
	//	}

}