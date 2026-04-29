package Controler;

import Auxiliar.Consts;
import Auxiliar.Desenho;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;

import javax.swing.ImageIcon;

public class Mensagem implements Serializable{

    protected transient ImageIcon iImage;
    protected boolean printavel;

    public Mensagem(boolean printavel) {
        this.printavel = printavel;
    }

    public void defineImagem(String sNomeImagePNG, int CELL_SIDE, int MUNDO_TAMANHO){
        try {
            iImage = new ImageIcon(new java.io.File(".").getCanonicalPath() + Consts.PATH + sNomeImagePNG);
            Image img = iImage.getImage();
            BufferedImage bi = new BufferedImage(CELL_SIDE*MUNDO_TAMANHO, CELL_SIDE*MUNDO_TAMANHO, BufferedImage.TYPE_INT_ARGB);
            Graphics g = bi.createGraphics();
            g.drawImage(img, 0, 0, CELL_SIDE*MUNDO_TAMANHO, CELL_SIDE*MUNDO_TAMANHO, null);
            iImage = new ImageIcon(bi);

            printavel = true;

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    protected void desenhaMensagem(Graphics g, int CELL_SIDE){
        Desenho.desenhar(g, iImage, 0, 0, true, CELL_SIDE);
    }

    protected void desativaMensagem(){
        printavel = false;
    }

    public void autoDesenho(Graphics g, int CELL_SIDE){
        Desenho.desenhar(g, iImage, 0, 0, true, CELL_SIDE);
    }

    public ImageIcon getImage(){
        return iImage;
    }

}