package bilac.com.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JFrame;

import bilac.com.conexao.Conexao;
import bilac.com.listener.EscutaTeclado;

@SuppressWarnings("serial")
public class Arena extends JComponent {
  
  private static final int LARGURA_ARENA = 640;
  private static final int ALTURA_ARENA = 480;

  private List<Tanque> tanques;
  private Tanque tanqueSelecionado;
  
  private PrintWriter writer;
  
  public Arena(){
    this.tanques = new ArrayList<Tanque>();
    addKeyListener(new EscutaTeclado(this));
    setFocusable(true);
    new Conexao(this).start();
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
      tanque.draw(g2d);
      tanque.getTiro().draw(g2d);
    }
  }

  public static void main(String args[]) {
    Arena arena = new Arena();
    JFrame janela = new JFrame("JTank");
    janela.getContentPane().add(arena);
    janela.setResizable(false);
    janela.pack();
    janela.setVisible(true);
    janela.setDefaultCloseOperation(3);
  }

  public List<Tanque> getTanques() {
    return tanques;
  }

  public void setTanques(List<Tanque> tanques) {
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

  public PrintWriter getWriter() {
    return writer;
  }

  public void setWriter(PrintWriter writer) {
    this.writer = writer;
  }
 
}
