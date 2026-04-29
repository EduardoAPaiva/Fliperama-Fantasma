package FaseExplorador;

import Modelo.Parede;

public class BlocoFinal extends Parede{
    public BlocoFinal(String sNomeImagePNG, int linha, int coluna, int CELL_SIDE, int MUNDO_TAMANHO){
        super(sNomeImagePNG,linha,coluna, CELL_SIDE, MUNDO_TAMANHO);
        this.bTransponivel=true;
    }
}
