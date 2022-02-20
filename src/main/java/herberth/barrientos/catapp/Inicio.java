package herberth.barrientos.catapp;

import java.io.IOException;
import javax.swing.JOptionPane;

public class Inicio {

    public static void main(String[] args) throws IOException {
        int opcionMenu = -1;
        String[] botones = {"1.Ver gatitos ", "2.Ver favoritos", "3.Salir"};

        do {
            String opcion = (String) JOptionPane.showInputDialog(null, "Gatitos Java ", "Menu Principal ", JOptionPane.INFORMATION_MESSAGE,
                    null, botones, botones[0]);

            for (int i = 0; i < botones.length; i++) {
                if (opcion.equals(botones[i])) {
                    opcionMenu = i;

                }
            }

            switch (opcionMenu) {
                case 0:
                    GatosService.verGatos();
                    break;
                    
                case 1: 
                     Gatos gato = new Gatos();
                    GatosService.verFavoritos(gato.getApikey());
                    break;
                   
                default:
                    break;

            }
        } while (opcionMenu != 1);

    }
}
