package bilac.com.listener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import bilac.com.main.Arena;
import bilac.com.main.Tanque;

public class EscutaClick implements MouseListener {
  
  private Arena arena;
  
  public EscutaClick(Arena arena) {
    this.arena = arena;
  }
  
  @Override
  public void mouseClicked(MouseEvent e) {
    for(Tanque tanque : this.arena.getTanques()) {
      tanque.setEstaAtivo(false);
      boolean tanqueClicado = tanque.pegarAreaClicavelDoTanque().contains(e.getX(), e.getY());
      if (tanqueClicado){
        tanque.setEstaAtivo(true);
        this.arena.setTanqueSelecionado(tanque);
      }
    }
    this.arena.repaint();
  }

  @Override
  public void mouseEntered(MouseEvent e) { }

  @Override
  public void mouseExited(MouseEvent e) { }

  @Override
  public void mousePressed(MouseEvent e) { }

  @Override
  public void mouseReleased(MouseEvent e) { }
  
}
