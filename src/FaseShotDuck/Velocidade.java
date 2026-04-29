package FaseShotDuck;

import java.io.Serializable;

public class Velocidade implements Serializable{
    private double xVelocidade;
    private double yVelocidade;

    public Velocidade() {
        xVelocidade = 0;
        yVelocidade = 0;
    }
    
    public int getxVelocidadeint() {
        return (int) Math.round(xVelocidade);
    }

    public int getyVelocidadeint() {
        return (int) Math.round(yVelocidade);
    }

    public double getxVelocidadedouble() {
        return xVelocidade;
    }

    public double getyVelocidadedouble() {
        return yVelocidade;
    }
    
    

    public void setxVelocidade(double xVelocidade) {
        this.xVelocidade = xVelocidade;
    }

    public void setyVelocidade(double yVelocidade) {
        this.yVelocidade = yVelocidade;
    }
    
    public double anguloatual(){
        return Math.atan2(yVelocidade, xVelocidade);
    }
}
