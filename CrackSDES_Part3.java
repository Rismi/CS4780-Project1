public class CrackSDES_Part3 {
    public static void main(String[] args) {
        //sdes encoding
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
        System.out.println();
        
        //sdes cracking
        String msg = "1011011001111001001011101111110000111110100000000001110111010001111011111101101100010011000000101101011010101000101111100011101011010111100011101001010111101100101110000010010101110001110111011111010101010100001100011000011010101111011111010011110111001001011100101101001000011011111011000010010001011101100011011110000000110010111111010000011100011111111000010111010100001100001010011001010101010000110101101111111010010110001001000001111000000011110000011110110010010101010100001000011010000100011010101100000010111000000010101110100001000111010010010101110111010010111100011111010101111011101111000101001010001101100101100111001110111001100101100011111001100000110100001001100010000100011100000000001001010011101011100101000111011100010001111101011111100000010111110101010000000100110110111111000000111110111010100110000010110000111010001111000101011111101011101101010010100010111100011100000001010101110111111101101100101010011100111011110101011011";
        byte[] key1 = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
        byte[] plaintext1;
        byte[] ciphertext1 = strToByteArr(msg);
        //decrypt the ciphertext for each possible 10 bit key and convert the decrypted string to ascii
        for (int i = 0; i < 1024; i++) {
            plaintext1 = SDES_Part1.Decrypt(key1, ciphertext1);
            String s = CASCII.toString(plaintext1);
            //check if the decrypted string has valid punctuation, may need more checks if this does not find the message
            if (hasValidPunctuation(s)) {
                //print the key and decrypted message if it passes the check
                System.out.print("key: ");
                printArray(key1);
                System.out.println(s);
            }
            //increment the key
            binaryIncrement(key1);
        }
    }
    public static void printArray(byte[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]);
        }
        System.out.println();
    }
    //converts the string binary input to a byte array of equal length
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
    //used to add 1 to the key byte array for brute force cracking
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
    public static boolean hasValidPunctuation(String str) {
        for (int i = 0; i < str.length() - 1; i++) {
            char c = str.charAt(i);
            if (c == '.' || c == '?' || c == ',' || c == ':') {
                if (str.charAt(i + 1) != ' ')
                    return false;
            }
        }
        return true;
    }
}
