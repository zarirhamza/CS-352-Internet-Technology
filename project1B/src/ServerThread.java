import java.io.*;
import java.net.Socket;
import java.util.Date;

class ServerThread extends Thread {
        Socket s;
        PrintWriter pws, pwf;
        BufferedReader br;
        ServerThread(Socket s, PrintWriter logfile){
            try {
                this.s = s;
                pwf = logfile;
                pws = new PrintWriter(s.getOutputStream(), true);
                br = new BufferedReader(new InputStreamReader(s.getInputStream()));
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        public void run(){
            boolean connected = true;
            pwf.println("Connected at port number " + s.getPort() + " at IP " + s.getInetAddress() + " at time " + new Date().toString());
            PolyAlphabet PA = new PolyAlphabet();
            while(connected){
                try {
                    String input = br.readLine();
                    PA.setCypherText(input);
                    String output = PA.encrypt(); //CHANGE THIS TO DECODE
                    pws.println(output); //CHECK FOR DECODED QUIT
                    if (input.equals("quit"))
                        connected = false;
                }catch(Exception e) {
                    pwf.println("Unexpected disconnect");
                    //pwf.println(e.toString());
                    //e.printStackTrace();
                    connected = false;
                }
            }
            try {
                pwf.println("Disconnected at port number " + s.getPort() + " at IP " + s.getInetAddress() + " at time " + new Date().toString());
                s.close();
            } catch (IOException e) {
                pwf.println(e.toString());
            }
        }
    }
