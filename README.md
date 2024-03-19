# Toy World 21JHB52

Toy World Project For Team 21JHB52

## Description
This project creates a simple server for clients to connect to, launch and control various robots in a world with other clients and their robots.

## Installation
1. Navigate to the Directory that contains the project.
2. For the Server run (java -jar Server.jar)
3. For the Client run (java -jar Client.jar)
4. Add any additional arguments after Client/Server.jar if necessary (e.g. java -jar Server.jar 10 20 30 40 50 60)

## Contributing/Refactoring authors
1. Mpho Dipitso
2. Shafique Francis
3. Mmathuso Kgalane
4. Seipati Tshabalala

## Story
Title:   Launch a Robot
A)  Valid launch command should succeed
a.1) If command incorrect, then it should fail
and return a failure message.
a.2) When command fails, client can use help to see possible commands.
B)  When world has no space for a robot
b.1) Error message
C)  World does not allow robots with the same names
c.1) Error message

Title: Get the state of the robot
A)  When robot name is valid
a.1) Give state
B)  When robot invalid
b.1) Error message


Title: Look Around
A) If world is empty
a.1) State empty variables
B) If object in view
b.1) State objects
C) Robot in view
c.1) State robots


## Authors and acknowledgment
1. Liam Quick
2. Thapelo Mmakola
3. Hlulani Clifton Ngobeni
4. Kamogelo Molope


## Project status
Finished