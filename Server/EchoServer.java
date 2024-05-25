// package com.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


public class EchoServer {
    /**
     * @param args the port number
     */
    public static void main(String[] args) {

        if (args.length < 1) {
            System.err.println("provide server port as command line arg");
            System.exit(1);
        }

        final int PORT_NUMBER = Integer.parseInt(args[0]);
        System.out.println("Server listening on port " + PORT_NUMBER);

        // sockets and streams close automatically when established in a try-resources statement
        try (ServerSocket serverSocket = new ServerSocket(PORT_NUMBER); // Create and open server socket
            Socket clientSocket = serverSocket.accept(); // wait for the client request/connection with server, the server communicates with the client via this object
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true); // input/output streams to the socket
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));) {
                System.out.println("Client connected on port " + PORT_NUMBER);
                String inp = in.readLine(); // receive data from client
                while (inp != null) {
                    System.out.println("Received message " + inp + " from " + clientSocket.toString());
                    out.println(inp); // send data to the client
                    inp = in.readLine();
                }

            } catch (IOException e) {
                System.err.println("Error establishing connection on port " + PORT_NUMBER);
            }        
    }
}
