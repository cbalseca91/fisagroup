
package main;

import componentes.*;
import java.util.ArrayList;
import java.util.Arrays;
/**
 *
 * @author Christian Balseca
 */
public class main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //Creación de Clases
        mapa mapa = new mapa();
        //Valores quemados
        String datos = "AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7";
        
        //Variables
        String[] rutas = datos.split(","); //Transformamos el String en Array por las comas
        System.out.println(Arrays.toString(rutas));
        mapa.crearRutas(rutas);
        /*
        System.out.println("-------------- MAPA DE DISTANCIAS --------------");        
        mapa.rutas.entrySet().forEach(entry->{
           System.out.print(entry.getKey());
           for(ruta val : entry.getValue()) {
               System.out.print(" " + val.distancia + ", ");
           }
            System.out.println("");
        });
        */
        //OUTPUT #1
        String entrada1 = "A-B-C";
        System.out.println("Respuesta #1: "+ mapa.distanciaEntreCiudades(entrada1.split("-")));
        //OUTPUT #2
        String entrada2 = "A-D";
        System.out.println("Respuesta #2: "+ mapa.distanciaEntreCiudades(entrada2.split("-")));
        //OUTPUT #3
        String entrada3 = "A-D-C";
        System.out.println("Respuesta #3: "+ mapa.distanciaEntreCiudades(entrada3.split("-")));
        //OUTPUT #4
        String entrada4 = "A-E-B-C-D";
        System.out.println("Respuesta #4: "+ mapa.distanciaEntreCiudades(entrada4.split("-")));
        //OUTPUT #5
        String entrada5 = "A-E-D";
        System.out.println("Respuesta #5: "+ mapa.distanciaEntreCiudades(entrada5.split("-")));
        //OUTPUT #6
        mapa.paradas = 0; //SETEAMOS A CERO LAS PARADAS PARA UN NUEVO CÁLCULO
        String entrada6 = "C-C";
        int numParadas6 = 3;
        System.out.println("Respuesta #6: "+ mapa.rutasEntreCiudades(entrada6.split("-"), numParadas6, false));
        //OUTPUT #7
        mapa.paradas = 0; //SETEAMOS A CERO LAS PARADAS PARA UN NUEVO CÁLCULO
        String entrada7 = "A-C";
        int numParadas7 = 4;
        System.out.println("Respuesta #7: "+ mapa.rutasEntreCiudades(entrada7.split("-"), numParadas7, true));
        //OUTPUT #8
        String entrada8 = "A-C";
        System.out.println("Respuesta #8: "+mapa.distanciaMasCortaEntreCiudades(entrada8.split("-")));
        //OUTPUT #9
        String entrada9 = "B-B";
        System.out.println("Respuesta #9: "+mapa.distanciaMasCortaEntreCiudades(entrada9.split("-")));
        //OUTPUT #10
        String entrada10 = "C-C";
        double distancia10 = 30;
        System.out.println("Respuesta #10: "+mapa.rutasConDistanciaMaxima(entrada10.split("-"), distancia10));
    }
    
}
