

typeMap = {'time': lambda x,y: x.time,
           'distance': lambda x,y: x.distance,
           'turns': lambda x,y: 1 if x.name != y.name else 0}



class City:
    '''Represents a city with a list of roads traveling through the city'''
    def __init__(self, name):
        self.roads = []
        self.name = name
        self.correctRoad = ''
  

class Road:
    '''Represents a road passing through a city'''
    def __init__(self, name, distance, time, destination):
        self.name = name
        self.distance = distance
        self.time = time
        self.destination = destination


def solve():
    f = open('in.txt')
    numCities = int(f.readline())
    citySet = {}
    for i in range(numCities):
        cityName = f.readline().rstrip()
        citySet[cityName] = City(cityName)

    numRoads = int(f.readline())
    for i in range(numRoads):
        splitLine = f.readline().rstrip().split(' ')
        roadName = splitLine[0]
        length = len(splitLine)
        numCitiesPassed = (length - 1) / 3 + 1
        for i in range(numCitiesPassed - 1):
            distance = float(splitLine[i*3 + 2])
            time = float(splitLine[i*3 + 3])

            firstCity = citySet[splitLine[i*3 + 1]]
            secondCity = citySet[splitLine[i*3 + 4]]

            firstCity.roads.append(Road(roadName, distance, time, secondCity.name))
            secondCity.roads.append(Road(roadName, distance, time, firstCity.name))
            
    typeLine = f.readline().rstrip().split(' ')
    type, departureCity, arrivalCity = typeLine[0], typeLine[1], typeLine[2]

    while (type != ''):
        answer = solveType(citySet, departureCity, arrivalCity, type)
        print 'from {0}'.format((answer[1])[0].name)
        for city in (answer[1])[1:]:
            roadName = city.correctRoad.name
            cityName = city.name
            print '{0} to {1}'.format(roadName, cityName)

        type = f.readline().rstrip()

    
def solveType(citySet, dep, arr, type, prevRoad = None, count = 0, visited = []):
    
    depCity = citySet[dep]
    if (dep == arr):
        depCity.correctRoad = prevRoad
        visited.append(depCity)
        return (count, visited)

    if (depCity in visited):
        return (-1, visited)

    visited.append(depCity)

    minInfo = (-1, [])
    for road in depCity.roads:
        newVisited = []
        newVisited.extend(visited)  

        newCount = typeMap[type](road, prevRoad)

        travelInfo = solveType(citySet, road.destination, arr, type, road, newCount, newVisited)
        if ((travelInfo[0] > 0) and ((travelInfo[0] < minInfo[0]) or (minInfo[0] < 0))):
            minInfo = travelInfo
            depCity.correctRoad = prevRoad

    if (minInfo[0] < 0):
        visited.pop()
    return minInfo



if (__name__ == "__main__"):
    solve() 

            
             
            