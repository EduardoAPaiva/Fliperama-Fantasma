package FaseCobrinha;

import Auxiliar.Consts;
import Auxiliar.Desenho;
import Modelo.Hero;
import Modelo.Personagem;

import java.io.Serializable;
import java.util.ArrayList;
import javax.swing.ImageIcon;

public class Cobra implements Serializable{
    
    private Hero Cabeca;
    private transient ImageIcon imgCima;
    private transient ImageIcon imgBaixo;
    private transient ImageIcon imgEsq;
    private transient ImageIcon imgDir;
    private ArrayList<Personagem> Corpo = new ArrayList<>();

    public Cobra(String sNomeImagePNG, String imgCima, String imgBaixo, String imgDir, int linha, int coluna, int CELL_SIDE, int MUNDO_TAMANHO){
        Cabeca = new Hero(sNomeImagePNG, linha, coluna, CELL_SIDE, MUNDO_TAMANHO);

        Cabeca.setDirecaoAtual(Consts.BAIXO);
        Cabeca.setDirecaoAnterior(Consts.BAIXO);
        Cabeca.setPosicaoAnterior(linha-1, coluna);

        this.imgCima = Desenho.retornaImagem(imgCima, CELL_SIDE);
        this.imgBaixo = Desenho.retornaImagem(imgBaixo, CELL_SIDE);
        this.imgEsq = Desenho.retornaImagemEspelhadaHorizontal(imgDir, CELL_SIDE);
        this.imgDir = Desenho.retornaImagem(imgDir, CELL_SIDE);

        Cabeca.setImage(this.imgBaixo);
    }
 
    public Hero getCabeca(){
        return Cabeca;
    }

    public ArrayList<Personagem> getCorpo(){
        return Corpo;
    }

    public boolean moveUp(int MUNDO_TAMANHO){
        Cabeca.setImage(imgCima);
        return Cabeca.moveUp(MUNDO_TAMANHO);
    }

    public boolean moveDown(int MUNDO_TAMANHO){
        Cabeca.setImage(imgBaixo);
        return Cabeca.moveDown(MUNDO_TAMANHO);
    }

    public boolean moveLeft(int MUNDO_TAMANHO){
        Cabeca.setImage(imgEsq);
        return Cabeca.moveLeft(MUNDO_TAMANHO);
    }

    public boolean moveRight(int MUNDO_TAMANHO){
        Cabeca.setImage(imgDir);
        return Cabeca.moveRight(MUNDO_TAMANHO);
    }

    public int getDirecao(){
        return Cabeca.getDirecao();
    }

    public int getDirecaoAtual(){
        return Cabeca.getDirecaoAtual();
    }

    public void setDirecao(int direcao){
        Cabeca.setDirecao(direcao);
    }

    public void atualizaDirecao(){
        Cabeca.setDirecaoAtual(Cabeca.getDirecaoAtual());
    }

    public void setDirecaoAtual(){
        Cabeca.setDirecaoAtual(Cabeca.getDirecao());
    }

    public void AdicionaCorpo(String FasePath, int CELL_SIDE, int MUNDO_TAMANHO){
        if(Corpo.isEmpty()){
            Hero aux = new Hero(FasePath + "cauda.png", Cabeca.obtemPosicaoAntiga().getLinha(), Cabeca.obtemPosicaoAntiga().getColuna(), CELL_SIDE, MUNDO_TAMANHO);
            aux.setDirecaoAtual(Cabeca.getDirecaoAnterior());
            Corpo.add(aux);

        }

        else{
            Hero ultimoCorpo = (Hero)Corpo.get(Corpo.size() - 1);
            Hero aux = new Hero(FasePath + "cauda.png", ultimoCorpo.getPosicao().getLinhaAnterior(), ultimoCorpo.getPosicao().getColunaAnterior(), CELL_SIDE, MUNDO_TAMANHO);
            aux.setDirecaoAtual(ultimoCorpo.getDirecaoAnterior());
            Corpo.add(aux);
        }
    }

    public void atualizaCorpo(){
        if(Corpo.isEmpty() == false){
            Corpo.get(0).setPosicao(Cabeca.getPosicao().getLinhaAnterior(), Cabeca.getPosicao().getColunaAnterior(), Consts.JANELA);
            Corpo.get(0).setDirecaoAtual(Cabeca.getDirecaoAnterior());
        }

        for(int i = 1; i<Corpo.size(); i++){
            Corpo.get(i).setPosicao(Corpo.get(i-1).getPosicao().getLinhaAnterior(), Corpo.get(i-1).getPosicao().getColunaAnterior(), Consts.JANELA);
            Corpo.get(i).setDirecaoAtual(Corpo.get(i-1).getDirecaoAnterior());
        }
    }

    public boolean cabecaBateu(){
        Hero aux;
        for(int i = 0; i<Corpo.size(); i++){
            aux = (Hero)Corpo.get(i);
            if(Cabeca.getPosicao().getLinha() == aux.getPosicao().getLinha() && Cabeca.getPosicao().getColuna() == aux.getPosicao().getColuna())
                return true;
        }

        return false;
    }

    public void retornaCabeca(){
        Cabeca.setPosicao(Cabeca.getPosicao().getLinhaAnterior(), Cabeca.getPosicao().getColunaAnterior(), Consts.JANELA);
    }

    public void avancaCabeca(){
        Cabeca.setPosicaoAnterior(Cabeca.getPosicao().getLinha(), Cabeca.getPosicao().getColuna());
    }

    public void zeraDirecao(){
        Cabeca.setDirecaoAtual(0);;
        for(int i = 0; i<Corpo.size(); i++){
            Corpo.get(i).setDirecaoAtual(0);
        }
    }

    public void recuperarTransient(String sNomeImagePNG, String imgCima, String imgBaixo, String imgDir, String strImgCorpo, int CELL_SIDE){
        this.imgCima = Desenho.retornaImagem(imgCima, CELL_SIDE);
        this.imgBaixo = Desenho.retornaImagem(imgBaixo, CELL_SIDE);
        this.imgEsq = Desenho.retornaImagemEspelhadaHorizontal(imgDir, CELL_SIDE);
        this.imgDir = Desenho.retornaImagem(imgDir, CELL_SIDE);

        Cabeca.setImage(this.imgCima);

        ImageIcon imgCorpo = Desenho.retornaImagem(strImgCorpo, CELL_SIDE);

        for(Personagem c : Corpo)
            c.setImage(imgCorpo);
    }

}