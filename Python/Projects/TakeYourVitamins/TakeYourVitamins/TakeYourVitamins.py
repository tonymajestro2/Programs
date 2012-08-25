class Vitamin:
    '''Class represnting a daily Vitamin'''

    def __init__(self, amount, units, dailyTotal, name):
        self.amount = amount
        self.units = units
        self.dailyTotal = dailyTotal
        self.name = name
        self._percentage = self.amount / self.dailyTotal

    def __str__(self):
        return "{0} {1:.1f} {2} {3:.0f}%".format(self.name, self.amount, self.units, self._percentage * 100)

    def containsEnough(self):
        return self._percentage > 0.01


if (__name__ == "__main__"):
    import sys
    significantAmountList = []
    insignificantAmountList = []

    lines = sys.stdin.read().split('\n')
    for line in lines:
        splitLine = line.split(' ')
        amount = float(splitLine[0])
        unit = splitLine[1]
        total = float(splitLine[2])
        name = " ".join(splitLine[3:])

        if (amount < 0):
            break

        vitamin = Vitamin(amount, unit, total, name)
        if (vitamin.containsEnough()):
            significantAmountList.append(vitamin)
        else:
            insignificantAmountList.append(vitamin)

    for significantVitamin in significantAmountList:
          print significantVitamin
    
    print 'Provides no significant amount of:'
    for insignificantVitamin in insignificantAmountList:
        print insignificantVitamin.name
