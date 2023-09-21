package extraordinariapoo;
/**
     * Autor: [Víctor Arroyo Madera]
     * Fecha: [14/06/2023]
     */
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

 /*
 private boolean toCompare(int R, int C,HashSet<Character> carac) {
        boolean check = false;
        Iterator<Character> iter = carac.iterator();
        while (iter.hasNext()) {
        if (!check) {
            char c = iter.next();
            byte rowByteCopy = (byte) ((c & 0xFF00) >> 8);
            byte columnByteCopy = (byte) (c & 0x00FF);
            int rowCopy = (int) rowByteCopy;
            int columnCopy = (int) columnByteCopy;
            if ((rowByteCopy == R) && (columnCopy == C-1) || (rowByteCopy == R) && (columnCopy == C+1)){
                check = true;
            }
        }
    }
    return check;
}*/

/* if (seatState == SeatState.occupied) {
                // Lógica para el caso en que el asiento esté ocupado
            } else if (caracteresSeleccionados.size() >= 4) {
                getDispenserManager().getDispenser().markSeat(row, column, 2);
                caracteresSeleccionados.remove(event);
            }else{
                if (caracteresSeleccionados.size() >= 1){
                    
                    if (toCompare(row, column, (HashSet<Character>) caracteresSeleccionados)){
                        if (!caracteresSeleccionados.contains(event)) {//compruea si está marcada
                            getDispenserManager().getDispenser().markSeat(row, column, 1);
                            caracteresSeleccionados.add(event);
                        } else {
                            getDispenserManager().getDispenser().markSeat(row, column, 2);
                            caracteresSeleccionados.remove(event);
                        }
                } 
                
                
                
            } else{if (!caracteresSeleccionados.contains(event)) {//compruea si está marcada
                            getDispenserManager().getDispenser().markSeat(row, column, 1);
                            caracteresSeleccionados.add(event);
                        } else {
                            getDispenserManager().getDispenser().markSeat(row, column, 2);
                            caracteresSeleccionados.remove(event);
                        }}
        }

        event = getDispenserManager().getDispenser().waitEvent(30);
    }*/


public class SeatSelectionScreen extends Screen {
    private TheaterAreaState selectArea;
    private Map<LocalDate, TheaterState> schedule;
    private LocalDate date;
    private int areaElegida;

    public SeatSelectionScreen(String title, ScreenMode mode, DispenserManager dm) {
        super("", mode, dm);
        this.areaElegida = 0;
    }

 public ScreenResult optionButtonPressed(DispenserHardware dh, char event) {
    Set<Character> caracteresSeleccionados = new HashSet<>();

    while (event != 'A' && event != 'B') {
        if (event == 0) {
            return ScreenResult.exitScreen;
        }
        
        if (event == '1') {
            // Lógica para el caso de '1'
        } else {
            byte rowByte = (byte) ((event & 0xFF00) >> 8);
            byte columnByte = (byte) (event & 0x00FF);
            int row = (int) rowByte;
            int column = (int) columnByte;

            SeatState seatState = schedule.get(date).getArea(areaElegida).getSeat(row - 1, column - 1);

            if (seatState == SeatState.occupied) {
                // Lógica para el caso en que el asiento esté ocupado
            } else if (caracteresSeleccionados.size() >= 4) {
                getDispenserManager().getDispenser().markSeat(row, column, 2);
                caracteresSeleccionados.remove(event);
            } else if (!caracteresSeleccionados.contains(event)) {
                getDispenserManager().getDispenser().markSeat(row, column, 1);
                caracteresSeleccionados.add(event);
            } else {
                getDispenserManager().getDispenser().markSeat(row, column, 2);
                caracteresSeleccionados.remove(event);
            }
        }

        event = getDispenserManager().getDispenser().waitEvent(30);
    }

    if (event == 'B' && !caracteresSeleccionados.isEmpty()) {
        PaymentScreen ps = new PaymentScreen("", ScreenMode.messageMode, getDispenserManager(), date, areaElegida, schedule, new ArrayList<>(caracteresSeleccionados));
        getDispenserManager().showScreen(30, ps);
    } else {
        // Lógica para el caso en que no haya elementos en caracteresSeleccionados
    }

    return ScreenResult.exitScreen;
}


    public void setDateChoose(LocalDate date) {
        this.date = date;
    }

    public void setSchedule(Map<LocalDate, TheaterState> schedule) {
        this.schedule = schedule;
    }

    public Map<LocalDate, TheaterState> getSchedule() {
        return schedule;
    }

    public int getAreaElegida() {
        return areaElegida;
    }

    public void setAreaElegida(int areaElegida) {
        this.areaElegida = areaElegida;
    }

    public LocalDate getDateChoose() {
        return date;
    }

   

   

   
}
