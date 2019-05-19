import javafx.stage.Stage;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIClientInterface extends Remote {
    void buildingLocalDirectory();
    void buildingRemoteDirectory();
    void refreshLocalDirectory();
    void findingSelectedRemoteFileName();
    void findingSelectedLocalFileName();
    void serverRequestDirectory();
    void serverRequestDownloadFile();
    void serverRequestUploadFile();
    void serverRequestDisconnect();
    void start(Stage primaryStage) throws RemoteException;
}
