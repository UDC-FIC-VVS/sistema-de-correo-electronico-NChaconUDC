package gal.udc.fic.vvs.email;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Test;

import gal.udc.fic.vvs.email.archivo.Texto;

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

    @Test
    public void testTextoObtenerMimeType() throws NoSuchMethodException, SecurityException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {
        Texto texto = new Texto("nombre", "contenido");

        Method method = Texto.class.getDeclaredMethod("obtenerMimeType");
        method.setAccessible(true);
        String output = (String) method.invoke(texto);
 
        assertEquals(output, "text/plain");
    }
}
