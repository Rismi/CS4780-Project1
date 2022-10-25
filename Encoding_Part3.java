import java.util.*;

public class Encoding_Part3 {
    public static void main(String[] args) throws Exception {
        byte[] key = { 0, 1, 1, 1, 0, 0, 1, 1, 0, 1 };
        String str = "CRYPTOGRAPHY";
        //convert input string to cascii byte array
        byte[] plaintext = CASCII.Convert(str);
        //encrypt cascii byte array for cascii ciphertext
        byte[] ciphertext = SDES_Part1.Encrypt(key, plaintext);
        System.out.println("Input string: " + str);
        System.out.print("SDES Encoding 64 bits: ");
        printArray(ciphertext);
        //convert cascii bits to ascii string for output
        String output = CASCII.toString(ciphertext);
        System.out.println("Encrypted message: " + output);
    }

    public static void printArray(byte[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]);
        }
        System.out.println();
    }
}
