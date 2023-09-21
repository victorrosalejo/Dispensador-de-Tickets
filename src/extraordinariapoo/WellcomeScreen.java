package extraordinariapoo;

import java.util.List;

class WellcomeScreen extends Screen {
    private Theater theater;
    private DateSectionScreen ds;
    private IdiomSelectionScreen is;
    private Play p;

    public WellcomeScreen(DispenserManager dm, Theater t) {
        super("", ScreenMode.optionsMode, dm);  // Llama al constructor de la clase padre
        this.theater = t;
        this.p = new Play();
        this.is = new IdiomSelectionScreen(ScreenMode.optionsMode, dm);
        this.ds = new DateSectionScreen(theater, ScreenMode.optionsMode, dm);
        List<String> optionsAux = getOptions();
        optionsAux.add(is.getTitle());
        optionsAux.add(ds.getTitle());
        setOptions(optionsAux);
    }
    //La clase menu se encarga de imprimir por pantalla lo correpondiente a la misma
    public void Menu() {
        super.getDispenserManager().getDispenser().setTitle(p.getTitle());
        super.getDispenserManager().getDispenser().setOption(0, java.util.ResourceBundle.getBundle("extraordinariapoo/" + super.getDispenserManager().getTranslator().getActiveIdiom()).getString("Idioma"));
        super.getDispenserManager().getDispenser().setOption(1, java.util.ResourceBundle.getBundle("extraordinariapoo/" + super.getDispenserManager().getTranslator().getActiveIdiom()).getString("Fecha"));
        super.getDispenserManager().getDispenser().setOption(2, null);
        super.getDispenserManager().getDispenser().setOption(3, null);
        super.getDispenserManager().getDispenser().setOption(4, null);
        super.getDispenserManager().getDispenser().setOption(5, null);
        getDispenserManager().getDispenser().setDescription(getDescription());
        getDispenserManager().getDispenser().setImage(getImage());
    }

    public String getTitle() {
        return p.getTitle();
    }

    public String getDescription() {
        return p.getDescription();
    }

    public String getImage() {
        return p.getImage();
    }

    public ScreenResult optionButtonPressed(DispenserHardware dh, char c) {
        switch (c) {
            case 'A':
                getDispenserManager().showScreen(30, is);
                break;
            case 'B':
                getDispenserManager().showScreen(30, ds);
                break;
        }

        return ScreenResult.exitScreen;
    }
}
