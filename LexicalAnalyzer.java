import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
//my imports
import java.util.Arrays;

/*                               NOTICE
For this assignment, you are NOT allowed to use any member methods of class java.util.Scanner except
hasNextLine and nextLine. For example, you CANNOT use hasNextInt, nextInt, hasNextFloat, nextFloat, 
hasNextDouble, and nextDouble for this assignment.
*/

public class A1{
      static int charClass;
      static char[] lexeme = new char[100];
      static char nextChar;
      static int lexLen;
      //static int token;
      static int nextToken;
      //static File data = new File(args[0]);//insert file name
      
      //my variables
      //static char[] arrOfLexemes;
      static int place = -1;
      final static int EOF = -1;
      static String line = "";
      
      //character classes//
      final static int LETTER = 0;
      final static int DIGIT = 1;
      final static int UNKNOWN = 99;
      
      //TOKEN CODES//
      final static int FLOATDCL = 0;
      final static int INTDCL = 1;
      final static int PRINT = 2;
      final static int ID = 3;
      final static int ASSIGN = 4;
      final static int PLUS = 5;
      final static int MINUS = 6;
      final static int INUM = 7;
      final static int FNUM = 8;
      static Scanner sc;


   public static void main(String args[]){
      File data = new File(args[0]);
      
      try{
         sc = new Scanner(data);
         
         line = sc.nextLine(); //start taking textFileContents and putting them into a String called line  21 chars
         while(sc.hasNextLine()){
            line += " " + sc.nextLine();   
         }
         line = line.trim();         
         getChar();
         
         do{
            lex();
         }while (nextToken != EOF);
      }
      
      catch(FileNotFoundException e){
         //e.printStackTrace();
         System.out.println("SOrry but there was an ERROR!");
      }
   }
   
   static void getChar(){
      //if(place > line.length)
      place++;
      
      if(place < line.length()){  //should put to while nextChar != EOF but if it
      
      nextChar = line.charAt(place);
         if(Character.isLetter(nextChar))
            charClass = LETTER;
            
         else if(Character.isDigit(nextChar) || nextChar == '.')
            charClass = DIGIT;
            
         else
            charClass = UNKNOWN;  // 
      }
      else
        charClass = EOF;
   }
   
   static int lex(){  //not sure whether to make this an int or a string
      lexLen = 0;
      
      getNonBlank();
      
      switch (charClass){
         case LETTER:
            addChar();
            getChar();
        
         if(lexeme[0] == 'f')
            nextToken = FLOATDCL;
         else if(lexeme[0] == 'i')
            nextToken = INTDCL;
         else if(lexeme[0] == 'p')
            nextToken = PRINT;
         else
            nextToken = ID;
              //********Need to double check this*****************88
         break;
         
         //parse integer literals*/
         case DIGIT:
            addChar();
            getChar();
            nextToken = INUM;
            while(charClass == DIGIT){ //while charclass equals digit or floating point numbers
               if(nextChar == '.' && line.charAt(place-1) == '.'){
                  System.out.printf("Next token is: %d, Next lexeme is %s\n", nextToken, answer(lexeme));  //working but may need to fix
                  System.exit(0);}
                  
               else if(nextChar == '.')
                  nextToken = FNUM;
              
               if(nextToken == FNUM){ 
                  addChar();
                  getChar();
               }
               
               else{
                  addChar();
                  getChar();
                  nextToken = INUM;
               }
            }
  
         break;
         
         //Parentheses and operators//
         case UNKNOWN:
            lookup(nextChar); //*************NEED to FIgure out waht this means
            getChar();
            break;
            
         case EOF:
            System.exit(0);
            //dont know if needed
            nextToken = EOF;
            lexeme[0] = 'E';
            lexeme[1] = 'O';
            lexeme[2] = 'F';
            lexeme[3] = 0;
            break;
      }//END of swtich
      
      System.out.printf("Next token is: %d, Next lexeme is %s\n", nextToken, answer(lexeme));  //need to make sure that I am outputting the entire contents of lexeme instead of just lexeme[0]
      return nextToken;
   }
   
   //getNonBlank - a function to call getChar until it returns a non-whitespace character
   static void getNonBlank(){
      while (Character.isSpace(nextChar)){  //need to fix this
         getChar();
      }
   }
   
   //addChar - a function to add nextChar to lexeme*/
   static void addChar(){
      if(lexLen <= 98){
         lexeme[lexLen++] = nextChar;
         lexeme[lexLen] = 0;
      }
      else
         System.out.println("Error - lexeme is too long");
   }
   
   static int lookup(char ch){
      switch(ch){
        case '+':
            addChar();
            nextToken = PLUS;
            break;
        case '-':
            addChar();
            nextToken = MINUS;
            break;
        case '=':
            addChar();
            nextToken = ASSIGN;
            break;
        default:
            addChar();
            nextToken = EOF;
            break;
      }
      return nextToken;
   }
   
   static String answer(char[] c){
      String x = "";
      for(int i = 0; i<lexLen; i++)
         x += lexeme[i];
      return x;
   }
   
}