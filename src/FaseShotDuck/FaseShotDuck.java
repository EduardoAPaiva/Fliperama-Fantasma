package FaseShotDuck;

import Controler.Fase;
import Auxiliar.Consts;
import Auxiliar.Desenho;
import Modelo.Personagem;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import javax.swing.ImageIcon;

public class FaseShotDuck extends Fase {

    private static final double velAbs = 8;

    public boolean jogoAtivo = true;
    public boolean sair = false;

    private int vidas = 3;
    private int pontos = 0;

    static double velocidadeAtual = velAbs;
    private boolean estilingueAtivo = false;
    private int xInicialEstilingue = 360, yInicialEstilingue = Consts.pixelsAltura - 255;
    private int xAtualEstilingue = xInicialEstilingue, yAtualEstilingue = yInicialEstilingue;
    int aumentodevel = 0;
    int qtdPatos = 3;

    private boolean recarregando = false;
    private long inicioRecarregar = 0;

    private long ultimoTiro = 0;
    private final long TEMPO_ESPERA_MS = 800;
    private final int raioMaxEstilingue = 200; // limite de puxar o estilingue

    static transient ArrayList<Image> imagens = new ArrayList<>();
    private ArrayList<Personagem> personagens = new ArrayList<>();
    private ArrayList<Pedra> pedras = new ArrayList<>();
    private Queue<Integer> trajetoriaproibida = new LinkedList<>();
    String[] nomesImagens = {
        "fundofasepato.png",
        "duck.png",
        "duck_red.png",
        "estilingue.png",
        "pedra.png",
        "basedetiro.png",
        "duckabatido.png",
        "redduckabatido.png",
        "vida.png",
        "basetiroreccarregando.png"
    };

    private transient ImageIcon textoDuck;

    public FaseShotDuck(String sNomeImagePNG, int linha, int coluna, String NomeImgFundo){
        super(sNomeImagePNG, linha, coluna, NomeImgFundo);
        FasePath = "./imgs/FaseShotDuck" + File.separator;
        for (String nome : nomesImagens) {
            Image img = new ImageIcon(FasePath + nome).getImage();
            imagens.add(img);
        }
        ConstPeriod = 20;

        textoDuck = Desenho.retornaImagemTamanhoCustomizado("FaseShotDuck/textoDuck.png", Consts.CONSTANTE_HORIZONTAL, (int)(Consts.JANELA*0.1));

        nomeMusica = "temaDuck.wav";
        
    }

    @Override
    public void desenhaTudo(Graphics g) {
        if (!jogoAtivo) {
            gameover(g);
            return;
        }

        for (Personagem p : personagens) {
            g.drawImage(Desenho.gerenciaRotacaoImagem(p),
                    p.getPosicao().getColuna(), p.getPosicao().getLinha(), null);
        }

        for (Pedra pedra : pedras) {
            g.drawImage(pedra.getImg(), pedra.getPosicao().getColuna(), pedra.getPosicao().getLinha(), null);
        }

        Desenho.desenharPorPixel(g, textoDuck, -Consts.CONSTANTE_HORIZONTAL, 0);     

    }

    @Override
    public void desenhaFundo(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        g2D.setColor(new Color(81, 59, 48));
        g2D.setStroke(new BasicStroke(5f));

        int largura = Consts.pixelsLargura;
        int altura = Consts.pixelsAltura;

        g.drawImage(imagens.get(0), 0, 0, largura, altura, null);

        // Base e pedras no fundo
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                g.drawImage(imagens.get(4), 180 + 40 * i - 12 * j, altura - 100 + 20 * j, null);
            }
        }

        if (!estilingueAtivo) {
            if(recarregando){
                g.drawImage(imagens.get(9), 300, altura - 300, null);
            }
            else{
                g.drawImage(imagens.get(5), 300, altura - 300, null);
            }
            g.drawImage(imagens.get(3), 300, altura - (int) (imagens.get(3).getHeight(null) / 1.7),
                    imagens.get(3).getWidth(null) / 2, imagens.get(3).getHeight(null) / 2, null);
            g2D.drawLine(383, altura - 255, 415, altura - 270);
            g2D.drawLine(360, altura - 250, 316, altura - 267);
        } else {
            g2D.drawLine(415, altura - 270, xAtualEstilingue + 26, yAtualEstilingue - 3);
            g.drawImage(imagens.get(3), 300, altura - (int) (imagens.get(3).getHeight(null) / 1.7),
                    imagens.get(3).getWidth(null) / 2, imagens.get(3).getHeight(null) / 2, null);
            g.drawImage(imagens.get(5),
                    xAtualEstilingue - imagens.get(5).getWidth(null) / 2,
                    yAtualEstilingue - imagens.get(5).getHeight(null) / 2,
                    null);
            g2D.drawLine(316, altura - 267, xAtualEstilingue + 12, yAtualEstilingue + 4);
        }
        for (int i = 0; i < vidas; i++) {
            g.drawImage(imagens.get(8), 10, altura - 70 - i * 70, null);
        }
        if (recarregando) {
            long tempoPassado = System.currentTimeMillis() - inicioRecarregar;

            double progresso = Math.min(1.0, (double) tempoPassado / TEMPO_ESPERA_MS);

            int xBase = 208;
            int yBase = altura - 80;

            int yDestino = Consts.pixelsAltura - 270;
            int xDestino = 330;

            int xPedraRecarregar = (int) ((1 - progresso) * xBase + progresso * xDestino);
            int yPedraRecarregar = (int) ((1 - progresso) * yBase + progresso * yDestino);

            g.drawImage(imagens.get(4), xPedraRecarregar, yPedraRecarregar, null);

            if (tempoPassado >= TEMPO_ESPERA_MS) {
                recarregando = false; // agora pode atirar
            }
        }
        printarPontuacao(g);

    }

    public void addDucks() {
        Random trajetoria = new Random();
        Random pato = new Random();
        double patoescolhido = pato.nextDouble();
        double probRedDuck = 0.15 + pontos * 0.003;

        if (probRedDuck > 0.4)
            probRedDuck = 0.4;
        
        int marcoAtual = pontos / 40;
        if (marcoAtual > aumentodevel) {
            velocidadeAtual += 0.4;        // aumenta a velocidade
            aumentodevel = marcoAtual; // marca que já aumentou
            if(qtdPatos < 6)
                qtdPatos++;
        }

        int escolha;
        if (trajetoriaproibida.size() > qtdPatos - 1) {
            trajetoriaproibida.poll();
        }
        while (true) {
            escolha = trajetoria.nextInt(6);
            if (trajetoriaproibida.contains(escolha)) {
                continue;
            }
            trajetoriaproibida.add(escolha);
            break;
        }
        Personagem p;
        if (escolha == 0) {
            if (patoescolhido < probRedDuck) {
                p = new RedDuck(imagens.get(2), imagens.get(7));
            } else {
                p = new Duck(imagens.get(1), imagens.get(6));
            }
            p.setTrajetoria(new TrajetoriaParabolaVertical(p, false));
            personagens.add(p);

        }
        if (escolha == 1) {
            if (patoescolhido < probRedDuck) {
                p = new RedDuck(imagens.get(2), imagens.get(7));
            } else {
                p = new Duck(imagens.get(1), imagens.get(6));
            }
            p.setTrajetoria(new TrajetoriaParabolaHorizontal(p, true));
            personagens.add(p);
        }
        if (escolha == 2) {
            if (patoescolhido < probRedDuck) {
                p = new RedDuck(imagens.get(2), imagens.get(7));
            } else {
                p = new Duck(imagens.get(1), imagens.get(6));
            }
            p.setTrajetoria(new TrajetoriaParabolaHorizontal(p, false));
            personagens.add(p);
        }
        if (escolha == 3) {
            if (patoescolhido < probRedDuck) {
                p = new RedDuck(imagens.get(2), imagens.get(7));
            } else {
                p = new Duck(imagens.get(1), imagens.get(6));
            }
            p.setTrajetoria(new TrajetoriaParabolaVertical(p, true));
            personagens.add(p);
        }
        if (escolha == 4) {
            if (patoescolhido < probRedDuck) {
                p = new RedDuck(imagens.get(2), imagens.get(7));
            } else {
                p = new Duck(imagens.get(1), imagens.get(6));
            }
            p.setTrajetoria(new TrajetoriaSinuosaHorizontal(p, true));
            personagens.add(p);

        }
        if (escolha == 5) {
            if (patoescolhido < probRedDuck) {
                p = new RedDuck(imagens.get(2), imagens.get(7));
            } else {
                p = new Duck(imagens.get(1), imagens.get(6));
            }
            p.setTrajetoria(new TrajetoriaSinuosaHorizontal(p, false));
            personagens.add(p);

        }
    }

    @Override
    public boolean processaTudo() {
        if (personagens.size() < qtdPatos) {
            addDucks();
        }

        for (Personagem p : personagens) {
            p.mover(this);

        }

        for (Pedra pedra : pedras) {
            pedra.mover();

            Rectangle rPedra = new Rectangle(
                    pedra.getPosicao().getColuna(),
                    pedra.getPosicao().getLinha(),
                    pedra.getImg().getWidth(null),
                    pedra.getImg().getHeight(null)
            );

            for (Personagem p : personagens) {
                if (p instanceof Duck) {
                    Duck pato = (Duck) p;
                    Rectangle rPato = new Rectangle(
                            pato.getPosicao().getColuna(),
                            pato.getPosicao().getLinha(),
                            pato.getImg().getWidth(null),
                            pato.getImg().getHeight(null)
                    );

                    if (rPedra.intersects(rPato)) {
                        if (!pato.getbMorto()) {
                            pato.setbMorto(true);
                            pontos += 3; // 1 ponto
                        } else {
                            pontos += 6; //pontos por acertar quando tiver caindo
                        }
                        pedra.setRemovivel(true); // pedra some
                        break;
                    }
                }
            }
        }

        pedras.removeIf(Pedra::isRemovivel);
        personagens.removeIf(p -> p instanceof Duck && ((Duck) p).isRemovivel());

        if (vidas == 0) {
            if(pontos < 100){
                setFaseVencida(false);
                setGoodEnding(false);
            }

            return true;
            
        }

        if (sair) {
            sair = false;
            return true;
        }
        return false;
    }

    @Override
    public void moveUp() {
    }

    @Override
    public void moveDown() {
    }

    @Override
    public void moveLeft() {
    }

    @Override
    public void moveRight() {
    }

    @Override
    public void reiniciaFase() {
        personagens.clear();
        pedras.clear();
        vidas = 3;
        pontos = 0;
        velocidadeAtual = velAbs;
        jogoAtivo = true;
        qtdPatos = 2;
        aumentodevel = 0;
    }

    @Override
    public void setDirecao(int direcao) {
    }

    public void mousePressed(int xMouse, int yMouse) {
        if (!jogoAtivo) {
            Rectangle botaoReiniciar = new Rectangle(Consts.pixelsLargura / 2 - 115, 330, 200, 100);
            Rectangle botaoSair = new Rectangle(Consts.pixelsLargura / 2 - 115, 480, 200, 100);

            if (botaoReiniciar.contains(xMouse, yMouse)) {
                reiniciaFase();
            } else if (botaoSair.contains(xMouse, yMouse)) {
                sair = true;
            }
            return;
        }
        long agora = System.currentTimeMillis();
        if (agora - ultimoTiro < TEMPO_ESPERA_MS) {
            return; // não pode puxar ainda
        }
        double dist = Math.hypot(xMouse - xInicialEstilingue, yMouse - yInicialEstilingue);
        if (dist <= raioMaxEstilingue) {
            estilingueAtivo = true;
            atualizaEstilingue(xMouse, yMouse);
        }
    }

    public void mouseDraggedNoEstilingue(int xMouse, int yMouse) {
        if (!estilingueAtivo) {
            return;
        }
        atualizaEstilingue(xMouse, yMouse);
    }

    public void mouseReleasedEstilingue() {
        if (!estilingueAtivo) {
            return;
        }

        estilingueAtivo = false;

        double dx = xInicialEstilingue - xAtualEstilingue;
        double dy = yInicialEstilingue - yAtualEstilingue;

        double distancia = Math.hypot(dx, dy);

        // define intensidade baseada na distância puxada
        double intensidade;
        if (distancia >= raioMaxEstilingue) {
            intensidade = 0.3; // mais rápida
        } else if (distancia > 100) {
            intensidade = 0.2; // média
        } else {
            intensidade = 0.1; // baixa
        }

        // cria pedra na posição do estilingue
        Pedra pedra = new Pedra(imagens.get(4),
                xInicialEstilingue, yInicialEstilingue,
                dx * intensidade, dy * intensidade);

        pedras.add(pedra);

        // reseta estilingue
        xAtualEstilingue = xInicialEstilingue;
        yAtualEstilingue = yInicialEstilingue;

        // registra tempo do último tiro
        ultimoTiro = System.currentTimeMillis();
        // inicia animação de recarga
        recarregando = true;
        inicioRecarregar = System.currentTimeMillis();

    }

    private void atualizaEstilingue(int xMouse, int yMouse) {
        int dx = xMouse - xInicialEstilingue;
        int dy = yMouse - yInicialEstilingue;
        double dist = Math.hypot(dx, dy);
        if (dist > raioMaxEstilingue) {
            double fator = (double) raioMaxEstilingue / dist;
            dx *= fator;
            dy *= fator;
        }
        xAtualEstilingue = xInicialEstilingue + dx;
        yAtualEstilingue = yInicialEstilingue + dy;
    }

    public void reduzirVida() {
        vidas--;
    }

    public void printarPontuacao(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        
        String pontuacao = String.valueOf(pontos);
        
        g2D.setFont(new Font("SansSerif", Font.PLAIN, 40));
        g2D.setColor(Color.black);
        
        int larguraTexto = g2D.getFontMetrics().stringWidth(pontuacao);
        
        g2D.drawString(pontuacao, Consts.pixelsLargura - larguraTexto - 20, 40);
    }

    @Override
    public void recuperarTransient(){
        ImageIcon imgicon = new ImageIcon("./imgs/FaseShotDuck/duck.png");
        textoDuck = Desenho.retornaImagemTamanhoCustomizado("FaseShotDuck/textoDuck.png", Consts.CONSTANTE_HORIZONTAL, (int)(Consts.JANELA*0.1));


        imagens = new ArrayList<>();
        for(String nome : nomesImagens) {
            Image img = new ImageIcon(FasePath + nome).getImage();
            imagens.add(img);
        }
        for(Personagem p: personagens){
            p.setImage(imgicon);
            if(p instanceof RedDuck){
                ((RedDuck)p).setImageNormal(imagens.get(2));
                ((RedDuck)p).setImageMorto(imagens.get(7));
            }
            else{
                ((Duck)p).setImageNormal(imagens.get(1));
                ((Duck)p).setImageMorto(imagens.get(6));
            }
            ((Duck)p).atualizarImagem();
        }
        for(Pedra p: pedras){
            p.setImage(imagens.get(4));
        }

    }

    public void adicionarPersonagem(Personagem p, int x, int y){
        ImageIcon imgicon = new ImageIcon("./imgs/FaseShotDuck/duck.png");
        if(p instanceof Duck){
            p.setImage(imgicon);
            p.setPosicao(x, y);
            p.setTrajetoria(new TrajetoriaDiagonal(p));

            if(p instanceof RedDuck){
                ((RedDuck)p).setImageNormal(imagens.get(2));
                ((RedDuck)p).setImageMorto(imagens.get(7));
            }
            else{
                ((Duck)p).setImageNormal(imagens.get(1));
                ((Duck)p).setImageMorto(imagens.get(6));
            }
            ((Duck)p).atualizarImagem();
            personagens.add(p);
        }
    }

    private void gameover(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        g2D.setColor(new Color(210, 0, 0));
        g2D.setFont(new Font("Comic Sans MS", Font.PLAIN, 64));
        g2D.drawString("FIM DE JOGO", Consts.pixelsLargura / 2 - 240, 200);
        g2D.setColor(Color.green);
        g2D.setStroke(new BasicStroke(3f));
        g2D.fillRect(Consts.pixelsLargura / 2 - 120, 300, 200, 100);
        g2D.fillRect(Consts.pixelsLargura / 2 - 120, 450, 200, 100);
        g2D.setColor(new Color(0, 0, 0));
        g2D.setFont(new Font("Comic Sans MS", Font.PLAIN, 48));
        g2D.drawString("Reiniciar", Consts.pixelsLargura / 2 - 115, 365);
        g2D.drawString("Sair", Consts.pixelsLargura / 2 - 68, 515);
        g2D.drawString("Pontuação: " + pontos, Consts.pixelsLargura / 2 - 170, 700);
    }

}
