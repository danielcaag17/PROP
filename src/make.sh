javac --release 11 domain/classes/*.java ;
javac --release 11 domain/controllers/*.java ;
javac --release 11 drivers/*.java ;
javac --release 11 exceptions/*.java ;
javac --release 11 presentation/*.java ;
javac --release 11 Main.java ;

mv domain/classes/*.class ../bin/class/domain/classes ;
mv domain/controllers/*.class ../bin/class/domain/controllers ;
mv drivers/*.class ../bin/class/drivers ;
mv exceptions/*.class ../bin/class/exceptions ;
mv presentation/*.class ../bin/class/presentation ;
mv Main.class ../bin/class
