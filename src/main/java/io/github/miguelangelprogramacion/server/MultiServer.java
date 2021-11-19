package io.github.miguelangelprogramacion.server;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.github.miguelangelprogramacion.dto.command.ICommand;
import io.github.miguelangelprogramacion.dto.response.IResponse;
import io.github.miguelangelprogramacion.server.service.CommandService;
import io.github.miguelangelprogramacion.server.service.IServerService;

public class MultiServer {

	private static final Logger logger = LogManager.getLogger(MultiServer.class.getName());
	boolean running;
	private IServerService service = null;

	public MultiServer(String mongoHost, int mongoPort) {
		super();
		service = new CommandService(mongoHost, mongoPort);
	}

	public void start(int port) {
		running = true;
		try (ServerSocket serverSocket = new ServerSocket(port);) {
			while (running)
				new ClientHandler(serverSocket.accept(), service).start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("peta por aquí?");
		}

	}

	public void stop() {
		running = false;
	}

	private static class ClientHandler extends Thread {
		private Socket clientSocket;
//		private ObjectOutputStream out;
//		private ObjectInputStream in;
		private IServerService service;

		public ClientHandler(Socket socket, IServerService service) {
			this.clientSocket = socket;
			this.service = service;
		}

		@Override
		public void run() {

			try (ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
					ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());) {

				ICommand inputObject;
				IResponse outputObject;
				while ((inputObject = (ICommand) in.readObject()) != null) {
					outputObject = service.processCommand(inputObject);
					out.writeObject(outputObject);
				}
			}catch(EOFException e) {
				logger.info("client ends session \n");
			} 
			catch (IOException | ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				clientSocket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

}
