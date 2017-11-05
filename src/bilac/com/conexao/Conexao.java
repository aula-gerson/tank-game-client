package bilac.com.conexao;

import java.awt.Color;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

import bilac.com.encryption.Descrypt;
import bilac.com.entidades.Tanque;
import bilac.com.main.Arena;

public class Conexao extends Thread {
  
  private Socket socket;
  private Arena arena;
  private ObjectInputStream reader;
  private Boolean firstTime;
  private int playerId;
  
  public Conexao(Arena arena) {
    super();
    this.firstTime = true;
    this.arena = arena;
  }

  @Override
  public void run() {
    super.run();
    try {
      this.socket = new Socket("", 4811);
      this.reader = new ObjectInputStream(socket.getInputStream());
      PrintWriter writer = new PrintWriter(socket.getOutputStream());
      this.arena.setWriter(writer);
      escutaServidor(reader);
    } catch(Exception e){ System.err.println(e.getMessage()); }
  }
  
  @SuppressWarnings("unchecked")
  private void escutaServidor(ObjectInputStream reader) {
    try {
      List<Tanque> tanques;
      while((tanques = (List<Tanque>) reader.readObject()) != null) {
        tanques = Descrypt.listaDeTanques(tanques, 124);
        this.arena.setTanques(tanques);
        this.arena.repaint();
        selecionaTanque(tanques);
      }
    } catch (Exception e) { System.err.println(e.getMessage()); }
  }
  
  private void selecionaTanque(List<Tanque> tanques) {
    if(this.firstTime) {
      this.playerId = tanques.size()-1;
      this.firstTime = false;
    }
    Tanque player = tanques.get(playerId);
    player.setCor(Color.BLUE);
    player.setEstaAtivo(true);
  }
    
}
