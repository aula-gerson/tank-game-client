package bilac.com.listener;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.PrintWriter;

import bilac.com.encryption.Encrypt;
import bilac.com.entidades.Tanque;
import bilac.com.main.Arena;

public class EscutaTeclado implements KeyListener {
  
  private Arena arena;
  
  public EscutaTeclado(Arena arena) {
    this.arena = arena;
  }
  
  @Override
  public void keyPressed(KeyEvent e) {
    switch(e.getKeyCode()){
      case KeyEvent.VK_A: {
        getWriter().println(Encrypt.text("GIRAR_ANTI_HORARIO", 124));
        getWriter().flush();
        break;
      }
      case KeyEvent.VK_D: {
        getWriter().println(Encrypt.text("GIRAR_HORARIO", 124));
        getWriter().flush();
        break;
      }
      case KeyEvent.VK_W: {
        getWriter().println(Encrypt.text("AUMENTAR_VELOCIDADE", 124));
        getWriter().flush();
        break;
      }
      case KeyEvent.VK_S : {
        getWriter().println(Encrypt.text("DIMINUIR_VELOCIDADE", 124));
        getWriter().flush();
        break;
      }
      case KeyEvent.VK_SPACE: {
        getWriter().println(Encrypt.text("ATIRAR", 124));
        getWriter().flush();
        break;
      }
    }
    this.arena.repaint();
  }

  @Override
  public void keyReleased(KeyEvent e) { }

  @Override
  public void keyTyped(KeyEvent e) { }
  
  private PrintWriter getWriter() {
    return this.arena.getWriter();
  }

}
