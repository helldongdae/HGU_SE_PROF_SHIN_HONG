package se.markdowm;

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