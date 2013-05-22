package remote;

import java.rmi.Remote;

public interface clientInterface extends Remote{
	
	void pullFinished();
}
