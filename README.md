# site-parser

ETL project which converts all CSV and JSON files in a given directory into a
consolidated JSON output.

This is the Clojure implementation of Java take-home problem used to screen
candidates for a team I was on. Built to provide comparison between the
languages for Java developers unfamiliar with Clojure.

The input files are lists of "sites", with unique IDs and several
other attributes relevant to and RTB system.

## To Build

Install `lein`.

Build the uberjar: `lein uberjar`.

## To Run

With the uberjar:
```
java -jar target/uberjar/site-parser-0.1.0-SNAPSHOT-standalone.jar resources/input output.json
```
