import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] argv) throws IOException {
        Server ss = new Server();
        ss.run();
    }
    public void run() throws IOException{
        ServerSocket ss = new ServerSocket(5520);
        PrintWriter pw = new PrintWriter(new FileOutputStream("prog1b.log"), true);
        while(true) {
            try {
                Socket s = ss.accept();
                ServerThread st = new ServerThread(s, pw);
                st.start();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}
