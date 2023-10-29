import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.util.Scanner;

//Métoodo para añadir aerolíneas
public class Busqueda {
    public static void Interaccion(){
        System.out.println("=== Bienvenid@ al sistema de búsqueda de vuelos baratos ===");
        System.out.println("Primero se va a cargar la información.");
        aceptarArchivos();

        int repeticion = insertarMasAerolineas();
        while (repeticion != 0){
            if (repeticion == -1){
                repeticion = insertarMasAerolineas(); // Volver a preguntar si la entrada no es válida
            }

            else if (repeticion == 1){
                aceptarArchivos(); // Añadir más aerolíneas
                repeticion = insertarMasAerolineas();
            }
        }
    }

    public static int insertarMasAerolineas(){
        Scanner in = new Scanner (System.in);
        System.out.println("¿Desea ingresar otra aerolínea? (S/N): ");
        String respuesta = in.nextLine();
        if (respuesta.equalsIgnoreCase("S")){
            return 1; // Agregar más aerolíneas
        }
        else if (respuesta.equalsIgnoreCase("N")){
            return 0; // No agregar más aerolíneas, finalizar
        }
        else {
            System.out.println("Por favor, ingrese S para ingresar más y N para no: ");
            return -1; // Entrada no válida, volver a preguntar
        }
    }

    //Metodo de Validaciones
    public static void aceptarArchivos(){
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
                BufferedReader br = new BufferedReader (fr);
                System.out.println("Se ha cargado la información. \n");
            } catch (Exception e){
                System.out.println(e.getMessage()); // Manejar errores al abrir archivos
                validacion = 1; // Marcador de error, volver a preguntar
            }
        } while (validacion >= 1); // Repetir hasta que se ingrese un archivo válido
    }

}
