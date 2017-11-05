package bilac.com.entidades;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;

public class Tiro implements Serializable {
  
  private Tanque tanque;
  
  private double x, y;
  private double angulo;
  private boolean estaAtivo;
  
  public Tiro(Tanque tanque, double x, double y, double angulo) {
    this.tanque = tanque;
    this.x = x;
    this.y = y;
    this.angulo = angulo;
    this.estaAtivo = false;
  }

  public void mover() {
    if(this.estaAtivo){
      this.x += Math.sin(Math.toRadians(this.angulo)) * 20;
      this.y -= Math.cos(Math.toRadians(this.angulo)) * 20;
    }
    if(this.x < -5 || this.x >645 || this.y < -5 || this.y > 485) this.estaAtivo = false;
  }

  public void draw(Graphics2D g2d) {
    //Armazenamos o sistema de coordenadas original.
    AffineTransform antes = g2d.getTransform();
    //Criamos um sistema de coordenadas para o tanque.
    AffineTransform depois = new AffineTransform();
    depois.translate(this.x, this.y);
    depois.rotate(Math.toRadians(this.angulo));
    //Aplicamos o sistema de coordenadas.
    g2d.transform(depois);
    //Desenhamos o missil
    g2d.setColor(Color.RED);
    g2d.fillRect(-3, -3, 4, 4);
    //Aplicamos o sistema de coordenadas
    g2d.setTransform(antes);
  }
  
  public void verificarColisaoComOsTanques(List<Tanque> tanques) {
    if(this.estaAtivo){
      for(Tanque tanque : tanques) {
        double dist = Math.sqrt(Math.pow(this.x - tanque.getX(), 2) + Math.pow(this.y - tanque.getY(), 2));
        if(tanque != this.tanque) {
          if(dist <= 20){
            /*Distancia de acerto*/
            this.x = -10;
            this.y = -10;
            this.setEstaAtivo(false);
            tanque.setEstaVivo(false);
            tanque.setContador(-100);
            tanque.setVelocidade(1);
          }
        }
      }
    }
  }

  public Tanque getTanque() {
    return tanque;
  }

  public void setTanque(Tanque tanque) {
    this.tanque = tanque;
  }

  public double getX() {
    return x;
  }

  public void setX(double x) {
    this.x = x;
  }

  public double getY() {
    return y;
  }

  public void setY(double y) {
    this.y = y;
  }

  public double getAngulo() {
    return angulo;
  }

  public void setAngulo(double angulo) {
    this.angulo = angulo;
  }

  public boolean isEstaAtivo() {
    return estaAtivo;
  }

  public void setEstaAtivo(boolean estaAtivo) {
    this.estaAtivo = estaAtivo;
  }
  
}
