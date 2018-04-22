#  OAuth 2.0 Swagger-UI 

## How to start ?

```
mvn spring-boot:run
```

## Swagger-UI
* After starting the application Click on [Swagger-home](http://localhost:8080/api/swagger-ui.html)

![Swagger-Home](/authentication/src/main/resources/images/Swagger-UI-Home.png "Swagger UI Home")

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
   badr@example.com  | password
   ayoub@example.com | password
   nidal@example.com | password
```


## Authorize
* Use above given user details to login and generate the authorization token.
![Swagger-Home](/authentication/src/main/resources/pic/Swagger-ui2-authHive.png)

![Swagger-Home](/authentication/src/main/resources/pic/swagger-ui2-loginHive.png)

* Before : Unauthorized
![Swagger-Home](/authentication/src/main/resources/pic/swagger-ui2-login-tokenHive.png)


* After: you can now see information about user
![Swagger-Home](/authentication/src/main/resources/pic/swagger-ui2-login-tokenHive.png)


# useful links 
http://www.bubblecode.net/fr/2016/01/22/comprendre-oauth2/






