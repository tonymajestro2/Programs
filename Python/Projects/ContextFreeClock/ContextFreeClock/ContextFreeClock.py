
import sys

DEGREES_PER_SECOND = 360.0/43200.0
DEGREES_PER_MINUTE = 0.5
DEGREES_PER_HOUR = 30.0
SECONDS_PER_HALF_DAY = 60 * 60 * 12
INCREASE_ANGLE_PER_SECOND = (1.0/600) - (1.0/36000)


def solve():
    for line in open("in.txt"):
        splitLine = line.rstrip().split(' ')
        angle, timeStr = int(splitLine[0]), splitLine[1]
        splitTimeStr = timeStr.split(':')

        if (angle == -1):
            break

        h,m,s = int(splitTimeStr[0]), int(splitTimeStr[1]), int(splitTimeStr[2])
        currentAngle = 0

        secondsLeft = angle / INCREASE_ANGLE_PER_SECOND
        print convertNewTime(h, m, s, secondsLeft)


def convertNewTime(h, m, s, secondsLeft):
    s += secondsLeft;
    m += s / 60
    s = int(s % 60)

    h += m % 60
    m = int(m % 60)

    h = int(h % 24)

    return "{0}:{1}:{2}".format(str(h).zfill(2), str(m).zfill(2), str(s).zfill(2))
             


if (__name__ == "__main__"):
    solve()

