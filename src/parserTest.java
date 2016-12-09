import java.io.*;
import java.util.*;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

class parser{
  String s = "";
  int c = 0;
  boolean endOfFile = false;

  boolean isItEnd(){
    return endOfFile;
  }

  String parse(FileInputStream f){
    try{
      while(true){
        c = f.read();
        // END CONDITION
        if(c == -1){
          String rets = s;
          endOfFile = true;
          return rets;
        }
        // EOF
        else if((char)c == '\n'){
          String rets = s;
          s = "";
          return rets;
        }
        s += (char)c;
      }
    }
    catch(Exception e){
      return "";  
    }
  }
}

public class parserTest{
   @Test
   public void test(){
      FileInputStream f = null;
      try{
         f = new FileInputStream("test.md");
      }
      catch(Exception e){

      }
      parser parse = new parser();
      String s = parse.parse(f);
      System.out.println(s);
      assertEquals(s, "* ul 1\r");
   }

   @Test
   public void test2(){
     boolean endOfFile = false;
     parser parse = new parser();
     boolean n = parse.isItEnd();
     assertEquals(n,endOfFile);          
  }
}