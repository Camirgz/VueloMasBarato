import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JFileChooser;
import java.util.Scanner;

public class Interaccion {
    public static void Interaccion() {
        System.out.println("=== Bienvenid@ al sistema de búsqueda de vuelos baratos ===\n");
        System.out.println("Seleccione una carpeta con los vuelos de cada aerolínea\n");
        
        // Crear un objeto JFileChooser para que el usuario seleccione una carpeta
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        int returnValue = fileChooser.showOpenDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFolder = fileChooser.getSelectedFile();
            String carpeta = selectedFolder.getAbsolutePath();
            
            Busqueda busqueda = new Busqueda();
            
            busqueda.cargarInformacion(carpeta);
            System.out.println("Se ha cargado la información\n");
            
            System.out.println("==== Inicio de Búsqueda ====");

            // Solicitar las ciudades de salida y llegada al usuario
            Scanner scanner = new Scanner(System.in);
            System.out.print("Ciudad de salida: ");
            String ciudadSalida = scanner.nextLine().trim().toUpperCase();
            System.out.print("Ciudad de llegada: ");
            String ciudadLlegada = scanner.nextLine().trim().toUpperCase();

            // Llamar al método vueloDirecto con la carpeta y ciudades proporcionadas
            busqueda.vueloDirecto(ciudadSalida, ciudadLlegada);
            
            System.out.println("Se ignorarán los siguientes vuelos:  \n" + busqueda.getIgnorados());
        } else {
            System.out.println("No se ha seleccionado una carpeta. El programa se cerrará.");
        }
    }
}
