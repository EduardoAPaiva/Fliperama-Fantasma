package Auxiliar;

import java.io.Serializable;

public class Posicao implements Serializable {
    private int linha;
    private int coluna;
    
    private int linhaAnterior;
    private int colunaAnterior;

    public Posicao(int coluna, int linha) {
        this.linha = linha;
        this.coluna = coluna;
    }
    
    public Posicao(int linha, int coluna, int MUNDO_TAMANHO) {
        this.setPosicao(linha, coluna, MUNDO_TAMANHO);
    }

    public boolean volta(int MUNDO_TAMANHO){
        return this.setPosicao(linhaAnterior, colunaAnterior, MUNDO_TAMANHO);
    }

    public boolean setPosicao(int linha, int coluna, int MUNDO_TAMANHO) {
        if (linha < 0 || linha >= MUNDO_TAMANHO)
            return false;
        linhaAnterior = this.linha;
        this.linha = linha;

        if (coluna < 0 || coluna >= MUNDO_TAMANHO)
            return false;
        colunaAnterior = this.coluna;
        this.coluna = coluna;

        return true;
    }
    public void setPosicao(int x, int y) {
        this.coluna = x;
        this.linha = y;
    }
    
    public void proximaPosicao(int velx, int vely){
        linha = linha + vely;
        coluna = coluna + velx;
    }

    public void setPosicaoAnterior(int linhaAnterior, int colunaAnterior){
        this.linhaAnterior = linhaAnterior;
        this.colunaAnterior = colunaAnterior;
    }

    public int getLinha() {
        return linha;
    }

    public void setLinha(int linha){
        this.linha = linha;
    }

    public void setColuna(int coluna){
        this.coluna = coluna;
    }

    public int getColuna() {
        return coluna;
    }

    public int getLinhaAnterior() {
        return linhaAnterior;
    }

    public int getColunaAnterior() {
        return colunaAnterior;
    }

    public boolean igual(Posicao posicao) {
        return (linha == posicao.getLinha() && coluna == posicao.getColuna());
    }

    public boolean moveUp(int MUNDO_TAMANHO) {
        return this.setPosicao(this.getLinha() - 1, this.getColuna(), MUNDO_TAMANHO);
    }

    public boolean moveDown(int MUNDO_TAMANHO) {
        return this.setPosicao(this.getLinha() + 1, this.getColuna(), MUNDO_TAMANHO);
    }

    public boolean moveRight(int MUNDO_TAMANHO) {
        return this.setPosicao(this.getLinha(), this.getColuna() + 1, MUNDO_TAMANHO);
    }

    public boolean moveLeft(int MUNDO_TAMANHO) {
        return this.setPosicao(this.getLinha(), this.getColuna() - 1, MUNDO_TAMANHO);
    }
    
}