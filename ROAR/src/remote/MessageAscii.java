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
	private static final long serialVersionUID = 0xDEADC0FE;
	List<String> arts;

	public MessageAscii(long iD, String autho, String cont, List<String> arts) {
		super(iD, autho, cont);
		this.arts = arts;
	}

	@Override
	public String toString(){
		
		String mes = super.toString();
		Pattern p = Pattern .compile("(::[^:]+::)");
		String res = "";
		String after="";
		int i = 0;
		
		while ( i< arts.size()){
			Matcher m = p.matcher(mes);
			m.find();
			String before = mes.substring(0, m.start());
			after = mes.substring(m.end(), mes.length());
			mes = after;
			
			res += before + "\n" + arts.get(i) + "\n";

			++i;
		}
		
		res += after;

		return res;
	}
	
	@Override
	public int compareTo(Message o) {
		
		return o.getDate().compareTo(getDate());
	}

}
