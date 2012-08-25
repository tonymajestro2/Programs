import functools
import sys

@functools.total_ordering
class OlympicTeam:
    def __init__(self, country, gold, silver, bronze, total):
        self.country = country
        self.gold = gold
        self.silver = silver
        self.bronze = bronze
        self.total = total

    def __gt__(self, other):
        return (self.gold >= other.gold) and \
               (self.gold + self.silver > other.gold + other.silver) and \
               (self.total >= other.total)
     
    def __eq__ (self, other):
        selfAsGood = self > other 
        otherAsGood = other > self
        return selfAsGood == otherAsGood

    def __str__(self):
        return " ".join([self.country, 
                         str(self.gold), 
                         str(self.silver), 
                         str(self.bronze), 
                         str(self.total)])


def solve():
    countries = []
    for line in open('medals.csv'):
        split = line.split(',')
        countries.append(OlympicTeam(split[0], 
                                     int(split[1]), 
                                     int(split[2]), 
                                     int(split[3]), 
                                     int(split[4])))

    countries.sort(reverse = True)
    for country in countries:
        print(country)

if (__name__ == "__main__"):
    solve()
