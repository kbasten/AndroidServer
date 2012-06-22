package com.kbasten.androidserver;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class Server {
	private CommandParser cp;
	private Log l;

	private int port;
	private boolean isRunning = false;
	private boolean listening = false; // listen for incoming connections

	private ServerSocket ssocket = null;

	private HashMap<String, Socket> connClients = new HashMap<String, Socket>();

	public Server(Log l, int port) {
		this.l = l;
		this.listening = true;

		this.port = port;
	}

	public void startServer() throws IOException, InterruptedException {
		l.msg("Starting server...");
		// prepare commandparser
		cp = new CommandParser();

		try {
			InetSocketAddress isa = new InetSocketAddress(port);

			ssocket = new ServerSocket();
			ssocket.setReuseAddress(true);
			ssocket.bind(isa);
			l.msg("Listening on port " + port);
			isRunning = true;
		} catch (IOException e) {
			l.msg("Could not listen on port: " + port);
			l.msg(e.getMessage());
			return;
		}

		while (listening) {
			try {
				Socket clientSocket = ssocket.accept();

				String socketId = clientSocket.toString();
				if (connClients.containsKey(socketId)) {
					l.msg("Client already connected, connection refused.");
				} else {
					l.msg("New client: " + clientSocket.toString());
					connClients.put(socketId, clientSocket);

					ServerThread st = new ServerThread(clientSocket, l, cp);
					Thread t = new Thread(st);
					t.start();
				}
			} catch (IOException e) {
				l.msg("Accept failed.");
			}
		}

		ssocket.close();

		l.msg("Closing server");
		isRunning = false;
	}

	public void stopRunning() {
		this.listening = false;
		try {
			ssocket.close();
		} catch (IOException e) {
		}
		this.isRunning = false;
	}

	public boolean isRunning() {
		return this.isRunning;
	}
}
