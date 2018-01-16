`Run project`

   - mvn clean install
   - mvn spring-boot:run
   
_Endpoints_

 - **GET** /items
 - **POST** /items -d {"name" : "test", "quality" : 0, "sell_in" : 0}
 - **PATCH** /items/quality -d {"quality" : 0}
