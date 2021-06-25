#!/bin/bash

echo "=== POST some Employees ===";
curl -i -H "Content-Type:application/json" -d '{"name": "Martha","surname":"Sutherland","birthDate":"2000-07-30","gender":"FEMALE"}' http://localhost:8080/employees
curl -i -H "Content-Type:application/json" -d '{"name": "John","surname":"Doe","birthDate":"1990-10-10","gender":"MALE"}' http://localhost:8080/employees
curl -i -H "Content-Type:application/json" -d '{"name": "Alice","surname":"Barnum","birthDate":"1993-01-01","gender":"FEMALE"}' http://localhost:8080/employees
curl -i -H "Content-Type:application/json" -d '{"name": "Brian","surname":"Doe","birthDate":"1997-02-20","gender":"MALE"}' http://localhost:8080/employees
echo

echo "=== Find by Surname ==="
curl -i "http://localhost:8080/employees/search/findBySurname?surname=Doe"
echo

echo "=== Find by BirthDate ==="
curl -i "http://localhost:8080/employees/search/findByBirthDateAfter?date=1995-01-01"
echo

echo "=== PATCH an Employee ==="
echo "This should be done by searching the right resource URI with HATEOAS and then applying the PATCH to that resource"
curl -i -X PATCH -H "Content-Type:application/json" -d '{"name": "Anna"}' http://localhost:8080/employees/3
echo

echo "=== POST some Positions ==="
curl -i -H "Content-Type:application/json" -d '{"name":"Developer", "desc":"Writes software"}' http://localhost:8080/positions
curl -i -H "Content-Type:application/json" -d '{"name":"ProjectManager", "desc":"Leads developers"}' http://localhost:8080/positions
echo

echo "=== Create some associations by PUT a Position in an Employee ==="
curl -i -X PUT -H "Content-Type:text/uri-list" -d "/positions/1" "http://localhost:8080/employees/1/position"
curl -i http://localhost:8080/employees/1/position
curl -i -X PUT -H "Content-Type:text/uri-list" -d "/positions/2" "http://localhost:8080/employees/2/position"
curl -i http://localhost:8080/employees/2/position
curl -i -X PUT -H "Content-Type:text/uri-list" -d "/positions/2" "http://localhost:8080/employees/3/position"
curl -i http://localhost:8080/employees/3/position
curl -i -X PUT -H "Content-Type:text/uri-list" -d "/positions/2" "http://localhost:8080/employees/4/position"
curl -i http://localhost:8080/employees/4/position
echo

echo "=== Find Employees by Position ==="
curl -i "http://localhost:8080/employees/search/findByPosition?position=/positions/2"
echo
