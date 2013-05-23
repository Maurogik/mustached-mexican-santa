package core;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import remote.InterfacePrivee;
import remote.Message;
import remote.clientInterface;

public class AccesPrive extends AccesPublic implements InterfacePrivee, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 0xDEADFACE;
	private String userName;
	
	public AccesPrive(String user) throws RemoteException{
		super();
		userName = user;
	}
	public AccesPrive() throws RemoteException{
		super();
		userName = null;
	}
	
	@Override
	public List<Message> getUserMessages(clientInterface cl) throws RemoteException {
		
		return server.getUserMessages(userName, 10, cl);
	}

	@Override
	public void postMessage(String mes) throws RemoteException {
		
		List<String> recip = new ArrayList<String>();
		List<String> hashT = new ArrayList<String>();
		
		try{
			
			   Pattern p = Pattern .compile("@([a-z]|[A-Z]|[0-9])+");
			  
			   Matcher m = p.matcher(mes);
			   
			   while (m.find()){
				   
				  recip.add(mes.substring(m.start()+1, m.end()));
			      System.out.println(mes.substring(m.start()+1, m.end()));
			      
			   }
			   
			   
			   Pattern p2 = Pattern .compile("#([a-z]|[A-Z]|[0-9])+");
				  
			   Matcher m2 = p2.matcher(mes);
			   
			   while (m2.find()){
				   
				  hashT.add(mes.substring(m2.start()+1, m2.end()));
			      System.out.println(mes.substring(m2.start(), m2.end()));
			      
			   }
			   
		}catch(PatternSyntaxException pse){
		}
		
		server.postMessage(mes, userName, recip, hashT);
	}

	@Override
	public void relayerMessage(long mesID) throws RemoteException {
		
		server.relayerMessage(userName, mesID);
	}

	@Override
	public void follow(String userToFollow) throws RemoteException {
		
		server.addFollowRelationship(userName, userToFollow);
	} 

	@Override
	public void addInterest(String interest) throws RemoteException {
		
		server.addInterest(userName, interest);
	}

	@Override
	public String getName() throws RemoteException {
		
		return userName;
	}

	@Override
	public List<String> getInsterest() throws RemoteException {
		
		return server.getInsterestOf(userName);
	}

}
