


da_map = dict()
eb_map = dict()
fc_map = dict()

for message in open('in.txt').readlines():
    da_map[message[0]] = message[3]
    eb_map[message[1]] = message[4]
    fc_map[message[2]] = message[5]




if (__name__ == '__main__'):
    print 'hello'