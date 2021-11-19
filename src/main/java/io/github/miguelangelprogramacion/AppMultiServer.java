package io.github.miguelangelprogramacion;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.github.miguelangelprogramacion.server.MultiServer;

public class AppMultiServer {
	
	private static final Logger logger = LogManager.getLogger(AppMultiServer.class.getName());
	
	public static void main(String[] args) {
		String mongoHost = "localhost";
		int mongoPort = 27017;
		logger.info("Waiting clients !!!!");
		
		MultiServer p;
		try {
			p = new MultiServer(mongoHost, mongoPort);
			p.start(8887);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
