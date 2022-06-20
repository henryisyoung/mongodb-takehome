# mongodb-takehome

This is a repo for mongodb takehome excercise

Following these the steps to run the code and verify the output

1. install maven `brew install maven`
2. git clone the repo `git clone https://github.com/henryisyoung/mongodb-takehome.git` to your local machine
3. `cd` to the repo directory and run `mvn clean compile package` to complie and package the program
4. Pipe a json file to java program and execute the coode. i.e `cat src/main/resources/test2.json | java -jar target/mongo-test-1.0-SNAPSHOT-jar-with-dependencies.jar`

Unit tests are located in `src/test/java/com/mongo/AppTest.java`
