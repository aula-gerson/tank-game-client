package bilac.com.conexao;

import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashSet;
import java.util.Scanner;

import bilac.com.main.Arena;
import bilac.com.main.Tanque;

public class Conexao extends Thread {
  
  private Socket socket;
  private Arena arena;
  private Scanner scanner;
  private ObjectInputStream reader;
  PrintWriter writer;
  
  public Conexao(Arena arena) {
    super();
    this.arena = arena;
  }

  @Override
  public void run() {
    super.run();
    try {
      this.socket = new Socket("", 4811);
      this.scanner = new Scanner(socket.getInputStream());
      this.reader = new ObjectInputStream(socket.getInputStream());
      this.writer = new PrintWriter(socket.getOutputStream());
      this.arena.setWriter(writer);
      escutaServidor(reader);
    } catch(Exception e){ System.err.println(e.getMessage()); }
  }
  
  @SuppressWarnings("unchecked")
  private void escutaServidor(ObjectInputStream reader) {
    try {
      HashSet<Tanque> tanques;
      while((tanques = (HashSet<Tanque>) reader.readObject()) != null) {
        this.arena.setTanques(tanques);
        this.arena.repaint();
      }
    } catch (Exception e) { System.err.println(e.getMessage()); }
  }
    
}
