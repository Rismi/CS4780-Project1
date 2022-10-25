import java.util.*;
import java.io.File;
import java.util.Scanner;

public class Cracking_Part3 {
    public static void main(String[] args) throws Exception {
        //sdes cracking
        //read file and its contents. could also just copy from the file and leave as a variable
        File file1 = new File("C:\\Users\\rismi\\Desktop\\CSULA Fall 2022\\CS 4780\\msg1.txt");
        Scanner scan1 = new Scanner(file1);
        String msg = scan1.nextLine();
        scan1.close();
        byte[] key = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
        byte[] plaintext;
        byte[] ciphertext = strToByteArr(msg);
        //decrypt the ciphertext for each possible key and convert the decrypted string to ascii
        for (int i = 0; i < 1024; i++) {
            plaintext = SDES_Part1.Decrypt(key, ciphertext);
            String str = CASCII.toString(plaintext);
            //check if the decrypted string is something valid. may need to be looked at
            if (hasValidPunctuation(str)) {
                System.out.print("key: ");
                printArray(key);
                System.out.println(str);
            }
            binaryIncrement(key);
        }
        System.out.println();
        //triple sdes cracking
        //read file and its contents. could also just copy from the file and leave as a variable
        File file2 = new File("C:\\Users\\rismi\\Desktop\\CSULA Fall 2022\\CS 4780\\msg2.txt");
        Scanner scan2 = new Scanner(file2);
        String msg2 = scan2.nextLine();
        scan2.close();
        byte[] key1 = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
        byte[] key2 = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
        byte[] plaintext1;
        byte[] ciphertext1 = strToByteArr(msg2);
        //decrypt the ciphertext for each possible pair of keys and convert the decrypted string to ascii
        for (int i = 0; i < 1024; i++) {
            for (int j = 0; j < 1024; j++) {
                plaintext1 = TripleSDES_Part2.Decrypt(key1, key2, ciphertext1);
                String str = CASCII.toString(plaintext1);
                //if statement needs to be looked at for better output
                if (wordPercentRegex(str) && hasValidPunctuation(str)) {
                    System.out.print("key1: ");
                    printArray(key1);
                    System.out.print("key2: ");
                    printArray(key2);
                    System.out.println(str);
                }
                binaryIncrement(key1);
            }
            binaryIncrement(key2);
        }
        System.out.println();
    }
    
    public static void printArray(byte[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]);
        }
        System.out.println();
    }
    //converts the string binary input to a byte array of the string length
    public static byte[] strToByteArr(String s) {
        byte[] b = new byte[s.length()];
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '0') {
                b[i] = (byte) 0;
            } else {
                b[i] = (byte) 1;
            }
        }
        return b;
    }
    //used to add 1 to the key byte array for brute force
    public static void binaryIncrement(byte[] b) {
        boolean carry = true;
        for (int i = b.length - 1; i >= 0; i--) {
            if (carry) {
                if (b[i] == 0) {
                    b[i] = 1;
                    carry = false;
                } else {
                    b[i] = 0;
                    carry = true;
                }
            }
        }
    }
    //check if punctuation is placed properly like a period followed by a space, etc
    private static boolean hasValidPunctuation(String str) {
        for (int i = 0; i < str.length() - 1; i++) {
            char c = str.charAt(i);
            if (c == '.' || c == '?' || c == ',' || c == ':') {
                if (str.charAt(i + 1) != ' ')
                    return false;
            }
        }
        return true;
    }
    //used regex here but not sure if it works well
    //it really just checks if each word is a bunch of letters and no punctuation
    //maybe change or use it in combination with the 10-20 most common english words?
    public static boolean wordPercentRegex(String s) {
        s = s.toUpperCase();
        //split on spaces using regex
        String[] words = s.split("\\s+");
        int count = 0;
        int total = 0;
        for (String word : words) {
            //check if word is all letters using regex
            if (word.matches("^\\w+$"))
                count++;
            total++;
        }
        //true if valid words are above a percentage, using 85% here
        double percentCorrect = ((double) count / total);
        //adjust for different percentage of "valid" words
        if (percentCorrect < 0.85)
            return false;
        else
            return true;

    }
}
