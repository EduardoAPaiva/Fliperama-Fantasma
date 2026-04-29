package Controler;

import Auxiliar.Consts;
import Auxiliar.Desenho;
import Auxiliar.Posicao;
import FaseCabana.FaseCabana;
import FaseCobrinha.FaseCobra;
import FaseExplorador.FaseExplo;
import FaseMenu.FaseMenu;
import FasePacman.FasePacman;
import FaseShotDuck.FaseShotDuck;
import Modelo.Personagem;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetListener;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTargetEvent;

import java.awt.datatransfer.DataFlavor;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Tela extends JFrame implements MouseMotionListener, MouseListener, KeyListener {

    private int resetarTecla = 0;
    private int teclaAtual;
    private ArrayList<Fase> Fases = new ArrayList<>();
    private int fase = 0;
    private Fase faseAtual;
    private Graphics g2;
    private final Set<Integer> teclasPressionadas = new HashSet<>();

    private ImageIcon imagemVitoria;
    private ImageIcon imagemDerrota;
    private ImageIcon imagemCreditos;

    private int ConstTelaVitoria = 3 * Consts.FRAME_RATE;
    private int contadorTelaVitoria = 0;

    private boolean faseAtualVencida;
    private boolean trocandoFaseAtual = false;

    private Clip musicaCreditos;
    private boolean nosCreditos;
    private boolean musicaCreditosComecou = false;
    

    public Tela() {
        Desenho.setCenario(this);
        initComponents();
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        /*mouse*/
        this.addKeyListener(this);
        /*teclado*/
 /*Cria a janela do tamanho do tabuleiro + insets (bordas) da janela*/

        this.dispose();
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setVisible(true);

        habilitarArrastarSoltar();
        
        Consts.JANELA = getHeight() - getInsets().top;
        Consts.CONSTANTE_HORIZONTAL = (getWidth() - Consts.JANELA)/2;
        Consts.pixelsAltura = Consts.JANELA;
        Consts.pixelsLargura = getWidth();
        Consts.TAMANHO_PONTUACAO = (int)(0.04 * Consts.JANELA);

        imagemVitoria = Desenho.retornaImagem("voceVenceu.png", Consts.JANELA);
        imagemDerrota = Desenho.retornaImagem("vocePerdeu.png", Consts.JANELA);
        imagemCreditos = Desenho.retornaImagem("FaseMenu/creditos.png", Consts.JANELA);

        faseAtual = new FaseMenu("", 1, 1, "bricks.png");
        Fases.add(faseAtual);

        Fases.add(new FaseCabana("", 14, 8, "chao.png"));

        Fases.add(new FaseExplo("", 1, 1, "fundo_explorador_escuro.png"));

        Fases.add(new FaseCobra("", 1, 1, "grama.png"));

        Fases.add(new FasePacman("", 26, 12, "black.png"));

        Fases.add(new FaseShotDuck(null, 1, 1, "fundofasepato.png"));
    }

    public boolean ehPosicaoValida(Posicao p) {
        if (faseAtual == null) {
            return true;
        }
        return faseAtual.ehPosicaoValida(p);
    }

    public Graphics getGraphicsBuffer() {
        return g2;
    }

    public void go() {
        TimerTask task = new TimerTask() {
            public void run() {
                repaint();
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, 0, 1000 / Consts.FRAME_RATE);
    }

    public void paint(Graphics gOld) {

        Graphics g = this.getBufferStrategy().getDrawGraphics();
        /*Criamos um contexto gráfico*/
        g2 = g.create(getInsets().left, getInsets().top, getWidth() - getInsets().right, getHeight() - getInsets().top);
        /**
         * ***********Desenha cenário de fundo*************
         */

        if(contadorTelaVitoria != 0){

            if(fase == 0)
                faseAtual.printaFundoPreto(g);

            if(faseAtualVencida == false)
                Desenho.desenharPorPixel(g2, imagemDerrota, 0, 0);
            else
                Desenho.desenharPorPixel(g2, imagemVitoria, 0, 0);

            contadorTelaVitoria--;

            g.dispose();
            g2.dispose();
            if (!getBufferStrategy().contentsLost()) {
                getBufferStrategy().show();
            }

            return;
        }

        if(nosCreditos == true){

            faseAtual.printaFundoPreto(g2);
            Desenho.desenharPorPixel(g2, imagemCreditos, 0, 0);

            g.dispose();
            g2.dispose();
            if (!getBufferStrategy().contentsLost()) {
                getBufferStrategy().show();
            }

            if(musicaCreditosComecou == false){
                if(faseAtual.getGoodEnding() == true)
                    iniciarMusicaCreditos("temaVenceu.wav");
                else
                    iniciarMusicaCreditos("temaPerdeu.wav");
                musicaCreditosComecou = true;
                return;
            }
            else if(musicaCreditos.isRunning())
                return;
            else
                nosCreditos = false;
        }


        if(trocandoFaseAtual == true){
            faseAtual.iniciarMusica();
            trocandoFaseAtual = false;
            resetarTecla();
        }

        faseAtual.atualizaContador();

        if (faseAtual.getContador() == 0) {
            if (faseAtual.getMorto() == false) {
                setDirecao();
                if (faseAtual.processaTudo()) {
                    
                    fase++;

                    if(fase == Fases.size()){
                        fase = 0;
                        nosCreditos = true;
                    }

                    faseAtual.pararMusica();

                    boolean goodEnding = true;

                    if(fase == 0 || (fase >= 3 && fase <=5)){
                        contadorTelaVitoria = ConstTelaVitoria;
                        goodEnding = faseAtual.getGoodEnding();
                        faseAtualVencida = faseAtual.getFaseVencida();
                        trocandoFaseAtual = true;
                    }

                    faseAtual = Fases.get(fase);

                    if(fase<3 && fase != 0)
                        faseAtual.iniciarMusica();

                    faseAtual.setGoodEnding(goodEnding);
                    return;
                    

                }
            }
            resetarTecla();
        }

        faseAtual.desenhaFundo(g2);
        faseAtual.desenhaTudo(g2);

        g.dispose();
        g2.dispose();
        if (!getBufferStrategy().contentsLost()) {
            getBufferStrategy().show();
        }
    }

 // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Jogo");
        setAlwaysOnTop(true);
        setAlwaysOnTop(false);
        setAutoRequestFocus(false);
        setResizable(false);
        setUndecorated(true);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 561, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 500, Short.MAX_VALUE)
        );

        
        pack();

    }// </editor-fold>                        
    // Variables declaration - do not modify                     
    // End of variables declaration                   

    public void keyPressed(KeyEvent e) {
        try {

            if (teclasPressionadas.contains(e.getKeyCode())) {
                return;
            }

            teclasPressionadas.add(e.getKeyCode());

            resetarTecla = 0;

            if (e.getKeyCode() == KeyEvent.VK_T) {
                /*this.faseAtual.clear();
                ArrayList<Personagem> novaFase = new ArrayList<Personagem>();

                Cria faseAtual adiciona personagens
                hero = new Hero("Robbo.png", 10, 10);
                hero.setPosicao(10, 10);
                novaFase.add(hero);
                this.atualizaCamera();

                faseAtual = novaFase;*/
            } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                teclaAtual = Consts.CIMA;
            } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                teclaAtual = Consts.BAIXO;
            } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                teclaAtual = Consts.ESQUERDA;
            } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                teclaAtual = Consts.DIREITA;
            } else if (e.getKeyCode() == KeyEvent.VK_R) {
                faseAtual.reiniciaFase();
            } else if (e.getKeyCode() == KeyEvent.VK_S) {
                File tanque = new File("POO.dat");
                tanque.createNewFile();
                FileOutputStream canoOut = new FileOutputStream(tanque);
                GZIPOutputStream zipar = new GZIPOutputStream(canoOut);
                ObjectOutputStream serializador = new ObjectOutputStream(zipar);
                System.out.println(faseAtual);
                try{serializador.writeObject(faseAtual);}
                catch(IOException alo){
                   System.out.println(alo.getMessage());
                }
                serializador.close();
            } else if (e.getKeyCode() == KeyEvent.VK_L) {
                File tanque = new File("POO.dat");
                FileInputStream canoOut = new FileInputStream(tanque);
                GZIPInputStream deszipar = new GZIPInputStream(canoOut);
                ObjectInputStream serializador = new ObjectInputStream(deszipar);
                try{
                    faseAtual.pararMusica();
                    faseAtual = (Fase)serializador.readObject();
                }
                catch(IOException alo){
                    System.out.println(alo.getMessage());
                }

                if(faseAtual instanceof FaseMenu){
                    Fases.remove(0);
                    Fases.add(0, faseAtual);
                    fase = 0;
                }
                else if(faseAtual instanceof FaseCabana){
                    Fases.remove(1);
                    Fases.add(1, faseAtual);
                    fase = 1;
                }
                else if(faseAtual instanceof FaseExplo){
                    Fases.remove(2);
                    Fases.add(2, faseAtual);
                    fase = 2;
                }
                else if(faseAtual instanceof FaseCobra){
                    Fases.remove(3);
                    Fases.add(3, faseAtual);
                    fase = 3;
                }
                else if(faseAtual instanceof FasePacman){
                    Fases.remove(4);
                    Fases.add(4, faseAtual);
                    fase = 4;
                }
                else if(faseAtual instanceof FaseShotDuck){
                    Fases.remove(5);
                    Fases.add(5, faseAtual);
                    fase = 5;
                }

                faseAtual.recuperarTransient();
                faseAtual.iniciarMusica();
                serializador.close();
                
            } else if (e.getKeyCode() == KeyEvent.VK_J) {
                retornaFase();
            } else if (e.getKeyCode() == KeyEvent.VK_K) {
                avancaFase();
            } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                faseAtual.enterPressionado();
            } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                faseAtual.ESCPressionado();
            }

            //repaint(); /*invoca o paint imediatamente, sem aguardar o refresh*/
        } catch (Exception ee) {
                ee.getMessage();
        }
    }

    public void keyReleased(KeyEvent e) {
        teclasPressionadas.remove(e.getKeyCode());
        if (teclasPressionadas.isEmpty()) {
            resetarTecla = 1;
        }
    }

    public void setDirecao() {
        faseAtual.setDirecao(teclaAtual);
    }

    public int getCameraColuna() {
        return faseAtual.getCameraColuna();
    }

    public int getCameraLinha() {
        return faseAtual.getCameraLinha();
    }

    public int getCameraColunaAnterior() {
        return faseAtual.getCameraColunaAnterior();
    }

    public int getCameraLinhaAnterior() {
        return faseAtual.getCameraLinhaAnterior();
    }

    public void retornaFase() {
        if (fase != 0) {
            fase--;
            faseAtual.pararMusica();
            faseAtual = Fases.get(fase);
            faseAtual.reiniciaFase();
            faseAtual.iniciarMusica();
        }
    }

    public void avancaFase() {
        if (fase != Fases.size() - 1) {
            fase++;
            faseAtual.pararMusica();
            faseAtual = Fases.get(fase);
            faseAtual.reiniciaFase();
            faseAtual.iniciarMusica();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (faseAtual instanceof FaseShotDuck) {
            ((FaseShotDuck) faseAtual).mousePressed(e.getX(), e.getY());
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (faseAtual instanceof FaseShotDuck && ((FaseShotDuck) faseAtual).jogoAtivo) {
            ((FaseShotDuck) faseAtual).mouseDraggedNoEstilingue(e.getX(), e.getY());
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (faseAtual instanceof FaseShotDuck && ((FaseShotDuck) faseAtual).jogoAtivo) {
            ((FaseShotDuck) faseAtual).mouseReleasedEstilingue();
        }
    }

    public void mouseMoved(MouseEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
        if (faseAtual instanceof FaseCabana) {
            ((FaseCabana) faseAtual).mouseClicked(e.getX(), e.getY());
        }
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void keyTyped(KeyEvent e) {
    }

    public void verifica_apertado(Set<Integer> teclasPressionadas) {
        if (teclasPressionadas.contains(KeyEvent.VK_UP)) {
            faseAtual.setDirecao(1);
        } else if (teclasPressionadas.contains(KeyEvent.VK_DOWN)) {
            faseAtual.setDirecao(2);
        } else if (teclasPressionadas.contains(KeyEvent.VK_LEFT)) {
            faseAtual.setDirecao(3);
        } else if (teclasPressionadas.contains(KeyEvent.VK_RIGHT)) {
            faseAtual.setDirecao(4);
        } else {
            faseAtual.setDirecao(0);
        }
    }

    public void limpaTeclas(Set<Integer> teclasPressionadas) {
        teclasPressionadas.clear();
    }

    public void resetarTecla() {
        if (resetarTecla == 1) {
            teclaAtual = Consts.PARADO;
        }
    }

    public void habilitarArrastarSoltar() {

        new DropTarget(this, new DropTargetListener() {
            @Override
            public void drop(DropTargetDropEvent dtde) {
                try {
                    dtde.acceptDrop(DnDConstants.ACTION_COPY);

                    @SuppressWarnings("unchecked")
                    List<File> arquivos = (List<File>) dtde.getTransferable()
                            .getTransferData(DataFlavor.javaFileListFlavor);

                    int x = dtde.getLocation().x;
                    int y = dtde.getLocation().y;

                    for (File file : arquivos) {
                        if (file.getName().endsWith(".zip")) {

                            System.out.println("Arquivo ZIP solto: " + file.getName());
                            System.out.println("Posição: X=" + x + " Y=" + y);

                            FileInputStream canoOut = new FileInputStream(file);
                            GZIPInputStream deszipar = new GZIPInputStream(canoOut);
                            ObjectInputStream serializador = new ObjectInputStream(deszipar);

                            try{
                                Personagem novoPersonagem;
                                novoPersonagem = (Personagem)serializador.readObject();
                                faseAtual.adicionarPersonagem(novoPersonagem, x, y);
                            }
                            catch(IOException alo){
                                System.out.println(alo.getMessage());
                            }

                            serializador.close();
                        }
                    }

                    teclasPressionadas.clear();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            // Métodos obrigatórios mas que você não usa:
            public void dragEnter(DropTargetDragEvent dtde) {}
            public void dragOver(DropTargetDragEvent dtde) {}
            public void dropActionChanged(DropTargetDragEvent dtde) {}
            public void dragExit(DropTargetEvent dte) {}
        });
    }

    public void iniciarMusicaCreditos(String nomeArquivo){
        try {
            File arquivoAudio = new File(new java.io.File(".").getCanonicalPath() + Consts.PATH + nomeArquivo);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(arquivoAudio);
            musicaCreditos = AudioSystem.getClip();
            musicaCreditos.open(audioStream);
            musicaCreditos.start();
            
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println("Erro ao tocar a música: " + e.getMessage());
        }

    }

}
