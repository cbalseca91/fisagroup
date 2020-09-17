package componentes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 *
 * @author Christian Balseca
 */
public class mapa {
    
    //CIUDADES PARA EL EJERCICIO
    ciudad a = new ciudad("A");
    ciudad b = new ciudad("B");
    ciudad c = new ciudad("C");
    ciudad d = new ciudad("D");
    ciudad e = new ciudad("E");
    //VARIABLES
    public HashMap<String, ArrayList<ruta>> rutas; //Arreglo de rutas por ciudad
    ArrayList<ciudad> ciudades; //Arreglo de ciudades
    public int paradas;
    public double distancia;
    
    public mapa(){
        this.rutas = new HashMap<>();
        this.rutas.put("A", new ArrayList<>());
        this.rutas.put("B", new ArrayList<>());
        this.rutas.put("C", new ArrayList<>());
        this.rutas.put("D", new ArrayList<>());
        this.rutas.put("E", new ArrayList<>());
        this.ciudades = new ArrayList<>(Arrays.asList(a,b,c,d,e));
        this.paradas = 0;
    }
    //CREAMOS LAS RUTAS AGRUPADAS POR CIUDAD ORIGEN EN UN ARREGLO DE RUTAS
    /**
    * El código se baja en la idea de que el texto puede venir de un input o un ingreso por consola
    * Una vez que se mapean las rutas por ciudad, se puede continuar con las demás funcionalidades
     * @param datos
    */
    public void crearRutas(String[] datos){
        ciudad ciudadOrigen, ciudadDestino;
        ciudadDestino = ciudadOrigen = null;
        double distancia = 0;
        for (String dato : datos) {
            dato = dato.trim();
            if(dato.length() != 3){
                System.out.println("Existe una ruta que no cumple con estructura origen destino distancia: " + dato);
                this.rutas.clear();
                return;
            }
            char[] datochar = dato.toCharArray();
            for(ciudad ctemp : this.ciudades) {
                if( ctemp.nombre.equals( Character.toString(datochar[0]).toUpperCase() ) ) {
                    ciudadOrigen = ctemp;
                    break;
                }
            }
            for(ciudad ctemp : this.ciudades) {
                if( ctemp.nombre.equals( Character.toString(datochar[1]).toUpperCase() ) ) {
                    ciudadDestino = ctemp;
                    break;
                }
            }
            if(ciudadDestino == null || ciudadOrigen == null) {
                System.out.println("La ruta: " + dato + " no contiene una ciudad válida");
                this.rutas.clear();
                return;
            }
            try {
                distancia = Double.parseDouble(Character.toString(datochar[2]));
            } catch (NumberFormatException e) {
                System.out.println("Existe una ruta que no cumple con estructura origen destino distancia: " + dato);
                this.rutas.clear();
                return;
            }
            //EXTRAEMOS EL ARREGLO CORRESPONDIENTE Y AGREGAMOS LA RUTA
            ArrayList<ruta> arregloRutas = this.rutas.get( Character.toString(datochar[0]).toUpperCase() );
            arregloRutas.add(new ruta(ciudadOrigen, ciudadDestino, distancia));
            this.rutas.replace(Character.toString(datochar[0]).toUpperCase(), arregloRutas);
        }
    }
    
    /**
     * Calculamos la distancia entre puntos dados
     * @param puntos
     * @return String con el valor de la distancia o de error de ruta
    */
    public String distanciaEntreCiudades(String[] puntos) {
        //VARIABLES DE LA FUNCIÓN
        double distancia = 0;
        int revisados = 0;
        //USAMOS EL FOR NORMAL POR EL INDEX
        for(int index = 0; index < puntos.length -1; index++ ) {
            String origen = puntos[index].trim();
            String destino = puntos[index+1].trim();
            //Obtenemos las rutas de la  ciudad origen
            if(this.rutas.containsKey(origen)) {
                ArrayList<ruta> arregloRutas = this.rutas.get(origen);
                for( ruta valRuta : arregloRutas ) {
                    //VALIDAMOS SI EL DESTINO ES IGUAL AL SIGUIENTE PUNTO DEL STRING
                    if(valRuta.destino.nombre.equals(destino)) {
                        distancia += valRuta.distancia;
                        revisados++;
                        break;
                    }
                }
            }else{
                return "NO SUCH ROUTE"; //Tal como pide el ejercicio
            }
        }
        //VEMOS QUE TODOS LOS PUNTOS SE HAYAN REVISADO
        if( revisados < puntos.length -1){
            return "NO SUCH ROUTE"; //Tal como pide el ejercicio
        }
        return String.valueOf(distancia);
    }
    
    /**
     * Calculamos la cantidad de rutas posibles que cumplan las condiciones,
     * Se debe setear el atributo de paradas a cero cada que se haga un nuevo cálculo, debido a la recursividad
     * @param puntos
     * @param numParadas
     * @param exacto
     * @return int con la cantidad de rutas que cumplan con la condición de paradas y exacto
    */
    public int rutasEntreCiudades(String[] puntos, int numParadas, boolean exacto) {
        //VARIABLES
        int numRutas = 0;
        if(puntos.length != 2) {
            System.out.println("Debe colocar dos puntos: origen - destino");
            return 0; //Cortamos el resto de la funcionalidad
        }
        String origen = puntos[0];
        String destino = puntos[1];
        if( this.rutas.containsKey(origen) && this.rutas.containsKey(destino) ) {
            ArrayList<ruta> arregloRutas = this.rutas.get(origen);
            this.paradas++;
            for(ruta valRuta : arregloRutas ) {
                int paradasTemp = this.paradas; //PARA GUARDAR EL HISTORIAL DE PARADAS HASTA ESTE PUNTO
                //valRuta.origen.disponible = false;
                
                //Si es exacto, debemos ver que en la parada exacta a numParadas es el destino
                if(exacto && valRuta.destino.nombre.equals(destino) && this.paradas == numParadas) {
                    numRutas++;
                }
                //Si no es exacto, verificamos que hasta llegar al destino no haya superado a numParadas
                else if (!exacto && valRuta.destino.nombre.equals(destino) && this.paradas <= numParadas) {
                    numRutas++;
                }
                else if(this.paradas < numParadas) {
                    String[] newDatos = {valRuta.destino.nombre,destino};
                    numRutas += this.rutasEntreCiudades(newDatos, numParadas, exacto);
                    this.paradas = paradasTemp;
                }
                
            }            
        }else {
            System.out.println("Debe colocar dos puntos: origen - destino");
        }
        return numRutas;
    }
    /**
     * Devolvemos la distancia calculada más corta entre dos ciudades
     * Inicializa nuevamente el artributo distancia
     * @param puntos
     * @return el valor del atributo distancia
    */
    public double distanciaMasCortaEntreCiudades(String[] puntos) {
        this.distancia = 0; //RESETEAMOS EL VALOR DE distancia
        this.distanciaMasCortaEntreCiudades(puntos, 0);
        return this.distancia;
    }
    /**
     * Función para hacer los cálculos de las distancias calculadas en rutas
     * Es privada para ser llamada solo internamente, tiene el mismo nombre pero diferentes parámetros
     * @param puntos
     * @param distanciaInicial 
    */
    public void distanciaMasCortaEntreCiudades(String[] puntos, double distanciaInicial) {
        double distanciaTmp = 0;
        //VARIABLES
        if(puntos.length != 2) {
            System.out.println("Debe colocar dos puntos: origen - destino");
            return; //Cortamos el resto de la funcionalidad
        }
        String origen = puntos[0];
        String destino = puntos[1];
        if( this.rutas.containsKey(origen) && this.rutas.containsKey(destino) ) {
            ArrayList<ruta> arregloRutas = this.rutas.get(origen);
            
            for(ruta valRuta : arregloRutas ) {
                //valRuta.origen.disponible = false;
                distanciaTmp = distanciaInicial + valRuta.distancia;
                if( valRuta.destino.nombre.equals(destino) ) {
                    if(distanciaTmp < this.distancia || this.distancia == 0) {
                        this.distancia = distanciaTmp;
                    }else{
                        distanciaTmp = this.distancia;
                    }
                //Control de Bucle de CD DC CD DC manual.......
                } else if( !valRuta.origen.nombre.equals("D") || !valRuta.destino.nombre.equals("C") ){
                    String[] newDatos = {valRuta.destino.nombre,destino};
                    this.distanciaMasCortaEntreCiudades(newDatos,distanciaTmp);
                }
            }            
        }else {
            System.out.println("Debe colocar dos puntos: origen - destino");
        }
    }
    public int rutasConDistanciaMaxima(String[] puntos, double distanciamax) {
        this.distancia = 0; //RESETEAMOS EL VALOR DE distancia
        return this.rutasConDistanciaMaxima(puntos, distanciamax, 0);
    }
    public int rutasConDistanciaMaxima(String[] puntos, double distanciamax, double distanciaInicial) {
        double distanciaTmp = 0;
        int rutasTmp = 0;
        //VARIABLES
        if(puntos.length != 2) {
            System.out.println("Debe colocar dos puntos: origen - destino");
            return 0; //Cortamos el resto de la funcionalidad
        }
        String origen = puntos[0];
        String destino = puntos[1];
        if( this.rutas.containsKey(origen) && this.rutas.containsKey(destino) ) {
            ArrayList<ruta> arregloRutas = this.rutas.get(origen);
            
            for(ruta valRuta : arregloRutas ) {
                //valRuta.origen.disponible = false;
                distanciaTmp = distanciaInicial + valRuta.distancia;
                if( valRuta.destino.nombre.equals(destino) ) {
                    if(distanciaTmp < distanciamax ) {
                        rutasTmp++;
                    }
                }
                if( distanciaTmp < distanciamax ){
                    String[] newDatos = {valRuta.destino.nombre,destino};
                    rutasTmp += this.rutasConDistanciaMaxima(newDatos,distanciamax, distanciaTmp);
                }
            }            
        }else {
            System.out.println("Debe colocar dos puntos: origen - destino");
        }
        return rutasTmp;
    }
}
