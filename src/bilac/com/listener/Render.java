package bilac.com.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import bilac.com.main.Arena;
import bilac.com.main.Tanque;

public class Render implements ActionListener {
  
  private Arena arena;
  
  public Render(Arena arena) {
    this.arena = arena;
  }
  
  public void start() {
    Timer renderizacao = new Timer(40, this);
    renderizacao.start();
  }
  
  @Override
  public void actionPerformed(ActionEvent e) {
    for(Tanque tanque : this.arena.getTanques()){
      tanque.mover();
      tanque.getTiro().mover();
      tanque.calculaTempo();
    }
    this.arena.repaint();

  }

}
