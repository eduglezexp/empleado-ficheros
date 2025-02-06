package es.ies.puerto.model;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

/**
 * @author eduglezexp
 * @version 1.0.0
 */

public class Persona {
    private String identificador;
    private String nombre;
    private String fechaNacimiento;

    /**
     * Constructor por defecto
     */
    public Persona() {
    }

    /**
     * Constructor con la propiedad del identificador
     * @param identificador
     */
    public Persona(String identificador) {
        this.identificador = identificador;
    }

    /**
     * Constructor con todas las propiedades
     * @param identificador
     * @param nombre
     * @param fechaNacimiento
     */
    public Persona(String identificador, String nombre, String fechaNacimiento) {
        this.identificador = identificador;
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
    }

    /**
     * Getters and Setters
     */
    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public int getEdad() {
        try {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate fechaParse = LocalDate.parse(fechaNacimiento, formatter);
        return Period.between(fechaParse, LocalDate.now()).getYears();
        } catch (DateTimeParseException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public String toString() {
        return getIdentificador() + "," + getNombre() + "," + getFechaNacimiento();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Persona)) {
            return false;
        }
        Persona persona = (Persona) o;
        return Objects.equals(identificador, persona.identificador);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identificador);
    }    
}