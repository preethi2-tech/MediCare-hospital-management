package util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

public final class PasswordUtil {
    private static final SecureRandom RANDOM = new SecureRandom();
    private PasswordUtil() {}
    public static String generateSalt() { byte[] bytes = new byte[16]; RANDOM.nextBytes(bytes); return Base64.getEncoder().encodeToString(bytes); }
    public static String hashPassword(String password, String salt) {
        try { MessageDigest digest = MessageDigest.getInstance("SHA-256"); digest.update(Base64.getDecoder().decode(salt)); return Base64.getEncoder().encodeToString(digest.digest(password.getBytes(StandardCharsets.UTF_8))); }
        catch (Exception ex) { throw new IllegalStateException("Unable to hash password", ex); }
    }
    public static boolean verifyPassword(String password, String hash, String salt) { return MessageDigest.isEqual(hashPassword(password, salt).getBytes(StandardCharsets.UTF_8), hash.getBytes(StandardCharsets.UTF_8)); }
}
