package FaseExplorador;

import Modelo.Parede;

public class diamante extends Parede{
    public diamante(String sNomeImagePNG, int linha, int coluna, int CELL_SIDE, int MUNDO_TAMANHO){
        super(sNomeImagePNG,linha,coluna, CELL_SIDE, MUNDO_TAMANHO);
        this.bTransponivel=true;
        this.jacaiu=false;
    }
}
