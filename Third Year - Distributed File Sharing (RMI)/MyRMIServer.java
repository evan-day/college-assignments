//Evan Day - R00139868 - CS3 - Distributed Systems Programming

import java.io.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;

public class MyRMIServer extends UnicastRemoteObject implements RMIServerInterface {
	private static final File target = new File("/home/ubuntu/IdeaProjects/distsysprog/assignment-two/remote-server/");

	MyRMIServer() throws RemoteException {
		super();
	}
	public static void main(String args[]) {
		int portNumber = 8000;

		// Start RMI Registry //
		String serverAddress = "localhost";

		try {
			Registry serverRegistry = LocateRegistry.createRegistry(portNumber);
			RMIServerInterface myServer = new MyRMIServer();
			serverRegistry.rebind(serverAddress, myServer);
		}
		catch (RemoteException e13) {
			e13.printStackTrace();
		}
		// End RMI Registry   //
	}
	public String[] sendDirectoryToClient() throws RemoteException {
		String[] serverDirectoryToSend;

		serverDirectoryToSend = target.list();

		return serverDirectoryToSend;

	}
	public byte[] sendRequestedFileToClient(String fileNameReceived) throws RemoteException {
		File fileToDownload = new File (target + "/" + fileNameReceived);

		FileInputStream fileOpener;

		byte[] fileContent = new byte[1024];

		try {
			fileOpener = new FileInputStream(fileToDownload);

			fileContent = new byte[(int) fileToDownload.length()];

			fileOpener.read(fileContent);

			fileOpener.close();

			return fileContent;
		}
		catch (IOException errorOpeningFile) {
			System.out.println("An Error Occurred!");
			errorOpeningFile.printStackTrace();
		}
		return fileContent;
	}
	public void receiveRequestedFileFromClient(String fileNameReceived, byte[] receivedData) throws RemoteException {
		try {
				FileOutputStream fileWriter;

				File fileToSave = new File (target + "/" + fileNameReceived);

				fileWriter = new FileOutputStream(fileToSave);
				fileWriter.write(receivedData);

				fileWriter.close();

				//Code for refreshing the remote server directory
		}
		catch (IOException e15) {
			e15.printStackTrace();
		}
	}
}