package io.github.miguelangelprogramacion;

import java.io.IOException;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.github.miguelangelprogramacion.client.SocialNetworkClient;
import io.github.miguelangelprogramacion.client.service.ResponseService;
import io.github.miguelangelprogramacion.dto.response.IResponse;
import io.github.miguelangelprogramacion.dto.response.Message;

public class AppSocialNetworkClient {
	private static final Logger logger = LogManager.getLogger(AppSocialNetworkClient.class.getName());
	private static ResponseService responseService = new ResponseService();
	
	public static void main(String[] args) {
		logger.info("\n Welcome to the social network !!!! \n\n");

		SocialNetworkClient socialNetworkClient = new SocialNetworkClient("localhost", 8887);
		Scanner scanner = null;
		IResponse response = new Message("Welcome");
		try {
			while (response != null) {
				logger.info(" > ");
				scanner = new Scanner(System.in);
				String command = scanner.nextLine();
				socialNetworkClient.startConnection();
				response = socialNetworkClient.sendMessage(command);
				String stringResponse = responseService.processCommand(response);	
				if(stringResponse!=null) logger.info(stringResponse);
			}
			socialNetworkClient.stopConnection();

		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		scanner.close();
		logger.info("\n Goodbye");
	}
}
