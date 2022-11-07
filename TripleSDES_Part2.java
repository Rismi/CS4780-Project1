public class TripleSDES_Part2 {
    public static void main(String[] args) {
        //table to be filled out part 2
        byte key1[] = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
        byte key2[] = { 1, 0, 0, 0, 1, 0, 1, 1, 1, 0 };
        byte key3[] = { 0, 1, 1, 0, 1, 0, 1, 1, 1, 0 };
        byte key4[] = { 1, 0, 1, 1, 1, 0, 1, 1, 1, 1 };
        byte key5[] = { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 };
        byte plaintext1[] = { 0, 0, 0, 0, 0, 0, 0, 0 };
        byte plaintext2[] = { 1, 1, 0, 1, 0, 1, 1, 1 };
        byte plaintext3[] = { 1, 0, 1, 0, 1, 0, 1, 0 };
        byte ciphertext1[] = { 1, 1, 1, 0, 0, 1, 1, 0 };
        byte ciphertext2[] = { 0, 1, 0, 1, 0, 0, 0, 0 };
        byte ciphertext3[] = { 1, 0, 0, 0, 0, 0, 0, 0 };
        byte ciphertext4[] = { 1, 0, 0, 1, 0, 0, 1, 0 };
        System.out.println("Raw Key 1\tRaw Key 2\tPlaintext\tCiphertext");
        SDES_Part1.printArray(key1);
        SDES_Part1.printArray(key1);
        SDES_Part1.printArray(plaintext1);
        SDES_Part1.printArray(Encrypt(key1, key1, plaintext1));
        System.out.println();
        SDES_Part1.printArray(key2);
        SDES_Part1.printArray(key3);
        SDES_Part1.printArray(plaintext2);
        SDES_Part1.printArray(Encrypt(key2, key3, plaintext2));
        System.out.println();
        SDES_Part1.printArray(key2);
        SDES_Part1.printArray(key3);
        SDES_Part1.printArray(plaintext3);
        SDES_Part1.printArray(Encrypt(key2, key3, plaintext3));
        System.out.println();
        SDES_Part1.printArray(key5);
        SDES_Part1.printArray(key5);
        SDES_Part1.printArray(plaintext3);
        SDES_Part1.printArray(Encrypt(key5, key5, plaintext3));
        System.out.println();
        SDES_Part1.printArray(key2);
        SDES_Part1.printArray(key3);
        SDES_Part1.printArray(Decrypt(key2, key3, ciphertext1));
        SDES_Part1.printArray(ciphertext1);
        System.out.println();
        SDES_Part1.printArray(key4);
        SDES_Part1.printArray(key3);
        SDES_Part1.printArray(Decrypt(key4, key3, ciphertext2));
        SDES_Part1.printArray(ciphertext2);
        System.out.println();
        SDES_Part1.printArray(key1);
        SDES_Part1.printArray(key1);
        SDES_Part1.printArray(Decrypt(key1, key1, ciphertext3));
        SDES_Part1.printArray(ciphertext3);
        System.out.println();
        SDES_Part1.printArray(key5);
        SDES_Part1.printArray(key5);
        SDES_Part1.printArray(Decrypt(key5, key5, ciphertext4));
        SDES_Part1.printArray(ciphertext4);
        System.out.println();
    }
    //use the work from sdes to do the three step encryption following formula: E3DES(p) = EDES(k1,DDES(k2,EDES(k1, p)))
    public static byte[] Encrypt(byte[] rawkey1, byte[] rawkey2, byte[] plaintext) {
        return SDES_Part1.Encrypt(rawkey1, SDES_Part1.Decrypt(rawkey2, SDES_Part1.Encrypt(rawkey1, plaintext)));
    }
    //use the work from sdes to do the three step decryption following formula: D3DES(c) = DDES(k1,EDES(k2,DDES(k1, c)))
    public static byte[] Decrypt(byte[] rawkey1, byte[] rawkey2, byte[] ciphertext) {
        return SDES_Part1.Decrypt(rawkey1, SDES_Part1.Encrypt(rawkey2, SDES_Part1.Decrypt(rawkey1, ciphertext)));
    }
}
