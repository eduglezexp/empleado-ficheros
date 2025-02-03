package es.ies.puerto.model;

import java.util.Set;

/**
 * @author eduglezexp
 * @version 1.0.0
 */

public interface Operations {
    /**
     * Funcion para crear un nuevo empleado
     * @param empleado a crear
     * @return true/false
     */
    boolean create(Empleado empleado);
    /**
     * Funcion para leer a un empleado, dado un identificador
     * @param identificador del empleado
     * @return empleado
     */
    Empleado read(String identificador);
    /**
     * Funcion para leer a un empleado, dado un empleado
     * @param empleado a leer
     * @return empleado
     */
    Empleado read(Empleado empleado);
    /**
     * Funcion para actualizar un empleado, dado un empleado
     * @param empleado a actualizar
     * @return true/false
     */
    boolean update(Empleado empleado);
    /**
     * Funcion para eliminar a un empleado, dado su identificador
     * @param identificador del empleado
     * @return true/false
     */
    boolean delete(String identificador);
    /**
     * Funcion que obtiene un listado de empleados de un puesto concreto
     * @param puesto al cual se obtendra la lista
     * @return lista de empleados de un puesto concreto
     */
    Set<Empleado> empleadosPorPuesto(String puesto);
    /**
     * Funcion que obtiene un listado de empleados entre dos fechas
     * @param fechaInicio fecha de inicio 
     * @param fechaFin fecha del final
     * @return lista de empleados entre dos fechas concretas
     */
    Set<Empleado> empleadosPorEdad(String fechaInicio, String fechaFin);
}