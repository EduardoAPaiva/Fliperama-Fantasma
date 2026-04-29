package FaseShotDuck;

import java.io.Serializable;
import java.util.Random;

import Modelo.Personagem;

public class TrajetoriaDiagonal implements Trajetorias, Serializable{
    Personagem pComTraj;
    Velocidade v = new Velocidade();
    public TrajetoriaDiagonal(Personagem umP){

        pComTraj = umP;
        double vx = new Random().nextDouble()*2 - 1;
        double vy = new Random().nextDouble()*2 - 1;
        v.setxVelocidade(vx*10);
        v.setyVelocidade(vy*10);

    }
    public void mover(Personagem umP) {
        double dx = v.getxVelocidadedouble();
        double dy = v.getyVelocidadedouble();

        double norma = Math.sqrt(dx * dx + dy * dy);

        dx = (dx / norma) * FaseShotDuck.velocidadeAtual;
        dy = (dy / norma) * FaseShotDuck.velocidadeAtual;

        pComTraj.velocidade.setxVelocidade(dx);
        pComTraj.velocidade.setyVelocidade(dy);

        // atualiza posição do personagem
        pComTraj.getPosicao().proximaPosicao((int) dx, (int) dy);
    }
    
}
