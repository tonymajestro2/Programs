
# Cycle decomposition maps and message list
da_map = dict()
eb_map = dict()
fc_map = dict()
messages = []

# For each message, map the 1st letter to the 4th, the 2nd
# letter to the 5th, and the 3rd letter to the 6th.
for message in open('in.txt').readlines():
    da_map[message[0]] = message[3]
    eb_map[message[1]] = message[4]
    fc_map[message[2]] = message[5]
    messages.append(message)
   

def getIncorrectMessage(cycleMap, offset):
    # Get the expected key and value for each message
    for message in messages:
        key = message[offset]
        value = message[offset + 3]
        
        # If the key's value is not equal to the value stored
        # in the cycle map, then the value is incorrect 
        if (cycleMap[key] != value):
            # Replace incorrect value with correct value from cycle map
            correctedMessage = message.replace(value, cycleMap[key])
            
            # Print incorrect and correct letter and message
            print 'Incorrect letter: ' + value
            print 'Incorrect message: ' + message
            print 'Corrected letter: ' + cycleMap[key]
            print 'Corrected message: ' + correctedMessage
            
# Check messages using each cycle map
getIncorrectMessage(da_map, 0)
getIncorrectMessage(eb_map, 1)
getIncorrectMessage(fc_map, 2)
