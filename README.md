# This project is a sandbox for learning Spring Cloud #

## Modules ##
* `config-server`

Contains configuration for all other modules

* `person-info-service`

Service that provides data about a person via JSON-RPC API

* `person-info-service-api`

Interface and data-transfer objects for the `person-info-service`

* `mailing-service`

Service provides http api for sending the emails to users. Receives users data from the `person-info-service`

## Run the application ##

First you should build all the modules. To do this, run this command in the base directory:

`mvn clean install`

The `config-server` must be started first. To run services use `spring-boot-maven-plugin`.

`cd config-server`<br />
`mvn clean spring-boot:run`

Then run `person-info-service` and `mailing-service` in other terminals in any order.

Of you can use IntellijIDEA Run Dashboard

## Check ##

This request checks if the `mailing-service` is available and whether it's integration with `person-info-service` works correctly

`$ curl -X GET -H "Content-Type:application/json" http://localhost:8878/send`<br />
Result:<br />
`["Ivan Petrov","Petr Ivanov","John Smith"]`

You also can check `person-info-service` without `mailing-service` using JSON-RPC request:

`curl -d "{\"id\":\"1\",\"jsonrpc\":\"2.0\",\"method\":\"allPersons\"}" ^`<br />
`-X POST -H "Content-Type:application/json" ^`<br />
`http://localhost:8877/PersonService.json`<br />
Result:<br />
`{"jsonrpc":"2.0","id":"1","result":[{"name":"Ivan Petrov","email":"ivan@mail.com"},{"name":"Petr Ivanov","email":"petr@google.com"},{"name":"John Smith","email":"john@smith.c"}]}`

