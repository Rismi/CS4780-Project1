import java.util.*;

public class SDES_Part1 {
    public static void main(String[] args) {
        //table with all values given to check answers
        byte[] key1 = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
        byte[] key2 = { 1, 1, 1, 0, 0, 0, 1, 1, 1, 0 };
        byte[] key3 = { 1, 1, 1, 0, 0, 0, 1, 1, 1, 0 };
        byte[] key4 = { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 };
        byte[] plaintext1 = { 1, 0, 1, 0, 1, 0, 1, 0 };
        byte[] plaintext2 = { 1, 0, 1, 0, 1, 0, 1, 0 };
        byte[] plaintext3 = { 0, 1, 0, 1, 0, 1, 0, 1 };
        byte[] plaintext4 = { 1, 0, 1, 0, 1, 0, 1, 0 };
        byte[] ciphertext1 = { 0, 0, 0, 1, 0, 0, 0, 1 };
        byte[] ciphertext2 = { 1, 1, 0, 0, 1, 0, 1, 0 };
        byte[] ciphertext3 = { 0, 1, 1, 1, 0, 0, 0, 0 };
        byte[] ciphertext4 = { 0, 0, 0, 0, 0, 1, 0, 0 };
        System.out.println("Raw Key\t\tPlaintext\tCiphertext");
        printArray(key1);
        printArray(Decrypt(key1, ciphertext1));
        printArray(Encrypt(key1, plaintext1));
        System.out.println();
        printArray(key2);
        printArray(Decrypt(key2, ciphertext2));
        printArray(Encrypt(key2, plaintext2));
        System.out.println();
        printArray(key3);
        printArray(Decrypt(key3, ciphertext3));
        printArray(Encrypt(key3, plaintext3));
        System.out.println();
        printArray(key4);
        printArray(Decrypt(key4, ciphertext4));
        printArray(Encrypt(key4, plaintext4));
        System.out.println();
        //table to be filled out part 1
        byte[] k1 = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
        byte[] k2 = { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 };
        byte[] k3 = { 0, 0, 0, 0, 0, 1, 1, 1, 1, 1 };
        byte[] k4 = { 1, 0, 0, 0, 1, 0, 1, 1, 1, 0 };
        byte[] k5 = { 0, 0, 1, 0, 0, 1, 1, 1, 1, 1 };
        byte[] p1 = { 0, 0, 0, 0, 0, 0, 0, 0 };
        byte[] p2 = { 1, 1, 1, 1, 1, 1, 1, 1 };
        byte[] c1 = { 0, 0, 0, 1, 1, 1, 0, 0 };
        byte[] c2 = { 1, 1, 0, 0, 0, 0, 1, 0 };
        byte[] c3 = { 1, 0, 0, 1, 1, 1, 0, 1 };
        byte[] c4 = { 1, 0, 0, 1, 0, 0, 0, 0 };
        System.out.println("\nRaw Key\t\tPlaintext\tCiphertext");
        printArray(k1);
        printArray(p1);
        printArray(Encrypt(k1, p1));
        System.out.println();
        printArray(k2);
        printArray(p2);
        printArray(Encrypt(k2, p2));
        System.out.println();
        printArray(k3);
        printArray(p1);
        printArray(Encrypt(k3, p1));
        System.out.println();
        printArray(k3);
        printArray(p2);
        printArray(Encrypt(k3, p2));
        System.out.println();
        printArray(k4);
        printArray(Decrypt(k4, c1));
        printArray(c1);
        System.out.println();
        printArray(k4);
        printArray(Decrypt(k4, c2));
        printArray(c2);
        System.out.println();
        printArray(k5);
        printArray(Decrypt(k5, c3));
        printArray(c3);
        System.out.println();
        printArray(k5);
        printArray(Decrypt(k5, c4));
        printArray(c4);
        System.out.println();
    }

    public static void printArray(byte[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]);
        }
        System.out.print("\t");
    }
    /* encrypt before adjusting for more than 8 bits for part 3
    public static byte[] Encrypt(byte[] rawkey, byte[] plaintext) {
        byte[] step1 = keyLS1(keyP10(rawkey));
        byte[] key1 = keyP8(step1);
        byte[] key2 = keyP8(keyLS1(keyLS1(step1)));
        byte[] fk1 = fSubK(functionIP(plaintext), key1);
        byte[] fk2 = fSubK(switchFunction(fk1), key2);
        return inverseIP(fk2);
    }*/
    //modified encrypt to handle multiples of 8 bits for part 3, following the process in the instruction pdf
    public static byte[] Encrypt(byte[] rawkey, byte[] plaintext) {
        byte[] step1 = keyLS1(keyP10(rawkey));
        byte[] key1 = keyP8(step1);
        byte[] key2 = keyP8(keyLS1(keyLS1(step1)));
        byte[] encrypted = new byte[plaintext.length];
        for (int i = 0; i < plaintext.length; i += 8) {
            byte[] plaintext8Byte = Arrays.copyOfRange(plaintext, i, i + 8);
            byte[] fk1 = fSubK(functionIP(plaintext8Byte), key1);
            byte[] fk2 = fSubK(switchFunction(fk1), key2);
            byte[] temp = inverseIP(fk2);
            for (int j = 0; j < 8; j++) {
                encrypted[j + i] = temp[j];
            }
        }
        return encrypted;
    }
    /* decrypt before adjusting for more than 8 bits for part 3
    public static byte[] Decrypt(byte[] rawkey, byte[] ciphertext) {
        byte[] step1 = keyLS1(keyP10(rawkey));
        byte[] key1 = keyP8(step1);
        byte[] key2 = keyP8(keyLS1(keyLS1(step1)));
        byte[] fk2 = fSubK(functionIP(ciphertext), key2);
        byte[] fk1 = fSubK(switchFunction(fk2), key1);
        return inverseIP(fk1);
    }*/
    //modified decrypt to handle multiples of 8 bits for part 3, following the process in the instruction pdf
    public static byte[] Decrypt(byte[] rawkey, byte[] ciphertext) {
        byte[] step1 = keyLS1(keyP10(rawkey));
        byte[] key1 = keyP8(step1);
        byte[] key2 = keyP8(keyLS1(keyLS1(step1)));
        byte[] decrypted = new byte[ciphertext.length];
        for (int i = 0; i < ciphertext.length; i += 8) {
            byte[] ciphertext8Byte = Arrays.copyOfRange(ciphertext, i, i + 8);
            byte[] fk2 = fSubK(functionIP(ciphertext8Byte), key2);
            byte[] fk1 = fSubK(switchFunction(fk2), key1);
            byte[] temp = inverseIP(fk1);
            for (int j = 0; j < 8; j++) {
                decrypted[j + i] = temp[j];
            }
        }
        return decrypted;
    }
    //permutation 10 for keys
    public static byte[] keyP10(byte[] inputKey) {
        byte[] permutedKey = new byte[10];
        permutedKey[0] = inputKey[2];
        permutedKey[1] = inputKey[4];
        permutedKey[2] = inputKey[1];
        permutedKey[3] = inputKey[6];
        permutedKey[4] = inputKey[3];
        permutedKey[5] = inputKey[9];
        permutedKey[6] = inputKey[0];
        permutedKey[7] = inputKey[8];
        permutedKey[8] = inputKey[7];
        permutedKey[9] = inputKey[5];
        return permutedKey;
    }
    //left shift by 1 for keys
    public static byte[] keyLS1(byte[] inputKey) {
        byte[] leftShift1 = new byte[10];
        leftShift1[0] = inputKey[1];
        leftShift1[1] = inputKey[2];
        leftShift1[2] = inputKey[3];
        leftShift1[3] = inputKey[4];
        leftShift1[4] = inputKey[0];
        leftShift1[5] = inputKey[6];
        leftShift1[6] = inputKey[7];
        leftShift1[7] = inputKey[8];
        leftShift1[8] = inputKey[9];
        leftShift1[9] = inputKey[5];
        return leftShift1;
    }
    //permutation 8 for keys
    public static byte[] keyP8(byte[] inputKey) {
        byte[] permutedKey = new byte[8];
        permutedKey[0] = inputKey[5];
        permutedKey[1] = inputKey[2];
        permutedKey[2] = inputKey[6];
        permutedKey[3] = inputKey[3];
        permutedKey[4] = inputKey[7];
        permutedKey[5] = inputKey[4];
        permutedKey[6] = inputKey[9];
        permutedKey[7] = inputKey[8];
        return permutedKey;
    }
    //initial permutation for plaintext
    public static byte[] functionIP(byte[] plaintext) {
        byte[] ip = new byte[8];
        ip[0] = plaintext[1];
        ip[1] = plaintext[5];
        ip[2] = plaintext[2];
        ip[3] = plaintext[0];
        ip[4] = plaintext[3];
        ip[5] = plaintext[7];
        ip[6] = plaintext[4];
        ip[7] = plaintext[6];
        return ip;
    }
    //inverse initial permuation for end of algorithm
    public static byte[] inverseIP(byte[] input) {
        byte[] ip = new byte[8];
        ip[0] = input[3];
        ip[1] = input[0];
        ip[2] = input[2];
        ip[3] = input[4];
        ip[4] = input[6];
        ip[5] = input[1];
        ip[6] = input[7];
        ip[7] = input[5];
        return ip;
    }
    //xor operation on two byte arrays
    public static byte[] bitXOR(byte[] x, byte[] y) {
        byte[] xor = new byte[x.length];
        for (int i = 0; i < x.length; i++) {
            if (x[i] != y[i]) {
                xor[i] = 1;
            } else {
                xor[i] = 0;
            }
        }
        return xor;
    }
    //combine two input arrays into one retaining the order
    public static byte[] combineArray(byte[] x, byte[] y) {
        byte[] combined = new byte[x.length + y.length];
        System.arraycopy(x, 0, combined, 0, x.length);
        System.arraycopy(y, 0, combined, x.length, y.length);
        return combined;
    }
    //function f_k a combination of permuation and substitution functions
    public static byte[] fSubK(byte[] input, byte[] key) {
        byte[] left4 = Arrays.copyOfRange(input, 0, 4);
        byte[] right4 = Arrays.copyOfRange(input, 4, input.length);
        byte[] xor = bitXOR(left4, mappingF(right4, key));
        byte[] combined = combineArray(xor, right4);
        return combined;
    }
    //expansion/permuation operation
    public static byte[] ep(byte[] number) {
        byte[] ep = new byte[8];
        ep[0] = number[3];
        ep[1] = number[0];
        ep[2] = number[1];
        ep[3] = number[2];
        ep[4] = number[1];
        ep[5] = number[2];
        ep[6] = number[3];
        ep[7] = number[0];
        return ep;
    }
    //s box operation with s box 0
    public static byte[] sBox0(byte[] row) {
        int[][] s0 = { { 1, 0, 3, 2 },
                { 3, 2, 1, 0 },
                { 0, 2, 1, 3 },
                { 3, 1, 3, 2 } };
        int s0Row = row[0] * 2 + row[3] * 1;
        int s0Col = row[1] * 2 + row[2] * 1;
        byte[] output = new byte[2];
        int num = s0[s0Row][s0Col];
        if (num == 3) {
            output[0] = 1;
            output[1] = 1;
        } else if (num == 2) {
            output[0] = 1;
            output[1] = 0;
        } else if (num == 1) {
            output[0] = 0;
            output[1] = 1;
        } else {
            output[0] = 0;
            output[1] = 0;
        }
        return output;
    }
    //s box operation with s box 1
    public static byte[] sBox1(byte[] row) {
        int[][] s1 = { { 0, 1, 2, 3 },
                { 2, 0, 1, 3 },
                { 3, 0, 1, 0 },
                { 2, 1, 0, 3 } };
        int s1Row = row[0] * 2 + row[3] * 1;
        int s1Col = row[1] * 2 + row[2] * 1;
        byte[] output = new byte[2];
        int num = s1[s1Row][s1Col];
        if (num == 3) {
            output[0] = 1;
            output[1] = 1;
        } else if (num == 2) {
            output[0] = 1;
            output[1] = 0;
        } else if (num == 1) {
            output[0] = 0;
            output[1] = 1;
        } else {
            output[0] = 0;
            output[1] = 0;
        }
        return output;
    }
    //permuation 4 for after s boxes
    public static byte[] perm4(byte[] input) {
        byte[] output = new byte[4];
        output[0] = input[1];
        output[1] = input[3];
        output[2] = input[2];
        output[3] = input[0];
        return output;
    }
    //mapping of f is part of f sub k for the left input
    public static byte[] mappingF(byte[] number, byte[] subkey) {
        byte[] xor = bitXOR(ep(number), subkey);
        byte[] firstRow = Arrays.copyOfRange(xor, 0, 4);
        byte[] secondRow = Arrays.copyOfRange(xor, 4, xor.length);
        byte[] combined = combineArray(sBox0(firstRow), sBox1(secondRow));
        return perm4(combined);
    }
    //switch function interchanges left and right 4 bits
    public static byte[] switchFunction(byte[] input) {
        byte[] output = new byte[8];
        output[0] = input[4];
        output[1] = input[5];
        output[2] = input[6];
        output[3] = input[7];
        output[4] = input[0];
        output[5] = input[1];
        output[6] = input[2];
        output[7] = input[3];
        return output;
    }
}
