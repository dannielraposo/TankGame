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

Do not forget to turn up the volume!!

![image](https://github.com/dannielraposo/TankGame/assets/148542289/35e02cc3-88f8-4db3-9d4e-ab0c9590d2cf)
![image](https://github.com/dannielraposo/TankGame/assets/148542289/b60cf90e-7fbd-48ad-8871-065e8eb85302)
![image](https://github.com/dannielraposo/TankGame/assets/148542289/cc1ddca3-a714-4e07-ae52-8f56dab10f5f)

# Objective and information about the game:
The objective of the game is to pass all 6 levels without being hit 3 times.
Throughout these levels you will encounter different types of enemies, blocks and power-ups!

## Tank Types
![tanktypes](https://github.com/dannielraposo/TankGame/assets/148542289/044dffe5-b3d7-4567-90ed-e039d13432b1)
- **Main Tank**: this is your tank. It doesn't have any special advantage but it can pick up power-ups. Your bullets (as well as the enemies' bullets) ricochet on the walls, but only the enemies' bullets will be able to hit (not your own).
- **Basic Tank**: this is the most basic tank. It doesn't move, but it will shoot you if you are in its line of sight. The tanks scans around a 90º cone looking your you.
- **Moves Tank**: this tank is able to move, and will shoot you when you are seen. They will get near you but have a "safety distance" in order to not be too close.
- **Hard Tank**: this tank will be able to take more shots than the basic and move tank.
- **Triple Tank**: this tank is the most dangerous one. It can take more hits than any other enemy. It also has increased speed and triple shot in a cone in front of him.

## Wall Types
![walls](https://github.com/dannielraposo/TankGame/assets/148542289/6054c259-9548-456d-99b3-3acb340ad494)

- **Standard Wall**: they are indestructible, they build the limits for the arena where you will be playing.
- **Weak Wall**: they can be destroyed by shooting them. Although only you will be able to destroy them (not the enemies).
- **Reward Wall**: they can be destroyed by shooting them and will drop a reward. This reward is fixed for each block and map (they are not random).

## Reward Types
![rewards](https://github.com/dannielraposo/TankGame/assets/148542289/1b09b1df-11b7-4492-beeb-31f08a1372e7)

- **Energy**: this will boost your movement and shooting speed by some time.
- **Shield**: this shield will let you take one hit without losing a heart, but it stays on for a limited time.
- **1 UP**: this reward will allow you to recover one of your hearts.
- **Ghost**: for a limited amount of time, you will be able to go through walls and the enemies won't be able to see you.
- **3-Shot**: for some time, this will allow you to have a triple shot in a cone area just in front of you.
