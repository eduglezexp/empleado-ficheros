package es.ies.puerto;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

import es.ies.puerto.model.Empleado;
import es.ies.puerto.model.fichero.FileOperations;

public class FileOperationsTest {
private FileOperations fileOps;
    private Empleado testEmpleado;

    @BeforeEach
    void setUp() {
        fileOps = new FileOperations();
        testEmpleado = new Empleado("99", "Test User", "Tester", 5000.0, "01/01/1990");
        fileOps.create(testEmpleado);
    }

    @AfterEach
    void tearDown() {
        fileOps.delete("99");
    }

    @Test
    void testCreate() {
        assertTrue(fileOps.create(new Empleado("100", "Test User 2", "Developer", 4500.0, "02/02/1992")));
        fileOps.delete("100");
    }

    @Test
    void testRead() {
        Empleado empleado = fileOps.read("99");
        assertNotNull(empleado);
        assertEquals("Test User", empleado.getNombre());
    }

    @Test
    void testUpdate() {
        testEmpleado.setSalario(6000.0);
        assertTrue(fileOps.update(testEmpleado));
        assertEquals(6000.0, fileOps.read("99").getSalario());
    }

    @Test
    void testDelete() {
        assertTrue(fileOps.delete("99"));
        assertNull(fileOps.read("99"));
    }
}