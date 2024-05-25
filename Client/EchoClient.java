package Client; // TODO ???

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class EchoClient {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        if (args.length != 2) {
            System.err.println("Provide arguments for host-name and port number");
            System.exit(0);
        }
        
        final String hostName = args[0]; // client machine
        final int portNumber = Integer.parseInt(args[1]); // the server's port

	    try (Socket echoSocket = new Socket(hostName, portNumber); // create and open client socket
            PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true); // input and output streams from server
            BufferedReader in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));) { // input stream from client
                String inp = stdIn.readLine(); // read data from server
                while (inp != null) {
                    out.println(inp); // send data to the server
                    System.out.println("echo: " + inp);
                    inp = stdIn.readLine();
                }
            } catch (UnknownHostException e) {
                System.err.println("Failed to recognise host " + hostName);
                System.exit(1);
            } 
            catch (IOException e) {
                System.err.println("Failed to establish I/O connection on " + hostName);
                System.exit(1);
        }
    }  
}
