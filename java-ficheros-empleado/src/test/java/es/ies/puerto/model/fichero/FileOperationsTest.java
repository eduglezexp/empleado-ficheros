package es.ies.puerto.model.fichero;

import es.ies.puerto.model.Empleado;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Set;

public class FileOperationsTest {

    private FileOperations fileOperations;
    private Empleado empleado1;
    private Empleado empleado2;
    private Empleado empleado3;
    private Empleado empleado4;

    @BeforeEach
    public void setUp() {
        fileOperations = new FileOperations();
        empleado1 = new Empleado("12345", "Juan", "Manager", 3000, "10/05/1980");
        empleado2 = new Empleado("67890", "Ana", "Developer", 2500, "15/03/1990");
        empleado3 = new Empleado("11223", "Carlos", "Designer", 2800, "25/07/1995");
        empleado4 = new Empleado("44556", "Marta", "Manager", 3200, "05/12/1985");
    }

    @Test
    public void testCreateEmpleado() {
        boolean result = fileOperations.create(empleado1);
        assertTrue(result, "El empleado debería haberse creado correctamente.");
        result = fileOperations.create(empleado1); 
        assertFalse(result, "No debería permitir crear un empleado con el mismo identificador.");
    }

    @Test
    public void testReadEmpleadoExistente() {
        fileOperations.create(empleado1);
        Empleado result = fileOperations.read("12345");
        assertNotNull(result, "Debería encontrar al empleado.");
        assertEquals(empleado1.getIdentificador(), result.getIdentificador(), "Los identificadores deben coincidir.");
    }

    @Test
    public void testReadEmpleadoNoExistente() {
        Empleado result = fileOperations.read("00000");
        assertNull(result, "El empleado con ese identificador no debería existir.");
    }

    @Test
    public void testUpdateEmpleadoExistente() {
        fileOperations.create(empleado1);
        empleado1.setPuesto("Senior Manager");
        boolean result = fileOperations.update(empleado1);
        assertTrue(result, "El empleado debería haberse actualizado correctamente.");
        Empleado updatedEmpleado = fileOperations.read("12345");
        assertEquals("Senior Manager", updatedEmpleado.getPuesto(), "El puesto debería haberse actualizado.");
    }

    @Test
    public void testUpdateEmpleadoNoExistente() {
        empleado2.setPuesto("Senior Developer");
        boolean result = fileOperations.update(empleado2);
        assertFalse(result, "No debería permitir actualizar un empleado que no existe.");
    }

    @Test
    public void testDeleteEmpleadoExistente() {
        fileOperations.create(empleado1);
        boolean result = fileOperations.delete("12345");
        assertTrue(result, "El empleado debería haberse eliminado correctamente.");
        Empleado deletedEmpleado = fileOperations.read("12345");
        assertNull(deletedEmpleado, "El empleado ya no debería existir.");
    }

    @Test
    public void testDeleteEmpleadoNoExistente() {
        boolean result = fileOperations.delete("00000");
        assertFalse(result, "No debería permitir eliminar un empleado que no existe.");
    }

    @Test
    public void testEmpleadosPorPuesto() {
        fileOperations.create(empleado1);
        fileOperations.create(empleado2);
        Set<Empleado> result = fileOperations.empleadosPorPuesto("Manager");
        assertEquals(1, result.size(), "Debería haber un solo empleado con el puesto 'Manager'.");
        assertTrue(result.contains(empleado1), "El conjunto debería contener al empleado con el puesto 'Manager'.");
    }

    @Test
    public void testEmpleadosPorPuestoNoExistente() {
        Set<Empleado> result = fileOperations.empleadosPorPuesto("CEO");
        assertTrue(result.isEmpty(), "No debería haber empleados con el puesto 'CEO'.");
    }

    @Test
    public void testEmpleadosPorEdad() {
        fileOperations.create(empleado3);
        fileOperations.create(empleado4);
        Set<Empleado> result = fileOperations.empleadosPorEdad("01/01/1985", "01/01/1995");
        assertTrue(result.contains(empleado3), "El empleado Carlos debería estar en el rango de edades.");
        assertFalse(result.contains(empleado4), "El empleado Marta no debería estar en el rango de edades.");
    }

    @Test
    public void testEmpleadosPorEdadNoExistente() {
        Set<Empleado> result = fileOperations.empleadosPorEdad("01/01/2000", "01/01/2005");
        assertTrue(result.isEmpty(), "No deberían existir empleados en el rango de edades.");
    }
}