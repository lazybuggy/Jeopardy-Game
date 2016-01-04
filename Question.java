//Lucia Okeh
//7702444
//03/01/2015
//Assignment 2 - ITI1121C

public class Question{
  
  private String mResponse;
  private String mQuestion;
  
  public Question(String response, String question)
  {
    mResponse = response;
    mQuestion = question;
  }
  
  public String getQuestion()
  {
    return mQuestion;
  }
  public String getResponse()
  {
    return mResponse;
  }
}