import java.net.InetAddress;
import java.sql.Timestamp;

public class PingMessage {
    private InetAddress addr;
    private int port;
    private String payload;

    public InetAddress getIP() {
        return addr;
    }

    public void setIP(InetAddress addr) {
        this.addr = addr;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public PingMessage(InetAddress addr, int port, int sequenceNum){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        this.addr = addr;
        this.port = port;
        this.payload = "PING " + Integer.toString(sequenceNum)+ " " + Long.toString(timestamp.getTime());
    }

    public PingMessage(InetAddress addr, int port, String payload){
        //Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        this.addr = addr;
        this.port = port;
        this.payload = payload;
    }
}
