package extraordinariapoo;
/**
     * Autor: [Víctor Arroyo Madera]
     * Fecha: [14/06/2023]
     * Clase que representa la pantalla de selección de idioma en una aplicación.
     * Permite al usuario elegir entre diferentes idiomas para la interfaz.
 */
class IdiomSelectionScreen extends Screen {

    /**
     * Constructor de la clase.
     * 
     * @param mode El modo de la pantalla
     * @param dm   El administrador del dispensador
     */
    public IdiomSelectionScreen(ScreenMode mode, DispenserManager dm) {
        super("", ScreenMode.optionsMode, dm);
        this.Menu();
    }

    /**
     * Muestra el menú de selección de idioma en la pantalla.
     */
    public void Menu() {
        // Configurar los títulos y opciones del dispensador de acuerdo al idioma activo
        super.getDispenserManager().getDispenser().setTitle(java.util.ResourceBundle.getBundle("extraordinariapoo/" + super.getDispenserManager().getTranslator().getActiveIdiom()).getString("Idioma2"));
        super.getDispenserManager().getDispenser().setOption(0, java.util.ResourceBundle.getBundle("extraordinariapoo/" + super.getDispenserManager().getTranslator().getActiveIdiom()).getString("Castellano"));
        super.getDispenserManager().getDispenser().setOption(1, java.util.ResourceBundle.getBundle("extraordinariapoo/" + super.getDispenserManager().getTranslator().getActiveIdiom()).getString("Ingles"));
        super.getDispenserManager().getDispenser().setOption(2, java.util.ResourceBundle.getBundle("extraordinariapoo/" + super.getDispenserManager().getTranslator().getActiveIdiom()).getString("Catalan"));
        super.getDispenserManager().getDispenser().setOption(3, java.util.ResourceBundle.getBundle("extraordinariapoo/" + super.getDispenserManager().getTranslator().getActiveIdiom()).getString("Euskera"));
        super.getDispenserManager().getDispenser().setOption(4, java.util.ResourceBundle.getBundle("extraordinariapoo/" + super.getDispenserManager().getTranslator().getActiveIdiom()).getString("Cancelar"));
        super.getDispenserManager().getDispenser().setOption(5, null);
        super.getDispenserManager().getDispenser().setImage("ConfigFilesExamples/idiom.jpg");
        super.getDispenserManager().getDispenser().setDescription("");
    }

    /**
     * Maneja el evento de presionar un botón de opción en la pantalla.
     * 
     * @param dh El hardware del dispensador
     * @param c  El carácter del botón presionado
     * @return El resultado de la pantalla
     */
    public ScreenResult optionButtonPressed(DispenserHardware dh, char c) {
        // Actualizar el idioma activo según la opción seleccionada por el usuario
        switch (c) {
            case 'A':
                getDispenserManager().getTranslator().setActiveIdiom("es");
                return ScreenResult.exitScreen;
            case 'B':
                getDispenserManager().getTranslator().setActiveIdiom("en");
                return ScreenResult.exitScreen;
            case 'C':
                getDispenserManager().getTranslator().setActiveIdiom("ca");
                return ScreenResult.exitScreen;
            case 'D':
                getDispenserManager().getTranslator().setActiveIdiom("eu");
                return ScreenResult.exitScreen;
            case 'E':
                return ScreenResult.exitScreen;
        }
        return ScreenResult.continueInScreen;
    }
}
