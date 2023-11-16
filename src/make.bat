javac --release 11 domain/classes/*.java ;
javac --release 11 domain/controllers/*.java ;
javac --release 11 drivers/*.java ;
javac --release 11 exceptions/*.java ;
javac --release 11 presentation/*.java ;
javac --release 11 Main.java ;

move domain\classes\*.class ..\bin\class\domain\classes ;
move domain\controllers\*.class ..\bin\class\domain\controllers ;
move drivers\*.class ..\bin\class\drivers ;
move exceptions\*.class ..\bin\class\exceptions ;
move presentation\*.class ..\bin\class\presentation ;
move Main.class ..\bin\class
