package Controler;

import Auxiliar.Consts;
import Auxiliar.Desenho;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.swing.ImageIcon;

public class Texto extends Mensagem{
    
    private int linha;
    private int coluna;

    public Texto(boolean printavel, int linha, int coluna) {
        super(printavel);
        this.linha = linha;
        this.coluna = coluna;
    }

    @Override
    public void autoDesenho(Graphics g, int CELL_SIDE){
        Desenho.desenhar(g, iImage, coluna, linha, false, CELL_SIDE);
    }

    public void defineImagem(String sNomeImagePNG, int CELL_SIDE){
        try {
            iImage = new ImageIcon(new java.io.File(".").getCanonicalPath() + Consts.PATH + sNomeImagePNG);
            Image img = iImage.getImage();
            BufferedImage bi = new BufferedImage(CELL_SIDE, CELL_SIDE, BufferedImage.TYPE_INT_ARGB);
            Graphics g = bi.createGraphics();
            g.drawImage(img, 0, 0, CELL_SIDE, CELL_SIDE, null);
            iImage = new ImageIcon(bi);

            printavel = true;

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void defineAlgarismo(int i, int CELL_SIDE){
        if(i == 0) this.defineImagem("letras/0.png", CELL_SIDE);
        else if(i == 1) this.defineImagem("letras/1.png", CELL_SIDE);
        else if(i == 2) this.defineImagem("letras/2.png", CELL_SIDE);
        else if(i == 3) this.defineImagem("letras/3.png", CELL_SIDE);
        else if(i == 4) this.defineImagem("letras/4.png", CELL_SIDE);
        else if(i == 5) this.defineImagem("letras/5.png", CELL_SIDE);
        else if(i == 6) this.defineImagem("letras/6.png", CELL_SIDE);
        else if(i == 7) this.defineImagem("letras/7.png", CELL_SIDE);
        else if(i == 8) this.defineImagem("letras/8.png", CELL_SIDE);
        else if(i == 9) this.defineImagem("letras/9.png", CELL_SIDE);
        else this.defineImagem("", CELL_SIDE);
    }

}