Pagina Web Billetera Virtual. 

Ide usado Spring Tool 

Base de datos creada "alkewallet"
Despues correr programa para que se creen las tablas 
ingresar en la tabla "cuentas"
el numero de la cuenta, el saldo y el usuario asignado. 
luego registrarte en la pagina de inicio. 
En lo ideal crea dos cuentas para que puedas probar el envio y recepcion de dinero. 
Hay un boton "retiro" pero lo tengo deshabilitado por el momento" (se supone que sería para poder realizar retiros de dinero de las cuentas.)


spring.datasource.url = jdbc:mysql://localhost:3306/alkewallet
spring.datasource.username = root
spring.datasource.password = 
server.port=8080

## Hibernate Properties
spring.jpa.properties.hibernate.dialect= org.hibernate.dialect.MySQL8Dialect
# Hibernate ddl auto (create, create-drop, validate, update)
spring.jpa.hibernate.ddl-auto = update

logging.level.com.registro.usuarios=DEBUG
logging.level.org.springframework.web=DEBUG
logging.level.org.hibernate.SQL=debug

logging.level.org.hibernate.type=TRACE

spring.thymeleaf.prefix=classpath:/templates/
spring.thymeleaf.suffix=.html

spring.thymeleaf.cache=false

