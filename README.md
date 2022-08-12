# challeng

El proyecto se creo con las siguientes características 
Spring boot 2.7.2
java 11
Maven


El proyecto a la hora de levantarse se crean las siguientes tablas
usuarios
  id 
  cantidad_prestamos,
  fecha_creacion,
  identificacion,
  nombre,
  volumen_prestamos

prestamos
   id,
   user_id,
   amount,
   date,
   rate,
   target,
   term
   
pagos
   id ,
   amount ,
   date ,
   debt ,
   loan_id
   
Se generan los siguientes Ususarios de manera prederminada
insert into usuarios (id, nombre, identificacion,cantidad_prestamos, volumen_prestamos,fecha_creacion) values
    (default, 'daniel', '1144076347', 1, 30000, current_date );
insert into usuarios (id, nombre, identificacion,cantidad_prestamos, volumen_prestamos,fecha_creacion) values
    (default, 'Angee', '159487', 4, 700000, current_date );
insert into usuarios (id, nombre, identificacion,cantidad_prestamos, volumen_prestamos,fecha_creacion) values
    (default, 'Jose', '84753456', 6, 1200000, current_date );
insert into usuarios (id, nombre, identificacion,cantidad_prestamos, volumen_prestamos,fecha_creacion) values
    (default, 'Ana', '78912354', 3, 60000, current_date );
insert into usuarios (id, nombre, identificacion,cantidad_prestamos, volumen_prestamos,fecha_creacion) values
    (default, 'Jimmy', '7914628', 9, 4000000, current_date );
    
En caso de querer modificar los usuarios insertados por defecto, se puede cambiar su informacion en el data.sql ubicado en src/main/resources/data.sql

una vez la aplicación este levantada estarán publicados los siguientes EndPoints.

Petición GET: /api/v1/Ususarios
No necesita parámetros de entrada y la respuesta es una lista de los uauarios que existen en la base de datos.

Petición POST: /api/v1/SolicitudPrestamo
Se requiere en el body el Json con los siguientes parámetros: amount, term, user_id
Ejemplo:
{
"amount": 200000,
"term": 12,
"user_id": 1
}
Dará como respuesta el id del prestamo que se creo y la mensualidad
Ejemplo:
{
    "loan_id": 2,
    "installment": 4680.693644824612
}

Petición GET: /api/v1/ListarPrestamos
necesita los siguientes Query Params 
From: fecha en formato YYYY-MM-DD. Ejemplo: 2022-06-01
to: fecha en formato YYYY-MM-DD. Ejemplo: 2022-06-01

Dará como respuesta un listado de los prestamos existentes dentro de las fechas


Petición POST: /api/v1/pago
Se requiere en el body el Json con los siguientes parametros: loan_id, term, amount
### Comentario: Los datos de entrada de esta petición tuvieron que ser cambiados, ya que sin el id del prestamo no se podía tener una buena respuesta ###
Ejemplo
{
    "loan_id": 4,
    "amount": 85.60
}
Dará como respuesta la información de pago realizado


Petición GET: /api/v1/balance
necesita los siguientes Query Params 
date: fecha en formato YYYY-MM-DD. Ejemplo: 2022-06-01

Petición GET: /api/v1/balanceByTarget
date: fecha en formato YYYY-MM-DD. Ejemplo: 2022-06-01
targe: Strig que puede ser (PREMIUM, MEDIUM, NEW)


Los dos EndPoints darán como respuesta un balance de los prestamos segun sus parámetros.
















