import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class Server {

    public Consumer<Socket> getConsumer()
    {
        return (clientSocket) -> {
            try {
                PrintWriter toClient = new PrintWriter(clientSocket.getOutputStream() ,true);
                toClient.println("Hello from the Server");
                toClient.close();
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
    }

    public static void main(String[] args) throws IOException {
        int port = 8010 ;
        Server server = new Server();
        ServerSocket serverSocket = new ServerSocket(port);
        serverSocket.setSoTimeout(100000);
        System.out.println("Server listening at port "+port);
        ExecutorService threadPool = Executors.newFixedThreadPool(10);
        while (true)
        {
            Socket socketAccepted = serverSocket.accept();
            threadPool.execute(() -> server.getConsumer().accept(socketAccepted));
        }

    }
}
