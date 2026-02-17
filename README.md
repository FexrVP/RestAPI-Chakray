# RestAPI-Chakray
##Descripción
API REST desarrollada con Spring Boot 4.0.2, Java 17 y Maven, que expone recursos para la gestión de usuarios y direcciones.
Los datos se almacenan en una base de datos Oracle.
El proyecto incluye:

1. Endpoints CRUD para usuarios.
2. Filtrado y ordenamiento mediante atributos.
3. Autenticación básica (comparación de contraseñas en texto plano).
4. Validaciones de taxId duplicado, formato de RFC taxId, longitud de taxId y phone.
5. Zona horaria Madagascar (Indian/Antananarivo) para la fecha de creación.
6. Dockerización de la aplicación.
7. Colección de Postman con pruebas exitosas y fallidas.
8. Control de versiones con Git.
  
##Configuración de la base de datos Oracle
	CREATE TABLE USUARIOS_ENCRIPTADOS(
    id_usuarios NVARCHAR2(50) PRIMARY KEY,
    email NVARCHAR2(100),
    name NVARCHAR2(100),
    phone NVARCHAR2(13),
    password NVARCHAR2(500),
    tax_id NVARCHAR2(13) CHECK(LENGTH(tax_id) BETWEEN 12 AND 13),
    created_at DATE
	);

	CREATE TABLE ADDRESSES(
    id_addresses NUMBER PRIMARY KEY,
    id_usuarios NVARCHAR2(50),
    name NVARCHAR2(50),
    street NVARCHAR2(100),
    country_code NVARCHAR2(10),
    CONSTRAINT fk_addresses_usuarios FOREIGN KEY (id_usuarios) 
        REFERENCES USUARIOS_ENCRIPTADOS(id_usuarios)
	);

	INSERT INTO USUARIOS_ENCRIPTADOS (id_usuarios, email, name, phone, password, tax_id, created_at)
	VALUES ('a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', 'user1@mail.com', 'user1', '+15555555555', '12345', 'AARR990101XXX', SYSDATE);

	INSERT INTO ADDRESSES (id_addresses, id_usuarios, name, street, country_code) VALUES (1, 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', 'workaddress', 'street No. 	1', 'UK');
	INSERT INTO ADDRESSES (id_addresses, id_usuarios, name, street, country_code) VALUES (2, 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', 'homeaddress', 'street No. 	2', 'AU');
	COMMIT;

##Ejecución de la aplicación

La aplicación utiliza las siguientes variables de entorno para la conexión a la base de datos(o configurar en application.properties y archivos docker):

- SPRING_DATASOURCE_URL (por defecto: jdbc:oracle:thin:@localhost:1521:XE)

- SPRING_DATASOURCE_USERNAME (por defecto: system)

- SPRING_DATASOURCE_PASSWORD (por defecto: 12345)

Modificarlas segun el entorno.  

##Ejecución desde GitHub
1. Clonar el repositorio con desde https://github.com/FexrVP/RestAPI-Chakray.git
2. Compilar y empeaquetar el API desde nuestro IDE o con ./mvnw clean package
3. Ejecutar la aplicación
4. Verificar que la API funciona desde http://localhost:9001/usuariosApi.

## Ejecución con Docker

1. Asegúrate de tener Docker y Docker Compose instalados.
2. Clona el repositorio y navega a la raíz del proyecto.
3. Ajusta las variables de entorno en "docker-compose.yml" según tu configuración de Oracle (usuario, contraseña, URL).
4. Asegurate de no correr ninguna otra aplicacion en el puerto escogido (9001), o configuralo en application.propierties y en docker-compose.yml
4. Ejecuta desde la consola en la carpeta raiz:
   docker-compose up --build
   
##Endpoints de la API

URL base: http://localhost:9001/usuariosApi

1. Login (autenticación)
Metodo POST 
/usuariosApi/login
Parámetros en x-www-form-urlencoded:
taxId: RFC del usuario
password: contraseña en texto plano
Respuesta: 200 OK si las credenciales coinciden, 401 Unauthorized en caso contrario.

2. Obtener todos los usuarios (con ordenamiento o filtros)
Metodo GET 
Parámetros opcionales:
-sortedBy: ordena por un campo (email, id, name, phone, tax_id, created_at).
Ejemplo: ?sortedBy=email
-filter: aplica un filtro con formato atributo+operador+valor.
Operadores: co (contiene), eq (igual), sw (empieza con), ew (termina con).
Atributos: email, id, name, phone, tax_id, created_at.
Ejemplo: ?filter=name+co+user	
Nota: El signo + debe codificarse como %2B en la URL.

3. Crear un nuevo usuario
Metodo POST
/usuariosApi
JSON:

	{
    "email": "name1@gmail.com",
    "name": "name1",
    "phone": "+521234567890",
    "password": "12345",
    "taxId": "CARR990101XXX",
    "addresses": [
        {
            "idAddres": 5,
            "name": "workaddress",
            "street": "street No. 1",
            "countryCode": "UK"
        },
        {
            "idAddres": 6,
            "name": "homeaddress",
            "street": "street No. 2",
            "countryCode": "AU"
        }
    ]
	}
Nota: El id y el createdAt se generan automaticamente

4. Actualizar el usuario
Metodo PATCH
/usuariosApi/{id}

JSON de ejemplo:

{
    "email": "nuevo@mail.com",
    "phone": "+525566778899"
}

5. Eliminar un usuario
Metodo DELETE
/usuariosApi/{id}

##Colección de Postman
Se incluye en el repositorio el archivo postman_collection.json con ejemplos de todos los endpoints. Para usarlo:

1. Abre Postman.
2. Importa el archivo (File > Import).
3. Ajusta la variable base_url si es necesario (por defecto http://localhost:9001).
4. Ejecuta las peticiones.

##Validaciones implementadas
- tax_id: obligatorio, longitud entre 12 y 13 caracteres, único.

- phone: longitud máxima 13 caracteres (se permite código de país).

- created_at: se genera automáticamente con la zona horaria de Madagascar (Indian/Antananarivo) y formato dd-MM-yyyy HH:mm.

##Tecnologías utilizadas
- Java 17

- Spring Boot 4.0.2

- Spring Data JPA (Hibernate)

- Oracle Database

- Maven

- Docker / Docker Compose

- Postman (colección de pruebas)

- Git (control de versiones)

Desarrollado por: Diego Fernando Vázquez Pérez

Fecha: 16 de febrero de 2026

Repositorio: https://github.com/FexrVP/RestAPI-Chakray