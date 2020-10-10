Automated Dev Env Build
Build & Local Run Dependencies
JDK 8
sbt brew install sbt for macOS, choco install sbt for win
Prod Run Dependencies
JDK 8
Testing
This will run automated tests

sbt test
Running Locally
This will print out a message to STDOUT.

sbt "runMain example.Hello"
Packaging Up For Docker
Directory containing app is target/universal/stage/

sbt stage
bin/ contains the start script, lib/ contains the libraries, and resources contains config files.

Running in Prod
bin/automated-fargate-poc
Versioining
Version.txt is set to 0.0.0 and if {{MAJOR}} or {{MINOR}} or {{PATCH}} present in the commit message, then it will bump as desired the version.txt It is compulsary to enter the tag as {{MAJOR}} or {{MINOR}} or {{PATCH}} inside the commit message.

Use of if-else to be implemented in the Jenkinsfile.