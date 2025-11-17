package dao.impl;

import dao.BD;
import dao.IDao;
import model.Dentist;
import org.apache.logging.log4j.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DentistDaoH2 implements IDao<Dentist> {

    private static final Logger LOGGER = LogManager.getLogger(DentistDaoH2.class);

    private static final String SQL_INSERT = "INSERT INTO DENTIST (REGISTRATION, NAME, LASTNAME)" +
            " VALUES(?,?,?)";

    private static final String SQL_SELECT = "SELECT * FROM DENTIST WHERE ID = ?";

    private static final String SQL_UPDATE = "UPDATE DENTIST SET REGISTRATION=?, NAME=?, LASTNAME=? " +
            "WHERE ID=?";

    private static final String SQL_DELETE = "DELETE FROM DENTIST WHERE ID=?";

    private static final String SQL_SELECT_ALL = "SELECT * FROM DENTIST";




    //Implementacion metodo save de la interfaz IDao-------------------------------------------------------------
    @Override
    public Dentist save(Dentist dentist) {
        Connection connection = null;
        try {
            LOGGER.info("Se inició una operación de guardado de odontólogo");

            //Conectar a la BD
            connection = BD.getConnection();

            //insertar valores -> guardarlo
            PreparedStatement psInsert = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);//metodo para que retenga el id generado
            psInsert.setInt(1, dentist.getRegistration());
            psInsert.setString(2, dentist.getName());
            psInsert.setString(3, dentist.getLastName());
            psInsert.execute();

            ResultSet rs = psInsert.getGeneratedKeys();//metodo recupera clave generada
            while (rs.next()) {
                dentist.setId(rs.getInt(1));
                LOGGER.info("Este es el odontólogo que se guardó: " +
                        dentist.getName());
            }


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
                e.printStackTrace();
            }
        }
        return dentist;
    }

    //Implementacion metodo findById de la interfaz Idao-------------------------------------------------------------
    @Override
    public Dentist findById(Integer id) {
        Connection connection = null;
        LOGGER.info("Iniciando la búsqueda de un odontólogo");
        Dentist dentist = null;

        try {
            connection = BD.getConnection();
            PreparedStatement psSelect = connection.prepareStatement(SQL_SELECT);
            psSelect.setInt(1, id);

            ResultSet rs = psSelect.executeQuery();
            while (rs.next()) {
                dentist = new Dentist(rs.getInt(1),
                        rs.getInt(2),
                        rs.getString(3),
                        rs.getString(4));
                LOGGER.info("Consultamos el odontólogo con id: " + dentist.getId() +
                        " es: " + " con nombre: " + dentist.getName() +
                        ", con apellido: " + dentist.getLastName());
            }


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
        return dentist;
    }

    //Implementacion metodo update de la interfaz Idao-------------------------------------------------------------
    @Override
    public void update(Dentist dentist) {
        LOGGER.info("Iniciando la actualización de un odontólogo");
        Connection connection = null;

        try {
            connection = BD.getConnection();
            PreparedStatement psUpdate = connection.prepareStatement(SQL_UPDATE);

            psUpdate.setInt(1, dentist.getRegistration());
            psUpdate.setString(2, dentist.getName());
            psUpdate.setString(3, dentist.getLastName());
            psUpdate.setInt(4, dentist.getId());
            psUpdate.execute();

            LOGGER.info("El atributo actualizado: " + dentist.getName());


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

    //Implementacion metodo delete de la interfaz Idao-------------------------------------------------------------
    @Override
    public void delete(Integer id) {
        Connection connection = null;

        try {
            connection = BD.getConnection();
            PreparedStatement psDelete = connection.prepareStatement(SQL_DELETE);
            psDelete.setInt(1, id);
            psDelete.execute();
            LOGGER.warn("Cuidado se eliminó el odontólogo con id: " + id);
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

    //Implementacion metodo findAll de la interfaz Idao-------------------------------------------------------------
    @Override
    public List<Dentist> findAll() {
        Connection connection = null;
        LOGGER.info("Iniciando la búsqueda de todos los odontólogos");
        List<Dentist> dentistList = new ArrayList<>();
        Dentist dentist = null;

        try {
            connection = BD.getConnection();
            PreparedStatement psSelectAll = connection.prepareStatement(SQL_SELECT_ALL);

            //guardarmos en un ResultSet la consulta a la BD
            ResultSet rs = psSelectAll.executeQuery();

            while (rs.next()) {
                //guardar esa consulta proveniente del rs en un objeto en java
                dentist = new Dentist(rs.getInt(1), rs.getInt(2),
                        rs.getString(3), rs.getString(4));

                //guardar los odontologos en la lista
                dentistList.add(dentist);
                LOGGER.info("Encontramos los odontólogos con id: " + dentist.getId() + " ,matrícula: " +
                        dentist.getRegistration() + " ,nombre: " + dentist.getName() +
                        " ,apellido: " + dentist.getLastName());
            }
            System.out.println(dentistList);


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
         return dentistList;
    }
}
