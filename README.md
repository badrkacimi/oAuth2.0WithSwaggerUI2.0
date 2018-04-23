#  OAuth 2.0 Swagger-UI 

## How to Run ?

```
mvn clean
mvn spring-boot:run
```

## Swagger-UI
* After starting the application Click on [Swagger-home](http://localhost:8080/api/swagger-ui.html)

![Swagger-UI-Home](/src/main/resources/pic/Swagger-UI-Home.PNG)

## Configuration 
I used H2 DB [Embedded Databases](https://dzone.com/articles/3-java-embedded-databases)
to get information about user, you can add user in [data.sql](/src/main/resources/data.sql)

to secure methods, you can add the mapping in (ResourceServerConfiguration.java)
```
 public void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .anonymous().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                .antMatchers("/oauth/**").authenticated();

    }
```
Type of authorization : L’autorisation via mot de passe (Resource Owner Password Credentials Grant)

oauth configurations : applications properties

```
config.oauth2.tokenTimeout=3600
config.oauth2.resource.id=*****
config.oauth2.clientID=*****
config.oauth2.clientSecret=*****
security.oauth2.client.grantType=*****
config.oauth2.accessTokenUri=*******
```
## User Data (data.sql)

```
   badr@hive.com  | password
   ayoub@hive.com | password
   nidal@hive.com | password
```


## Authorize
* Use above given user details to login and generate the authorization token.
![login fill](/src/main/resources/pic/login fill.PNG)

![Swagger-UI-login](/src/main/resources/pic/Swagger-UI-login.PNG)

* Before : Unauthorized
![before](/src/main/resources/pic/before.PNG)


* After : you can now see information about user
![after](/src/main/resources/pic/after.PNG)

# Postman
* Test requests .
![Swagger-UI-login](/src/main/resources/pic/postman1.PNG)

* get the authorization token from swagger-UI after login.
![Swagger-UI-login](/src/main/resources/pic/postman2.PNG)

# Useful links 
http://www.bubblecode.net/fr/2016/01/22/comprendre-oauth2/

https://dzone.com/articles/hashing-passwords-in-java-with-bcrypt

https://swagger.io/docs/specification/authentication/oauth2/

https://github.com/spring-projects/spring-security-oauth

https://github.com/Baeldung/spring-security-oauth


