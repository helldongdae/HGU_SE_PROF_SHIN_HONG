import java.io.*;
import java.util.*;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

class htmlHandler{
  FileWriter out;
  String hasHeader = "";
  boolean isOrderList = false;
  boolean isBlockQuote = false;
  boolean isParagraph = true;
  String fin = "";

  void init(String outputname){
    try{
             out = new FileWriter(outputname);
             String s = "<!DOCTYPE html><html><head><meta charset="+"\"utf-8\"><title>Expected Output</title><style></style></head><body id="+"\"Preview\">\n<p>\n";
             out.write(s);
        }
        catch(Exception e){
            System.out.println("HTML OPEN ERROR");
        }
  }

  String writeHtml(int flag, String token, String sentence){
    // Starts with something
    if(flag == 1){
      if(token.startsWith("h")){
        sentence = sentence.trim();
        if(isOrderList){
          endOfOrderList();
          isOrderList = false;
        }
        if(isBlockQuote){
          endOfBlockQuote();
          isBlockQuote = false;
        }
        fin += ("<" + token + ">");
        int n = Integer.parseInt(token.substring(1));
        hasHeader = token;
        return sentence.substring(n+1);
      }
      else if(token.equals("ul")){
        sentence = sentence.trim();
        if(isOrderList){
          endOfOrderList();
          isOrderList = false;
        }
        if(isBlockQuote){
          endOfBlockQuote();
          isBlockQuote = false;
        }
        try{
          //out.write("<ul><li>");
          hasHeader = "ul";
          fin += ("<ul><li>");
          return sentence.substring(2);
        }
        catch(Exception e){

        }
      }
      else if(token.equals("ol")){
        sentence = sentence.trim();
        if(isBlockQuote){
          endOfBlockQuote();
          isBlockQuote = false;
        }
        if(isOrderList){
          try{
            //out.write("<li>");
            hasHeader = "ol";
            fin += "<li>";
            return sentence.substring(sentence.indexOf(". ") + 2);
          }
          catch(Exception e){

          }
        }
        else{
          isOrderList = true;
          try{
            //out.write("<ol>\n<li>");
            hasHeader = "ol";
            fin += "<ol>\n<li>";
            return sentence.substring(sentence.indexOf(". ") + 2);
          }
          catch(Exception e){

          }
        }
      }
      else if(token.equals("bq")){
        if(isBlockQuote){
          return sentence.substring(1);
        }
        else{
          hasHeader = "bq";
          isBlockQuote = true;
          fin += "<blockquote>\n";
          return sentence.substring(1);
        }
      }
      else{
        return sentence;
      }
    }
    // Just write down the sentence
    else {
      fin += sentence;  
    }
    return "";
  }

  void writeFin(){
    try{
      out.write(fin);
    }
    catch(Exception e){

    }
    fin = "";
  }

  boolean isHorizontalRule(String s){
    String s2 = s.trim();
    int n1 = count(s2, '*');
    int n2 = count(s2, ' ');
    int n3 = count(s2, '_');
    if(n1 + n2 == s2.length() && n1 >= 3){
      fin += "<hr>\n";
      return true;
    }
    else if(n2 + n3 == s2.length() && n3 >= 3){
      fin += "<hr>\n";
      return true;
    }
    else  
      return false;
  }

  int count(String s, char c){
    int n = 0, i = 0;
    char [] carr = s.toCharArray();
    for(i = 0; i < s.length(); i++){
      if(carr[i] == c)
        n++;
    }
    return n;
  }

  void decide(){
    if(isOrderList){
      endOfOrderList();
      isOrderList = false;
    }
    if(isBlockQuote){
      endOfBlockQuote();
      isBlockQuote = false;
    }
  }

  void endOfLine(){
    if(hasHeader.startsWith("h"))
        fin += ("</" + hasHeader + ">\n");
    else if(hasHeader.equals("ul"))
        fin += "</li></ul>\n";
    else if(hasHeader.equals("ol"))
        fin += "</li>\n";
  }

  void endOfOrderList(){
    try{
      fin += "</ol>\n";
    }
    catch(Exception e){
        
    }
  }

  int isItSetext(String s){
    String s2 = s.trim();
    int n1 = count(s2, '=');
    int n2 = count(s2, ' ');
    int n3 = count(s2, '-');
    int maxn = Math.max(n1, Math.max(n2, n3));
    if(n2 > 0){
      return 3;
    } 
    if(n1 == maxn && n1 == s2.length() && n1 != 0){
      return 1;
    }
    if(n3 == maxn && n3 == s2.length()){
      return 2;
    }
    
    return 3;
  }

  void setextHeader(int n){
    if(n == 1){
      try{
        out.write("<h1>");
      }
      catch(Exception e){

      }
    }
    else if(n == 2){
      try{
        out.write("<h2>");
      }
      catch(Exception e){

      }
    }
    else{
      return;
    }
  }

  void closeSetextHeader(int n){
    if(n == 1){
      try{
        out.write("</h1>");
      }
      catch(Exception e){

      }
    }
    else if(n == 2){
      try{
        out.write("</h2>");
      }
      catch(Exception e){

      }
    }
    else{
      return;
    }
  }

  void endOfBlockQuote(){
    fin += "</blockquote>\n";
  }

  void newLineHtml(){
    fin += "<br>";  
  }

  String analyze(String s){
    int n1 = s.indexOf("**");
    if(n1 < 0)
      n1 = 999999;
    if(s.indexOf("/**") == n1 - 1 && n1 != 0)
      n1 = 999999;
    int n2 = s.indexOf("*");
    if(n2 < 0)
      n2 = 999999;
    if(s.indexOf("/*") == n2 - 1 && n2 != 0)
      n2 = 999999;
    int n3 = s.indexOf("__");
    if(n3 < 0)
      n3 = 999999;
    if(s.indexOf("/__") == n3 - 1 && n3 != 0)
      n3 = 999999;    
    int n4 = s.indexOf("_");
    if(n4 < 0)
      n4 = 999999;
    if(s.indexOf("/_") == n4 - 1 && n4 != 0)
      n4 = 999999;
    int n5 = s.indexOf("~~");
    if(n5 < 0)
      n5 = 999999;
    if(s.indexOf("/~~") == n5 - 1 && n5 != 0)
      n5 = 999999;
    int n6 = s.indexOf("[");
    if(n6 < 0)
      n6 = 999999;
    int n7 = s.indexOf("(");
    if(n7 < 0)
      n7 = 999999;
    int n8 = s.indexOf("&");
    if(n8 < 0)
      n8 = 999999;
    int n9 = s.indexOf("<");
    if(n9 < 0)
      n9 = 999999;
    int nMin = Math.min(n1, Math.min(n2, Math.min(n3, Math.min(n4, Math.min(n5, Math.min(n6, Math.min(n7, Math.min(n8, n9))))))));
    if(nMin == 999999)
      nMin = nMin+1;
    // Bold
    if(nMin == n1){
      writeHtml(2, "", s.substring(0, s.indexOf("**")));
      s = s.substring(s.indexOf("**") + 2);
      if(s.indexOf("**") != -1){
        fin += "<strong>";
        fin += s.substring(0, s.indexOf("**"));
        fin += "</strong>";
        return s.substring(s.indexOf("**") + 2);
      }
      else{
        writeHtml(2, "", "**");
        return s;
      }
    }
    // Bold
    else if(nMin == n3){
      writeHtml(2, "", s.substring(0, s.indexOf("__")));
      s = s.substring(s.indexOf("__") + 2);
      if(s.indexOf("__") != -1){
        fin += "<strong>";
        fin += s.substring(0, s.indexOf("__"));
        fin += "</strong>";
        return s.substring(s.indexOf("__") + 2);
      }
      else{
        writeHtml(2, "", "__");
        return s;
      }
    }
    // Emphasize
    else if(nMin == n2){
      writeHtml(2, "", s.substring(0, s.indexOf("*")));
      s = s.substring(s.indexOf("*") + 1);
      if(s.indexOf("*") != -1){
        fin += "<em>";
        fin += s.substring(0, s.indexOf("*"));
        fin += "</em>";
        return s.substring(s.indexOf("*") + 1);
      }
      else{
        writeHtml(2, "", "*");
        return s;
      }
    }
    // Emphasize
    else if(nMin == n4){
      writeHtml(2, "", s.substring(0, s.indexOf("_")));
      s = s.substring(s.indexOf("_") + 1);
      if(s.indexOf("_") != -1){
        fin += "<em>";
        fin += s.substring(0, s.indexOf("_"));
        fin += "</em>";
        return s.substring(s.indexOf("_") + 1);
      }
      else{
        writeHtml(2, "", "_");
        return s;
      }
    }
    // Strikethrough
    else if(nMin == n5){
      writeHtml(2, "", s.substring(0, s.indexOf("~~")));
      s = s.substring(s.indexOf("~~") + 2);
      if(s.indexOf("~~") != -1){
        fin += "<strike>";
        fin += s.substring(0, s.indexOf("~~"));
        fin += "</strike>";
        return s.substring(s.indexOf("~~") + 2);
      }
      else{
        writeHtml(2, "", "~~");
        return s;
      }
    }
    // link with inline style
    else if(nMin == n6){
      writeHtml(2, "", s.substring(0, s.indexOf("[")));
      s = s.substring(s.indexOf("[") + 1);
      if(s.indexOf("](") != -1){
        if(s.indexOf(")") != -1){
          String txt = s.substring(0, s.indexOf("]("));
          s = s.substring(s.indexOf("](") + 2);
          String link = s.substring(0, s.indexOf(")"));
          fin += "<a href=";
          fin += ("\"" + link + "\"");
          fin += (">" + txt + "</a>");
          return s.substring(s.indexOf(")") + 1);
        }
        else{
          writeHtml(2, "", s.substring(0, s.indexOf("])") + 2));
          return s.substring(s.indexOf("](") + 2);
        }
      }
      else{
        return s;
      }
    }
    // link w/o inline style
    else if(nMin == n7){

    }
    // ampersand
    else if(nMin == n8){
      writeHtml(2, "", s.substring(0, s.indexOf("&")) + "&amp;");
      return s.substring(s.indexOf("&") + 1);
    }
    // <
    else if(nMin == n9){
      if(s.indexOf(">") == -1){
        writeHtml(2, "", s.substring(0, s.indexOf("<")) + "&lt;");
        return s.substring(s.indexOf("<") + 1);
      }
      else{
        writeHtml(2, "", s.substring(0, s.indexOf("<")));
        return s.substring(s.indexOf("<") + 1);
      }
    }
    else{
      writeHtml(2, "", s);
      newLineHtml();
    }
    return "";
  }

  void closeHtml(){
    if(isOrderList){
      endOfOrderList();
      isOrderList = false;
    }
    if(isBlockQuote){
      endOfBlockQuote();
      isBlockQuote = false;
    }
    writeFin();
        try{
            String s = "\n</p>\n</body></html>";
            out.write(s);
            fin += s;
            out.close();
        }
         catch(Exception e){
            System.out.println("HTML CLOSE ERROR");
        }
    }

    void paragraph(){

        fin += "</p><p>";
    }
}

public class htmlHandlerTest
{ 
   @Test
   public void t1()
   {
      htmlHandler handler = new htmlHandler();
      String s = handler.writeHtml(1, "h1", "# header");
      assertEquals(s, "header");
   }
   
   @Test
   public void t2()
   {
      htmlHandler handler = new htmlHandler();
      String s = handler.writeHtml(1, "h2", "## header");
      assertEquals(s, "header");
   }

   @Test
   public void t3()
   {
      htmlHandler handler = new htmlHandler();
      String s = handler.writeHtml(1, "h3", "### header");
      assertEquals(s, "header");
   }
  
}