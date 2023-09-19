package bhtu.work.tths.testing;

/**
 * Where you save data, emulate an user
 */
public class Client {
    public final String baseUrl;
    public String username;
    public String password;
    public String houseNumber;
    public String jwt;

    public Client(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    /**
     * Init a client with target link is <a href="http://127.0.0.1:8080/">localhost:8080</a>
     */
    public Client() {
        this("http://127.0.0.1:8080"); // localhost
    }
}
