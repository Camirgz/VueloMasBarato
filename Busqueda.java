import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Busqueda {
    public static void Interaccion(){
        System.out.println("=== Bienvenid@ al sistema de búsqueda de vuelos baratos ===");
        System.out.println("Primero se va a cargar la información.");
        aceptarArchivos();

        int repeticion = insertarMasAerolineas();
        while (repeticion!=0){
            if (repeticion == -1){
                repeticion = insertarMasAerolineas();
            }

            else if (repeticion == 1){
                aceptarArchivos();
                repeticion = insertarMasAerolineas();
            }
        }
    }

    public static int insertarMasAerolineas(){
        Scanner in = new Scanner (System.in);
        System.out.println("¿Desea ingresar otra aerolínea? (S/N): ");
        String respuesta = in.nextLine();
        if (respuesta.equalsIgnoreCase("S")){
            return 1;
        }
        else if (respuesta.equalsIgnoreCase("N")){
            return 0;
        }
        else {
            System.out.println("Por favor, ingrese S para ingresar más y N para no: ");
            return -1;
        }
    }

    public static void aceptarArchivos(){
        int validacion = 0;
        String aerolinea;
        Scanner in = new Scanner (System.in);
        do{
            System.out.println("Ingrese el nombre de una Aerolínea: ");
            aerolinea = in.nextLine();
        } while (aerolinea.length()==0);
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
                System.out.println(e.getMessage());
                validacion = 1;
            }
        } while (validacion>=1);
    }
}
