public class CrackTripleSDES_Part3 {

    public static void main(String[] args) {
        String msg = "00011111100111111110011111101100111000000011001011110010101010110001011101001101000000110011010111111110000000001010111111000001010010111001111001010101100000110111100011111101011100100100010101000011001100101000000101111011000010011010111100010001001000100001111100100000001000000001101101000000001010111010000001000010011100101111001101111011001001010001100010100000";
        byte[] key1 = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
        byte[] key2 = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
        byte[] plaintext;
        byte[] ciphertext = CrackSDES_Part3.strToByteArr(msg);
        // decrypt the ciphertext for each possible pair of keys and convert the
        // decrypted string to ascii
        for (int i = 0; i < 1024; i++) {
            for (int j = 0; j < 1024; j++) {
                plaintext = TripleSDES_Part2.Decrypt(key1, key2, ciphertext);
                String str = CASCII.toString(plaintext);
                // additional logic on top of punctuation deals with valid words
                if (CrackSDES_Part3.hasValidPunctuation(str) && wordPercentRegex(str) && containsCommonWords(str)) {
                    System.out.print("key1: ");
                    CrackSDES_Part3.printArray(key1);
                    System.out.print("key2: ");
                    CrackSDES_Part3.printArray(key2);
                    System.out.println(str);
                }
                // increment key 1 to check all pairs with current key 2
                CrackSDES_Part3.binaryIncrement(key1);
            }
            // increment key 2 after all key 1 for the current key 2 are done
            CrackSDES_Part3.binaryIncrement(key2);
        }
    }

    // just checks if each word is a bunch of letters and no invalid punctuation
    // gets close but there are still some garbage outputs along with the message
    public static boolean wordPercentRegex(String s) {
        s = s.toUpperCase();
        // split on spaces using regex
        String[] words = s.split("\\s+");
        int count = 0;
        int total = 0;
        for (String word : words) {
            // check if word is all letters using regex for word character
            if (word.matches("^\\w+$"))
                count++;
            total++;
        }
        // true if valid words are above a determined percentage
        double percentCorrect = ((double) count / total);
        if (percentCorrect < 0.85)
            return false;
        else
            return true;

    }

    // check if any of the preset common words are in the decrypted message
    public static boolean containsCommonWords(String s) {
        s = s.toUpperCase();
        // split on spaces using regex
        String[] words = s.split("\\s+");
        // using top 5 words with 3 or more letters because 1/2 letters can be more
        // easily obtained randomly
        String[] top = { "THE", "AND", "THAT", "HAVE", "FOR" };
        // for each word in the message check if that word is one of the top words
        for (String w : words) {
            for (String t : top) {
                if (w.equals(t)) {
                    return true;
                }
            }
        }
        return false;
    }
}