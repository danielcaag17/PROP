# Projecte de Programació - Primera Entrega
**Fet per: [Dani, Jordi i Pau](Equip.txt)**

## Descripció
Primera entrega de l'assignatura [PROP](https://www.cs.upc.edu/~prop/) de la Facultat d'Informàtica de Barcelona ([FIB](https://www.fib.upc.edu/ca)). 

L'[enunciat](altres/Enunciat.pdf) del Quadrimestre de tardor de 2023, consisteix en fer un programa capaç de dissenyar un Teclat, a partir d'un alfabet, optimitzant la posició de cada caràcter en el que nosaltres definim com a layout.

## Compilació

La compilació del Projecte es fa ...

```
$ comandes_per_a_compilar
```

## Estructura de directoris

- **[bin/](bin/)**: els binaris (.class) del projecte una vegada compilat.

- **[data/](data/)**: les dades de la capa de persistència.

- **[doc/](doc/)**: la diferent documentació del Projecte. - e.g.: Casos d'ús, Diagrama de Classes, etc.

- **[src/](src/)**: on es troben els diferents fitxers de codi.

    - **[data/](src/data/)**: s'hi troben les classes que gestionen la capa de persistència.

    - **[domain/](src/domain/)**: s'hi troben les classes que gestionen la capa de model, així com també el controlador de domini i la factoria.

    - **[drivers/](src/drivers/)**: s'hi troben els drivers per a fer els tests de les classes de model.

    - **[exceptions/](src/exceptions/)**: s'hi troben les classes que gestionen les diferentes excepcions que es poden produir durant l'execució del programa.

    - **[presentation/](src/presentation/)**: s'hi troben les classes que gestionen la capa de presentació.

- **[test/](test/)**: on es troben fitxers necessaris per al testing del programa.