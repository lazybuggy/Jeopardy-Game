//Lucia Okeh
//7702444
//03/01/2015
//Assignment 2 - ITI1121C

import java.util.Scanner;
import java.io.*;

public class Database{
  
 private String[] fileCategories;
 private Question[][] fileQuestions;
 private int col;
 private int row;
 private static Database data;
 
  private Database(int m, int n)
  {
    col = m;
    row = n;
  }
  
  public static Database readQuestions(String name)
  {
    // returns a Database object with the information in it. 
    //The method returns a null value if the file cannot be found, or its format is incorrect or incomplete
   Scanner sc;
   int count=0;
   boolean formatCorrect=false;
   
    try {  
      sc = new Scanner(new File(name));  
    } catch (java.io.FileNotFoundException e) {  
      sc = null;  
    }
   
    //If there is no problem with the file loaded in, store the data from the file
    if(sc != null){
    int cols;
    int rows;
    
    cols = sc.nextInt();
    rows = sc.nextInt();
    
    //create a 2D array for questions, and a 1D array to hold the names of the category
    Question[][] questions= new Question[rows][cols];
    String[] categories= new String[cols];
    
    
    sc.nextLine();
    
    for(int i=0; i < cols;i++)
    {
      categories[i]=sc.nextLine();
    }
    //store the answers and questions into an object of type question
    for(int i=0; i < questions.length;i++)
     {
       for(int j=0; j < questions[0].length;j++)
       {
         String question=sc.nextLine();
         String answer=sc.nextLine();
         questions[i][j] = new Question(answer,question);
       }
     }
     //create a new database using the ints in the file
      data = new Database(cols,rows);

      //transfer the data stored to an array accesible to the rest of the class
     data.fileCategories = categories;
     data.fileQuestions = questions;
     
     
    }
    if(sc ==null){
    System.out.println("Problem reading the file in. Please include the file in the same folder. \n and check the format of the file.");
    data=null;
    }
   
    return data;
  }
  
  public String getCategory(int index)
  {
    return fileCategories[index];
  }
  public void setCategory(int index,String category)
  {
    fileCategories[index] = category;
  }
  public Question getQuestion(int category,int index)
 {
    //returns the question at position index for the given category
    Question quest;

        quest= fileQuestions[index][category];
  
    return quest;
 }
  public void setQuestion(int category, int index,Question question)
  {
    //sets the question for a given category and index.
    for(int i=0; i < col;i++)
    {
      if (fileCategories[i].equals(category))
      {
        fileQuestions[index][i]=question;
      }
    }
  }
  public int getNumCategories()
  {
    return col;
  }
  public int getNumQuestions()
  {
    return (col*row);
  }

}