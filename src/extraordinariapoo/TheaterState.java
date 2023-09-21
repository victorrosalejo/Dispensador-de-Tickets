package extraordinariapoo;
/*
     * Autor: [Víctor Arroyo Madera]
     * Fecha: [14/06/2023]
*/
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TheaterState implements Serializable {
    private Theater theater;
    private LocalDate date;
    private List<TheaterAreaState> areas;

    public TheaterState(Theater theater, LocalDate date) {
        this.theater = theater;
        this.date = date;
        this.areas = new ArrayList<>();
        
        // Crear los estados de las áreas del teatro
        for (int i = 0; i < 5; i++) {
            TheaterAreaState nuevo = new TheaterAreaState(theater.getArea(i));
            areas.add(nuevo);
        }
    }

    public LocalDate getDate() {
        return date;
    }

    public int getNumAreas() {
        return theater.getNumAreas();
    }

    public TheaterAreaState getArea(int pos) {
        return areas.get(pos);
    }
    
    public String getImagen() {
        return theater.getImagen();
    }
}
