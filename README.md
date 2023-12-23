# Projecte de Programació - Primera Entrega
**Fet per: [Dani, Jordi i Pau](Equip.txt)**

## Descripció
Primera entrega de l'assignatura [PROP](https://www.cs.upc.edu/~prop/) de la Facultat d'Informàtica de Barcelona ([FIB](https://www.fib.upc.edu/ca)). 

L'[enunciat](doc/Enunciat.pdf) del Quadrimestre de tardor de 2023, consisteix en fer un programa capaç de dissenyar un Teclat, a partir d'un alfabet, optimitzant la posició de cada caràcter en el que nosaltres definim com a layout.

La feina feta per cada membre del equip es pot trobar al fitxer [Relació classes implementades](doc/Relació-classes-implementades.pdf). S'hi ha posat les diferents classes del domini, els drivers i les excepcions implementades per cada membre. És possible que alguna classe estigui repetida, vol dir que dues persones han col·laborat en fer aquesta classe.

## Compilació

Al directori [src/](src/) trobem dos fitxers [make.sh](src/make.sh) i [make.bat](src/make.bat). Aquests fitxers compilen tot el codi del Projecte i el guarden a [bin/class/](bin/class/). Allá hi ha més informació sobre com executar el codi i els drivers.

Per a terminals Windows:
```
$ cd src/
$ ./make.bat
```
Per a terminals Linux:
```
$ cd src/
$ ./make.sh
```

## Execució

Al directori [bin/](bin/) trobem dos fitxers [start.sh](bin/start.sh) i [start.bat](bin/start.bat). Executant aquests fitxers s'executarà el programa. [Veure més informació](bin/README.md)

## Estructura de directoris

- **[bin/](bin/)**: els binaris (.class) del projecte una vegada compilat.

- **[data/](data/)**: les dades de la capa de persistència i altres fitxers necessaris com imatges per la GUI.

- **[doc/](doc/)**: la diferent documentació del Projecte. - e.g.: Casos d'ús, Diagrama de Classes, etc.

- **[src/](src/)**: on es troben els diferents fitxers de codi.

    - **[data/](src/data/)**: s'hi troben les classes que gestionen la capa de persistència.

    - **[domain/](src/domain/)**: s'hi troben les classes que gestionen la capa de model, així com també el controlador de domini i la factoria.

    - **[drivers/](src/drivers/)**: s'hi troben els drivers per a fer els tests de les classes de model.

    - **[exceptions/](src/exceptions/)**: s'hi troben les classes que gestionen les diferentes excepcions que es poden produir durant l'execució del programa.

    - **[presentation/](src/presentation/)**: s'hi troben les classes que gestionen la capa de presentació.

- **[test/](test/)**: on es troben fitxers necessaris per al testing del programa.