package FaseShotDuck;

import Auxiliar.Consts;
import Auxiliar.Posicao;
import Modelo.Personagem;
import java.awt.Image;
import java.io.Serializable;

public class Pedra implements Serializable{

    private Posicao pos;
    private double vx, vy;
    private transient Image img;
    private boolean removivel = false;

    private final double GRAVIDADE = 0.5; 

    public Pedra(Image img, int x, int y, double vx, double vy) {
        this.img = img;
        this.pos = new Posicao(x, y); // linha = y, coluna = x
        this.vx = vx;
        this.vy = vy;
    }

    public void mover() {
        pos.setColuna(pos.getColuna() + (int) vx);
        pos.setLinha(pos.getLinha() + (int) vy);

        // aplica gravidade
        vy += GRAVIDADE;

        // checa limite da tela
        if (pos.getLinha() > Consts.pixelsAltura|| pos.getColuna() < 0 ||
                pos.getColuna() >  Consts.pixelsLargura||pos.getLinha()<-30) {
            removivel = true;
        }
    }

    public Image getImg() { return img; }
    public Posicao getPosicao() { return pos; }

    public boolean isRemovivel() { return removivel; }
    public void setRemovivel(boolean r) { this.removivel = r; }

    // colisão com personagem
    public boolean colideCom(Personagem p) {
        int pedraX = pos.getColuna();
        int pedraY = pos.getLinha();
        int pedraW = img.getWidth(null);
        int pedraH = img.getHeight(null);

        int patoX = p.getPosicao().getColuna();
        int patoY = p.getPosicao().getLinha();
        int patoW = p.getImg().getWidth(null);
        int patoH = p.getImg().getHeight(null);

        return pedraX < patoX + patoW &&
               pedraX + pedraW > patoX &&
               pedraY < patoY + patoH &&
               pedraY + pedraH > patoY;
    }

    public void setImage(Image i){
        img = i;
    }
}
