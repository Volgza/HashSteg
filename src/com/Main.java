package com;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.sql.SQLOutput;
import java.util.Arrays;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;

import static javax.crypto.Cipher.ENCRYPT_MODE;

public class Main {
    public static void main(String[] args) throws NoSuchAlgorithmException, IOException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {

        /*Считываем сообщение для вложения. Выход в строку InputMess*/
        int a = -111;
        System.out.println(Integer.toBinaryString(a));
        /*InputStream inputStream = System.in;
        Reader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String InputMess = bufferedReader.readLine();
        System.out.println(InputMess);
        *//*Шифруем*//*
        *//*Считываем ключ с клавиатуры в символьную строку*//*
        InputStream inputKey = System.in;
        Reader inputKeyReader = new InputStreamReader(inputKey);
        BufferedReader KeyReader = new BufferedReader(inputKeyReader);
        String InputKeyString = KeyReader.readLine();
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        byte[] keyBytes = InputKeyString.getBytes(StandardCharsets.UTF_8);
        SecureRandom secRandom = new SecureRandom(keyBytes);
        keyGen.init(secRandom);
        Key key = keyGen.generateKey();

        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(ENCRYPT_MODE, key);
        byte[] cipherMess = cipher.doFinal(InputMess.getBytes(StandardCharsets.UTF_8));*/
        /*for (byte Mbyte : cipherMess) {
        System.out.println(Mbyte); }*/

        /*StringBuffer appendMess = new StringBuffer();
        for (int i = 0; i < cipherMess.length; i++) {
            //int asciiValue = InputMess.charAt(i);
            String binaryMess = Integer.toBinaryString(cipherMess[i]);
            if (binaryMess.length() < 8) {
                int LengthOfZero = 8 - binaryMess.length();
                binaryMess = "0".repeat(LengthOfZero) + binaryMess;
                appendMess.append(binaryMess);
            }
            else {
                appendMess.append(binaryMess);
            }
        }
        System.out.println(appendMess);*/

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


