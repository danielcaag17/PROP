javac --release 11 src/domain/classes/*.java ;
javac --release 11 src/domain/controllers/*.java ;
javac --release 11 src/drivers/*.java ;
javac --release 11 src/exceptions/*.java ;
javac --release 11 src/presentation/*.java ;
javac --release 11 src/Main.java ;

move src\domain\classes\*.class ..\bin\class\domain\classes\ ;
move src\domain\controllers\*.class ..\bin\class\domain\controllers\ ;
move src\drivers\*.class ..\bin\class\drivers\ ;
move src\exceptions\*.class ..\bin\class\exceptions\ ;
move src\presentation\*.class ..\bin\class\presentation\ ;
move src\Main.class ..\bin\class\ ;
