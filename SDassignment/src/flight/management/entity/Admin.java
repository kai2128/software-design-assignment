package flight.management.entity;

import java.util.Map;

public class Admin extends User{
    private String adminId;

    public String getAdminId() {
        return adminId;
    }

    public Admin(String username, String password, String name, String phone, String email, String adminId) {
        super(username, password, name, phone, email);
        this.adminId = adminId;
    }

    public Admin(Map<String, String> result) {
        super(
                result.get("username"),
                result.get("password"),
                result.get("name"),
                result.get("phone"),
                result.get("email")
        );

        adminId = result.get("prefix") + result.get("id");
    }

}
