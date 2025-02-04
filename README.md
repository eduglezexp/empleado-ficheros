# **Sistema de Gestión de Empleados en Archivos**

<img src="img/frontpage.webp">

## **Descripción**

Este proyecto es una solución para gestionar empleados mediante operaciones básicas (crear, leer, actualizar, eliminar) sobre un archivo de texto. La aplicación utiliza clases de Java para realizar las operaciones y almacenar los datos de los empleados en un archivo de texto, proporcionando funcionalidades adicionales para filtrar empleados por puesto y edad.

## **Estructura del Proyecto**

- **`es.ies.puerto.model`**: Contiene las clases de modelo, como `Empleado` que define las propiedades de un empleado y las operaciones asociadas a ellos.
- **`es.ies.puerto.model.fichero`**: Contiene la clase `FileOperations`, que implementa las operaciones sobre los empleados almacenados en un archivo.
- **`es.ies.puerto`**: Contiene el archivo principal `EjemploFile`, que simula las operaciones de lectura, escritura, actualización y eliminación sobre un conjunto de empleados.

## **Funcionalidades Principales**

Este sistema permite realizar las siguientes operaciones sobre los empleados:

1. **Crear un nuevo empleado**: Añadir empleados al archivo, verificando que no existan duplicados.
2. **Leer empleados por identificador**: Buscar un empleado por su identificador único.
3. **Actualizar un empleado**: Modificar la información de un empleado existente.
4. **Eliminar un empleado**: Eliminar un empleado dado su identificador.
5. **Filtrar empleados por puesto**: Obtener un conjunto de empleados que ocupan un puesto específico.
6. **Filtrar empleados por edad**: Obtener un conjunto de empleados nacidos en un rango de fechas.

## **Instrucciones para Ejecutar el Proyecto**

### 1. **Contenido del Archivo de Empleados**
   El archivo de texto `empleados.txt` contendrá la información de los empleados en el siguiente formato:

identificador, nombre, puesto, salario, fechaNacimiento


### 2. **Funcionamiento de las Operaciones**

#### **Crear Empleados**
- Se crean varios empleados utilizando el método `create()` de la clase `FileOperations`. Esto añade los empleados al archivo `empleados.txt`.
- Ejemplo:
  ```java
  fileOperations.create(empleado1);
  ```

#### **Leer Empleados**
- Se pueden leer los detalles de un empleado buscando por su identificador utilizando el método `read()`.
- Ejemplo:
  ```java
  Empleado empleadoLeido = fileOperations.read("01");
  System.out.println(empleadoLeido);
  ```

#### **Actualizar Empleados**
- Para actualizar un empleado, se crea una nueva instancia del empleado con el mismo identificador pero con los nuevos detalles, y luego se llama a `update()` para actualizar el archivo.
- Ejemplo:
  ```java
  Empleado empleadoActualizado = new Empleado("05", "Pedro Gomez", "Desarrollador", 2500, "02/05/1992");
  fileOperations.update(empleadoActualizado);
  ```

#### **Eliminar Empleados**
- Para eliminar un empleado, basta con proporcionar su identificador al método `delete()`.
- Ejemplo:
  ```java
  fileOperations.delete("04");
  ```

#### **Filtrar Empleados por Puesto**
- Puedes obtener una lista de empleados que ocupan un puesto específico usando el método `empleadosPorPuesto()`.
- Ejemplo:
  ```java
  Set<Empleado> desarrolladores = fileOperations.empleadosPorPuesto("Desarrollador");
  ```

#### **Filtrar Empleados por Edad**
- Filtra los empleados nacidos dentro de un rango de fechas con el método `empleadosPorEdad()`, proporcionando dos fechas en formato `dd/MM/yyyy`.
- Ejemplo:
  ```java
  Set<Empleado> empleadosPorEdad = fileOperations.empleadosPorEdad("01/01/1990", "31/12/2000");
  ```

## **Ejemplo de Salida**
Al ejecutar la aplicación con los empleados de ejemplo proporcionados, la salida en consola será algo similar a esto:

```txt
Empleado leído (01): 01, Juan Perez, Desarrollador, 2000.0, 06/09/2001    
Empleado leído (emp3): 03, Carlos Gomez, Desarrollador, 2200.0, 21/07/1988
Empleado actualizado (05): 05, Pedro Gomez, Desarollador, 2500.0, 02/05/1992
Intentando leer empleado eliminado (04): null
Empleados en el puesto de Desarrollador: [01, Juan Perez, Desarrollador, 2000.0, 06/09/2001, 03, Carlos Gomez, Desarrollador, 2200.0, 21/07/1988]
Empleados nacidos entre 01/01/1990 y 31/12/2000: [02, Maria Lopez, Diseniador, 1800.0, 15/03/1995, 05, Pedro Gomez, Desarollador, 2500.0, 02/05/1992]