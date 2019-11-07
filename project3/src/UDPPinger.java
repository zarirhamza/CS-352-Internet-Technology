import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.sql.Timestamp;

public class UDPPinger {
    DatagramSocket ds;
    int PACKET_SIZE = 512;

    boolean[] receievedArr = new boolean[10];
    int received = 0;

    long minRTT = 100000;
    long maxRTT = 0;
    long averageRTT;

    long[] RTTs = new long[10];

    Timestamp lastTime;


    public void sendPing(PingMessage ping){
        try {
            byte[] buf = new byte[PACKET_SIZE];
            buf = ping.getPayload().getBytes();
            DatagramPacket dpo = new DatagramPacket(buf,buf.length, ping.getIP(), ping.getPort());
            ds.send(dpo);
            //System.out.println("Sent ping, waiting for response...");
        }catch(IOException e){
            System.out.println("Unable to send packet to sever");
        }
    }

    public PingMessage receivePing() throws SocketException {
        PingMessage rPing = null;
        try {
            byte[] buff = new byte[PACKET_SIZE];
            DatagramPacket dpi = new DatagramPacket(buff, PACKET_SIZE);
            ds.receive(dpi);
            rPing = new PingMessage(dpi.getAddress(), dpi.getPort(), dpi.getData().toString());
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            System.out.println("Received packet from: " + rPing.getIP() + " " + rPing.getPort() + " " + timestamp);

            long RTT = timestamp.getTime() - lastTime.getTime();

            if(RTT < minRTT)
                minRTT = RTT;
            else if(RTT > maxRTT)
                maxRTT = RTT;

            averageRTT += RTT;

            receievedArr[received] = true;
            RTTs[received] = RTT;
            received++;

        }catch(IOException e){
            //e.printStackTrace();
            if(ds.getSoTimeout() == 5000)
                return null;

            long RTT = ds.getSoTimeout();
            if(RTT < minRTT)
                minRTT = RTT;
            else if(RTT > maxRTT)
                maxRTT = RTT;

            averageRTT += RTT;

            receievedArr[received] = false;
            RTTs[received] = RTT;
            received++;

            System.out.println("Received time out");
        }
        return rPing;
    }
}
