package extraordinariapoo;
/*
     * Autor: [Víctor Arroyo Madera]
     * Fecha: [14/06/2023]
*/
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public class TheaterArea implements Serializable{
    private int rows;
    private int cols;
    private String name;
    private SeatState[][] seats;
    private int price;
    private int descuento;

    public TheaterArea(String fileName, String name, int price, int descuento) throws IOException {
        read("ConfigFilesExamples/" + fileName);
        this.price = price;
        this.name = name;
        this.descuento = descuento;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public String getName() {
        return name;
    }
    public int getDescuento(){
    return descuento;}
    
    
    public SeatState getSeat(int row, int col) {
        if (row >= 0 && row < rows && col >= 0 && col < cols) {
            return seats[row][col];
        } else {
            throw new IndexOutOfBoundsException("Invalid seat position");
        }
    }

    public int getPrice() {
        return price;
    }

    public void read(String filename) throws FileNotFoundException, IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            ArrayList<String> areaInfo = new ArrayList<>();

            while ((line = br.readLine()) != null) {
                areaInfo.add(line);
            }

            int maxLength = 0;
            for (String element : areaInfo) {  //cogemos la fila más grande
                int length = element.length();
                if (length > maxLength) {
                    maxLength = length;
                }
            }

            rows = areaInfo.size();
            cols = maxLength;
            seats = new SeatState[rows][cols];

            for (int i = 0; i < rows; i++) {
                String row = areaInfo.get(i);
                for (int j = 0; j < row.length(); j++) {
                    char seatChar = row.charAt(j);
                    if (seatChar == '*') {
                        seats[i][j] = SeatState.free;
                    } else if (seatChar == ' ') {
                        seats[i][j] = null;
                    }
                }
            }
        }
    }
}
