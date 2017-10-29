package bilac.com.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashSet;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class Arena extends JComponent implements MouseListener, ActionListener, KeyListener {
  
  private static final int LARGURA_ARENA = 640;
  private static final int ALTURA_ARENA = 480;

  private HashSet<Tanque> tanques;
  private Tanque tanqueSelecionado;
  private Timer contador;
  
  public Arena(){
    this.tanques = new HashSet<Tanque>();
    this.contador = new Timer(40, this);
    this.contador.start();
    addMouseListener(this);
    addKeyListener(this);
    setFocusable(true);
  }
  
  public void adicionaTanque(Tanque t) {
    this.tanques.add(t);
  }
  
  public Dimension getMaximumSize() {
    return getPreferredSize();
  }
  
  public Dimension getMinimumSize() {
    return getPreferredSize();
  }

  public Dimension getPreferredSize() {
    return new Dimension(LARGURA_ARENA, ALTURA_ARENA);
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D)g;
    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
    RenderingHints.VALUE_ANTIALIAS_ON);
    g2d.setColor(new Color(245,245,255));
    g2d.fillRect(0,0, LARGURA_ARENA, ALTURA_ARENA);
    g2d.setColor(new Color(220,220,220));
    // Cria gade de fundo
    for(int largura = 0; largura <= LARGURA_ARENA; largura += 20)
      g2d.drawLine(largura, 0, largura, ALTURA_ARENA);
    for(int altura=0; altura <= ALTURA_ARENA; altura+=20) 
      g2d.drawLine(0, altura, LARGURA_ARENA, altura);
    for(Tanque tanque : this.tanques) {
      tanque.verificarColisaoComOsTanques(tanques);
      tanque.draw(g2d);
      Tanque tanqueAtingido = tanque.getTiro().verificarColisaoComOsTanques(tanques);
      tanques.remove(tanqueAtingido);
      tanque.getTiro().draw(g2d);
    }
  }

  public void mouseClicked(MouseEvent e) {
    for(Tanque tanque : this.tanques) tanque.setEstaAtivo(false);
    for(Tanque tanque : this.tanques) {
      boolean tanqueClicado = tanque.pegarAreaClicavelDoTanque().contains(e.getX(), e.getY());
      if (tanqueClicado){
        tanque.setEstaAtivo(true);
        tanqueSelecionado= tanque;
      }
    }
    repaint();
  }

  public void mouseEntered(MouseEvent e) { }

  public void mouseExited(MouseEvent e) { }

  public void mousePressed(MouseEvent e) { }

  public void mouseReleased(MouseEvent e) { }
  
  public void actionPerformed(ActionEvent e) {
    for(Tanque tanque : this.tanques){
      tanque.mover();
      tanque.getTiro().mover();
      tanque.calculaTempo();
    }
    repaint();
  }

  public void keyPressed(KeyEvent e) {
    for(Tanque tanque : this.tanques) {
      tanque.setEstaAtivo(false);
      if(tanque == tanqueSelecionado) {
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
      repaint();
    }
  }
  
  @Override
  public void keyReleased(KeyEvent e) { }

  @Override
  public void keyTyped(KeyEvent e) { }

  public static void main(String args[]) {
    Arena arena = new Arena();
    arena.adicionaTanque(new Tanque(400,50,180,Color.BLUE));
    arena.adicionaTanque(new Tanque(400,200,0,Color.RED));
    arena.adicionaTanque(new Tanque(400,300,270,Color.GREEN));
    arena.adicionaTanque(new Tanque(200,50,90,Color.YELLOW));
    arena.adicionaTanque(new Tanque(100,120,270,Color.GRAY));
    arena.adicionaTanque(new Tanque(180,307,180,Color.WHITE));
    arena.adicionaTanque(new Tanque(520,208,23,Color.CYAN));
    arena.adicionaTanque(new Tanque(300,300,47,Color.ORANGE));
    
    JFrame janela = new JFrame("JTank");
    janela.getContentPane().add(arena);
    janela.setResizable(false);
    janela.pack();
    janela.setVisible(true);
    janela.setDefaultCloseOperation(3);
  }
 
}
