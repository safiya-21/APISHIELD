import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class AuthService {

    private static final Set<String> validApiKeys = new HashSet<>();

    static {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("apikeys.txt"));
            String line;

            while ((line = reader.readLine()) != null) {
                validApiKeys.add(line.trim());
            }

            reader.close();
        } catch (IOException e) {
            System.out.println("Error reading API keys file");
        }
    }

    public static boolean isValidKey(String apiKey) {
        return validApiKeys.contains(apiKey);
    }
}