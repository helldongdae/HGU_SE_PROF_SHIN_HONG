//package pack.song;

import java.io.*;
import java.util.*;

interface Visitor{
	public FileInputStream visit(getCmd getcmd);
	public String visit(tokenizer tok);
	public void visit(htmlHandler handler);
	public String visit(parser parse);
}

interface Visitable{
	public void accept(Visitor visitor);
}

class htmlHandler implements Visitable{
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

	public void accept(Visitor visitor){
		visitor.visit(this);
	}
}

class tokenizer implements Visitable{
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

	public void accept(Visitor visitor){
		visitor.visit(this);
	}
}

class parser implements Visitable{
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

	public void accept(Visitor visitor){
		visitor.visit(this);
	}
}

class getCmd implements Visitable{
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

	public void accept(Visitor visitor){
		visitor.visit(this);
	}	
}

class initialize implements Visitor{
	String args[];
	String option;
	FileInputStream file;

	initialize(String arguments[]){
		args = arguments;
	}

	public FileInputStream visit(getCmd getcmd){
		getcmd.getargs(args);
		if(!getcmd.checkIfValid(args))
			System.exit(1);
		file = getcmd.readFile(args[0]);
		option = getcmd.getOption();
		if(file == null)
			System.exit(1);
		return file;
	}

	public String visit(tokenizer tok){
		return "";
	}

	public void visit(htmlHandler handler){
		handler.init("output.html");
		return;
	}

	public String visit(parser parse){
		return "";
	}
}

class mainProgram implements Visitor{
	FileInputStream mdFile;
	String line;
	String token;
	mainProgram(FileInputStream f){
		mdFile = f;
	}

	public FileInputStream visit(getCmd getcmd){
		return null;
	}

	public String visit(tokenizer tok){
		token = tok.whatToken(line);
		return token;
	}

	public void visit(htmlHandler handler){
		int num = handler.isItSetext(line);
		if(num != 3)
			line = "";
		handler.setextHeader(num);
		handler.writeFin();
		handler.closeSetextHeader(num);
		if(handler.isHorizontalRule(line))
			return;
		// Starting condition checker
		if(token.startsWith("h") || token.equals("ul") || token.equals("ol") || token.equals("bq")){
			line = handler.writeHtml(1, token, line);
		}
		else{
			handler.decide();
			handler.hasHeader = "";
		}
		if(token.equals("pg")){
			handler.paragraph();
		}
		// Check through the line
		while(true){
			line = handler.analyze(line);
			if(line.equals(""))
				break;
		}
		handler.endOfLine();
		if(token.equals("")){
			line = handler.writeHtml(1, token, line);
			handler.writeHtml(2, "", line);
		}
		return;
	}

	public String visit(parser parse){
		String s = "";
		if(!parse.isItEnd())
			s = parse.parse(mdFile);
		line = s;
		return s;
	}
}

class finish implements Visitor{
	public FileInputStream visit(getCmd getcmd){
		return null;
	}

	public String visit(tokenizer tok){
		return "";
	}

	public void visit(htmlHandler handler){
		handler.closeHtml();
		return;
	}

	public String visit(parser parse){
		return "";
	}
}

public class vp {
   public static void main(String[] args) {
   		FileInputStream mdFile;
   		initialize init = new initialize(args);
   		mdFile = init.visit(new getCmd());
   		htmlHandler html = new htmlHandler();
   		// INITIATE
   		init.visit(html);
   		init.visit(new tokenizer());
   		init.visit(new parser());

   		// PARSE
   		mainProgram mainP = new mainProgram(mdFile);
   		mainP.visit(new getCmd());
   		parser p = new parser();
   		String s = "INITIAL";

   		while(true){
   			s = mainP.visit(p);
   			if(s.equals(""))
   				break;
   			mainP.visit(new tokenizer());
   			mainP.visit(html);
   		}

   		// FINISH
   		finish fin = new finish();
   		fin.visit(html);
   		fin.visit(new tokenizer());
   		fin.visit(new getCmd());
   		fin.visit(p);
		System.out.println("End of the program");
   }
}