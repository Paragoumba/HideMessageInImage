package fr.paragoumba.hidemessageinimage;

public class Test {

    public static void main(String[] args) {

        String blue = "44 44 44 45 45 46 47 47 48 48 49 50 50 51 51 51 51 51 52 53 54 " +
                "54 54 54 54 54 54 54 54 55 56 57 58 59 60 61 62 63 64 65 66 67 68 69 70 71 72 73 74 75 76 77 78 78 78 78 78 78 78 78 78 79 80 81 82 83 84 85 86 86 86 86 86 86 86 86 86 86 86 86 86 86 86 86 86 86 86 86 86 86 86 86 86 87 88 89 90 91 92 93 94 95 96 97 98 99 100 101 102 102 102 102 102 102 102 102 102 102 102 102 102 102 102 102 102 102 43 43 43 43 43 43 43 43 43 43 43 43 43 43 43 43 43 43 43 43 43 43 43 43 43 43 43 43 43 43 43 43 43 43 43 43 43 43 43 43 43 43 43 43 43 43 43 43 43 43 43 43 43 43 43 43 43 43 43 43 43 43 43 43 43 43 43 43 43 43 43 43 43 43 43 43 43 43 43 43 43 43 43 43 43 43 43 43 43 43 43 43 43 43 43 43 43 43 43 43 43 43 43 43 43 ";

        System.out.println("43 43 43 43 43 43 43 43 43 43 43 43 43 43 43 43 43 43 43 43 43");
        System.out.println("43 42 42 43 42 43 43 42 43 42 43 43 42 43 42 42 42 42 43 43 43");
        System.out.println(" 1  0  0  1  0  1  1  0  1  0  1  1  0  1  0  0  0  0  1  1  1");

        System.out.println("100101101011010000111");
        System.out.println("011101000110010101110 01101110100001000000011000100100000001100100010000001110100011  001010111001101110100");
        System.out.println("                      01101110100001000000011000100100000001100100010000001110100011 001010111001101110100");
        System.out.println("                                                                         01110100011                          00000000000000000000000000000000000000000");

        System.out.println("01110100 01100101 01110011 01110100 00100000 00110001 00100000 00110010 00100000 01110100 01100101 01110011 01110100");
        System.out.println("       t        e        s        t      ' '        1      ' '        2      ' '        t        e        s        t");

        System.out.println("11111110 11111111 00000000 01110100 00000000 01100101 00000000 01110011 00000000 01110100 00000000 00100000 00000000 00110001 00000000 00100000 00000000 00110010 00000000 00100000 00000000 01110100 00000000 01100101 00000000 01110011 00000000 01110100");
        System.out.println("utf-16 start char                 t                 e                 s                 t               ' '                 1               ' '                 2               ' '                 t                 e                 s                 t");

        String[] values = blue.split(" ");
        String controlBits = "";
        String bits = "";
        String messageBits = "";

        for (int i = 0; i < 22; ++i) controlBits += Integer.parseInt(values[i]) % 2;
        for (int i = 22; i < values.length; ++i) bits += Integer.parseInt(values[i]) % 2;
        for (byte b : "test 1 2 test".getBytes()) messageBits += String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(" ", "0");

        System.out.println(controlBits.contains("100101101011010000111"));
        System.out.println(bits.contains(messageBits));
        System.out.println(controlBits);
        System.out.println(bits);

    }

    private static int makeFilter(){

        int i = 6;

        //Puts a 1 at the ith bit
        return 1 << 8 - i;

    }

    private static int[] addByteToInt(){

        int[] ints = {250, 250, 250, 250, 250, 250, 250, 250};
        byte[] bytes = getBits(new byte[]{0b01110011});

        for (int i = 0; i < ints.length; ++i){

            ints[i] += bytes[i];

        }

        return ints;

    }

    private static byte[] getBits(byte[] bytes){

        byte[] newBytes = new byte[bytes.length * 8];

        for (int i = 0; i < bytes.length; ++i){

            String[] bits = String.format("%8s", Integer.toBinaryString(bytes[i] & 0xFF)).replace(" ", "0").split("");

            for (int j = 0; j < 8; ++j){

                newBytes[i * 8 + j] = Byte.parseByte(bits[j]);

            }
        }

        return newBytes;

    }

    private static void dispBits(byte b){

        System.out.println(String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(" ", "0"));

    }

    private static void dispBits(int b){

        System.out.println(String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(" ", "0"));

    }
}
