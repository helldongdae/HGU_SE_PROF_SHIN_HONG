import java.io.;
import java.util.;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class getcmdTest
{ 
	 boolean fal = false; 
	  boolean tru = true ;

   @Test
   public void test(){
      FileInputStream f = null;
      try{
         f = new FileInputStream(test.md);
      }
      catch(Exception e){

      }
      parser parse = new parser();
      String s = parse.parse(f);
      assertEquals(s, My name is Jiwoong Songn);
   }
   @Test
   public void getOptionTest()
   {
	  String option = "plain" ;
	  arguments[1] = "plain"; // will return true 
	  getCmd getcmd = new getCmd(arguments);
	  System.out.println("inside getOptionTest()");
	  assertEquals(tru, getcmd.checkIfValid(arguments[1]));
	  assertEquals(option, getcmd.getoption());
   }

   @Test
   public void checkIfValidTest1()
   {
	   // testcase for help() argument[0]
	   String arguments[0] = "HELP" ;
	   getCmd getcmd = new getCmd(arguments);
	   System.out.println("inside checkIfValidTest1()");
	   boolean n = getcmd.checkIfValid(arguments[0]);
	   assertEquals(fal,n);
   }
   @Test
   public void checkIfValidTest2()
   {
	   // testcase for argument[0] = filename check  Invalid filename 
	   String arguments[0] = "test.msd" ; // shoud return false
	   getCmd getcmd = new getCmd(arguments[0]);
	   System.out.println("inside checkIfValidTest2()");
	   boolean n = getcmd.checkIfValid(arguments[0]);
	   assertEquals(fal,n);
   }
   @Test
   public void checkIfValidTest3()
   {
	   // testcase for argument[1] == plain 
	   String arguments[1] = "plain" ;
	   getCmd getcmd = new getCmd(arguments[1]);
	   System.out.println("inside checkIfValidTest3()");
	   boolean n = getcmd.checkIfValid(arguments[1]);
	   assertEquals(true,n);
   }
}
