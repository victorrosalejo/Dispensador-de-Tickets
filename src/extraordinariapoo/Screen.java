
package extraordinariapoo;
/**
     * Autor: [Víctor Arroyo Madera]
     * Fecha: [14/06/2023]
     */
import java.util.ArrayList;
import java.util.List;

public class Screen {
    private DispenserManager dispenseManager;
    private String title;
    private String description;
    private String image;
    private List<String> options;
    private ScreenMode mode; //crear clase especial
    
      
    //Constructor ???
    public Screen(String title ,ScreenMode mode , DispenserManager dispenseManager ){
        this.title = title;
        this.mode = mode;
        this.dispenseManager = dispenseManager;
        this.options = new ArrayList<String>();
    }
    
    
    
    
    
    public List<String> getOptions(){
    
    
        return options;
    
    }
    
      public String getTitle(){
            return title;
    
    }
    
    public String getDescription(){
            return description;
    
    }
    
    public String getImage(){
    
            return image;
    
    }
    
    public ScreenMode getScreenMode(){
               return mode;
    }
    
    
    public ScreenResult optionButtonPressed(DispenserHardware d, char c) {
        return ScreenResult.continueInScreen;
    }
    
    public ScreenResult seatButtonPressed(DispenserHardware d, int row, int col){
    
    
        
        
        return null;
  
    }
    
    public ScreenResult creditCardDetected(DispenserHardware d){
        
        
        
        return null;
            
        
    }
    
    public TheaterAreaState getAreaState(){
    
    
        return null;
    
    
    }
    
     public DispenserManager getDispenserManager (){
     return dispenseManager;
     }
   
    
    public ScreenResult begin(DispenserHardware d){ //no usa el dispenser hardware
    
        return ScreenResult.continueInScreen;
    } 
            
    
    public ScreenResult end(DispenserHardware d){
        
        return ScreenResult.exitScreen;
    }

    
    public void setDescription(String txt){
        this.description = txt;
    }

    public void setImage(String txt){
    this.image = txt;
    }
    
    public void setOptions( List<String> op){
    this.options =  op;
    }

    public void setTitle(String t){
    this.title = t;
    }
    public void setMode(ScreenMode m){
    this.mode = m;
    }
    public void setDispenseManager(DispenserManager dm){
    this.dispenseManager = dm;
    }

    void Menu() {
        }
    
   

}
