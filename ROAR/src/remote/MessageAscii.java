package remote;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageAscii extends Message implements Serializable{ 

	/**
	 * 
	 */
	private static final long serialVersionUID = 0xDEADCAFE;
	List<String> arts;

	public MessageAscii(long iD, String autho, String cont, List<String> arts) {
		super(iD, autho, cont);
		this.arts = new ArrayList<String>();
		this.arts = arts;
	}

	@Override
	public String toString(){
		
		String mes = super.toString();
		
		Pattern p = Pattern .compile("(::[^:]+::)");

		Matcher m = p.matcher(mes);
		
		int i = 0;
		
		while (m.find() && i< arts.size()){
			
			String before = mes.substring(0, m.start());
			String after = mes.substring(m.end(), mes.length());
			
			mes = before + "\n" + arts.get(i) + "\n" + after;
			
			System.out.println(mes);
			++i;
		}

		return mes;
	}

}
