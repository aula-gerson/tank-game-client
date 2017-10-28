package bilac.com.main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.HashSet;

public class Tiro {
  
  protected Tanque atirador;
  protected Color cor;
  protected double x, y;
  protected double angulo;
  protected int anguloc, cont = 0;
  protected double velocidade;
  protected boolean estaAtivo;
  
  public Tiro(Tanque atirador, double x, double y, double a) {
    this.atirador = atirador;
    this.x = x;
    this.y = y;
    this.angulo = a;
  }

  public void mover() {
    if(estaAtivo){
      x = x + Math.sin(Math.toRadians(angulo)) * 10;
      y = y - Math.cos(Math.toRadians(angulo)) * 10;
    }
    if(x < -5 || x >645 || y<-5 || y> 485) estaAtivo = false;
  }

  public void draw(Graphics2D g2d) {
    //Armazenamos o sistema de coordenadas original.
    AffineTransform antes = g2d.getTransform();
    //Criamos um sistema de coordenadas para o tanque.
    AffineTransform depois = new AffineTransform();
    depois.translate(x, y);
    depois.rotate(Math.toRadians(angulo));
    //Aplicamos o sistema de coordenadas.
    g2d.transform(depois);
    //Desenhamos o missil
    g2d.setColor(cor);
    g2d.fillRect(-3, -3, 4, 4);    
    //Aplicamos o sistema de coordenadas
    g2d.setTransform(antes);
  }
  
  public void colisao(HashSet<Tanque> tanquesInimigos) {
    if(estaAtivo){
      for(Tanque tanqueInimigo : tanquesInimigos) {
        double dist = Math.sqrt(Math.pow(this.x - tanqueInimigo.x, 2) + Math.pow(this.y - tanqueInimigo.y, 2));
        if(tanqueInimigo != this.atirador) {
          if(dist <= 20){
            /*Distancia de acerto*/
            this.x = -10;
            this.y = -10;
            estaAtivo = false;
            tanqueInimigo.estaVivo = false;
          }
        }
      }
    }
  }
  
}
