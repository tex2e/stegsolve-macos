# Stegsolve on MacOS

This program is a modified version of the [Stegsolve](http://www.caesum.com/handbook/Stegsolve.jar).
Original stegsolve displays an odd layout when you run [Analyze] -> [Data Extract] on MacOS.


## Compilation

```
javac stegsolve/*.java
jar cvfm stegsolve.jar META-INF/MANIFEST.MF -C stegsolve/*.class
```

## Execution

```
java -jar stegsolve.jar
```
