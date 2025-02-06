package es.ies.puerto.model.fichero;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Map;
import java.util.TreeMap;

import es.ies.puerto.model.Empleado;
import es.ies.puerto.model.interfaces.OperationsMap;

/**
 * @author eduglezexp
 * @version 1.0.0
 */

public class FileOperationsMap implements OperationsMap {
    private File file;
    private String rutaAbsoluta; 

    /**
     * Constructor por defecto
     */
    public FileOperationsMap() {
        ClassLoader classLoader = getClass().getClassLoader();
        URL resourceUrl = classLoader.getResource("empleados.txt");
        if (resourceUrl == null) {
            throw new IllegalArgumentException("El archivo no se encontro en el classpath: empleados.txt");
        }
        this.rutaAbsoluta = resourceUrl.getFile(); 
        this.file = new File(rutaAbsoluta);
        if (!file.exists() || !file.isFile()) {
            throw new IllegalArgumentException("El recurso no es de tipo fichero: " + rutaAbsoluta);
        }
    }

    /**
     * Funcion para crear un nuevo empleado
     * @param empleado a crear
     * @return true/false
     */
    @Override
    public boolean create(Empleado empleado) {
        if (empleado == null || empleado.getIdentificador() == null) {
            return false;
        }
        Map<String, Empleado> empleados = read(file);
        if (empleados.containsKey(empleado.getIdentificador())) {
            return false;
        }
        return create(empleado.toString(), file);
    }

    /**
     * Funcion para crear un nuevo empleado
     * @param data informacion 
     * @param file fichero
     * @return true/false
     */
    private boolean create(String data, File file) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write(data);
            writer.newLine();
            return true;
        } catch (IOException exception) {
            exception.printStackTrace();
            return false;
        }
    }

    /**
     * Funcion para leer a un empleado, dado un identificador
     * @param file a leer
     * @return mapa de empleados
     */
    public Map<String, Empleado> read(File file) {
        Map<String, Empleado> empleados = new TreeMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] arrayline = line.split(",");
                Empleado empleado = new Empleado(arrayline[0], arrayline[1], arrayline[2], Double.parseDouble(arrayline[3]), arrayline[4]);
                empleados.put(empleado.getIdentificador(), empleado);
            }
        } catch (IOException e) {
            return new TreeMap<>();
        }
        return empleados;
    }

    /**
     * Funcion para leer a un empleado, dado un identificador
     * @param identificador del empleado
     * @return empleado
     */
    @Override
    public Empleado read(String identificador) {
        if (identificador == null || identificador.isEmpty()) {
            return null;
        }
        Map<String, Empleado> empleados = read(file);
        return empleados.get(identificador);
    }

    /**
     * Funcion para leer a un empleado, dado un empleado
     * @param empleado a leer
     * @return empleado
     */
    @Override
    public Empleado read(Empleado empleado) {
        if (empleado == null || empleado.getIdentificador() == null) {
            return empleado;
        }
        Map<String, Empleado> empleados = read(file);
        return empleados.get(empleado.getIdentificador());
    }
    
    /**
     * Funcion para actualizar un empleado, dado un empleado
     * @param empleado a actualizar
     * @return true/false
     */
    @Override
    public boolean update(Empleado empleado) {
        Map<String, Empleado> empleados = getAllEmpleados();
        if (empleados.containsKey(empleado.getIdentificador())) {
            empleados.put(empleado.getIdentificador(), empleado);
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(rutaAbsoluta))) {
                for (Empleado emp : empleados.values()) {
                    bw.write(emp.toString());
                    bw.newLine();
                }
                return true;
            } catch (IOException exception) {
                exception.printStackTrace();
                return false;
            }
        }
        return false;
    }

    /**
     * Funcion para eliminar a un empleado, dado su identificador
     * @param identificador del empleado
     * @return true/false
     */
    @Override
    public boolean delete(String identificador) {
        Map<String, Empleado> empleados = getAllEmpleados();
        if (empleados.remove(identificador) != null) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(rutaAbsoluta))) {
                for (Empleado empleado : empleados.values()) {
                    bw.write(empleado.toString());
                    bw.newLine();
                }
                return true;
            } catch (IOException exception) {
                exception.printStackTrace();
                return false;
            }
        }
        return false;
    }

    /**
     * Funcion para obtener todos los empleados 
     * @return mapa de empleados
     */
    private Map<String, Empleado> getAllEmpleados() {
        Map<String, Empleado> empleados = new TreeMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(rutaAbsoluta))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(", ");
                Empleado empleado = new Empleado(data[0], data[1], data[2], Double.parseDouble(data[3]), data[4]);
                empleados.put(empleado.getIdentificador(), empleado);
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return empleados;
    }

    /**
     * Funcion que obtiene un listado de empleados de un puesto concreto
     * @param puesto al cual se obtendra la lista
     * @return lista de empleados de un puesto concreto
     */
    @Override
    public Map<String, Empleado> empleadosPorPuesto(String puesto) {
        Map<String, Empleado> empleadosFiltrados = new TreeMap<>();
        for (Empleado empleado : getAllEmpleados().values()) {
            if (empleado.getPuesto().equalsIgnoreCase(puesto)) {
                empleadosFiltrados.put(empleado.getIdentificador(), empleado);
            }
        }
        return empleadosFiltrados;
    }

    /**
     * Funcion que obtiene un listado de empleados entre dos fechas
     * @param fechaInicio fecha de inicio 
     * @param fechaFin fecha del final
     * @return lista de empleados entre dos fechas concretas
     */
    @Override
    public Map<String, Empleado> empleadosPorEdad(String fechaInicio, String fechaFin) {
        Map<String, Empleado> empleadosFiltrados = new TreeMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try {
            LocalDate inicio = LocalDate.parse(fechaInicio, formatter);
            LocalDate fin = LocalDate.parse(fechaFin, formatter);
            
            for (Empleado empleado : getAllEmpleados().values()) {
                LocalDate fechaNacimiento = LocalDate.parse(empleado.getFechaNacimiento(), formatter);
                if ((fechaNacimiento.isEqual(inicio) || fechaNacimiento.isAfter(inicio)) &&
                    (fechaNacimiento.isEqual(fin) || fechaNacimiento.isBefore(fin))) {
                    empleadosFiltrados.put(empleado.getIdentificador(), empleado);
                }
            }
        } catch (DateTimeParseException exception) {
            exception.printStackTrace();
        }
        return empleadosFiltrados;
    }
}