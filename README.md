# 🦷 Clínica Odontológica – Proyecto Java (DAO + H2 + Log4j)

Este proyecto es una aplicación completa de gestión para una clínica odontológica desarrollada en **Java**, aplicando buenas prácticas de arquitectura como el **Patrón DAO**, uso de **Base de Datos H2**, y **logging con Log4j**.

Permite **registrar, buscar y administrar** información de **Pacientes** y **Odontólogos** de manera ordenada, modular y escalable.

---

## 🚀 Tecnologías utilizadas

* **Java 17 o superior**
* **Patrón DAO (Data Access Object)**
* **Base de datos H2** (modo archivo o memoria)
* **Log4j2** para logging
* **JDBC** para la comunicación con la base de datos

---

## 🏗️ Arquitectura

El sistema está dividido en capas:

* **Model** → Clases `Patient` y `Dentist`
* **DAO** → Interfaces y clases concretas para acceso a la BD  
  Ejemplo: `PatientDaoH2`, `DentistDaoH2`
* **Service** → Lógica de negocio que utiliza los DAOs
* **Database** → Configuración de H2 y creación automática de tablas
* **Log** → Configuración de Log4j para registrar cada operación

Esta arquitectura permite mantener el código **modular**, **limpio** y **fácil de mantener**, pudiendo reemplazar componentes (por ejemplo H2 → MySQL) sin modificar la lógica.

---

## ✨ Funcionalidades

✔ Registrar pacientes  
✔ Registrar dentistas  
✔ Buscar por ID  
✔ Listar registros  
✔ Logs detallados con Log4j  
✔ Persistencia en H2 Database  

---
