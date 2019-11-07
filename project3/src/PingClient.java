import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.sql.Timestamp;

public class PingClient extends UDPPinger implements Runnable{

    @Override
    public void run() {
        try {
         //   InetAddress address = InetAddress.getByName("constance.cs.rutgers.edu");
            InetAddress address = InetAddress.getByName("localhost");
            int port = 5530;
            ds = new DatagramSocket();
            ds.connect(address,port);
            ds.setSoTimeout(1000);
            System.out.println("Connected to host");

        } catch (UnknownHostException | SocketException e) {
            System.out.println("Unable to connect to server");
            return;
        }

        try {
            for (int a = 1; a <= 10; a++) {
                lastTime = new Timestamp(System.currentTimeMillis());

                PingMessage ping = new PingMessage(ds.getInetAddress(), ds.getPort(), a);
                sendPing(ping);
                receivePing();
            }
        }
        catch (SocketException e) {
                System.out.println("Error sending packets to socket");
        }


        //System.out.println(received);
        if(received != 9){
            while(true) {
                try {
                    ds.setSoTimeout(5000);
                    if(receivePing() == null)
                        break;
                } catch (SocketException e) {
                    System.out.println("Error waiting for server");
                }
            }
        }

        for(int b = 0; b < 10; b++){
            System.out.println("PING " + b + ": " + receievedArr[b] + " RTT: " + RTTs[b]);
        }

        System.out.println("Min RTT: " + minRTT + "ms, Max RTT: " + maxRTT + "ms, Average RTT: " + averageRTT/10 + "ms");
    }

    public static void main(String[] args) throws InterruptedException {
        //Thread server = new Thread(new PingServer());
        Thread client = new Thread(new PingClient());

        //server.start();
        client.start();
    }

}
