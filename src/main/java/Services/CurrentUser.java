package Services;

public class CurrentUser {
    private static Integer userId;
    private static String role;

    public static void setUser(Integer id, String r) {
        userId = id;
        role = r;
    }

    public static Integer getUserId() {
        return userId;
    }

    public static String getRole() {
        return role;
    }

    public static void clear() {
        userId = null;
        role = null;
    }
}
