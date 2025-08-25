import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

public class PasswordUtils {
    
    // Generate a random salt
    public static String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }
    
    // Hash password with salt using SHA-256
    public static String hashPassword(String password, String salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(Base64.getDecoder().decode(salt));
            byte[] hashedPassword = md.digest(password.getBytes("UTF-8"));
            return Base64.getEncoder().encodeToString(hashedPassword);
        } catch (Exception e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }
    
    // Verify password against stored hash and salt
    public static boolean verifyPassword(String password, String storedHash, String salt) {
        String hashOfInput = hashPassword(password, salt);
        return hashOfInput.equals(storedHash);
    }
    
    // Convenience method to hash password with new salt
    public static String[] hashPasswordWithNewSalt(String password) {
        String salt = generateSalt();
        String hash = hashPassword(password, salt);
        return new String[]{hash, salt};
    }
    
    // Test method
    public static void main(String[] args) {
        String password = "testPassword123";
        String[] result = hashPasswordWithNewSalt(password);
        String hash = result[0];
        String salt = result[1];
        
        System.out.println("Original password: " + password);
        System.out.println("Salt: " + salt);
        System.out.println("Hash: " + hash);
        System.out.println("Verification: " + verifyPassword(password, hash, salt));
        System.out.println("Wrong password verification: " + verifyPassword("wrongPassword", hash, salt));
    }
}
