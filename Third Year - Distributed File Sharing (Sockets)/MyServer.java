//Evan Day - R00139868 - CS3 - Distributed Systems Programming

import java.net.*;
import java.io.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.collections.ObservableList;
import java.util.ArrayList;
import java.util.Arrays;

public class MyServer extends Application implements ServerInterface {
	private ListView<String> remoteDirectoryListView;
	ArrayList<String> remoteDirectoryArrayList;
	//Array For Building File Directory
	String[] remoteDirectoryListing;
	
	ObservableList<String> remoteDirectoryObservableList;
	
	private static final File target = new File("H:\\EmbeddedButDistributed\\EmbeddedButDistributed\\src\\remote-server\\");
	
	private ServerSocket serverSocket;
	
	private DataInputStream controlTrafficFromClient;
	private DataOutputStream controlTrafficToClient;

	private ObjectInputStream physicalTrafficFromClient;
	private ObjectOutputStream physicalTrafficToClient;
	
	private int portNumber = 8000;

	private int clientChoice;

	public static void main(String args[]) {
		launch();
	}
	@Override
	public void start(Stage primaryStage) {
		//Local Variable Creation Starts
		Scene primaryScene;

		Stage primaryWindow;


		//Start Method Call For Building Local File Directory
		buildingRemoteDirectory();
		//End Method Call For Building Local File Directory


		//Start Stage Configuration
		primaryWindow = primaryStage;
		//End Stage Configuration

		//											       //
		//    JavaFX Server Interface Configuration Starts //
		//											       //

		//Remote Server GridPane Configuration Starts
		GridPane primaryRemoteServerGridPane = new GridPane();
		primaryRemoteServerGridPane.setPadding(new Insets(20, 20, 20, 20));
		primaryRemoteServerGridPane.setVgap(16);
		primaryRemoteServerGridPane.setHgap(20);
		primaryRemoteServerGridPane.setAlignment(Pos.CENTER);
		//Remote Server GridPane Configuration Ends
		//Stage And Scene Configuration Starts
		primaryScene = new Scene(primaryRemoteServerGridPane, 840,640);
		primaryWindow.setScene(primaryScene);
		primaryWindow.setTitle("Server Interface - Evan Day - R00139868 - CS3 - Distributed Systems Programming");
		primaryWindow.show();
		//Stage And Scene Configuration Ends
		//Remote Server Directory Label Configuration Starts
		Label serverDirectory = new Label("Server Directory");
		GridPane.setConstraints(serverDirectory,2,1);
		//Remote Server Directory Label Configuration Ends
		//Remote Server Listview Configuration Starts
		remoteDirectoryListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		remoteDirectoryListView.setPrefWidth(160);
		remoteDirectoryListView.setPrefHeight(320);
		GridPane.setConstraints(remoteDirectoryListView, 2, 2);
		//Remote Server ListView Configuration Ends
		//Refresh Remote Directory Button Starts
		Button refreshRemoteDirectory = new Button("Refresh Remote Directory");
		refreshRemoteDirectory.setOnAction(e -> {
			refreshRemoteDirectory();
		});
		GridPane.setConstraints(refreshRemoteDirectory,1,3);
		//Refresh Remote Directory Button Ends
		//Shutdown Remote Server Button Starts
		Button shutdownRemoteServer = new Button("Shutdown Remote Server");
		shutdownRemoteServer.setOnAction(e -> {
			try {
				primaryStage.close();
				serverSocket.close();
			}
			catch (IOException excepetionWhenClosingServer)
			{
				System.out.println("An Error Occurred Shutting Down The Server");
			}
		});
		GridPane.setConstraints(shutdownRemoteServer,3,3);
		//Shutdown Remote Server Button Ends
		//Status Updates Label Starts
		Label statusUpdates = new Label("Status Updates");
		GridPane.setConstraints(statusUpdates,2,4);
		//Status Updates Label Ends
		//Server Log TextArea Starts
		TextArea serverLogTextOutput = new TextArea();
		serverLogTextOutput.setEditable(false);
		serverLogTextOutput.setWrapText(true);
		serverLogTextOutput.setPrefWidth(320);
		serverLogTextOutput.setPrefHeight(160);
		GridPane.setConstraints(serverLogTextOutput,2,5);
		//Server Log TextArea Ends
		//Start Remote Server GridPane Configuration
		primaryRemoteServerGridPane.getChildren().addAll(serverDirectory,remoteDirectoryListView,refreshRemoteDirectory,shutdownRemoteServer,statusUpdates,serverLogTextOutput);
		//End Remote Server GridPane Configuration

		//											       //
		//    JavaFX Server Interface Configuration Ends   //
		//											       //
		
		//Thread Responsible for creation server
		new Thread( () -> {
			//Initial Server Connection Creation
			try {
				serverSocket = new ServerSocket(portNumber);

				Platform.runLater(() -> serverLogTextOutput.appendText("Server Started!\n"));
				Platform.runLater(() -> serverLogTextOutput.appendText("Awaiting First Connection!\n"));
				while (true) {
					//Accepting the client connection
					Socket clientSocket = serverSocket.accept();
					
					//Thread responsible for handling client options
					new Thread ( () -> {
						try {
								while (true) {
									Platform.runLater(() ->
									{
										serverLogTextOutput.appendText("Awaiting Next Event\n");
									});

									controlTrafficFromClient = new DataInputStream(clientSocket.getInputStream());

									clientChoice = controlTrafficFromClient.readInt();

									switch(clientChoice) {
										case 1: //Sending Server Directory To Client
										{
											Platform.runLater(() -> {
												serverLogTextOutput.appendText("Client Requested Server Directory\n");
											});
											
											String[] serverDirectoryToSend;

											serverDirectoryToSend = target.list();

											physicalTrafficToClient = new ObjectOutputStream(clientSocket.getOutputStream());

											physicalTrafficToClient.writeObject(serverDirectoryToSend);
											physicalTrafficToClient.flush();
											break;
										}
										case 2: //Sending Requested File To Client
										{
											String fileNameReceived = controlTrafficFromClient.readUTF();

											Platform.runLater(() -> {
												serverLogTextOutput.appendText("Client Download Request For " + fileNameReceived + " From Server");
											});

											File fileToDownload = new File (target + "\\" + fileNameReceived);

											FileInputStream fileOpener;

											try {
													fileOpener = new FileInputStream(fileToDownload);

													byte[] fileContent = new byte[(int) fileToDownload.length()];

													fileOpener.read(fileContent);

													physicalTrafficToClient = new ObjectOutputStream(clientSocket.getOutputStream());

													physicalTrafficToClient.writeObject(fileContent);
													physicalTrafficToClient.flush();

													fileOpener.close();
											}
											catch (IOException errorOpeningFile) {
												System.out.println("An Error Occurred!");
												errorOpeningFile.printStackTrace();
											}
											break;
										}
										case 3: //Receiving Requested File From Client
										{
											String fileNameReceived = controlTrafficFromClient.readUTF();

											Platform.runLater(() ->
											{
												serverLogTextOutput.appendText("Client Upload Request For " + fileNameReceived + " To Server\n");
											});
											try {
													physicalTrafficFromClient = new ObjectInputStream(clientSocket.getInputStream());

													byte[] receivedData = (byte[]) physicalTrafficFromClient.readObject();

													FileOutputStream fileWriter;

													File fileToSave = new File (target + "\\" + fileNameReceived);

													fileWriter = new FileOutputStream(fileToSave);
													fileWriter.write(receivedData);

													fileWriter.close();

													refreshRemoteDirectory();

													//Code for refreshing the remote server directory
											}
											catch (ClassNotFoundException e4) {
												e4.printStackTrace();
											}
											break;
										}
										case 4: //Client Has Disconnected From The Server
										{
											Platform.runLater( () -> {
												serverLogTextOutput.appendText("A Client Has Disconnected From The Server \n");
											});
											break;
										}

									}
							}
						}
						catch (Exception e5) {
							e5.printStackTrace();
						}
					}).start();
				}
			}
			catch(IOException e6) {
				e6.printStackTrace();
			}

		}
		).start();
	}
	public void buildingRemoteDirectory()
	{
		remoteDirectoryListing = target.list();
		//Declaring a new ArrayList while setting its contents to be the previous String Array
		remoteDirectoryArrayList = new ArrayList<>(Arrays.asList(remoteDirectoryListing));
		//Creating a new observableList using the ArrayList created for the listView to listen to
		remoteDirectoryObservableList = FXCollections.observableArrayList(remoteDirectoryArrayList);
		//Creating a listview object with the observableList created as the listener.
		remoteDirectoryListView = new ListView<>(remoteDirectoryObservableList);
	}
	public void refreshRemoteDirectory()
	{
	    remoteDirectoryArrayList.clear();
		remoteDirectoryListing = target.list();
		remoteDirectoryArrayList = new ArrayList<>(Arrays.asList(remoteDirectoryListing));
		remoteDirectoryObservableList = FXCollections.observableArrayList(remoteDirectoryArrayList);
		Platform.runLater(() -> remoteDirectoryListView.setItems(remoteDirectoryObservableList));
		//remoteDirectoryListView.setItems(remoteDirectoryObservableList);
	}
}