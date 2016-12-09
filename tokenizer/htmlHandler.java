package se.markdowm;

import java.io.FileWriter;

class htmlHandler implements Visitable{
	FileWriter out;
	String has_header = "";
	boolean isOrderList = false;
	boolean isBlockQuote = false;
	boolean isParagraph = true;

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
				try{
					out.write("<" + token + ">");
				}
				catch(Exception e){

				}
				int n = Integer.parseInt(token.substring(1));
				has_header = token;
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
					out.write("<ul><li>");
					has_header = "ul";
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
						out.write("<li>");
						has_header = "ol";
						return sentence.substring(sentence.indexOf(". ") + 2);
					}
					catch(Exception e){

					}
				}
				else{
					isOrderList = true;
					try{
						out.write("<ol>\n<li>");
						has_header = "ol";
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
					try{
						out.write("<blockquote>\n");
						has_header = "bq";
						isBlockQuote = true;
						return sentence.substring(1);
					}
					catch(Exception e){

					}
				}
			}
			else{
				if(isOrderList){
					endOfOrderList();
					isOrderList = false;
				}
				if(isBlockQuote){
					endOfBlockQuote();
					isBlockQuote = false;
				}
				return sentence;
			}
		}
		// Just write down the sentence
		if(flag == 2){
			try{
				out.write(sentence);
			}
			catch(Exception e){
			
			}	
		}
		return "";
	}

	void horizontalRule(){
		try{
			out.write("<hr>\n");
		}
		catch(Exception e){
		
		}
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
		if(has_header.startsWith("h")){
			try{
				out.write("</" + has_header + ">\n");
			}
			catch(Exception e){

			}
		}
		else if(has_header.equals("ul")){
			try{
				out.write("</li></ul>\n");
			}
			catch(Exception e){
				
			}
		}
		else if(has_header.equals("ol")){
			try{
				out.write("</li>\n");
			}
			catch(Exception e){
				
			}
		}
	}

	void endOfOrderList(){
		try{
			out.write("</ol>\n");
		}
		catch(Exception e){
				
		}
	}

	void endOfBlockQuote(){
		try{
			out.write("</blockquote>\n");
		}
		catch(Exception e){
				
		}
	}

	void newLineHtml(){
		try{
			out.write("<br>");
		}
		catch(Exception e){
				
		}
	}

	String analyze(String s){
		int n1 = s.indexOf("**");
		if(n1 < 0)
			n1 = 999999;
		int n2 = s.indexOf("*");
		if(n2 < 0)
			n2 = 999999;
		int n3 = s.indexOf("__");
		if(n3 < 0)
			n3 = 999999;
		int n4 = s.indexOf("_");
		if(n4 < 0)
			n4 = 999999;
		int n5 = s.indexOf("~~");
		if(n5 < 0)
			n5 = 999999;
		int n6 = s.indexOf("[");
		if(n6 < 0)
			n6 = 999999;
		int n7 = s.indexOf("(");
		if(n7 < 0)
			n7 = 999999;
		int nMin = Math.min(n1, Math.min(n2, Math.min(n3, Math.min(n4, Math.min(n5, Math.min(n6, n7))))));
		if(nMin == 999999)
			nMin = nMin+1;
		// Bold
		if(nMin == n1){
			writeHtml(2, "", s.substring(0, s.indexOf("**")));
			s = s.substring(s.indexOf("**") + 2);
			if(s.indexOf("**") != -1){
				try{
					out.write("<strong>");
					out.write(s.substring(0, s.indexOf("**")));
					out.write("</strong>");
				}
				catch(Exception e){

				}
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
				try{
					out.write("<strong>");
					out.write(s.substring(0, s.indexOf("__")));
					out.write("</strong>");
				}
				catch(Exception e){

				}
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
				try{
					out.write("<em>");
					out.write(s.substring(0, s.indexOf("*")));
					out.write("</em>");
				}
				catch(Exception e){

				}
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
				try{
					out.write("<em>");
					out.write(s.substring(0, s.indexOf("_")));
					out.write("</em>");
				}
				catch(Exception e){

				}
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
				try{
					out.write("<strike>");
					out.write(s.substring(0, s.indexOf("~~")));
					out.write("</strike>");
				}
				catch(Exception e){

				}
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
				try{
					if(s.indexOf(")") != -1){
						String txt = s.substring(0, s.indexOf("]("));
						s = s.substring(s.indexOf("](") + 2);
						String link = s.substring(0, s.indexOf(")"));
						out.write("<a href=");
						out.write("\"" + link + "\"");
						out.write(">" + txt + "</a>");
						return s.substring(s.indexOf(")") + 1);
					}
					else{
						writeHtml(2, "", s.substring(0, s.indexOf("])") + 2));
						return s.substring(s.indexOf("](") + 2);
					}
				}
				catch(Exception e){

				}
				return s.substring(s.indexOf("~~") + 2);
			}
			else{
				writeHtml(2, "", "~~");
				return s;
			}
		}
		// link w/o inline style
		else if(nMin == n7){

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
        try{
            String s = "\n</p>\n</body></html>";
            out.write(s);
            out.close();
        }
         catch(Exception e){
            System.out.println("HTML CLOSE ERROR");
        }
    }

    void paragraph(){
    	if(isOrderList){
			endOfOrderList();
			isOrderList = false;
		}
		if(isBlockQuote){
			endOfBlockQuote();
			isBlockQuote = false;
		}
    	try{
           out.write("</p><p>");
        }
        catch(Exception e){
            
        }
    }

	public void accept(Visitor visitor){
		visitor.visit(this);
	}
}