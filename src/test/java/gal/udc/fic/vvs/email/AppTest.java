package gal.udc.fic.vvs.email;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Vector;

import org.junit.Assert;
import org.junit.Test;

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

/**
 * Unit test for simple App.
 */
public class AppTest {

    // Archivador
    @Test
    public void testArchivadorSimpleAlmacenarCorreo() {
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
        archi.almacenarCorreo(correoPadre);
        assertEquals(4, archi.obtenerEspacioDisponible());
    }

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

    // Correo
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
    
    /*
    @Test
    public void testCarpetaEstablecerLeidoSaleMal() {
    	Carpeta carpeta = new Carpeta("nombre");
    	Correo correo = null;
    	String oi = "";
    	try {
    		carpeta.añadir(correo);
    		carpeta.establecerLeido(true);
    	} catch (Exception e) {
    		oi = new OperacionInvalida("error").toString();
    	}
    	String expected = new OperacionInvalida("error").toString();
    	assertEquals(expected, oi);
    }*/

    @Test
    public void testCarpetaEstablecerLeidoSaleMal() {
    	Carpeta carpeta = new Carpeta("nombre");
    	Exception output = null;
    	try {
    		carpeta.añadir(carpeta);
    		carpeta.establecerLeido(true);
    	} catch (OperacionInvalida e) {
    		output = e;
    	} catch (NullPointerException e) {
    		output = e;
    	}
    	Exception expected = new OperacionInvalida();
    	assertEquals(expected.getClass(), output.getClass());
    }

    @Test
    public void testCarpetaExplorar() {
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
