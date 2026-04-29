package FaseMenu;

import java.io.IOException;
import java.io.Serializable;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;

import Auxiliar.Consts;
import Auxiliar.Desenho;

public class Botao implements Serializable{

    private int estado = 0;
    private transient ImageIcon naoSelecionado;
    private transient ImageIcon selecionado;
    private int tamanhoX;
    private int tamanhoY;
    private int posicaoX;
    private int posicaoY;

    public Botao(String strNaoSelecionado, String strSelecionado, int posicaoX, int posicaoY, int tamanhoX, int tamanhoY){

        try {
            naoSelecionado = new ImageIcon(new java.io.File(".").getCanonicalPath() + Consts.PATH + strNaoSelecionado);
            Image img = naoSelecionado.getImage();
            BufferedImage bi = new BufferedImage(tamanhoX, tamanhoY, BufferedImage.TYPE_INT_ARGB);
            Graphics g = bi.createGraphics();
            g.drawImage(img, 0, 0, tamanhoX, tamanhoY, null);
            naoSelecionado = new ImageIcon(bi);

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        try {
            selecionado = new ImageIcon(new java.io.File(".").getCanonicalPath() + Consts.PATH + strSelecionado);
            Image img = selecionado.getImage();
            BufferedImage bi = new BufferedImage(tamanhoX, tamanhoY, BufferedImage.TYPE_INT_ARGB);
            Graphics g = bi.createGraphics();
            g.drawImage(img, 0, 0, tamanhoX, tamanhoY, null);
            selecionado = new ImageIcon(bi);

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        this.tamanhoX = tamanhoX;
        this.tamanhoY = tamanhoY;
        this.posicaoX = posicaoX;
        this.posicaoY = posicaoY;
        
    }

    public void desenhaBotao(Graphics g){
        if(estado == 0){
            Desenho.desenharPorPixel(g, naoSelecionado, posicaoX, posicaoY);
        }
        else{
            Desenho.desenharPorPixel(g, selecionado, posicaoX, posicaoY);
        }
    }

    public void setEstado(int estado){
        this.estado = estado;
    }

    public void recuperarTransient(String strNaoSelecionado, String strSelecionado){
        try {
            naoSelecionado = new ImageIcon(new java.io.File(".").getCanonicalPath() + Consts.PATH + strNaoSelecionado);
            Image img = naoSelecionado.getImage();
            BufferedImage bi = new BufferedImage(tamanhoX, tamanhoY, BufferedImage.TYPE_INT_ARGB);
            Graphics g = bi.createGraphics();
            g.drawImage(img, 0, 0, tamanhoX, tamanhoY, null);
            naoSelecionado = new ImageIcon(bi);

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        try {
            selecionado = new ImageIcon(new java.io.File(".").getCanonicalPath() + Consts.PATH + strSelecionado);
            Image img = selecionado.getImage();
            BufferedImage bi = new BufferedImage(tamanhoX, tamanhoY, BufferedImage.TYPE_INT_ARGB);
            Graphics g = bi.createGraphics();
            g.drawImage(img, 0, 0, tamanhoX, tamanhoY, null);
            selecionado = new ImageIcon(bi);

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

}