package es.ies.puerto;

import es.ies.puerto.model.Empleado;
import es.ies.puerto.model.fichero.FileOperations;

public class EjemploFile {
    public static void main(String[] args) {
        FileOperations fileOperations = new FileOperations();
        Empleado empleado = new Empleado("01", "nombre2", "puesto1", 1200, "06-09-2001");
        String identificador = "01";
        fileOperations.update(empleado);
    }    
}