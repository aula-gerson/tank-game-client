package bilac.com.main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.util.HashSet;

public class Tanque {

  protected Color cor;
  private Tiro tiro;
  protected double x,y;
  protected double angulo;
  protected int anguloc,cont=0;
  protected double velocidade;
  protected boolean estaAtivo;
  protected boolean estaVivo;
  private long tempo;
  
  public Tanque(double x, double y, double a, Color cor, int id) {
    this.tiro = new Tiro(this, -10, -10, 0);
    this.x = x; this.y = y; this.angulo = a;
    this.cor = cor; velocidade = 1;
    this.estaAtivo = false;
    this.estaVivo = true;
  }

  public void calculaTempo() {
    if(!estaAtivo && System.currentTimeMillis() - tempo > 5000) {
      if(velocidade > 0) velocidade = 2;
      else velocidade = -2;
    }
  }

  public long getTempo() {
    return tempo;
  }

  public void setTempo(long agora) {
    this.tempo = agora; 
  }

  public void aumentarVelocidade() {
    if(velocidade < 5 && estaVivo) velocidade++;
  }

  public void diminuirVelocidade() {
    if(velocidade > 0 && estaVivo) velocidade--;
  }

  public void girarHorario(int a) {
    angulo += a;
    if(angulo >= 360 && estaVivo) angulo = angulo - 360;
  }

  public void girarAntiHorario(int a) {
    angulo -= a;
    if(angulo <= 0 && estaVivo) angulo = 360-a;
  }
  
  public void mover() {
    x = x + Math.sin(Math.toRadians(angulo)) * velocidade;
    y = y - Math.cos(Math.toRadians(angulo)) * velocidade;
    if (x<=30 ) {
      if(angulo >= 270 && angulo < 360) angulo = 360 - angulo;
      if(angulo > 180 && angulo <= 270) angulo = 360 - angulo;
      if(velocidade < 0){
        velocidade *= -1;
        girarHorario(5);
      }      
    }
    if (y<=30) {
      if(angulo > 270 && angulo <= 360) angulo = 360 - angulo + 180;
      if(angulo >= 0 && angulo < 90) angulo = 360 - angulo - 180;
      if(velocidade < 0) velocidade *= -1;
    }
    if (y>=450) {
      if(angulo > 90 && angulo < 180) angulo = 360 - angulo - 180;
      if(angulo >= 180 && angulo < 270) angulo = 360 - angulo + 180;
      if(velocidade < 0){
        velocidade *= -1;
        girarAntiHorario(5);
      }
    }
    if (x>=610) {
      if(angulo > 0 && angulo <= 90) angulo = 360 - angulo;
      if(angulo >= 90 && angulo < 180) angulo = 360 - angulo;
      if(velocidade < 0) velocidade *= -1;
    }
  }
  
  public void setEstaAtivo(boolean estaAtivo) {
    this.estaAtivo = estaAtivo;
  }

  public void draw(Graphics2D g2d) {
    if(estaVivo) {
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
      if(estaAtivo) {
        g2d.setColor(new Color(120,120,120));
        Stroke linha = g2d.getStroke();
        g2d.setStroke(new BasicStroke(1f,BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND,0, new float[]{8},0));
        g2d.drawRect(-24, -32, 48, 55);
        g2d.setStroke(linha);
      }
      //Aplicamos o sistema de coordenadas
      g2d.setTransform(antes);
    }
  }
  
  public Shape getRectEnvolvente() {
    AffineTransform at = new AffineTransform();
    at.translate(x,y);
    at.rotate(Math.toRadians(angulo));
    Rectangle rect = new Rectangle(-24,-32,48,55);
    return at.createTransformedShape(rect);
  }
  
  public void autoColisao(HashSet<Tanque> tanquesInimigos) {
    for(Tanque tanqueInimigo : tanquesInimigos) {
      if(tanqueInimigo != this) {        
        /*verifica a distancia para checar colisão entre os  tanques*/
        double dist = Math.sqrt(Math.pow(tanqueInimigo.x - this.x, 2) + Math.pow(tanqueInimigo.y - this.y, 2));
        if(dist <= 30){
          /*Colisão entre tanques*/
          if(this.velocidade > 0){
            this.velocidade *= -1;
            this.girarAntiHorario(7);
          }
          else{
            this.velocidade = 2;
            this.girarHorario(7);
          }
        }
      }
    }
  }
  
  public void atirar() {
    if(!getTiro().estaAtivo) {
      getTiro().x = this.x;
      getTiro().y = this.y;
      getTiro().angulo = this.angulo;
      getTiro().cor = Color.RED;
      getTiro().estaAtivo = true;
    }
  }

  public Tiro getTiro() {
    return tiro;
  }

  public void setTiro(Tiro tiro) {
    this.tiro = tiro;
  }
  
}
