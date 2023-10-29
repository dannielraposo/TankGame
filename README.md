# TankGame
We present you our Tanketo++ game. Hope you have fun!

Main class - TankGame.java

Instructions:
- Use WASD keys to move.
- Use your mouse to aim and shoot (left click) the enemy tanks.


# Instalation and execution instructions:
1. Clone repository
2. Access the folder in the command window
3. Compile all java classes with "javac -d . *.java"
4. Launch game with "java TankGame"

   ![image](https://github.com/dannielraposo/TankGame/assets/148542289/73f4349e-df9c-4227-8f36-29d050931ad3)
   ![image](https://github.com/dannielraposo/TankGame/assets/148542289/f96406aa-f92d-48f5-8402-65faf152fab4)
   ![image](https://github.com/dannielraposo/TankGame/assets/148542289/2292308c-0eda-454d-b7c3-c5cfb412bde7)

# Objective and information about the game:
The objective of the game is to pass all 6 levels without being hit 3 times.
Throughout these levels you will encounter different types of enemies, blocks and power-ups!

## Tank Types
![tanktypes](https://github.com/dannielraposo/TankGame/assets/54643225/8ceace96-5d49-49f6-b4bf-d45f11c792df)
- **Main Tank**: this is your tank. It doesn't have any special advantage but it can pick up power-ups. Your bullets (as well as the enemies' bullets) ricochet on the walls, but only the enemies' bullets will be able to hit (not your own).
- **Basic Tank**: this is the most basic tank. It doesn't move, but it will shoot you if you are in its line of sight. The tanks scans around a 90º cone looking your you.
- **Moves Tank**: this tank is able to move, and will shoot you when you are seen. They will get near you but have a "safety distance" in order to not be too close.
- **Hard Tank**: this tank will be able to take more shots than the basic and move tank.
- **Triple Tank**: this tank is the most dangerous one. It can take more hits than any other enemy. It also has increased speed and triple shot in a cone in front of him.

