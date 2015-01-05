# SelfStarter

An other crowdfunding project, by Ensimag's students.

Have a look to our quick reminder at the bottom of this file !





## Setup

### MySQL

You will need a mysql database. An easy way to get one is to use
[docker](http://docker.com).

#### With Docker

First, install docker: https://docs.docker.com/installation/ubuntulinux/
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

The database keep its state when the container is stoped (No need to recreate
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

To create the tables, use:
```
mysql -udac -pcoucou -h127.0.0.1 -Ddac < sql/create.sql
```

### Glassfish

Download and unpack Glassfish, or use Netbeans to install it.

#### Environement variables

Set `$GLASSFISH_HOME` in your .bashrc:
```
export GLASSFISH_HOME=...
```

#### JDBC driver

Download the JDBC driver for MySQL from
`http://dev.mysql.com/downloads/connector/j/3.1.html`

Extract it and put `mysql-connector-java-3.1.14-bin.jar` in
`$GLASSFISH_HOME/glassfish/lib`

#### Connect Glassfish to MySQL

To setup the ressource `jdbc/dac` and the connection pool `dac`, insert the
following into `$GLASSFISH_HOME/glassfish/domains/domain1/config/domain.xml`
between `<resources>` and `</resources>` :
```
<jdbc-connection-pool datasource-classname="com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource" res-type="javax.sql.ConnectionPoolDataSource" name="dac">
  <property name="password" value="coucou"></property>
  <property name="URL" value="jdbc:mysql://localhost:3306/dac"></property>
  <property name="DatabaseName" value="dac"></property>
  <property name="ServerName" value="localhost"></property>
  <property name="username" value="dac"></property>
  <property name="Url" value="jdbc:mysql://localhost:3306/dac"></property>
</jdbc-connection-pool>
<jdbc-resource pool-name="dac" jndi-name="jdbc/dac"></jdbc-resource>
```

##### Troubleshooting

If the copy/paste to the xml configuration did not worked, you can try this.

* Launch your GlassFish server.
* Go to GlassFish admin page (usually http://localhost:4848, change the port as
needed).
* Go to Resources - JDBC - Connection Pools - New
```
Pool name: dac
Resource: javax.sql.ConnectionPoolDataSource
Database driver: MySQL
```
* You need to change properties such as:
```
URL and url: jdbc:mysql://localhost:3306/dac
ServerName: localhost
DatabaseName: dac
Password and password: coucou
User and username: dac
```
* Try to ping it in the console to see if it worked. (Start your mySQL server
    first !).
* Then create a new JDBC Resource: Go to Resources - JDBC - Connection
    Resources - New
```
JNDI Name: jdbc/dac
Pool Name: dac (your previously set pool)
```

#### Set the Glassfish password

Run your Glassfish server, either with Netbeans, or with:
```
$GLASSFISH_HOME/glassfish/bin/asadmin start-domain
```

* Go to http://localhost:4848/
* Go to Common Task - Domain - Administrator Password
* Set the password to `admin`


### Netbeans

#### Environment variables

You need to set `$GLASSFISH_HOME` for Netbeans.

* Go to your $(netbeans intallation folder)/etc
* Add the following line at the end of `netbeans.conf`
```
export GLASSFISH_HOME
```

#### Glassfish

If you use the embedded Glassfish, you will need to set the new password to
`admin`:
* Go to Service - Servers - Glassfish
* Right click - Properties -Common
* Set the password to `admin`








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

### Run tests

```
mvn install -Ddac.skipTests=false
```





## Quick reminder

If you followed our instructions, these should be valid...

### Passwords

* MySQL dac:coucou root:dac
* Glassfish admin:admin
* VM equipe2:dac root:dac

### Commands

* Start MySQL `sudo docker start mysql-dac`
* Stop MySQL `sudo docker stop mysql-dac`
* Create tables `mysql -udac -pcoucou -h127.0.0.1 -Ddac < sql/create.sql`
* Log into MySQL `mysql -udac -pcoucou -h127.0.0.1 -Ddac`
* Start Glassfish `$GLASSFISH_HOME/glassfish/bin/asadmin start-domain`
* Stop Glassfish `$GLASSFISH_HOME/glassfish/bin/asadmin stop-domain`
* Clean `mvn clean`
* Build `mvn install`
* Deploy from command-line `mvn glassfish:deploy` (in ear/)
* Undeploy from command-line `mvn glassfish:undeploy` (in ear/)
* Redeploy from command-line `mvn glassfish:redeploy` (in ear/)
* Run tests `mvn install -Ddac.skipTests=false`

