# Correr 

## Abrir (doble click) en start.bat (windows)
```
	@echo off
	java -jar miApp.jar

	java -cp "miApp.jar;gson.jar" Navixy
	java -cp "miApp.jar:gson.jar" Navixy (linux)
```

# Compilar (JAR incluido -cp)
```
	javac -cp gson.jar Navixy.java
```

# Correr (All JAR) 
```
	java -cp ".;gson.jar" Navixy  (. optional)
	java -cp ":gson.jar" Navixy   (linux)				
```

# Empaquetar JAR

## Compilar (JAR incluido -cp)
```
	javac -cp gson.jar Navixy.java
```
## Empaquetar Jar 
```
	jar cf miApp.jar *.class
```

## Correr (All JAR) 
```
	java -cp "miApp.jar;gson.jar" Navixy
```





# Empaquetar JAR (Manisfest)
``
Manifest-Version: 1.0
Main-Class: Navixy
Class-Path: gson.jar 
``

## Compilar (JAR incluido -cp)
```
	javac -cp gson.jar Navixy.java
```

## Empaquetar Jar (manisfest incluido) 
```
	jar cfm miApp.jar mymanisfest.txt *.class
```

## Correr (All JAR) 
```
	java -cp "miApp.jar;gson.jar" Navixy

```

## java -jar (Completo con manisfest y todo)

### NOTA: para ejecutar 
```
	java -jar miApp.jar                         //  en lugar de  
	java -cp "miApp.jar;gson.jar" Navixy
```
``
Debes de crear el Manisfeste incluir el:
	Class-Path: gson.jar 
``

### NOTA2: todavia necesita la libreria gson.jar a usar  
``
	Java -jar , pero ya no hay que espeficar ni las dos jar, ni la clase
	porque esta en el manisfest
``

