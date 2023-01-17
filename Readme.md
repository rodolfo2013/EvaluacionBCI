## Instrucciones de ejecucion ##

1.- Bajar archivo hsqldb-2.7.1.jar desde repositorio maven (https://repo1.maven.org/maven2/org/hsqldb/hsqldb/2.7.1/hsqldb-2.7.1.jar).

2.- En el directorio donde se encuentre el archivo hsqldb-2.7.1.jar, ejecutar el comando "java -cp hsqldb-2.7.1.jar org.hsqldb.Server --database usuarios". Esto creara una base de datos llamada usuarios.

3.- En otra consola, pero en el mismo directorio donde se encuentra el archivo hsqldb-2.7.1.jar, ejecutar el comando "java -jar hsqldb-2.7.1.jar", Esto levantara una interfaz grafica para administrar la base de datos.

4.- En el campo URL, agregar el valor "jdbc:hsqldb:hsql://localhost"

5.- En la interfaz grafica levantada en el punto anterior, ejecutar el script que se encuentra en el respositorio. puedes bajarlo, haciendo click [Aqui](https://github.com/rodolfo2013/EvaluacionBCI/blob/main/Sql/script.sql)

6.- Con la base de datos ya funcionando, bajar el codigo del repositorio y abrirlo con un editor, en mi caso utilice IntelliJ-IDEA Community Edition.

7.- El codigo esta desarrollado con Java 16

8.- Al ejecutar el proyecto, este levanta en el puerto 8181.

9.- Se puede ver la documentacion con Swagger en el siguiente link "http://localhost:8181/swagger-ui/index.html#/"

10.- El metodo POST de la api, permite la grabacion de un usuario. El metodo GET de la api, permite listar todos los usuarios en la base de datos. 

11.- La configuracion puede ser cambiada en el archivo application.properties

12.- Ejemplo de un JSON correcto:

{
  "name": "Juan Rodriguez",
  "email": "juan@rodriguez.org",
  "password": "hunter2@",
  "phones": [
    {
      "number": "1234567",
      "citycode": "1",
      "contrycode": "57"
    }
  ]
}


13.- Ejemplo de un JSON erroneo:

{
  "name": "Juan Rodriguez",
  "email": "juan@rodriguez.orgxxxxx",
  "password": "hunter2@",
  "phones": [
    {
      "number": "1234567",
      "citycode": "1",
      "contrycode": "57"
    }
  ]
}
