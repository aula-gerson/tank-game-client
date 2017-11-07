package bilac.com.encryption;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import bilac.com.entidades.Tanque;
import bilac.com.entidades.Tiro;

public class Encrypt {
  
  private static int key;
  private static String[] ALPHABET = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "X", "Z", "W", "Y", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "@", "#", "$", "%", "*", "(", ")", "&", "-", "_", "=", "+", ",", ".", ";", ":", "?", "/", "!", " "};
    
  public static String text(String message, int key) {
    Encrypt.key = removeToUnnecessaryTurns(key);
    return eachLetterOfTheWord(message);
  }
  
  public static List<Tanque> listaDeTanques(List<Tanque> tanques, int key) {
    Encrypt.key = removeToUnnecessaryTurns(key);
    for (Tanque tanque : tanques) {
      tanque = Encrypt.tanque(tanque);
    }
    return tanques;
  }
  
  public static Tanque tanque(Tanque tanque) {
    tanque.setX(eachLetterOfTheWord(tanque.getXString()));
    tanque.setY(eachLetterOfTheWord(tanque.getYString()));
    tanque.setAngulo(eachLetterOfTheWord(tanque.getAnguloString()));
    tanque.setVelocidade(eachLetterOfTheWord(tanque.getVelocidadeString()));
    tanque.setEstaAtivo(eachLetterOfTheWord(tanque.isEstaAtivoString()));
    tanque.setEstaVivo(eachLetterOfTheWord(tanque.isEstaVivoString()));
    tanque.setContador(eachLetterOfTheWord(tanque.getContadorString()));
    tanque.setTiro(Encrypt.tiro(tanque.getTiro()));
    return tanque;
  }
  
  public static Tiro tiro(Tiro tiro) {
    tiro.setX(eachLetterOfTheWord(tiro.getXString()));
    tiro.setY(eachLetterOfTheWord(tiro.getYString()));
    tiro.setAngulo(eachLetterOfTheWord(tiro.getAnguloString()));
    tiro.setEstaAtivo(eachLetterOfTheWord(tiro.isEstaAtivoString()));
    return tiro;
  }
  
  private static String eachLetterOfTheWord(String word) {
    String encryptedWord = "";
    for(int i = 0; i < word.length(); i++) {
      String letter = formatLetter(word.charAt(i));
      encryptedWord += findLetterInAlphabet(letter);
    }
    return encryptedWord;
  }
  
  private static String findLetterInAlphabet(String letter) {
    for (int i = 0; i < Encrypt.ALPHABET.length; i++) {
      if(Encrypt.ALPHABET[i].contains(letter)) {
        return encryptLetter(i);
      }
    }
    return " ";
  }
  
  private static String encryptLetter(int pos) {
    int codeLetter = pos + Encrypt.key;
    if(codeLetter < 0) {
      return Encrypt.ALPHABET[Encrypt.ALPHABET.length + codeLetter];
    }
    if(codeLetter >= Encrypt.ALPHABET.length) {
      return Encrypt.ALPHABET[codeLetter - Encrypt.ALPHABET.length];
    }
    return Encrypt.ALPHABET[codeLetter];
  }
  
  private static int removeToUnnecessaryTurns(int key) {
    return key - (key / Encrypt.ALPHABET.length) * Encrypt.ALPHABET.length;
  }
  
  private static String formatLetter(char letter) {
    return Character.toString(letter).toUpperCase();
  }
  
}
