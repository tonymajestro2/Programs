import re
import sys

def solve():
    for line in sys.stdin:
        splitLine = line.split(' ', 2)
        B = int(splitLine[0])
        E = int(splitLine[1])
        if (B == E == -1)   :
            break
    
        text = splitLine[2]

        textToCopy = text[B:E]
        flattenedLeftTags = flatten(text[:B])
        flattenedRightTags = flatten(text[E:])

        print flattenedLeftTags + textToCopy + flattenedRightTags


def flatten(html):
        
    def isClosingMostRecentTag(tag, tagStack):
        # Check if there are any tags
        if (len(tagStack) == 0):
            return False
     
        # Check if top of tagStack is an opening tag and new tag is closing
        # Name checking is unimportant since HTML is well formed 
        topTag = tagStack[-1]
        return (topTag[0] == '') and (tag[0] == '/')
    

    tagStack = []

    # each tuple is in form ('' or '/', string tag name)
    tagTuples = re.findall(r'<([/]?)([a-zA-Z0-9-]+)>', html)
    
    for tagTuple in tagTuples:
        if (isClosingMostRecentTag(tagTuple, tagStack)): 
            tagStack.pop()
        else:
            tagStack.append(tagTuple)

    return ''.join(['<' + tag[0] + tag[1] + '>' for tag in tagStack])


if (__name__ == "__main__"):
    solve()

