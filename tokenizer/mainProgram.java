package se.markdowm;

import java.io.FileInputStream;

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
		// Starting condition checker
		if(token.startsWith("h") || token.equals("ul") || token.equals("ol") || token.equals("bq")){
			line = handler.writeHtml(1, token, line);
		}
		else{
			handler.decide();
			handler.has_header = "";
		}
		if(token.equals("pg")){
			handler.paragraph();
		}
		// Check through the line
		while(true){
			//System.out.println(line);
			line = handler.analyze(line);
			if(line.equals(""))
				break;
		}
		//System.out.println(token);
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