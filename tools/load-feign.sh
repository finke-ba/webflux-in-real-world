#! /bin/bash

wrk2 -t8 -c1000 -d20s -R200 http://localhost:8081/http/items/feign?id=5c7e3cf664043f50a0b46db1 -H "Authorization: Basic dXNlcjpwYXNzd29yZA=="