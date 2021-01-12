# Codecool shop

## About

This is a webshop where you can buy fantasy-themed puppets from different TV shows and movies. The project was written in Java, using JavaScript, Bootstrap and PostgreSQL database.

## How to run this project

You need [Jetty](https://www.eclipse.org/jetty/) webserver to run this project.
The pom.xml file needs to contain this:

```XML
<plugin>
  <groupId>org.eclipse.jetty</groupId>
  <artifactId>jetty-maven-plugin</artifactId>
  <version>9.4.9.v20180320</version>
  <configuration>
    <reload>automatic</reload>
    <scanIntervalSeconds>1</scanIntervalSeconds>
  </configuration>
</plugin>
```
After that you can build and run the application by writing:

```bash
mvn jetty:run 
```
in your terminal in the same directory as your pom.xml file!

Now the webpage is running on [this](http://localhost:8080) link.

## Authors

- Szabó Friderika, Bodroghy Kristóf, Gamper Renátó, Catanzáró Tamás

## Contact

- szabomidu@gmail.com
