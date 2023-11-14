public class Aerolinea{
    private String nombre;
    private String [] ciudadesOperando;
    private String [] vuelos;
    private Vuelo [][][] matriz;
    
    public Aerolinea(String nombre, String [] datos){
        this.nombre = nombre;
        this.vuelos = new String [datos.length];
        for (int i = 0; i<datos.length; i++){
            vuelos[i] = datos[i];
        }
        
        //sacar ciudades operando
    }
    
    public void CargarMatriz(){
        matriz = new Vuelo [ciudadesOperando.length][ciudadesOperando.length][150];
        
        
    }
    
    public void VueloDirecto(){
        
    }
    
    public void VueloEscalas(){
        
    }
}
