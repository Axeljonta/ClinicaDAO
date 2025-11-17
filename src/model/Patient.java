package model;

import java.time.LocalDate;

public class Patient {
    private Integer id;
    private String name;
    private String lastName;
    private String cardIdentity;
    private LocalDate admissionOfDate;

    public Patient(String name, String lastName, String cardIdentity, LocalDate admissionOfDate) {
        this.name = name;
        this.lastName = lastName;
        this.cardIdentity = cardIdentity;
        this.admissionOfDate = admissionOfDate;
    }

    public Patient(Integer id, String name, String lastName, String cardIdentity, LocalDate admissionOfDate) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.cardIdentity = cardIdentity;
        this.admissionOfDate = admissionOfDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCardIdentity() {
        return cardIdentity;
    }

    public void setCardIdentity(String cardIdentity) {
        this.cardIdentity = cardIdentity;
    }

    public LocalDate getAdmissionOfDate() {
        return admissionOfDate;
    }

    public void setAdmissionOfDate(LocalDate admissionOfDate) {
        this.admissionOfDate = admissionOfDate;
    }

    @Override
    public String toString(){
        return
                "Paciente { id: "
                        + id + '\''
                        + " ,Nombre: " + name + '\''
                        + " ,apellido: " + lastName + '\''
                        + " ,DNI: " + cardIdentity + '\''
                        + " ,Dia de ingreso: " + admissionOfDate + '\''
                        + "}";
    };
}

