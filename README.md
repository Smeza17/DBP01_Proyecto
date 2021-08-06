# DBP_PROYECTO

# **Nombre del Proyecto:**
Aplicación Web y Movil para el registro de ancianos con Covid en el albergue Gerovitalis

**Integrantes:**
- Francisco Magot
- Pedro Mori
- Sebastian Meza
- Luis David Torres
- Elizabeth Huamán

### **Descripción del Proyecto:**
La pagina web y movil sirven para un geriatrioco en el cual se ecuentren pacientes covid. En la pagina es requerido tener un usuario para poder acceder a las funcionalidades de ver y registrar paciente.

**Misión:**
Queremos ser una pagina web y appmovil, utilizada por los geriatricos para poder tener un mejor control de los pacientes que se encuentran en la residencia.

**Visión:**
Ser la pagina web y app movil, utilizada por diferentes geriatricos y clinicas para poder tener un registro de los pacientes, por parte de los doctores, pero tambien poder estar al tanto de los pacientes por parte de los familiares.

Visión:

- Front-End: Bootstrap, JavaScript
- Framework: Bootstrap 4
- Back-End: 
    - Librerias:
        - Flask-Login
        - Flask-SQLAlchemy
        - Flask-Migrate
        - Flask
        - OkHttp
    

**Base de Datos:**
PostgreSQL

Script para iniciar la base de datos con datos: datos.py

**API:**
Hicimos uso de API para pode conectar la pagina web con la aplicion movil

## **Forma de Auntenticación:**
El usuario necesita crear una cuenta para hacer uso de la funcionalidades. 
Usamos Flask-Login, para manejar las tareas de iniciar session, cerrar sesion y recordar las sesiones de los usuarios durante un periodo de tiempo.

Manejo de Errores HTTP:
- 500
- 400
- 300
- 200
- 100

**Deployment Scripts:** 
- Ejecutar app.py para la creación de tablas en la BD con Flask Migrate. Posteriormente, datos.py para la inserción de datos. Finalmente, ejecutar app.py nuevamente para tener en funcionamiento el servidor web. 

- Para ejecutar el app cambiar el ip al suyo, primero correr la pagina web la,el archivo app.py y datos.py. Una vez funcionando la pagina web corra la aplicacion
