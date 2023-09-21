package extraordinariapoo;
/**
     * Autor: [Víctor Arroyo Madera]
     * Fecha: [14/06/2023]
     */

import sienens.TheaterTicketDispenser;

public class DispenserManager {
    
    private TranslatorManager traslator; // Instancia de TranslatorManager que maneja la traducción de idiomas.
    private TheaterTicketDispenser dispenser; // Instancia de TheaterTicketDispenser que representa el dispensador de boletos del teatro.
    private DispenserHardware hardware; // Instancia de DispenserHardware que representa el hardware del dispensador de boletos.
    
    public DispenserManager(){
        hardware = new DispenserHardware(); // Crea una nueva instancia de DispenserHardware.
        dispenser = new TheaterTicketDispenser(); // Crea una nueva instancia de TheaterTicketDispenser.
        traslator = new TranslatorManager("es"); // Crea una nueva instancia de TranslatorManager con el idioma "es" (español).
    }
    
    /**
     * Muestra una pantalla en el dispensador de boletos durante un tiempo específico.
     * 
     * @param time El tiempo en segundos que se mostrará la pantalla.
     * @param s La pantalla a mostrar.
     */
    public void showScreen(int time, Screen s){
        ScreenResult result = s.begin(hardware); // Inicia la pantalla y obtiene el resultado.
        ScreenMode modeResult = s.getScreenMode(); // Obtiene el modo de la pantalla.
        
        switch (modeResult) {
            
            case optionsMode:
                dispenser.setMenuMode(); // Configura el modo del dispensador a modo de menú.
                s.Menu(); // Muestra el menú en la pantalla.
                
                while (result == ScreenResult.continueInScreen) {
                    char event = dispenser.waitEvent(30); // Espera un evento del dispensador durante 30 segundos.
                    
                    switch (event) {
                        case 0:
                            result = ScreenResult.exitScreen; // Sale de la pantalla.
                            break;
                        case '1':
                            // Por definir (No hay implementación actual)
                            break;
                        default:
                            result = s.optionButtonPressed(hardware, event); // Maneja el evento del botón de opción en la pantalla.
                            break;
                    }
                }
                break;
        
            case messageMode:
                PaymentScreen ps = (PaymentScreen) s;
                dispenser.setMessageMode(); // Configura el modo del dispensador a modo de mensaje.
                
                if (!ps.getBanck().comunicationAvaiable()){
                    ErrorScreen er = new ErrorScreen("", s.getScreenMode(), s.getDispenserManager());
                    dispenser.setTitle(java.util.ResourceBundle.getBundle("extraordinariapoo/" + getTranslator().getActiveIdiom()).getString("Error"));
                    dispenser.setDescription(java.util.ResourceBundle.getBundle("extraordinariapoo/" + getTranslator().getActiveIdiom()).getString("Comunicacion"));
                    dispenser.setOption(0, java.util.ResourceBundle.getBundle("extraordinariapoo/" + getTranslator().getActiveIdiom()).getString("Volver"));
                    dispenser.setOption(1, null);
                    char volver = dispenser.waitEvent(30);
                    
                    er.optionButtonPressed(hardware, volver);
                    break;
                } else {
                    dispenser.setTitle(java.util.ResourceBundle.getBundle("extraordinariapoo/" + getTranslator().getActiveIdiom()).getString("InsertarTarjeta"));
                    dispenser.setOption(0, java.util.ResourceBundle.getBundle("extraordinariapoo/" + getTranslator().getActiveIdiom()).getString("Cancelar"));
                    dispenser.setOption(1, null);
                    char eventCard = dispenser.waitEvent(30);
                    ps.optionButtonPressed(hardware, eventCard);
                    break;
                }
            
            case theaterMode:
                SeatSelectionScreen swNew = (SeatSelectionScreen) s;
                int row = swNew.getSchedule().get(swNew.getDateChoose()).getArea(swNew.getAreaElegida()).getRows();
                int col = swNew.getSchedule().get(swNew.getDateChoose()).getArea(swNew.getAreaElegida()).getCols();

                dispenser.setTheaterMode(row, col); // Configura el modo del dispensador a modo de teatro.

                for (int i = 1; i <= row; i++) {
                    for (int j = 1; j <= col; j++) {
                        SeatState st = swNew.getSchedule().get(swNew.getDateChoose()).getArea(swNew.getAreaElegida()).getSeat(i - 1, j - 1);
                        
                        if (st == SeatState.free) {
                            dispenser.markSeat(i, j, 2); // Marca un asiento libre en el dispensador.
                        } else if (st == SeatState.occupied) {
                            dispenser.markSeat(i, j, 1); // Marca un asiento ocupado en el dispensador.
                        } else {
                            dispenser.markSeat(i, j, 0); // Marca un asiento no disponible en el dispensador.
                        }
                    }
                }

                dispenser.setOption(0, java.util.ResourceBundle.getBundle("extraordinariapoo/" + getTranslator().getActiveIdiom()).getString("Cancelar"));
                dispenser.setOption(1, java.util.ResourceBundle.getBundle("extraordinariapoo/" + getTranslator().getActiveIdiom()).getString("Compra"));
                char event = dispenser.waitEvent(30);
                s.optionButtonPressed(hardware, event);
                break;
        }
    }
    
    /**
     * Obtiene el TranslatorManager asociado a este DispenserManager.
     * 
     * @return El TranslatorManager asociado.
     */
    public TranslatorManager getTranslator(){
        return traslator;
    }

    /**
     * Obtiene el TheaterTicketDispenser asociado a este DispenserManager.
     * 
     * @return El TheaterTicketDispenser asociado.
     */
    public TheaterTicketDispenser getDispenser() {
        return dispenser;
    }
    
    /**
     * Obtiene el DispenserHardware asociado a este DispenserManager.
     * 
     * @return El DispenserHardware asociado.
     */
    public DispenserHardware getHardware() {
        return hardware;
    }        
}
