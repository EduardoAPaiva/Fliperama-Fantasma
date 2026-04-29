package Controler;

import Auxiliar.Consts;
import Auxiliar.Desenho;
import Auxiliar.Posicao;
import FaseShotDuck.FaseShotDuck;
import Modelo.Hero;
import Modelo.Personagem;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;

public abstract class Fase implements Serializable{

    public int ConstCellSide;
    public int ConstMundoTamanho;
    public int ConstPeriod;

    protected transient ImageIcon bordaDir;
    protected transient ImageIcon bordaEsq;
    protected transient ImageIcon fundoPreto;
    protected transient ArrayList<Mensagem> ArrayPontuacao = new ArrayList<Mensagem>();
    protected int contador = 0;
    protected String FasePath;
    private Hero hero;
    protected boolean morto;
    protected transient ArrayList<Personagem> faseAtual = new ArrayList<>();
    protected String NomeImgFundo;
    public ControleDeJogo cj;
    protected int cameraLinha = 0;
    protected int cameraColuna = 0;
    protected int cameraLinhaAnterior = 0;
    protected transient Clip musicaTema;
    protected transient Clip somAtual;
    protected float volume = 1;
    protected String nomeMusica;
    protected int cameraColunaAnterior = 0;
    protected boolean goodEnding = true;
    protected boolean faseVencida = true;

    public Fase(String sNomeImagePNG, int linha, int coluna, String NomeImgFundo){
        this.NomeImgFundo = NomeImgFundo;
        cj = new ControleDeJogo();
        fundoPreto = Desenho.retornaImagemTamanhoCustomizado("preto.jpg", Consts.pixelsLargura, Consts.pixelsAltura);

    }

    public String getNomeImgFundo(){
        return NomeImgFundo;
    }

    public int getCameraLinha() {
        return cameraLinha;
    }

    public int getCameraColuna() {
        return cameraColuna;
    }

    public int getCameraColunaAnterior(){
        return cameraColunaAnterior;
    }

    public int getCameraLinhaAnterior(){
        return cameraLinhaAnterior;
    }

    public int getContador(){
        return contador;
    }

    public void enterPressionado(){
        return;
    }

    public void ESCPressionado(){
        System.exit(0);
        return;
    }

    public void adicionarPersonagem(Personagem p, int x, int y){
        return;
    }

    public void atualizaContador(){
        contador++;
        contador = contador % (ConstPeriod*Consts.FRAME_RATE/1000);
    }

    public void addPersonagem(Personagem umPersonagem) {
        faseAtual.add(umPersonagem);
    }

    public void removePersonagem(Personagem umPersonagem) {
        faseAtual.remove(umPersonagem);
    }

    public void atualizaCamera() {
        int linha = hero.getPosicao().getLinha();
        int coluna = hero.getPosicao().getColuna();

        cameraColunaAnterior = cameraColuna;
        cameraLinhaAnterior = cameraLinha;

        cameraLinha = Math.max(0, Math.min(linha - Consts.RES / 2, ConstMundoTamanho - Consts.RES));
        cameraColuna = Math.max(0, Math.min(coluna - Consts.RES / 2, ConstMundoTamanho - Consts.RES));
    }

    public abstract void desenhaTudo(Graphics g);
    
    public abstract boolean processaTudo();

    public abstract void moveUp();

    public abstract void moveDown();

    public abstract void moveLeft();

    public abstract void moveRight();

    public abstract void reiniciaFase();

    public boolean ehPosicaoValida(Posicao p){
        return cj.ehPosicaoValida(faseAtual, p);
    }

    public void setDirecao(int direcao){
        hero.setDirecao(direcao);
    }

    public boolean getMorto(){
        return morto;
    }

    public void printaPontuacao(Graphics g){
        Mensagem caractereAtual;
        for(int i = 0; i<ArrayPontuacao.size(); i++){
            caractereAtual = ArrayPontuacao.get(i);
            Desenho.desenharPorPixel(g, caractereAtual.iImage, Consts.JANELA - (i + 1) * Consts.TAMANHO_PONTUACAO, 0);
        }
    }

    public void desenhaFundo(Graphics g){

        printaFundoPreto(g);

        Image newImage;
        String caminho = "";

        try {
            caminho = new java.io.File(".").getCanonicalPath() + Consts.PATH + FasePath + NomeImgFundo;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        newImage = Toolkit.getDefaultToolkit().getImage(caminho);

        for (int i = 0; i < ConstMundoTamanho; i++) {
            for (int j = 0; j < ConstMundoTamanho; j++) {
                int mapaLinha = cameraLinha + i;
                int mapaColuna = cameraColuna + j;

                if (mapaLinha < ConstMundoTamanho && mapaColuna < ConstMundoTamanho) {
                        g.drawImage(newImage,
                                j * ConstCellSide + Consts.CONSTANTE_HORIZONTAL, i * ConstCellSide,
                                ConstCellSide, ConstCellSide, null);

                }
            }
        }
    }

    public abstract void recuperarTransient();

    public void printaBordas(Graphics g){
        Desenho.desenharPorPixel(g, bordaEsq, -Consts.CONSTANTE_HORIZONTAL, 0);
        Desenho.desenharPorPixel(g, bordaDir, Consts.JANELA, 0);
    }

    public void printaFundoPreto(Graphics g){
        if(fundoPreto.getImage() != null)
            Desenho.desenharPorPixel(g, fundoPreto, -Consts.CONSTANTE_HORIZONTAL, 0);
    }
    

    public void iniciarMusica() {
        pararMusica(); 
        
        String nomeArquivo = Consts.PATH + FasePath + nomeMusica;

        if(this instanceof FaseShotDuck)
            nomeArquivo = FasePath + nomeMusica;

        try {
            File arquivoAudio;
            if(this instanceof FaseShotDuck)
                arquivoAudio = new File(nomeArquivo);
            else
                arquivoAudio = new File(new java.io.File(".").getCanonicalPath() + nomeArquivo);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(arquivoAudio);
            musicaTema = AudioSystem.getClip();
            musicaTema.open(audioStream);
            ajustarVolume(volume);
            musicaTema.loop(Clip.LOOP_CONTINUOUSLY);    
            musicaTema.start();
            
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println("Erro ao tocar a música: " + e.getMessage());
        }
    }

    public void tocarSom(String nomeDoArquivo) {
        
        String caminhoCompleto = Consts.PATH + FasePath + nomeDoArquivo;

        if(this instanceof FaseShotDuck)
            caminhoCompleto = FasePath + nomeDoArquivo;

        try {
            File arquivoAudio;
            if(this instanceof FaseShotDuck)
                arquivoAudio = new File(caminhoCompleto);
            else
                arquivoAudio = new File(new java.io.File(".").getCanonicalPath() + caminhoCompleto);
                
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(arquivoAudio);
   
            somAtual = AudioSystem.getClip();
            somAtual.open(audioStream);

            somAtual.start(); 
            
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println("Erro ao tocar efeito sonoro: " + e.getMessage());
        }
    }

    public void ajustarVolume(float volume) {
        if (musicaTema != null && musicaTema.isOpen()) {
            
            if (musicaTema.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                
                FloatControl gainControl = (FloatControl) musicaTema.getControl(FloatControl.Type.MASTER_GAIN);
                
                if (volume <= 0.0f) {
                    gainControl.setValue(gainControl.getMinimum());
                } else {
                    float dB = (float) (Math.log(volume) / Math.log(10.0) * 20.0);
                    
                    dB = Math.min(dB, gainControl.getMaximum());
                    dB = Math.max(dB, gainControl.getMinimum());
                    
                    gainControl.setValue(dB);
                }
            }
        }
    }

    public void pararMusica() {
        if (musicaTema != null && musicaTema.isRunning()) {
            musicaTema.stop();
            musicaTema.close();
        }
    }

    public void pararSom(){
        if (somAtual != null && somAtual.isRunning()) {
            somAtual.stop();
            somAtual.close();
        }
    }

    public void setGoodEnding(boolean b){
        goodEnding = b;
    }

    public boolean getGoodEnding(){
        return goodEnding;
    }

    public void setFaseVencida(boolean f){
        faseVencida = f;
    }

    public boolean getFaseVencida(){
        return faseVencida;
    }

}