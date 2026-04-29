package FaseShotDuck;

import Auxiliar.Consts;
import Modelo.Personagem;
import java.io.Serializable;

public class TrajetoriaParabolaVertical implements Trajetorias, Serializable {

    private final Personagem pComTraj;
    private final int[] parametros; // [p, Xv, Yv]
    private double xAtual; // posição x contínua (com precisão double)
    private double direcaoX; // 1 → direita, -1 → esquerda

    public TrajetoriaParabolaVertical(Personagem pComTraj, boolean descendo) {
        this.pComTraj = pComTraj;
        this.parametros = new int[3];
        int Xini;
        if (descendo) {
            // Equação: 2p(Y - Yv) = (X - Xv)^2
            parametros[0] = -600;  // p < 0 => parábola voltada para baixo
            parametros[1] = (int) (Consts.pixelsLargura*0.52);   // Xv (vértice)
            parametros[2] = (int) (Consts.pixelsAltura*0.46);   // Yv (vértice)
            Xini = -80;
            this.direcaoX = 1; // começa indo para a direita
        } else {
            // Equação: 2p(Y - Yv) = (X - Xv)^2
            parametros[0] = 400;  // p > 0 => parábola voltada para cima
            parametros[1] = (int) (Consts.pixelsLargura*0.78);   // Xv (vértice)
            parametros[2] = (int) (Consts.pixelsAltura*0.37);   // Yv (vértice)
            Xini = Consts.pixelsLargura+70;

            this.direcaoX = -1; // começa indo para a direita
        }
        // Início: lado esquerdo da parábola
        int Yini = calculaY(Xini);

        // Centraliza imagem na posição inicial
        pComTraj.setPosicao(Xini - pComTraj.getImage().getIconWidth() / 2, Yini);
        this.xAtual = Xini;

    }

    // === CÁLCULO EXATO DA PARÁBOLA ===
    private int calculaY(double x) {
        double p = parametros[0];
        double xv = parametros[1];
        double yv = parametros[2];
        return (int) ((Math.pow(x - xv, 2)) / (2.0 * p) + yv);
    }

    // === CÁLCULO DA DERIVADA (TANGENTE) ===
    private double derivadaY(double x) {
        double p = parametros[0];
        double xv = parametros[1];
        return (x - xv) / p;
    }

    @Override
    public void mover(Personagem umP) {
        // derivada da parábola no ponto atual
        double dy_dx = derivadaY(xAtual);

        // vetor tangente (1, dy/dx)
        double dx = 1.0 * direcaoX;
        double dy = dy_dx * dx;

        // normalizar para ter velocidade constante
        double norma = Math.sqrt(dx * dx + dy * dy);
        dx = (dx / norma) * FaseShotDuck.velocidadeAtual;
        dy = (dy / norma) * FaseShotDuck.velocidadeAtual;

        // avança em X contínuo
        xAtual += dx;

        // calcula Y exato pela equação da parábola
        double yNovo = calculaY(xAtual);

        // atualiza velocidade dentro do personagem
        pComTraj.velocidade.setxVelocidade(dx);
        pComTraj.velocidade.setyVelocidade(dy);

        // atualiza posição do personagem
        pComTraj.getPosicao().proximaPosicao((int) dx, (int) (yNovo - pComTraj.getYcentro()));
    }
}
