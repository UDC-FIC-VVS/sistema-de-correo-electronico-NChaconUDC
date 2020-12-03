package gal.udc.fic.vvs.email;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Test;

import gal.udc.fic.vvs.email.archivador.ArchivadorSimple;
import gal.udc.fic.vvs.email.archivador.DecoradorArchivador;
import gal.udc.fic.vvs.email.archivador.Delegado;
import gal.udc.fic.vvs.email.archivador.Log;
import gal.udc.fic.vvs.email.archivo.Texto;
import gal.udc.fic.vvs.email.correo.Carpeta;
import gal.udc.fic.vvs.email.correo.CarpetaLimitada;
import gal.udc.fic.vvs.email.correo.Correo;
import gal.udc.fic.vvs.email.correo.Mensaje;
import gal.udc.fic.vvs.email.correo.OperacionInvalida;

/**
 * Unit test for simple App.
 */
public class AppTest {
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() {
        assertTrue(true);
    }

    // Archivador
    @Test
    public void testArchivadorSimpleAlmacenarCorreo() {
        Texto contenido = new Texto("nombre","c");
        Carpeta carpeta = new Carpeta("nombre");

        Correo correoHijo = new Mensaje(contenido);
        Correo correoPadre = new CarpetaLimitada(carpeta, 1);
        
        try {
            correoPadre.añadir(correoHijo);
        } catch (OperacionInvalida e) {
            e.printStackTrace();
        }
        ArchivadorSimple archi = new ArchivadorSimple("", 5);
        archi.almacenarCorreo(correoPadre);
        assertEquals(4, archi.obtenerEspacioDisponible());
    }

    @Test
    public void testDelegadoAlmacenarCorreo() {
        Texto contenido = new Texto("nombre","c");
        Carpeta carpeta = new Carpeta("nombre");

        Correo correoHijo = new Mensaje(contenido);
        Correo correoPadre = new CarpetaLimitada(carpeta, 1);
        
        try {
            correoPadre.añadir(correoHijo);
        } catch (OperacionInvalida e) {
            e.printStackTrace();
        }
        ArchivadorSimple archi = new ArchivadorSimple("", 5);
        Delegado delegado = new Delegado(archi);
        delegado.almacenarCorreo(correoPadre);
        assertEquals(4, delegado.obtenerEspacioDisponible());
    }

    @Test
    public void testLogAlmacenarCorreo() {
        Texto contenido = new Texto("nombre","c");
        Carpeta carpeta = new Carpeta("nombre");

        Correo correoHijo = new Mensaje(contenido);
        Correo correoPadre = new CarpetaLimitada(carpeta, 1);
        
        try {
            correoPadre.añadir(correoHijo);
        } catch (OperacionInvalida e) {
            e.printStackTrace();
        }
        ArchivadorSimple archi = new ArchivadorSimple("", 5);
        Log log = new Log(archi);
        boolean si = log.almacenarCorreo(correoPadre);
        assertEquals(true, si);
    }

    //Archivo
    @Test
    public void testTextoObtenerMimeType() {
        Texto texto = new Texto("nombre", "contenido");


        String output = "";
        try {
            Method method = Texto.class.getDeclaredMethod("obtenerMimeType");
            method.setAccessible(true);
            output = (String) method.invoke(texto);
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException |
            IllegalArgumentException | InvocationTargetException e) {
                e.printStackTrace();
        }
        
        assertEquals("text/plain", output);
    }
}
