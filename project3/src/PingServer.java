import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Random;

public class PingServer implements Runnable{
    double LOSS_RATE = 0.3;
    int AVERAGE_DELAY = 100;
    int PACKET_SIZE = 512;
    DatagramSocket socket;
    int counter = 1;

    Random random = new Random(new Date().getTime());


    @Override
    public void run() {
        try {
            socket = new DatagramSocket(5530);
            System.out.println("Ping Server Running");
        } catch (SocketException e) {
            System.out.println("Unable to start server");
            return;
        }
        while (true) {
            System.out.println("Waiting for UDP Packet");
            try {

                byte[] buf = new byte[PACKET_SIZE];

                // receive request
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                String data = new String(packet.getData());

                System.out.println("Received packet from: " + packet.getAddress() + " " + packet.getPort() + " " + data.substring(0,24));


                if(random.nextDouble() < LOSS_RATE){
                    System.out.println("Packet loss... reply not sent");
                }
                else {
                    // send the response to the client at "address" and "port"
                    Thread.sleep((int)(random.nextDouble() * 2 * AVERAGE_DELAY));
                    PingMessage ping = new PingMessage(packet.getAddress(), packet.getPort(), buf.toString());
                    packet = new DatagramPacket(ping.getPayload().getBytes(), ping.getPayload().getBytes().length, ping.getIP(), ping.getPort());
                    socket.send(packet);
                }

            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
        //socket.close();
    }

    public static void main(String[] args) throws InterruptedException {
        Thread server = new Thread(new PingServer());
        //Thread client = new Thread(new PingClient());

        server.start();
        //client.start();
    }

}
