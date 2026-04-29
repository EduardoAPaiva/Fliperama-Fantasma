package Modelo;

public class Parede extends Personagem{
    protected boolean jacaiu;
    public Parede(String sNomeImagePNG, int linha, int coluna, int CELL_SIDE, int MUNDO_TAMANHO) {
        super(sNomeImagePNG, linha, coluna, CELL_SIDE, MUNDO_TAMANHO);
        
        this.bTransponivel = false; //define que nao pode ser atravessado
    }
    public boolean getjacaiu(){
        return this.jacaiu;
    }
    public void setjacaiu(boolean jacaiu){
        this.jacaiu=jacaiu;
    }
}