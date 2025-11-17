package dao.impl;

import dao.BD;
import dao.IDao;
import model.Patient;
import model.Patient;
import org.apache.logging.log4j.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PatientDaoH2 implements IDao<Patient> {

    private static final Logger LOGGER = LogManager.getLogger(PatientDaoH2.class);

    private static final String SQL_INSERT = "INSERT INTO PATIENT (NAME, LASTNAME, CARDIDENTITY, ADMISSIONOFDATE)" +
            "VALUES(?,?,?,?)";

    private static final String SQL_FIND = "SELECT * FROM PATIENT WHERE ID = ?";

    private static final String SQL_UPDATE = "UPDATE PATIENT SET NAME=?, LASTNAME=? , CARDIDENTITY= ?, ADMISSIONOFDATE=? " +
            "WHERE ID=?";

    private static final String SQL_DELETE = "DELETE FROM PATIENT WHERE ID=?";

    private static final String SQL_SELECT_ALL = "SELECT * FROM PATIENT";





    @Override
    public Patient save(Patient patient) {
        //nos aseguramos que la conexion sea nula
        Connection  connection = null;
        try{
            LOGGER.info("Ingresando nuevo paciente a la BD");

            //Conectar con la BD
            connection = BD.getConnection();
            //para que no se autocommitee
            connection.setAutoCommit(false);
            //insertando valores pasamos metodo Statement.RETURN_GENERATED_KEYS para que retenga id generado
            PreparedStatement psInsert = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
            psInsert.setString(1,patient.getName());
            psInsert.setString(2,patient.getLastName());
            psInsert.setString(3, patient.getCardIdentity());
            psInsert.setDate(4, java.sql.Date.valueOf(patient.getAdmissionOfDate())); //convertimos el tipo de dato a Date sino nos daria un error
            //guardamos el valor
            psInsert.executeUpdate();

            connection.setAutoCommit(true);


            //recuperamos el id generado
            ResultSet rs = psInsert.getGeneratedKeys();
            while (rs.next()){
                patient.setId(rs.getInt(1));
                LOGGER.info("Se guardo correctamente pasiente" +
                        "Nombre completo: " + patient.getName() + " " + patient.getLastName() +
                        "DNI: " + patient.getCardIdentity());
            }

        }
        catch (Exception e){
            LOGGER.error("Error: " + e.getMessage());
            e.printStackTrace();
        }
        finally {
            try {
                connection.close();
            }
            catch (Exception e) {
                e.printStackTrace();
            }

        }
        return patient;
    }

    //Implementacion metodo findById de la interfaz Idao-------------------------------------------------------------

    @Override
    public Patient findById(Integer id) {
        Connection connection = null;
        LOGGER.info("Iniciando busqqueda de paciente");
        Patient patient = null;

        try {
            connection = BD.getConnection();
            PreparedStatement psFind = connection.prepareStatement(SQL_FIND);
            psFind.setInt(1, id);

            ResultSet rs = psFind.executeQuery();
            while (rs.next()) {
                patient = new Patient(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getDate(5).toLocalDate());
                LOGGER.info("Consultamos el paciente con id: " + patient.getId() +
                        " Nombre: " + patient.getName() +
                        " Apellido: " + patient.getLastName());
            }


        } catch (Exception e) {
            LOGGER.error("Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (Exception e) {
                LOGGER.error("Error: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return patient;
    }

    @Override
    public void update(Patient patient) {
        LOGGER.info("Iniciando actualizacion de un paciente");
        Connection connection = null;

        try {
            connection = BD.getConnection();
            PreparedStatement psUpdate = connection.prepareStatement(SQL_UPDATE);

            psUpdate.setString(1, patient.getName());
            psUpdate.setString(2,patient.getLastName());
            psUpdate.setString(3, patient.getCardIdentity());
            psUpdate.setDate(4, java.sql.Date.valueOf(patient.getAdmissionOfDate()));
            psUpdate.setInt(5,patient.getId());

            psUpdate.executeUpdate();
            LOGGER.info("El atributo actualizado: ");
        }
        catch (Exception e) {
            LOGGER.error("Error: " + e.getMessage());
            e.printStackTrace();
        }
        finally {
            try {
                connection.close();
            }
            catch (Exception e) {
                LOGGER.error("Error: " + e.getMessage());
                e.printStackTrace();
            }
        }

    }

    @Override
    public void delete(Integer id) {
        Connection connection = null;

        try {
            connection = BD.getConnection();
            PreparedStatement psDelete = connection.prepareStatement(SQL_DELETE);
            psDelete.setInt(1, id);
            psDelete.execute();
            LOGGER.warn("Cuidado se eliminó el paciente con id: " + id);
        }
        catch (Exception e) {
            LOGGER.error("Error: " + e.getMessage());
            e.printStackTrace();
        }
        finally {
            try {
                connection.close();
            }
            catch (Exception e) {
                LOGGER.error("Error: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<Patient> findAll() {
        Connection connection = null;
        LOGGER.info("Iniciando la búsqueda de todos los pacientes");
        List<Patient> patientList = new ArrayList<>();
        Patient patient = null;

        try {
            connection = BD.getConnection();
            PreparedStatement psSelectAll = connection.prepareStatement(SQL_SELECT_ALL);

            //guardarmos en un ResultSet la consulta a la BD
            ResultSet rs = psSelectAll.executeQuery();

            while (rs.next()) {
                //guardar esa consulta proveniente del rs en un objeto en java
                patient = new Patient(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getDate(5).toLocalDate());

                //guardar los odontologos en la lista
                patientList.add(patient);
                LOGGER.info("Encontramos los pacientes con id: " + patient.getId() +
                        " ,DNI: " + patient.getCardIdentity()+
                        " ,nombre: " + patient.getName() +
                        " ,apellido: " + patient.getLastName());
            }
            System.out.println(patientList);
        }
        catch (Exception e) {
            LOGGER.error("Error: " + e.getMessage());
            e.printStackTrace();
        }
        finally {
            try {
                connection.close();
            }
            catch (Exception e) {
                LOGGER.error("Error: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return patientList;
    }
}
