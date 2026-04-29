package Controler;

import Auxiliar.Posicao;
import Modelo.Hero;
import Modelo.Personagem;

import java.awt.Graphics;
import java.io.Serializable;
import java.util.ArrayList;

public class ControleDeJogo implements Serializable{
    
    public void desenhaTudo(Graphics g, ArrayList<Personagem> e, ArrayList<Mensagem> msg, int contador, int CELL_SIDE, int PERIOD) {
        if(e != null){
            for (int i = 0; i < e.size(); i++)
                e.get(i).autoDesenho(g, contador, CELL_SIDE, PERIOD);
        }

        if(msg != null){
            for(int i = 0; i < msg.size(); i++){
                msg.get(i).autoDesenho(g, CELL_SIDE);
            }
        }
    }

    public void processaTudo(ArrayList<Personagem> umaFase) {
        Hero hero = (Hero) umaFase.get(0);
        Personagem pIesimoPersonagem;
        
        for (int i = 1; i < umaFase.size(); i++) {
            pIesimoPersonagem = umaFase.get(i);
            
            if (hero.getPosicao().igual(pIesimoPersonagem.getPosicao())) {
                if (pIesimoPersonagem.isbTransponivel()) /*TO-DO: verificar se o personagem eh mortal antes de retirar*/ {
                    if (pIesimoPersonagem.isbMortal())
                    umaFase.remove(pIesimoPersonagem);
                }
            }
        }
    }

    /*Retorna true se a posicao p é válida para Hero com relacao a todos os personagens no array*/
    public boolean ehPosicaoValida(ArrayList<Personagem> umaFase, Posicao p) {
        if(umaFase == null){
            return true;
        }

        Personagem pIesimoPersonagem;
        for (int i = 1; i < umaFase.size(); i++) {
            pIesimoPersonagem = umaFase.get(i);
            if (!pIesimoPersonagem.isbTransponivel()) {
                if (pIesimoPersonagem.getPosicao().igual(p)) {
                    return false;
                }
            }
        }
        return true;
    }
}