package es.ies.puerto.model;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

public class Empleado {
    private String identificador;
    private String nombre;
    private String puesto;
    private double salario;
    private String fechaNacimiento;
    
    public Empleado(String identificador, String nombre, String puesto, double salario, String fechaNacimiento) {
        this.identificador = identificador;
        this.nombre = nombre;
        this.puesto = puesto;
        this.salario = salario;
        this.fechaNacimiento = fechaNacimiento;
    }
    
    public String getIdentificador() { return identificador; }
    public String getNombre() { return nombre; }
    public String getPuesto() { return puesto; }
    public double getSalario() { return salario; }
    public String getFechaNacimiento() { return fechaNacimiento; }
    
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setPuesto(String puesto) { this.puesto = puesto; }
    public void setSalario(double salario) { this.salario = salario; }
    
    public int getEdad() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date fechaNac = (Date) sdf.parse(fechaNacimiento);
            Calendar calNac = Calendar.getInstance();
            calNac.setTime(fechaNac);
            
            Calendar calHoy = Calendar.getInstance();
            int edad = calHoy.get(Calendar.YEAR) - calNac.get(Calendar.YEAR);
            if (calHoy.get(Calendar.DAY_OF_YEAR) < calNac.get(Calendar.DAY_OF_YEAR)) {
                edad--;
            }
            return edad;
        } catch (ParseException e) {
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