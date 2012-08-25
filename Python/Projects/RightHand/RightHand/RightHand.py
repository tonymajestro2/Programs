
import sys

def solve():
    f = open('in.txt')
    while not f.closed:
        dimensions = f.readline().rstrip().split(' ')
        width = int(dimensions[0])
        height = int(dimensions[1])
        if (width < height < 2):
            f.close()
            break

        maze = []
        entrances = []
        goal = (0,0)
        for i in range(height):
            mazeLine = f.readline().rstrip()
            maze.append(mazeLine)
            
            # find goal
            goalIndex = mazeLine.find('G')
            if (goalIndex >= 0):
                goal = (i, goalIndex)

            # find entrances
            entrance = mazeLine.find('E')
            while (entrance >= 0):
                entrances.append((i, entrance))
                entrance = mazeLine.find('E', entrance + 1)

        solveMaze(maze, entrances, goal)


def solveMaze(maze, entrances, goal):
    
    moveForwardMap = {0: lambda pos: (pos[0] - 1, pos[1]),
               1: lambda pos: (pos[0], pos[1] - 1),
               2: lambda pos: (pos[0] + 1, pos[1]),
               3: lambda pos: (pos[0], pos[1] + 1)}

    direction = 0
    solved = 0
    for pos in entrances:
        direction = getStartingDirection(pos, len(maze[0]), len(maze))
        pos = moveForwardMap[direction](pos) 

        while True:
            # check if solved maze
            if (canSeeGoal):
                solved += 1
                break

            # check if we have left the maze
            elif (pos in entrances):
                break

            # check if the next position contains a wall on the right hand side
            elif (not nextMoveHasRightWall(maze, dir, pos)):
                pos = moveForwardMap[direction](pos)
                direction   


def getStartingDirection(pos, width, height):
    if (pos[0] == height - 1):
        return 0
    if (pos[0] == 0):
        return 2
    if (pos[1] == width - 1):
        return 1
    if (pos[1] == 0):
        return 3
        



if (__name__ == "__main__"):
    solve()
            

