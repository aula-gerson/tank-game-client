package bilac.com.entidades;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;

public class Tiro implements Serializable {
  
  private Tanque tanque;
  
  private String x, y;
  private String angulo;
  private String estaAtivo;
  
  public Tiro(Tanque tanque, double x, double y, double angulo) {
    this.tanque = tanque;
    setX(x);
    setY(y);
    setAngulo(angulo);
    setEstaAtivo(false);
  }

  public void mover() {
    if(isEstaAtivo()){
      setX(getX() + Math.sin(Math.toRadians(getAngulo())) * 20);
      setY(getY() - Math.cos(Math.toRadians(getAngulo())) * 20);
    }
    if(getX() < -5 || getX() >645 || getY() < -5 || getY() > 485) setEstaAtivo(false);
  }

  public void draw(Graphics2D g2d) {
    //Armazenamos o sistema de coordenadas original.
    AffineTransform antes = g2d.getTransform();
    //Criamos um sistema de coordenadas para o tanque.
    AffineTransform depois = new AffineTransform();
    depois.translate(getX(), getY());
    depois.rotate(Math.toRadians(getAngulo()));
    //Aplicamos o sistema de coordenadas.
    g2d.transform(depois);
    //Desenhamos o missil
    g2d.setColor(Color.RED);
    g2d.fillRect(-3, -3, 4, 4);
    //Aplicamos o sistema de coordenadas
    g2d.setTransform(antes);
  }
  
  public void verificarColisaoComOsTanques(List<Tanque> tanques) {
    if(isEstaAtivo()){
      for(Tanque tanque : tanques) {
        double dist = Math.sqrt(Math.pow(getX() - tanque.getX(), 2) + Math.pow(getY() - tanque.getY(), 2));
        if(tanque != this.tanque) {
          if(dist <= 20){
            /*Distancia de acerto*/
            setX(-10);
            setY(-10);
            setEstaAtivo(false);
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
    return Double.parseDouble(this.x);
  }
  
  public String getXString() {
    return this.x;
  }

  public void setX(double x) {
    this.x = String.valueOf(x);
  }
  
  public void setX(String x) {
    this.x = x;
  }

  public double getY() {
    return Double.parseDouble(this.y);
  }
  
  public String getYString() {
    return this.y;
  }

  public void setY(double y) {
    this.y = String.valueOf(y);
  }
  
  public void setY(String y) {
    this.y = y;
  }

  public double getAngulo() {
    return Double.parseDouble(this.angulo);
  }
  
  public String getAnguloString() {
    return this.angulo;
  }

  public void setAngulo(double angulo) {
    this.angulo = String.valueOf(angulo);
  }
  
  public void setAngulo(String angulo) {
    this.angulo = String.valueOf(angulo);
  }

  public boolean isEstaAtivo() {
    return Boolean.parseBoolean(this.estaAtivo);
  }
  
  public String isEstaAtivoString() {
    return this.estaAtivo;
  }

  public void setEstaAtivo(boolean estaAtivo) {
    this.estaAtivo = String.valueOf(estaAtivo);
  }
  
  public void setEstaAtivo(String estaAtivo) {
    this.estaAtivo = estaAtivo;
  }
  
}
