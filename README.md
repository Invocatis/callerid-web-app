# caller id

## Problem Prompt

Interview Exercise
Caller Id Service:

The goal of this exercise is for you to implement a standalone service that will respond to requests seeking caller id information.
Api Requirements/Notes
* 		Api should return json
* 		Phone numbers should be in E.164 format.
* 		Appropriate http codes should be returned on error.
API Endpoints
GET /query
Fetches caller id information.

Params:
number - the number we want caller id information for.

Example query:
GET http://localhost/query?number=%2B15556789090

Response:
{results: [{ “name”: “Bob Barker”, “number”: “+15556789090”, “context”: “personal”}]}

POST /number
Adds caller id data to the service.

Body:
name - contact name
number - the number in E.164 format
context - the context for the phone number.

Note: A phone number may be present multiple times, but can only appear once per context. In other words you can think of a <number,context> pair as unique.

Seed Data
Normally a service like this would call other services in turn, but to keep things simple we are going to provide seed data for the service as a csv file. Your service will need to read in that initial data and use it to serve requests.

Line format:
<phone number>,<context>,<caller id>

Example:
5556780909,work,John Doe

The seed data can be downloaded is attached.

Service Requirements
* 		We prefer if your service is standalone, this means that it shouldn’t require additional server software (tomcat/apache/etc) to run. If it does require such software you will need to provide setup instructions.
* 		The port on which the service listens should be configurable without editing code.
* 		Your code should include a README file with instructions on how to run your service. Shell scripts are also appreciated but not required.
* 		You may NOT use an external datastore.
* 		You may NOT use an embedded datastore.

Misc/Other Notes
* 		Once again to keep things simple, data submitted to your service does not need to be persisted.
* 		While our main goal is to look at your code, we will run your code and test it.
* 		There is no timeline, please take as long as you want. However if you take especially long we may check in.
* 		If you have any questions please ask.

## Design Choices

I tried to keep my implementation as simple as possible. The last time I completed a challenge like this I added a lot of details and abstract concepts that while I still stand behind, made the project a bit intractable for the readers to understand in the context of an interview.

The solution uses compojure for routing, and leverages clojure.data libraries for json and csv parsing. The database, mocked in the phonewagon.data namespace, was by no means the focus of this project, and as such I did what I had to to make it work, and little more. The logic for loading in test data is included in this namespace, and denoted by comments.

If implementing a more verbose data solution, I would use a meaningless id scheme (uuid, auto-increment) and have an index for number/context pairs to verify uniqueness.

The phonewagon.middleware namespace contains components simplifying dealing with conversions of request and response data so all functionality corresponding to the app can expect clojure.core data structures.

Number parameters submitted are coerced into the E.164 format, and if not specified, assumed to be US numbers. In essence, if a string with 9-15 digits in it is submitted, all non digit numbers are removed and it's assumed to be a phone number. More complex parsing and validating might be desired, but again, keeping it simple.

Deploying the database is done with the leiningen ring plugin; for a more proper implementation, a main method would likely be implemented with some command line parameters or a properties file, however, this felt sufficient for the given specs.

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
