package es.ies.puerto.model.fichero;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import es.ies.puerto.model.Empleado;
import es.ies.puerto.model.Operations;

/**
 * @author eduglezexp
 * @version 1.0.0
 */

public class FileOperations implements Operations {
    File fichero;
    String path = "/Users/eduex/OneDrive/Escritorio/1DAM/PRO/Repository/empleado-ficheros/java-ficheros-empleado/src/main/resources/empleados.txt";
    
    /**
     * Constructor por defecto
     */
    public FileOperations() {
        fichero = new File(path);
        if (!fichero.exists() || !fichero.isFile()) {
            throw new IllegalArgumentException("El recurso no es de tipo fichero" +path);
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
        String identificador = empleado.getIdentificador();
        Empleado empleadoExistente = read(identificador);
        if (empleadoExistente != null) {
            return false; 
        }
        return create(empleado.toString(), fichero);
    }

    /**
     * Funcion para crear un nuevo empleado
     * @param data informacion 
     * @param file fichero
     * @return true/false
     */
    private boolean create(String data,File file) {
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
     * @param identificador del empleado
     * @return empleado
     */
    @Override
    public Empleado read(String identificador) {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(", ");
                if (data[0].equals(identificador)) {
                    return new Empleado(data[0], data[1], data[2], Double.parseDouble(data[3]), data[4]);
                }
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    /**
     * Funcion para leer a un empleado, dado un empleado
     * @param empleado a leer
     * @return empleado
     */
    @Override
    public Empleado read(Empleado empleado) {
        return read(empleado.getIdentificador());
    }
    
    /**
     * Funcion para actualizar un empleado, dado un empleado
     * @param empleado a actualizar
     * @return true/false
     */
    @Override
    public boolean update(Empleado empleado) {
        List<Empleado> empleados = getAllEmpleados();
        boolean updated = false;
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {
            for (Empleado empleadoBuscar : empleados) {
                if (empleadoBuscar.getIdentificador().equals(empleado.getIdentificador())) {
                    bw.write(empleado.toString());
                    updated = true;
                } else {
                    bw.write(empleadoBuscar.toString());
                }
                bw.newLine();
            }
        } catch (IOException exception) {
            exception.printStackTrace();
            return false;
        }
        return updated;
    }

    /**
     * Funcion para eliminar a un empleado, dado su identificador
     * @param identificador del empleado
     * @return true/false
     */
    @Override
    public boolean delete(String identificador) {
        List<Empleado> empleados = getAllEmpleados();
        boolean deleted = empleados.removeIf(e -> e.getIdentificador().equals(identificador));
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {
            for (Empleado empleado : empleados) {
                bw.write(empleado.toString());
                bw.newLine();
            }
        } catch (IOException exception) {
            exception.printStackTrace();
            return false;
        }
        return deleted;
    }

    /**
     * Funcion para obtener todos los empleados 
     * @return lsita de empleados
     */
    private List<Empleado> getAllEmpleados() {
        List<Empleado> empleados = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(", ");
                empleados.add(new Empleado(data[0], data[1], data[2], Double.parseDouble(data[3]), data[4]));
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
    public Set<Empleado> empleadosPorPuesto(String puesto) {
        Set<Empleado> empleadosFiltrados = new HashSet<>();
        for (Empleado empleado : getAllEmpleados()) {
            if (empleado.getPuesto().equalsIgnoreCase(puesto)) {
                empleadosFiltrados.add(empleado);
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
    public Set<Empleado> empleadosPorEdad(String fechaInicio, String fechaFin) {
        Set<Empleado> empleadosFiltrados = new HashSet<>();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    try {
        LocalDate inicio = LocalDate.parse(fechaInicio, formatter);
        LocalDate fin = LocalDate.parse(fechaFin, formatter);
        
        for (Empleado empleado : getAllEmpleados()) {
            LocalDate fechaNacimiento = LocalDate.parse(empleado.getFechaNacimiento(), formatter);
            if ((fechaNacimiento.isEqual(inicio) || fechaNacimiento.isAfter(inicio)) &&
                (fechaNacimiento.isEqual(fin) || fechaNacimiento.isBefore(fin))) {
                empleadosFiltrados.add(empleado);
            }
        }
    } catch (DateTimeParseException exception) {
        exception.printStackTrace();
    }
        return empleadosFiltrados;
    }
}