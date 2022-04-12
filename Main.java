import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.nio.file.Files;

public class Main {
    public static void main(String[] args) {

        // Validador del archivo
        String regex = "^(\\d{1,4}|10000)\\n(\\d+\\s\\d+\\n?)+$";
        Pattern p = Pattern.compile(regex);

        try {

            String contentStr = Files.readString(Paths.get("in.txt"));
            Matcher m = p.matcher(contentStr);

            // Si el archivo es valido
            if (m.matches()) {
                // Lectura de archivo
                String[] content = contentStr.split("\n");

                // Lee los datos y los separa
                int rondas = Integer.parseInt(content[0]);

                if (rondas + 1 != content.length) {
                    System.out.println(rondas + " no coincide con el numero de rondas.");
                    System.exit(0);
                }

                int[] ventaja = new int[rondas];
                int[] ganador = new int[rondas];

                for (int i = 1; i <= rondas; i++) {

                    String[] aux = content[i].split(" ");
                    int x = Integer.parseInt(aux[0]);
                    int y = Integer.parseInt(aux[1]);

                    ventaja[i - 1] = x - y;

                    if (ventaja[i - 1] > 0) {
                        // Gana jugador 1
                        ganador[i - 1] = 1;
                    } else {
                        // Gana jugador 2
                        ganador[i - 1] = 2;
                        ventaja[i - 1] = ventaja[i - 1] * -1;
                    }
                }
                int max = ventaja[0];
                int win = ganador[0];

                for (int i = 1; i < rondas; i++) {
                    if (max < ventaja[i]) {
                        max = ventaja[i];
                        win =  ganador[i];
                    }
                }

                // Creacion de archivo
                FileWriter outFile = new FileWriter("out.txt");
                outFile.write(win + " " + max);

                outFile.close();
            } else {
                System.out.println("Problema en la estructura del archivo");
            }

        } catch (IOException e) {
            System.out.println("Problema en la lectura o guardado de archivo: " + e.getMessage());
            ;
        }
    }
}