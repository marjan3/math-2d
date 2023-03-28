# Math 2D

> Tool for visualizing vectors, calculating visually vectors (adding, subtracting, dot, cross product), trigonometry etc.

![Image](screenshot.png)
<br/>

## Getting started 
The project is a multi-module maven project consisting of:

- [gui](gui/)
- [math](math/)

To get a local copy up and running follow these simple example steps. 

### Prerequisites
This section will describe requirements needed to satisfy the installation or running of the project.

 - Java 17
 - Maven
 
### Nice to have
 - Intellij

### Build
 - `git clone https://github.com/Tanevski3/math-2d.git`
 - `cd ./math-2d`
 - `mvn clean install`
 
### Run
 - Right click and run `MainEntry.java`
 
### Test
 - Run `mvn test`

## Future changes

- Fix: on selection label moves
- Selection is clunky
- Undo: delete, X, Y, rename, add, subtract, perpendicular left and right
- Map undo/redo to keys

- implement other math operations
- improve code, separation of concerns, single responsibility, reduce duplication, maybe event buss
- always expand Inspect accordions
- write unit tests
 
## Contact

For contact, you can reach me at [marjantanevski@outlook.com](marjantanevski@outlook.com).

## License

MIT © [Marjan Tanevski](marjantanevski@outlook.com)