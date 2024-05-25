// package com.example;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    public static void main(String[] args) {

        /*
        if (args.length < 1) {
            System.err.println("provide server port as command line arg");
            System.exit(1);
        }

        final int PORT_NUMBER = Integer.parseInt(args[0]);
        */

        final int PORT_NUMBER = 8004;
        System.out.println("Server listening on port " + PORT_NUMBER);

        ExecutorService executor = null;

	    try (ServerSocket serverSocket = new ServerSocket(PORT_NUMBER);) { // Open a server socket and automatically close it when done
            executor = Executors.newFixedThreadPool(5); // Create a thread pool with 5 threads for handling requests
            System.out.println("Waiting for clients...");
            while (true) { // repeatedly check for new clients
                Socket clientSocket = serverSocket.accept(); // wait and accept connection with client
                Runnable worker = new RequestHandler(clientSocket); // put the client connection into a runnable so it can be put into a thread
                executor.execute(worker); // Submit the client handling task to the thread pool for execution
            }
        } catch (IOException e) {
            System.err.println("Exception while listening to port " + PORT_NUMBER + " or establishing connection");
            System.exit(1);
        } finally {
            if (executor != null) {
                executor.shutdown();
            }
        }
    }
    
}
