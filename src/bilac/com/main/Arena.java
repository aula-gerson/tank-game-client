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
public class Arena extends JComponent implements MouseListener, ActionListener,KeyListener {
  
  private HashSet<Tanque> tanques;
  private Tanque apontado;
  private Timer contador;
  private int largura, altura;
  
  public Arena(int largura,int altura){
    this.largura = largura; 
    this.altura = altura;
    this.tanques = new HashSet<Tanque>();
    this.contador = new Timer(40, this);
    this.contador.start();
    addMouseListener(this);
    addKeyListener(this);
    setFocusable(true);
  }
  
  public void adicionaTanque(Tanque t) {
    tanques.add(t);
  }
  
  public Dimension getMaximumSize() {
    return getPreferredSize();
  }
  
  public Dimension getMinimumSize() {
    return getPreferredSize();
  }

  public Dimension getPreferredSize() {
    return new Dimension(largura, altura);
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D)g;
    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
    RenderingHints.VALUE_ANTIALIAS_ON);
    g2d.setColor(new Color(245,245,255));
    g2d.fillRect(0,0,largura,altura);
    g2d.setColor(new Color(220,220,220));
    for(int _largura=0;_largura<=largura;_largura+=20) g2d.drawLine(_largura,0,_largura,altura);
    for(int _altura=0;_altura<=altura;_altura+=20) g2d.drawLine(0,_altura,largura,_altura);
    for(Tanque tanque:tanques) {
      tanque.autoColisao(tanques);
      tanque.draw(g2d);
      tanque.getTiro().colisao(tanques);
      tanque.getTiro().draw(g2d);
    }
  }

  public void mouseClicked(MouseEvent e) {
    for(Tanque t:tanques) t.setEstaAtivo(false);
    for(Tanque t:tanques) {
      boolean clicado = t.getRectEnvolvente().contains(e.getX(),e.getY());
      if (clicado){
        t.setEstaAtivo(true);
        apontado= t ;
      }
    }
    repaint();
  }

  public void mouseEntered(MouseEvent e) { }

  public void mouseExited(MouseEvent e) { }

  public void mousePressed(MouseEvent e) { }

  public void mouseReleased(MouseEvent e) { }
  
  public void actionPerformed(ActionEvent e) {
    for(Tanque tanque : tanques){
      tanque.mover();
      tanque.getTiro().mover();
      tanque.calculaTempo();
    }
    repaint();
  }

  public void keyPressed(KeyEvent e) {
    for(Tanque tanque:tanques) {
      tanque.setEstaAtivo(false);
      if(tanque == apontado) {
        tanque.setEstaAtivo(true);
        switch(e.getKeyCode()){
          case KeyEvent.VK_A: tanque.girarAntiHorario(10); break;
          case KeyEvent.VK_D: tanque.girarHorario(10); break;
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
    Arena arena = new Arena(640,480);
    arena.adicionaTanque(new Tanque(400,50,180,Color.BLUE,1));
    arena.adicionaTanque(new Tanque(400,200,0,Color.RED,2));
    arena.adicionaTanque(new Tanque(400,300,270,Color.GREEN,3));
    arena.adicionaTanque(new Tanque(200,50,90,Color.YELLOW,4));
    arena.adicionaTanque(new Tanque(100,120,270,Color.GRAY,5));
    arena.adicionaTanque(new Tanque(180,307,180,Color.WHITE,6));
    arena.adicionaTanque(new Tanque(520,208,23,Color.CYAN, 7));
    arena.adicionaTanque(new Tanque(300,300,47,Color.ORANGE,8));
    
    JFrame janela = new JFrame("JTank");
    janela.getContentPane().add(arena);
    janela.setResizable(false);
    janela.pack();
    janela.setVisible(true);
    janela.setDefaultCloseOperation(3);
  }
 
}
