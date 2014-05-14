package quiz;

import java.util.ArrayList;

public class Quiz{
	ArrayList<QuizItem> quizItems;
	public Quiz(){
		quizItems = new ArrayList<QuizItem>();
	}
	public void addQuizItem(QuizItem q){
		quizItems.add(q);
	}
	public void removeQuizItem(int i){
		quizItems.remove(i);
	}
	public ArrayList<QuizItem> getQuizItems(){
		return quizItems;
	}
	public int grade(ArrayList<Answer> correctAnswers,ArrayList<String> userAnswer){
		int score = 0;
		for( int i = 0; i < correctAnswers.size(); i++){
			ArrayList<String> possibleAnswers = correctAnswers.get(i).getPossibleAnswers();
			for(int j = 0; j < possibleAnswers.size(); j++){
				if(userAnswer.contains(possibleAnswers.get(j))) {
					score++;
				}
			}
		}
		return score;
	}
}