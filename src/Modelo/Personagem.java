package Modelo;

import Auxiliar.Consts;
import Auxiliar.Desenho;
import Auxiliar.Posicao;
import FaseShotDuck.FaseShotDuck;
import FaseShotDuck.Trajetorias;
import FaseShotDuck.Velocidade;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;
import javax.swing.ImageIcon;

public abstract class Personagem implements Serializable {

    protected int direcao = Consts.PARADO;
    protected int direcaoAtual = Consts.PARADO;
    protected int direcaoAnterior = Consts.PARADO;
    protected Trajetorias trajetoria;
    public Velocidade velocidade;
    protected transient ImageIcon iImage;
    protected transient Image imagem;
    protected Posicao pPosicao;
    protected boolean bTransponivel;
    /*Pode passar por cima?*/
    protected boolean bMortal;
    private boolean removivel = false;

    /*Se encostar, morre?*/
    public boolean isbMortal() {
        return bMortal;
    }

    protected Personagem(String sNomeImagePNG, int linha, int coluna, int CELL_SIDE, int MUNDO_TAMANHO) {
        this.pPosicao = new Posicao(1, 1, 10);
        this.bTransponivel = true;
        this.bMortal = false;
        try {
            iImage = new ImageIcon(new java.io.File(".").getCanonicalPath() + Consts.PATH + sNomeImagePNG);
            Image img = iImage.getImage();
            BufferedImage bi = new BufferedImage(CELL_SIDE, CELL_SIDE, BufferedImage.TYPE_INT_ARGB);
            Graphics g = bi.createGraphics();
            g.drawImage(img, 0, 0, CELL_SIDE, CELL_SIDE, null);
            iImage = new ImageIcon(bi);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        this.setPosicao(linha, coluna, MUNDO_TAMANHO);
    }

    protected Personagem(Image imagem) {
        this.bMortal = false;
        this.velocidade = new Velocidade();
        this.pPosicao = new Posicao(0, 0);
        this.iImage = new ImageIcon(imagem);
        this.imagem = imagem;
    }

    public Posicao getPosicao() {
        /*TODO: Retirar este método para que objetos externos nao possam operar
         diretamente sobre a posição do Personagem*/
        return pPosicao;
    }

    public int getDirecao() {
        return direcao;
    }

    public int getDirecaoAtual() {
        return direcaoAtual;
    }

    public int getDirecaoAnterior() {
        return direcaoAnterior;
    }

    public Trajetorias getTrajetoria() {
        return this.trajetoria;
    }

    public void mover(FaseShotDuck f) {
        this.trajetoria.mover(this);
    }

    public int getXcentro() {
        return this.pPosicao.getColuna() + (this.iImage.getIconWidth() / 2);
    }

    public int getYcentro() {
        return this.pPosicao.getLinha() + (this.iImage.getIconHeight() / 2);
    }

    public void setDirecaoAnterior(int direcaoAnterior) {
        this.direcaoAnterior = direcaoAnterior;
    }

    public void setDirecaoAtual(int novaDirecao) {
        this.direcaoAnterior = direcaoAtual;
        this.direcaoAtual = novaDirecao;
    }

    public void setDirecao(int direcao) {
        this.direcao = direcao;
    }

    public boolean isbTransponivel() {
        return bTransponivel;
    }

    public void setbTransponivel(boolean bTransponivel) {
        this.bTransponivel = bTransponivel;
    }

    public void setTrajetoria(Trajetorias trajetoria) {
        this.trajetoria = trajetoria;
    }

    public void autoDesenho(Graphics g, int contador, int CELL_SIDE, int PERIOD) {
        if (direcaoAtual == Consts.PARADO) {
            Desenho.desenhar(g, this.iImage, this.pPosicao.getColuna(), this.pPosicao.getLinha(), false, CELL_SIDE);
        } else if (direcaoAtual == Consts.CIMA) {
            Desenho.desenharLiso(g, this.iImage, this.pPosicao.getColunaAnterior(), this.pPosicao.getLinhaAnterior(), false, -contador * 1000 * CELL_SIDE / (PERIOD * Consts.FRAME_RATE), 0, CELL_SIDE);
        } else if (direcaoAtual == Consts.BAIXO) {
            Desenho.desenharLiso(g, this.iImage, this.pPosicao.getColunaAnterior(), this.pPosicao.getLinhaAnterior(), false, contador * 1000 * CELL_SIDE / (PERIOD * Consts.FRAME_RATE), 0, CELL_SIDE);
        } else if (direcaoAtual == Consts.ESQUERDA) {
            Desenho.desenharLiso(g, this.iImage, this.pPosicao.getColunaAnterior(), this.pPosicao.getLinhaAnterior(), false, 0, -contador * 1000 * CELL_SIDE / (PERIOD * Consts.FRAME_RATE), CELL_SIDE);
        } else if (direcaoAtual == Consts.DIREITA) {
            Desenho.desenharLiso(g, this.iImage, this.pPosicao.getColunaAnterior(), this.pPosicao.getLinhaAnterior(), false, 0, contador * 1000 * CELL_SIDE / (PERIOD * Consts.FRAME_RATE), CELL_SIDE);
        }

    }

    public ImageIcon getImage() {
        return iImage;
    }

    /*/
    public void movimenta(int i){
        if(this.pPosicao.getColuna() > this.pPosicao.getColunaAnterior())
            Desenho.desenhar(this.iImage, this.pPosicao.getColuna() + i, this.pPosicao.getLinha(), false);
        if(this.pPosicao.getColuna() < this.pPosicao.getColunaAnterior())
            Desenho.desenhar(this.iImage, this.pPosicao.getColuna() - i, this.pPosicao.getLinha(), false);
        if(this.pPosicao.getLinha() > this.pPosicao.getLinhaAnterior())
            Desenho.desenhar(this.iImage, this.pPosicao.getColuna(), this.pPosicao.getLinha() + 1, false);
        if(this.pPosicao.getLinha() < this.pPosicao.getLinhaAnterior())
            Desenho.desenhar(this.iImage, this.pPosicao.getColuna(), this.pPosicao.getLinha() - 1, false);
    }
     */
    public boolean setPosicao(int linha, int coluna, int MUNDO_TAMANHO) {
        return pPosicao.setPosicao(linha, coluna, MUNDO_TAMANHO);
    }

    public void setPosicao(int x, int y) {
        pPosicao.setPosicao(x, y);
    }

    public void setPosicaoAnterior(int linhaAnterior, int colunaAnterior) {
        pPosicao.setPosicaoAnterior(linhaAnterior, colunaAnterior);
    }

    public boolean moveUp(int MUNDO_TAMANHO) {
        return this.pPosicao.moveUp(MUNDO_TAMANHO);
    }

    public boolean moveDown(int MUNDO_TAMANHO) {
        return this.pPosicao.moveDown(MUNDO_TAMANHO);
    }

    public boolean moveRight(int MUNDO_TAMANHO) {
        return this.pPosicao.moveRight(MUNDO_TAMANHO);
    }

    public boolean moveLeft(int MUNDO_TAMANHO) {
        return this.pPosicao.moveLeft(MUNDO_TAMANHO);
    }

    public void setImage(ImageIcon iImage) {
        this.iImage = iImage;
    }

    public Posicao obtemPosicaoAntiga() {
        int linha = pPosicao.getLinhaAnterior();
        int coluna = pPosicao.getColunaAnterior();

        if (direcaoAnterior == Consts.CIMA) {
            linha++;
        } else if (direcaoAnterior == Consts.BAIXO) {
            linha--;
        } else if (direcaoAnterior == Consts.ESQUERDA) {
            coluna++;
        } else if (direcaoAnterior == Consts.DIREITA) {
            coluna--;
        }

        return new Posicao(linha, coluna, Consts.JANELA);
    }

    public Image getImg() {
        return this.imagem;
    }

    public void setImg(Image img) {
        this.imagem = img;
    }

    public boolean getbMorto() {
        return bMortal;
    }

    public void setbMorto(boolean morto) {
        this.bMortal = morto;
    }

    public void setRemovivel(boolean r) {
        this.removivel = r;
    }
    
    public boolean isRemovivel(){
        return this.removivel;
    }

}
