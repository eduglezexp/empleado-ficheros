package es.ies.puerto.model.fichero;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import es.ies.puerto.model.Empleado;
import es.ies.puerto.model.Operations;

public class FileOperations implements Operations{
    private static final String FILE_NAME = "empleados.txt";

    @Override
    public boolean create(Empleado empleado) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            bw.write(empleado.toString());
            bw.newLine();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Empleado read(String identificador) {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(", ");
                if (data[0].equals(identificador)) {
                    return new Empleado(data[0], data[1], data[2], Double.parseDouble(data[3]), data[4]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Empleado read(Empleado empleado) {
        return read(empleado.getIdentificador());
    }

    @Override
    public boolean update(Empleado empleado) {
        List<Empleado> empleados = getAllEmpleados();
        boolean updated = false;
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Empleado e : empleados) {
                if (e.getIdentificador().equals(empleado.getIdentificador())) {
                    bw.write(empleado.toString());
                    updated = true;
                } else {
                    bw.write(e.toString());
                }
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return updated;
    }

    @Override
    public boolean delete(String identificador) {
        List<Empleado> empleados = getAllEmpleados();
        boolean deleted = empleados.removeIf(e -> e.getIdentificador().equals(identificador));
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Empleado e : empleados) {
                bw.write(e.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return deleted;
    }

    private List<Empleado> getAllEmpleados() {
        List<Empleado> empleados = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(", ");
                empleados.add(new Empleado(data[0], data[1], data[2], Double.parseDouble(data[3]), data[4]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return empleados;
    }

    @Override
    public Set<Empleado> empleadosPorPuesto(String puesto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'empleadosPorPuesto'");
    }

    @Override
    public Set<Empleado> empleadosPorEdad(String fechaInicio, String fechaFin) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'empleadosPorEdad'");
    }
}
