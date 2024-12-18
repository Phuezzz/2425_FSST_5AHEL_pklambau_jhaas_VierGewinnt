Erstellen Sie eine Java Anwendung welche nach dem Model-View-Controller Prinzip arbeitet - siehe https://www.tutorialspoint.com/design_pattern/mvc_pattern.htm und nach dieser Aufgabenstellung: Spielregeln

Auf einem Feld aus 7 Spalten und 6 Zeilen werfen zwei Spieler abwechselnd einen Stein in eine noch nicht belegte Spalte.
Der Stein fällt nach unten bis er auf einen anderen Stein oder den Boden trifft.
Sobald ein Spieler eine Reihe aus vier ununterbrochenen Steinen bildet (waagerecht, senkrecht oder diagonal), gewinnt er das Spiel.
Kann kein Spieler vier Steine in eine Reihe bringen und sind alle Spalten belegt, so endet das Spiel unentschieden
Anforderungen

Vor Start des Spieles werden die Namen der Spieler durch Tastatur eingelesen.
Spieler 1 wird durch ein o dargestellt, Spieler 2 durch ein x
Der Spieler, der gerade an der Reihe ist soll mit seinem eingegebenen Namen über dem Spielfeld angezeigt werden
Vor jeder Eingabe der gewünschten Spaltennummer muss das Spielfeld mit dem aktuellen Spielstand dargestellt werden. Die Eingabe der gewünschten Spaltennummer hat mit Zahlen zu erfolgen. Die Eingabe muss 
solange wiederholt werden bis eine gültige Spaltennummer (im gültigen Bereich bzw. nicht volle Spalte) angegeben wurde.
Nach jedem Spielzug muss geprüft werden, ob das Spiel gewonnen wurde. Ist dies der Fall, muss dem Gewinner des Spieles gratuliert werden.
Außerdem muss geprüft werden, ob das Spiel unentschieden ausgegangen ist. Dies ist dann der Fall, wenn alle Spalten voll sind, und keiner der Spieler 4 Spielsteine in einer Reihe hat. Eine entsprechende 
Meldung muss ausgegeben werden
Ich möchte es als JavaFX Projekt implementieren, es gibt bereits eine Application und einen Controller im FXML Projekt. Verwechsel diesen Controller nicht mit dem Controller des Model-View-Controller Prinzips.

Erstelle eine eigene Klasse fürs View, fürs Model und für den Controller. 

Schreibe nun eine Implementierung dieser Klassen in die FXML Application Datei. Wenn nötig auch im FXML Controller

Kannst du die ersten zwei Wichtigen Ergänzungen, also die Dynamische Spieler-Namen-Eingabe und das Styling, ausprogrammieren

Nun werden 'x' und 'o' in die jeweiligen Raster geschrieben, wenn man auf die Spalte klickt. Ich möchte, dass man bei der Namenseingabe am Anfang, die Farbe auswählen kann die der Spieler verwenden will. 
Ändere dies bitte so um, dass statt dem 'x' und dem 'o' das Raster im Spielfeld auf die bei der Namenseingabe jeweils ausgewählten Farbe für den Spieler eingefärbt wird.

Ein Problem gibt es noch: Sobald ein Spieler seine letzte Auswahl eingibt, bevor er gewinnt, wird zwar angezeigt, dass dieser Spieler gewonnen hat, jedoch wird dieses Raster nicht mehr in der jeweiligen Farbe eingefärbt.
Außerdem soll das Spiel nachdem ein Spieler gewonnen hat nach 10 Sekunden resetet werde. Also nur das Spielfeld resetet, die Spieler bleiben die gleichen und die Farben auch.