package FaseExplorador;

import Auxiliar.Desenho;
import Modelo.Hero;

import java.io.Serializable;

import javax.swing.ImageIcon;

public class Explorador implements Serializable{

    private int imagemCima = 0;
    private int imagemBaixo = 0; 
    private int imagemLado = 0;
    private Hero explorador;
    private transient ImageIcon imgparado;
    private transient ImageIcon imgCima;
    private transient ImageIcon imgCimaEspelhado;
    private transient ImageIcon imgBaixo;
    private transient ImageIcon imgBaixoEspelhado;
    private transient ImageIcon imgDir1;
    private transient ImageIcon imgDir2;
    private transient ImageIcon imgDir3;
    private transient ImageIcon imgEsq1;
    private transient ImageIcon imgEsq2;
    private transient ImageIcon imgEsq3;

    public Explorador(String sNomeImagePNG, String imgCima, String imgBaixo, String imgLado1, String imgLado2, String imgLado3, int linha, int coluna, int CELL_SIDE, int MUNDO_TAMANHO){
        explorador = new Hero(sNomeImagePNG, linha, coluna, CELL_SIDE,MUNDO_TAMANHO);
        this.imgparado= Desenho.retornaImagem(sNomeImagePNG, CELL_SIDE);
        this.imgCima = Desenho.retornaImagem(imgCima, CELL_SIDE);
        this.imgCimaEspelhado = Desenho.retornaImagemEspelhadaHorizontal(imgCima, CELL_SIDE);
        this.imgBaixo = Desenho.retornaImagem(imgBaixo, CELL_SIDE);
        this.imgBaixoEspelhado = Desenho.retornaImagemEspelhadaHorizontal(imgBaixo, CELL_SIDE);
        this.imgDir1 = Desenho.retornaImagem(imgLado1, CELL_SIDE);
        this.imgEsq1 = Desenho.retornaImagemEspelhadaHorizontal(imgLado1, CELL_SIDE);
        this.imgDir2 = Desenho.retornaImagem(imgLado2, CELL_SIDE);
        this.imgEsq2 = Desenho.retornaImagemEspelhadaHorizontal(imgLado2, CELL_SIDE);
        this.imgDir3 = Desenho.retornaImagem(imgLado3, CELL_SIDE);
        this.imgEsq3 = Desenho.retornaImagemEspelhadaHorizontal(imgLado3, CELL_SIDE);

    }
 
    public Hero getExplorador(){
        return explorador;
    }

    public boolean moveUp(int MUNDO_TAMANHO){
        trocaImagemCima();
        return explorador.moveUp(MUNDO_TAMANHO);
    }

    public boolean moveDown(int MUNDO_TAMANHO){
        trocaImagemBaixo();
        return explorador.moveDown(MUNDO_TAMANHO);
    }

    public boolean moveLeft(int MUNDO_TAMANHO){
        trocaImagemLadoEsquerdo();
        return explorador.moveLeft(MUNDO_TAMANHO);
    }

    public boolean moveRight(int MUNDO_TAMANHO){
        trocaImagemLadoDireito();
        return explorador.moveRight(MUNDO_TAMANHO);
    }
    public boolean ficaparado(){
        explorador.setImage(imgparado);
        return true;
    }
    public int getDirecao(){
        return explorador.getDirecao();
    }

    public int getDirecaoAtual(){
        return explorador.getDirecaoAtual();
    }

    public void setDirecao(int direcao){
        explorador.setDirecao(direcao);
    }

    public void setDirecaoAtual(){
        explorador.setDirecaoAtual(explorador.getDirecao());
    }

    public void retornaExplorador(int MUNDO_TAMANHO){
        explorador.setPosicao(explorador.getPosicao().getLinhaAnterior(), explorador.getPosicao().getColunaAnterior(),MUNDO_TAMANHO);
    }

    public void trocaImagemCima(){
        if(imagemCima == 0){
            imagemCima = 1;
            explorador.setImage(imgCima);
        }
        else{
            imagemCima = 0;
            explorador.setImage(imgCimaEspelhado);
        }
    }

    public void trocaImagemBaixo(){
        if(imagemBaixo == 0){
            imagemBaixo = 1;
            explorador.setImage(imgBaixo);
        }
        else{
            imagemBaixo = 0;
            explorador.setImage(imgBaixoEspelhado);
        }
    }

    public void trocaImagemLadoDireito(){
        if(imagemLado == 0){
            imagemLado = 1;
            explorador.setImage(imgDir2);
        }
        else if(imagemLado == 1){
            imagemLado = 2;
            explorador.setImage(imgDir3);
        }
        else if(imagemLado == 2){
            imagemLado = 3;
            explorador.setImage(imgDir2);
        }
        else if(imagemLado == 3){
            imagemLado = 0;
            explorador.setImage(imgDir1);
        }
    }

    public void trocaImagemLadoEsquerdo(){
        if(imagemLado == 0){
            imagemLado = 1;
            explorador.setImage(imgEsq2);
        }
        else if(imagemLado == 1){
            imagemLado = 2;
            explorador.setImage(imgEsq3);
        }
        else if(imagemLado == 2){
            imagemLado = 3;
            explorador.setImage(imgEsq2);
        }
        else if(imagemLado == 3){
            imagemLado = 0;
            explorador.setImage(imgEsq1);
        }
    }

    public void setImagemParado(){
        explorador.setImage(imgparado);
    }


    public void recuperarTransient(String sNomeImagePNG, String imgCima, String imgBaixo, String imgLado1, String imgLado2, String imgLado3, int CELL_SIDE){
        this.imgparado= Desenho.retornaImagem(sNomeImagePNG, CELL_SIDE);
        this.imgCima = Desenho.retornaImagem(imgCima, CELL_SIDE);
        this.imgCimaEspelhado = Desenho.retornaImagemEspelhadaHorizontal(imgCima, CELL_SIDE);
        this.imgBaixo = Desenho.retornaImagem(imgBaixo, CELL_SIDE);
        this.imgBaixoEspelhado = Desenho.retornaImagemEspelhadaHorizontal(imgBaixo, CELL_SIDE);
        this.imgDir1 = Desenho.retornaImagem(imgLado1, CELL_SIDE);
        this.imgEsq1 = Desenho.retornaImagemEspelhadaHorizontal(imgLado1, CELL_SIDE);
        this.imgDir2 = Desenho.retornaImagem(imgLado2, CELL_SIDE);
        this.imgEsq2 = Desenho.retornaImagemEspelhadaHorizontal(imgLado2, CELL_SIDE);
        this.imgDir3 = Desenho.retornaImagem(imgLado3, CELL_SIDE);
        this.imgEsq3 = Desenho.retornaImagemEspelhadaHorizontal(imgLado3, CELL_SIDE);
        explorador.setImage(imgparado);
    }
}