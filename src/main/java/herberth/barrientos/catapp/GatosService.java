package herberth.barrientos.catapp;

import com.google.gson.Gson;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class GatosService {

    public static void verGatos() throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("https://api.thecatapi.com/v1/images/search").get().build();
        Response response = client.newCall(request).execute();

        String cJson = response.body().string();

        cJson = cJson.substring(1, cJson.length());
        cJson = cJson.substring(0, cJson.length() - 1);

        Gson gson = new Gson();
        Gatos gatos = gson.fromJson(cJson, Gatos.class);

        Image image = null;
        try {
            URL url = new URL(gatos.getUrl());
            image = ImageIO.read(url);

            ImageIcon fondoGato = new ImageIcon(image);

            if (fondoGato.getIconWidth() > 800) {
                Image fondo = fondoGato.getImage();
                Image modificada = fondo.getScaledInstance(800, 600, java.awt.Image.SCALE_SMOOTH);
                fondoGato = new ImageIcon(modificada);
            }

            String menu = "Opciones: \n" + " 1.Ver otra imagen \n" + " 2.Favoritos \n" + " 3. Volver \n";

            String[] botones = {"Ver otra imagen", "Favorito", "Ver favoritos", "Volver"};
            String idGato = gatos.getId();
            String opcion = (String) JOptionPane.showInputDialog(null, menu, idGato, JOptionPane.INFORMATION_MESSAGE, fondoGato, botones, botones[0]);

            int seleccion = -1;

            for (int i = 0; i < botones.length; i++) {
                if (opcion.equals(botones[i])) {
                    seleccion = i;

                }
            }
            switch (seleccion) {
                case 0:
                    verGatos();
                    break;
                case 1:
                    favoritoGato(gatos);
                    break;

                default:
                    break;
            }

        } catch (IOException e) {
            System.out.println("e = " + e);
        }
    }

    public static void favoritoGato(Gatos gato) {
        try {
            OkHttpClient client = new OkHttpClient();

            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, "{\r\n    \"image_id\":\"+gato.getId+\"\r\n}");
            Request request = new Request.Builder()
                    .url("https://api.thecatapi.com/v1/favourites?Content-Type=application/json")
                    .method("POST", body)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("x-api-key", gato.getApikey())
                    .build();
            Response response = client.newCall(request).execute();

        } catch (IOException e) {
            System.out.println("e = " + e);
        }

    }

    public static void verFavoritos(String apiKey) throws IOException {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.thecatapi.com/v1/favourites")
                .get()
                .addHeader("Content-Type", "application/json")
                .addHeader("x-api-key", apiKey)
                .build();
        Response response = client.newCall(request).execute();

        String sJson = response.body().string();
        Gson gson = new Gson();
        GatosFav[] gatosArray = gson.fromJson(sJson, GatosFav[].class);

        if (gatosArray.length > 0) {
            int min = 1;
            int max = gatosArray.length;
            int aleatorio = (int) (Math.random() * ((max - min) + 1)) + min;
            int indice = aleatorio - 1;

            GatosFav gatoFav = gatosArray[indice];

            ///////////////////////////////7
            Image image = null;
            try {
                URL url = new URL(gatoFav.getImage().getUrl());
                image = ImageIO.read(url);

                ImageIcon fondoGato = new ImageIcon(image);

                if (fondoGato.getIconWidth() > 800) {
                    Image fondo = fondoGato.getImage();
                    Image modificada = fondo.getScaledInstance(800, 600, java.awt.Image.SCALE_SMOOTH);
                    fondoGato = new ImageIcon(modificada);
                }

                String menu = "Opciones: \n" + " 1.Ver otra imagen \n" + " 2.Eliminar Favoritos \n" + " 3. Volver \n";

                String[] botones = {"Ver otra imagen", "Favorito", "Volver"};
                String idGato = gatoFav.getId();
                String opcion = (String) JOptionPane.showInputDialog(null, menu, idGato, JOptionPane.INFORMATION_MESSAGE, fondoGato, botones, botones[0]);

                int seleccion = -1;

                for (int i = 0; i < botones.length; i++) {
                    if (opcion.equals(botones[i])) {
                        seleccion = i;

                    }
                }
                switch (seleccion) {
                    case 0:
                        verFavoritos(apiKey);

                        break;
                    case 1:
                        borrarFavorito(gatoFav);
                        break;

                    default:
                        break;
                }

            } catch (IOException e) {
                System.out.println("e = " + e);
            }

        }

    }

    public static void borrarFavorito(GatosFav fav) {
    }

}
