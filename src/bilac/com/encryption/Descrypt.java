package bilac.com.encryption;

import java.util.List;

import bilac.com.entidades.Tanque;

public class Descrypt {
  
  private static int key;
  private static String[] ALPHABET = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "X", "Z", "W", "Y", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "@", "#", "$", "%", "*", "(", ")", "&", "-", "_", "=", "+", ",", ".", ";", ":", "?", "/", "!", " "};
  
  
  public static String text(String text, int key) {
    Descrypt.key = removeToUnnecessaryTurns(key);
    return eachEncryptedLetter(text);
  }
  
  public static List<Tanque> listaDeTanques(List<Tanque> tanques, int key) {
    Descrypt.key = removeToUnnecessaryTurns(key);
    for (Tanque tanque : tanques) {
      tanque = Descrypt.tanque(tanque);
    }
    return tanques;
  }
  
  public static Tanque tanque(Tanque tanque) {
    tanque.setX(eachEncryptedLetter(tanque.getXString()));
    tanque.setY(eachEncryptedLetter(tanque.getYString()));
    tanque.setAngulo(eachEncryptedLetter(tanque.getAnguloString()));
    tanque.setVelocidade(eachEncryptedLetter(tanque.getVelocidadeString()));
    tanque.setEstaAtivo(eachEncryptedLetter(tanque.isEstaAtivoString()));
    tanque.setEstaVivo(eachEncryptedLetter(tanque.isEstaVivoString()));
    tanque.setContador(eachEncryptedLetter(tanque.getContadorString()));
    return tanque;
  }
  
  private static String eachEncryptedLetter(String encryptedWord) {
    String word = "";
    for(int i = 0; i < encryptedWord.length(); i++) {
      String encryptedLetter = formatString(encryptedWord.charAt(i));
      word += findLetterInAlphabet(encryptedLetter);
    }
    return word;
  }
  
  private static String findLetterInAlphabet(String encryptedLetter) {
    for (int i = 0; i < Descrypt.ALPHABET.length; i++) {
      if(Descrypt.ALPHABET[i].contains(encryptedLetter)) {
        return decryptLetter(i);
      }
    }
    return " ";
  }
  
  private static int removeToUnnecessaryTurns(int key) {
    return key - (Math.floorDiv(key, Descrypt.ALPHABET.length)) * Descrypt.ALPHABET.length;
  }
  
  private static String decryptLetter(int pos) {
    int codeLetter = pos - Descrypt.key;
    if(codeLetter < 0) {
      return Descrypt.ALPHABET[Descrypt.ALPHABET.length + codeLetter];
    }
    if(codeLetter >= Descrypt.ALPHABET.length) {
      return Descrypt.ALPHABET[codeLetter - Descrypt.ALPHABET.length];
    }
    return Descrypt.ALPHABET[codeLetter];
  }
  
  private static String formatString(char letter) {
    return Character.toString(letter).toUpperCase();
  }
  
}
