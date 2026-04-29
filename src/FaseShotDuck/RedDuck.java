package FaseShotDuck;

import Auxiliar.Consts;
import java.awt.Image;

public class RedDuck extends Duck {
    private boolean tirouVida = false; // para não tirar mais de 1 vida
    private boolean abatido = false;   // para controle de queda

    public RedDuck(Image imgVivo, Image imgMorto) {
        super(imgVivo, imgMorto);
    }

    @Override
    public void setbMorto(boolean bMorto) {
        if (bMorto && !abatido) {
            abatido = true;
            this.setImg(this.imgMorto);
            this.velY = 0; // começa a cair
        }
        this.bMortal = bMorto;
    }

    @Override
    public void mover(FaseShotDuck f) {
        if (!abatido) {
            // Movimento normal (horizontal ou parabólico)
            if (getTrajetoria() != null) {
                getTrajetoria().mover(this);
            }
        } else {
            // Queda suave igual ao Duck morto
            velY += 0.3;
            getPosicao().proximaPosicao(this.velocidade.getxVelocidadeint(), (int) velY);
        }

        // Se saiu da tela sem ser abatido, reduz vida e inicia queda
        if (!abatido && saiuDaTela() && !tirouVida) {
            f.reduzirVida();
            tirouVida = true;
            this.setImg(this.imgMorto);
            velY = 0; // começa a cair
        }

        // Remove quando sair da tela completamente
        if (saiuDaTela()) {
            this.setRemovivel(true);
        }
    }

    private boolean saiuDaTela() {
        int x = this.getPosicao().getColuna();
        int y = this.getPosicao().getLinha();
        int largura = this.getImg().getWidth(null);
        int altura = this.getImg().getHeight(null);

        if(x + largura < 0 && velocidade.getxVelocidadedouble()<0)
            return true;
        if(x > Consts.pixelsLargura && velocidade.getxVelocidadedouble()>0)
            return true;
        if(y + altura < 0 && velocidade.getyVelocidadedouble()<0)
            return true;
        if(y > Consts.pixelsAltura && velocidade.getyVelocidadedouble()>0)
            return true;
        return false;
    }
}
