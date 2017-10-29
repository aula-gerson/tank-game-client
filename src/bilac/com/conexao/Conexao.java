package bilac.com.conexao;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Conexao extends Thread {
  
  private Socket socket;
  
  @Override public void run() {
    super.run();
    try {
      socket = new Socket("", 4811);
      PrintWriter writer = new PrintWriter(socket.getOutputStream());
      Scanner scanner = new Scanner(socket.getInputStream());
      
    } catch(Exception e){ System.err.println(e.getMessage()); }
  }
  
}
