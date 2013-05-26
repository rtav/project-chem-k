# project-chem-k

This is a basic class project for Artificial Intelligence / Machine Learning.
The goal is to predict a chemical compound's reaction speed constant *k* when it
is reacted away through acid in a watery environment.

This repository represents only the sublimation of all previous and abandoned
efforts put into the project.

The project uses a lot of [clj-ml](https://github.com/leadtune/clj-ml),
some [WEKA](http://weka.sourceforge.net/) and a sprinkling of
[Midje](https://github.com/marick/Midje/).

## How to run the project

First run `lein deps` to fetch all dependencies (some 20MB).

`lein run` will (amateurishly) print our findings. 

Alternatively, you can run `lein jar` to create a JAR-file, or `lein uberjar` to
create a standalone JAR-file that includes all dependencies. You can then simply
run the standalone jar in target, but the working directory must absolutely
include `resources/results.arff`.

## How to run the tests

`lein midje` will run all tests.

`lein midje :autotest` will run all the tests indefinitely. It sets up a
watcher on the code files. If they change, only the relevant tests will be
run again.
