package Client;

// package Client; // TODO ???

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

public class EchoClient {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        /*
        if (args.length != 2) {
            System.err.println("Provide arguments for host-name and port number");
            System.exit(0);
        }
        
        final String hostName = args[0]; // client machine
        final int portNumber = Integer.parseInt(args[1]); // the server's port
        */
        // final String hostName = "104.28.222"
        final int portNumber = 8004;

        // gett IP address and set it to the hostName
        String hostName = null;
        try { // TODO only works if client and server are on same machine
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface iface = interfaces.nextElement();
                // filters out 127.0.0.1 and inactive interfaces
                if (iface.isLoopback() || !iface.isUp())
                    continue;
    
                Enumeration<InetAddress> addresses = iface.getInetAddresses();
                while(addresses.hasMoreElements()) {
                    InetAddress addr = addresses.nextElement();
                    hostName = addr.getHostAddress();
                    System.out.println(iface.getDisplayName() + " " + hostName);
                }
            }
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }

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
                e.printStackTrace();
                System.exit(1);
            } 
            catch (IOException e) {
                System.err.println("Failed to establish I/O connection on " + hostName);
                e.printStackTrace();
                System.exit(1);
            }
    }  
}
