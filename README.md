## Developer Notes ##
Shipment-process is a Spring-boot application which runs on 8080 port in internal TomCat server by default

It has below services
* GET: /api/shipment/healthcheck
* POST: /api/shipment/process

### Libraries ###
* Java 8
* Maven 3
* Spring-boot Starter Web - 2.14-RELEASE
* Swagger2 - 2.7.0

### Clone the project ###
* git clone https://github.com/Eesuram/shipment-processor.git
* cd shipment-processor

### Build the application ###
mvn clean install

### Run the Application ###
java -jar target/shipment-processor-1.0.jar

### Run the Application in Debug mode, port 8000 ###
java -jar -Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=8000 target/shipment-processor-1.0.jar
 
### Accessing Application
* PORT: 8080
* Context Path: /api

* Swagger URL: http://localhost:8080/api/swagger-ui.html
* Shipment Resource Direct URL: http://localhost:8080/api/swagger-ui.html#/Shipment32Resource

### Additional Details ###
* ShipmentManagerImpl - It has the actual logic to optimize zipcode ranges
* ShipmentValidator - It has the logic to validate input
* ShipmentExceptionHandler - It generates the meaningful error response
* ShipmentResource - SpringBoot Controller provides services
* JUnits for the corresponding classes, used Mock where required
* Slf4j for logging, used default log properties
  
### HTTP Status codes ###
* 200 - Success Response
* 412 - Validate Failed
* 500 - Unhandled Runtime Exceptions
* 404 - Resource not found 

### Assumptions Made ###
* ZipCode should be 5 digits
* As few US zipcodes start with '0', defined zipcode as String and parsed to Integer when required
* Used Collections.sort() for sorting

### Validations added ###
* Input should not be empty
* ZipCode range: startValue should not be greater than endValue
* ZipCode should be 5 digits in length

### Sample Payloads ###
Sample payloads are under src/main/resources/sample_payloads