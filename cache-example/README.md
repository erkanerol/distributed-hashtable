### Description

This project is an example project to show the usage of distributed hash table project.


### Usage

Open two console

    `console1>`
    
    `console2>`


 
change directory to ./cache-example

    `console1> cd ./cache-example`
    
    `console2> cd ./cache-example`


start example in two jvm

    `console1> mvn exec:java -Dexec.args="localhost 9878"`
    
    `console2> mvn exec:java -Dexec.args="localhost 9879 localhost 9878"`


put one of the app and try to get this value another. For example

    `console1> PUT testValue value123`
    
    `console2> GET testValue`

exit apps

    `console1>EXIT`
    
    `console2>EXIT`
