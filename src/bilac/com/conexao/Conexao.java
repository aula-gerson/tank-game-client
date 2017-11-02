package bilac.com.conexao;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import bilac.com.main.Arena;

public class Conexao extends Thread {
  
  private Socket socket;
  private Arena arena;
  
  public Conexao(Arena arena) {
    super();
    this.arena = arena;
  }

  @Override public void run() {
    super.run();
    try {
      this.socket = new Socket("", 4811);
      Scanner scanner = new Scanner(socket.getInputStream());
      PrintWriter writer = new PrintWriter(socket.getOutputStream());
      this.arena.setWriter(writer);
      String msg;
      while ((msg = scanner.nextLine()) != null) {
         System.out.println(msg);
      }
    } catch(Exception e){ System.err.println(e.getMessage()); }
  }
    
}
