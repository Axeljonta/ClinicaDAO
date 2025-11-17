import dao.BD;
import dao.impl.DentistDaoH2;
import dao.impl.PatientDaoH2;
import model.Dentist;
import model.Patient;
import service.DentistService;
import service.PatientService;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {

        DentistService dentistService = new DentistService(new DentistDaoH2());
        PatientService patientService = new PatientService(new PatientDaoH2());

        //creamos las tablas
        BD.createTables();

        //crear algunos objetos
        Dentist dentist1 = new Dentist(132, "Vanina", "Godoy");
        Dentist dentist2 = new Dentist(456, "Juan", "Perez");
        Dentist dentist3 = new Dentist(789, "Carlos", "Suarez");

        Patient patient1 = new Patient("Sergio", "Perez", "24.551.323", LocalDate.of(2022,5,11));
        Patient patient2 = new Patient("Romina", "Malaespina", "35.354.897", LocalDate.of(2021,7,3));
        Patient patient3 = new Patient("Glotilde", "Grant", "40.961.129", LocalDate.of(2015,6,4));
        //persistir los objetos en la BD (guardarlos)
        dentistService.save(dentist1);
        dentistService.save(dentist2);
        dentistService.save(dentist3);

        patientService.save(patient1);
        patientService.save(patient2);
        patientService.save(patient3);

        //consultar por id
        int id = 2;
        dentistService.findById(id);
        patientService.findById(id);

        //actualizar alguno de los atributos
        String updateName = "Andrea";
        dentist1.setName(updateName);
        patient3.setName(updateName);

        dentistService.update(dentist1);
        patientService.update(patient3);
        System.out.println("El nombre actualizado es: " + dentist1.getName());

        //borrar alguno de los registros de la tabla
        int idDelete = 2;
        dentistService.delete(idDelete);

        //consultar los registros restantes de la tabla
        dentistService.findAll();
        patientService.findAll();

    }
}
