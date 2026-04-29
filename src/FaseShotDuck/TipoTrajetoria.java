package FaseShotDuck;

import java.util.Random;

public enum TipoTrajetoria {
    PARABOLICA,
    CUBICA,
    ESPIRAL;
    
    private static final Random rand = new Random();

    public static TipoTrajetoria sorteia() {
        TipoTrajetoria[] valores = values();
        return valores[rand.nextInt(valores.length)];
    }
}
