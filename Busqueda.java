import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Busqueda {
    //Iniciar
    public static void Interaccion() {
        System.out.println("=== Bienvenid@ al sistema de búsqueda de vuelos baratos ===");
        String carpeta;
        do {
            System.out.println("Ingrese la ruta de la carpeta que contiene los archivos de información de vuelos: ");
            Scanner in = new Scanner(System.in);
            carpeta = in.nextLine();
        } while (carpeta.isEmpty());


    }

    //Método para encontrar un vuelo directo
    public static void vueloDirecto(String carpeta, String ciudadSalida, String ciudadLlegada) {
        File folder = new File(carpeta);
        File[] archivos = folder.listFiles();

        double precioMasBarato = Double.MAX_VALUE;
        String aerolineaMasBarata = "";

        for (File archivo : archivos) { //Recorre todos los archivos de la Carpeta
            if (archivo.isFile()) { //Que no sea una Carpeta
                try (BufferedReader br = new BufferedReader(new FileReader(archivo))) { //Leer archivo
                    String line; //Cada Linea
                    while ((line = br.readLine()) != null) { //Que no sea linea vacia
                        String[] partes = line.split(", "); //Convertir en un Array
                        if (partes.length == 7) { //Cumpla con el formato
                            double precio = Double.parseDouble(partes[0]); //Agarra el precio
                            String salida = partes[1]; //Agarra la ciudad de salida
                            String llegada = partes[2]; //Agarra la ciudad de llegada
                            if (salida.equals(ciudadSalida) && llegada.equals(ciudadLlegada) && precio < precioMasBarato) { //Comparar con entrada usuario
                                //Boxeador
                                precioMasBarato = precio;
                                aerolineaMasBarata = archivo.getName(); // Usar el nombre del archivo como el nombre de la aerolínea
                            }
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Error al leer el archivo: " + e.getMessage());
                }
            }
        }

        if (aerolineaMasBarata.isEmpty()) {
            System.out.println("No se encontraron vuelos disponibles entre " + ciudadSalida + " y " + ciudadLlegada + " en la carpeta " + carpeta);
        } else {
            System.out.println("El vuelo más barato entre " + ciudadSalida + " y " + ciudadLlegada + " en la carpeta " + carpeta + " es de la aerolínea " + aerolineaMasBarata + " por $" + precioMasBarato);
        }
    }

    // 400, CDG, JFK, 10:00, 14:20, AF543, 150
    public static boolean vueloValido(String[] vuelo){
        



    }
<<<<<<< Updated upstream

    //Metodo de Validaciones
    public void aceptarArchivos(){
        int validacion = 0;
        String aerolinea;
        Scanner in = new Scanner (System.in);
        do{
            System.out.println("Ingrese el nombre de una Aerolínea: ");
            aerolinea = in.nextLine();
        } while (aerolinea.length() == 0); // Asegurarse de que se ingrese un nombre de aerolínea válido

        do{
            validacion = 0;
            System.out.println("Ingrese el nombre de archivo que contiene la información de los vuelos de " + aerolinea + ": ");
            String file_aerolinea = in.nextLine();
            try{
                File f = new File (file_aerolinea);
                FileReader fr = new FileReader (f);
                System.out.println("Se ha cargado la información. \n");
            } catch (Exception e){
                System.out.println(e.getMessage()); // Manejar errores al abrir archivos
                validacion = 1; // Marcador de error, volver a preguntar
            }
        } while (validacion >= 1); // Repetir hasta que se ingrese un archivo válido
    }

    //Vuelo Directo


=======
>>>>>>> Stashed changes
}


