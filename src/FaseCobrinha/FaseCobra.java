package FaseCobrinha;

import Auxiliar.Consts;
import Auxiliar.Desenho;
import Controler.Fase;
import Controler.Mensagem;
import Controler.Texto;
import Modelo.Personagem;
import java.awt.Graphics;
import java.io.File;
import java.util.ArrayList;

public class FaseCobra extends Fase{

    public int pontuacao = 0;
    protected ArrayList<Mensagem> Printaveis = new ArrayList<Mensagem>();
    public int tamanhoCobra = 1;
    public Cobra cobra;
    public Comida comida;

    public FaseCobra(String sNomeImagePNG, int linha, int coluna, String NomeImgFundo){
        super(sNomeImagePNG, linha, coluna, NomeImgFundo);

        nomeMusica = "temaCobrinha.wav";

        ConstMundoTamanho = 8;
        ConstCellSide = Consts.JANELA / ConstMundoTamanho;
        ConstPeriod = 200;

        FasePath = "FaseCobra" + File.separator;

        bordaDir = Desenho.retornaImagemTamanhoCustomizado(FasePath + "bordaDireita.jpg", Consts.CONSTANTE_HORIZONTAL, Consts.JANELA);
        bordaEsq = Desenho.retornaImagemTamanhoCustomizado(FasePath + "bordaEsquerda.jpg", Consts.CONSTANTE_HORIZONTAL, Consts.JANELA);

        cobra = new Cobra("", FasePath + "cobra_cima.png", FasePath + "cobra_baixo.png", FasePath + "cobra_dir.png", 1, ConstMundoTamanho/2, ConstCellSide, ConstMundoTamanho);
        cobra.getCabeca().setDirecaoAtual(Consts.PARADO);

        comida = new Comida(FasePath + "comida.png", 0, 0, ConstCellSide, ConstMundoTamanho);
        comida.definePosicao(cobra, ConstMundoTamanho);

        atualizaPontuacao();
    }

    @Override
    public void desenhaTudo(Graphics g){
        cj.desenhaTudo(g, transformaArray(), Printaveis, contador, ConstCellSide, ConstPeriod);
        printaPontuacao(g);
        printaBordas(g);
    }

    @Override
    public void moveUp(){
        cobra.moveUp(ConstMundoTamanho);
    }

    @Override
    public void moveDown(){
        cobra.moveDown(ConstMundoTamanho);
    }

    @Override
    public void moveLeft(){
        cobra.moveLeft(ConstMundoTamanho);
    }

    @Override
    public void moveRight(){
        cobra.moveRight(ConstMundoTamanho);
    }

    public ArrayList<Personagem> transformaArray(){
        ArrayList<Personagem> tudo = new ArrayList<>();
        tudo.add(cobra.getCabeca());
        ArrayList<Personagem> corpo = cobra.getCorpo();

        if(corpo != null){
            for(int i = 0; i<corpo.size(); i++){
                tudo.add(corpo.get(i));
            }
        }

        tudo.add(comida);

        return tudo;
    }

    @Override
    public boolean processaTudo(){

        if(contador % ConstCellSide != 0){
            return false;
        }
        
        boolean posicaoValida = true;


        if(cobra.getDirecao() == Consts.CIMA && cobra.getDirecaoAtual() != Consts.BAIXO){
            cobra.setDirecaoAtual();
        }
        else if(cobra.getDirecao() == Consts.BAIXO && cobra.getDirecaoAtual() != Consts.CIMA){
            cobra.setDirecaoAtual();
        }
        else if(cobra.getDirecao() == Consts.ESQUERDA && cobra.getDirecaoAtual() != Consts.DIREITA){
            cobra.setDirecaoAtual();
        }
        else if(cobra.getDirecao() == Consts.DIREITA && cobra.getDirecaoAtual() != Consts.ESQUERDA){
            cobra.setDirecaoAtual();
        }
        else{
            cobra.atualizaDirecao();
        }
        

        if(cobra.getDirecaoAtual() == Consts.CIMA){
            posicaoValida = cobra.moveUp(ConstMundoTamanho);
        }
        else if(cobra.getDirecaoAtual() == Consts.BAIXO){
            posicaoValida = cobra.moveDown(ConstMundoTamanho);
        }
        else if(cobra.getDirecaoAtual() == Consts.ESQUERDA){
            posicaoValida = cobra.moveLeft(ConstMundoTamanho);
        }
        else if(cobra.getDirecaoAtual() == Consts.DIREITA){
            posicaoValida = cobra.moveRight(ConstMundoTamanho);
        }


        if(posicaoValida == false){
            /*cobra.avancaCabeca();
            cobra.zeraDirecao();
            cobra.getCabeca().setDirecaoAtual(Consts.PARADO);
            Mensagem msg = new Mensagem(true);
            msg.defineImagem(FasePath + "morte.png", ConstCellSide, ConstMundoTamanho);
            Printaveis.add(msg);
            morto = true;*/
            if(pontuacao < 100){
                setFaseVencida(false);
                setGoodEnding(false);
            }
            return true;
        }


        if(cobra.cabecaBateu()){
           /*  cobra.zeraDirecao();
            cobra.retornaCabeca();
            Mensagem msg = new Mensagem(true);
            msg.defineImagem(FasePath + "morte.png", ConstCellSide, ConstMundoTamanho);
            Printaveis.add(msg);
            morto = true;*/
            if(pontuacao < 100){
                setFaseVencida(false);
                setGoodEnding(false);
            }
            return true;
        }



        if(cobra.getCabeca().getPosicao().getColuna() == comida.getPosicao().getColuna() && cobra.getCabeca().getPosicao().getLinha() == comida.getPosicao().getLinha()){
            tamanhoCobra++;

            if(tamanhoCobra == ConstMundoTamanho * ConstMundoTamanho)
                return true;

            cobra.AdicionaCorpo(FasePath, ConstCellSide, ConstMundoTamanho);
            cobra.atualizaCorpo();
            comida.definePosicao(cobra, ConstMundoTamanho);
            pontuacao += 1 + tamanhoCobra/5;
            atualizaPontuacao();
        }
        else{
            cobra.atualizaCorpo();
        }

        return false;
    }

    @Override
    public void setDirecao(int direcao){
        cobra.setDirecao(direcao);
    }

    @Override
    public void reiniciaFase(){
        cobra = new Cobra("", FasePath + "cobra_cima.png", FasePath + "cobra_baixo.png", FasePath + "cobra_dir.png", 1, ConstMundoTamanho/2, ConstCellSide, ConstMundoTamanho);

        comida.definePosicao(cobra, ConstMundoTamanho);

        tamanhoCobra = 1;
        Printaveis.clear();

        morto = false;
    }

    public void atualizaPontuacao(){
        String strPontuacao = "" + pontuacao;

        if(strPontuacao.length() > ArrayPontuacao.size()){
            ArrayPontuacao.add(new Texto(true, 0, 0));
        }

        for(int i = strPontuacao.length() - 1; i>=0; i--){
            Texto algarismoAtual = (Texto)ArrayPontuacao.get(strPontuacao.length() - 1 - i);
            algarismoAtual.defineAlgarismo(strPontuacao.charAt(i) - '0', Consts.TAMANHO_PONTUACAO);
        }

    }

    @Override
    public void recuperarTransient(){
        cobra.recuperarTransient("", FasePath + "cobra_cima.png", FasePath + "cobra_baixo.png", FasePath + "cobra_dir.png", FasePath + "cauda.png",ConstCellSide);
        fundoPreto = Desenho.retornaImagemTamanhoCustomizado("preto.jpg", Consts.pixelsLargura, Consts.pixelsAltura);
        comida.setImage(Desenho.retornaImagem(FasePath+"comida.png", ConstCellSide));
        ArrayPontuacao = new ArrayList<>();
        faseAtual = new ArrayList<>();
        atualizaPontuacao();
        bordaDir = Desenho.retornaImagemTamanhoCustomizado(FasePath + "bordaDireita.jpg", Consts.CONSTANTE_HORIZONTAL, Consts.JANELA);
        bordaEsq = Desenho.retornaImagemTamanhoCustomizado(FasePath + "bordaEsquerda.jpg", Consts.CONSTANTE_HORIZONTAL, Consts.JANELA);
     
        for(Mensagem m: Printaveis)
            m.defineImagem(FasePath + "morte.png", ConstCellSide, ConstMundoTamanho);
        
    }

    public void adicionarPersonagem(Personagem p, int x, int y){
        if(p instanceof Comida){
            comida.getPosicao().setPosicao((x-Consts.CONSTANTE_HORIZONTAL)/ConstCellSide, y/ConstCellSide);
        }
    }

}