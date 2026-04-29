package FaseExplorador;

import Modelo.Parede;

public class pedra extends Parede{
    public pedra(String sNomeImagePNG, int linha, int coluna, int CELL_SIDE, int MUNDO_TAMANHO){
        super(sNomeImagePNG,linha,coluna, CELL_SIDE, MUNDO_TAMANHO);
        this.bTransponivel=false;
        this.jacaiu=false;
    }
}