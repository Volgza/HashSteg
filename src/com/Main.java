package com;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.Random;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;

import static com.variable.arrayKey;
import static java.lang.Math.ceil;


public class Main {
    public static void main(String[] args) throws NoSuchAlgorithmException, IOException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {

        /*Считываем сообщение для вложения. Выход в строку InputMess*/

        InputStream inputStream = System.in;
        Reader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String InputMess = bufferedReader.readLine();
        System.out.println(InputMess);


        //Шифруем

        StringBuffer appendMess = new StringBuffer();
        String[] StringArMess = new String[InputMess.length()];
        for (int i = 0; i < InputMess.length(); i++) {
            int asciiValue = InputMess.charAt(i);
            String binaryMess = Integer.toBinaryString(asciiValue);
            if (binaryMess.length() < 11) {
                int LengthOfZero = 11 - binaryMess.length();
                binaryMess = "0".repeat(LengthOfZero) + binaryMess;
                appendMess.append(binaryMess);
                StringArMess[i] = binaryMess;
            } else {
                appendMess.append(binaryMess);
                StringArMess[i] = binaryMess;
            }
        }
        System.out.println(appendMess);
        System.out.println(Arrays.toString(StringArMess));
        int length = (int) ceil(appendMess.length() / 11.0);
        //System.out.println("длина "+length);
        //com.key.getKey();
        //SecureRandom randomKey = new SecureRandom();
        //short[] comarrayKey = new short[length];
        /*for (int l = 0; l < length; l++) {
            arrayKey[l] = (short) randomKey.nextInt();
        }*/

            System.out.println("первый элемент ключа " + Integer.toBinaryString(arrayKey[0]));

            short[] appendMessAr = new short[length];
            for (int i = 0; i < length; i++) {
                appendMessAr[i] = Short.parseShort(StringArMess[i], 2);
            }

            //System.out.println("первый элемент массива " + Integer.toBinaryString(appendMessAr[0]));
            short[] cipherMess = new short[length];
            for (int i = 0; i < cipherMess.length; i++) {
                cipherMess[i] = (short) (appendMessAr[i] ^ arrayKey[i]);
            }
            //System.out.println("первый элемент зашифр массива " + Integer.toBinaryString(cipherMess[0]));
            //System.out.println("length "+cipherMess.length);
            StringBuilder cipherSb = new StringBuilder();
            for (short cipM : cipherMess) {
                String StcipM = Integer.toBinaryString(cipM);
                if (StcipM.length() < 32) {
                    int LengthOfZero = 32 - StcipM.length();
                    StcipM = "0".repeat(LengthOfZero) + StcipM;
                    cipherSb.append(StcipM.substring(21, 32));
                } else {
                    cipherSb.append(StcipM.substring(21, 32));
                }
            }
            System.out.println("зашифрованная строка\n" + cipherSb.toString());
        /*int decLenght = (int) ceil(cipherSb.length()/11.0);
        StringBuilder decodSb = new StringBuilder();
        String[] decodeMess = new String[decLenght];
        int j=0;
        for (int i = 0; i < cipherSb.length()-10; i = i + 11) {
                decodeMess[j] = cipherSb.substring(i, i + 11);
                j++;
            }

        //System.out.println("массив зашифрованных строк " + Arrays.toString(decodeMess));

        short[] DecodeappendMessAr = new short[length];
        for (int i = 0; i<decLenght; i++) {
            DecodeappendMessAr[i] = Short.parseShort(decodeMess[i], 2);
        }
        //System.out.println("первый элемент массива для расшифровки "+Integer.toBinaryString(DecodeappendMessAr[0]));

        short[] DecodeCipherMess = new short[length];
        for (int i=0; i<decLenght; i++) {
            DecodeCipherMess[i] = (short) (DecodeappendMessAr[i] ^ arrayKey[i]);
        }
        String[] DecodecipherSb = new String[decLenght];
        for (int i=0;i<decLenght; i++) {
            String StcipM = Integer.toBinaryString(DecodeCipherMess[i]);
            if (StcipM.length() < 32) {
                int LengthOfZero = 32 - StcipM.length();
                StcipM = "0".repeat(LengthOfZero) + StcipM;
                DecodecipherSb[i] = StcipM.substring(21, 32);
            } else {
                DecodecipherSb[i] = StcipM.substring(21, 32);
            }
        }
        System.out.println("массив расшифрованных символов"+Arrays.toString(DecodecipherSb));
        StringBuilder decryptedString = new StringBuilder();
        for (int i=0; i<decLenght; i++) {
            int outascii = Integer.parseInt(DecodecipherSb[i],2);
            decryptedString.append((char) outascii);
        }
        System.out.println(decryptedString);*/

            char[] arrayChar = cipherSb.toString().toCharArray();
            /*for (char charM : arrayChar) {
                System.out.println(charM);
            }*/
            File text = new File("/home/maria/IdeaProjects/HashSteg/src/com/InputText.txt");

            BufferedReader ReadText = new BufferedReader(new FileReader(text));
            String lineText;
            StringBuffer appendText = new StringBuffer();
            while ((lineText = ReadText.readLine()) != null)
                appendText.append(lineText);
            //System.out.println(appendText);
            String[] array = appendText.toString().split(",|\\.|\\;|\"");
            /*for (String word : array) {
                System.out.println(word);
            }*/
            String[] arrayForMess = new String[arrayChar.length];
            if (array.length < arrayChar.length)
                System.out.println("Текст недостаточно длинный для вложения");
            else {

                for (int i = 0; i < arrayForMess.length; i++) {
                    arrayForMess[i] = array[i];
                }
            }
            /*try (FileWriter writer = new FileWriter("/home/maria/IdeaProjects/HashSteg/src/com/MiddleText.txt", false)) {
                for (String forMess : arrayForMess) {
                    writer.write(forMess + "\n");
                }
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }*/
            File MiddleText = new File("/home/maria/IdeaProjects/HashSteg/src/com/MiddleText.txt");

            BufferedReader ReadMiddle = new BufferedReader(new FileReader(MiddleText));
            String MiddlelineText;
            String[] arrayMiddle = new String[arrayForMess.length];
            //StringBuffer appendMiddleText = new StringBuffer();
            for (int i = 0; i < arrayForMess.length; i++) {
                arrayMiddle[i] = ReadMiddle.readLine();
            }
            int f = 0;
            while ((MiddlelineText = ReadMiddle.readLine()) != null) {
                arrayMiddle[f] = MiddlelineText;
                f++;
            }
            //String[] arrayMiddle = appendMiddleText.toString().split("\\n");
            /*for (String word : arrayMiddle) {
                System.out.println(word);
            }*/
            MessageDigest md = MessageDigest.getInstance("MD5");
            char[] firstbit = new char[arrayMiddle.length];
            for (int i = 0; i < arrayMiddle.length; i++) {
                byte[] hash = md.digest(arrayMiddle[i].getBytes(StandardCharsets.UTF_8));
                StringBuffer sb = new StringBuffer();
                for (int j = 0; j < hash.length; j++) {
                    String s = Integer.toBinaryString(0xff & hash[j]);
                    if (s.length() < 8) {
                        int LengthOfZero = 8 - s.length();
                        s = "0".repeat(LengthOfZero) + s;
                    }
                    sb.append(s);
                }
                //System.out.println(String.format("%s", sb.toString()));
                firstbit[i] = sb.charAt(0);
            }
            System.out.print("массив хешей редактируемого текста\n");
            for (char bit : firstbit) {
                System.out.print(bit);
            }
            if (Arrays.equals(arrayChar, firstbit)) {
                System.out.println("\nSucceed");
            } else {
                System.out.println("\nUnsucceed");
            }
            try (FileReader fr = new FileReader("/home/maria/IdeaProjects/HashSteg/src/com/MiddleText.txt");
                 FileWriter fw = new FileWriter("/home/maria/IdeaProjects/HashSteg/src/com/OutputText.txt")) {
                int c = fr.read();
                while(c!=-1) {
                    fw.write(c);
                    c = fr.read();
                }
            } catch(IOException e) {
                e.printStackTrace();
            }
        /*String OutputText = "I haad a dream, which waas not all a dream. The bright sun was extinguish'd, and the stars. " +
                "Did waander darkling in the eternal space, Raaaabyless, and pathless, and the icy earth " +
                "Swung blind and blackening in the moonless air;";*/

            File FileOutputText = new File("/home/maria/IdeaProjects/HashSteg/src/com/OutputText.txt");

            BufferedReader CountOutputText = new BufferedReader(new FileReader(FileOutputText));
            String lineOutputText;
            //StringBuffer appendOutputText = new StringBuffer();
            int lengthOut = 0;
            while ((CountOutputText.readLine()) != null)
                lengthOut++;
            String[] outarray = new String[lengthOut];
            BufferedReader ReadOutputText = new BufferedReader(new FileReader(FileOutputText));
            for (int i = 0; i<lengthOut; i++) {
                outarray[i] = ReadOutputText.readLine();
            }
        System.out.println(Arrays.toString(outarray));
            //appendOutputText.append(lineOutputText);
            //System.out.println(appendText);

            //String[] outarray = appendOutputText.toString().split("\\r?\\n");
        //System.out.println(outarray[2]);

            MessageDigest outmd = MessageDigest.getInstance("MD5");
            char[] outfirstbit = new char[outarray.length];
            for (int k = 0; k < outarray.length; k++) {
                byte[] hash = outmd.digest(outarray[k].getBytes(StandardCharsets.UTF_8));
                StringBuffer sb = new StringBuffer();
                for (int m = 0; m < hash.length; m++) {
                    String s = Integer.toBinaryString(0xff & hash[m]);
                    if (s.length() < 8) {
                        int LengthOfZero = 8 - s.length();
                        s = "0".repeat(LengthOfZero) + s;
                    }
                    sb.append(s);
                }
                //System.out.println(String.format("%s", sb.toString()));
                outfirstbit[k] = sb.charAt(0);
            }
            StringBuilder cipherOutSb = new StringBuilder();
            for (int k = 0; k < outfirstbit.length; k++) {
                cipherOutSb.append(outfirstbit[k]);
            }
            System.out.println("зашифрованная строка выходного текста "+cipherOutSb);

            int decOutLenght = (int) ceil(cipherOutSb.length() / 11.0);
            StringBuilder decodSb = new StringBuilder();
            String[] decodeMess = new String[decOutLenght];
            int j = 0;
            for (int i = 0; i < cipherOutSb.length() - 10; i = i + 11) {
                decodeMess[j] = cipherOutSb.substring(i, i + 11);
                j++;
            }

            System.out.println("массив зашифрованных строк " + Arrays.toString(decodeMess));

            short[] DecodeappendMessAr = new short[decOutLenght];
            for (int i = 0; i < decOutLenght; i++) {
                DecodeappendMessAr[i] = Short.parseShort(decodeMess[i], 2);
            }
            System.out.println("первый элемент массива для расшифровки "+Integer.toBinaryString(DecodeappendMessAr[0]));

            short[] DecodeCipherMess = new short[length];
            for (int i = 0; i < decOutLenght; i++) {
                DecodeCipherMess[i] = (short) (DecodeappendMessAr[i] ^ arrayKey[i]);
            }
            String[] DecodecipherSb = new String[decOutLenght];
            for (int i = 0; i < decOutLenght; i++) {
                String StcipM = Integer.toBinaryString(DecodeCipherMess[i]);
                if (StcipM.length() < 32) {
                    int LengthOfZero = 32 - StcipM.length();
                    StcipM = "0".repeat(LengthOfZero) + StcipM;
                    DecodecipherSb[i] = StcipM.substring(21, 32);
                } else {
                    DecodecipherSb[i] = StcipM.substring(21, 32);
                }
            }
            System.out.println("массив расшифрованных символов"+Arrays.toString(DecodecipherSb));
            StringBuilder decryptedString = new StringBuilder();
            for (int i = 0; i < decOutLenght; i++) {
                int outascii = Integer.parseInt(DecodecipherSb[i], 2);
                decryptedString.append((char) outascii);
            }
            System.out.println(decryptedString);
        /*String outMess = outsb.toString();
        int outascii = Integer.parseInt(outMess, 2);
        System.out.println((char) outascii);*/

        }

    }




