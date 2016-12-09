import java.io.*;
import java.util.*;
import org.junit.Test ;
import static org.junit.Assert.* ;


class tokenizer{
   // Condition : There is a possibility of table
   String whatToken(String s){
      if(s.startsWith("\r"))
         return "pg";
      String tok = startsWtoken(s);
      if(tok.equals(""))
         return "";
      else
         return tok;
   }

   String startsWtoken(String s){
      s = s.trim();
      try{
         int n = Integer.parseInt(s.substring(0, s.indexOf(". ")));
         return "ol";
      }
      catch(Exception e){
         if(s.startsWith("###### "))
            return "h6";
         else if(s.startsWith("##### "))
            return "h5";
         else if(s.startsWith("#### "))
            return "h4";
         else if(s.startsWith("### "))
            return "h3";
         else if(s.startsWith("## "))
            return "h2";
         else if(s.startsWith("# "))
            return "h1";
         else if(s.startsWith("* "))
            return "ul";
         else if(s.startsWith("+ "))
            return "ul";
         else if(s.startsWith("- "))
            return "ul";
         else if(s.startsWith(">"))
            return "bq";
         else
            return "";
      }
   }
}

public class tokenizerTest {
   @Test
   public void whatTokenTest(){
      tokenizer t = new tokenizer();
      String tok = "";
      assertNotNull(t);
      assertNotEquals("pg",t.whatToken("$"));
      assertEquals(tok,t.startsWtoken("$"));
      assertEquals(tok,t.whatToken("$"));
   }
   @Test
   public void whatTokenTest1(){
      tokenizer t = new tokenizer();
      assertNotNull(t);
      assertEquals("pg",t.whatToken("\r"));
   }
   
   @Test
   public void whatTokenTest2(){
      tokenizer t = new tokenizer();
      String tok = "bq";
      assertNotNull(t);
      assertNotEquals("pg",t.whatToken("> test"));
      assertEquals(tok,t.startsWtoken("> test"));
      assertEquals(tok,t.whatToken("> test"));
   }
   
   @Test
   public void whatTokenTest3(){
      tokenizer t = new tokenizer();
      String tok = "";
      assertNotNull(t);
      assertNotEquals("pg",t.whatToken(""));
      assertEquals(tok,t.startsWtoken(""));
      assertEquals(tok,t.whatToken(""));
   }
   
   
   @Test
   public void whatTokenTestH6(){
      tokenizer t = new tokenizer();
      String tok = "h6";
      assertNotNull(t);
      assertNotEquals("pg",t.whatToken("###### test"));
      assertEquals(tok,t.startsWtoken("###### test"));
      assertEquals(tok,t.whatToken("###### test"));
   }
   
   @Test
   public void whatTokenTestH5(){
      tokenizer t = new tokenizer();
      String tok = "h5";
      assertNotNull(t);
      assertNotEquals("pg",t.whatToken("##### test"));
      assertEquals(tok,t.startsWtoken("##### test"));
      assertEquals(tok,t.whatToken("##### test"));
   }
   @Test
   public void whatTokenTestH4(){
      tokenizer t = new tokenizer();
      String tok = "h4";
      assertNotNull(t);
      assertNotEquals("pg",t.whatToken("#### test"));
      assertEquals(tok,t.startsWtoken("#### test"));
      assertEquals(tok,t.whatToken("#### test"));
   }
   @Test
   public void whatTokenTestH3(){
      tokenizer t = new tokenizer();
      String tok = "h3";
      assertNotNull(t);
      assertNotEquals("pg",t.whatToken("### test"));
      assertEquals(tok,t.startsWtoken("### test"));
      assertEquals(tok,t.whatToken("### test"));
   }
   @Test
   public void whatTokenTestH2(){
      tokenizer t = new tokenizer();
      String tok = "h2";
      assertNotNull(t);
      assertNotEquals("pg",t.whatToken("## test"));
      assertEquals(tok,t.startsWtoken("## test"));
      assertEquals(tok,t.whatToken("## test"));
   }
   @Test
   public void whatTokenTestH1(){
      tokenizer t = new tokenizer();
      String tok = "h1";
      assertNotNull(t);
      assertNotEquals("pg",t.whatToken("# test"));;
      assertEquals(tok,t.startsWtoken("# test"));
      assertEquals(tok,t.whatToken("# test"));
   }
   
   @Test
   public void whatTokenTestUL(){
      tokenizer t = new tokenizer();
      String tok = "ul";
      
      assertNotNull(t);
      
      assertNotEquals("pg",t.whatToken("* test"));
      assertEquals(tok,t.startsWtoken("* test"));
      assertEquals(tok,t.whatToken("* test"));

      assertNotEquals("pg",t.whatToken("+ test"));
      assertEquals(tok,t.startsWtoken("+ test"));
      assertEquals(tok,t.whatToken("+ test"));

      assertNotEquals("pg",t.whatToken("- test"));
      assertEquals(tok,t.startsWtoken("- test"));
      assertEquals(tok,t.whatToken("- test"));
   }

   @Test
   public void whatTokenTestOL(){
      tokenizer t = new tokenizer();
      String tok = "ol";
      assertNotNull(t);
      assertNotEquals("pg",t.whatToken(" 1. test"));
      assertEquals(tok,t.startsWtoken(" 1. test"));
      assertEquals(tok,t.startsWtoken(" 1. test"));
   } 
   

   @Test
   public void startsWtokenTest(){
      tokenizer t = new tokenizer();
      String s = " ###### test";
      String s1 = " ##### test";
      String s2 = " #### test";
      String s3 = " ### test";
      String s4 = " ## test";
      String s5 = " # test";
      String s6 = " * test";
      String s7 = " + test";
      String s8 = " - test";
      String s9 = " > test";
      String s10 = " ";
      assertNotNull(t);
      assertEquals("h6",t.startsWtoken(s));
      assertEquals("h5",t.startsWtoken(s1));
      assertEquals("h4",t.startsWtoken(s2));
      assertEquals("h3",t.startsWtoken(s3));
      assertEquals("h2",t.startsWtoken(s4));
      assertEquals("h1",t.startsWtoken(s5));
      assertEquals("ul",t.startsWtoken(s6));
      assertEquals("ul",t.startsWtoken(s7));
      assertEquals("ul",t.startsWtoken(s8));
      assertEquals("bq",t.startsWtoken(s9));
      assertEquals("",t.startsWtoken(s10));
   }


}    