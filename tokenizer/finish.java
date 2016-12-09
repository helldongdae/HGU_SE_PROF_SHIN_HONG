package se.markdowm;

import java.io.FileInputStream;

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
