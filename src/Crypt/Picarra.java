package Crypt;

import java.security.SecureRandom;

public class Picarra implements EncryptAlgorithm{
    private static final String CHAR_SET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()_+-=[]{}|;:'\",.<>?/`~";
    private static final SecureRandom RANDOM = new SecureRandom();
    private final int binSize;

    public Picarra(int binSize) {
        this.binSize = binSize;
    }

    public static String generateRandomString(int length) {
        if (length < 1) throw new IllegalArgumentException("Length must be greater than 0");
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = RANDOM.nextInt(CHAR_SET.length());
            sb.append(CHAR_SET.charAt(index));
        }
        return sb.toString();
    }

    @Override
    public String decrypt(String message, String secret) {
        if(message == null) return null;
        int jump, ptr = 0;
        StringBuilder sb = new StringBuilder();
        LCGRandom lcg = new LCGRandom(secret.hashCode()); //Possible cross-platform error - don't use hashcode
        //Iterate over the char's in the string
        for(int i = 0; i<message.length()/binSize; i++){
            jump = lcg.nextInt(binSize);
            //Append the char to the string
            sb.append(message.charAt(jump+ptr));
            ptr += binSize;
        }
        return sb.toString();
    }

    @Override
    public String encrypt(String message, String secret) {
        if(message == null) return null;
        int jump, ptr = 0;
        String str = generateRandomString(binSize*message.length());
        StringBuilder sb = new StringBuilder(str);
        LCGRandom lcg = new LCGRandom(secret.hashCode()); //Possible cross-platform error - don't use hashcode

        //Iterate over the char's in the message
        for(int i = 0; i<message.length(); i++){
            char c = message.charAt(i);
            jump = lcg.nextInt(binSize);
            //Set the char in the string to the char in the message
            sb.setCharAt(jump+ptr, c);
            ptr += binSize;
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        String message = "Hello, World!";
        String secret = "mySecretKey";
        int binSize = 16;

        Picarra picarra = new Picarra(binSize);

        // Encrypt the message
        String encryptedMessage = picarra.encrypt(message, secret);
        System.out.println("Encrypted Message: " + encryptedMessage);

        // Decrypt the message
        String decryptedMessage = picarra.decrypt(encryptedMessage, secret);
        System.out.println("Decrypted Message: " + decryptedMessage);
    }
}
