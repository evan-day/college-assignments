//Evan Day - R00139868 - CS3 - Distributed Systems Programming

import java.io.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.media.MediaException;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.collections.ObservableList;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.File;

public class MyRMIClient extends Application implements RMIClientInterface {

	private String serverHost = "localhost";
	private int serverPort = 8000;

	private String selectedRemoteFile = "";
	private String selectedLocalFile = "";

	private ListView<String> remoteDirectoryListView;
	private ListView<String> localDirectoryListView;


	private MediaPlayer mainMediaPlayer;

	private String[] serverDirectory;

	private String[] localDirectoryListing;

	private TextArea clientTextOutput;

	private GridPane primaryLocalClientGridPane;

	private static File target = new File("/home/ubuntu/IdeaProjects/distsysprog/assignment-two/local-server//");
	

	private ArrayList<String> localDirectoryArrayList;

	private ObservableList<String> localDirectoryObservableList;

	RMIServerInterface clientServerConnection;


	public static void main(String[] args) {
		launch();

	}
	public void start(Stage primaryStage) throws RemoteException {
		//Local Variable Creation Starts
		Scene primaryScene;

		Stage primaryWindow;

		//Start Method Call For Building Local File Directory
		buildingLocalDirectory();
		//End Method Call For Building Local File Directory
		//Start Method Calls For Identifying Selected Items
		findingSelectedLocalFileName();
		//findingSelectedRemoteFileName();
		//End Method Calls For Identifying Selected Items
		//Start Stage Configuration
		primaryWindow = primaryStage;
		//End Stage Configuration

		//	Start RMI Registry	//
		try {
			Registry clientRegistry;
			clientRegistry = LocateRegistry.getRegistry(serverPort);
			clientServerConnection = (RMIServerInterface)clientRegistry.lookup(serverHost);

		}
		catch (NotBoundException e16) {
			e16.printStackTrace();
		}

		//	End RMI Registry	//

		//											       //
		//    JavaFX Client Interface Configuration Starts //
		//											       //

		//Local Client GridPane Configuration Starts
		primaryLocalClientGridPane = new GridPane();
		primaryLocalClientGridPane.setPadding(new Insets(20, 20, 20, 20));
		primaryLocalClientGridPane.setVgap(16);
		primaryLocalClientGridPane.setHgap(20);
		primaryLocalClientGridPane.setAlignment(Pos.CENTER);
		//Local Client GridPane Configuration Ends
		//Stage And Scene Configuration Starts
		primaryScene = new Scene(primaryLocalClientGridPane, 640, 640);
		primaryWindow.setScene(primaryScene);
		primaryWindow.setTitle("Client Interface - Evan Day - R00139868 - CS3 - Distributed Systems Programming");
		primaryWindow.show();
		//Stage and Scene Configuration Ends
		//Client Directory Label Configuration Starts
		Label remoteServerLabel = new Label ("Your Current Directory");
		GridPane.setConstraints(remoteServerLabel,1,1);
		//Remote Server Label Configuration Ends
		//Local Server Label Configuration Starts
		Label localServerLabel = new Label ("Remote Server Directory");
		GridPane.setConstraints(localServerLabel,2,1);
		//Local Server Label Configuration Ends
		//Local Server ListView Configuration Starts
		localDirectoryListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		localDirectoryListView.setPrefWidth(160);
		localDirectoryListView.setPrefHeight(320);
		GridPane.setConstraints(localDirectoryListView, 1, 2);
		//Local Server ListView Configuration Ends
		//Start Request Server Directory Button
		Button requestServerDirectoryButton = new Button("Request Server Directory");
		requestServerDirectoryButton.setOnAction(e -> {
			serverRequestDirectory();
		});
		GridPane.setConstraints(requestServerDirectoryButton,1,3);
		//End Request Server Directory Button
		//Start Download Selected File Button
		Button downloadFromRemoteDirectoryButton = new Button("Download File From Remote Directory");
		downloadFromRemoteDirectoryButton.setOnAction(e -> {
			serverRequestDownloadFile();
		});
		GridPane.setConstraints(downloadFromRemoteDirectoryButton,2,3);
		//End Start Download Selected File Button
		//Start Upload Selected File Button
		Button uploadToRemoteDirectoryButton = new Button("Upload File To Remote Directory");
		uploadToRemoteDirectoryButton.setOnAction(e -> {
			serverRequestUploadFile();
		});
		GridPane.setConstraints(uploadToRemoteDirectoryButton,1,4);
		//End Upload Selected File Button
		//Start Play Selected File Button
		Button playSelectedFileButton = new Button("Play Selected File");
		playSelectedFileButton.setOnAction(e -> {
            try {
                String mediaToPlay = target + "\\" + selectedLocalFile;
                Media soundToPlay = new Media((new File(mediaToPlay).toURI().toString()));
                mainMediaPlayer = new MediaPlayer(soundToPlay);
                mainMediaPlayer.play();
            }
            catch(MediaException incorrectDirectory) {
                System.out.println("Error - Attempting to play from the Remote directory when you should be playing from the local directory");
                incorrectDirectory.printStackTrace();
            }
		});
		GridPane.setConstraints(playSelectedFileButton,2,4);
		//End Start Play Selected File Button
        //Start Pause Selected File Button
        Button pauseSelectedFileButton = new Button("Pause Playing File");
        pauseSelectedFileButton.setOnAction(e -> {
            mainMediaPlayer.stop();
        });
        GridPane.setConstraints(pauseSelectedFileButton,2,5);
        //Start Refresh Local Directory Button
        Button refreshLocalDirectory = new Button("Refresh Local Directory");
        refreshLocalDirectory.setOnAction(e -> {
            refreshLocalDirectory();
        });
        GridPane.setConstraints(refreshLocalDirectory,1,7);
        //End Refresh Local Directory Button
		//Start Disconnect From Server Button
		Button disconnectFromRemoteButton = new Button("Disconnect From Server");
		disconnectFromRemoteButton.setOnAction(e -> {
			serverRequestDisconnect();
		});
		GridPane.setConstraints(disconnectFromRemoteButton,1,5);
		//End Disconnect From Server Button
		//Start Client TextField Area
		clientTextOutput = new TextArea();
		clientTextOutput.setEditable(false);
		clientTextOutput.setWrapText(true);
		clientTextOutput.setPrefWidth(320);
		clientTextOutput.setPrefHeight(160);
		GridPane.setConstraints(clientTextOutput,1,9);
		//Start Local Client GridPane Configuration
		primaryLocalClientGridPane.getChildren().addAll(remoteServerLabel,localServerLabel,localDirectoryListView, requestServerDirectoryButton, downloadFromRemoteDirectoryButton, uploadToRemoteDirectoryButton, playSelectedFileButton, pauseSelectedFileButton, refreshLocalDirectory, disconnectFromRemoteButton, clientTextOutput);
		//End Local Client GridPane Configuration

		//											       //
		//    JavaFX Client Interface Configuration Ends   //
		//											       //
		
		try {

			Platform.runLater(() -> clientTextOutput.appendText("Client Has Successfully Connected!\n"));
		}
		catch(Exception e9) {
			e9.printStackTrace();
		}
	}
	public void buildingLocalDirectory() {
		//Setting Array to be list of files within directory
		localDirectoryListing = target.list();
		//Declaring a new ArrayList while setting its contents to be the previous String Array
		localDirectoryArrayList = new ArrayList<>(Arrays.asList(localDirectoryListing));
		//Creating a new observableList using the ArrayList created for the listView to listen to
		localDirectoryObservableList = FXCollections.observableArrayList(localDirectoryArrayList);
		//Creating a listview object with the observableList created as the listener.
		localDirectoryListView = new ListView<>(localDirectoryObservableList);
	}
	public void buildingRemoteDirectory() {
        String[] remoteDirectoryListing;

        remoteDirectoryListing = serverDirectory;

        ArrayList<String> remoteDirectoryArrayList;
        //ObservableList Object
        ObservableList<String> remoteDirectoryObservableList;
        remoteDirectoryArrayList = new ArrayList<>(Arrays.asList(remoteDirectoryListing));
        remoteDirectoryObservableList = FXCollections.observableArrayList(remoteDirectoryArrayList);
        remoteDirectoryListView = new ListView<>(remoteDirectoryObservableList);
        Platform.runLater(() -> {
            remoteDirectoryListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
            remoteDirectoryListView.setPrefWidth(160);
            remoteDirectoryListView.setPrefHeight(320);
            primaryLocalClientGridPane.add(remoteDirectoryListView,2,2);
			findingSelectedRemoteFileName();
        });
    }
	public void refreshLocalDirectory() {
		localDirectoryArrayList.clear();
		localDirectoryListing = target.list();
        //Declaring a new ArrayList while setting its contents to be the previous String Array
        localDirectoryArrayList = new ArrayList<>(Arrays.asList(localDirectoryListing));
        //Creating a new observableList using the ArrayList created for the listView to listen to
        localDirectoryObservableList = FXCollections.observableArrayList(localDirectoryArrayList);
        //Creating a listview object with the observableList created as the listener.
        Platform.runLater( () -> {
            localDirectoryListView.setItems(localDirectoryObservableList);
        });
	}
	public void findingSelectedRemoteFileName() {
		selectedRemoteFile = "";

		remoteDirectoryListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue)
			{
				selectedRemoteFile = newValue;

			}
		});

	}
	public void findingSelectedLocalFileName() {
		selectedLocalFile = "";

		localDirectoryListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue)
			{
				selectedLocalFile = newValue;

			}
		});

	}
	public void serverRequestDirectory() {
		new Thread( () -> {
			try {
				serverDirectory = clientServerConnection.sendDirectoryToClient();

				buildingRemoteDirectory();

				Platform.runLater(() -> clientTextOutput.appendText("Successfully Retrieved Server Directory!\n"));
			}
			catch(IOException requestServerDirectoryException) {
                System.out.println("An Error Occurred Requesting The Server Directory");
                requestServerDirectoryException.printStackTrace();
            }

		}
		).start();
	}
	public void serverRequestDownloadFile() {
		new Thread( () -> {
			try {
				byte[] receivedData = clientServerConnection.sendRequestedFileToClient(selectedRemoteFile);

				FileOutputStream fileWriter;

				File fileToSave = new File (target +  "/" + selectedRemoteFile);

				fileWriter = new FileOutputStream(fileToSave);
				fileWriter.write(receivedData);

				fileWriter.close();

				refreshLocalDirectory();
			}
			catch(IOException downloadingFromDirectoryException) {
				System.out.println("An Error Occurred Downloading The Requested File");
				downloadingFromDirectoryException.printStackTrace();
			}
		}
		).start();
	}
	public void serverRequestUploadFile() {
		new Thread( () -> {
			try {
				File fileToUpload = new File(target + "/" + selectedLocalFile);

				FileInputStream fileOpener;

				fileOpener = new FileInputStream(fileToUpload);

				byte[] fileContent = new byte[(int) fileToUpload.length()];

				fileOpener.read(fileContent);

				clientServerConnection.receiveRequestedFileFromClient(selectedLocalFile, fileContent);

				fileOpener.close();

				serverRequestDirectory();
				//Continue Building Uploading File
			}
			catch(IOException uploadingToDirectoryException) {
				System.out.println("An Error Occurred Uploading The Requested File");
				uploadingToDirectoryException.printStackTrace();
			}
		}
		).start();
	}
	public void serverRequestDisconnect() {
		Platform.exit();
	}
}
