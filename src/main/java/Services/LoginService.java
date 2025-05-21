package Services;
import Repository.UsersRepository;
import models.Users;
import utils.PasswordUtils;

public class LoginService {

    private final UsersRepository usersRepository;

    public LoginService() {
        this.usersRepository = new UsersRepository();
    }

    public String authenticate(String email, String inputPassword) throws Exception {
        Users user = usersRepository.findByEmail(email);
        if (user == null) return null;

        String stored = user.getPassword();
        String[] parts = stored.split("\\$");
        if (parts.length != 2) return null;

        String storedSalt = parts[0];
        String storedHash = parts[1];
        String inputHash = PasswordUtils.hashPassword(inputPassword, storedSalt);

        return inputHash.equals(storedHash) ? user.getRole() : null;
    }

    public int fetchUserIdByEmail(String email) {
        return usersRepository.getUserIdByEmail(email);
    }
}