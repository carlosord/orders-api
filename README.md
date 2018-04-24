# Orders API
Tecnologías: Spring-boot + JPA (Hibernate) + Mysql + REST + OAuth with JWT + docker config

## Abstract
Aplicación basada en un sencillo sistema que consta de artículos que pertenecen a un catalogo y de pedidos de dichos articulos.
La aplicación esta construida con SpringBoot, versión 1.5.10. Se hace uso de JPA con Hibernate para conectarse a las distintas
bases de datos (mysql para produción y h2 en memoria para testing). En si es una API REST con métodos para acceder al detalle
de un articulo y un CRUD para los pedidos.
Incluye autenticación OAuth integrada con Spring Security utilizando JWT Token de manera básica.
Por último, incluye la configuración apropiada para poder ser desplegada en un contenedor Docker. Para ello incluye el DokerFile
y la dependencia necesaria.

## Pasos para la realización de la aplicación
### Creación del proyecto
Para crear el proyecto inicial hacemos uso de [Spring Initializr](http://start.spring.io/). Seleccionamos las dependencias de JPA, Web y Mysql en un primer momento. Descargamos el proyecto resultante y lo importamos como maven project en Eclipse, que sera el IDE escogido para realizar el ejercicio.

### Implementación del modelo
Para la implementación del modelo se emplea una herramienta de generación de código llamada DIESELS que se puede encontrar [aquí](https://github.com/carlosord/https://github.com/carlosord/DieselsPlus). Esta genera todas las clases de modelo en base a una especificación dada, con las anotaciones de JPA pertinentes. Para el mantenimiento de las relacciones utilizamos la clase Associantions con subclases estáticas que contienen métodos para enlazar y desenlazar entidades.

Para las distintas entidades tendremos un atributo id que será la clave primaria en la BD. De igual forma tendremos un atributo code que servirá de clave natural de la identidad.

En un primer momento utilizamos una Mysql desplegada en local, por lo que añadimos las siguientes lineas al application.properties:
```sh
spring.datasource.platform = mysql
spring.datasource.username = root #Your user
spring.datasource.password = secret #Your password
spring.datasource.url = jdbc:mysql://localhost:8080/orders?useSSL=false #Your db name
```

### Implementación de los repositorios
Para el acceso a persistencia usaremos repositorios de JPA. Basta con crear una interface y extender de JpaRepository. De esta manera tendremos los métodos basicos de accceso a la base de datos. Si quisieramos realizar otro tipo de consultas más complejas, basta con añadir la signatura del método indicando el campo de busqueda.Ej: buscar un artículo por codigo (atributo code) -> añadir en la interface el siguiente método:
```sh
public Articulo findByCode(String code);
```
Recordar que debemos añadir la anotación @Repository para que SpringBoot lo reconozca como una clase repositorio y este en el contexto.

### Implementación de los servicios
Para realizar los servicios usaremos una interface donde definiremos los métodos y su implementación, que la capa superior no tiene por que conocer. Debemos recordar que la clase implementada debe llevar la anotación @Service. Con ella decimos que esta es una clase de configuración a tener en cuenta, igual que pasaba con los repositorios.
Como los métodos necesitarán realizar llamadas al repositorio, es necesario una instancia del mismo en la clase. Para ello usaremos inyección. Gracias a haber anotado previamente el repositorio con @Repository, spring sabrá que debe inyectar. Para conseguir que funcione pondremos la anotación @Aurowired sobre el atributo del repositorio en la clase.
Los métodos a implementar son un CRUD (create, read, update & delete) en el servicio de pedidos y un read (find) en el servicio
de articulos.
A su vez, lo métodos de este servicio son transacionales, para ello usamos la anotacion @Transactional en los métodos (menos en el GET). Con esta anotación se intenta ejecutar el código del método, si surgiese algún error o excepción se ejecutaría un rollback. Podemos probar que funciona si forzamos a incluir una excepcion despues de un save y veremos que la bd no sufre ninguna alteración.

### Controladores Rest
Los controladores Rest serán nuestro punto de acceso desde el exterior. Es necesario anotarlos con @RestController. A partir de aquí cada método anotado tendrá su propia URL, parametros, etc. La siguiente tabla muestra de que manera se anotan los métodos en función de que queremos implementar (usaremos el controlador de pedidos como ejemplo):

| HTTP Method | CRUD Method | Anotation | URL |
| ------ | ------ | ------ | ------ |
| POST | Create | @PostMapping | /order/ |
| GET | Read | @GetMapping | /order/{id} |
| PUT | Update | @PutMapping | /order/{id} |
| DELETE | Delete | @DeleteMapping | /order/{id} |

Además, estos métodos contienen parametros, ya sean por la URL o por json. Aquellos que vengan con la URL llevarán la anotacion
@PathParam. A las entidades que pasemos en formato json se les puede añadir la anotacion @Valid, que comprobara anotaciones dentro de las entidades que verifiquen los atributos como @NotNull o @Size.

En caso de que los métodos se ejecuten de forma correcta devolveremos un código 200 con la entidad creada, modificada... En caso 
contrario se devolverá un códido de error, se capturará y se informará al usuario.

### Autogeneración de la base de datos y datos inciales
Para generar la base de datos debemos añadir la siguiente línea al application.properties:
```sh
spring.jpa.hibernate.ddl-auto = create
```
Arrancamos la aplicación. Con esto estammos autogenerando las tablas y sus columnas en la base de datos. Una vez finalizado el arranque podemos detenerlo y comprobar que, efectivamente, la base de datos contiene las tablas que definimos con las anotaciones en las entidades. No olvidemos que esto sirve para cualquier otra base de datos como Hsqldb, oracle...

Modificamos nuevamente el valor de la propiedad en el fichero application.properties:
```sh
spring.jpa.hibernate.ddl-auto = none
```
Tambien se podría poner en "update" para que se actualizara ante cualquier cambio en el modelo.

A continuación vamos a crear unos datos iniciales. Para ello conectaremos con un cliente a la base de datos y cargaremos el
siguiente script de datos inciales (Este proceso podría ser automático, más adelante, en la parte de testing se verá un ejemplo):

```sh
INSERT INTO `t_catalogues`(`code`, `name`) VALUES ('CAT001','Xiaomi Smartphones');
INSERT INTO `t_catalogues`(`code`, `name`) VALUES ('CAT002','BQ Smartphones');
INSERT INTO `t_catalogues`(`code`, `name`) VALUES ('CAT002','Huawei Smartphones');

INSERT INTO `t_articles`(`code`, `description`, `name`, `price`, `catalogue_id`) VALUES ('ART002','Xiaomi Redmi 5 2/16Gb Negro Libre','Xiaomi Redmi 5',129,1);
INSERT INTO `t_articles`(`code`, `description`, `name`, `price`, `catalogue_id`) VALUES ('ART003','Xiaomi Redmi Note 4 3GB/32GB 4G Gris Libre','Xiaomi Redmi Note 4',169,1);
INSERT INTO `t_articles`(`code`, `description`, `name`, `price`, `catalogue_id`) VALUES ('ART004','Xiaomi Redmi 5A 4G 16GB Dual Sim Gris Libre','Xiaomi Redmi 5A',109,1);

INSERT INTO `t_articles`(`code`, `description`, `name`, `price`, `catalogue_id`) VALUES ('ART005','BQ Aquaris VS 4/64GB Dorado Libre','BQ Aquaris VS',199,2);
INSERT INTO `t_articles`(`code`, `description`, `name`, `price`, `catalogue_id`) VALUES ('ART006','Bq Aquaris U Plus 4G 2GB/16GB Negro Libre','Bq Aquaris U Plus',134,2);
INSERT INTO `t_articles`(`code`, `description`, `name`, `price`, `catalogue_id`) VALUES ('ART007','Bq Aquaris X Pro 3GB/32GB Negro Libre Dual Sim','Bq Aquaris X Pro',251.90,2);
INSERT INTO `t_articles`(`code`, `description`, `name`, `price`, `catalogue_id`) VALUES ('ART008','Bq Aquaris U2 Lite 2GB/16GB Blanco','Bq Aquaris U2 Lite',129,2);

INSERT INTO `t_articles`(`code`, `description`, `name`, `price`, `catalogue_id`) VALUES ('ART009','Huawei P8 Lite 2017 Negro Libre','Huawei P8 Lite',159.01,3);
INSERT INTO `t_articles`(`code`, `description`, `name`, `price`, `catalogue_id`) VALUES ('ART010','Huawei P10 Lite 4GB/32GB Blanco Libre','Huawei P10 Lite',259,3);
INSERT INTO `t_articles`(`code`, `description`, `name`, `price`, `catalogue_id`) VALUES ('ART011','Huawei Y6 II Blanco','Huawei Y6',139,3);
INSERT INTO `t_articles`(`code`, `description`, `name`, `price`, `catalogue_id`) VALUES ('ART012','Huawei Nexus 6P 4G Gris Libre','Huawei Nexus 6P',430.33,3);

INSERT INTO `t_orders`(`code`) VALUES ('ORD001');
INSERT INTO `t_orders`(`code`) VALUES ('ORD002');

INSERT INTO `t_orders_articles`(`order_id`, `articles_id`) VALUES (1,1);
INSERT INTO `t_orders_articles`(`order_id`, `articles_id`) VALUES (1,5);
INSERT INTO `t_orders_articles`(`order_id`, `articles_id`) VALUES (1,9);

INSERT INTO `t_orders_articles`(`order_id`, `articles_id`) VALUES (2,2);
INSERT INTO `t_orders_articles`(`order_id`, `articles_id`) VALUES (2,6);
INSERT INTO `t_orders_articles`(`order_id`, `articles_id`) VALUES (2,10);
```

### Primeras pruebas
Ya estamos en disposición de hacer las primeras pruebas. Si todo ha salido según lo esperado deberiamos tener la aplicación en ejecución, con conexión a la base de datos y las URL de acceso disponibles. En nuestro caso vamos a añadir un path de acesso previo a los servicios web. Añadimos la siguiente línea en el fichero application.properties:
```sh
server.contextPath = /api # En SpringBoot 1.5.x, la versión 2.0.x modifica el nombre de esta propiedad
```
Si quisieramos modificar el puerto, que por defecto es el 8080, añadiriamos la siguiente:
```sh
server.port = 8090
```
Ejecutamos la primera sentencia, por ejemplo para obtener el articulo con identificador 5. Podemos usar el navegador o una herramienta. En este caso haremos uso de Postman:
- Indicamos la url: http://localhost:8080/api/article/5
- Indicamos que es un GET
- Clicamos "Send"
Obtenemos la siguiente respuesta:
```sh
{
    "id": 5,
    "code": "ART006",
    "name": "Bq Aquaris U Plus",
    "description": "Bq Aquaris U Plus 4G 2GB/16GB Negro Libre",
    "price": 134,
    "catalogue": {
        "id": 2,
        "code": "CAT002",
        "name": "BQ Smartphones"
    }
}
```
A partir de aquí podemos probar todas las URL para ver como funciona el servicio web implementado. Hay que recordar que debemos 
pasar datos válidos, de lo contrario el servicio dará un error (Omitido para el usuario, dado que los capturadores están vacios
en este ejemplo a falta de implementación, aunque deberían usar ApiError y un código de respuesta de error con información para 
el usuario que indique que sucedio).

### Test unitarios: Probando los servicios
Para realizar los test, escribiremos metodos en la clase ApplicationTest con la anotacion @Test. Como queremos probar los servicios contra una base de datos, debemos crear algo aislado de la base de datos de producción. Es por esto que haremos uso de H2, una base que se mantendrá en memoria.

Incluimos su depencencia en el pom.xml y agregamos la anotación @DataJpaTest y @RunWith(SpringRunner.class) a la clase de test. Con ello indicamos que se hara uso del modelo para mapear las tablas. Es necesario incluir una serie de configuraciones en un fichero application.properties que crearemos en src/test/resources:
```sh
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.url=jdbc:h2:mem:db;DB_CLOSE_DELAY=-1
spring.datasource.username=sa
spring.datasource.password=sa
```
A partir de aquí, tendremos la base de datos operativa.

Para ejecutar test, querremos un dataset inicial. Como indicabamos anteriormente, esto puede cargarse de forma automatica al iniciar los test. En el directorio src/test/resources, incluimos un fichero data.sql con los insert que se mostraron en apartados anteriores. Esto es suficiente para tener un dataset inicial.
Es necesario tener cuidado, pues en ocasiones se duplica este fichero, apareciendo en la carpeta target. Se debe eliminar si no queremos que de un error en tiempo de ejecución al lanzarse dos veces el fichero.

Ahora crearemos diversos test para los métodos de los servicios. Se tratan de test muy sencillos con casos de prueba positivos en su mayoría, dado que el esfuerzo de la práctica no se ha centrado aquí.

Faltaría por testear los servicios REST. Aunque se probee de un sistema de mockear servicios, se propone usar una libreria llamada Mockito para la misma función. No se ha realizado está parte, pero queda pendiente para su investigación.

### Incluyendo seguridad
El primer paso para incluir seguridad es hacer uso de Spring-security. Para ello añadimos su dependencia y creamos una clase que
anotaremos como @Configuration, para que sea detectada. Y le añadiremos las anotaciones @WebSecurity y @EnableGlobalMethodSecurity.

Podría validarse contra un servicio de usuarios, pero en este caso, los usuarios que tendrán acceso a la base estarán guardados en memoria. Añadiremos dos, uno con rol USER y otro con rol ADMIN.

Será necesario también incluir los metodos de configuración para indicar que rutas tienen permiso al público y en cuales hay que entrar bajo un cierto rol.

Si probamos ahora a hacer un get de los articulos, veremos que nos pide autenticación. En caso de no darsela nos devuelve un error por no estar autorizados.

### Integrando OAuth con JWT Token
Este paso es más importante, debemos integrar oauth con spring security utilizando el token JWT. Para ello aparecerán dos nuevas
clases. En la primera definiremos los niveles de autorización y como se configura el token. La segunda clase definirá si el token es válido para acceder a los resources. Debemos incluir las anotaciones @EnableAuthorizationServer y @EnableResourceServer en estas clases respectivamente.

Modificaremos de igual manera la clase de configuración de Spring Security para eliminar roles para acceder a los servicios y limitarlo a que haya que estar autenticado para hacer cualquier petición.

Incluiremos tambien un método que permitirá ignorar ciertas URL, esto nos vendrá bien de cara a las URL de swagger, que comentaremos más adelante.

Para poder realizar peticiones ahora, debemos pedir un token, y después realizar la petición con dicho token. Existe un POST al que se pueden lanzar una petición que devolvera el token. La ruta es http://localhost:8080/api/oauth/token. Si hacemos esta consulta sin más, nos dará un error de credenciales. Es necesario que lo hagamos con autenticación básica, usando como usuario y contraseña los indicados en la clase de Autorización, y que pasemos por parametro un usuario de Spring-security válido, además del grant_type.
Una vez tengamos el token, podemos realizar peticiones como hicimos en pasos anteriores indicando seguridad de tipo OAuth2 con el token obtenido en la consulta anterior.
Para que todo funcione correctamente, en esta versión de spring-boot, debemos añadir la siguiente línea al fichero application.properties:
```sh
security.oauth2.resource.filter-order = 3
```

### Peticiones de prueba con la seguridad implementada
Ejemplo de peticion POST con curl a /api/pedido:
Realizamos la peticion con Postman por comodidad para incluir los parámetros. Primero pedimos el token:
```sh
curl -X POST \
  http://localhost:8080/api/oauth/token \
  -H 'Authorization: Basic Y2xpZW50OnNlY3JldA==' \
  -H 'Cache-Control: no-cache' \
  -H 'Content-Type: application/x-www-form-urlencoded' \
  -H 'Postman-Token: 7627efa4-361f-4ae8-a0fa-a054009bef7b' \
  -d 'username=user&password=pass1234&grant_type=password'
```
Nos devuelve la siguiente respuesta:
```sh
{
    "access_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MjQ1NjY4NzksInVzZXJfbmFtZSI6InVzZXIiLCJhdXRob3JpdGllcyI6WyJST0xFX1VTRVIiXSwianRpIjoiYmZlMjUzNzktMDQzMS00Y2U4LTkwYWYtZDgzODdmMjQxN2YzIiwiY2xpZW50X2lkIjoiY2xpZW50Iiwic2NvcGUiOlsicmVhZCIsIndyaXRlIl19.QRKGuR9TJAAbntIwn-qGodv2cb5NAKk-VpJ3kKQd484",
    "token_type": "bearer",
    "expires_in": 3599,
    "scope": "read write",
    "jti": "bfe25379-0431-4ce8-90af-d8387f2417f3"
}
```
Una vez obtenido el token podemos realizar el POST.
```sh
curl -X POST \
  http://localhost:8080/api/order \
  -H 'Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MjQ1NjY4NzksInVzZXJfbmFtZSI6InVzZXIiLCJhdXRob3JpdGllcyI6WyJST0xFX1VTRVIiXSwianRpIjoiYmZlMjUzNzktMDQzMS00Y2U4LTkwYWYtZDgzODdmMjQxN2YzIiwiY2xpZW50X2lkIjoiY2xpZW50Iiwic2NvcGUiOlsicmVhZCIsIndyaXRlIl19.QRKGuR9TJAAbntIwn-qGodv2cb5NAKk-VpJ3kKQd484' \
  -H 'Cache-Control: no-cache' \
  -H 'Content-Type: application/json' \
  -H 'Postman-Token: 27b190a7-747b-4e73-bf0a-44940224dd17' \
  -d '{
    "code": "ORD020",
    "articles": [
        {
            "id": 6,
			"code": "ART007",
			"name": "Bq Aquaris X Pro",
			"description": "Bq Aquaris X Pro 3GB/32GB Negro Libre Dual Sim",
			"price": 251.9,
			"catalogue": {
				"id": 2,
				"code": "CAT002",
				"name": "BQ Smartphones"
			}
        },
        {
            "id": 9,
			"code": "ART010",
			"name": "Huawei P10 Lite",
			"description": "Huawei P10 Lite 4GB/32GB Blanco Libre",
			"price": 259,
			"catalogue": {
				"id": 3,
				"code": "CAT002",
				"name": "Huawei Smartphones"
			}
        },
        {
            "id": 11,
			"code": "ART012",
			"name": "Huawei Nexus 6P",
			"description": "Huawei Nexus 6P 4G Gris Libre",
			"price": 430.33,
			"catalogue": {
				"id": 3,
				"code": "CAT002",
				"name": "Huawei Smartphones"
			}
        }
    ]
}'
```
El servidor nos devuelve la entidad generada con su id, en este caso el 3. Lo usaremos para la petición GET.

Ejemplo de peticion GET con curl a /api/pedido/{id}:
```sh
curl -X GET \
  http://localhost:8080/api/order/3 \
  -H 'Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MjQ1NjY4NzksInVzZXJfbmFtZSI6InVzZXIiLCJhdXRob3JpdGllcyI6WyJST0xFX1VTRVIiXSwianRpIjoiYmZlMjUzNzktMDQzMS00Y2U4LTkwYWYtZDgzODdmMjQxN2YzIiwiY2xpZW50X2lkIjoiY2xpZW50Iiwic2NvcGUiOlsicmVhZCIsIndyaXRlIl19.QRKGuR9TJAAbntIwn-qGodv2cb5NAKk-VpJ3kKQd484' \
  -H 'Cache-Control: no-cache' \
  -H 'Content-Type: application/json' \
  -H 'Postman-Token: a272e8ca-ea7e-4328-a2bb-2acdf0f1719e'
```
Obtenemos la siguiente respuesta:
```sh
{
    "id": 3,
    "code": "ORD020",
    "articles": [
        {
            "id": 6,
            "code": "ART007",
            "name": "Bq Aquaris X Pro",
            "description": "Bq Aquaris X Pro 3GB/32GB Negro Libre Dual Sim",
            "price": 251.9,
            "catalogue": {
                "id": 2,
                "code": "CAT002",
                "name": "BQ Smartphones"
            }
        },
        {
            "id": 9,
            "code": "ART010",
            "name": "Huawei P10 Lite",
            "description": "Huawei P10 Lite 4GB/32GB Blanco Libre",
            "price": 259,
            "catalogue": {
                "id": 3,
                "code": "CAT002",
                "name": "Huawei Smartphones"
            }
        },
        {
            "id": 11,
            "code": "ART012",
            "name": "Huawei Nexus 6P",
            "description": "Huawei Nexus 6P 4G Gris Libre",
            "price": 430.33,
            "catalogue": {
                "id": 3,
                "code": "CAT002",
                "name": "Huawei Smartphones"
            }
        }
    ]
}
```

### Documentando la aplicación con Swagger
Swagger es un framework que permite tanto documentar apis como crearlas. De igual forma, una API como es la nuestra, con las anotaciones de Swagger hace que el framework genere una UI accesible desde la web, capaz de explicar que hace cada método así como lanzar peticiones.

Para documentar usaremos varias anotaciones. Existen tanto para los controladores, como para las clases de modelo. Si observamos la UI, a la que podemos acceder mediante http://localhost:8080/api/swagger-ui.html, vemos que contiene anotaciones personalizadas tanto de lo que hace cada método como de las posibles respuestas. Además las clases de modelo que aparecen abajo, también tienen documentados sus atributos. Todo esto se logra gracias a las anotaciones @ApiOperation, @ApiResponse y @ApiModelProperty. Existen más, pero en este ejemplo son las que se han utilizado para manejar incluir Swagger.

## Construyendo la aplicación
La aplicación está lista para su despliegue y construcción. Debemos tener maven instalado en el ordenador. Si todo es correcto, podemos ejecutar el comando que aparece debajo. Con el se construye la aplicación y se crea el .jar listo para su despliegue.
```sh
mvn clean package

java -jar app-name.jar
```

## Despliegue en un contenedor Docker
Como punto final para el desarrollo del ejercicio. Vamos a desplegar la aplicación y la base de datos en contenedores Docker.
Si tenemos instalado Docker, el primer paso será descargar una imagen de mysql y arrancar la base de datos. Para ello ejecutaremos el siguiente comando:
```sh
docker run -d -p 33061:3306 --name mysql57 -e MYSQL_ROOT_PASSWORD=secret mysql:5.7 --character-set-server=utf8mb4 --collation-server=utf8mb4_unicode_ci
```

Ahora debemos configurar el proyecto para que acepte y se construya en un contenedor docker. Añadimos las dependencias de Docker al pom.xml y creamos en la ruta src/main/docker el fichero Dockerfile con el siguiente contenido:
```sh
FROM frolvlad/alpine-oraclejdk8:slim
 VOLUME /tmp
 ADD orders-api.jar app.jar
 RUN sh -c 'touch /app.jar'
 ENV JAVA_OPTS=""
 ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]
```
Posteriormente, usamos el siguiente comando en una consola en el directorio raiz del proyecto para construirlo:
```sh
mvn package docker:build
```

Ya tenemos ambos contenedores, solo es necesario enlazarlos. Tenemos que tener en cuenta que al enlazar los contenedores, el de 
mysql va a depender del de nuestra aplicación, por lo que el acceso no será desde la IP 127.0.0.1 sino desde la 172.17.0.2. Esto
nos obliga a modificar el fichero application.properties para conectar correctamente con la bd.
```sh
spring.datasource.url = jdbc:mysql://172.17.0.2:3306/orders?useSSL=false
```

Volvemos a ejecutar el comando para construir la imagen de docker con la aplicación y para finalizar ejecutamos el comando que 
permitirá que esto sea posible:
```sh
docker run -p 8080:8080 --name app --link mysql57:mysql -ti ricoh/orders
```

Finalmente, incluimos con un cliente mysql unos datos iniciales y ya tenemos nuestra aplicación lista para funcionar.

### Acceso a los contenedores Docker
Contenedor con mysql: https://hub.docker.com/r/carlosord/mysql/
Contenedor con app: https://hub.docker.com/r/carlosord/ricoh-orders/
