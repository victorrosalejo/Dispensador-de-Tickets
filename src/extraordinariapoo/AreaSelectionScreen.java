package extraordinariapoo;

/**
 * Autor: [Víctor Arroyo Madera]
 * Fecha: [14/06/2023]s
 */
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Map;

public class AreaSelectionScreen extends Screen {
    private LocalDate date;
    private Map<LocalDate, TheaterState> schedule;
    private SeatSelectionScreen sw;
    private float newprice;
    private int price;

    public AreaSelectionScreen(String title, ScreenMode mode, DispenserManager dm) {
        super("", ScreenMode.optionsMode, dm);
        this.sw = new SeatSelectionScreen("", mode, dm);
    }

    /**
     * Muestra el menú de selección de área en la pantalla.
     */
    public void Menu() {
        Theater t = new Theater();

        super.getDispenserManager().getDispenser().setTitle(java.util.ResourceBundle.getBundle("extraordinariapoo/" + super.getDispenserManager().getTranslator().getActiveIdiom()).getString("Butacas"));
        for (int i = 0; i < 5; i++) {
            this.price = t.getArea(i).getPrice();
            if (date.getDayOfWeek() == DayOfWeek.SATURDAY) {
                newprice = t.getArea(i).getPrice() * t.getfestivo();
                int newPriceInt = (int) newprice;
                this.price = newPriceInt;
            } else if (date.getDayOfWeek() == DayOfWeek.FRIDAY) {
                newprice = price * t.getfestivo();
                int newPriceInt = (int) newprice;
                this.price = newPriceInt;
            } else if (date.getDayOfWeek() == DayOfWeek.SUNDAY) {
                newprice = price * t.getfestivo();
                int newPriceInt = (int) newprice;
                this.price = newPriceInt;
            }
            super.getDispenserManager().getDispenser().setOption(i, java.util.ResourceBundle.getBundle("extraordinariapoo/" + super.getDispenserManager().getTranslator().getActiveIdiom()).getString(schedule.get(date).getArea(i).getName()) + " (" + price + " €)" + " -" + t.getArea(i).getDescuento() +"%");
        }
        super.getDispenserManager().getDispenser().setImage(schedule.get(date).getImagen());
    }

    /**
     * Maneja el evento de presionar un botón de opción en la pantalla.
     * 
     * @param d El hardware del dispensador
     * @param c El carácter del botón presionado
     * @return El resultado de la pantalla
     */
    public ScreenResult optionButtonPressed(DispenserHardware d, char c) {
        this.sw.setSchedule(schedule);
        this.sw.setDateChoose(date);
        switch (c) {
            case 'A':
                sw.setAreaElegida(0);
                getDispenserManager().showScreen(30, this.sw);
                break;
            case 'B':
                sw.setAreaElegida(1);
                getDispenserManager().showScreen(30, this.sw);
                break;
            case 'C':
                sw.setAreaElegida(2);
                getDispenserManager().showScreen(30, this.sw);
                break;
            case 'D':
                sw.setAreaElegida(3);
                getDispenserManager().showScreen(30, this.sw);
                break;
            case 'E':
                sw.setAreaElegida(4);
                getDispenserManager().showScreen(30, this.sw);
                break;
            case 'F':
                return ScreenResult.exitScreen;
        }
        return ScreenResult.exitScreen;
    }

    public Map<LocalDate, TheaterState> getSchedule() {
        return schedule;
    }

    public void setSchedule(Map<LocalDate, TheaterState> schedule) {
        this.schedule = schedule;
    }

    public void setDateChoose(LocalDate date) {
        this.date = date;
    }

    public LocalDate getDate() {
        return date;
    }
}
