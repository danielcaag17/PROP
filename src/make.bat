javac --release 11 -cp .;../lib/json-20231013.jar src/domain/classes/*.java 
javac --release 11 -cp .;../lib/json-20231013.jar src/domain/controllers/*.java 
javac --release 11 -cp .;../lib/junit-4.13.2.jar src/drivers/*.java  
javac --release 11 src/exceptions/*.java 
javac --release 11 src/presentation/*.java 
javac --release 11 -cp .;../lib/json-20231013.jar src/data/*.java
javac --release 11 src/Main.java

move src\domain\classes\*.class ..\bin\class\src\domain\classes\ 
move src\domain\controllers\*.class ..\bin\class\src\domain\controllers\ 
move src\drivers\*.class ..\bin\class\src\drivers\ 
move src\exceptions\*.class ..\bin\class\src\exceptions\ 
move src\presentation\*.class ..\bin\class\src\presentation\ 
move src\data\*.class ..\bin\class\src\data\ 
move src\Main.class ..\bin\class\src\ 
