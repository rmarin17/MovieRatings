# MovieRatings

Capas de la Aplicacion:

1. Capa de presentaci�n: Encargada de presentar el sistema al usuario, comunicar la informaci�n 
y capturar la informacion del usuario.
La conforman: Toda la carptea UI con 

->Listmoviefragment: Encargado de Visualizar la lista de peliculas.
->MovieRatingsActivity: Es el activity principal en el cual se mustran todos los fragments y navegaci�n.
->PopularityMovieFragment: Encargado de Visualizar la lista de peliculas en base a la popularidad.
->TopMovieFragment: Encargado de Visualizar la lista de peliculas en base a la calificaci�n.
->UpcomingFragment: Encargado de Visualizar la lista de peliculas en base a la fecha de lanzamiento

Nota** Cabe resaltar que toda la carpeta de recusos junto con la declaracion de las navegaciones, menus 
y vistas de la aplicacion tambien pertenecen a esta capa.

2. Capa de negocio: Se reciben las peticiones del usuario y se env�an las respuestas tras el proceso.
La conforman: las carpetas work,viewmodel,repository y network; junto con las clases 

->RefresDataWork: Encargado de ejecutar la actualizacion de datos en background.
->MoviesViewModel: Encargado de cargar la informacion de peliculas desde la base de datos.
->MoviesRepository: Encargado de consultar la informacion de internet y dirigirla a la base de datos.
->DataTransferObject: Modelado de clases para hacer transacciones de internet a la base de datos.
->Service: Encargado de hacer la peticiones a la api y descargar la informacion.
->MovieRatingsApp: encargada del manejo de la clase refreshdatawork para que esta se ejecute una vez por d�a
por el consumo de bateria.

3. Capa de datos: Es donde residen los datos y es la encargada de acceder a los mismos.
La conforma la carpeta de database junto con las clases 

->DataBaseentity: Modelado de clases de peliculas para almacenar en la base de datos
->Room: Definicion de la base datos y sus transacciones.


---------------------------------------------------------------------------------------------------------

Preguntas:

1. En qu� consiste el principio de responsabilidad �nica? Cu�l es su prop�sito?
Consiste en que cada clase o modulo debe cumplir o tener una unica responsabilidad, guiandonos a separar los 
comportamientos.


2. Qu� caracter�sticas tiene, seg�n su opini�n, un �buen� c�digo o c�digo limpio?

Debe tener una buena documentacion , con comentarios claros y consisos, debe ser facil de lerr y entender, simple 
de cambiar y donde los metodos no deben hacer mas de una cosa y sobre todo debe estar bien estructurado.




