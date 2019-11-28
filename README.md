### Micronaut example app

Simple app on Java 11 and Micronaut for money transfer between accounts. Data stored in-memory.

To run, you need Java 11 jdk/jre to compile and run, or docker installed

#####Java 11

```
./gradlew build                                #or gradle.bat build for windows systems
./gradlew run
```

#####Docker
```
docker build . -t mn-money-transfer:latest
docker run -p 8900:8900 mydocker:latest
```
Api calls:
```
curl -XPOST -H "Content-Type: application/json" -d '{"userId":1, "balance":100.00}' http://localhost:8900/bank/account
curl -XPOST -H "Content-Type: application/json" -d '{"userId":2, "balance":100.00}' http: //localhost:8900/bank/account
curl -XPOST -H "Content-Type: application/json" -d '{"userIdFrom":1, "userIdTo":2, "amount":10.0}' http://localhost:8900/bank/transfer
curl -XGET 'http://localhost:8900/bank/account/balance?userId=1'
curl -XGET 'http://localhost:8900/bank/account/balance?userId=2'
```