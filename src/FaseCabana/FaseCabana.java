package FaseCabana;

import Controler.Fase;
import java.awt.Graphics;
import java.io.File;
import java.util.Random;

import javax.swing.ImageIcon;

import Auxiliar.Consts;
import Auxiliar.Desenho;

public class FaseCabana extends Fase{

    private Crianca crianca;

    private int[][] matrizCenario;

    private transient ImageIcon fliperamaFrenteCima;
    private transient ImageIcon fliperamaFrenteBaixo;
    private transient ImageIcon fliperamaCimaChiando1;
    private transient ImageIcon fliperamaBaixoChiando1;
    private transient ImageIcon fliperamaCimaChiando2;
    private transient ImageIcon fliperamaBaixoChiando2;
    private transient ImageIcon fliperamaLadoCima;
    private transient ImageIcon fliperamaLadoBaixo;
    private transient ImageIcon fliperamaLadoEsqCima;
    private transient ImageIcon fliperamaLadoEsqBaixo;
    private transient ImageIcon caixa;
    private transient ImageIcon cantoSuperiorDireito;
    private transient ImageIcon cantoSuperiorEsquerdo;
    private transient ImageIcon cantoInferiorDireito;
    private transient ImageIcon cantoInferiorEsquerdo;
    private transient ImageIcon paredeMeioVertical;
    private transient ImageIcon paredeMeioHorizontal;
    private transient ImageIcon paredeFrontalCima;
    private transient ImageIcon paredeFrontalBaixo;
    private transient ImageIcon papel;
    private transient ImageIcon pergaminhoVermelho;
    private transient ImageIcon pergaminhoAzul;
    private transient ImageIcon pergaminhoAmarelo;
    private transient ImageIcon pergaminhoBranco;

    private transient ImageIcon cabos;
    private transient ImageIcon caboVermelhoCortado;
    private transient ImageIcon caboAzulCortado;
    private transient ImageIcon caboAmareloCortado;
    private transient ImageIcon caboBrancoCortado;

    private transient ImageIcon fantasmaFalando1;
    private transient ImageIcon fantasmaFalando2;
    private int imagemFantasmaAtual = 0;

    private transient ImageIcon pergaminhoEscolhido;

    private int numeroPergaminhoEscolhido;
    private boolean cortouCerto = false;
    private int tempoAcertouCabo = 150;
    private int contadorAcertouCabo = 150;
    private int contadorErrouCabo = 30;
    private boolean faseCabos = false;
    private int caboCortado = -1;

    private Random random = new Random();

    private int imagemAtual = 0;

    public FaseCabana(String sNomeImagePNG, int linha, int coluna, String NomeImgFundo){
        super(sNomeImagePNG, linha, coluna, NomeImgFundo);

        nomeMusica = "temaCabana.wav";

        ConstMundoTamanho = 16;
        ConstCellSide = Consts.JANELA / ConstMundoTamanho;
        ConstPeriod = 150;

        FasePath = "FaseCabana" + File.separator;

        bordaDir = Desenho.retornaImagemTamanhoCustomizado(FasePath + "bordaDireita.jpg", Consts.CONSTANTE_HORIZONTAL, Consts.JANELA);
        bordaEsq = Desenho.retornaImagemTamanhoCustomizado(FasePath + "bordaEsquerda.jpg", Consts.CONSTANTE_HORIZONTAL, Consts.JANELA);

        crianca = new Crianca(FasePath + "heroi_baixo_parado.png",FasePath + "heroi_cima_parado.png", FasePath + "heroi_baixo_parado.png",FasePath + "heroi_cima_movendo.png",FasePath + "heroi_baixo_movendo.png", FasePath + "heroi_lado_movendo1.png",FasePath + "heroi_lado_movendo2.png",FasePath + "heroi_lado_parado.png",linha, coluna, ConstCellSide, ConstMundoTamanho);
        crianca.setLadoVirado(Consts.CIMA);

        fliperamaFrenteCima = Desenho.retornaImagem(FasePath + "fliperama_frente_cima.png", ConstCellSide);
        fliperamaFrenteBaixo = Desenho.retornaImagem(FasePath + "fliperama_frente_baixo.png", ConstCellSide);
        fliperamaCimaChiando1 = Desenho.retornaImagem(FasePath + "fliperamaCimaChiando1.png", ConstCellSide);
        fliperamaBaixoChiando1 = Desenho.retornaImagem(FasePath + "fliperamaBaixoChiando1.png", ConstCellSide);
        fliperamaCimaChiando2 = Desenho.retornaImagem(FasePath + "fliperamaCimaChiando2.png", ConstCellSide);
        fliperamaBaixoChiando2 = Desenho.retornaImagem(FasePath + "fliperamaBaixoChiando2.png", ConstCellSide);
        fliperamaLadoCima = Desenho.retornaImagem(FasePath + "fliperama_lado_cima.png", ConstCellSide);
        fliperamaLadoBaixo = Desenho.retornaImagem(FasePath + "fliperama_lado_baixo.png", ConstCellSide);
        fliperamaLadoEsqCima = Desenho.retornaImagemEspelhadaHorizontal(FasePath + "fliperama_lado_cima.png", ConstCellSide);
        fliperamaLadoEsqBaixo = Desenho.retornaImagemEspelhadaHorizontal(FasePath + "fliperama_lado_baixo.png", ConstCellSide);
        caixa = Desenho.retornaImagem(FasePath + "caixa.jpg", ConstCellSide);
        cantoSuperiorDireito = Desenho.retornaImagem(FasePath + "ParedeCantoSuperiorDireito.png", ConstCellSide);
        cantoSuperiorEsquerdo = Desenho.retornaImagemEspelhadaHorizontal(FasePath + "ParedeCantoSuperiorDireito.png", ConstCellSide);
        cantoInferiorDireito = Desenho.retornaImagem(FasePath + "ParedeCantoInferiorDireito.png", ConstCellSide);
        cantoInferiorEsquerdo = Desenho.retornaImagemEspelhadaHorizontal(FasePath + "ParedeCantoInferiorDireito.png", ConstCellSide);
        paredeMeioVertical = Desenho.retornaImagemEspelhadaHorizontal(FasePath + "ParedeMeioVertical.png", ConstCellSide);
        paredeMeioHorizontal = Desenho.retornaImagemEspelhadaHorizontal(FasePath + "ParedeMeioHorizontal.png", ConstCellSide);
        paredeFrontalCima = Desenho.retornaImagemEspelhadaHorizontal(FasePath + "ParedeFrontalCima.png", ConstCellSide);
        paredeFrontalBaixo = Desenho.retornaImagemEspelhadaHorizontal(FasePath + "ParedeFrontalBaixo.png", ConstCellSide);
        papel = Desenho.retornaImagem(FasePath + "papel.png", ConstCellSide);
        pergaminhoVermelho = Desenho.retornaImagem(FasePath + "pergaminhoVermelho.png", Consts.JANELA);
        pergaminhoAzul = Desenho.retornaImagem(FasePath + "pergaminhoAzul.png", Consts.JANELA);
        pergaminhoAmarelo = Desenho.retornaImagem(FasePath + "pergaminhoAmarelo.png", Consts.JANELA);
        pergaminhoBranco = Desenho.retornaImagem(FasePath + "pergaminhoBranco.png", Consts.JANELA);

        cabos = Desenho.retornaImagem(FasePath + "cabos.png", Consts.JANELA);
        caboVermelhoCortado = Desenho.retornaImagem(FasePath + "caboVermelhoCortado.png", Consts.JANELA);
        caboAzulCortado = Desenho.retornaImagem(FasePath + "caboAzulCortado.png", Consts.JANELA);
        caboAmareloCortado = Desenho.retornaImagem(FasePath + "caboAmareloCortado.png", Consts.JANELA);
        caboBrancoCortado = Desenho.retornaImagem(FasePath + "caboBrancoCortado.png", Consts.JANELA);

        fantasmaFalando1 = Desenho.retornaImagem(FasePath + "fliperama_fantasma1.png", Consts.JANELA);
        fantasmaFalando2 = Desenho.retornaImagem(FasePath + "fliperama_fantasma2.png", Consts.JANELA);

        aleatorizaPergaminho();

        criaCenario();

    }

    @Override
    public void desenhaTudo(Graphics g){

        if(cortouCerto == false && contadorErrouCabo < 20){
            printaFundoPreto(g);
            return;
        }

        if(cortouCerto == true && contadorAcertouCabo < tempoAcertouCabo){
            bordaDir = Desenho.retornaImagemTamanhoCustomizado("madeira.jpg", Consts.CONSTANTE_HORIZONTAL, Consts.JANELA);
            bordaEsq = Desenho.retornaImagemTamanhoCustomizado("madeira.jpg", Consts.CONSTANTE_HORIZONTAL, Consts.JANELA);
            
            if(imagemFantasmaAtual == 0)
                Desenho.desenharPorPixel(g, fantasmaFalando1, 0, 0);
            else
                Desenho.desenharPorPixel(g, fantasmaFalando2, 0, 0);

            printaBordas(g);
            return;
        }

        for(int i = 0; i<ConstMundoTamanho; i++){
            for(int j = 0; j<ConstMundoTamanho; j++){
                if(matrizCenario[i][j] == 1)
                    Desenho.desenhar(g, fliperamaFrenteCima, j, i, false, ConstCellSide);
                else if(matrizCenario[i][j] == 2)
                    Desenho.desenhar(g, fliperamaFrenteBaixo, j, i, false, ConstCellSide);
                else if(matrizCenario[i][j] == 3)
                    Desenho.desenhar(g, fliperamaLadoCima, j, i, false, ConstCellSide);
                else if(matrizCenario[i][j] == 4)
                    Desenho.desenhar(g, fliperamaLadoBaixo, j, i, false, ConstCellSide);
                else if(matrizCenario[i][j] == 5){
                    Desenho.desenhar(g, fliperamaLadoBaixo, j, i, false, ConstCellSide);
                    Desenho.desenhar(g, fliperamaLadoCima, j, i, false, ConstCellSide);
                }
                else if(matrizCenario[i][j] == 6)
                    Desenho.desenhar(g, caixa, j, i, false, ConstCellSide);
                else if(matrizCenario[i][j] == 7)
                    Desenho.desenhar(g, cantoSuperiorDireito, j, i, false, ConstCellSide);
                else if(matrizCenario[i][j] == 8)
                    Desenho.desenhar(g, cantoSuperiorEsquerdo, j, i, false, ConstCellSide);
                else if(matrizCenario[i][j] == 9)
                    Desenho.desenhar(g, paredeMeioVertical, j, i, false, ConstCellSide);
                else if(matrizCenario[i][j] == 10)
                    Desenho.desenhar(g, paredeMeioHorizontal, j, i, false, ConstCellSide);
                else if(matrizCenario[i][j] == 11)
                    Desenho.desenhar(g, paredeFrontalCima, j, i, false, ConstCellSide);
                else if(matrizCenario[i][j] == 12)
                    Desenho.desenhar(g, paredeFrontalBaixo, j, i, false, ConstCellSide);
                else if(matrizCenario[i][j] == 13){
                    Desenho.desenhar(g, paredeFrontalBaixo, j, i, false, ConstCellSide);
                    Desenho.desenhar(g, fliperamaFrenteCima, j, i, false, ConstCellSide);
                }
                else if(matrizCenario[i][j] == 14){
                    Desenho.desenhar(g, paredeFrontalBaixo, j, i, false, ConstCellSide);
                    Desenho.desenhar(g, fliperamaLadoCima, j, i, false, ConstCellSide);
                }
                else if(matrizCenario[i][j] == 15)
                    Desenho.desenhar(g, cantoInferiorEsquerdo, j, i, false, ConstCellSide);
                else if(matrizCenario[i][j] == 16)
                    Desenho.desenhar(g, cantoInferiorDireito, j, i, false, ConstCellSide);
                else if(matrizCenario[i][j] == 17){
                    Desenho.desenhar(g, paredeFrontalBaixo, j, i, false, ConstCellSide);
                    Desenho.desenhar(g, fliperamaLadoEsqCima, j, i, false, ConstCellSide);
                }
                else if(matrizCenario[i][j] == 18){
                    Desenho.desenhar(g, fliperamaLadoEsqBaixo, j, i, false, ConstCellSide);
                    Desenho.desenhar(g, fliperamaLadoEsqCima, j, i, false, ConstCellSide);
                }
                else if(matrizCenario[i][j] == 19)
                    Desenho.desenhar(g, fliperamaLadoEsqBaixo, j, i, false, ConstCellSide);
                else if(matrizCenario[i][j] == 20){
                    Desenho.desenhar(g, paredeFrontalBaixo, j, i, false, ConstCellSide);
                    if(imagemAtual == 0){
                        Desenho.desenhar(g, fliperamaCimaChiando1, j, i, false, ConstCellSide);
                    }
                    else{
                        Desenho.desenhar(g, fliperamaCimaChiando2, j, i, false, ConstCellSide);                        
                    }
                }
                else if(matrizCenario[i][j] == 21){
                    if(imagemAtual == 0){
                        Desenho.desenhar(g, fliperamaBaixoChiando1, j, i, false, ConstCellSide);
                    }
                    else{
                        Desenho.desenhar(g, fliperamaBaixoChiando2, j, i, false, ConstCellSide);                        
                    }
                }
                else if(matrizCenario[i][j] == 90)
                    Desenho.desenhar(g, papel, j, i, false, ConstCellSide);
            }
        }
        crianca.autoDesenho(g, contador, ConstCellSide, ConstPeriod);

        if(crianca.getPosicao().getLinha() == 7 && crianca.getPosicao().getColuna() == 1)
            Desenho.desenharPorPixel(g, pergaminhoEscolhido, 0, 0);

        if(crianca.getPosicao().getLinha() == 4 && crianca.getPosicao().getColuna() == 12 && crianca.getLadoVirado() == Consts.CIMA){
            Desenho.desenharPorPixel(g, cabos, 0, 0);
            faseCabos = true;
        }
        else{
            faseCabos = false;
        }

        printaBordas(g);

    }

    @Override
    public void moveUp(){
        crianca.setLadoVirado(Consts.CIMA);
        if(estaOcupado()){
            crianca.ficaParado();
        }
        else{
            crianca.mudaImagemCima();
            crianca.moveUp(ConstMundoTamanho);
        }
    }

    @Override
    public void moveDown(){
        crianca.setLadoVirado(Consts.BAIXO);
        if(estaOcupado()){
            crianca.ficaParado();
        }
        else{
            crianca.mudaImagemBaixo();
            crianca.moveDown(ConstMundoTamanho);
        }
    }

    @Override
    public void moveLeft(){
        crianca.setLadoVirado(Consts.ESQUERDA);
        if(estaOcupado()){
            crianca.ficaParado();
        }
        else{
            crianca.mudaImagemEsquerda();
            crianca.moveLeft(ConstMundoTamanho);
        }
    }

    @Override
    public void moveRight(){
        crianca.setLadoVirado(Consts.DIREITA);
        if(estaOcupado()){
            crianca.ficaParado();
        }
        else{
            crianca.mudaImagemDireita();
            crianca.moveRight(ConstMundoTamanho);
        }
    }

    @Override
    public boolean processaTudo(){

        if(imagemFantasmaAtual == 0)
            imagemFantasmaAtual = 1;
        else
            imagemFantasmaAtual = 0;

        if(caboCortado != -1){
            if(cortouCerto == true)
                contadorAcertouCabo--;
            else
                contadorErrouCabo--;
        }

        if(contadorErrouCabo == 19 && cortouCerto == false){
            pararMusica();
            tocarSom("bomba.wav");
        }

        if(contadorAcertouCabo == tempoAcertouCabo - 1 && cortouCerto == true){
            pararMusica();
            tocarSom("rapFantasma.wav");
        }

        if(contadorAcertouCabo == 0){
            pararSom();
            return true;
        }
        
        if(contadorErrouCabo == 0)
            reiniciaFase();


        if(imagemAtual == 0)
            imagemAtual = 1;
        else 
            imagemAtual = 0;
        

        if(crianca.getDirecao() == 1){
            crianca.setDirecaoAtual(crianca.getDirecao());
        }
        else if(crianca.getDirecao() == 2){
            crianca.setDirecaoAtual(crianca.getDirecao());
        }
        else if(crianca.getDirecao() == 3){
            crianca.setDirecaoAtual(crianca.getDirecao());
        }
        else if(crianca.getDirecao() == 4){
            crianca.setDirecaoAtual(crianca.getDirecao());
        }
        else if(crianca.getDirecao()==0)
            crianca.setDirecaoAtual(crianca.getDirecao());


        crianca.setDirecao(0);


        if(crianca.getDirecaoAtual() == 1){
            moveUp();
        }
        else if(crianca.getDirecaoAtual() == 2){
            moveDown();
        }
        else if(crianca.getDirecaoAtual() == 3){
            moveLeft();
        }
        else if(crianca.getDirecaoAtual() == 4){
            moveRight();
        }
        else if(crianca.getDirecaoAtual() == 0){
            crianca.ficaParado();
        }

        return false;
    }

    @Override
    public void setDirecao(int direcao){
        crianca.setDirecao(direcao);
    }

    @Override
    public void reiniciaFase(){
        iniciarMusica();

        crianca = new Crianca(FasePath + "heroi_baixo_parado.png",FasePath + "heroi_cima_parado.png", FasePath + "heroi_baixo_parado.png",FasePath + "heroi_cima_movendo.png",FasePath + "heroi_baixo_movendo.png", FasePath + "heroi_lado_movendo1.png",FasePath + "heroi_lado_movendo2.png",FasePath + "heroi_lado_parado.png",14, 8, ConstCellSide, ConstMundoTamanho);
        crianca.setLadoVirado(Consts.CIMA);

        cortouCerto = false;
        contadorAcertouCabo = tempoAcertouCabo;
        contadorErrouCabo = 30;
        faseCabos = false;
        caboCortado = -1;

        cabos = Desenho.retornaImagem(FasePath + "cabos.png", Consts.JANELA);
        aleatorizaPergaminho();

    }

    public void criaCenario(){

        int aux[][] = {{ 8,10,10,10,10,10,10,10,10,10,10,10,10,10,10, 7},
                       { 9,11,11,11,11,11,11,11,11,11,11,11,11,11,11, 9},
                       { 9,17,13,13,13,13,13,13,13,13,13,13,20,13,14, 9},
                       { 9,18, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2,21, 2, 5, 9},
                       { 9,18,99,99, 0, 0, 0, 0,99, 0, 0,99, 0, 0, 5, 9},
                       { 9,18, 0, 0, 0,99,99, 0, 0, 0,99,99,99, 0, 5, 9},
                       { 9,19, 0,99, 0,99,99, 0,99,99, 0, 0, 0, 0, 4, 9},
                       { 9,90, 0,99, 0,99,99, 0,99,99, 0,99,99,99,99, 9},
                       { 9,99,99, 0, 0,99,99, 0,99, 0, 0, 0, 0, 0, 0, 9},
                       { 9, 0, 0, 0,99,99, 0, 0,99,99,99,99, 0,99, 0, 9},
                       { 9, 0,99,99, 0, 0, 0,99,99,99, 0, 0, 0,99, 0, 9},
                       { 9, 0,99, 0, 0,99, 0,99, 0, 0, 0,99,99, 0, 0, 9},
                       { 9, 0, 0, 0,99, 0, 0,99, 0,99, 0,99,99,99, 0, 9},
                       { 9, 0,99,99,99,99, 0,99, 0,99, 0,99, 0, 0, 0, 9},
                       { 9,99, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 9},
                       {15,10,10,10,10,10,10,10,10,10,10,10,10,10,10,16}};

        matrizCenario = aux;

    }

    public boolean estaOcupado(){
        int linha = crianca.getPosicao().getLinha();
        int coluna = crianca.getPosicao().getColuna();

        if(crianca.getDirecaoAtual() == Consts.BAIXO)
            linha += 1;
        else if(crianca.getDirecaoAtual() == Consts.CIMA)
            linha -= 1;
        else if(crianca.getDirecaoAtual() == Consts.ESQUERDA)
            coluna -= 1;
        else if(crianca.getDirecaoAtual() == Consts.DIREITA)
            coluna += 1;

        if(linha<0 || linha>=ConstMundoTamanho || coluna<0 || coluna>=ConstMundoTamanho)
            return true;
        
        int objetoAtual = matrizCenario[linha][coluna];

        if(objetoAtual == 0 || objetoAtual == 90)
            return false;

        return true;

    }

    @Override
    public void recuperarTransient(){
        bordaDir = Desenho.retornaImagemTamanhoCustomizado(FasePath+ "bordaDireita.jpg", Consts.CONSTANTE_HORIZONTAL, Consts.JANELA);
        bordaEsq = Desenho.retornaImagemTamanhoCustomizado(FasePath + "bordaEsquerda.jpg", Consts.CONSTANTE_HORIZONTAL, Consts.JANELA);
        fundoPreto = Desenho.retornaImagemTamanhoCustomizado("preto.jpg", Consts.pixelsLargura, Consts.pixelsAltura);

        fliperamaFrenteCima = Desenho.retornaImagem(FasePath + "fliperama_frente_cima.png", ConstCellSide);
        fliperamaFrenteBaixo = Desenho.retornaImagem(FasePath + "fliperama_frente_baixo.png", ConstCellSide);
        fliperamaCimaChiando1 = Desenho.retornaImagem(FasePath + "fliperamaCimaChiando1.png", ConstCellSide);
        fliperamaBaixoChiando1 = Desenho.retornaImagem(FasePath + "fliperamaBaixoChiando1.png", ConstCellSide);
        fliperamaCimaChiando2 = Desenho.retornaImagem(FasePath + "fliperamaCimaChiando2.png", ConstCellSide);
        fliperamaBaixoChiando2 = Desenho.retornaImagem(FasePath + "fliperamaBaixoChiando2.png", ConstCellSide);
        fliperamaLadoCima = Desenho.retornaImagem(FasePath + "fliperama_lado_cima.png", ConstCellSide);
        fliperamaLadoBaixo = Desenho.retornaImagem(FasePath + "fliperama_lado_baixo.png", ConstCellSide);
        fliperamaLadoEsqCima = Desenho.retornaImagemEspelhadaHorizontal(FasePath + "fliperama_lado_cima.png", ConstCellSide);
        fliperamaLadoEsqBaixo = Desenho.retornaImagemEspelhadaHorizontal(FasePath + "fliperama_lado_baixo.png", ConstCellSide);
        caixa = Desenho.retornaImagem(FasePath + "caixa.jpg", ConstCellSide);
        cantoSuperiorDireito = Desenho.retornaImagem(FasePath + "ParedeCantoSuperiorDireito.png", ConstCellSide);
        cantoSuperiorEsquerdo = Desenho.retornaImagemEspelhadaHorizontal(FasePath + "ParedeCantoSuperiorDireito.png", ConstCellSide);
        cantoInferiorDireito = Desenho.retornaImagem(FasePath + "ParedeCantoInferiorDireito.png", ConstCellSide);
        cantoInferiorEsquerdo = Desenho.retornaImagemEspelhadaHorizontal(FasePath + "ParedeCantoInferiorDireito.png", ConstCellSide);
        paredeMeioVertical = Desenho.retornaImagemEspelhadaHorizontal(FasePath + "ParedeMeioVertical.png", ConstCellSide);
        paredeMeioHorizontal = Desenho.retornaImagemEspelhadaHorizontal(FasePath + "ParedeMeioHorizontal.png", ConstCellSide);
        paredeFrontalCima = Desenho.retornaImagemEspelhadaHorizontal(FasePath + "ParedeFrontalCima.png", ConstCellSide);
        paredeFrontalBaixo = Desenho.retornaImagemEspelhadaHorizontal(FasePath + "ParedeFrontalBaixo.png", ConstCellSide);
        papel = Desenho.retornaImagem(FasePath + "papel.png", ConstCellSide);
        pergaminhoVermelho = Desenho.retornaImagem(FasePath + "pergaminhoVermelho.png", Consts.JANELA);
        pergaminhoAzul = Desenho.retornaImagem(FasePath + "pergaminhoAzul.png", Consts.JANELA);
        pergaminhoAmarelo = Desenho.retornaImagem(FasePath + "pergaminhoAmarelo.png", Consts.JANELA);
        pergaminhoBranco = Desenho.retornaImagem(FasePath + "pergaminhoBranco.png", Consts.JANELA);

        cabos = Desenho.retornaImagem(FasePath + "cabos.png", Consts.JANELA);
        caboVermelhoCortado = Desenho.retornaImagem(FasePath + "caboVermelhoCortado.png", Consts.JANELA);
        caboAzulCortado = Desenho.retornaImagem(FasePath + "caboAzulCortado.png", Consts.JANELA);
        caboAmareloCortado = Desenho.retornaImagem(FasePath + "caboAmareloCortado.png", Consts.JANELA);
        caboBrancoCortado = Desenho.retornaImagem(FasePath + "caboBrancoCortado.png", Consts.JANELA);

        fantasmaFalando1 = Desenho.retornaImagem(FasePath + "fliperama_fantasma1.png", Consts.JANELA);
        fantasmaFalando2 = Desenho.retornaImagem(FasePath + "fliperama_fantasma2.png", Consts.JANELA);

        if(numeroPergaminhoEscolhido == 0)
            pergaminhoEscolhido = pergaminhoVermelho;
        else if(numeroPergaminhoEscolhido == 1)
            pergaminhoEscolhido = pergaminhoAzul;
        else if(numeroPergaminhoEscolhido == 2)
            pergaminhoEscolhido = pergaminhoAmarelo;
        else if(numeroPergaminhoEscolhido == 3)
            pergaminhoEscolhido = pergaminhoBranco;

        if(caboCortado != -1){
            if(caboCortado == 0)
                cabos = caboVermelhoCortado;
            else if(caboCortado == 1)
                cabos = caboAzulCortado;
            else if(caboCortado == 2)
                cabos = caboAmareloCortado;
            else if(caboCortado == 3)
                cabos = caboBrancoCortado;
        }

        crianca.recuperarTransient(FasePath + "heroi_cima_parado.png", FasePath + "heroi_baixo_parado.png",FasePath + "heroi_cima_movendo.png",FasePath + "heroi_baixo_movendo.png", FasePath + "heroi_lado_movendo1.png",FasePath + "heroi_lado_movendo2.png",FasePath + "heroi_lado_parado.png",ConstCellSide);

    }

    public void aleatorizaPergaminho(){
        numeroPergaminhoEscolhido = random.nextInt(4);
        if(numeroPergaminhoEscolhido == 0)
            pergaminhoEscolhido = pergaminhoVermelho;
        else if(numeroPergaminhoEscolhido == 1)
            pergaminhoEscolhido = pergaminhoAzul;
        else if(numeroPergaminhoEscolhido == 2)
            pergaminhoEscolhido = pergaminhoAmarelo;
        else if(numeroPergaminhoEscolhido == 3)
            pergaminhoEscolhido = pergaminhoBranco;
    }

    public void mouseClicked(int x, int y) {

        if(faseCabos == false || caboCortado != -1)
            return;

        x = x - Consts.CONSTANTE_HORIZONTAL;

        if(x < Consts.JANELA * 0.26 || x > Consts.JANELA*0.77)
            return;

        if(y > Consts.JANELA*0.17 &&
           y < Consts.JANELA*0.36){
            cabos = caboVermelhoCortado;
            caboCortado = 0;
        }

        else if(y > Consts.JANELA*0.44 &&
           y < Consts.JANELA*0.56){
            cabos = caboAmareloCortado;
            caboCortado = 2;
        }
        
        else if(y > Consts.JANELA*0.58 &&
           y < Consts.JANELA*0.69){
            cabos = caboAzulCortado;
            caboCortado = 1;
        }

        else if(y > Consts.JANELA*0.70 &&
           y < Consts.JANELA*0.80){
            cabos = caboBrancoCortado;
            caboCortado = 3;
        }

        if(caboCortado == numeroPergaminhoEscolhido){
            cortouCerto = true;
        }

    }

}