public class Aerolinea{
    private String nombre;
    private String [] ciudadesOperando = new String [1000];
    private int cantidadCiudadesOperando;
    private Vuelo [] vuelos;
    private Vuelo [][][] vuelos_relacionados;
    
    public Aerolinea (String nombre, String [] informacion){
        String ciudadesOperando = "";
        vuelos = new Vuelo [150];
        
        for (int i = 0; i<informacion.length; i++){
            String [] datos = informacion[i].split(",");
            
            String precio_str = datos[0];
            double precio = Double.parseDouble(precio_str);
            String ciudadSalida = datos[1];
            ciudadesOperando = ciudadesOperando + "," + ciudadSalida;
            String ciudadLlegada = datos[2];
            String horaSalida = datos[3];
            String horaLlegada = datos[4];
            String diaSalida = datos[5];
            String diaLlegada = datos[6];
            String codigoVuelo = datos[7];
            String asientosDisponibles_str = datos[8];
            int asientosDisponibles = Integer.parseInt(asientosDisponibles_str);
            
            this.nombre = nombre;
            vuelos[i] = new Vuelo (precio, ciudadSalida, ciudadLlegada, horaSalida, horaLlegada, diaSalida, diaLlegada, codigoVuelo, asientosDisponibles);
        }
        
        String [] ciudades_temporales = ciudadesOperando.split(",");
        for (int i = 0; i<ciudades_temporales.length; i++){
            if (buscadorCiudades(ciudades_temporales[i]) == false){
                this.cantidadCiudadesOperando++;
            }
        }
        
        this.ciudadesOperando = new String [this.cantidadCiudadesOperando];
        for (int i = 0; i<ciudades_temporales.length; i++){
            if (ciudadesOperando != null && buscadorCiudades(ciudades_temporales[i]) == false){
                this.ciudadesOperando[i] = ciudades_temporales[i];
            }
        }
    }
    
    public boolean buscadorCiudades(String ciudad){
        boolean existe = true;
        for (int i = 0; i<ciudadesOperando.length; i++){
            if (ciudad.equals(ciudadesOperando[i])){
                existe = true;
                break;
            }
            else {
                existe = false;
            }
        }
        return existe;
    }
    
    public Vuelo[] getVuelos (){
        return this.vuelos;
    }
    
    public String getNombre(){
        return this.nombre;
    }
}
