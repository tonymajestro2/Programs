def solve():
    with open('in.txt') as f:
        splitLine = f.readline().split(' ')
        width, height = map(int, splitLine)
        moves = splitLine[2]

    grid = list([[' '] * width for x in range(height)])
    x = y = 0

    for move in moves:
        if (move == 'P'): grid[x][y] = 'X'
        elif (move == 'E'): y += 1
        elif (move == 'W'): y -= 1
        elif (move == 'N'): x -= 1
        elif (move == 'S'): x += 1

    print '\n'.join([' '.join(line) for line in grid])
    raw_input()

if (__name__ == "__main__"):
    solve()


