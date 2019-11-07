import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.util.StringTokenizer;
import java.util.concurrent.ExecutionException;

public class HTTP {
    Socket s;
    DataOutputStream dos;
    BufferedReader br;


    String request;
    String status = "HTTP/1.0 ";
    String header = "Content-type: ";

    boolean exists;


    HTTP(Socket s, DataOutputStream dos, BufferedReader br) {
        this.s = s;
        this.dos = dos;
        this.br = br;

        try {
            request = br.readLine();
            StringTokenizer st = new StringTokenizer(request, " ");
            st.nextToken();
            request = "." + st.nextToken();
            FileInputStream f = new FileInputStream(request);
            //System.out.println(request);
            exists = true;
            this.request = request;
        } catch (Exception e) {
            System.out.println("Cannot find file");
            this.request = "FAIL";
            exists = false;
        }
    }

    public void generateStatus() throws FileNotFoundException, IOException {
        if (exists) {
            status += "200 OK\r\n";
        } else {
            status += "404 Not Found\r\n";
        }

    }

    public void generateHeader() throws IOException {
        this.header = "Content-type: ";
        String mimetype = "application/octet-stream";
        try {
            if (exists)
                mimetype = Files.probeContentType(new File(request).toPath());
        } catch (IOException e) {
            ;
        }
        if (mimetype == null)
            mimetype = "application/octet-stream";

        if(exists)
            this.header += mimetype;
        else
            this.header += "text/html";

            this.header += "\r\n\r\n";
    }

    public void generateResponse(){
        try {
            System.out.println(status);
            System.out.println(header);
            //System.out.println(exists);

            int bytesRead = 0;
            int i = 0;
            if (exists) {
                dos.writeBytes(status);
                dos.writeBytes(header);

                FileInputStream f = new FileInputStream(request);
                byte[] buf = new byte[1024];
                while((i = f.read(buf)) > 0){
                    //System.out.println(i);
                    dos.write(buf, 0, i);
                }
            }else{
                dos.writeBytes(status);
                dos.writeBytes(header);
                String errorMessage =
                        "<html><head></head><body>not found</body></html>\n";
                dos.writeBytes(errorMessage);
            }
            s.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}