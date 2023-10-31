//Import
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Busqueda {

    private String ignorados;
    private double precio;
    private String ciudadSalida;
    private String ciudadLlegada;
    private String horaSalida;
    private String horaLlegada;
    private String fechaSalida;
    private String fechaLlegada;
    private String codigoVuelo;
    private int asientos;


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
    public void vueloDirecto(String carpeta, String ciudadSalida, String ciudadLlegada) {
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

                        if (partes.length == 9) { // Cumpla con el formato (se agregó uno más para el código de vuelo)
                            // Llama al método vueloValido para verificar si el vuelo es válido
                            if (vueloValido(partes)) {
                                double precio = Double.parseDouble(partes[0]); //Agarra el precio
                                String salida = partes[1]; //Agarra la ciudad de salida
                                String llegada = partes[2]; //Agarra la ciudad de llegada

                                if (salida.equals(ciudadSalida) && llegada.equals(ciudadLlegada) && precio < precioMasBarato) {
                                    //Boxeador
                                    precioMasBarato = precio;
                                    aerolineaMasBarata = archivo.getName(); // Usar el nombre del archivo como el nombre de la aerolínea
                                }
                            }
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Error al leer el archivo: " + e.getMessage());
                }
            }
        }

        // Return
        if (aerolineaMasBarata.isEmpty()) {
            System.out.println("No se encontraron vuelos disponibles entre " + ciudadSalida + " y " + ciudadLlegada + " en la carpeta " + carpeta);
        } else {
            System.out.println("El vuelo más barato entre " + ciudadSalida + " y " + ciudadLlegada + " en la carpeta " + carpeta + " es de la aerolínea " + aerolineaMasBarata + " por $" + precioMasBarato);
        }
    }




    // 400, CDG, JFK, 10:00, 14:20, AF543, 150
    public boolean vueloValido(String[] vuelo){
        if(precioValidado(vuelo) &&
                ciudadesValidas(vuelo) &&
                validadorHorario(vuelo) &&
                codigoValidado(vuelo) &&
                asientosValidado(vuelo)){
            return true;
        }
        else{
            return false;
        }
    }

    //Validador de Precio
    public boolean precioValidado(String[] vuelo) {
        String precioStr = vuelo[0];
        double precioDouble;

        try {
            precioDouble = Double.parseDouble(precioStr);
            if (precioDouble > 0) {
                //Mini Constructor
                this.precio = precioDouble;
                return true;
            } else {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }
    }

    //Validador de Ciudades
    public boolean ciudadesValidas(String[] vuelo) {
        String ciudadSalida = vuelo[1];
        String ciudadLlegada = vuelo[2];

        // Verifica que ambas ciudades tengan 3 caracteres y sean diferentes
        if (ciudadSalida.trim().length() == 3 && ciudadLlegada.trim().length() == 3) {
            ciudadSalida = ciudadSalida.trim().toUpperCase();
            ciudadLlegada = ciudadLlegada.trim().toUpperCase();
            this.ciudadSalida = ciudadSalida;
            this.ciudadLlegada = ciudadLlegada;
            if (!ciudadSalida.equals(ciudadLlegada)) {
                return true;
            }
        }
        return false;
    }

    //Validador de Horarios (Horas y Fechas)
    public boolean validadorHorario(String[] vuelo) {
        String horaSalida = vuelo[3];
        String horaLlegada = vuelo[4];
        String fechaSalida = vuelo[5];
        String fechaLlegada = vuelo[6];

        try {
            // Verificar que las horas tengan el formato HH:MM y las fechas DD-MM-AAAA
            if (horaSalida.matches("\\d{2}:\\d{2}") && horaLlegada.matches("\\d{2}:\\d{2}") &&
                    fechaSalida.matches("\\d{2}-\\d{2}-\\d{2}") && fechaLlegada.matches("\\d{2}-\\d{2}-\\d{4}")) {

                // Parser de las horas y fechas
                String[] horaSalidaParts = horaSalida.split(":");
                String[] horaLlegadaParts = horaLlegada.split(":");
                String[] fechaSalidaParts = fechaSalida.split("-");
                String[] fechaLlegadaParts = fechaLlegada.split("-");

                //Dividir por partes ; HorasHH, MinutosMM
                int horaSalidaHH = Integer.parseInt(horaSalidaParts[0]);
                int horaLlegadaHH = Integer.parseInt(horaLlegadaParts[0]);
                int horaSalidaMM = Integer.parseInt(horaSalidaParts[1]);
                int horaLlegadaMM = Integer.parseInt(horaLlegadaParts[1]);

                //Dividir por partes ; DiaDD, MesMM, AñoAA
                int fechaSalidaDD = Integer.parseInt(fechaSalidaParts[0]);
                int fechaLlegadaDD = Integer.parseInt(fechaLlegadaParts[0]);
                int fechaSalidaMM = Integer.parseInt(fechaSalidaParts[1]);
                int fechaLlegadaMM = Integer.parseInt(fechaLlegadaParts[1]);
                int fechaSalidaAA = Integer.parseInt(fechaSalidaParts[2]);
                int fechaLlegadaAA = Integer.parseInt(fechaLlegadaParts[2]);

                boolean mismoDia;
                boolean diaSgte;
                boolean minsValidados;

                //Diferentes Dias
                if(fechaSalidaDD == fechaLlegadaDD && fechaSalidaMM == fechaLlegadaMM && fechaSalidaAA == fechaLlegadaAA){
                    mismoDia = true;
                }
                else {
                    mismoDia = false;
                }

                //Dia Siguiente
                if(fechaSalidaDD == fechaLlegadaDD -1 && fechaSalidaMM == fechaLlegadaMM && fechaSalidaAA == fechaLlegadaAA){
                    diaSgte = true;
                }
                else {
                    diaSgte = false;
                }

                //Validacion final
                if((mismoDia && horaSalidaHH < horaLlegadaHH) || (diaSgte && horaSalidaHH > horaLlegadaHH)){
                    //Mini Constructor
                    this.horaSalida = horaSalida;
                    this.horaLlegada = horaLlegada;
                    this.fechaSalida = fechaSalida;
                    this.fechaLlegada = fechaLlegada;
                    return true;
                }
                else{
                    return false;
                }

            }
        } catch (NumberFormatException e) {
            return false;
        }
        return false;
    }

    public boolean codigoValidado(String[] vuelo) {
        String codigoVuelo = vuelo[7];

        // Verificar si el código de vuelo tiene el formato correcto (dos letras y cuatro dígitos)
        if (codigoVuelo.matches("^[A-Z]{2}\\d{4}$")) {
            this.codigoVuelo = codigoVuelo;
            return true;
        } else {
            return false;
        }
    }

    public boolean asientosValidado(String[] vuelo) {
        String asientosStr = vuelo[8];

        try {
            int asientos = Integer.parseInt(asientosStr);
            if (asientos > 0) {
                this.asientos = asientos;
                return true;
            }
        } catch (NumberFormatException e) {
            // Manejo de errores si la conversión falla
            return false;
        }

        return false;
    }

    public void annadirIgnorado(String[] vuelo) {
        this.ignorados += vuelo + "\n";
    }


}

