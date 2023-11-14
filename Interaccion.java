import java.util.Scanner;
import java.io.File;
import javax.swing.JFileChooser;
import java.util.InputMismatchException;

public class Interaccion{
    public static void Interaccion() {
        System.out.println("=== Bienvenid@ al sistema de búsqueda de vuelos baratos ===\n");
        System.out.println("Seleccione una carpeta con los vuelos de cada aerolínea\n");
        
        // Crear un objeto JFileChooser para que el usuario seleccione una carpeta
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        int returnValue = fileChooser.showOpenDialog(null);
        boolean continuar = true;

        while (continuar){
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFolder = fileChooser.getSelectedFile();
                String carpeta = selectedFolder.getAbsolutePath();
                
                Busqueda correr = new Busqueda();
                
                correr.CargarInformacion(carpeta);
                System.out.println("Se ha cargado la información\n");
                
                boolean busqueda = true;
                
                while (busqueda){
                    System.out.println("==== Inicio de Búsqueda ====");
                    
                    Scanner in = new Scanner(System.in);
                    
                    boolean salida = true;
                    String ciudadSalida = "";
                    while (salida){
                        System.out.print("Ciudad de salida: ");
                        ciudadSalida = in.nextLine().trim().toUpperCase();
                        salida = false;
                        
                        if (ciudadSalida.equals("")){
                            salida = true;
                        }
                    }
                    
                    boolean llegada = true;
                    String ciudadLlegada = "";
                    while (llegada){
                        System.out.print("Ciudad de llegada: ");
                        ciudadSalida = in.nextLine().trim().toUpperCase();
                        llegada = false;
                        
                        if (ciudadLlegada.equals("")){
                            llegada = true;
                        }
                    }
                    
                    boolean fecha_bool = false;
                    String fecha = "";
                    while (fecha_bool == false){
                        try{
                            int validaciones = 0;
                            
                            System.out.println("Ingrese la fecha de inicio (DD/MM/AAAA): ");
                            fecha = in.nextLine();
                            
                            if (fecha.length()!=10){
                                validaciones++;
                            }
                            
                            if (fecha.charAt(2)!=('/') && fecha.charAt(5)!=('/')){
                                validaciones++;
                            }
                            
                            
                            //verificar que sean numeros y que sean menores que los numeros logicos. meses menores que 12. no negativos. verificar que no sea espacio.
                            
                            
                            if (validaciones == 0){
                                break;
                            }
                            else{
                                System.out.println("Ingrese bien la fecha.\n");
                                fecha_bool = false;
                            }
                            
                        } catch (InputMismatchException e){
                            System.out.println("Ingrese bien la fecha.\n");
                            fecha_bool = false;
                        }
                    }
                    
                    while (true){
                        try{
                            System.out.println("Cuántas escalas permite como máximo: ");
                            int cantidad_escalas = in.nextInt();
                            
                            if (cantidad_escalas == 0){
                                correr.VueloMasBaratoDirecto(ciudadSalida, ciudadLlegada, fecha);
                                break;
                            }
                            else{
                                System.out.println("Permite cruce de aerolíneas? (S/N): ");
                                String permite = in.nextLine();
                                //hacer validaciones para el si o no. que no se caiga y repita la pregunta. y que solo sea un char.
                                
                                System.out.println("¿Cuántas son las escalas máximas?: ");
                                int escalasMaximas = in.nextInt();
                                
                                if (permite.equals("N")){
                                    correr.VueloMasBaratoEscalasSolo(ciudadSalida, ciudadLlegada, fecha, escalasMaximas);
                                    break;
                                }
                                
                                else if (permite.equals("S")){
                                    correr.VueloMasBaratoEscalasAerolineas(ciudadSalida, ciudadLlegada, fecha, escalasMaximas);
                                    break;
                                }
                            }
                        } catch (InputMismatchException error){
                            System.out.println("Ingrese un número.\n");
                        }
                    }
                    
                    System.out.println("¿Desea hacer otra búsqueda? (S/N): ");
                    //hacer validaciones para el si o no. que no se caiga y repita la pregunta. y que solo sea un char.
                    String otra = in.nextLine();
                    
                    if (otra.equals("N")){
                        break;
                    }
                    
                    else if (otra.equals("S")){
                        busqueda = true;
                    }                    
                }
                
                System.out.println("Se ignorarán los siguientes vuelos:  \n" + correr.getIgnorados());
                
                continuar = false;
            } else {
                System.out.println("Por favor, seleccione una carpeta.");
                continuar = true;
            }
        }
    }
}
