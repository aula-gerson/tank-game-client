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
  private Tiro tiro;
  private Timer contador;
  private int largura, altura;
  private long agora;
  
  public Arena(int largura,int altura){
    this.largura = largura; 
    this.altura = altura;
    this.tanques = new HashSet<Tanque>();
    this.tiro = new Tiro(-10, -10, 0, null,-1);
    this.contador = new Timer(40, this);
    this.contador.start();
    this.agora = 1;
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
    for(Tanque t:tanques) t.draw(g2d);
    tiro.draw(g2d);
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
    for(Tanque t:tanques){
      t.mover();
      t.calculaTempo();
    }
    colisao();
    tiro.mover();
    repaint();
  }

  public void colisao() {
    if(tiro.estaAtivo){
      for(Tanque t : tanques){
        if(t.getId() != tiro.getId()){ 
          double dist = Math.sqrt(Math.pow(tiro.x - t.x, 2) + Math.pow(tiro.y - t.y, 2));
          if(dist <= 20){
            /*Distancia de acerto*/
            tiro.x = -10;
            tiro.y = -10;
            tanques.remove(t);
            tiro.estaAtivo = false;
            break;
          }
          /*Tanque detecta o m�ssil e tenta se esquivar*/
          if(dist < 100) { 
            t.setTempo(System.currentTimeMillis());
            if(agora%2 == 0) t.girarAntiHorario(7);
            else t.girarHorario(7);
            t.velocidade = 6;
          }
        }
      }
    }
    for(Tanque t : tanques) autoColisao(t);
  }

  public void autoColisao(Tanque tanque) {
    for(Tanque t : tanques){
      /*verifica a distancia para checar colis�o entre os  tanques*/
      if(tanque.getId() != t.getId()){
        double dist = Math.sqrt(Math.pow(tanque.x - t.x, 2) + Math.pow(tanque.y - t.y, 2));
        if(dist <= 30){
          /*Colis�o entre tanques*/
          if(t.velocidade > 0){
            t.velocidade *= -1;
            t.girarAntiHorario(7);
          }
          else{
            t.velocidade = 2;
            t.girarHorario(7);
          }
        }
        if(dist < 80 &&  tanque.estaAtivo){
          /*tanque inimigo tenta fugir*/
          t.setTempo(System.currentTimeMillis());
          if(agora%2 == 0) t.girarAntiHorario(5);
          else t.girarHorario(5);
          t.velocidade = 6;
        }
      }
    }
  }
  
  public void keyPressed(KeyEvent e) {
    for(Tanque t:tanques) {
      t.setEstaAtivo(false);
      if(t == apontado) {
        t.setEstaAtivo(true);
        switch(e.getKeyCode()){
          case KeyEvent.VK_LEFT: t.girarAntiHorario(3); break;
          case KeyEvent.VK_UP: t.aumentarVelocidade(); break;
          case KeyEvent.VK_DOWN : t.diminuirVelocidade(); break;
          case KeyEvent.VK_RIGHT: t.girarHorario(3); break;
          case KeyEvent.VK_SPACE: {
            atirar(t.getId());
            agora = System.currentTimeMillis();
          }
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

  public void atirar(int id) {
    for(Tanque t:tanques){
      if(t.estaAtivo && !tiro.estaAtivo) {
        tiro.x = t.x + 50;
        tiro.y = t.y + 50;
        tiro.angulo = t.angulo;
        tiro.cor = Color.RED;
        tiro.setId(t.getId());
        tiro.estaAtivo = true;
      }
    }
  }

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
