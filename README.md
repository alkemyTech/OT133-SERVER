# OT133 - Cohorte Enero 2022 - Java

## Docs

### Swagger

To display the Swagger documentation use the endpoint:

```
/swagger-ui/#
```

You can also access the JSON documentation:

```
/v2/api-docs
```

### Users

| E-mail                    | password |  rol  |
| :------------------------ | :------: | :---: |
| admin@alkemy.org          |  admin   | ADMIN |
| juanlopez@alkemy.org      |  admin   | ADMIN |
| agustinleyes@alkemy.org   |  admin   | ADMIN |
| tomassasnchez@alkemy.org  |  admin   | ADMIN |
| juanrodriguez@alkemy.org  |  admin   | ADMIN |
| maurodell@alkemy.org      |  admin   | ADMIN |
| murielcorrea@alkemy.org   |  admin   | ADMIN |
| ricardoledesma@alkemy.org |  admin   | ADMIN |
| rodrigocaro@alkemy.org    |  admin   | ADMIN |
| alkemy@alkemy.org         |  admin   | ADMIN |
| user@mail.com             |   user   | USER  |
| juanlopez@mail.com        |   user   | USER  |
| agustinleyes@mail.com     |   user   | USER  |
| tomassasnchez@mail.com    |   user   | USER  |
| juanrodriguez@mail.com    |   user   | USER  |
| maurodell@mail.com        |   user   | USER  |
| murielcorrea@mail.com     |   user   | USER  |
| ricardoledesma@mail.com   |   user   | USER  |
| rodrigocaro@mail.com      |   user   | USER  |
| alkemy@mail.com           |   user   | USER  |


### Test 

Se añade el plugin de pocoyo para ver el estado de cobertura. Con paquetes excluidos

```<!-- Code Coverage report generation -->
      <plugin>
        <groupId>org.jacoco</groupId>
        <artifactId>jacoco-maven-plugin</artifactId>
        <version>0.8.4</version>
        <configuration>
          <excludes>
            <exclude>src/main/java/com/alkemy/ong/entity/*</exclude>
            <exclude>**/entity/** </exclude>
            <exclude>**/dto/* </exclude>
            <exclude>**/service/** </exclude>
            <exclude>**/mapper/** </exclude>
          </excludes>
        </configuration>
    
        <executions>
          <execution>
            <goals>
              <goal>prepare-agent</goal>
            </goals>
          </execution>
          <execution>
            <id>generate-code-coverage-report</id>
            <phase>test</phase>
            <goals>
              <goal>report</goal>
            </goals>
          </execution>
        </executions>
      </plugin>
    
```


El reporte se encuentra en la siguente ruta.

```
\OT133-SERVER\target\site\jacoco\index.html
```

Si desea solo ejecutar desde consola.
```
mvn test
``` 
Si le surge un el siguiente error, pruebe cambiar el proyecto a java 8 desde el pom.xml(Mi caso es que tengo instalado java 8 y 11 a la vez y al parecer le gusta el 8 que el 11)
```
com/alkemy/ong/controller/ActivityControllerTest has been compiled by a more recent version of the Java Runtime (class file version 55.0), this version of the Java Runtime only recognizes class file versions up to 52.0.
```