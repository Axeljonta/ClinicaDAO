package test;

import dao.BD;
import dao.impl.DentistDaoH2;
import model.Dentist;
import org.junit.jupiter.api.Test;
import service.DentistService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class DentistServiceTest {

    DentistService dentistService = new DentistService(new DentistDaoH2());

    @Test
    void save() {
        BD.createTables();
        Dentist dentist = new Dentist();
        dentist.setRegistration(555);
        dentist.setName("Carolina");
        dentist.setLastName("Garro");

        dentistService.save(dentist);

        assertNotNull(dentist.getId());
    }

    @Test
    void findById() {
        Dentist dentist = dentistService.findById(1);
        assertNotNull(dentist);
    }

    @Test
    void update() {
        Dentist dentist = new Dentist();
        dentist.setId(1);
        dentist.setName("Juan Andres");
        dentist.setLastName("Perez");
        dentist.setRegistration(12345);
        dentistService.update(dentist);

        assertNotNull(dentist.getId());
        assertEquals(true, dentist.getName().equals("Juan Andres"));
    }

    @Test
    void delete() {
        Dentist dentistDeleted = dentistService.findById(2);

        assertNull(dentistDeleted);
    }

    @Test
    void findAll() {
        List<Dentist> dentistList = dentistService.findAll();

        assertTrue(dentistList.size() > 0);
    }
}