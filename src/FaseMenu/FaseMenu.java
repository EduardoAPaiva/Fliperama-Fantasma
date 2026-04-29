package FaseMenu;

import java.io.File;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import java.awt.Graphics;

import Auxiliar.Consts;
import Auxiliar.Desenho;
import Controler.Fase;
import Controler.Mensagem;

public class FaseMenu extends Fase {

    private transient ImageIcon Creditos;
    private boolean creditosAtivos = false;
    private boolean enterPressionado = false;
    private int fundoAtual = 0;
    private transient Mensagem fundo = new Mensagem(true);
    private transient Mensagem fliperama = new Mensagem(true);
    private int botaoAtual = 0;
    private Botao iniciarJogo;
    private Botao creditos;
    private transient ArrayList<Mensagem> prints = new ArrayList<Mensagem>();

    public FaseMenu(String sNomeImagePNG, int linha, int coluna, String NomeImgFundo){
        super(sNomeImagePNG, linha, coluna, NomeImgFundo);

        bordaDir = Desenho.retornaImagemTamanhoCustomizado("madeira.jpg", Consts.CONSTANTE_HORIZONTAL, Consts.JANELA);
        bordaEsq = Desenho.retornaImagemTamanhoCustomizado("madeira.jpg", Consts.CONSTANTE_HORIZONTAL, Consts.JANELA);

        ConstPeriod = 100;

        nomeMusica = "temaMenu.wav";
        volume = 0.2f;

        FasePath = "FaseMenu" + File.separator;

        Creditos = Desenho.retornaImagem(FasePath + "creditos.png", Consts.JANELA);

        fundo.defineImagem(FasePath + "fundo.png", Consts.JANELA, 1);
        fliperama.defineImagem(FasePath + "fliperama.png", Consts.JANELA, 1);

        prints.add(fundo);
        prints.add(fliperama);

        iniciarJogo = new Botao(FasePath + "iniciarJogoNaoSelecionado.png", FasePath + "iniciarJogoSelecionado.png", 186 * Consts.JANELA/560, 250 * Consts.JANELA/560, 180 * Consts.JANELA/560, 40 * Consts.JANELA/560);
        creditos = new Botao(FasePath + "creditosNaoSelecionado.png", FasePath + "creditosSelecionado.png", 186 * Consts.JANELA/560, 330 * Consts.JANELA/560, 180 * Consts.JANELA/560, 40 * Consts.JANELA/560);

        iniciarMusica();
        atualizaBotoes();
    }

    @Override
    public void desenhaTudo(Graphics g){
        if(creditosAtivos){
            Desenho.desenhar(g, Creditos, 0, 0, true, 0);
        }
        else{
            cj.desenhaTudo(g, null, prints, contador, 0, ConstPeriod);
            iniciarJogo.desenhaBotao(g);
            creditos.desenhaBotao(g);
        }
        printaBordas(g);
    }

    @Override
    public boolean processaTudo() {

        if(enterPressionado == true && botaoAtual == 0){
            enterPressionado = false;
            return true;
        }
        else if(enterPressionado == true && botaoAtual == 1){
            enterPressionado = false;
            creditosAtivos = true;
        }

        enterPressionado = false;

        trocaFundo();
        atualizaBotoes();
        return false;
    }

    @Override
    public void moveUp() {
        if(botaoAtual != 0){
            botaoAtual--;
        }
    }

    @Override
    public void moveDown() {
        if(botaoAtual != 1){
            botaoAtual++;
        }
    }

    public void setDirecao(int direcao){
        if(direcao == Consts.CIMA && botaoAtual != 0){
            botaoAtual--;
        }
        else if(direcao == Consts.BAIXO && botaoAtual != 1){
            botaoAtual++;
        }
    }

    @Override
    public void moveLeft() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'moveLeft'");
    }

    @Override
    public void moveRight() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'moveRight'");
    }

    @Override
    public void reiniciaFase() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'reiniciaFase'");
    }

    public void atualizaBotoes(){
        if(botaoAtual == 0){
            iniciarJogo.setEstado(1);
            creditos.setEstado(0);
        }
        if(botaoAtual == 1){
            iniciarJogo.setEstado(0);
            creditos.setEstado(1);
        }
    }

    public void trocaFundo(){
        if(fundoAtual == 0){
            fundoAtual = 1;
            fundo.defineImagem(FasePath + "fundo2.png", Consts.JANELA,1);
        }
        else if(fundoAtual == 1){
            fundoAtual = 0;
            fundo.defineImagem(FasePath + "fundo.png", Consts.JANELA,1);
        }
    }

    @Override
    public void enterPressionado(){
        enterPressionado = true;
    }

    @Override
    public void ESCPressionado(){
        creditosAtivos = false;
    }

    @Override
    public void recuperarTransient(){
        iniciarJogo.recuperarTransient(FasePath + "iniciarJogoNaoSelecionado.png", FasePath + "iniciarJogoSelecionado.png");
        creditos.recuperarTransient(FasePath + "creditosNaoSelecionado.png", FasePath + "creditosSelecionado.png");
        prints = new ArrayList<>();
        fundo = new Mensagem(true);
        fliperama = new Mensagem(true);
        fundo.defineImagem(FasePath + "fundo.png", Consts.JANELA, 1);
        fliperama.defineImagem(FasePath + "fliperama.png", Consts.JANELA, 1);
        prints.add(fundo);
        prints.add(fliperama);
        bordaDir = Desenho.retornaImagemTamanhoCustomizado("madeira.jpg", Consts.CONSTANTE_HORIZONTAL, Consts.JANELA);
        bordaEsq = Desenho.retornaImagemTamanhoCustomizado("madeira.jpg", Consts.CONSTANTE_HORIZONTAL, Consts.JANELA);
        Creditos = Desenho.retornaImagem(FasePath + "creditos.png", Consts.JANELA);
        fundoPreto = Desenho.retornaImagemTamanhoCustomizado("preto.jpg", Consts.pixelsLargura, Consts.pixelsAltura);
    }

}
