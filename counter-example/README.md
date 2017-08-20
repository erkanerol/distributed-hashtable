### Description

This project is an example project to show the usage of distributed hash table project.


### Usage

open one console, clone the project and install with maven

`console1> git clone https://github.com/erkanerol/distributed-hashtable.git`

`console1> cd distributed-hashtable`

`console1> mvn clean install`

open another console and change directory to ./cache-example

`console1> cd ./counter-example`
    
`console2> cd ./counter-example`


start two different apps

`console1> mvn  exec:java -Dexec.args="8080"`
    
`console2> mvn  exec:java -Dexec.args="8081 localhost 9879 localhost 9878"`


open another console and call the urls with different parameters and see the values increase 

`console3> curl http://localhost:8081/counter?query=key1`
   
`console3> curl http://localhost:8080/counter?query=key1`

`console3> curl http://localhost:8080/counter?query=key2`

`console3> curl http://localhost:8081/counter?query=key2`

`console3> curl http://localhost:8081/counter?query=key2`

`console3> curl http://localhost:8080/counter?query=key2`

`console3> curl http://localhost:8080/counter?query=key1`