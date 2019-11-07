import java.io.*;
import java.net.*;

public class WebServer {
    public static void main (String[] argv) throws IOException{
        WebServer ws = new WebServer();
        ws.run();
    }
    public void run() throws IOException{
        ServerSocket ss = new ServerSocket(5520);
        while (true){
            try {
                Socket s = ss.accept();
                WebRequest wr = new WebRequest(s);
                wr.start();
                System.out.println("Recieved a request from " + s.getInetAddress());
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}
