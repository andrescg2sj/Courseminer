
A brief presentation
======

The objective of this project is to gather information about formation courses contained in documents made by different entities in Madrid, Spain.

Current version of the project is able to extract tables from some PDF files and write them to an HTML file.

Compile with dependencies:

```
mvn compile package assembly:single
```

Test with:
```
java -cp target/crminer-app-1.0-SNAPSHOT-jar-with-dependencies.jar org.sj.punidos.crminer.PDFTableToHTML res/CEPI-1-1.pdf -o test.htm
```

You can find a little more information [on the wiki](https://github.com/andrescg2sj/Courseminer/wiki/Full-page-HTML-export-starting-to-work).


Presentación
=====


Este proyecto surge con la intención de apoyar el servicio prestado por [Pueblos Unidos](http://pueblosunidos.org/) en la Comunidad de Madrid para facilitar la [búsquedad de cursos de formación](https://voluntariadopueblosunidosv09.firebaseapp.com/#/main), como ayuda a la búsqueda de empleo.

Hasta ahora, la única forma de actualizar los datos en este buscador es introducirlos manualmente en un documento de hoja de cálculo, a partir de los documentos publicados por distintas entidades que ofrecen los cursos de formación.

El objetivo de **Courseminer** es facilitar esta tarea, procesando automáticamente los documentos que emiten las principales entidades (por ejemplo, los CEPI de la Comunidad de Madrid), y rellenar la hoja de cálculo con los datos obtenidos.

Primeros pasos
====

Este proyecto usa Maven como gestor de construcción.

Compilar el código:

```
mvn package
```

Compilar con dependencias:

```
mvn compile package assembly:single
```

Probar `PDFTableToHTML`:

```
java -cp target/crminer-app-1.0-SNAPSHOT-jar-with-dependencies.jar org.sj.punidos.crminer.PDFTableToHTML res/CEPI-1-1.pdf -o test.htm
```


Estructura
===

...

Próximas tareas
===

Fases del proyecto.

1. Extraer datos de los documentos procedentes de un CEPI y exportarlos a un documento Excel.
2. Extraer datos de los documentos de una OMIO y exportarlos a un docuemnto Excel.
3. Desarrollar *web scrappers* para distintas fuentes de cursos en la web.


[Más detalles...](https://github.com/andrescg2sj/Courseminer/wiki)
