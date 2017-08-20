### Description

This project is an example project to show the usage of distributed hash table project.


### Usage

open one console, clone the project and install with maven

`console1> git clone https://github.com/erkanerol/distributed-hashtable.git`

`console1> cd distributed-hashtable`

`console1> mvn clean install`

open another console and change directory to ./cache-example

`console1> cd ./cache-example`
    
`console2> cd ./cache-example`


start example apps in these consoles

`console1> mvn exec:java`
    
`console2> mvn exec:java -Dexec.args="localhost 9879 localhost 9878"`


put an entry in one of the app and try to get this in the other. For example

`console1> PUT testValue value123`
    
`console2> GET testValue`

exit from apps

`console1>EXIT`
    
`console2>EXIT`
