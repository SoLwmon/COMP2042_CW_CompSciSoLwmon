# Brick_Destroy
# Refactor 

**Ball**
1. Encapsulate the variable in this class (eg : getup(),getdown())
2. Add final to center variable
3. Change the method name of setXSpeed and setYSpeed into setSpeedX and setSpeedY

**Addition**
1. Add method changeState to change the state of the ball

**Brick**
1. Remove unused import
2. Add import.awt.geom.Ellipse2D
3. Remove variable MIN_CRACK
4. Remove Crack class from Brick class
5. Encapsulate the variable (eg : getBrickFace())
6. remove variable name and rnd from method Brick as it is unused

**Addition**
1. Add method getPowerUpEmplacement1,2,3 To the class

**Crack**
1. Move expression to a method (eg : moveLeft, moveRight)

**CementBrick**
1. Remove variable NAME

**ClayBrick**
1. Remove variable NAME
2. Change DEF_INNER, DEF_BORDER to COLOR_INNER, COLOR_BORDER

**Wall**
1. Remove unused import
2. Remove unused variable (eg : CLAY,STEEL,CEMENT)
3. Encapsulate variable

**InvisibleBrick**
1. Add invisible brick class

**Level**
1. Add method to add level

**Player**
1. remove unused import
2. Change BORDER_COLOR, INNER_COLOR to a new colour

**Resources**
1.Add resource class to change background

**RubberBall**
1. Changes to the colour variable
2. Remove 2 in the equation (radiusA/2)

**Addition**
1. Add changeColorState  method

**Sound**
1. Add sound class

**SteelBrick**
1. encapsulate variable
2. final variable rnd,brickFace
3. Change how to add colour

**DebugConsole**
1. Add import

**DebugPanel**
1. Move expression to a method (eg : AddButton(),AddSlider())

**GameBoard**
1. Add import File
2. Add variable COLOR_FIRE_1,COLOR_FIRE_2,COLOR_FIRE_3
3. Change the colour variable

**HighScoreBoard**
1. Add highScoreBoard class

**InfoBoard**
1. Add InfoBoard class

**HomeMenu**
1. Change the appearance

**GameFrame**
1. Add method (eg : enableScoreBoardFromGameBoard(),enableGameBoardFromScoreBoard)

**HighScoreHandler**
1. Add HighScoreHangler class


**Additional**
1. Arrange the class into mvc pattern (model,view,controller)
2. used Maven


