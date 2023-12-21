javac --release 11 src/domain/classes/*.java ;
javac --release 11 -cp .:../lib/json-20231013.jar src/domain/controllers/*.java ;
javac --release 11 -cp .:../lib/junit-4.13.2.jar src/drivers/*.java ;
javac --release 11 src/exceptions/*.java ;
javac --release 11 -cp .:../lib/json-20231013.jar src/data/*.java ;
javac --release 11 src/presentation/*.java ;
javac --release 11 src/Main.java ;

mv src/domain/classes/*.class ../bin/class/src/domain/classes/ ;
mv src/domain/controllers/*.class ../bin/class/src/domain/controllers/ ;
mv src/drivers/*.class ../bin/class/src/drivers/ ;
mv src/exceptions/*.class ../bin/class/src/exceptions/ ;
mv src/presentation/*.class ../bin/class/src/presentation/ ;
mv src/data/*.class ../bin/class/src/data/ ;
mv src/Main.class ../bin/class/src/ ;
