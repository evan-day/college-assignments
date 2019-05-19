import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIServerInterface extends Remote {
    String[] sendDirectoryToClient() throws RemoteException;
    byte[] sendRequestedFileToClient(String fileNameReceived) throws RemoteException;
    void receiveRequestedFileFromClient(String fileNameReceived, byte[] receivedFileData) throws RemoteException;
}
