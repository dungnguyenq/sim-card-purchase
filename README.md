# sim card purchase
- This is the simple microservice system to provide the purchase prepaid data for SIM card

   
## Getting Started
#### First, you need Update Twillio account in {path to sim-card-purchase}\sms-service\src\main\resources (You can create your account in https://www.twilio.com/ or contact me: quocdunguit08@gmail.com to get demo account)
- Using Docker Compose
```shell
cd {path to sim-card-purchase}>
./packageAll
./buildDockerImage.bat
docker-compose up
```
- Using IDE (IntelliJ IDEA)
   - Install PostgreSQL 10 (https://www.postgresql.org/download/windows/)
   - Restore voucher_service_db and secure_service_db
   ```shell
   psql -U {username} < {path to sim-card-purchase}\init-db\init-secure-service-db.sql
   psql -U {username} < {path to sim-card-purchase}\init-db\init-secure-service-db.sql
   ```
   - Install RabbitMQ (https://www.rabbitmq.com/install-windows.html)
   - Enable Rabbit Management (https://www.rabbitmq.com/management.html)
   - Run the system in order ()
      - third-party-server -> {path to sim-card-purchase}\third-party-server
      - registration-server -> {path to sim-card-purchase}\registration-server
      - zuul-server -> {path to sim-card-purchase}\zuul-server
      - voucher-service -> {path to sim-card-purchase}\voucher-service
      - gallery-service -> {path to sim-card-purchase}\gallery-service
      - sms-service -> {path to sim-card-purchase}\sms-service
      - secure-service -> {path to sim-card-purchase}\secure-service
      
## Curl Commands
```shell
curl -d "{\"phoneNumber\":\"<your-phone-number-with-country-code>\"}" -H "Content-Type: application/json" http://localhost:8881/api/voucher
curl -v http://localhost:8881/api/gallery?phonenumber=<your-phone-number-with-country-code>
curl -d "{\"phoneNumber\":\"<your-phone-number-with-country-code>\"}" -H "Content-Type: application/json" http://localhost:8881/api/secure/otp
curl -d "{\"otpCode\":<otp-receive-from-sms>}" -H "Content-Type: application/json" http://localhost:8881/api/secure/otp/<your-phone-number-with-country-code>
```

#### Examples:
```shell
curl -d "{\"phoneNumber\":\"84968864509\"}" -H "Content-Type: application/json" http://localhost:8881/api/voucher
curl -v http://localhost:8881/api/gallery?phonenumber=84968864509
curl -d "{\"phoneNumber\":\"84968864509\"}" -H "Content-Type: application/json" http://localhost:8881/api/secure/otp
curl -d "{\"otpCode\":914799}" -H "Content-Type: application/json" http://localhost:8881/api/secure/otp/84968864509
```

## Technologies:
   - Java 8
   - Spring Boot
   - Spring Cloud Netflix
   - JPA/Hibernate
   - JUnit
   - RabbitMQ
   - PostgreSQL
   - Docker
   
## Architecture Overview
![Architecture_Overview1](https://user-images.githubusercontent.com/26158591/104240627-f5271300-548e-11eb-8d87-282385300cda.png)

- third-party-server : the server provide API to generate voucher code, it will generate the voucher code from 3 -> 120 seconds. In this API I only use some case (in miliseconds) for test: (3000, 4000, 8000, 10000, 15000, 29000, 30000, 31000, 35000, 40000, 60000, 80000, 120000, 125000).
- registration-server: this is eureka server which can manage services.
- zuul-server: this is a proxy gateway, it is an intermediate layer between the user and service, it will map the request from user to the services.
- voucher-service: It provide the APIs to generate the voucher and get list vouchers from phone number.
- gallery-service: It provide the API to get list vouchers from phone number
- sms-service: It will listening Queue on RabbitMQ and send message via SMS. It also open a API to send SMS.
- secure-service: It provide the APIS to geneate One Time Password (OTP) and verify it.\

## Entity Diagram
#### voucher_service_db
![voucher_service_db (1)](https://user-images.githubusercontent.com/26158591/104239716-8a290c80-548d-11eb-803c-f8198b4549d3.png)

#### secure_service_db
![voucher_service_db (2)](https://user-images.githubusercontent.com/26158591/104239988-ee4bd080-548d-11eb-8d47-68bf9a305562.png)

## Sequence Diagram
### Generate voucher
![sequence_generate_voucher](https://user-images.githubusercontent.com/26158591/104246888-5ce25b80-5499-11eb-9566-54254e8bcbf2.png)

### Get vouchers
![sequence_get_vouchers](https://user-images.githubusercontent.com/26158591/104248947-07a84900-549d-11eb-81ec-2510b2cb9a71.png)

## Screenshots
### Eureka Dashboard
![image](https://user-images.githubusercontent.com/26158591/104250224-b2216b80-549f-11eb-879f-64bcec0fc8b5.png)

### RabbitMQ
![image](https://user-images.githubusercontent.com/26158591/104250319-eeed6280-549f-11eb-80a3-add952c0e834.png)

![image](https://user-images.githubusercontent.com/26158591/104250344-00366f00-54a0-11eb-8adc-4e350680f3ee.png)
