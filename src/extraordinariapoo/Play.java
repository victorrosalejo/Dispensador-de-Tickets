package extraordinariapoo;
/**
     * Autor: [Víctor Arroyo Madera]
     * Fecha: [14/06/2023]
     */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

class Play {
    private String title;
    private String image;
    private String description;
    private String filePath = "ConfigFilesExamples/play.txt";

    // Constructor de la clase Play
    public Play() {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            // Leer cada línea del archivo
            while ((line = br.readLine()) != null) {
                // Dividir la línea en clave y valor usando el separador ":"
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    String key = parts[0].trim();
                    String value = parts[1].trim();

                    // Asignar los valores correspondientes a los atributos adecuados
                    switch (key) {
                        case "play_name":
                            title = value;
                            break;
                        case "play_poster":
                            image = "ConfigFilesExamples/" + value;
                            break;
                        case "description":
                            description = value;
                            break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para obtener el título de la obra
    public String getTitle() {
        return title;
    }

    // Método para obtener la ruta de la imagen del cartel de la obra
    public String getImage() {
        return image;
    }

    // Método para obtener la descripción de la obra
    public String getDescription() {
        return description;
    }
}
