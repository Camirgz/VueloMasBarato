import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

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

    public static void Interaccion() {
        System.out.println("=== Bienvenid@ al sistema de búsqueda de vuelos baratos ===");
    }

    public void vueloDirecto(String carpeta, String ciudadSalida, String ciudadLlegada) {
        File folder = new File(carpeta);
        File[] archivos = folder.listFiles();

        double precioMasBarato = Double.MAX_VALUE;
        String aerolineaMasBarata = "";

        for (File archivo : archivos) {
            if (archivo.isFile()) {
                try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        String[] partes = line.split(", ");
                        if (partes.length == 9) {
                            if (vueloValido(partes)) {
                                double precio = Double.parseDouble(partes[0]);
                                String salida = partes[1];
                                String llegada = partes[2];
                                if (salida.equals(ciudadSalida) && llegada.equals(ciudadLlegada) && precio < precioMasBarato) {
                                    precioMasBarato = precio;
                                    aerolineaMasBarata = archivo.getName();
                                }
                            } else {
                                annadirIgnorado(partes);
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

    public boolean vueloValido(String[] vuelo) {
        return precioValidado(vuelo) && ciudadesValidas(vuelo) && validadorHorario(vuelo) && codigoValidado(vuelo) && asientosValidado(vuelo);
    }

    public boolean precioValidado(String[] vuelo) {
        String precioStr = vuelo[0];
        double precioDouble;
        try {
            precioDouble = Double.parseDouble(precioStr);
            if (precioDouble > 0) {
                this.precio = precioDouble;
                return true;
            }
        } catch (NumberFormatException e) {
            return false;
        }
        return false;
    }

    public boolean ciudadesValidas(String[] vuelo) {
        String ciudadSalida = vuelo[1];
        String ciudadLlegada = vuelo[2];
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

    public boolean validadorHorario(String[] vuelo) {
        String horaSalida = vuelo[3];
        String horaLlegada = vuelo[4];
        String fechaSalida = vuelo[5];
        String fechaLlegada = vuelo[6];

        try {
            if (horaSalida.matches("\\d{2}:\\d{2}") && horaLlegada.matches("\\d{2}:\\d{2}") &&
                    fechaSalida.matches("\\d{2}-\\d{2}-\\d{2}") && fechaLlegada.matches("\\d{2}-\\d{2}-\\d{4}")) {

                String[] horaSalidaParts = horaSalida.split(":");
                String[] horaLlegadaParts = horaLlegada.split(":");
                String[] fechaSalidaParts = fechaSalida.split("-");
                String[] fechaLlegadaParts = fechaLlegada.split("-");

                int horaSalidaHH = Integer.parseInt(horaSalidaParts[0]);
                int horaLlegadaHH = Integer.parseInt(horaLlegadaParts[0]);
                int horaSalidaMM = Integer.parseInt(horaSalidaParts[1]);
                int horaLlegadaMM = Integer.parseInt(horaLlegadaParts[1]);

                int fechaSalidaDD = Integer.parseInt(fechaSalidaParts[0]);
                int fechaLlegadaDD = Integer.parseInt(fechaLlegadaParts[0]);
                int fechaSalidaMM = Integer.parseInt(fechaSalidaParts[1]);
                int fechaLlegadaMM = Integer.parseInt(fechaLlegadaParts[1]);
                int fechaSalidaAA = Integer.parseInt(fechaSalidaParts[2]);
                int fechaLlegadaAA = Integer.parseInt(fechaLlegadaParts[2]);

                boolean mismoDia;
                boolean diaSgte;

                if (fechaSalidaDD == fechaLlegadaDD && fechaSalidaMM == fechaLlegadaMM && fechaSalidaAA == fechaLlegadaAA) {
                    mismoDia = true;
                } else {
                    mismoDia = false;
                }

                if (fechaSalidaDD == fechaLlegadaDD - 1 && fechaSalidaMM == fechaLlegadaMM && fechaSalidaAA == fechaLlegadaAA) {
                    diaSgte = true;
                } else {
                    diaSgte = false;
                }

                if ((mismoDia && horaSalidaHH < horaLlegadaHH) || (diaSgte && horaSalidaHH > horaLlegadaHH)) {
                    this.horaSalida = horaSalida;
                    this.horaLlegada = horaLlegada;
                    this.fechaSalida = fechaSalida;
                    this.fechaLlegada = fechaLlegada;
                    return true;
                } else {
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
            return false;
        }
        return false;
    }

    public void annadirIgnorado(String[] vuelo) {
        this.ignorados += String.join(", ", vuelo) + "\n";
    }
    
}
