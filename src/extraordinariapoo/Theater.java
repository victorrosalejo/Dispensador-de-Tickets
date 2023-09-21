package extraordinariapoo;
/*
     * Autor: [Víctor Arroyo Madera]
     * Fecha: [14/06/2023]
*/
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class Theater implements Serializable {
    private ArrayList<TheaterArea> theaterAreas;
    private String theaterImage;
    private float festivos;
     
   public Theater(){
        theaterAreas = new ArrayList<>();
        theaterImage = new String();
        read("ConfigFilesExamples");
    }
    
    public float getfestivo(){
    return festivos;
    }

    public int getNumAreas() {
        return theaterAreas.size();
    }

    public TheaterArea getArea(int pos) {
        return theaterAreas.get(pos);
    }

    
public void read(String theatreDir) {
    String filePath = theatreDir + "/theater.txt";
    String theaterImage = null; // Variable para almacenar la ruta de la imagen del teatro
    try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8))) {
        String line;
        
        while ((line = br.readLine()) != null) {
            
            if (line.startsWith("Area:")) {
                 String[] areaInfo = line.substring("Area:".length()).split(";");
                 
                if (areaInfo.length == 3) {
                    String areaName = areaInfo[0];
                    String areaPriceString = areaInfo[1].replaceAll("[^0-9.€]", "").replace("€", "");
                    double areaPrice = Double.parseDouble(areaPriceString);
                    String areaFile = areaInfo[2];
                    int areaDescuento = 0;
                    TheaterArea area = new TheaterArea(areaFile, areaName, (int) areaPrice, areaDescuento);
                    theaterAreas.add(area);
                }else if (areaInfo.length == 4){
                    String areaName = areaInfo[0];
                    String areaPriceString = areaInfo[1].replaceAll("[^0-9.€]", "").replace("€", "");
                    double areaPrice = Double.parseDouble(areaPriceString);
                    String areaFile = areaInfo[3];
                    String areaDescuentoString = areaInfo[2];
                    areaDescuentoString = areaDescuentoString.replace("%", "");
                    int descuento = Integer.parseInt(areaDescuentoString);
                    TheaterArea area = new TheaterArea(areaFile, areaName, (int) areaPrice, (int) descuento);
                    theaterAreas.add(area);
                
                }
            } else if (line.startsWith("TheaterPlaneImageFile:")) {
                theaterImage = line.substring("TheaterPlaneImageFile:".length()); // Almacenar la ruta de la imagen del teatro
                this.theaterImage = theaterImage;
            } else if (line.startsWith("festivo:")) {   
                String festivosString = line.substring("festivo:".length()); // Almacenar el valor de festivo como cadena
                this.festivos = Float.parseFloat(festivosString); // Convertir la cadena a float
                
            }
            
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
    // Utiliza el valor de theaterImage para cargar la imagen del teatro
}

public String getImagen(){
return "ConfigFilesExamples/" + theaterImage;
}

}
