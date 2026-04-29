package FaseCobrinha;

import Auxiliar.Posicao;
import Modelo.Personagem;
import java.util.ArrayList;
import java.util.Random;

public class Comida extends Personagem{

    public Comida(String sNomeImagePNG, int linha, int coluna, int CELL_SIDE, int MUNDO_TAMANHO){
        super(sNomeImagePNG, linha, coluna, CELL_SIDE, MUNDO_TAMANHO);
    }

    public void definePosicao(Cobra cobra, int MUNDO_TAMANHO){
        Random aleatorio = new Random();

        Posicao tentativa = new Posicao(0,0, MUNDO_TAMANHO);

        tentativa.setLinha(aleatorio.nextInt(MUNDO_TAMANHO));
        tentativa.setColuna(aleatorio.nextInt(MUNDO_TAMANHO));

        while(comidaValida(tentativa, cobra) == false){
            tentativa.setLinha(aleatorio.nextInt(MUNDO_TAMANHO));
            tentativa.setColuna(aleatorio.nextInt(MUNDO_TAMANHO));
        }

        super.setPosicao(tentativa.getLinha(), tentativa.getColuna(), MUNDO_TAMANHO);
    }

    public boolean comidaValida(Posicao p, Cobra c){
        if(c.getCabeca().getPosicao().getColuna() == p.getColuna() && c.getCabeca().getPosicao().getLinha() == p.getLinha()){
            return false;
        }

        if(c.getCorpo() != null){
            ArrayList<Personagem> copiaCorpo = c.getCorpo();
            for(int i = 0; i<copiaCorpo.size(); i++){
                if(copiaCorpo.get(i).getPosicao().getColuna() == p.getColuna() && copiaCorpo.get(i).getPosicao().getLinha() == p.getLinha())
                    return false;
            }
        }

        return true;
    }

}