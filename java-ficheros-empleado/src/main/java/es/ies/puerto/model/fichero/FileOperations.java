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
import java.util.HashSet;
import java.util.Set;

import es.ies.puerto.model.Empleado;
import es.ies.puerto.model.Operations;

/**
 * @author eduglezexp
 * @version 1.0.0
 */

public class FileOperations implements Operations {
    private File file;
    private String rutaAbsoluta; 

    /**
     * Constructor por defecto
     */
    public FileOperations() {
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
        Set<Empleado> empleados = read(file);
        if (empleados.contains(empleado)) {
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
     * @param file a leer
     * @return lista de empleado
     */
    public Set<Empleado> read(File file) {
        Set<Empleado> empleados = new HashSet<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] arrayline = line.split(",");
                Empleado empleado = new Empleado(arrayline[0], arrayline[1], arrayline[2], Double.parseDouble(arrayline[3]), arrayline[4]);
                empleados.add(empleado);
            }
        } catch (IOException e) {
            return new HashSet<>();
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
        Empleado empleado = new Empleado(identificador);
        return read(empleado);
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
        Set<Empleado> empleados = read(file);
        if (empleados.contains(empleado)) {
            for (Empleado personaBuscar : empleados) {
                if (personaBuscar.equals(empleado)) {
                    return personaBuscar;
                }
            }
        }
        return empleado;
    }
    
    /**
     * Funcion para actualizar un empleado, dado un empleado
     * @param empleado a actualizar
     * @return true/false
     */
    @Override
    public boolean update(Empleado empleado) {
        Set<Empleado> empleados = getAllEmpleados();
        boolean updated = false;
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(rutaAbsoluta))) {
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
        Set<Empleado> empleados = getAllEmpleados();
        boolean deleted = empleados.removeIf(e -> e.getIdentificador().equals(identificador));
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(rutaAbsoluta))) {
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
    private Set<Empleado> getAllEmpleados() {
        Set<Empleado> empleados = new HashSet<>();
        try (BufferedReader br = new BufferedReader(new FileReader(rutaAbsoluta))) {
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