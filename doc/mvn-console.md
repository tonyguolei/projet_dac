How to launch the application in console
=========================================

* `mvn clean` (optional)
* `mvn install` to build the application.
* `mvn embedded-glassfish:run [-Dgf.port=PORT]` to launch the application, by
  default the server will be launch on port 8080, you can change it by setting
  up PORT with the optionnal parameter.
