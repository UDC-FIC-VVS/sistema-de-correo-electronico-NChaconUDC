package gal.udc.fic.vvs.email;

import static org.junit.Assert.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Vector;

import org.junit.Assert;
import org.junit.Test;

import gal.udc.fic.vvs.email.archivador.Archivador;
import gal.udc.fic.vvs.email.archivador.ArchivadorSimple;
import gal.udc.fic.vvs.email.archivador.DecoradorArchivador;
import gal.udc.fic.vvs.email.archivador.Delegado;
import gal.udc.fic.vvs.email.archivador.Log;
import gal.udc.fic.vvs.email.archivo.Archivo;
import gal.udc.fic.vvs.email.archivo.Audio;
import gal.udc.fic.vvs.email.archivo.Imagen;
import gal.udc.fic.vvs.email.archivo.Texto;
import gal.udc.fic.vvs.email.correo.Adjunto;
import gal.udc.fic.vvs.email.correo.Cabecera;
import gal.udc.fic.vvs.email.correo.Carpeta;
import gal.udc.fic.vvs.email.correo.CarpetaLimitada;
import gal.udc.fic.vvs.email.correo.Correo;
import gal.udc.fic.vvs.email.correo.CorreoAbstracto;
import gal.udc.fic.vvs.email.correo.DecoradorMensaje;
import gal.udc.fic.vvs.email.correo.Mensaje;
import gal.udc.fic.vvs.email.correo.MensajeAbstracto;
import gal.udc.fic.vvs.email.correo.OperacionInvalida;
import gal.udc.fic.vvs.email.correo.Reenvio;

/**
 * Unit test for simple App.
 */
public class AppTest {

    // Archivador - ArchivadorSimple
    @Test
    public void testArchivadorSimpleAlmacenarCorreoSaleBien() {
        Texto contenido = new Texto("nombre", "c");
        Carpeta carpeta = new Carpeta("nombre");

        Correo correoHijo = new Mensaje(contenido);
        Correo correoPadre = new CarpetaLimitada(carpeta, 1);

        try {
            correoPadre.añadir(correoHijo);
        } catch (OperacionInvalida e) {
            e.printStackTrace();
        }
        ArchivadorSimple archi = new ArchivadorSimple("", 5);
        boolean output = archi.almacenarCorreo(correoPadre);
        assertEquals(true, output);
    }
    
    @Test
    public void testArchivadorSimpleAlmacenarCorreoSaleMal() {
        Texto contenido = new Texto("nombre", "c");
        Carpeta carpeta = new Carpeta("nombre");

        Correo correoHijo = new Mensaje(contenido);
        Correo correoPadre = new CarpetaLimitada(carpeta, 1);

        try {
            correoPadre.añadir(correoHijo);
        } catch (OperacionInvalida e) {
            e.printStackTrace();
        }
        ArchivadorSimple archi = new ArchivadorSimple("", 1);
        boolean output = archi.almacenarCorreo(correoPadre);
        assertEquals(false, output);
    }
    
    @Test
    public void testArchivadorSimpleObtenerNombre() {
    	Archivador archi = new ArchivadorSimple("nombre", 1);
    	String output = archi.obtenerNombre();
    	assertEquals("nombre", output);
    }

    @Test
    public void testArchivadorSimpleObtenerEspacioTotal() {
    	Archivador archi = new ArchivadorSimple("nombre", 1);
    	int output = archi.obtenerEspacioTotal();
    	assertEquals(1, output);
    }
    
    @Test
    public void testArchivadorSimpleObtenerDelegado() {
    	Archivador archi = new ArchivadorSimple("nombre", 1);
    	Archivador output = archi.obtenerDelegado();
    	Archivador expected = null;
    	assertEquals(expected, output);
    }
    
    //Archivador - DecoradorArchivador
    /*@Test
    public void testDecoradorArchivadorSimpleObtenerNombre() {
    	Archivador archi = new ArchivadorSimple("nombre", 1);
    	Archivador deco = new DecoradorArchivador(archi);
    	String output = archi.obtenerNombre();
    	assertEquals("nombre", output);
    }*/
    
    //Archivador - Delegado
    @Test
    public void testDelegadoAlmacenarCorreo() {
        Texto contenido = new Texto("nombre", "c");
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

    //Archivador - Log
    @Test
    public void testLogAlmacenarCorreo() {
        Texto contenido = new Texto("nombre", "c");
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

    // Archivo
    @Test
    public void testAudioObtenerMimeType() {
        Audio audio = new Audio("nombre", "contenido");

        String output = "";
        try {
            Method method = Audio.class.getDeclaredMethod("obtenerMimeType");
            method.setAccessible(true);
            output = (String) method.invoke(audio);
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException e) {
            e.printStackTrace();
        }

        assertEquals("audio/ogg", output);
    }

    @Test
    public void testImagenObtenerMimeType() {
        Imagen img = new Imagen("nombre", "contenido");

        String output = "";
        try {
            Method method = Imagen.class.getDeclaredMethod("obtenerMimeType");
            method.setAccessible(true);
            output = (String) method.invoke(img);
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException e) {
            e.printStackTrace();
        }

        assertEquals("image/png", output);
    }

    @Test
    public void testTextoObtenerMimeType() {
        Texto texto = new Texto("nombre", "contenido");

        String output = "";
        try {
            Method method = Texto.class.getDeclaredMethod("obtenerMimeType");
            method.setAccessible(true);
            output = (String) method.invoke(texto);
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException e) {
            e.printStackTrace();
        }

        assertEquals("text/plain", output);
    }
    
    //Correo - Reenvio
    @Test
    public void testReenvioObtenerTamanho() {
    	Texto contenido = new Texto("nombre", "contenido");
    	MensajeAbstracto men = new Mensaje(contenido);
    	Correo correo = new Carpeta("nombreC");
    	Reenvio rv = new Reenvio(men, correo);
    	int output = rv.obtenerTamaño();
    	assertEquals(9, output);
    }
    
    @Test
    public void testReenvioObtenerVisualizacion() {
    	Texto contenido = new Texto("nombre", "contenido");
    	MensajeAbstracto men = new Mensaje(contenido);
    	Correo correo = new Carpeta("nombreC");
    	Reenvio rv = new Reenvio(men, correo);
    	String output = rv.obtenerVisualizacion();
    	String expected = "contenido" + "\n\n---- Correo reenviado ----\n\n" + "nombreC" + "\n---- Fin correo reenviado ----";
    	assertEquals(expected, output);
    }

    //Correo - Mensaje
    @Test
    public void testMensajeEstablecerLeido() {
    	Texto contenido = new Texto("nombre", "contenido");
        Mensaje mensaje = new Mensaje(contenido);
    	mensaje.establecerLeido(true);
    	int output = mensaje.obtenerNoLeidos();
    	assertEquals(0,output);
    }
    
    @Test
    public void testMensajeNoHayNoLeidos() {
    	Texto contenido = new Texto("nombre", "contenido");
        Mensaje mensaje = new Mensaje(contenido);
    	int output = mensaje.obtenerNoLeidos();
    	assertEquals(1,output);
    }
    
    @Test
    public void testMensajeObtenerIconoLeido() {
    	Texto contenido = new Texto("nombre", "contenido");
        Mensaje mensaje = new Mensaje(contenido);
    	mensaje.establecerLeido(true);
    	Integer output = mensaje.obtenerIcono();
    	Integer expected = new Integer(2);
    	assertEquals(expected,output);
    }
    
    @Test
    public void testMensajeObtenerIconoNoLeido() {
    	Texto contenido = new Texto("nombre", "contenido");
        Mensaje mensaje = new Mensaje(contenido);
    	Integer output = mensaje.obtenerIcono();
    	Integer expected = new Integer(3);
    	assertEquals(expected,output);
    }
    
    @Test
    public void testMensajeObtenerPrevisualizacion() {
    	Texto contenido = new Texto("nombre", "contenido");
        Mensaje mensaje = new Mensaje(contenido);
        String output = mensaje.obtenerPreVisualizacion();
        String expected = "contenido" + "...";
        assertEquals(expected, output);
    }
    
    @Test
    public void testMensajeBuscarSaleBien() {
        Texto contenido = new Texto("nombre", "contenido");
        Mensaje mensaje = new Mensaje(contenido);

        Collection output = mensaje.buscar("cont");
        Collection expected = new Vector();
        expected.add(mensaje);
        assertEquals(expected, output);
    }

    @Test
    public void testMensajeBuscarNoSeEncuentra() {
        Texto contenido = new Texto("nombre", "contenido");
        Mensaje mensaje = new Mensaje(contenido);

        Collection output = mensaje.buscar("no");
        Collection expected = new Vector();
        assertEquals(expected, output);
    }

    //Correo - DecoradorMensaje
    @Test
    public void testDecoradorMensajeBuscarSaleBien() {
        Texto contenido = new Texto("nombre", "contenido");
        MensajeAbstracto mensaje = new Mensaje(contenido);
        DecoradorMensaje deco = new Cabecera(mensaje, "nombre", "valor");

        Collection output = deco.buscar("contenido");
        Collection expected = new Vector();
        expected.add(deco);
        assertEquals(expected, output);
    }

    @Test
    public void testDecoradorMensajeBuscarNoSeEncuentra() {
        Texto contenido = new Texto("nombre", "contenido");
        MensajeAbstracto mensaje = new Mensaje(contenido);
        DecoradorMensaje deco = new Cabecera(mensaje, "nombre", "valor");

        Collection output = deco.buscar("no");
        Collection expected = new Vector();
        assertEquals(expected, output);
    }

    //Correo - Correo
    @Test
    public void testCorreoAbstractoObtenerRuta() {
        CorreoAbstracto ca = new Carpeta("nombre");
        Correo padre = new Carpeta("papa");
        //ca.establecerPadre(padre);
        try {
            Method method = CorreoAbstracto.class.getDeclaredMethod("establecerPadre", Correo.class);
            method.setAccessible(true);
            method.invoke(ca, padre);
        } catch(NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
        | InvocationTargetException e) {
            e.printStackTrace();
        }
        String output = ca.obtenerRuta();
        assertEquals("papa > nombre", output);
    }
    
    //Correo - Carpeta
    @Test
    public void testCarpetaEstablecerLeidoSaleBien() {
    	Carpeta carpeta = new Carpeta("nombre");
    	Correo correo = new Carpeta("nombre");
    	try {
			carpeta.añadir(correo);
		} catch (OperacionInvalida e) {
			e.printStackTrace();
		}
    	carpeta.establecerLeido(true);
    	int output = carpeta.obtenerNoLeidos();
    	assertEquals(0,output);
    }
    
    
    @Test
    public void testCarpetaEstablecerLeidoSaleMal() {
    	Carpeta carpeta = new Carpeta("nombre");
    	Correo correo = null;
    	String output = "";
    	try {
    		carpeta.añadir(correo);
    		carpeta.establecerLeido(true);
    	} catch (Exception e) {
    		output = new OperacionInvalida("error").toString();
    	}
    	String expected = new OperacionInvalida("error").toString();
    	assertEquals(expected, output);
    }
    
    @Test
    public void testCarpertaObtenerIcono() {
    	Carpeta carpeta = new Carpeta("nombre");
    	Integer output = carpeta.obtenerIcono();
    	Integer expected = new Integer(1);
    	assertEquals(expected, output);
    }
    
    @Test
    public void testCarpetaExplorar() {
        Carpeta carpeta = new Carpeta("nombre");
        Collection output = new Vector();
		try {
			output = carpeta.explorar();
		} catch (OperacionInvalida e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        Collection expected = new Vector();
        assertEquals(expected, output);
    }
    
    @Test
    public void testCarpetaBuscar() {
        Carpeta carpeta = new Carpeta("nombre");
        Collection output = carpeta.buscar("contenido");
        Collection expected = new Vector();
        assertEquals(expected, output);
    }

    @Test
    public void testCarpetaBuscarYAnhadir() {
        Texto contenido = new Texto("nombre", "contenido");
        Correo mensaje = new Mensaje(contenido);
        Carpeta carpeta = new Carpeta("carpeta");

        try {
            carpeta.añadir(mensaje);
        } catch (OperacionInvalida e) {
            e.printStackTrace();
        }
        Collection output = carpeta.buscar("contenido");
        Collection expected = new Vector();
        expected.add(mensaje);
        assertEquals(expected, output);
    }

    @Test
    public void testCarpetaEliminar() {
        Texto contenido = new Texto("nombre", "contenido");
        Correo mensaje = new Mensaje(contenido);
        Carpeta carpeta = new Carpeta("carpeta");

        try {
            carpeta.añadir(mensaje);
            carpeta.eliminar(mensaje);
        } catch (OperacionInvalida e) {
            e.printStackTrace();
        }
        Collection output = carpeta.buscar("contenido");
        Collection expected = new Vector();
        assertEquals(expected, output);
    }
    
    //Correo - Adjunto
    @Test
    public void testAdjuntoObtenerTamanho() {
    	Archivo archi = new Texto("nombre", "contenido");
    	Texto contenido = new Texto("nombre", "contenido");
    	Mensaje mensaje = new Mensaje(contenido);
    	MensajeAbstracto ma = new Cabecera(mensaje, "nombre", "valor");
    	Adjunto adj = new Adjunto(ma, archi);
    	
    	int output = adj.obtenerTamaño();
    	assertEquals(29, output);
    }
    
    @Test
    public void testAdjuntoObtenerVisualizacion() {
    	Archivo archi = new Texto("nombre", "contenido");
    	Texto contenido = new Texto("nombre", "contenido");
    	Mensaje mensaje = new Mensaje(contenido);
    	MensajeAbstracto ma = new Cabecera(mensaje, "nombre", "valor");
    	Adjunto adj = new Adjunto(ma, archi);
    	
    	String output = adj.obtenerVisualizacion();
    	String expected = "nombre: valor\ncontenido\n\nAdxunto: nombre(9 bytes, text/plain)";
    	assertEquals(expected, output);
    }
}
