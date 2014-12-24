Setup the database for dummies
===


Install GlassFish
---

Install your glassfish in Netbeans:
Tools > Servers > Add server.
Let the default configuration and finish


Install a MySQL server
---
You can install any mysql server you want.
Here we're using docker, which is available on any Linux distribution.
First, install docker: https://docs.docker.com/installation/ubuntulinux/

If needed, run the docker deamon.

On arch:
```
sudo systemctl start docker
```

Create and run the container named *mysql-dac* containing a mysql server with
user *dac* and password *coucou* and database *dac*:
```
sudo docker run --name mysql-dac -e MYSQL_ROOT_PASSWORD=coucou -e MYSQL_USER=dac
-e MYSQL_PASSWORD=coucou -e MYSQL_DATABASE=dac --net=host -d mysql
```
Here, you might want to create a table and populate the database, using dac.sql
```
mysql -udac -pcoucou -h127.0.0.1 -Ddac < sql/dac.sql
```

To stop the mysql server container:
```
sudo docker stop mysql-dac
```

To resume the mysql server container:
```
sudo docker start mysql-dac
```

The database keep its state when the container is stoped (No need to recreate
the table)

Install JDBC driver
---

Download the JDBC driver for MySQL from
`http://dev.mysql.com/downloads/connector/j/3.1.html`

Extract it and put `mysql-connector-java-3.1.14-bin.jar` in
`~/GlassFish_Server/glassfish/lib` *(this path can change, check, in admin
console, Server (Admin Server) > General > Installation Directory to know where
Glassfish is installed)*


Setup GlassFish for MySQL
---

Launch your netbeans GlassFish server (services, right click on glassfish).
Go to GlassFish admin page (usually http://localhost:4848, change the port as
needed).
Go to Resources > JDBC > Connection Pools > New

* Pool name: dac
* Resource: javax.sql.ConnectionPoolDataSource
* Database driver: MySQL

You need to change properties such as:

* URL and url: jdbc:mysql://localhost:3306/dac
* ServerName: localhost
* DatabaseName: dac
* Password and password: coucou
* User and username: dac

Try to ping it in the console to see if it worked.

Then create a new JDBC Resource.
Go to Resources > JDBC > Connection Resources > New

* JNDI Name: jdbc/dac
* Pool Name: dac (your previously set pool)


Load the MySQL script in the database
----

You can do it using Netbeans.
Go to:
Services > database

* Driver : MySQL

Next.

* Username: dac
* Password: coucou

Finnish

Open the sql/dac.sql script in netbeans, run it using the bar at the top of the
editor: Choose your newly created mysql connection.


You can now redeploy your GlassFish server.
WARNING: Launch ear submodule, not the whole mvn (integrated Glassfish).
