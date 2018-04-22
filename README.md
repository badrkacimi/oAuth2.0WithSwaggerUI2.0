#  OAuth 2.0 Swagger-UI 

## How to Run ?

```
mvn spring-boot:run
```

## Swagger-UI
* After starting the application Click on [Swagger-home](http://localhost:8080/api/swagger-ui.html)

![Swagger-UI-Home](/src/main/resources/pic/Swagger-UI-Home.PNG)

## Configuration 
I used H2 DB [Embedded Databases](https://dzone.com/articles/3-java-embedded-databases)
to get information about user, you can add user in data.sql

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
Type of authorization : Authorization Code Grant (AuthorizationServerConfig.java)
```
 public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients
                .inMemory()
                .withClient("client")
                .authorizedGrantTypes("client_credentials", "password", "refresh_token", "authorization_code")
                .scopes("read", "write")
                .resourceIds("oauth2-resource")
                .accessTokenValiditySeconds(expiration)
                .refreshTokenValiditySeconds(expiration)
                .secret("secret");
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


# useful links 
http://www.bubblecode.net/fr/2016/01/22/comprendre-oauth2/

https://swagger.io/docs/specification/authentication/oauth2/

https://github.com/spring-projects/spring-security-oauth

https://github.com/Baeldung/spring-security-oauth


