# caller id

## Usage

Run the server using:
```
lein ring server
```
The server runs on port 3000; to change this, change the [:ring :port] entry in the project.clj file.

Connect to the server's repl using:
```
lein repl :connect localhost:9998
```

This port may also be changed in the project.clj file.

Use the following commands to test if it works:
```
curl -X POST -H "Content-Type: application/json" --data "{\"number\": \"12345678910\", \"name\": \"Jacob Marley\", \"context\": \"mobile\"}" http://localhost:3000/number
```
```
curl -i -X GET 'http://localhost:3000/query?number=12345678910'
```

To get 400 Error for incorrectly formatted numbers:

```
curl -X POST -H "Content-Type: application/json" --data "{\"number\": \"1234\", \"name\": \"Jacob Marley\", \"context\": \"mobile\"}" http://localhost:3000/number
```
```
curl -i -X GET 'http://localhost:3000/query?number=1234'
```
