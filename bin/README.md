## Directori bin

En aquest directori es on es guarden els fitxers compilats. Dins de [class/](class/) Es pot trobar la mateixa estructura de fitxers que al directori [src/](../src/) amb els executables de cada classe del codi.

## Execució

Executar [start.sh](start.sh) o [start.bat](start.bat) en funció del sistema per a iniciar el programa complet.
```
$ ./start.sh
```
Per als drivers: 
```
$ ./start<nom-driver>.sh
```

## Informació dels jocs de prova per als drivers i l'execució del Main

- DriverAlfabet

Per a probar amb diferents Alfabets, hi ha uns textos de prova:
    ./subgrup-prop32.2/test/exemples_input_alfabet/Text1.txt     --> text en angles
    ./subgrup-prop32.2/test/exemples_input_alfabet/Text2.txt     --> text en catala
    ./subgrup-prop32.2/test/exemples_input_alfabet/Text3.txt     --> text en castella
    ./subgrup-prop32.2/test/exemples_input_alfabet/Words1.txt    --> llista-paraules en angles
    ./subgrup-prop32.2/test/exemples_input_alfabet/Words2.txt    --> llista-paraules en catala
    ./subgrup-prop32.2/test/exemples_input_alfabet/Words3.txt    --> llista-paraules en castella

- DriverTeclat

Per a probar a crear diferents Teclats amb diferents Alfabets, hi ha uns textos de prova:
    ./subgrup-prop32.2/test/exemples_input_alfabet/Text1.txt     --> text, mida 26
    ./subgrup-prop32.2/test/exemples_input_alfabet/Text2.txt     --> text, mida 24
    ./subgrup-prop32.2/test/exemples_input_alfabet/Text3.txt     --> text, mida 24
    ./subgrup-prop32.2/test/exemples_input_alfabet/Words1.txt    --> llista-paraules, mida 26
    ./subgrup-prop32.2/test/exemples_input_alfabet/Words2.txt    --> llista-paraules, mida 24
    ./subgrup-prop32.2/test/exemples_input_alfabet/Words3.txt    --> llista-paraules, mida 24
Es important que la mida de l'Alfabet sigui la mateixa que la del Layout

- DriverHungarian

info driver hungarian

- DriverDomini (o Main)

info main