package Modelo;

import Auxiliar.Desenho;
import javax.swing.ImageIcon;

public class Hero extends Personagem{
    public Hero(String sNomeImagePNG, int linha, int coluna, int CELL_SIDE, int MUNDO_TAMANHO) {
        super(sNomeImagePNG,linha, coluna, CELL_SIDE, MUNDO_TAMANHO);
    }

    public void voltaAUltimaPosicao(int MUNDO_TAMANHO){
        this.pPosicao.volta(MUNDO_TAMANHO);
    }
    
    
    public boolean setPosicao(int linha, int coluna, int MUNDO_TAMANHO){
        if(this.pPosicao.setPosicao(linha, coluna, MUNDO_TAMANHO)){
            if (!Desenho.acessoATelaDoJogo().ehPosicaoValida(this.getPosicao())) {
                this.voltaAUltimaPosicao(MUNDO_TAMANHO);
            }
            return true;
        }
        return false;       
    }

    /*TO-DO: este metodo pode ser interessante a todos os personagens que se movem*/
    private boolean validaPosicao(int MUNDO_TAMANHO){
        if (!Desenho.acessoATelaDoJogo().ehPosicaoValida(this.getPosicao())) {
            this.voltaAUltimaPosicao(MUNDO_TAMANHO);
            return false;
        }
        return true;       
    }
    
    public boolean moveUp(int MUNDO_TAMANHO) {
        if(super.moveUp(MUNDO_TAMANHO))
            return validaPosicao(MUNDO_TAMANHO);
        return false;
    }

    public boolean moveDown(int MUNDO_TAMANHO) {
        if(super.moveDown(MUNDO_TAMANHO))
            return validaPosicao(MUNDO_TAMANHO);
        return false;
    }

    public boolean moveRight(int MUNDO_TAMANHO) {
        if(super.moveRight(MUNDO_TAMANHO))
            return validaPosicao(MUNDO_TAMANHO);
        return false;
    }

    public boolean moveLeft(int MUNDO_TAMANHO) {
        if(super.moveLeft(MUNDO_TAMANHO))
            return validaPosicao(MUNDO_TAMANHO);
        return false;
    }    
    
    public void setImage(ImageIcon iImage){
        super.setImage(iImage);
    }

    public ImageIcon getImagem(){
        return iImage;
    }

}