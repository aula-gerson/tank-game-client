package bilac.com.entidades;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;

public class Tanque implements Serializable {

  private Tiro tiro;
  private Color cor;
  
  private String x, y;
  private String angulo;
  private String velocidade;
  private String estaAtivo;
  private String estaVivo;
  private String contador;
  
  public Tanque(double x, double y, double angulo, Color cor) {
    setX(x);
    setY(y);
    setAngulo(angulo);
    setCor(cor);
    setVelocidade(5);
    setEstaAtivo(false);
    setEstaVivo(true);
    setContador(0);
    this.tiro = new Tiro(this, 10, 10, 10);
  }
  
  public void calculaTempo() {
    if(!isEstaAtivo()) {
      if(getVelocidade() > 0) setVelocidade(2);
      else setVelocidade(-2);
    }
  }

  public void aumentarVelocidade() {
    if(!isEstaVivo()) return;
    if(getVelocidade() < 5) setVelocidade(getVelocidade()+1);
  }
  
  public void diminuirVelocidade() {
    if(!isEstaVivo()) return;
    if(getVelocidade() > 0) setVelocidade(getVelocidade()-1);;
  }
    
  public void girar(double angulo) {
    if(!isEstaVivo()) return;
    setAngulo(getAngulo() + angulo);
    if(getAngulo() >= 360) setAngulo(getAngulo() - 360);
    if(getAngulo() < 0) setAngulo(getAngulo() + 360);
  }
  
  public void mover() {
    if(!isEstaVivo()) return;
    setX(getX() + Math.sin(Math.toRadians(getAngulo())) * getVelocidade());
    setY(getY() - Math.cos(Math.toRadians(getAngulo())) * getVelocidade());

    /* TODO: A forma de manter o tanque dentro da tela causava alguns bugs.
     * Então eu simplifiquei essa operação, mas ela precisa ser melhorada depois. */
    if (getX() <= 30 || getY() <= 30 || getY() >= 450 || getX() >= 610)
      setVelocidade(getVelocidade()*-1);
  }
  
  public void verificarColisaoComOsTanques(List<Tanque> tanques) {
    for(Tanque tanque : tanques) {
      if(tanque != this) {
        /*verifica a distancia para checar colisão entre os  tanques*/
        double distanciaEntreOTanque = Math.sqrt(Math.pow(tanque.getX() - getX(), 2) + Math.pow(tanque.getY() - getY(), 2));
        /*Colisão entre tanques*/
        if(distanciaEntreOTanque <= 30){
          if(getVelocidade() > 0) setVelocidade(2);
          setVelocidade(getVelocidade()*-1);
          girar(10);
        }
      }
    }
  }
  
  public void atirar() {
    if(!this.tiro.isEstaAtivo()) {
      this.tiro.setX(getX());
      this.tiro.setY(getY());
      this.tiro.setAngulo(getAngulo());
      this.tiro.setEstaAtivo(true);
    }
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
    //Desenhamos o tanque. Primeiro o corpo
    if(isEstaVivo()) g2d.setColor(cor);
    else g2d.setColor(Color.BLACK);
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
    if(isEstaAtivo()) {
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
    this.angulo = angulo;
  }

  public double getVelocidade() {
    return Double.parseDouble(this.velocidade);
  }

  public String getVelocidadeString() {
    return this.velocidade;
  }  
  
  public void setVelocidade(double velocidade) {
    this.velocidade = String.valueOf(velocidade);
  }
  
  public void setVelocidade(String velocidade) {
    this.velocidade = velocidade;
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

  public boolean isEstaVivo() {
    return Boolean.parseBoolean(this.estaVivo);
  }
  
  public String isEstaVivoString() {
    return this.estaVivo;
  }

  public void setEstaVivo(boolean estaVivo) {
    this.estaVivo = String.valueOf(estaVivo);
  }
  
  public void setEstaVivo(String estaVivo) {
    this.estaVivo = estaVivo;
  }

  public int getContador() {
    return Integer.parseInt(this.contador);
  }
  
  public String getContadorString() {
    return this.contador;
  }

  public void setContador(int contador) {
    this.contador = String.valueOf(contador);
  }
  
  public void setContador(String contador) {
    this.contador = contador;
  }
  
}
