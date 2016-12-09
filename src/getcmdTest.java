import java.io.*;
import java.util.*;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

class getCmd{
  String option;
  String arguments[];

  void getargs(String args[]){
    arguments = args;
  }

  void help(){
        System.out.println("-----------HELP-------------");
        System.out.println("MarkDown -> HTML Converter");
        System.out.println("v. 1. 0.");
        System.out.println("Valid Options");
        System.out.println("1. plain   2. fancy   3. slide");
        System.out.println("argument 1 = File name");
        System.out.println("argument 2 = Option   ");
        System.out.println("________________________________");
    }   

    String getOption(){
      return option;
    }

    boolean checkIfValid(String arguments[]){
    // Check if given file is a valid md file
        int locationOfDot = 0;
        String checkValidMD = null;

        if(arguments.length > 2 || arguments.length == 0){
            System.out.println("ERROR : Invalid command line arguments!");
            return false;
        }

        if(arguments[0].equals("HELP")){
          help();
          return false;
        }

        locationOfDot = arguments[0].indexOf('.');
        
        if(locationOfDot == -1){
            System.out.println("ERROR : Invalid fle name!");
            return false;
        }
        
        checkValidMD = arguments[0].substring(locationOfDot);    
        
        if(!checkValidMD.equals(".md")){
            System.out.println("ERROR: Invalid fle name!");
            return false;
        }

        if(arguments.length > 1){
          if(arguments[1].equals("plain")){
            option = "plain";
            return true;
          } 

          if(arguments[1].equals("fancy")){
            option = "fancy";
            return true;
          }

          if(arguments[1].equals("slide")){
            option = "slide";
            return true;
          }
            
        else{
          System.out.println("ERROR: Invalid Option!");
          return false;
        }
    }
      else 
           return true;
    }

    FileInputStream readFile(String fileName){
        FileInputStream f = null;
        try{
            f = new FileInputStream(fileName);
        }
        catch(Exception e){
            System.out.println("ERROR: File does not exist!");
        }
        return f;
    }
}

public class getcmdTest
{ 
    boolean fal = false; 
     boolean tru = true ;
     String arguments[] = new String[2]; 
   
   @Test
   public void checkIfValidTest1()
   {
      // testcase for help() argument[0]
      arguments[0] = "HELP" ;
      getCmd getcmd = new getCmd();
      System.out.println("inside checkIfValidTest1()");
      boolean n = getcmd.checkIfValid(arguments);
      assertEquals(fal,n);
   }
   
   @Test
   public void checkIfValidTest2()
   {
      // testcase for argument[0] = filename check  Invalid filename 
      arguments[0] = "test.msd" ; // shoud return false
      getCmd getcmd = new getCmd();
      System.out.println("inside checkIfValidTest2()");
      boolean n = getcmd.checkIfValid(arguments);
      assertEquals(fal,n);
   }
   @Test
   public void checkIfValidTest3()
   {
      // testcase for argument[1] == plain 
      String arguments[] = new String[2]; 
      arguments[0] = "test.md" ;
      arguments[1] = "slide" ;
      getCmd getcmd = new getCmd();
      System.out.println("inside checkIfValidTest3()"); // should return true 
      boolean n = getcmd.checkIfValid(arguments);
      assertEquals(tru,n);
   }
   @Test
   public void checkIfValidTest4()
   {
      // testcase for argument.legnth == 1 should return true
      String arguments[] = new String[1]; // 
      arguments[0] = "test.md" ;
      getCmd getcmd = new getCmd();
      System.out.println("inside checkIfValidTest4()"); // should return true 
      boolean n = getcmd.checkIfValid(arguments);
      assertEquals(tru,n);
   }
   @Test
   public void checkIfValidTest5()
   {
      // testcase for argument[1] == invalid option
      String arguments[] = new String[2]; 
      arguments[0] = "test.md" ;
      arguments[1] = "sddddie" ;
      getCmd getcmd = new getCmd();
      System.out.println("inside checkIfValidTest3()"); // should return false
      boolean n = getcmd.checkIfValid(arguments);
      assertEquals(fal,n);
   }
}