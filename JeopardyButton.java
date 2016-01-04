//Lucia Okeh
//7702444
//03/01/2015
//Assignment 2 - ITI1121C

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.JButton;

public class JeopardyButton extends JButton {
  
  private int mCategory;
  private int mQuestion;
  private String mAmount;
  private JButton button; 
  
  
  public JeopardyButton(ActionListener listener, int category, int question, int amount)
  {
    //initializes the attributes of the object,
    //adds a listener, 
    //sets the label of the button to represent the amount of money associated with the question.
    
    mCategory=category;
    mQuestion = question;
    mAmount = Integer.toString(amount);
    String q = Integer.toString(question);
    
   button = new JButton(mAmount);
    button.setPreferredSize(new Dimension(80,45));
   button.addActionListener(listener);
   setText(q +". " + mAmount);
  }
  
  
  public int getCategory()
  {
    return mCategory;
  }
  public int getQuestion()
  {
    return mQuestion;
  }
}