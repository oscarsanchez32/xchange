XCHANGE
Web para comprar e intercambiar juegos, creada con Spring Boot y Angular.

Pasos para iniciar la aplicación:

Clonar el repositorio
git clone https://github.com/oscarsanchez32/xchange.git

Crear la base de datos MYSQL con el comando: "create database xchange;"

Lanzar el back de spring, estará ejecutándose en el puerto 8081

Para el front con angular, desplazarse al directorio "/ui"
Ejecutar el comando "npm install"
Tras esto, ejecutar "ng s -o"
Se abrirá el navegador en el puerto 4200.


En usuario puede:

Ver, comprar y agregar juegos a su carrito.
Cree solicitudes de intercambio de juegos que deseen, a cambio de un juego de su propiedad.

Un administrador puede...

Administre los juegos presentes en la aplicación y realice diversas operaciones sobre ellos.

Intercambio
Los usuarios tienen la posibilidad de intercambiar juegos con otros usuarios. 
Un usuario puede navegar a la página de Intercambios para ver el tablero de intercambio.

pagina de intercambios

Aquí, un usuario puede aceptar una solicitud de intercambio existente (realizada por otro usuario) o crear su propia solicitud de intercambio.
