import java.io.*;
import java.net.Socket;
import java.util.Date;

public class WebRequest extends Thread {
    Socket s;
    DataOutputStream dos;
    BufferedReader br;
    WebRequest(Socket s){
        try {
            this.s = s;
            dos = new DataOutputStream(s.getOutputStream());
            br = new BufferedReader(new InputStreamReader(s.getInputStream()));
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public void run(){
        //System.out.println("Connected at port number " + s.getPort() + " at IP " + s.getInetAddress() + " at time " + new Date().toString());
        boolean connected = true;
        HTTP h = new HTTP(s,dos,br);
            try {
                h.generateStatus();
                h.generateHeader();
                h.generateResponse();
            } catch (Exception e) {
                System.out.println("Unable to return file");
            }
        // output http object.status , headers, and body

    }
}
