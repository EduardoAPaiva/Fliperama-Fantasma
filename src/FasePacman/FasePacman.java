package FasePacman;

import Auxiliar.Consts;
import Auxiliar.Desenho;
import Controler.Fase;
import Controler.Mensagem;
import Controler.Texto;
import Modelo.Personagem;
import java.awt.Graphics;
import java.util.ArrayList;
import Modelo.Parede;

public class FasePacman extends Fase{

    public boolean teste = true;

    public int [][] labirinto = new int[28][28];
    public int [][] aux;
    public int pontuacao = 0;
    protected ArrayList<Mensagem> Printaveis = new ArrayList<Mensagem>();
    private boolean jogoEncerrado = false;
    private Pacman pacman;
    private int vidas = 3;
    private PacDot vidaIcon;
    private ArrayList<Fantasma> fantasmas = new ArrayList<>();
    private boolean pacmanEstaPoderoso = false;
    private int timerPoderoso = 0;
    private static final int TICKS_DE_PODER = 66;
    private int timerVitoria = 0;
    private boolean jogoEmGameOver = false; 
    private int timerGameOver = 0; 
    private Parede parede;
    private PacDot dotPequeno;
    private PacDot dotGrande;
    private int dotsRestantes = 0;

    public FasePacman(String sNomeImagePNG, int linha, int coluna, String NomeImgFundo){
        super(sNomeImagePNG, linha, coluna, NomeImgFundo);

        FasePath = "FasePacman/";

        bordaDir = Desenho.retornaImagemTamanhoCustomizado(FasePath + "bordaDireita.jpg", Consts.CONSTANTE_HORIZONTAL, Consts.JANELA);
        bordaEsq = Desenho.retornaImagemTamanhoCustomizado(FasePath + "bordaEsquerda.jpg", Consts.CONSTANTE_HORIZONTAL, Consts.JANELA);

        nomeMusica = "temaPacman.wav";

        ConstMundoTamanho = 28;
        ConstCellSide = Consts.JANELA / ConstMundoTamanho;
        ConstPeriod = 90;

        pacman = new Pacman(FasePath + "pacman.png", FasePath + "pacman_imgCima.png", FasePath + "pacman_imgBaixo.png", FasePath + "pacman_imgEsq.png", FasePath + "pacman_imgDir.png", 26, 12, ConstCellSide, ConstMundoTamanho);
        pacman.setDirecao(Consts.DIREITA);

        fantasmas.add(new Fantasma(FasePath + "redGhost.png", 1, 1, ConstCellSide, ConstMundoTamanho));
        fantasmas.add(new Fantasma(FasePath + "blueGhost.png", 1, 26, ConstCellSide, ConstMundoTamanho));
        fantasmas.add(new Fantasma(FasePath + "orangeGhost.png", 13, 1, ConstCellSide, ConstMundoTamanho));
        fantasmas.add(new Fantasma(FasePath + "pinkGhost.png", 13, 26, ConstCellSide, ConstMundoTamanho));

        criaLabirinto();
        atualizaPontuacao();

        parede = new Parede(FasePath + "wall.png", 0, 0,  ConstCellSide, ConstMundoTamanho);
        dotPequeno = new PacDot(FasePath + "dotPequeno.png", 0, 0, ConstCellSide, ConstMundoTamanho);
        dotGrande = new PacDot(FasePath + "dotGrande.png", 0, 0, ConstCellSide, ConstMundoTamanho);
        vidaIcon = new PacDot(FasePath + "pacman.png", 0, 0, ConstCellSide, ConstMundoTamanho);
    }

    private void criaLabirinto() {

        aux = new int[][] {     {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},//1
                                {1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1},
                                {1, 2, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 2, 1},
                                {1, 2, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 2, 1},
                                {1, 2, 1, 1, 1, 1, 2, 3, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 1, 1, 2, 1},//5
                                {1, 2, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 2, 1, 1, 2, 2, 2, 3, 2, 2, 1},
                                {1, 2, 2, 2, 2, 2, 2, 2, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 2, 1, 1, 1, 2, 1, 1, 1, 2, 1},
                                {1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 2, 1, 1, 1, 2, 1, 1, 1, 2, 1},
                                {1, 2, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 1, 2, 1, 1, 1, 2, 1},
                                {1, 2, 1, 1, 2, 2, 2, 2, 1, 1, 2, 1, 1, 1, 2, 1, 1, 1, 2, 1, 1, 1, 2, 1, 1, 1, 2, 1}, //12
                                {1, 2, 1, 1, 2, 1, 1, 2, 1, 1, 2, 1, 1, 1, 2, 1, 1, 1, 2, 1, 2, 2, 2, 2, 2, 2, 2, 1},
                                {1, 2, 1, 1, 2, 1, 1, 2, 1, 1, 2, 1, 1, 1, 2, 1, 1, 1, 2, 1, 2, 1, 2, 1, 1, 1, 2, 1},
                                {1, 2, 2, 2, 2, 1, 1, 2, 1, 1, 2, 1, 1, 1, 2, 1, 1, 1, 2, 1, 2, 1, 2, 1, 1, 1, 2, 1},
                                {1, 2, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 2, 1, 1, 1, 2, 1},
                                {1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 2, 1, 1, 1, 2, 1}, //15
                                {1, 2, 1, 1, 2, 2, 2, 2, 1, 1, 1, 2, 1, 2, 2, 2, 2, 1, 2, 1, 1, 1, 2, 1, 1, 1, 2, 1},
                                {1, 2, 1, 1, 2, 1, 1, 2, 1, 1, 1, 2, 1, 2, 1, 1, 2, 1, 2, 1, 1, 1, 2, 1, 1, 1, 2, 1},
                                {1, 2, 1, 1, 2, 1, 1, 2, 2, 2, 2, 2, 1, 2, 1, 1, 2, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1},
                                {1, 2, 1, 1, 2, 1, 1, 1, 2, 1, 1, 1, 1, 2, 3, 1, 2, 1, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1},
                                {1, 2, 2, 2, 2, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1}, //22
                                {1, 2, 1, 1, 2, 1, 1, 1, 2, 2, 2, 2, 2, 1, 1, 1, 2, 1, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1},
                                {1, 2, 1, 1, 2, 1, 1, 1, 2, 1, 1, 1, 2, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 2, 1},
                                {1, 2, 1, 1, 2, 2, 2, 2, 2, 1, 1, 1, 2, 2, 2, 2, 2, 1, 1, 1, 2, 1, 1, 2, 1, 1, 2, 1},
                                {1, 2, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 2, 1, 1, 2, 1, 1, 2, 1},
                                {1, 2, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 2, 1, 1, 2, 1},
                                {1, 2, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 2, 1, 1, 2, 1}, //25
                                {1, 3, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1},
                                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1} };
    
        labirinto = aux;

        this.dotsRestantes = 0; 
        for (int linha = 0; linha < ConstMundoTamanho; linha++) {
            for (int coluna = 0; coluna < ConstMundoTamanho; coluna++) {
                if (labirinto[linha][coluna] == 2 || labirinto[linha][coluna] == 3) {
                    this.dotsRestantes++;
                }
            }
        }

    }
                            

    @Override
    public void desenhaTudo(Graphics g){

        for (int linha = 0; linha < 28; linha++) {
            for (int coluna = 0; coluna < 28; coluna++) {
                
                if (labirinto[linha][coluna] == 1) {
                    Desenho.desenhar(g, parede.getImage(), coluna, linha, false, ConstCellSide);
                }
                
                else if (labirinto[linha][coluna] == 2) {
                    Desenho.desenhar(g, dotPequeno.getImage(), coluna, linha, false, ConstCellSide);
                } 

                else if (labirinto[linha][coluna] == 3) {
                    Desenho.desenhar(g, dotGrande.getImage(), coluna, linha, false, ConstCellSide);
                } 
            }
        }

        cj.desenhaTudo(g, transformaArray(), Printaveis, contador, ConstCellSide, ConstPeriod);
        printaPontuacao(g);
        desenhaVidas(g);
        printaBordas(g);
    }

    @Override
    public void moveUp(){
        pacman.moveUp(ConstMundoTamanho);
    }

    @Override
    public void moveDown(){
        pacman.moveDown(ConstMundoTamanho);
    }

    @Override
    public void moveLeft(){
        pacman.moveLeft(ConstMundoTamanho);
    }

    @Override
    public void moveRight(){
        pacman.moveRight(ConstMundoTamanho);
    }

    public ArrayList<Personagem> transformaArray(){
        ArrayList<Personagem> tudo = new ArrayList<>();  
        
        //adiciona as coisa tudo
        tudo.add(pacman); 
        tudo.addAll(fantasmas);

        return tudo;
    }

    public boolean temParede(int linha, int coluna) {
        
        return labirinto[linha][coluna] == 1;

    }

    private void colisaoFantasmas() {
    for (Fantasma f: fantasmas) {
        
        boolean colidiu = (pacman.getPosicao().getLinha() == f.getPosicao().getLinha() && pacman.getPosicao().getColuna() == f.getPosicao().getColuna()) ||
                          (pacman.getPosicao().getLinha() == f.getPosicao().getLinhaAnterior() &&
                           pacman.getPosicao().getColuna() == f.getPosicao().getColunaAnterior() &&
                           pacman.getPosicao().getLinhaAnterior() == f.getPosicao().getLinha() &&
                           pacman.getPosicao().getColunaAnterior() == f.getPosicao().getColuna());

        if (colidiu) {
            if (pacmanEstaPoderoso && f.estaAssustado()) {

                pontuacao += 200; 
                atualizaPontuacao();
                f.serComido(); 

            } else if (!pacmanEstaPoderoso && !f.foiComido()) {
                
                perdeVidas(); 
                break; 
            }
        }
    }
}
    

    @Override
    public boolean processaTudo(){

        if (pacmanEstaPoderoso) {
            timerPoderoso--;
            if (timerPoderoso <= 0) {
                finalizarModoComedor();
            }
        }

        for (Fantasma f : fantasmas) {
            f.moverFantasmas(this.labirinto, ConstMundoTamanho);
        }
        
        if(pacman.getDirecao() == Consts.CIMA){
            pacman.setDirecaoAtual(pacman.getDirecao());
        }
        else if(pacman.getDirecao() == Consts.BAIXO){
            pacman.setDirecaoAtual(pacman.getDirecao());
        }
        else if(pacman.getDirecao() == Consts.ESQUERDA){
            pacman.setDirecaoAtual(pacman.getDirecao());
        }
        else if(pacman.getDirecao() == Consts.DIREITA){
            pacman.setDirecaoAtual(pacman.getDirecao());
        }
        else{
            pacman.atualizaDirecao();
        }

        if(pacman.getDirecaoAtual() == Consts.CIMA){
            pacman.moveUp(ConstMundoTamanho);
        }
        else if(pacman.getDirecaoAtual() == Consts.BAIXO){
            pacman.moveDown(ConstMundoTamanho);
        }
        else if(pacman.getDirecaoAtual() == Consts.ESQUERDA){
            pacman.moveLeft(ConstMundoTamanho);
        }
        else if(pacman.getDirecaoAtual() == Consts.DIREITA){
            pacman.moveRight(ConstMundoTamanho);
        }

        if (temParede(pacman.getPosicao().getLinha(), pacman.getPosicao().getColuna())) {
            pacman.setDirecao(Consts.PARADO); 
            pacman.setDirecaoAtual(pacman.getDirecao());
            pacman.setPosicao(pacman.getPosicao().getLinhaAnterior(), pacman.getPosicao().getColunaAnterior(), ConstMundoTamanho);
        }

        if(labirinto[pacman.getPosicao().getLinha()][pacman.getPosicao().getColuna()] == 2){
            labirinto[pacman.getPosicao().getLinha()][pacman.getPosicao().getColuna()] = 0;
            pontuacao += 5;
            atualizaPontuacao();
            this.dotsRestantes--;
        }
        
        if(labirinto[pacman.getPosicao().getLinha()][pacman.getPosicao().getColuna()] == 3){
            labirinto[pacman.getPosicao().getLinha()][pacman.getPosicao().getColuna()] = 0;
            pontuacao += 50;
            atualizaPontuacao();
            iniciarModoComedor();
            this.dotsRestantes--;
            
        } 

        colisaoFantasmas();

        if(vidas == 0){
            setGoodEnding(false);
            setFaseVencida(false);
            return true;
        }

        if(checarVitoria())
            return true;


        return false;
    }

    @Override
    public void setDirecao(int direcao){
        pacman.setDirecao(direcao);
    }

    public void perdeVidas() {

        this.vidas = this.vidas - 1;

        menosVidas();

    }

    @Override
    public void reiniciaFase(){

        this.jogoEmGameOver = false;
        this.morto = false; 

        Printaveis.clear();

        finalizarModoComedor();

        this.morto = true;
        this.vidas = 3;

        criaLabirinto();
        
        pacman = new Pacman(FasePath + "pacman.png", FasePath + "pacman_imgCima.png", FasePath + "pacman_imgBaixo.png", FasePath + "pacman_imgEsq.png", FasePath + "pacman_imgDir.png", 26, 12, ConstCellSide, ConstMundoTamanho);
        pacman.setDirecao(2);

        for(Fantasma f : fantasmas){
            f.setPosicao(f.getLinhaInicial(), f.getColunaInicial(), ConstMundoTamanho);
            f.setDirecaoAtual(Consts.PARADO);
        }

        ArrayPontuacao.clear();
        pontuacao = 0;
        atualizaPontuacao();

        morto = false;
    }

    public void menosVidas() {

        finalizarModoComedor();

        this.morto = true;

        pacman = new Pacman(FasePath + "pacman.png", FasePath + "pacman_imgCima.png", FasePath + "pacman_imgBaixo.png", FasePath + "pacman_imgEsq.png", FasePath + "pacman_imgDir.png", 26, 12, ConstCellSide, ConstMundoTamanho);
        pacman.setDirecao(2);

        fantasmas.get(0).setPosicao(1, 1, ConstMundoTamanho);
        fantasmas.get(0).setDirecaoAtual(Consts.PARADO);
        fantasmas.get(1).setPosicao(1, 26, ConstMundoTamanho);
        fantasmas.get(1).setDirecaoAtual(Consts.PARADO);
        fantasmas.get(2).setPosicao(13, 1, ConstMundoTamanho);
        fantasmas.get(2).setDirecaoAtual(Consts.PARADO);
        fantasmas.get(3).setPosicao(13, 26, ConstMundoTamanho);
        fantasmas.get(3).setDirecaoAtual(Consts.PARADO);

        morto = false;
    }


    public void desenhaVidas(Graphics g) {
        for(int i =0; i < this.vidas; i++) {
            Desenho.desenhar(g, vidaIcon.getImage(), i, 0, false, ConstCellSide);
        }
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

    public boolean checarVitoria() {

        if (this.morto) return false;

        if (this.dotsRestantes <= 0) {
            return true;
        }
        return false;
    }

    public void proximaFase() {

        this.jogoEncerrado = false;
        this.morto = false;

        Printaveis.clear();

        this.vidas = 3;
        criaLabirinto();

        pacman = new Pacman(FasePath + "pacman.png", FasePath + "pacman_imgCima.png", FasePath + "pacman_imgBaixo.png", FasePath + "pacman_imgEsq.png", FasePath + "pacman_imgDir.png", 26, 12, ConstCellSide, ConstMundoTamanho);
        pacman.setDirecao(2);

        fantasmas.get(0).setPosicao(1, 1, ConstMundoTamanho);
        fantasmas.get(0).setDirecaoAtual(Consts.PARADO);
        fantasmas.get(1).setPosicao(1, 26, ConstMundoTamanho);
        fantasmas.get(1).setDirecaoAtual(Consts.PARADO);
        fantasmas.get(2).setPosicao(13, 1, ConstMundoTamanho);
        fantasmas.get(2).setDirecaoAtual(Consts.PARADO);
        fantasmas.get(3).setPosicao(13, 26, ConstMundoTamanho);
        fantasmas.get(3).setDirecaoAtual(Consts.PARADO);


        finalizarModoComedor();
    }


    public void iniciarModoComedor() {
        this.pacmanEstaPoderoso = true;
        this.timerPoderoso = TICKS_DE_PODER;
        for (Fantasma f : fantasmas) {
            f.assustar();
        }
    }

    public void finalizarModoComedor() {
        this.pacmanEstaPoderoso = false;
        for (Fantasma f : fantasmas) {
            f.normalizar(); 
        }
    }

    @Override
    public void atualizaContador(){
       
        if (jogoEncerrado) {
            if (timerVitoria <= 0) proximaFase();
            else timerVitoria--;
        }

        else if (jogoEmGameOver) {
            if (timerGameOver <= 0) reiniciaFase();
            else timerGameOver--;
        }

        super.atualizaContador();
    }

    @Override
    public void recuperarTransient(){
        bordaDir = Desenho.retornaImagemTamanhoCustomizado(FasePath + "bordaDireita.jpg", Consts.CONSTANTE_HORIZONTAL, Consts.JANELA);
        bordaEsq = Desenho.retornaImagemTamanhoCustomizado(FasePath + "bordaEsquerda.jpg", Consts.CONSTANTE_HORIZONTAL, Consts.JANELA);
        fundoPreto = Desenho.retornaImagemTamanhoCustomizado("preto.jpg", Consts.pixelsLargura, Consts.pixelsAltura);
        Printaveis = new ArrayList<>();
        if(jogoEmGameOver){
            Mensagem msg = new Mensagem(true);
            msg.defineImagem(FasePath + "voceperdeu.png", ConstCellSide, ConstMundoTamanho);
            Printaveis.add(msg); 
        }

        if(jogoEncerrado){
            Mensagem msg = new Mensagem(true);
            msg.defineImagem(FasePath + "voceganhou.png", ConstCellSide, ConstMundoTamanho);
            Printaveis.add(msg); 
        }

        pacman.recuperarTransient(FasePath + "pacman_imgCima.png", FasePath + "pacman_imgBaixo.png", FasePath + "pacman_imgEsq.png", FasePath + "pacman_imgDir.png", ConstCellSide);
        
        parede.setImage(Desenho.retornaImagem(FasePath + "wall.png", ConstCellSide));
        dotPequeno.setImage(Desenho.retornaImagem(FasePath + "dotPequeno.png", ConstCellSide));
        dotGrande.setImage(Desenho.retornaImagem(FasePath + "dotGrande.png", ConstCellSide));
        vidaIcon.setImage(Desenho.retornaImagem(FasePath + "pacman.png", ConstCellSide));

        fantasmas.get(0).setImage(Desenho.retornaImagem(FasePath + "redGhost.png", ConstCellSide));
        fantasmas.get(1).setImage(Desenho.retornaImagem(FasePath + "blueGhost.png", ConstCellSide));
        fantasmas.get(2).setImage(Desenho.retornaImagem(FasePath + "orangeGhost.png", ConstCellSide));
        fantasmas.get(3).setImage(Desenho.retornaImagem(FasePath + "pinkGhost.png", ConstCellSide));

        ArrayPontuacao = new ArrayList<>();
        atualizaPontuacao();

    }
    

    public void adicionarPersonagem(Personagem p, int x, int y){
        if(p instanceof Fantasma){
            int linha = y/ConstCellSide;
            int coluna = (x-Consts.CONSTANTE_HORIZONTAL)/ConstCellSide;

            if(labirinto[linha][coluna] != 1)
                fantasmas.add(new Fantasma(FasePath + "redGhost.png", linha, coluna, ConstCellSide, ConstMundoTamanho));

        }
    }

}
