package FaseShotDuck;

import Auxiliar.Consts;
import Modelo.Personagem;
import java.io.Serializable;
import java.util.Random;

public class TrajetoriaSinuosaHorizontal implements Trajetorias, Serializable {

    private final Personagem pComTraj;
    private double xAtual;       // posição horizontal contínua
    private double yAtual;       // posição vertical contínua
    private final boolean direita;
    private final double frequencia; // frequência da onda
    private final double amplitude;  // amplitude da onda
    private final double yBase;      // base do Y inicial (aleatório)

    public TrajetoriaSinuosaHorizontal(Personagem pComTraj, boolean direita) {
        this.pComTraj = pComTraj;
        this.direita = direita;

        this.frequencia = 0.005; // ajusta quantas curvas cabem na tela
        this.amplitude = 150;    // altura da onda em pixels

        // Posição horizontal inicial fora da tela
        xAtual = direita ? -70 : Consts.pixelsLargura+50;

        // Posição vertical inicial randomizada entre 200 e 600
        Random r = new Random();
        yBase = amplitude + r.nextInt(Consts.pixelsAltura - (int) amplitude - 500); // 200..600
        yAtual = yBase;

        // centraliza personagem
        pComTraj.setPosicao((int) (xAtual - pComTraj.getImage().getIconWidth() / 2),
                (int) (yAtual - pComTraj.getImage().getIconHeight() / 2));
    }

    @Override
    public void mover(Personagem umP) {
        // velocidade horizontal
        double vx = (direita ? 1 : -1) * FaseShotDuck.velocidadeAtual;

        // nova posição vertical = base + seno
        double yNovo = yBase + Math.sin(xAtual * frequencia) * amplitude;

        double vy = yNovo - yAtual;

        // normaliza velocidade para módulo constante
        double norma = Math.sqrt(vx * vx + vy * vy);
        vx = (vx / norma) * FaseShotDuck.velocidadeAtual;
        vy = (vy / norma) * FaseShotDuck.velocidadeAtual;

        // atualiza posições
        xAtual += vx;
        yAtual = yNovo;

        // atualiza vetor de velocidade do personagem
        pComTraj.velocidade.setxVelocidade(vx);
        pComTraj.velocidade.setyVelocidade(vy);

        // atualiza posição real considerando o centro da imagem
        pComTraj.getPosicao().proximaPosicao(
                (int) (xAtual - pComTraj.getXcentro()),
                (int) (yAtual - pComTraj.getYcentro())
        );
    }

}
