
with open("testFile.txt") as f:
    contents = []
    for line in f:
        for word in line.rstrip().split(' '):
            print word


