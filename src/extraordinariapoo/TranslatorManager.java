package extraordinariapoo;
/*
     * Autor: [Víctor Arroyo Madera]
     * Fecha: [14/06/2023]
*/
class TranslatorManager {
    private String activeIdiom;

    public TranslatorManager(String idiom){
        activeIdiom = idiom;     
    }
    
    public String getActiveIdiom() {
        return activeIdiom;
    }

    public void setActiveIdiom(String activeIdiom) {
        this.activeIdiom = activeIdiom;
    }
}



