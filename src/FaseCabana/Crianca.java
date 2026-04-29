package FaseCabana;

import javax.swing.ImageIcon;

import Auxiliar.Consts;
import Auxiliar.Desenho;
import Modelo.Hero;

public class Crianca extends Hero{
    
    private int ladoVirado = Consts.PARADO;

    private int imgCimaAtual = 0;
    private int imgBaixoAtual = 0;
    private int imgLadoAtual = 0;
    private transient ImageIcon imgCimaParado;
    private transient ImageIcon imgBaixoParado;
    private transient ImageIcon imgCimaMovendoEsq;
    private transient ImageIcon imgCimaMovendoDir;
    private transient ImageIcon imgBaixoMovendoDir;
    private transient ImageIcon imgBaixoMovendoEsq;
    private transient ImageIcon imgEsqParado;
    private transient ImageIcon imgDirParado;
    private transient ImageIcon imgDirMovendo1;
    private transient ImageIcon imgDirMovendo2;
    private transient ImageIcon imgEsqMovendo1;
    private transient ImageIcon imgEsqMovendo2;

    public Crianca(String sNomeImagePNG, String imgCimaParado, String imgBaixoParado, String imgCimaMovendo, String imgBaixoMovendo, String imgLado1, String imgLado2, String imgLadoParado, int linha, int coluna, int CELL_SIDE, int MUNDO_TAMANHO){
        super(sNomeImagePNG, linha, coluna, CELL_SIDE, MUNDO_TAMANHO);

        this.imgCimaParado = Desenho.retornaImagem(imgCimaParado, CELL_SIDE);
        this.imgBaixoParado = Desenho.retornaImagem(imgBaixoParado, CELL_SIDE);
        this.imgCimaMovendoEsq = Desenho.retornaImagem(imgCimaMovendo, CELL_SIDE);
        this.imgCimaMovendoDir = Desenho.retornaImagemEspelhadaHorizontal(imgCimaMovendo, CELL_SIDE);
        this.imgBaixoMovendoEsq = Desenho.retornaImagem(imgBaixoMovendo, CELL_SIDE);
        this.imgBaixoMovendoDir = Desenho.retornaImagemEspelhadaHorizontal(imgBaixoMovendo, CELL_SIDE);
        this.imgEsqParado = Desenho.retornaImagem(imgLadoParado, CELL_SIDE);
        this.imgDirParado = Desenho.retornaImagemEspelhadaHorizontal(imgLadoParado, CELL_SIDE);
        this.imgEsqMovendo1 = Desenho.retornaImagem(imgLado1, CELL_SIDE);
        this.imgDirMovendo1 = Desenho.retornaImagemEspelhadaHorizontal(imgLado1, CELL_SIDE);
        this.imgEsqMovendo2 = Desenho.retornaImagem(imgLado2, CELL_SIDE);
        this.imgDirMovendo2 = Desenho.retornaImagemEspelhadaHorizontal(imgLado2, CELL_SIDE);
    }

    public void ficaParado(){
        if(ladoVirado == Consts.BAIXO)
            super.setImage(imgBaixoParado);
        else if(ladoVirado == Consts.CIMA)
            super.setImage(imgCimaParado);
        else if(ladoVirado == Consts.ESQUERDA)
            super.setImage(imgEsqParado);
        else if(ladoVirado == Consts.DIREITA)
            super.setImage(imgDirParado);
        direcaoAtual = Consts.PARADO;
    }

    public void setLadoVirado(int ladoVirado){
        this.ladoVirado = ladoVirado;
    }

    public int getLadoVirado(){
        return ladoVirado;
    }

    public void mudaImagemCima(){
        if(imgCimaAtual == 0){
            imgCimaAtual = 1;
            setImage(imgCimaMovendoDir);
        }
        else if(imgCimaAtual == 1){
            imgCimaAtual = 0;
            setImage(imgCimaParado);
        }
        else if(imgCimaAtual == 2){
            imgCimaAtual = 3;
            setImage(imgCimaMovendoEsq);
        }
        else if(imgCimaAtual == 3){
            imgCimaAtual = 0;
            setImage(imgCimaParado);
        }
    }

    public void mudaImagemBaixo(){
        if(imgBaixoAtual == 0){
            imgBaixoAtual = 1;
            setImage(imgBaixoMovendoDir);
        }
        else if(imgBaixoAtual == 1){
            imgBaixoAtual = 0;
            setImage(imgBaixoParado);
        }
        else if(imgBaixoAtual == 2){
            imgBaixoAtual = 3;
            setImage(imgBaixoMovendoEsq);
        }
        else if(imgBaixoAtual == 3){
            imgBaixoAtual = 0;
            setImage(imgBaixoParado);
        }
    }

    public void mudaImagemDireita(){
        if(imgLadoAtual == 0){
            imgLadoAtual = 1;
            setImage(imgDirMovendo1);
        }
        else if(imgLadoAtual == 1){
            imgLadoAtual = 0;
            setImage(imgDirParado);
        }
        else if(imgLadoAtual == 2){
            imgLadoAtual = 3;
            setImage(imgDirMovendo2);
        }
        else if(imgLadoAtual == 3){
            imgLadoAtual = 0;
            setImage(imgDirParado);
        }
    }

    public void mudaImagemEsquerda(){
        if(imgLadoAtual == 0){
            imgLadoAtual = 1;
            setImage(imgEsqMovendo1);
        }
        else if(imgLadoAtual == 1){
            imgLadoAtual = 2;
            setImage(imgEsqParado);
        }
        else if(imgLadoAtual == 2){
            imgLadoAtual = 3;
            setImage(imgEsqMovendo2);
        }
        else if(imgLadoAtual == 3){
            imgLadoAtual = 0;
            setImage(imgEsqParado);
        }
    }

    public void recuperarTransient(String imgCimaParado, String imgBaixoParado, String imgCimaMovendo, String imgBaixoMovendo, String imgLado1, String imgLado2, String imgLadoParado, int CELL_SIDE){
        this.imgCimaParado = Desenho.retornaImagem(imgCimaParado, CELL_SIDE);
        this.imgBaixoParado = Desenho.retornaImagem(imgBaixoParado, CELL_SIDE);
        this.imgCimaMovendoEsq = Desenho.retornaImagem(imgCimaMovendo, CELL_SIDE);
        this.imgCimaMovendoDir = Desenho.retornaImagemEspelhadaHorizontal(imgCimaMovendo, CELL_SIDE);
        this.imgBaixoMovendoEsq = Desenho.retornaImagem(imgBaixoMovendo, CELL_SIDE);
        this.imgBaixoMovendoDir = Desenho.retornaImagemEspelhadaHorizontal(imgBaixoMovendo, CELL_SIDE);
        this.imgEsqParado = Desenho.retornaImagem(imgLadoParado, CELL_SIDE);
        this.imgDirParado = Desenho.retornaImagemEspelhadaHorizontal(imgLadoParado, CELL_SIDE);
        this.imgEsqMovendo1 = Desenho.retornaImagem(imgLado1, CELL_SIDE);
        this.imgDirMovendo1 = Desenho.retornaImagemEspelhadaHorizontal(imgLado1, CELL_SIDE);
        this.imgEsqMovendo2 = Desenho.retornaImagem(imgLado2, CELL_SIDE);
        this.imgDirMovendo2 = Desenho.retornaImagemEspelhadaHorizontal(imgLado2, CELL_SIDE);

        setImage(this.imgCimaParado);
    }

}
