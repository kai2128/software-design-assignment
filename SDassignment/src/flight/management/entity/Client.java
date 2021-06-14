package flight.management.entity;

import java.util.Map;

public class Client extends User{
    private String clientId;

    public String getClientId() {
        return clientId;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public Client(String username, String name, String phone, String email, String password) {
        super(username, password, name, phone, email);
    }

    public Client(Map<String, String> result) {
        super(
                result.get("username"),
                result.get("password"),
                result.get("name"),
                result.get("phone"),
                result.get("email")
        );

        clientId = result.get("prefix") + result.get("id");
    }
}
