package es.ies.puerto.model;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EmpleadoTest {

    private Empleado empleado;

    @BeforeEach
    void setUp() {
        empleado = new Empleado("01", "Juan Perez", "Desarrollador", 2500.0, "06/09/2001");
    }

    @Test
    void testGetters() {
        assertEquals("01", empleado.getIdentificador());
        assertEquals("Juan Perez", empleado.getNombre());
        assertEquals("Desarrollador", empleado.getPuesto());
        assertEquals(2500.0, empleado.getSalario());
        assertEquals("06/09/2001", empleado.getFechaNacimiento());
    }

    @Test
    void testSetters() {
        empleado.setNombre("Carlos Gómez");
        empleado.setPuesto("Arquitecto Software");
        empleado.setSalario(3000.0);
        
        assertEquals("Carlos Gómez", empleado.getNombre());
        assertEquals("Arquitecto Software", empleado.getPuesto());
        assertEquals(3000.0, empleado.getSalario());
    }

    @Test
    void testGetEdad() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate fechaNacimiento = LocalDate.parse("06/09/2001", formatter);
        int edadEsperada = Period.between(fechaNacimiento, LocalDate.now()).getYears();
        assertEquals(edadEsperada, empleado.getEdad());
    }

    @Test
    void testToString() {
        String esperado = "01, Juan Perez, Desarrollador, 2500.0, 06/09/2001";
        assertEquals(esperado, empleado.toString());
    }

    @Test
    void testEquals() {
        Empleado otroEmpleado = new Empleado("01", "Juan Perez", "Desarrollador", 2500.0, "06/09/2001");
        Empleado diferente = new Empleado("02", "Maria Lopez", "Diseñador", 1800.0, "15/03/1995");

        assertEquals(empleado, otroEmpleado);
        assertNotEquals(empleado, diferente);
    }

    @Test
    void testHashCode() {
        Empleado otroEmpleado = new Empleado("01", "Juan Perez", "Desarrollador", 2500.0, "06/09/2001");
        assertEquals(empleado.hashCode(), otroEmpleado.hashCode());
    }
}

