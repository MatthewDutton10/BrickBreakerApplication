
// package com.example;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class RequestHandler implements Runnable {
    private final Socket client;
    ServerSocket serverSocket = null;

    public RequestHandler(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));) {
                System.out.println("Thread started with thread name: " + Thread.currentThread().getName());
                String inp = in.readLine();
                while (inp != null) {
                    System.out.println("Received message from " + Thread.currentThread().getName() + ": " + inp);
                    writer.write("You entered: " + inp);
                    writer.newLine();
                    writer.flush();
                    inp = in.readLine();
                }
        } catch (IOException e) {
            System.out.println("IO Exception " + e);
        } catch (Exception e) {
            System.out.println("Exception " + e);
        }
            
    }
}
