package extraordinariapoo;
/*
     * Autor: [Víctor Arroyo Madera]
     * Fecha: [14/06/2023]
     * Clase que representa la pantalla de selección de idioma en una aplicación.
     * Permite al usuario elegir entre diferentes idiomas para la interfaz.
 */
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import javax.naming.CommunicationException;
import urjc.UrjcBankServer;

public class PaymentScreen extends Screen {
    private UrjcBankServer bank;
    private int price;
    private int areaElegida;
    private LocalDate date;
    private Map<LocalDate, TheaterState> schedule;
    private List<Character> listaentradas;
    private String title;
    private ScreenMode mode;
    private DispenserManager dm;
    private float newprice;

    public PaymentScreen(String title, ScreenMode mode, DispenserManager dm, LocalDate date, int area, Map<LocalDate, TheaterState> schedule, List<Character> listacaracteres) {
        super("", mode, dm);
        this.areaElegida = area;
        this.date = date;
        this.bank = new UrjcBankServer();
        this.schedule = schedule;
        this.listaentradas = listacaracteres;
        this.title = title;
        this.mode = mode;
        this.dm = dm;
    }

    public UrjcBankServer getBanck() {
        return bank;
    }

    public ScreenResult optionButtonPressed(DispenserHardware d, char option) {
        Theater t = new Theater();
        this.price = t.getArea(areaElegida).getPrice();

        switch (option) {
            case 0:
                return ScreenResult.exitScreen;
            case '1':
                getDispenserManager().getDispenser().retainCreditCard(false);
                long cardNum = getDispenserManager().getDispenser().getCardNumber();

                try {
                    if (!bank.doOperation(cardNum, price)) {
                        ErrorScreen er = new ErrorScreen("", mode, dm);
                        getDispenserManager().getDispenser().setTitle(java.util.ResourceBundle.getBundle("extraordinariapoo/" + getDispenserManager().getTranslator().getActiveIdiom()).getString("Error"));
                        getDispenserManager().getDispenser().setDescription(java.util.ResourceBundle.getBundle("extraordinariapoo/" + getDispenserManager().getTranslator().getActiveIdiom()).getString("Comunicacion"));
                        getDispenserManager().getDispenser().setOption(0, java.util.ResourceBundle.getBundle("extraordinariapoo/" + getDispenserManager().getTranslator().getActiveIdiom()).getString("Volver"));
                        getDispenserManager().getDispenser().setOption(1, null);

                        if (getDispenserManager().getDispenser().expelCreditCard(30)) {

                        } else {
                            getDispenserManager().getDispenser().retainCreditCard(true);
                        }

                        char volver = getDispenserManager().getDispenser().waitEvent(30);
                        er.optionButtonPressed(getDispenserManager().getHardware(), volver);
                        break;
                    } else {
                        if (date.getDayOfWeek() == DayOfWeek.SATURDAY) {
                            newprice = price * t.getfestivo();
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
                        if (t.getArea(areaElegida).getDescuento() != 0){
                        this.price = price - (price * t.getArea(areaElegida).getDescuento()/100);}
                        Random random = new Random();
                        int hour = random.nextInt(6) + 15; // Generate random hour between 15 and 20
                        int minute = random.nextInt(60); // Generate random minute
                        LocalTime time = LocalTime.of(hour, minute);
                        String timeString = time.toString();
                        for (int i = 0; i < listaentradas.size(); i++) {
                            char event = listaentradas.get(i);
                            byte byteF = (byte) ((event & 0xFF00) >> 8);
                            byte byteC = (byte) (event & 0x00FF);
                            int F = (int) byteF;
                            int C = (int) byteC;

                            List<String> text = new ArrayList<>();
                            text.add("   "+ java.util.ResourceBundle.getBundle("extraordinariapoo/" + getDispenserManager().getTranslator().getActiveIdiom()).getString("Entradas") + " " + java.util.ResourceBundle.getBundle("extraordinariapoo/" + getDispenserManager().getTranslator().getActiveIdiom()).getString("Titulo"));
                            text.add("   =========================");
                            text.add("   " + java.util.ResourceBundle.getBundle("extraordinariapoo/" + getDispenserManager().getTranslator().getActiveIdiom()).getString(schedule.get(date).getArea(areaElegida).getName()));
                            text.add("   " + java.util.ResourceBundle.getBundle("extraordinariapoo/" + getDispenserManager().getTranslator().getActiveIdiom()).getString("Dia") + ": " + date.getDayOfMonth() + " " +date.getMonth().getDisplayName(TextStyle.FULL, new Locale(getDispenserManager().getTranslator().getActiveIdiom())) + " " + date.getYear());
                            text.add("   " + java.util.ResourceBundle.getBundle("extraordinariapoo/" + getDispenserManager().getTranslator().getActiveIdiom()).getString("Hora") + timeString);
                            text.add("   " + java.util.ResourceBundle.getBundle("extraordinariapoo/" + getDispenserManager().getTranslator().getActiveIdiom()).getString("Fila") + F);
                            text.add("   " + java.util.ResourceBundle.getBundle("extraordinariapoo/" + getDispenserManager().getTranslator().getActiveIdiom()).getString("Asiento") + C);
                            text.add("   " + java.util.ResourceBundle.getBundle("extraordinariapoo/" + getDispenserManager().getTranslator().getActiveIdiom()).getString("Precio") + ":" + price + "€" + "(-" + t.getArea(areaElegida).getDescuento() + "%)");
                            getDispenserManager().getDispenser().print(text);
                        }

                        getDispenserManager().getDispenser().setTitle(java.util.ResourceBundle.getBundle("extraordinariapoo/" + getDispenserManager().getTranslator().getActiveIdiom()).getString("Rtarjeta"));
                        getDispenserManager().getDispenser().setDescription(java.util.ResourceBundle.getBundle("extraordinariapoo/" + getDispenserManager().getTranslator().getActiveIdiom()).getString("Tarjetaout"));
                        getDispenserManager().getDispenser().setOption(0, null);
                        getDispenserManager().getDispenser().setOption(1, null);
                        if (getDispenserManager().getDispenser().expelCreditCard(30)) {
                        } else {
                            getDispenserManager().getDispenser().retainCreditCard(true);
                        }

                        for (int i = 0; i < listaentradas.size(); i++) {
                            char event = listaentradas.get(i);
                            byte byteF = (byte) ((event & 0xFF00) >> 8);
                            byte byteC = (byte) (event & 0x00FF);
                            int F = (int) byteF;
                            int C = (int) byteC;
                            schedule.get(date).getArea(areaElegida).setSeat(F - 1, C - 1, SeatState.occupied);
                        }

                        String filename = "data/Storage.bin";
                        try (FileOutputStream fos = new FileOutputStream(filename);
                             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
                            oos.writeObject(schedule);
                            break;
                        } catch (IOException e) {

                        }

                    }

                } catch (CommunicationException ex) {

                }

        }

        return null;

    }

    public void setSchedule(Map<LocalDate, TheaterState> schedule) {
        this.schedule = schedule;
    }

    public ScreenResult creditCardDetected(DispenserHardware d) {
        
        return null;
    }
}
