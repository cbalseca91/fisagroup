
package componentes;

/**
 *
 * @author Christian Balseca
 */
public class ruta {
    public ciudad origen, destino;
    public double distancia = 0;
    public boolean usado;
    public ruta (ciudad origen, ciudad dest, double dist) {
        this.origen = origen;
        this.destino = dest;
        this.distancia = dist;
        this.usado = false;
    }
}
