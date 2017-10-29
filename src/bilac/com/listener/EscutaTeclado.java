package bilac.com.listener;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import bilac.com.main.Arena;
import bilac.com.main.Tanque;

public class EscutaTeclado implements KeyListener {
  
  private Arena arena;
  
  public EscutaTeclado(Arena arena) {
    this.arena = arena;
  }
  
  @Override
  public void keyPressed(KeyEvent e) {
    for(Tanque tanque : this.arena.getTanques()) {
      tanque.setEstaAtivo(false);
      if(tanque == this.arena.getTanqueSelecionado()) {
        tanque.setEstaAtivo(true);
        switch(e.getKeyCode()){
          case KeyEvent.VK_A: tanque.girar(-10); break;
          case KeyEvent.VK_D: tanque.girar(10); break;
          case KeyEvent.VK_W: tanque.aumentarVelocidade(); break;
          case KeyEvent.VK_S : tanque.diminuirVelocidade(); break;
          case KeyEvent.VK_SPACE: tanque.atirar();
          break;
        }
        break;
      }
      this.arena.repaint();
    }
  }

  @Override
  public void keyReleased(KeyEvent e) { }

  @Override
  public void keyTyped(KeyEvent e) { }

}
