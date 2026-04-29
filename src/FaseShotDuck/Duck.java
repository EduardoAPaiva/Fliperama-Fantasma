package FaseShotDuck;

import Auxiliar.Consts;
import Modelo.Personagem;
import java.awt.Image;

public class Duck extends Personagem {

    public transient Image imgNormal;
    public transient Image imgMorto;
    public double gravidade = 0.3;
    public double velY = 0;

    public Duck(Image imgNormal, Image imgMorto) {
        super(imgNormal);
        this.imgNormal = imgNormal;
        this.imgMorto = imgMorto;
    }

    @Override
    public void mover(FaseShotDuck f) {
        if (!getbMorto()) {
            // Movimento normal (parabólico ou horizontal)
            getTrajetoria().mover(this);
        } else {
            // Quando morto: aplicar gravidade
            velY += gravidade;
            getPosicao().proximaPosicao(
                this.velocidade.getxVelocidadeint(),
                (int) velY);

            this.setImg(imgMorto);

            // Remove quando sair da tela
        }
        if (saiuDaTela()){
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

    public void setImageNormal(Image i){
        imgNormal = i;

    }

    public void setImageMorto(Image i){
        imgMorto = i;
    }

    public void atualizarImagem(){
        if(getbMorto())
            setImg(imgMorto);
        else
            setImg(imgNormal);

    }
}
