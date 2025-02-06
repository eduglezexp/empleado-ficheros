package es.ies.puerto.model;

/**
 * @author eduglezexp
 * @version 1.0.0
 */

public class Empleado extends Persona{
    private String puesto;
    private double salario;

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
        super(identificador);
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
        super(identificador, nombre, fechaNacimiento);
        this.puesto = puesto;
        this.salario = salario;
    }
    
    /**
     * Getters and Setters
     */
    public String getPuesto() { 
        return puesto; 
    }

    public double getSalario() { 
        return salario; 
    }

    public void setPuesto(String puesto) { 
        this.puesto = puesto; 
    }

    public void setSalario(double salario) { 
        this.salario = salario; 
    }
    
    @Override
    public String toString() {
        return getIdentificador() + ", " + getNombre() + ", " + getPuesto() + ", " + getSalario() + ", " + getFechaNacimiento();
    }
}