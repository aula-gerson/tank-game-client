package bilac.com.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.HashSet;

import javax.swing.JComponent;
import javax.swing.JFrame;

import bilac.com.listener.EscutaClick;
import bilac.com.listener.EscutaTeclado;
import bilac.com.listener.Render;

@SuppressWarnings("serial")
public class Arena extends JComponent {
  
  private static final int LARGURA_ARENA = 640;
  private static final int ALTURA_ARENA = 480;

  private HashSet<Tanque> tanques;
  private Tanque tanqueSelecionado;
  
  public Arena(){
    this.tanques = new HashSet<Tanque>();
    addMouseListener(new EscutaClick(this));
    addKeyListener(new EscutaTeclado(this));
    new Render(this).start();
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

  public HashSet<Tanque> getTanques() {
    return tanques;
  }

  public void setTanques(HashSet<Tanque> tanques) {
    this.tanques = tanques;
  }

  public Tanque getTanqueSelecionado() {
    return tanqueSelecionado;
  }

  public void setTanqueSelecionado(Tanque tanqueSelecionado) {
    this.tanqueSelecionado = tanqueSelecionado;
  }

  public static int getLarguraArena() {
    return LARGURA_ARENA;
  }

  public static int getAlturaArena() {
    return ALTURA_ARENA;
  }
 
}
