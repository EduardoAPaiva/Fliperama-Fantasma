package FaseExplorador;

import Auxiliar.Consts;
import Auxiliar.Desenho;
import Controler.Fase;
import Controler.Mensagem;
import Controler.Texto;
import Modelo.Parede;
import Modelo.Personagem;

import java.util.ArrayList;
import java.util.Random;
import javax.swing.ImageIcon;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;

public class FaseExplo extends Fase{

    private int resolucao = 16;

    private boolean mundoAleatorio = true;
    private ArrayList<Mensagem> arrayTempo = new ArrayList<>();
    private int pontuacao = 0;
    private int cont=0;
    private int tempo = 80;
    private Explorador explo;
    private ArrayList<Parede> barreiras_fisicas = new ArrayList<Parede>();
    private boolean fim=false;
    private int[][] matrizCenario = new int[30][30];
    private Barreira exemploParede;
    private BlocoFinal exemploBlocofinal;
    private BlocoAreia exemploBlocoAreia;
    private Random random = new Random();

    public FaseExplo(String sNomeImagePNG, int linha, int coluna, String NomeImgFundo){
        super(sNomeImagePNG, linha, coluna, NomeImgFundo);

        FasePath = "FaseExplorador"+ File.separator;

        bordaDir = Desenho.retornaImagemTamanhoCustomizado(FasePath + "bordaDireita.jpg", Consts.CONSTANTE_HORIZONTAL, Consts.JANELA);
        bordaEsq = Desenho.retornaImagemTamanhoCustomizado(FasePath + "bordaEsquerda.jpg", Consts.CONSTANTE_HORIZONTAL, Consts.JANELA);

        nomeMusica = "temaExplorador.wav";
        volume = 0.8f;

        ConstMundoTamanho = 30;
        resolucao = 16;
        ConstCellSide = Consts.JANELA/resolucao;
        ConstPeriod = 120;

        exemploParede = new Barreira(FasePath + "Barreira_explorador.png",0,0,ConstCellSide,ConstMundoTamanho);
        exemploBlocofinal=new BlocoFinal(FasePath + "final_explorador.png",0,0,ConstCellSide,ConstMundoTamanho);
        exemploBlocoAreia = new BlocoAreia(FasePath + "fundo_explorador.png",0,0,ConstCellSide,ConstMundoTamanho);

        explo = new Explorador(FasePath + "explorador.png", FasePath + "explorador_cima.png", FasePath + "explorador_baixo.png", FasePath + "explorador_lado1.png", FasePath + "explorador_lado2.png", FasePath + "explorador_lado3.png", 1, 1, ConstCellSide, ConstMundoTamanho);
        explo.setDirecao(0);

        atualizaPontuacao();
        atualizaTempo();
        Criabarreiras();
    }

    @Override
    public void desenhaTudo(Graphics g){
        
        if(cameraColuna == cameraColunaAnterior && cameraLinha == cameraLinhaAnterior){
            explo.getExplorador().autoDesenho(g, contador, ConstCellSide, ConstPeriod);
            for(int i=0;i<ConstMundoTamanho;i++ ){
                for(int j=0;j<ConstMundoTamanho;j++){
                    if(matrizCenario[i][j]==1){
                        Desenho.desenhar(g, exemploParede.getImage(), j, i,  false, ConstCellSide);
                    }
		            else if(matrizCenario[i][j]==2){
                        barreiras_fisicas.add(new pedra(FasePath + "pedra_explorador.png",i,j,ConstCellSide,ConstMundoTamanho));
                        matrizCenario[i][j]=0;
                    }
                    else if(matrizCenario[i][j]==3){
                        barreiras_fisicas.add(new diamante(FasePath + "diamante_explorador.png",i,j,ConstCellSide,ConstMundoTamanho));
                        matrizCenario[i][j]=0;
                    }
                    else if(matrizCenario[i][j]==4){
                        Desenho.desenhar(g,exemploBlocofinal.getImage(), j, i,false,ConstCellSide);
                    }
                    else if(matrizCenario[i][j]==5){
                        Desenho.desenhar(g,exemploBlocoAreia.getImage(),j,i,false,ConstCellSide);
                    }
                    else if(matrizCenario[i][j]==6){
                        Desenho.desenhar(g,exemploBlocoAreia.getImage(),j,i,false,ConstCellSide);
                    }
                    else if(matrizCenario[i][j]==7){
                        Desenho.desenhar(g,exemploBlocoAreia.getImage(),j,i,false,ConstCellSide);
                    }
                    else if(matrizCenario[i][j]==8){
                        Desenho.desenhar(g,exemploBlocoAreia.getImage(),j,i,false,ConstCellSide);
                    }
                }
            }
            for(int i=0;i<barreiras_fisicas.size();i++){
                barreiras_fisicas.get(i).autoDesenho(g,contador,ConstCellSide,ConstPeriod);
            }
        }
        else{
            Desenho.desenhar(g, explo.getExplorador().getImage(), explo.getExplorador().getPosicao().getColuna(), explo.getExplorador().getPosicao().getLinha(), false, ConstCellSide);
            DesenhaCenarioMexendo(g);   
        }
        printaPontuacao(g);
        printatempo(g);
        printaBordas(g);
        
    }

    @Override
    public void moveUp(){
        explo.moveUp(ConstMundoTamanho);
    }

    @Override
    public void moveDown(){
        explo.moveDown(ConstMundoTamanho);
    }

    @Override
    public void moveLeft(){
        explo.moveLeft(ConstMundoTamanho);
    }

    @Override
    public void moveRight(){
        explo.moveRight(ConstMundoTamanho);
    }

    @Override
    public boolean processaTudo(){

        if(tempo == 0) {
            setGoodEnding(false);
            setFaseVencida(false);
            return true;
        }
        if(cont == 10){
            tempo--;
            atualizaTempo();
            cont=0;
        }

        cont++;
        if(explo.getDirecao() == 1){
            explo.setDirecaoAtual();
        }
        else if(explo.getDirecao() == 2){
            explo.setDirecaoAtual();
        }
        else if(explo.getDirecao() == 3){
            explo.setDirecaoAtual();
        }
        else if(explo.getDirecao() == 4){
            explo.setDirecaoAtual();
        }
        else if(explo.getDirecao()==0)
            explo.setDirecaoAtual();


        explo.setDirecao(0);


        if(explo.getDirecaoAtual() == 1){
            explo.moveUp(ConstMundoTamanho);
        }
        else if(explo.getDirecaoAtual() == 2){
            explo.moveDown(ConstMundoTamanho);
        }
        else if(explo.getDirecaoAtual() == 3){
            explo.moveLeft(ConstMundoTamanho);
        }
        else if(explo.getDirecaoAtual() == 4){
            explo.moveRight(ConstMundoTamanho);
        }
        else if(explo.getDirecaoAtual() == 0){
            explo.ficaparado();
        }

        if(estaOcupado()){
            explo.setImagemParado();
            explo.setDirecao(0);
            explo.setDirecaoAtual();
            explo.getExplorador().setPosicao(explo.getExplorador().getPosicao().getLinhaAnterior(),explo.getExplorador().getPosicao().getColunaAnterior(), ConstMundoTamanho); 
        }
	    if(fim){
            if (pontuacao < 100) {
                setGoodEnding(false);
                setFaseVencida(false);
            }
            return true;
        }
        
        desenhafisica();
        atualizaCamera();

        return false;
    }

    @Override
    public void setDirecao(int direcao){
        explo.setDirecao(direcao);
    }

    @Override
    public void reiniciaFase(){
        explo = new Explorador(FasePath + "explorador.png", FasePath + "explorador_cima.png", FasePath + "explorador_baixo.png", FasePath + "explorador_lado1.png", FasePath + "explorador_lado2.png", FasePath + "explorador_lado3.png", 1, 1, ConstCellSide, ConstMundoTamanho);
        explo.setDirecao(2);
        barreiras_fisicas.clear();
        Criabarreiras();

        morto = false;
    }

    public void atualizaPontuacao(){
       String strPontuacao = "" + pontuacao;

        if(strPontuacao.length() > ArrayPontuacao.size()){
            for(int i = ArrayPontuacao.size(); i< strPontuacao.length(); i++)
                ArrayPontuacao.add(new Texto(true, 0, 0));
        }

        for(int i = strPontuacao.length() - 1; i>=0; i--){
            Texto algarismoAtual = (Texto)ArrayPontuacao.get(strPontuacao.length() - 1 - i);
            algarismoAtual.defineAlgarismo(strPontuacao.charAt(i) - '0', Consts.TAMANHO_PONTUACAO);
        }

    }

    private void Criabarreiras(){
        int[][] matrizaux = {{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                            {1,0,5,5,6,5,5,0,5,5,3,2,2,0,5,5,5,5,2,6,2,5,5,5,5,2,5,3,5,1},
                            {1,5,2,0,2,5,5,5,5,5,5,0,5,5,5,5,5,5,5,5,2,3,5,5,2,5,2,5,5,1},
                            {1,5,5,5,5,5,6,5,5,5,5,0,5,5,0,5,5,5,5,2,5,2,5,5,2,8,5,5,5,1},
                            {1,2,5,5,5,5,5,5,5,5,5,5,5,5,2,5,5,5,5,5,2,5,5,2,5,5,2,5,5,1},
                            {1,2,5,2,2,5,0,5,5,5,5,5,5,5,2,2,5,5,2,5,5,5,5,5,2,5,5,5,5,1},
                            {1,5,5,0,2,5,8,2,5,0,5,5,5,5,5,5,2,5,5,5,5,2,5,5,2,5,2,5,5,1},
                            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,5,5,5,2,5,5,1},
                            {1,2,8,3,8,3,5,2,5,0,5,0,5,5,5,5,5,3,5,2,5,5,5,5,5,5,2,2,5,1},
                            {1,5,5,2,5,5,0,5,3,5,5,6,5,5,2,2,8,2,5,5,3,5,5,5,5,5,5,2,5,1},
                            {1,2,0,2,5,5,5,5,5,5,5,5,5,5,2,5,5,2,5,5,2,5,5,5,5,2,5,5,5,1},
                            {1,5,2,5,5,5,5,0,2,2,5,5,8,5,5,5,5,2,5,5,2,5,3,5,5,5,8,0,5,1},
                            {1,5,2,5,5,5,5,5,2,0,2,3,5,3,5,5,5,5,5,5,5,5,2,5,5,3,5,2,5,1},
                            {1,5,3,2,5,5,5,5,5,2,2,2,5,2,5,5,5,5,2,5,8,5,2,5,5,5,5,5,2,1},
                            {1,5,5,0,5,5,5,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                            {1,0,0,5,5,5,2,3,5,5,5,5,2,5,5,2,0,3,2,5,5,5,5,5,2,3,5,5,3,1},
                            {1,5,5,5,5,5,2,5,5,5,5,5,3,0,0,5,2,5,2,5,0,5,5,8,5,2,5,2,5,1},
                            {1,5,2,3,5,0,5,5,5,5,5,5,2,5,5,5,5,5,2,5,5,0,5,5,5,2,2,5,5,1},
                            {1,5,5,0,5,5,5,5,5,8,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,1},
                            {1,3,5,0,5,5,5,5,5,5,3,2,5,5,2,5,5,3,5,2,5,5,5,5,3,5,5,5,5,1},
                            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,5,5,5,2,5,5,1},
                            {1,5,5,5,5,5,5,5,5,5,5,5,5,3,5,5,5,5,5,5,8,5,5,5,5,5,2,2,5,1},
                            {1,3,2,5,5,2,2,5,5,5,5,8,2,5,5,5,5,5,2,5,5,5,0,2,5,5,5,5,5,1},
                            {1,5,5,5,5,5,2,5,5,5,5,5,3,2,5,5,5,5,5,5,5,5,3,2,5,0,2,5,5,1},
                            {1,5,5,5,5,5,2,5,5,8,5,5,2,2,5,5,3,5,2,5,5,5,5,5,5,5,3,5,5,1},
                            {1,5,0,5,5,5,2,5,5,5,2,5,5,5,2,5,5,5,5,5,8,5,5,0,5,5,5,5,5,1},
                            {1,5,4,5,5,5,3,5,5,5,2,5,5,5,0,2,5,5,5,5,5,5,5,5,5,5,5,2,2,1},
                            {1,8,2,5,5,5,5,5,5,5,5,5,5,0,5,5,2,0,0,5,5,5,5,5,5,5,5,2,2,1},
                            {1,5,5,5,5,5,5,5,5,5,0,5,3,5,5,5,5,5,5,5,5,5,5,5,5,5,5,3,8,1},
                            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}};


            if(mundoAleatorio){
                for(int i = 0; i<30; i++){
                    for(int j = 0; j<30; j++){

                        if(i == 1 && j == 1)
                            continue;

                        if(i == 26 && j == 2)
                            continue;

                        if(matrizaux[i][j] == 1)
                            continue;

                        else{
                            int valor = random.nextInt(100);

                            if(valor < 60)
                                matrizaux[i][j] = 5;
                            else if(valor >= 60 && valor<75)
                                matrizaux[i][j] = 2;
                            else if(valor>= 75 && valor<78)
                                matrizaux[i][j] = 3;
                            else if(valor >= 78 && valor<84)
                                matrizaux[i][j] = 8;
                            else if(valor >= 84 && valor<90)
                                matrizaux[i][j] = 0;
                            
                        }
                    }
                }
            }
                
                             
        matrizCenario = matrizaux;                    
        
    }

    @Override
    public void atualizaCamera() {
        int linha = explo.getExplorador().getPosicao().getLinha();
        int coluna = explo.getExplorador().getPosicao().getColuna();

        cameraColunaAnterior = cameraColuna;
        cameraLinhaAnterior = cameraLinha;

        cameraLinha = Math.max(0, Math.min(linha - Consts.JANELA / (2 * ConstCellSide), ConstMundoTamanho - Consts.JANELA / ConstCellSide));
        cameraColuna = Math.max(0, Math.min(coluna - Consts.JANELA / (2 * ConstCellSide), ConstMundoTamanho - Consts.JANELA / ConstCellSide));
    }

    @Override
    public void desenhaFundo(Graphics g){
        Image newImage;
        String caminho = "";

        try {
            caminho = new java.io.File(".").getCanonicalPath() + Consts.PATH + FasePath + NomeImgFundo;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        newImage = Toolkit.getDefaultToolkit().getImage(caminho);

        for (int i = -1; i < ConstMundoTamanho+1; i++) {
            for (int j = -1; j < ConstMundoTamanho+1; j++) {
                int mapaLinha = cameraLinha + i;
                int mapaColuna = cameraColuna + j;

                if(cameraColuna == cameraColunaAnterior && cameraLinha == cameraLinhaAnterior){
                    if (mapaLinha < ConstMundoTamanho && mapaColuna < ConstMundoTamanho) {
                            g.drawImage(newImage, j * ConstCellSide  + Consts.CONSTANTE_HORIZONTAL, i * ConstCellSide, ConstCellSide, ConstCellSide, null);

                    }
                }
                else{
                    if (mapaLinha < ConstMundoTamanho + 1 && mapaColuna < ConstMundoTamanho + 1) {
                        
                        if(explo.getDirecaoAtual() == Consts.BAIXO){
                            g.drawImage(newImage, j * ConstCellSide + Consts.CONSTANTE_HORIZONTAL, i * ConstCellSide - contador*1000*ConstCellSide/(ConstPeriod*Consts.FRAME_RATE), ConstCellSide, ConstCellSide, null);
                        }
                        else if(explo.getDirecaoAtual() == Consts.CIMA){
                            g.drawImage(newImage, j * ConstCellSide + Consts.CONSTANTE_HORIZONTAL, i * ConstCellSide + contador*1000*ConstCellSide/(ConstPeriod*Consts.FRAME_RATE), ConstCellSide, ConstCellSide, null);
                        }
                        else if(explo.getDirecaoAtual() == Consts.ESQUERDA){
                            g.drawImage(newImage, j * ConstCellSide + Consts.CONSTANTE_HORIZONTAL + contador*1000*ConstCellSide/(ConstPeriod*Consts.FRAME_RATE), i * ConstCellSide, ConstCellSide, ConstCellSide, null);
                        }
                        else if(explo.getDirecaoAtual() == Consts.DIREITA){
                            g.drawImage(newImage, j * ConstCellSide + Consts.CONSTANTE_HORIZONTAL - contador*1000*ConstCellSide/(ConstPeriod*Consts.FRAME_RATE), i * ConstCellSide, ConstCellSide, ConstCellSide, null);
                        }

                    }
                }
            }
        }
    }

    public void DesenhaCenarioMexendo(Graphics g){

        int contadorVertical = 0;
        int contadorHorizontal = 0;
        if(explo.getExplorador().getDirecaoAtual() == Consts.CIMA){
            contadorVertical = contador*1000*ConstCellSide/(ConstPeriod*Consts.FRAME_RATE);
        }
        else if(explo.getExplorador().getDirecaoAtual() == Consts.BAIXO){
            contadorVertical = -contador*1000*ConstCellSide/(ConstPeriod*Consts.FRAME_RATE);
        }
        else if(explo.getExplorador().getDirecaoAtual() == Consts.ESQUERDA){
            contadorHorizontal = contador*1000*ConstCellSide/(ConstPeriod*Consts.FRAME_RATE);
        }
        else if(explo.getExplorador().getDirecaoAtual() == Consts.DIREITA){
            contadorHorizontal = -contador*1000*ConstCellSide/(ConstPeriod*Consts.FRAME_RATE);
        }

        
        for(int i=cameraLinha-1;i<cameraLinha+resolucao+1;i++ ){
            for(int j=cameraColuna-1;j<cameraColuna+resolucao+1;j++){
                if(i < 0 || j < 0 || i > ConstMundoTamanho-1 || j > ConstMundoTamanho-1) continue;

                if(matrizCenario[i][j]==1){
                    Desenho.desenharLiso(g, exemploParede.getImage(), j, i,false, contadorVertical, contadorHorizontal, ConstCellSide);
                }
                if(matrizCenario[i][j]==4){
                    Desenho.desenharLiso(g, exemploBlocofinal.getImage(), j, i, false, contadorVertical, contadorHorizontal, ConstCellSide);
                }
                if(matrizCenario[i][j]==5){
                    Desenho.desenharLiso(g, exemploBlocoAreia.getImage(), j, i, false, contadorVertical, contadorHorizontal, ConstCellSide);
                }
                if(matrizCenario[i][j]==6){
                    Desenho.desenharLiso(g, exemploBlocoAreia.getImage(), j, i, false, contadorVertical, contadorHorizontal, ConstCellSide);
                }
                if(matrizCenario[i][j]==7){
                    Desenho.desenharLiso(g, exemploBlocoAreia.getImage(), j, i, false, contadorVertical, contadorHorizontal, ConstCellSide);
                }
                if(matrizCenario[i][j]==8){
                    Desenho.desenharLiso(g, exemploBlocoAreia.getImage(), j, i, false, contadorVertical, contadorHorizontal, ConstCellSide);
                }
            }
        }

        for(int i=0;i<barreiras_fisicas.size();i++){

            int contadorHorizontalBarreira = contadorHorizontal;
            int contadorVerticalBarreira = contadorVertical;

            Parede barreiraAtual = barreiras_fisicas.get(i);
            if(barreiraAtual.getPosicao().getLinha() < cameraLinha-1 ||
               barreiraAtual.getPosicao().getLinha() > cameraLinha + resolucao ||
               barreiraAtual.getPosicao().getColuna() < cameraColuna-1 ||
               barreiraAtual.getPosicao().getColuna() > cameraColuna + resolucao){
                continue;
               }
            
            if(barreiraAtual.getDirecaoAtual() == Consts.BAIXO)
               contadorVerticalBarreira += contador*1000*ConstCellSide/(ConstPeriod*Consts.FRAME_RATE);
            else if(barreiraAtual.getDirecaoAtual() == Consts.ESQUERDA)
               contadorHorizontalBarreira -= contador*1000*ConstCellSide/(ConstPeriod*Consts.FRAME_RATE);
            else if(barreiraAtual.getDirecaoAtual() == Consts.DIREITA)
               contadorHorizontalBarreira += contador*1000*ConstCellSide/(ConstPeriod*Consts.FRAME_RATE);

            if(barreiraAtual.getDirecaoAtual() == Consts.PARADO)
                Desenho.desenharLiso(g,barreiraAtual.getImage(),barreiraAtual.getPosicao().getColuna(),barreiraAtual.getPosicao().getLinha(),false,contadorVerticalBarreira,contadorHorizontalBarreira,ConstCellSide);
            else
                Desenho.desenharLiso(g,barreiraAtual.getImage(),barreiraAtual.getPosicao().getColunaAnterior(),barreiraAtual.getPosicao().getLinhaAnterior(),false,contadorVerticalBarreira,contadorHorizontalBarreira,ConstCellSide);

               
        }
            
    }

    public boolean estaOcupado(){
        int estadoPosicao = matrizCenario[explo.getExplorador().getPosicao().getLinha()][explo.getExplorador().getPosicao().getColuna()];

        if(estadoPosicao == 1){
            return true;
        }
	    if(estadoPosicao == 5){
            matrizCenario[explo.getExplorador().getPosicao().getLinha()][explo.getExplorador().getPosicao().getColuna()]=0;
        }
        if(estadoPosicao== 4){
            fim=true;
        }
        if(estadoPosicao==6){
            matrizCenario[explo.getExplorador().getPosicao().getLinha()][explo.getExplorador().getPosicao().getColuna()]=7;
            return true;
        }
        if(estadoPosicao == 7){
            matrizCenario[explo.getExplorador().getPosicao().getLinha()][explo.getExplorador().getPosicao().getColuna()]=0;
            pontuacao+=3;
            atualizaPontuacao();
        }

        for(int i=0;i<barreiras_fisicas.size();i++){
            if(barreiras_fisicas.get(i) instanceof pedra){
                if(explo.getExplorador().getPosicao().getLinha()==barreiras_fisicas.get(i).getPosicao().getLinha() && explo.getExplorador().getPosicao().getColuna()==barreiras_fisicas.get(i).getPosicao().getColuna()){
                    return true;
                }
            }
            else{
                if(explo.getExplorador().getPosicao().getLinha()==barreiras_fisicas.get(i).getPosicao().getLinha() && explo.getExplorador().getPosicao().getColuna()==barreiras_fisicas.get(i).getPosicao().getColuna()){
                    barreiras_fisicas.remove(i);
                    pontuacao+=3;
                    atualizaPontuacao();
                    return false;
                }
            }
        }

        
        if(estadoPosicao == 8){
            matrizCenario[explo.getExplorador().getPosicao().getLinha()][explo.getExplorador().getPosicao().getColuna()]=0;
            barreiras_fisicas.add(new diamante(FasePath + "diamante_explorador.png",explo.getExplorador().getPosicao().getLinha(),explo.getExplorador().getPosicao().getColuna(),ConstCellSide,ConstMundoTamanho));
        }

        return false;
    }
    public boolean verificabarreira(int linha,int coluna){
        for(int i=0;i<barreiras_fisicas.size();i++){
            if(linha == barreiras_fisicas.get(i).getPosicao().getLinha() && coluna == barreiras_fisicas.get(i).getPosicao().getColuna()){
                return false;
            }
        }
        return true;
    }
    public void desenhafisica(){
        boolean jaMoveu = false;
        for(int i=0;i<barreiras_fisicas.size();i++){
            if(jaMoveu == false){
                if(matrizCenario[barreiras_fisicas.get(i).getPosicao().getLinha()+1][barreiras_fisicas.get(i).getPosicao().getColuna()]==0 && verificabarreira(barreiras_fisicas.get(i).getPosicao().getLinha()+1,barreiras_fisicas.get(i).getPosicao().getColuna()) && (barreiras_fisicas.get(i).getPosicao().getLinha()+1 != explo.getExplorador().getPosicao().getLinha() || barreiras_fisicas.get(i).getPosicao().getColuna() != explo.getExplorador().getPosicao().getColuna())){
                    barreiras_fisicas.get(i).setDirecaoAtual(Consts.BAIXO);
                    barreiras_fisicas.get(i).moveDown(ConstMundoTamanho);
                    barreiras_fisicas.get(i).setjacaiu(true);
                    jaMoveu = true;
                    continue;
                }
                if(barreiras_fisicas.get(i).getjacaiu()){
                    if(matrizCenario[barreiras_fisicas.get(i).getPosicao().getLinha()+1][barreiras_fisicas.get(i).getPosicao().getColuna()-1]==0 && matrizCenario[barreiras_fisicas.get(i).getPosicao().getLinha()][barreiras_fisicas.get(i).getPosicao().getColuna()-1]==0 && verificabarreira(barreiras_fisicas.get(i).getPosicao().getLinha()+1,barreiras_fisicas.get(i).getPosicao().getColuna()-1) && verificabarreira(barreiras_fisicas.get(i).getPosicao().getLinha(),barreiras_fisicas.get(i).getPosicao().getColuna()-1) && (barreiras_fisicas.get(i).getPosicao().getLinha() != explo.getExplorador().getPosicao().getLinha() || barreiras_fisicas.get(i).getPosicao().getColuna()-1 != explo.getExplorador().getPosicao().getColuna()) && (barreiras_fisicas.get(i).getPosicao().getLinha() +1 != explo.getExplorador().getPosicao().getLinha() || barreiras_fisicas.get(i).getPosicao().getColuna()-1 != explo.getExplorador().getPosicao().getColuna())){
                    barreiras_fisicas.get(i).setDirecaoAtual(Consts.ESQUERDA);
                    barreiras_fisicas.get(i).moveLeft(ConstMundoTamanho);
                    jaMoveu = true;
                    continue;
                    }   
                if(matrizCenario[barreiras_fisicas.get(i).getPosicao().getLinha()+1][barreiras_fisicas.get(i).getPosicao().getColuna()+1]==0 && matrizCenario[barreiras_fisicas.get(i).getPosicao().getLinha()][barreiras_fisicas.get(i).getPosicao().getColuna()+1]==0 && verificabarreira(barreiras_fisicas.get(i).getPosicao().getLinha()+1,barreiras_fisicas.get(i).getPosicao().getColuna()+1) && verificabarreira(barreiras_fisicas.get(i).getPosicao().getLinha(),barreiras_fisicas.get(i).getPosicao().getColuna()+1) && (barreiras_fisicas.get(i).getPosicao().getLinha() != explo.getExplorador().getPosicao().getLinha() || barreiras_fisicas.get(i).getPosicao().getColuna()+1 != explo.getExplorador().getPosicao().getColuna()) && (barreiras_fisicas.get(i).getPosicao().getLinha() +1 != explo.getExplorador().getPosicao().getLinha() || barreiras_fisicas.get(i).getPosicao().getColuna()+1 != explo.getExplorador().getPosicao().getColuna())){
                    barreiras_fisicas.get(i).setDirecaoAtual(Consts.DIREITA);
                    barreiras_fisicas.get(i).moveRight(ConstMundoTamanho);
                    jaMoveu = true;
                    continue;
                    }
                
                }
            }
            barreiras_fisicas.get(i).setDirecaoAtual(Consts.PARADO);
    }
}

    public void printatempo(Graphics g){
        Mensagem caractereAtual;
        for(int i = 0; i<arrayTempo.size(); i++){
            caractereAtual = arrayTempo.get(i);
            Desenho.desenharPorPixel(g, caractereAtual.getImage(), i * Consts.TAMANHO_PONTUACAO, 0);
        }
    }
    
    public void atualizaTempo(){
       String strTempo = "" + tempo;

        if(strTempo.length() > arrayTempo.size()){
            for(int i = arrayTempo.size(); i< strTempo.length(); i++)
                arrayTempo.add(new Texto(true, 0, 0));
        }
        if(arrayTempo.size() > strTempo.length())
            arrayTempo.remove(0);

        for(int i = 0; i<strTempo.length(); i++){
            Texto algarismoAtual = (Texto)arrayTempo.get(i);
            algarismoAtual.defineAlgarismo(strTempo.charAt(i) - '0', Consts.TAMANHO_PONTUACAO);
        }

    }

    @Override
    public void recuperarTransient(){
        arrayTempo = new ArrayList<>();
        atualizaTempo();
        ArrayPontuacao = new ArrayList<>();
        atualizaPontuacao();
        explo.recuperarTransient(FasePath + "explorador.png", FasePath + "explorador_cima.png", FasePath + "explorador_baixo.png", FasePath + "explorador_lado1.png", FasePath + "explorador_lado2.png", FasePath + "explorador_lado3.png", ConstCellSide);
        bordaDir = Desenho.retornaImagemTamanhoCustomizado(FasePath + "bordaDireita.jpg", Consts.CONSTANTE_HORIZONTAL, Consts.JANELA);
        bordaEsq = Desenho.retornaImagemTamanhoCustomizado(FasePath + "bordaEsquerda.jpg", Consts.CONSTANTE_HORIZONTAL, Consts.JANELA);
        fundoPreto = Desenho.retornaImagemTamanhoCustomizado("preto.jpg", Consts.pixelsLargura, Consts.pixelsAltura);
        
        for(Parede p : barreiras_fisicas){
            ImageIcon imgPedra = Desenho.retornaImagem(FasePath + "pedra_explorador.png", ConstCellSide);
            ImageIcon imgDiamante = Desenho.retornaImagem(FasePath + "diamante_explorador.png", ConstCellSide);
            if(p instanceof pedra)
                p.setImage(imgPedra);
            else if(p instanceof diamante)
                p.setImage(imgDiamante);
        }

        exemploParede = new Barreira(FasePath + "Barreira_explorador.png",0,0,ConstCellSide,ConstMundoTamanho);
        exemploBlocofinal=new BlocoFinal(FasePath + "final_explorador.png",0,0,ConstCellSide,ConstMundoTamanho);
        exemploBlocoAreia = new BlocoAreia(FasePath + "fundo_explorador.png",0,0,ConstCellSide,ConstMundoTamanho);


    }

    public void adicionarPersonagem(Personagem p, int x, int y){
        if(p instanceof diamante){
            int linha = y/ConstCellSide + cameraLinha;
            int coluna = (x-Consts.CONSTANTE_HORIZONTAL)/ConstCellSide + cameraColuna;

            matrizCenario[linha][coluna] = 0;

            for(Parede parede: barreiras_fisicas){
                if(parede.getPosicao().getLinha() == linha && parede.getPosicao().getColuna() == coluna){
                    barreiras_fisicas.remove(parede);
                    break;
                }
            }

            barreiras_fisicas.add(new diamante(FasePath + "diamante_explorador.png", linha, coluna,ConstCellSide,ConstMundoTamanho));

        }
    }

}