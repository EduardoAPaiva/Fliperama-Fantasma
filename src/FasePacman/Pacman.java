package FasePacman;

import Auxiliar.Consts;
import Modelo.Hero;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.swing.ImageIcon;

public class Pacman extends Hero{
    
    private transient ImageIcon pacman_imgCima;
    private transient ImageIcon pacman_imgBaixo;
    private transient ImageIcon pacman_imgEsq;
    private transient ImageIcon pacman_imgDir;

    public Pacman(String sNomeImagePNG, String pacman_imgCima, String pacman_imgBaixo, String pacman_imgEsq, String pacman_imgDir, int linha, int coluna, int CELL_SIDE, int MUNDO_TAMANHO){
        super(sNomeImagePNG, linha, coluna, CELL_SIDE, MUNDO_TAMANHO);
        this.pacman_imgCima = retornaImagem(pacman_imgCima, CELL_SIDE);
        this.pacman_imgBaixo = retornaImagem(pacman_imgBaixo, CELL_SIDE);
        this.pacman_imgEsq = retornaImagem(pacman_imgEsq, CELL_SIDE);
        this.pacman_imgDir = retornaImagem(pacman_imgDir, CELL_SIDE);
        
    }

    public final ImageIcon retornaImagem(String sNomeImagePNG, int CELL_SIDE){
        try {
            ImageIcon iImage = new ImageIcon(new java.io.File(".").getCanonicalPath() + Consts.PATH + sNomeImagePNG);
            Image img = iImage.getImage();
            BufferedImage bi = new BufferedImage(CELL_SIDE, CELL_SIDE, BufferedImage.TYPE_INT_ARGB);
            Graphics g = bi.createGraphics();
            g.drawImage(img, 0, 0, CELL_SIDE, CELL_SIDE, null);
            return new ImageIcon(bi);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            return new ImageIcon();
        }
    }
 
    public boolean moveUp(int MUNDO_TAMANHO){
        setImage(pacman_imgCima);
        return super.moveUp(MUNDO_TAMANHO);
    }

    public boolean moveDown(int MUNDO_TAMANHO){
        setImage(pacman_imgBaixo);
        return super.moveDown(MUNDO_TAMANHO);
    }

    public boolean moveLeft(int MUNDO_TAMANHO){
        setImage(pacman_imgEsq);
        return super.moveLeft(MUNDO_TAMANHO);
    }

    public boolean moveRight(int MUNDO_TAMANHO){
        setImage(pacman_imgDir);
        return super.moveRight(MUNDO_TAMANHO);
    }

    public void atualizaDirecao(){
        direcaoAnterior = direcaoAtual;
        
    }

    public void recuperarTransient(String pacman_imgCima, String pacman_imgBaixo, String pacman_imgEsq, String pacman_imgDir, int CELL_SIDE){
        this.pacman_imgCima = retornaImagem(pacman_imgCima, CELL_SIDE);
        this.pacman_imgBaixo = retornaImagem(pacman_imgBaixo, CELL_SIDE);
        this.pacman_imgEsq = retornaImagem(pacman_imgEsq, CELL_SIDE);
        this.pacman_imgDir = retornaImagem(pacman_imgDir, CELL_SIDE);
        super.setImage(this.pacman_imgDir);
    }

}
