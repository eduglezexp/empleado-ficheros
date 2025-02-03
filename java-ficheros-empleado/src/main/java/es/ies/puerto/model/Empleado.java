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

public class Empleado {
    private String identificador;
    private String nombre;
    private String puesto;
    private double salario;
    private String fechaNacimiento;

    /**
     * Constructor por defecto
     */
    public Empleado() {

    }

    /**
     * Constructor con la propiedad del identificador
     * @param identificador
     */
    public Empleado(String identificador) {
        this.identificador = identificador;
    }

    /**
     * Constructor con todas las propiedades
     * @param identificador
     * @param nombre
     * @param puesto
     * @param salario
     * @param fechaNacimiento
     */
    public Empleado(String identificador, String nombre, String puesto, double salario, String fechaNacimiento) {
        this.identificador = identificador;
        this.nombre = nombre;
        this.puesto = puesto;
        this.salario = salario;
        this.fechaNacimiento = fechaNacimiento;
    }
    
    /**
     * Getters and Setters
     */
    public String getIdentificador() { 
        return identificador; 
    }
    
    public String getNombre() {
        return nombre; 
    }
    public String getPuesto() { 
        return puesto; 
    }

    public double getSalario() { 
        return salario; 
    }

    public String getFechaNacimiento() { 
        return fechaNacimiento; 
    }
    
    public void setNombre(String nombre) { 
        this.nombre = nombre; 
    }

    public void setPuesto(String puesto) { 
        this.puesto = puesto; 
    }

    public void setSalario(double salario) { 
        this.salario = salario; 
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
        return identificador + ", " + nombre + ", " + puesto + ", " + salario + ", " + fechaNacimiento;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Empleado empleado = (Empleado) obj;
        return Objects.equals(identificador, empleado.identificador);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(identificador);
    }
}