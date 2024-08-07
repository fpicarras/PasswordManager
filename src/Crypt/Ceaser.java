package Crypt;

public class Ceaser implements EncryptAlgorithm {

    @Override
    public String decrypt(String message, String secret) {
        if(message == null) return null;
        int shift = calculateShift(secret);
        return caesarCipher(message, -shift);
    }

    @Override
    public String encrypt(String message, String secret) {
        if(message == null) return null;
        int shift = calculateShift(secret);
        return caesarCipher(message, shift);
    }

    private int calculateShift(String secret) {
        int shift = 0;
        for (char ch : secret.toCharArray()) {
            shift += ch;
        }
        // To ensure the shift value stays within a manageable range, we can use modulo 26
        return shift % 26;
    }

    private String caesarCipher(String text, int shift) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);

            if (Character.isLetter(ch)) {
                char base = Character.isUpperCase(ch) ? 'A' : 'a';
                ch = (char) ((ch - base + shift + 26) % 26 + base);
            }
            result.append(ch);
        }
        return result.toString();
    }
}
