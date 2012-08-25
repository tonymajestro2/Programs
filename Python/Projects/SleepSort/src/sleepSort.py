'''
Created on Aug 25, 2012

@author: tony
'''

import random
import threading
import time
import sys

class SleepSortThread(threading.Thread):
    '''Sleep for n seconds, then print n'''
    def __init__(self, n):
        self.n = n
        threading.Thread.__init__(self)
    
    def run(self):
        time.sleep(self.n)
        print(str(self.n))
    
    
if __name__ == '__main__':
    if (len(sys.argv) <= 1):
        nums = set([random.randint(1, 15) for x in range(15)])
    else:
        nums = [int(x) for x in sys.argv[1:]]
    
    for num in nums:
        SleepSortThread(num).start()
