# Backend Alteryx Homework - Stock Market Simulator #

## Procedure ##

We ask all candidates for Innovation Labs team to complete a homework as part of the interview process. We expect the candidates to spend about 4-6 hours on the assignment but it’s completely up to you. Spend as much or as little as you’d like that can really show off your skills. During your on-site interview, one of the sessions will consist of you presenting this homework, how you solved the problem, trade offs you made, any limitations, etc. so be ready to talk through it.

## Problem ##

We want you to build a backend web server that maintains users data around an artificial portfolio containing a subset of 100 possible stocks (From S&P 500) for the user to buy and sell from the year 2017, given a starting budget of $100,000.

While we are leaving this fairly open ended here are some specific requirements we’d expect any solution to have:

* Ability to manage a User via CRUD API
* Ability to manage the User’s portfolio:
    * Stores a sequence of Trades, a Trade has at least:
        * Date of the Trade
        * Stock symbol
        * Amount of shares 
    * Allows CRUD operations. Recall that all Trades need to be constrained by the initial budget.
    * Allows for a CSV file upload that should replace the Trades in the Portfolio with the Trades in the file
    * Should include an API called evaluate that returns the value of a portfolio given a date in 2017.


## Provided to you ##
* JSON of stock symbols and prices per trading day in the year 2017
* This README.md file


## Submission Expectations ##
* You can use your language of choice! 
* A `README.md` describing the file structure, contents, and any specific
* A `Dockerfile` should be created that we can build and then run, the server in the container should bind to port 80. For example we should be able to run something like the following:
    * `docker build -t latest .`
    * `docker run -p 5000:80 latest`
* Need a databases/storage mechanism? No problem, please include a `docker-compose.yml` that can be used in conjunction with your `Dockerfile`
* Tar/Zip your source and send in when done


## Out of scope ##
* Authentication, we don’t need to see you implement this unless you want to :)



## Useful Commands
```shell
docker-compose down
mvn clean install -DskipTests
docker-compose up

docker exec -it stock-sim-postgres bash
psql -U postgres

CREATE DATABASE stocksimdb;
\c stocksimdb;

\l

\d

\d+ stock;

docker inspect -f '{{range.NetworkSettings.Networks}}{{.IPAddress}}{{end}}' stock-sim-postgres

docker rm stock-sim-app

docker rmi stock-sim-app:1.0


curl -v -F file=@file.csv http://localhost:8084/stocksim/v1/portfolio/uploadcsv

curl -X GET "http://localhost:8084/stocksim/v1/portfolio/evaluate?userId=2c98808382f51c6d0182f51ce1520000&evaluationDate=2017-06-23"

select sum(b.totalPrice) totalSpent from portfolio b where b.userId = '2c9e808382f545690182f5457ee50000' and b.dateOfTrade <=':dateOfTrade'
```