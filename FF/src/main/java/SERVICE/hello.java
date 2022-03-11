package SERVICE;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class hello {
    public static void main(String[] args) throws IOException {
        ServerSocket ss=new ServerSocket(9200);
        Socket s=ss.accept();
        InputStream inputStream=s.getInputStream();
        byte[] buffer=new byte[1024000];
        int read=inputStream.read(buffer);
        String str=new String(buffer,0,read);
        System.out.println(str);
    }
}
