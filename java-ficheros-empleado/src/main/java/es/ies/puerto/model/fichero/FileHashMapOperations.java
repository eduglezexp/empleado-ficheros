package es.ies.puerto.model.fichero;

import java.io.File;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import es.ies.puerto.model.Empleado;

/**
 * @author eduglezexp
 * @version 1.0.0
 */

public class FileHashMapOperations extends FileOperations{

    /**
     * Constructor por defecto
     */
    public FileHashMapOperations() {
        super();
    }

    private Map<String, Empleado> fileToMap(File file) {
        Map<String, Empleado> empleadoMap = new TreeMap<>();
        Set<Empleado> empleados = super.read(file);
        for (Empleado empleado : empleados) {
            empleadoMap.put(empleado.getIdentificador(), empleado);
        }
        return empleadoMap;
    }
}
