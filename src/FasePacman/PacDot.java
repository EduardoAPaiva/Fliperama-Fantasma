package FasePacman;

import Modelo.Personagem;

public class PacDot extends Personagem{
    
    public PacDot(String sNomeImagePNG, int linha, int coluna, int CELL_SIDE, int MUNDO_TAMANHO) {
        super(sNomeImagePNG, linha, coluna, CELL_SIDE, MUNDO_TAMANHO);
        
        this.bTransponivel = true; 
    }
}
