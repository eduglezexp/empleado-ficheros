package es.ies.puerto;

import java.util.Map;
import java.util.Set;

import es.ies.puerto.model.Empleado;
import es.ies.puerto.model.fichero.FileOperationsSet;
import es.ies.puerto.model.fichero.FileOperationsMap;

/**
 * @author eduglezexp
 * @version 1.0.0
 */

public class EjemploFile {
    public static void main(String[] args) {
        //FileOperationsSet fileOperations = new FileOperationsSet();
        FileOperationsMap fileOperations = new FileOperationsMap();
        Empleado empleado1 = new Empleado("01", "Juan Perez", "Desarrollador", 2000, "06/09/2001");
        Empleado empleado2 = new Empleado("02", "Maria Lopez", "Diseniador", 1800, "15/03/1995");
        Empleado empleado3 = new Empleado("03", "Carlos Gomez", "Desarrollador", 2200, "21/07/1988");
        Empleado empleado4 = new Empleado("04", "Laura Martinez", "Gerente", 3000, "10/12/1980");
        Empleado empleado5 = new Empleado("05", "Pedro Gomez", "Diseniador", 1900, "02/05/1992");
        String fechaInicio = "01/01/1990";
        String fechaFinal = "31/12/2000";

        fileOperations.create(empleado1);
        fileOperations.create(empleado2);
        fileOperations.create(empleado3);
        fileOperations.create(empleado4);
        fileOperations.create(empleado5);
        System.out.println(fileOperations);

        System.out.println("Empleado leído (01): " +fileOperations.read("01"));
        System.out.println("Empleado leído (emp3): " +fileOperations.read(empleado3));

        Empleado empleadoActualizado = new Empleado("05", "Pedro Gomez", "Desarollador", 2500, "02/05/1992");
        fileOperations.update(empleadoActualizado);
        System.out.println("Empleado actualizado (05): " +fileOperations.read("05"));

        fileOperations.delete("04");
        System.out.println("Intentando leer empleado eliminado (04): " +fileOperations.read("04"));
        
        //Set<Empleado> desarrolladores = fileOperations.empleadosPorPuesto("Desarrollador");
        //System.out.println("Empleados en el puesto de Desarrollador: " +desarrolladores);

        //Set<Empleado> empleadosPorEdad = fileOperations.empleadosPorEdad(fechaInicio, fechaFinal);
        //System.out.println("Empleados nacidos entre " +fechaInicio + " y " +fechaFinal + ": " +empleadosPorEdad);

        Map<String, Empleado> desarrolladoresMap = fileOperations.empleadosPorPuesto("Desarrollador");
        System.out.println("Empleados en el puesto de Desarrollador: " +desarrolladoresMap);

        Map<String, Empleado> empleadosPorEdadMap = fileOperations.empleadosPorEdad(fechaInicio, fechaFinal);
        System.out.println("Empleados nacidos entre " +fechaInicio + " y " +fechaFinal + ": " +empleadosPorEdadMap);
    }    
}