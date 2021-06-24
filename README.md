PUSH some employees:
```shell
curl -i -X POST -H "Content-Type:application/json" -d '{"name": "Martha","surname":"Sutherland","birthDate":"2000-07-30","gender":"FEMALE"}' http://localhost:8080/employees
curl -i -H "Content-Type:application/json" -d '{"name": "John","surname":"Doe","birthDate":"1990-10-10","gender":"MALE"}' http://localhost:8080/employees
curl -i -H "Content-Type:application/json" -d '{"name": "Alice","surname":"Barnum","birthDate":"1991-01-01","gender":"FEMALE"}' http://localhost:8080/employees
```
Being a level 3 REST API, it supports HATEOAS so links of the added resource will be returned.
