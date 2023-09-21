package extraordinariapoo;
/*
     * Autor: [Víctor Arroyo Madera]
     * Fecha: [14/06/2023]
*/
class TheaterManager {
    public void run(){
    Theater t = new Theater();
    DispenserManager dm = new DispenserManager();
    WellcomeScreen w = new WellcomeScreen(dm,t);
    while (1 != 0){
    dm.showScreen(30,w);
        }
    }
}
