### Aufgabe 1 ---------------------------------------------------
#       Mit Hilfe welchem Shortcut koennen Befehle in RStudio
#       unter Windows ausgefuehrt werden?
#Ctrl + Enter

### Aufgabe 2 ---------------------------------------------------
#       Mit Hilfe welchem Shortcut kann der Zuweisungsoperator
#       ' <- ' in RStudio unter Windows erzeugt werden?
#Alt + -

### Aufgabe 3 ---------------------------------------------------
#       Nutze den Zuweisungsoperator um einem Objekt 'alter' den
#       Wert deines aktuellen Alters zuzuweisen.
alter <- 20

### Aufgabe 4 ---------------------------------------------------
#       Mit welchem Symbol kann man in R die Hilfe zu einem
#       bekannten Befehl erhalten? Welcher Shortcut fuehrt
#       ebenfalls zur Hilfeseite, wenn der Cursor auf dem
#       Funktionsnamen steht?
#       Probiere beide Varianten aus um an die Hilfeseite der
#       empirischen Varianz (Funktion: var()) zu gelangen?
? var()
# F1

### Aufgabe 5 ---------------------------------------------------
#       Lege mindestens drei weitere beliebige Objekte an und
#       weise ihnen beliebige Werte zu.
hund <- 5
kater <- 4
maus <- 3

### Aufgabe 6 ---------------------------------------------------
#       Probiere herauszufinden (eigenstaendige Recherche) mit
#       welchem Befehl du dir alle bis jetzt im Enviroment
#       angelegten Objekte anzeigen lassen kannst.

ls()

### Aufgabe 7 ---------------------------------------------------
#       Probiere herauszufinden (eigenstaendige Recherche) mit 
#       welchem Befehl du ein oder mehrere Objekte aus dem 
#       Enviroment loeschen kannst. Verwende diesen Befehl und 
#       loesche damit das Objekt 'alter'.

rm(alter)

### Aufgabe 8 ---------------------------------------------------
#       Wie kann durch eine Kombination der letzten beiden 
#       Befehle das gesamte Enviroment/Workspace geloescht
#       werden? Loeschen aller aktuell vorhandener Objekte.

rm(list = ls())
