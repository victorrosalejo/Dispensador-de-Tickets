package extraordinariapoo;

import java.io.Serializable;
/*
     * Autor: [Víctor Arroyo Madera]
     * Fecha: [14/06/2023]
*/
public class TheaterAreaState implements Serializable {
    private SeatState[][] seatsState;
    private String name;
    private int cols;
    private int rows;

    public TheaterAreaState(TheaterArea area) {
        this.name = area.getName();
        this.cols = area.getCols();
        this.rows = area.getRows();
        this.seatsState = new SeatState[rows][cols];
        
        // Inicializar los estados de los asientos con los valores del área
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                seatsState[row][col] = area.getSeat(row, col);
            }
        }
    }

    public String getName() {
        return name;
    }

    public SeatState getSeat(int row, int col) {
        if (row >= 0 && row < rows && col >= 0 && col < cols) {
            return seatsState[row][col];
        } else {
            throw new IndexOutOfBoundsException("Invalid seat position");
        }
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public void setSeat(int row, int col, SeatState state) {
        if (row >= 0 && row < rows && col >= 0 && col < cols) {
            seatsState[row][col] = state;
        }
    }
}
