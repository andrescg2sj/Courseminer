

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
mvn compile
mvn package
mvn assembly:assembly
```

Ejecutar `PDFTableToHTML`:

```
java -cp target/crminer-app-1.0-SNAPSHOT-jar-with-dependencies.jar org.sj.punidos.crminer.PDFTableToHTML
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

Primera fase. Extraer datos de un CEPI
---

Las próximas tareas a realizar en el proyecto, por orden de prioridad son las siguientes:

1. Terminar y depurar `org.sj.punidos.crminer.PDFTableToHTML`.
   * Desarrollar la funcionalidad de leer un documento PDF que contiene una tabla de cursos de un CEPI, crear al menos un objeto de clase `Table` con su contenido, y mostrarlo en formato HTML.
   * Terminar y depurar la funcionalidad de recorte (clipping).
2. Desarrollar la funcionalidad de agrupar objetos gráficos para distungir múltiples tablas en una página.
3. Terminar la clase `org.sj.punidos.crminer.courses.Course`.
4. Desarrollar la funcionalidad de rellenar los datos de un objeto de clase `Course` a partir los datos del documentod de un CEPI.
5. Integrar la funcionalidad de exportar objetos `Course` a un fichero excel.


