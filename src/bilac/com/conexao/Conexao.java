package bilac.com.conexao;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Conexao extends Thread {
  
  private Socket socket;
  
  @Override public void run() {
    super.run();
    try {
      this.socket = new Socket("", 4811);
      PrintWriter writer = new PrintWriter(socket.getOutputStream());
      Scanner scanner = new Scanner(socket.getInputStream());
      String msg;
      while ((msg = scanner.nextLine()) != null) {
         System.out.println(msg);
      }
    } catch(Exception e){ System.err.println(e.getMessage()); }
  }
    
}
