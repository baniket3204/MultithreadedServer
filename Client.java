import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class Client {

    public Runnable getRunnable()
    {
        return new Runnable() {
            @Override
            public void run() {
              int port = 8010 ;
              try {
                  InetAddress address = InetAddress.getByName("localhost");
                  Socket socket = new Socket(address , port);
                  PrintWriter toSocket = new PrintWriter(socket.getOutputStream() ,true);
                  BufferedReader fromSocket = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                  toSocket.println("Hello from Client "+ socket.getLocalSocketAddress());
                  String line = fromSocket.readLine();
                  System.out.println("Response from server " + line);
              } catch (Exception e) {
                  e.printStackTrace();
              }
            }
        };
    }


    public static void main(String args[])
    {
        Client client = new Client();
        for (int i=0 ; i<100 ; i++)
        {
            try {
                Thread thread = new Thread(client.getRunnable());
                thread.start();
                thread.sleep(50);
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }
    }
}
