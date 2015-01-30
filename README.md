# SelfStarter

[![Build Status](https://travis-ci.org/tonyguolei/projet_dac.svg?branch=master)](https://travis-ci.org/tonyguolei/projet_dac)
[![Coverage Status](https://coveralls.io/repos/tonyguolei/projet_dac/badge.svg?branch=master)](https://coveralls.io/r/tonyguolei/projet_dac?branch=master)

Another crowdfunding project, by Ensimag's students.
The code is available [here](https://github.com/tonyguolei/projet_dac).

Have a look to our [quick reminder](#quick-reminder)!

![Selfstarter logo](https://raw.githubusercontent.com/tonyguolei/projet_dac/master/web/src/main/webapp/img/logo.png)

## Setup

### MySQL

You will need a mysql database. An easy way to get one is to use
[docker](http://docker.com).

#### With Docker

First, [install docker](https://docs.docker.com/installation/ubuntulinux/) and `mysql-client`. Running these should be enough
```
sudo apt-get install docker.io mysql-client
```
If needed, run the docker deamon.

Create and run the container named *mysql-dac* containing a mysql server with
user *dac* and password *coucou* and database *dac*:
```
sudo docker run --name mysql-dac -e MYSQL_ROOT_PASSWORD=coucou -e MYSQL_USER=dac -e MYSQL_PASSWORD=coucou -e MYSQL_DATABASE=dac --net=host -d mysql
```
##### Utils

To stop the mysql server container:
```
sudo docker stop mysql-dac
```

To resume the mysql server container:
```
sudo docker start mysql-dac
```

The database keeps its state when the container is stopped (No need to recreate
the table).

#### On OSx

Run:
```
brew install mysql
mysql.server start
mysql -uroot
```
Then in the `mysql` prompt:
```
CREATE USER 'dac'@'localhost' IDENTIFIED BY 'coucou';
GRANT ALL PRIVILEGES ON * . * TO 'dac'@'localhost';
FLUSH PRIVILEGES;
```

#### Create tables

To create the tables, do:
```
mysql -udac -pcoucou -h127.0.0.1 -Ddac < sql/create.sql
```

#### Populate the database

To populate the database, do:
```
mysql -udac -pcoucou -h127.0.0.1 -Ddac < sql/populate.sql
```

### Glassfish

Download and unpack Glassfish, or use Netbeans to install it.

#### Environment variables

Set `$GLASSFISH_HOME` in your `.bashrc`:
```
export GLASSFISH_HOME=...
```

#### JDBC driver

Download the JDBC driver for MySQL from
`http://dev.mysql.com/downloads/connector/j/3.1.html`

Extract it and put the `mysql-connector-java-3.1.14-bin.jar` in
`$GLASSFISH_HOME/glassfish/lib`

#### Connect Glassfish to MySQL

To setup the ressource `jdbc/dac` and the connection pool `dac`, copy
`domain.xml`, which can be found at the root of the project, to
`$GLASSFISH_HOME/glassfish/domains/domain1/config`

### Netbeans

#### Environment variables

You need to set `$GLASSFISH_HOME` for Netbeans.

* Go to your `<netbeans intallation folder>/etc`
* Add the following line at the end of `netbeans.conf`
```
export GLASSFISH_HOME
```

## Build and run

### Netbeans

* Right click on the maven project `ear`
* Build with dependencies
* Right click on the maven project `ear`
* Run

### Command line

```
$GLASSFISH_HOME/glassfish/bin/asadmin start-domain
mvn clean
mvn install
cd ear
mvn glassfish:deploy
```

### Access the web site

The web site is available at [http://localhost:8080/web](http://localhost:8080/web)

### Run Ejb tests

```
mvn test -DskipEjbTests=false
```

### Run Selenium tests

```
mvn test -DskipSeleniumTests=false

Clean database after execution of Selenium tests
mysql -udac -pcoucou -h127.0.0.1 -Ddac < sql/create.sql
mysql -udac -pcoucou -h127.0.0.1 -Ddac < sql/populate.sql
```

### Generate coverage report for ejb

```
cd ejb
mvn clean compile exec:exec cobertura:cobertura -DskipEjbTests=false
```
The report website index can be found at `ejb/target/site/cobertura/index.html`


## Quick reminder

If you followed our instructions, these lines should be valid...

### Passwords

* MySQL dac:coucou root:dac
* Glassfish admin: (no password)
* VM equipe2:dac root:dac
* Application admin account admin@dac.com:dac

### Reports

* The ejb's coverage `ejb/target/site/cobertura/index.html`

### Commands

* Start MySQL `sudo docker start mysql-dac`
* Stop MySQL `sudo docker stop mysql-dac`
* Create tables `mysql -udac -pcoucou -h127.0.0.1 -Ddac < sql/create.sql`
* Populate tables `mysql -udac -pcoucou -h127.0.0.1 -Ddac < sql/populate.sql`
* Log into MySQL `mysql -udac -pcoucou -h127.0.0.1 -Ddac`
* Start Glassfish `$GLASSFISH_HOME/glassfish/bin/asadmin start-domain`
* Stop Glassfish `$GLASSFISH_HOME/glassfish/bin/asadmin stop-domain`
* Clean `mvn clean`
* Build `mvn install`
* Deploy from command-line `mvn glassfish:deploy` (in ear/)
* Undeploy `$GLASSFISH_HOME/glassfish/bin/asadmin undeploy ear`
* Run Ejb tests `mvn test -DskipEjbTests=false`
* Run Selenium tests `mvn test -DskipSeleniumTests=false` (clean the database before running selenium tests. See above)
* Generate ejb's coverage report `mvn clean compile exec:exec cobertura:cobertura -DskipEjbTests=false`
    (in ejb/)

