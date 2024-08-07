package Crypt;

public interface EncryptAlgorithm {
    public abstract String decrypt(String filepath, String secret);
    public abstract String encrypt(String message, String secret);
}
