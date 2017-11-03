package bilac.com.main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.io.Serializable;
import java.util.HashSet;

public class Tanque implements Serializable {

  private Color cor;
  private Tiro tiro;
  
  private double x, y;
  private double angulo;
  private double velocidade;
  private boolean estaAtivo;
  
  public Tanque(double x, double y, double angulo, Color cor) {

  }

  public void calculaTempo() {
    if(!this.estaAtivo) {
      if(this.velocidade > 0) this.velocidade = 2;
      else this.velocidade = -2;
    }
  }

  public void aumentarVelocidade() {
    if(this.velocidade < 5) this.velocidade++;
  }
  
  public void diminuirVelocidade() {
    if(this.velocidade > -5) this.velocidade--;
  }
    
  public void girar(double angulo) {
    this.angulo += angulo;
    if(this.angulo >= 360) this.angulo = this.angulo - 360;
  }
  
  public void mover() {
    this.x += Math.sin(Math.toRadians(this.angulo)) * this.velocidade;
    this.y -= Math.cos(Math.toRadians(this.angulo)) * this.velocidade;

    /* TODO: A forma de manter o tanque dentro da tela causava alguns bugs.
     * Então eu simplifiquei essa operação, mas ela precisa ser melhorada depois. */
    if (this.x <= 30 || this.y <= 30 || this.y >= 450 || this.x >= 610)
      this.velocidade *= -1;
  }
  
  public void setEstaAtivo(boolean estaAtivo) {
    this.estaAtivo = estaAtivo;
  }
  
  public Shape pegarAreaClicavelDoTanque() {
    AffineTransform at = new AffineTransform();
    at.translate(this.x, this.y);
    at.rotate(Math.toRadians(this.angulo));
    Rectangle rect = new Rectangle(-24, -32, 48, 55);
    return at.createTransformedShape(rect);
  }

  public void verificarColisaoComOsTanques(HashSet<Tanque> tanques) {
    for(Tanque tanque : tanques) {
      if(tanque != this) {
        /*verifica a distancia para checar colisão entre os  tanques*/
        double distanciaEntreOTanque = Math.sqrt(Math.pow(tanque.x - this.x, 2) + Math.pow(tanque.y - this.y, 2));
        /*Colisão entre tanques*/
        if(distanciaEntreOTanque <= 30){
          if(this.velocidade > 0) this.velocidade = 2;
          this.velocidade *= -1;
          girar(10);
        }
      }
    }
  }
  
  public void atirar() {
    if(!this.tiro.isEstaAtivo()) {
      this.tiro.setX(this.x);
      this.tiro.setY(this.y);
      this.tiro.setAngulo(this.angulo);
      this.tiro.setEstaAtivo(true);
    }
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
    //Desenhamos o tanque. Primeiro o corpo
    g2d.setColor(cor);
    g2d.fillRect(-10, -12, 20, 24);
    //Agora as esteiras
    for(int i = -12; i <= 8; i += 4){
      g2d.setColor(Color.LIGHT_GRAY);
      g2d.fillRect(-15, i, 5, 4);
      g2d.fillRect(10, i, 5, 4);
      g2d.setColor(Color.BLACK);
      g2d.fillRect(-15, i, 5, 4);
      g2d.fillRect(10, i, 5, 4);
    }
    //O canhão
    g2d.setColor(Color.LIGHT_GRAY);
    g2d.fillRect(-3, -25, 6, 25);
    g2d.setColor(cor);
    g2d.drawRect(-3, -25, 6, 25);
    //Se o tanque estiver ativo
    //Desenhamos uma margem
    if(this.estaAtivo) {
      g2d.setColor(new Color(120,120,120));
      Stroke linha = g2d.getStroke();
      g2d.setStroke(new BasicStroke(1f,BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND,0, new float[]{8},0));
      g2d.drawRect(-24, -32, 48, 55);
      g2d.setStroke(linha);
    }
    //Aplicamos o sistema de coordenadas
    g2d.setTransform(antes);
  }

  public Tiro getTiro() {
    return tiro;
  }

  public void setTiro(Tiro tiro) {
    this.tiro = tiro;
  }

  public Color getCor() {
    return cor;
  }

  public void setCor(Color cor) {
    this.cor = cor;
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

  public double getVelocidade() {
    return velocidade;
  }

  public void setVelocidade(double velocidade) {
    this.velocidade = velocidade;
  }

  public boolean isEstaAtivo() {
    return estaAtivo;
  }
  
}
