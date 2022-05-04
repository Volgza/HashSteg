package com;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.Random;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;

import static java.lang.Math.ceil;
import static javax.crypto.Cipher.DECRYPT_MODE;
import static javax.crypto.Cipher.ENCRYPT_MODE;

public class Main {
    public static void main(String[] args) throws NoSuchAlgorithmException, IOException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {

        /*Считываем сообщение для вложения. Выход в строку InputMess*/
        /*int a = -111;
        System.out.println(Integer.toBinaryString(0xFF & a));*/
        InputStream inputStream = System.in;
        Reader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String InputMess = bufferedReader.readLine();
        System.out.println(InputMess);


        //Шифруем
        //Считываем ключ с клавиатуры в символьную строку
        /*InputStream inputKey = System.in;
        Reader inputKeyReader = new InputStreamReader(inputKey);
        BufferedReader KeyReader = new BufferedReader(inputKeyReader);
        String InputKeyString = KeyReader.readLine();
        KeyGenerator keyGen = KeyGenerator.getInstance("ARCFOUR");
        byte[] keyBytes = InputKeyString.getBytes(StandardCharsets.UTF_8);
        SecureRandom secRandom = new SecureRandom(keyBytes);
        keyGen.init(secRandom);
        Key key = keyGen.generateKey();

        Cipher cipher = Cipher.getInstance("RC4");
        cipher.init(ENCRYPT_MODE, key);
        byte[] cipherMess = cipher.doFinal(InputMess.getBytes(StandardCharsets.UTF_8));
        StringBuilder encrSB = new StringBuilder();
        for (byte encB : cipherMess) {
            encrSB.append(encB);
        }
        System.out.println(encrSB);*/
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
        System.out.println("длина "+length);
        SecureRandom randomKey = new SecureRandom();
        short[] arrayKey = new short[length];
        for (int i =0; i<length; i++) {
            arrayKey[i] = (short) randomKey.nextInt();
        }
        System.out.println("первый элемент ключа " + Integer.toBinaryString(arrayKey[0]));
        //int keyLong = randomKey.nextInt();
        short[] appendMessAr = new short[length];
        for (int i = 0; i<length; i++) {
            appendMessAr[i] = Short.parseShort(StringArMess[i], 2);
        }

        System.out.println("первый элемент массива " + Integer.toBinaryString(appendMessAr[0]));
        short[] cipherMess = new short[length];
        for (int i=0; i<cipherMess.length; i++) {
            cipherMess[i] = (short) (appendMessAr[i] ^ arrayKey[i]);
        }
        System.out.println("первый элемент зашифр массива " + Integer.toBinaryString(cipherMess[0]));
        //System.out.println("length "+cipherMess.length);
        StringBuilder cipherSb = new StringBuilder();
        for (short cipM : cipherMess) {
            String StcipM = Integer.toBinaryString(cipM);
            if (StcipM.length() < 32) {
                int LengthOfZero = 32 - StcipM.length();
                StcipM = "0".repeat(LengthOfZero) + StcipM;
                cipherSb.append(StcipM.substring(21, 32));
            } else {
                cipherSb.append(StcipM.substring(21,32));
            }
        }
        System.out.println("зашифрованная строка " + cipherSb.toString());
        int decLenght = (int) ceil(cipherSb.length()/11.0);
        StringBuilder decodSb = new StringBuilder();
        String[] decodeMess = new String[decLenght];
        int j=0;
        for (int i = 0; i < cipherSb.length()-10; i = i + 11) {
                decodeMess[j] = cipherSb.substring(i, i + 11);
                j++;
            }

        System.out.println("массив зашифрованных строк " + Arrays.toString(decodeMess));

        short[] DecodeappendMessAr = new short[length];
        for (int i = 0; i<decLenght; i++) {
            DecodeappendMessAr[i] = Short.parseShort(decodeMess[i], 2);
        }
        System.out.println("первый элемент массива для расшифровки "+Integer.toBinaryString(DecodeappendMessAr[0]));

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
        System.out.println(decryptedString);
        /*String keyShortSt = Integer.toBinaryString(keyLong).substring(0,11);
        short keyShort = Short.parseShort(keyShortSt, 2);
        System.out.println(Integer.toBinaryString(keyShort));
        int InputMessShort = Integer.parseInt(appendMess.toString(), 2);
        System.out.println(Integer.toBinaryString(InputMessShort));*/
        //short cipherMess = (short) (keyShort ^ InputMessShort);
        //System.out.println(Integer.toBinaryString(cipherMess));

        /*cipher.init(DECRYPT_MODE, key);
        byte[] cipherMessDec = cipher.doFinal(cipherMess);
        System.out.println(Integer.toBinaryString(cipherMessDec[0]));*/

        /*char[] arrayChar = appendMess.toString().toCharArray();
       for (char charM : arrayChar) {
            System.out.println(charM);
        }
        File text = new File("/home/maria/IdeaProjects/HashSteg/src/com/InputText.txt");

        BufferedReader ReadText = new BufferedReader(new FileReader(text));
        String lineText;
        StringBuffer appendText = new StringBuffer();
        while ((lineText = ReadText.readLine()) != null)
            appendText.append(lineText);
        //System.out.println(appendText);
        String[] array = appendText.toString().split(",|\\.|\\;|\"");
        for (String word : array) {
            System.out.println(word);
        }
        String[] arrayForMess = new String[arrayChar.length];
        if (array.length<arrayChar.length)
            System.out.println("Текст недостаточно длинный для вложения");
        else {

            for (int i = 0; i < arrayForMess.length; i++) {
                arrayForMess[i] = array[i];
            }
        }
        try(FileWriter writer = new FileWriter("/home/maria/IdeaProjects/HashSteg/src/com/MiddleText.txt", false)) {
            for (String forMess : arrayForMess) {
                writer.write(forMess + "\n");
            }
        }
        catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        File MiddleText = new File("/home/maria/IdeaProjects/HashSteg/src/com/MiddleText.txt");

        BufferedReader ReadMiddle = new BufferedReader(new FileReader(MiddleText));
        String MiddlelineText;
        String[] arrayMiddle = new String[arrayForMess.length];
        //StringBuffer appendMiddleText = new StringBuffer();
       for (int i = 0; i< arrayForMess.length; i++)
       {
           arrayMiddle[i] = ReadMiddle.readLine();
       }
         int i = 0;
        while ((MiddlelineText = ReadMiddle.readLine()) != null) {
            arrayMiddle[i] = MiddlelineText;
            i++;
        }
        //String[] arrayMiddle = appendMiddleText.toString().split("\\n");
        for (String word : arrayMiddle)
        {
            System.out.println(word);
        }
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

        for (char bit : firstbit) {
            System.out.print(bit);
        }
        if (Arrays.equals(arrayChar, firstbit)) {
            System.out.println("\nSucceed");
        } else {
            System.out.println("\nUnsucceed");
        }

        String OutputText = "I haad a dream, which waas not all a dream. The bright sun was extinguish'd, and the stars. " +
                "Did waander darkling in the eternal space, Raaaabyless, and pathless, and the icy earth " +
                "Swung blind and blackening in the moonless air;";
        String[] outarray = OutputText.split(",|\\.|\\;");
        MessageDigest outmd = MessageDigest.getInstance("MD5");
        char[] outfirstbit = new char[outarray.length];
        for (int i = 0; i < outarray.length; i++) {
            byte[] hash = outmd.digest(outarray[i].getBytes(StandardCharsets.UTF_8));
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
            outfirstbit[i] = sb.charAt(0);
        }
        StringBuilder outsb = new StringBuilder();
        for (int i = 0; i < outfirstbit.length; i++) {
            outsb.append(outfirstbit[i]);
        }
        String outMess = outsb.toString();
        int outascii = Integer.parseInt(outMess, 2);
        System.out.println((char) outascii);*/

        }

    }



