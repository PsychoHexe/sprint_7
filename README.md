#  krivstova_ai

Java 11

JUnit 4.12

maven 3.9.0

Aspectj 1.9.7

gson 2.8.9

Allure 2.15.0, rest-assured 5.3.0

Очистка теста и запуск Allure server
```
mvn clean test
```

```
mvn allure:serve
```

```
allure generate ./target/allure-results -o ./target/allure-report --clean
```