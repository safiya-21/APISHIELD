import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.OutputStream;

public class RequestHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        System.out.println("Incoming Request...");
        String apiKey = exchange.getRequestHeaders().getFirst("API-Key");

        String response;

        if (apiKey == null || !AuthService.isValidKey(apiKey)) {
            response = "Unauthorized: Invalid API Key";
            exchange.sendResponseHeaders(401, response.length());
        } else if (!RateLimiter.allowRequest(apiKey)) {
            response = "Rate Limit Exceeded";
            exchange.sendResponseHeaders(429, response.length());
        } else {
            response = "Request Successful";
            exchange.sendResponseHeaders(200, response.length());
        }

        System.out.println("Response: " + response);
        System.out.println("--------------------------");

        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}