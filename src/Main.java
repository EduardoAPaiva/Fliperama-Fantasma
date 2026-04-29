import Controler.Tela;

public class Main {

    public static void main(String[] args) {
        //System.setProperty("sun.java2d.opengl", "true");
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Tela tTela = new Tela();
                tTela.setVisible(true);
                tTela.createBufferStrategy(2);
                tTela.go();
                
            }
        });
    }
}
