# Prueba Tecnica

Este es un sistema de gestión de usuarios desarrollado con Angular y Spring Boot, y de base de datos MySQL.

## Tecnologías utilizadas

Este proyecto fue desarrollado utilizando las siguientes tecnologías:

- **Frontend**:
  - [Angular](https://angular.io/) - Framework para la construcción del frontend.
  - [Bootstrap](https://getbootstrap.com/) - Framework CSS para diseño responsivo.
  - [Angular Material](https://material.angular.io/) - Librería para componentes de interfaz de usuario con Material Design.

- **Backend**:
  - [Spring Boot](https://spring.io/projects/spring-boot) - Framework Java para la creación de aplicaciones backend.
  - [Spring Data JPA](https://spring.io/projects/spring-data-jpa) - Para la gestión de bases de datos.
  - **[MySQL](https://www.mysql.com/)** - Sistema de gestión de bases de datos relacional utilizado para almacenar los datos de los usuarios.
  - [H2 Database](https://www.h2database.com/html/main.html) (opcional, si usas base de datos en memoria para pruebas).

- **Librerías adicionales**:
  - [Axios](https://axios-http.com/) - Cliente HTTP para Angular.
  - [Lodash](https://lodash.com/) - Librería de utilidades para simplificar el trabajo con JavaScript.

## Requisitos

Antes de comenzar, asegúrate de tener instalados los siguientes programas en tu máquina:

- [Node.js](https://nodejs.org/) (para el frontend).
- [Java](https://www.java.com/es/download/) y [Spring Boot](https://spring.io/projects/spring-boot) (para el backend).
- [Maven](https://maven.apache.org/) (para gestionar las dependencias en el backend).
- **[MySQL](https://www.mysql.com/)** o cualquier otra base de datos que estés utilizando. Debes crear una base de datos con nombre `prueba_db` (o el que uses).
- [MySQL Workbench](https://dev.mysql.com/downloads/workbench/) (opcional, pero recomendable para gestionar la base de datos).

## Instalación

Sigue estos pasos para configurar el proyecto en tu máquina local.

### Backend (Spring Boot):

1. Clona el repositorio del backend:
   ```bash
   git clone https://github.com/tu_usuario/tu_repositorio_backend.git
2. Entra en la carpeta del proyecto:

  bash
  Copiar
  Editar
  cd tu_repositorio_backend
3. Configura la conexión a MySQL:

Abre el archivo src/main/resources/application.properties (o application.yml si usas YAML) y configura los detalles de tu base de datos MySQL:
properties
Copiar
Editar
spring.datasource.url=jdbc:mysql://localhost:3306/prueba_db
spring.datasource.username=tu_usuario_mysql
spring.datasource.password=tu_contraseña_mysql
spring.jpa.hibernate.ddl-auto=update
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
Asegúrate de tener configurada la base de datos prueba_db en MySQL.

4. Instala las dependencias (si estás usando Maven):

bash
Copiar
Editar
mvn install

5. Ejecuta el backend:

Si estás usando Maven, ejecuta el siguiente comando para iniciar el servidor backend:
bash
Copiar
Editar
mvn spring-boot:run
Esto iniciará el backend en http://localhost:8080.

## Frontend (Angular):
1. Clona el repositorio del frontend:

bash
Copiar
Editar
git clone https://github.com/tu_usuario/tu_repositorio_frontend.git
2. Entra en la carpeta del proyecto:

bash
Copiar
Editar
cd tu_repositorio_frontend
3. Instala las dependencias de Node.js:

Si es la primera vez que trabajas con el proyecto, instala las dependencias con el siguiente comando:
bash
Copiar
Editar
npm install
4. Ejecuta el frontend:

Inicia el servidor frontend con el siguiente comando:
bash
Copiar
Editar
ng serve
Esto ejecutará el frontend en http://localhost:4200.

## Uso
1. Asegúrate de que el servidor backend y el frontend estén corriendo.
2. Accede a la aplicación a través de tu navegador en http://localhost:4200.
3. Desde la interfaz podrás registrar, editar y eliminar usuarios, y los cambios se reflejarán en la base de datos a través del backend.

  # ¡Gracias por utilizar este proyecto!

markdown
Copiar
Editar

### Cambios realizados:

- Se incluyó la mención a **MySQL** tanto en la sección de **Tecnologías utilizadas** como en la de **Requisitos**.
- En la sección de **Instalación** se agregó el paso para configurar la base de datos **MySQL** en el archivo `application.properties` (o `application.yml` si usas YAML en lugar de properties).

Recuerda que debes crear la base de datos `prueba_db` en tu instancia de MySQL para que el backend funcione correctamente. Si ya tienes MySQL instalado, puedes hacerlo con el siguiente comando en la consola de MySQL:

```sql
CREATE DATABASE prueba_db;
