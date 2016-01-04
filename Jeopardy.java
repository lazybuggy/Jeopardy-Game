//Lucia Okeh
//7702444
//03/01/2015
//Assignment 2 - ITI1121C

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.JFrame;

public class Jeopardy extends JFrame implements ActionListener{
  
  //stores an array of jeopardy buttons
  private JeopardyButton[][] jB;
  private Database data;
  
  //main panel where other panels will be places
  private JPanel mainPanel;
  //holds the reveal button and Question/Answer text
  private  JPanel secondPanel = new JPanel();
  //holds the question text
  private JLabel text= new JLabel();
  //holds the answer text
  private JLabel text2= new JLabel();
  private JFrame frame;
  
  //another JFrame to get the name of the file
   JFrame user= new JFrame("File name");
   
  private JButton buttonReveal;
  private JButton buttonLoad;
  JButton buttonDisplay= new JButton("Display");
  JTextField textField = new JTextField();
  
  //checks if the load button has been clicked, if true do not allow it to be clicked if false then it can be clicked again
  private boolean loadClicked;
  //a 2D array of boolean values that turns true whenever that button has previously been clicked
  private boolean[][] questionClicked;
  //checks if the reveal button has been clicked, initialized to true because no buttons are clicked
  private boolean revealClicked=true;
  //checks if a button has been clicked, initalized to false because no question button has been clicked
  private boolean buttonClicked=false;
  
  //used for the question/answer button section
  private int indexRow=0;
  private int indexCol=0;
  //counter intialized to 1 for the first question
  private int counter=1;
  
  
 
  
  public Jeopardy()
  {
    
    frame = new JFrame("Jeopardy");

    mainPanel = new JPanel();
    frame.setPreferredSize(new Dimension(1200,700));
    frame.setMinimumSize(new Dimension(1200,700));

    mainPanel.setLayout(new BorderLayout());
    
    buttonLoad=new JButton("Load File");
    buttonLoad.addActionListener(this);
    buttonLoad.setPreferredSize(new Dimension(100,100));

    
    buttonDisplay.addActionListener(this);
    
    //add the load button to the top of the panel
    mainPanel.add(buttonLoad,BorderLayout.NORTH);

    frame.setContentPane(mainPanel);
    frame.getContentPane().setBackground(Color.BLACK);
    
    frame.pack();
    frame.setVisible(true);
    
  }
  public void actionPerformed(ActionEvent e)
  {
    Question quest;
    String filename=null;
    JLabel instruct = new JLabel("Please Enter the name of your file into the textbox, include '.txt'");
    
    
    
    if(e.getSource() == buttonLoad && loadClicked==false)
    {
      //NEW JFRAME that allows the user to enter the name of the file
      
      //the load button has been clicked
      loadClicked=true;
      
      buttonDisplay.setPreferredSize(new Dimension(60,40));
      
      textField.addActionListener(this);
      
      JPanel userpanel = new JPanel();
      userpanel.setSize(1000,5000);
      userpanel.setLayout(new BoxLayout(userpanel, BoxLayout.Y_AXIS));
      
      userpanel.add(instruct);
      userpanel.add(textField);
      userpanel.add(buttonDisplay);
      user.add(userpanel);
      user.pack();
      user.setVisible(true);
    }
    
    
    if(e.getSource() == buttonDisplay)
    {
      //when this button is clicked, the layout of the board is created 
      
      filename = textField.getText();
      //get rid of the other jframe
      user.setVisible(false);
      
      //if the file name is not null or not empty(nothing entered)
      if (filename != null  && (filename.isEmpty())==false)  
      {
        //read the questions in from the file name entered
        data = Database.readQuestions(filename);
        
        //if data is not null load the file
        if(data != null){
          int cols = data.getNumCategories();
          int questions = data.getNumQuestions();
          
          //create a new panel to hold the buttons
          JPanel buttonPanel = new JPanel();
          buttonPanel.setLayout(new GridLayout(questions/cols,cols,15,20));
          buttonPanel.setPreferredSize(new Dimension(1000,500));
          
          //create an array of labels
          JLabel[] textLabel=new JLabel[cols];
          
          //create a new panel to hold the labels
          JPanel textpanel = new JPanel();
          textpanel.setLayout(new GridLayout(1,cols,2,1));
          textpanel.setBackground(Color.BLACK);
          
          //array that stores row x col
          jB= new JeopardyButton[questions/cols][cols];
          questionClicked= new boolean[questions/cols][cols];
          
          for(int j=0; j < cols;j++)
          {
            //add label
            textLabel[j]= new JLabel(data.getCategory(j));
            textLabel[j].setPreferredSize(new Dimension(85, 50));
            textLabel[j].setForeground(Color.WHITE);
            textpanel.add(textLabel[j]);
          }
          //initalize amount to 100 for the first row
          int amount = 100;
          
          for(int i=0; i < jB.length;i++)
          {
            for(int j=0; j < jB[0].length;j++)
            {
              String amo= Integer.toString(amount);
              jB[i][j] =new JeopardyButton(this,i,counter,amount);
              jB[i][j].addActionListener(this);
              jB[i][j].setBackground(Color.WHITE);
              buttonPanel.add(jB[i][j]);
              questionClicked[i][j]=false;
              //initalize the counter for the next question
              counter++;     
            }
            //after the last column is loaded in increment the amount
            amount = amount +100;
          }
          
          //add things to the panels and add those panels to the main panel
          secondPanel.setLayout(new BorderLayout());
          secondPanel.setBackground(Color.BLACK);
          buttonPanel.setBackground(Color.BLACK);
          buttonReveal = new JButton("Reveal");
          buttonReveal.setBackground(Color.LIGHT_GRAY);
          buttonReveal.setPreferredSize(new Dimension(180,80));
          secondPanel.add(buttonReveal,BorderLayout.EAST);
          buttonReveal.addActionListener(this);
            
          mainPanel.remove(buttonLoad);
          mainPanel.add(textpanel,BorderLayout.NORTH);
          mainPanel.add(buttonPanel,BorderLayout.CENTER);
          mainPanel.add(secondPanel,BorderLayout.SOUTH);
          
          frame.pack();
        }
        else if(data ==null)
        {  
          loadClicked=false;
          Object[] options = { "OK", "CANCEL" };
          JOptionPane.showOptionDialog(null, "File name incorrect.", "Warning",
                                       JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
                                       null, options, options[0]);
        }
      }
      else{
        loadClicked=false;
        Object[] options = { "OK", "CANCEL" };
        JOptionPane.showOptionDialog(null, "No valid input.", "Warning",
                                     JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
                                     null, options, options[0]);
        
      }
    }
    
    if (e.getSource() instanceof JeopardyButton) {
      //if a button is clicked
      
      if(revealClicked==true){
        int countTrues=1;
        JeopardyButton src = (JeopardyButton) e.getSource();
        
        revealClicked=false;
        buttonClicked=true;
        for(int i=0; i < jB.length;i++)
        {
          for(int j=0; j < jB[0].length;j++)
          {
            if(questionClicked[i][j]==false && src== jB[i][j])
            {
              jB[i][j].setText("--");
              quest = data.getQuestion(j,i);
              text.setText("The Question:  "+ quest.getQuestion());
              text.setForeground(Color.WHITE);
              text.setPreferredSize(new Dimension(85, 50));
              
              secondPanel.add(text,BorderLayout.NORTH);
              questionClicked[i][j]=true;
              
              //initalize which question was clicked in order for the index to be used when Reveal is clicked
              indexRow=i;
              indexCol=j;
              break;
            }
            else if (questionClicked[i][j]==true)
            {
              countTrues++;
              //if the amount of trues in the booleanarray equals the amount of questions all buttons have been clicked
              if(counter== countTrues){
                text.setText("The game is now finished");
              System.out.println("THE GAME IS NOW FINSIHED");
              }
            }
          }
        }
      }
      //If the reveal button was not previously clicked
      else{
        Object[] options = { "OK", "CANCEL" };
        JOptionPane.showOptionDialog(null, "Anwser not revealed for previous question, click OK and reveal the answer. \n OR question already clicked, please choose another question.", "Warning",
                                     JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
                                     null, options, options[0]);
      }
    }
    
    if(e.getSource() == buttonReveal)
    {
      if(buttonClicked==false){
        Object[] options = { "OK", "CANCEL" };
        JOptionPane.showOptionDialog(null, "No question clicked, click OK to continue", "Warning",
                                     JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
                                     null, options, options[0]);
      }
      else{
        
        revealClicked=true;
        quest=data.getQuestion(indexCol,indexRow);
        text2.setText("The Answer:  "+ quest.getResponse());
        text2.setPreferredSize(new Dimension(85, 50));
        text2.setForeground(Color.WHITE);
        buttonClicked=false;
        secondPanel.add(text2,BorderLayout.SOUTH);
        frame.pack();
      }
    }
  }
  
  public static void main(String args[]) {
    
    StudentInfo.display();
    Jeopardy game = new Jeopardy();
  }
}