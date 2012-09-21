import string

class Node:
    def __init__(self, c, children):
        self.c = c
        self.children = children
       
       
n3 = Node('3', [])
nC = Node('C', [n3])
n2 = Node('2', [])
n6 = Node('6', [n2])
n7 = Node('7', [nC, n6])

n1 = Node('1', [])
n4 = Node('4', [n1])

nB = Node('B', [])
n5 = Node('5', [])
n9 = Node('9', [nB, n5])
nE = Node('E', [n9])

n0 = Node('0', [])
nF = Node('F', [n0])
n8 = Node('8', [nF])
nD = Node('D', [n8])

root = Node('A', [n7, n4, nE, nD])


def traverse(node, arr):
    arr.append(node.c)
    
    node.children.sort(key=sortKey)
    for child in node.children:
        traverse(child, arr)

def sortKey(val):
    if (val.c in string.digits):
        return string.digits.find(val.c) + 26
    else:
        return string.uppercase.find(val.c)
    
arr = []
traverse(root, arr)

print ''.join(arr)    