package io.github.miguelangelprogramacion.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import io.github.miguelangelprogramacion.dto.command.CommandsFactory;
import io.github.miguelangelprogramacion.dto.command.ICommand;
import io.github.miguelangelprogramacion.dto.response.IResponse;
public class SocialNetworkClient {
		
	private String hostName;
	private int portNumber;
	
	private Socket clientSocket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    
	public SocialNetworkClient(String hostName, int portNumber) {
		super();
		this.hostName = hostName;
		this.portNumber = portNumber;
	}
    public void startConnection() throws IOException {
        clientSocket = new Socket(this.hostName, this.portNumber);
        out = new ObjectOutputStream(clientSocket.getOutputStream());
        in = new ObjectInputStream(clientSocket.getInputStream());
    }

	public IResponse sendMessage(String command) throws IOException, ClassNotFoundException {
       
		ICommand  iCommand = CommandsFactory.getCommand(command);
		out.writeObject(iCommand);

        return (IResponse)in.readObject();
    }

    public void stopConnection() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
    }
}
