import javafx.stage.Stage;

public interface ClientInterface {
    void buildingLocalDirectory();
    void buildingRemoteDirectory();
    void refreshLocalDirectory();
    void findingSelectedRemoteFileName();
    void findingSelectedLocalFileName();
    void serverRequestDirectory();
    void serverRequestDownloadFile();
    void serverRequestUploadFile();
    void serverRequestDisconnect();
    void start(Stage primaryStage);
}
