import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;

public class MainServer {

    public static void main(String[] args) throws IOException {

        HttpServer server = HttpServer.create(new InetSocketAddress(5050), 0);

        server.createContext("/api", new RequestHandler());

        server.setExecutor(null);
        server.start();

        System.out.println("🚀 Server started at http://localhost:5050/api");
    }
}