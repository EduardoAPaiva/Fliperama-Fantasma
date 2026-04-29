package FasePacman;

import Auxiliar.Consts;
import Auxiliar.Desenho;
import Modelo.Hero;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import java.util.Random;

public class Fantasma extends Hero{

    private ImageIcon imgNormalEsq;
    private ImageIcon imgNormalDir;

    private ImageIcon imgNormalPadrao; 
    private static ImageIcon imgAssustado;

    private boolean estaAssustado = false;
    private boolean foiComido = false; 
    private int linhaInicial;
    private int colunaInicial;

    private static Random random = new Random();

    public Fantasma(String sNomeImagePNG, int linha, int coluna, int CELL_SIDE, int MUNDO_TAMANHO){
        super(sNomeImagePNG, linha, coluna, CELL_SIDE, MUNDO_TAMANHO);
        this.bTransponivel = true;

        this.linhaInicial = linha;
        this.colunaInicial = coluna;

        this.imgNormalPadrao = this.iImage; 

        this.imgNormalDir = Desenho.retornaImagem(sNomeImagePNG, CELL_SIDE);
        this.imgNormalEsq = Desenho.retornaImagemEspelhadaHorizontal(sNomeImagePNG, CELL_SIDE);

        if (imgAssustado == null) {
            String FasePath = "FasePacman/"; // Caminho das imagens
            imgAssustado = Desenho.retornaImagem(FasePath + "scaredGhost.png", CELL_SIDE);
        }
        

    }

    public void moverFantasmas(int[][] labirinto, int MUNDO_TAMANHO) {

        if (this.foiComido) {
         this.setDirecaoAtual(Consts.PARADO);
         return; 
        }

        int linha = this.getPosicao().getLinha();
        int coluna = this.getPosicao().getColuna();

        ArrayList<Integer> movimentosValidos = new ArrayList<>();

        if (labirinto[linha - 1][coluna] != 1 && direcaoAtual != Consts.BAIXO) movimentosValidos.add(Consts.CIMA);
        if (labirinto[linha + 1][coluna] != 1 && direcaoAtual != Consts.CIMA) movimentosValidos.add(Consts.BAIXO);
        if (labirinto[linha][coluna - 1] != 1 && direcaoAtual != Consts.DIREITA) movimentosValidos.add(Consts.ESQUERDA);
        if (labirinto[linha][coluna + 1] != 1 && direcaoAtual != Consts.ESQUERDA) movimentosValidos.add(Consts.DIREITA);

        if(!movimentosValidos.isEmpty()) {

            int indice = random.nextInt(movimentosValidos.size());
            int direcaoSorteada = movimentosValidos.get(indice);
            
            if (this.estaAssustado) {
            
            iImage = imgAssustado; 
            } 
            else {
                if (direcaoSorteada == Consts.ESQUERDA) {
                    iImage = imgNormalEsq;
                } else if (direcaoSorteada == Consts.DIREITA) {
                    iImage = imgNormalDir;
                } else {
                    iImage = imgNormalPadrao; 
                }
            }

            if (direcaoSorteada == Consts.CIMA) {
                this.setDirecaoAtual(Consts.CIMA);
                this.moveUp(MUNDO_TAMANHO);
            } else if (direcaoSorteada == Consts.BAIXO) {
                this.setDirecaoAtual(Consts.BAIXO);
                this.moveDown(MUNDO_TAMANHO);
            } else if (direcaoSorteada == Consts.ESQUERDA) {
                this.setDirecaoAtual(Consts.ESQUERDA);
                this.moveLeft(MUNDO_TAMANHO);
            } else if (direcaoSorteada == Consts.DIREITA) {
                this.setDirecaoAtual(Consts.DIREITA);
                this.moveRight(MUNDO_TAMANHO);
            }

        }
        else {
            this.setDirecaoAtual(Consts.PARADO);
        }
            
    }

    
    public void assustar() {
        if (!this.foiComido) { 
            this.estaAssustado = true;
            this.iImage = imgAssustado; 
        }
    }

    public void normalizar() {
        this.estaAssustado = false;
        this.foiComido = false;
        this.iImage = imgNormalPadrao; 
    }


    public void serComido() {
        this.foiComido = true;
        this.estaAssustado = false;

        this.setPosicao(this.linhaInicial, this.colunaInicial, 28); 
        this.setDirecaoAtual(Consts.PARADO);
    }

    public int getLinhaInicial(){
        return linhaInicial;
    }

    public int getColunaInicial(){
        return colunaInicial;
    }

    public boolean estaAssustado() { return this.estaAssustado; }
    public boolean foiComido() { return this.foiComido; }

}