import java.io.*;
import java.util.*;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class getcmdTest
{ 
	 boolean fal = false; 
	  boolean tru = true ;
	  String arguments[] = new String[2]; 
	
   /*
   @Test
   public void getOptionTest()
   {
	  String option = "plain" ;
	  arguments[0] = "plain"; // will return true 
	  getCmd getcmd = new getCmd();
	  System.out.println("inside getOptionTest()");
	  assertEquals(tru, getcmd.checkIfValid(arguments));
	  assertEquals(option, getcmd.getOption());
   }
*/
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
