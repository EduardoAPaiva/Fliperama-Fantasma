package FaseShotDuck;

import Auxiliar.Consts;
import Modelo.Personagem;
import java.io.Serializable;

public class TrajetoriaParabolaHorizontal implements Trajetorias, Serializable {

    private final Personagem pComTraj;
    private final int[] parametros; // [p, Xv, Yv]
    private double yAtual; // posição Y contínua (com precisão double)
    private double direcaoY; // 1 → descendo, -1 → subindo

    public TrajetoriaParabolaHorizontal(Personagem pComTraj, boolean paraDireita) {
        this.pComTraj = pComTraj;
        this.parametros = new int[3];
        int Yini;

        if (paraDireita) {
            // Equação: 2p(X - Xv) = (Y - Yv)^2  → abre para a direita
            parametros[0] = 300;   // p > 0 → abre para direita
            parametros[1] = (int) (Consts.pixelsLargura*0.31);   // Xv (vértice)
            parametros[2] = (int) (Consts.pixelsAltura*0.64);   // Yv (vértice)

            // começa acima do vértice (lado superior)
            Yini = -70;
            this.direcaoY = 1; // vai descendo
        } else {
            // Equação: 2p(X - Xv) = (Y - Yv)^2  → abre para a esquerda
            parametros[0] = -200;  // p < 0 → abre para esquerda
            parametros[1] = (int) (Consts.pixelsLargura*0.78);  // Xv (vértice)
            parametros[2] = (int) (Consts.pixelsAltura*0.56);   // Yv (vértice)

            // começa abaixo do vértice (lado inferior)
            Yini = Consts.pixelsAltura+70;
            this.direcaoY = -1; // vai subindo
        }

        // calcula X inicial pela equação inversa
        int Xini = calculaX(Yini);

        // Centraliza imagem na posição inicial
        pComTraj.setPosicao(Xini, Yini - pComTraj.getImage().getIconHeight() / 2);
        this.yAtual = Yini;
    }

    // === CÁLCULO EXATO DA PARÁBOLA ===
    private int calculaX(double y) {
        double p = parametros[0];
        double xv = parametros[1];
        double yv = parametros[2];
        return (int) ((Math.pow(y - yv, 2)) / (2.0 * p) + xv);
    }

    // === CÁLCULO DA DERIVADA (TANGENTE) ===
    private double derivadaX(double y) {
        double p = parametros[0];
        double yv = parametros[2];
        return (y - yv) / p; // dX/dY
    }

    public void mover(Personagem umP) {
        // derivada da parábola no ponto atual
        double dx_dy = derivadaX(yAtual);

        // vetor tangente (dx/dy, 1)
        double dy = 1.0 * direcaoY;
        double dx = dx_dy * dy;

        // normalizar vetor para manter velocidade constante
        double norma = Math.sqrt(dx * dx + dy * dy);
        dx = (dx / norma) * FaseShotDuck.velocidadeAtual;
        dy = (dy / norma) * FaseShotDuck.velocidadeAtual;
        // avança em Y contínuo
        yAtual += dy;

        // calcula X exato pela equação da parábola
        double xNovo = calculaX(yAtual);

        // atualiza velocidade no personagem
        pComTraj.velocidade.setxVelocidade(dx);
        pComTraj.velocidade.setyVelocidade(dy);

        // atualiza posição
        pComTraj.getPosicao().proximaPosicao((int) (xNovo - pComTraj.getXcentro()), (int) dy);
    }
}
