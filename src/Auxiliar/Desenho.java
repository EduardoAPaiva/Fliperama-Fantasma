package Auxiliar;

import Controler.Tela;
import Modelo.Personagem;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;
import javax.swing.ImageIcon;

public class Desenho implements Serializable {

    static Tela jCenario;

    public static void setCenario(Tela umJCenario) {
        jCenario = umJCenario;
    }

    public static Tela acessoATelaDoJogo() {
        return jCenario;
    }

    public static Graphics getGraphicsDaTela() {
        return jCenario.getGraphicsBuffer();
    }

    public static void desenhar(Graphics g, ImageIcon iImage, int iColuna, int iLinha, boolean telaInteira, int CELL_SIDE) {
        int telaX = (iColuna - jCenario.getCameraColuna()) * CELL_SIDE + Consts.CONSTANTE_HORIZONTAL;
        int telaY = (iLinha - jCenario.getCameraLinha()) * CELL_SIDE;

        if (telaInteira) {
                iImage.paintIcon(jCenario, g, Consts.CONSTANTE_HORIZONTAL, 0);
        }
        else if (telaX >= 0 && telaX < Consts.JANELA + Consts.CONSTANTE_HORIZONTAL
                && telaY >= 0 && telaY < Consts.JANELA) {
            iImage.paintIcon(jCenario, g, telaX, telaY);
        }
    }

    public static void desenharLiso(Graphics g, ImageIcon iImage, int iColuna, int iLinha, boolean telaInteira, int contadorVertical, int contadorHorizontal, int CELL_SIDE) {
        int telaX = (iColuna - jCenario.getCameraColunaAnterior()) * CELL_SIDE + contadorHorizontal + Consts.CONSTANTE_HORIZONTAL;
        int telaY = (iLinha - jCenario.getCameraLinhaAnterior()) * CELL_SIDE + contadorVertical;

        if (telaInteira) {
                // Desenha imagem na tela inteira
                Image imgEscalada = iImage.getImage().getScaledInstance(
                        Consts.JANELA,
                        Consts.JANELA,
                        Image.SCALE_SMOOTH
                );
                new ImageIcon(imgEscalada).paintIcon(jCenario, g, 0, 0);
        }
        else if (telaX + CELL_SIDE >= 0 && telaX < Consts.JANELA + Consts.CONSTANTE_HORIZONTAL
                && telaY + CELL_SIDE >= 0 && telaY < Consts.JANELA) {
            iImage.paintIcon(jCenario, g, telaX, telaY);
        }
    }

    public static void desenharPorPixel(Graphics g, ImageIcon iImage, int telaX, int telaY) {
        iImage.paintIcon(jCenario, g, telaX  + Consts.CONSTANTE_HORIZONTAL, telaY);
    }
    
    public static final Image gerenciaRotacaoImagem(Personagem umP) {
        BufferedImage bi = new BufferedImage(150, 150, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = bi.createGraphics();

        // Espelhar horizontalmente se estiver indo para a esquerda
        boolean esquerda = umP.velocidade.getxVelocidadeint() < 0;
        int xparaespelhado = esquerda ? 0 : 150;
        int larguraparaespelhado = esquerda ? 150 : -150;
        double novoAngulo = esquerda ? umP.velocidade.anguloatual() + Math.PI : umP.velocidade.anguloatual();

        // Rotaciona em torno do centro
        g.rotate(novoAngulo, 75, 75);
        g.drawImage(umP.getImg(), xparaespelhado, 0, larguraparaespelhado, 150, null);

        g.dispose();
        return (new ImageIcon(bi)).getImage();
    }

    public static final ImageIcon retornaImagem(String sNomeImagePNG,int CELL_SIDE){
        try {
            ImageIcon iImage = new ImageIcon(new java.io.File(".").getCanonicalPath() + Consts.PATH + sNomeImagePNG);
            Image img = iImage.getImage();
            BufferedImage bi = new BufferedImage(CELL_SIDE,CELL_SIDE, BufferedImage.TYPE_INT_ARGB);
            Graphics g = bi.createGraphics();
            g.drawImage(img, 0, 0, CELL_SIDE,CELL_SIDE, null);
            return new ImageIcon(bi);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            return new ImageIcon();
        }
    }

    public static final ImageIcon retornaImagemTamanhoCustomizado(String sNomeImagePNG,int tamX, int tamY){
        try {
            ImageIcon iImage = new ImageIcon(new java.io.File(".").getCanonicalPath() + Consts.PATH + sNomeImagePNG);
            Image img = iImage.getImage();
            BufferedImage bi = new BufferedImage(tamX,tamY, BufferedImage.TYPE_INT_ARGB);
            Graphics g = bi.createGraphics();
            g.drawImage(img, 0, 0, tamX,tamY, null);
            return new ImageIcon(bi);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    public static final ImageIcon retornaTelaCheia(String sNomeImagePNG){
        try {
            ImageIcon iImage = new ImageIcon(new java.io.File(".").getCanonicalPath() + Consts.PATH + sNomeImagePNG);
            Image img = iImage.getImage();
            BufferedImage bi = new BufferedImage(Consts.pixelsLargura, Consts.pixelsAltura, BufferedImage.TYPE_INT_ARGB);
            Graphics g = bi.createGraphics();
            g.drawImage(img, 0, 0, Consts.pixelsLargura, Consts.pixelsAltura, null);
            return new ImageIcon(bi);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            return new ImageIcon();
        }
    }

    public static final ImageIcon retornaImagemEspelhadaHorizontal(String sNomeImagePNG,int CELL_SIDE){
        try {
            ImageIcon iImage = new ImageIcon(new java.io.File(".").getCanonicalPath() + Consts.PATH + sNomeImagePNG);
            Image img = iImage.getImage();
            BufferedImage bi = new BufferedImage(CELL_SIDE,CELL_SIDE, BufferedImage.TYPE_INT_ARGB);
            Graphics g = bi.createGraphics();
            g.drawImage(img, CELL_SIDE, 0, -CELL_SIDE,CELL_SIDE, null);
            return new ImageIcon(bi);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            return new ImageIcon();
        }
    }

    public static final ImageIcon retornaImagemEspelhadaVertical(String sNomeImagePNG,int CELL_SIDE){
        try {
            ImageIcon iImage = new ImageIcon(new java.io.File(".").getCanonicalPath() + Consts.PATH + sNomeImagePNG);
            Image img = iImage.getImage();
            BufferedImage bi = new BufferedImage(CELL_SIDE,CELL_SIDE, BufferedImage.TYPE_INT_ARGB);
            Graphics g = bi.createGraphics();
            g.drawImage(img, 0, CELL_SIDE, CELL_SIDE,-CELL_SIDE, null);
            return new ImageIcon(bi);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            return new ImageIcon();
        }
    }

}