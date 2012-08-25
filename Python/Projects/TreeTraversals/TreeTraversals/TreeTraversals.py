
class Tree:
    """This is a class representing a binary tree"""

    def __init__(self, data, left = None, right = None):
        self.left = left
        self.right = right
        self.data = data

    def printInOrder(self):
        if (self.left != None):
            self.left.printInOrder()

        print self.data

        if (self.right != None):
            self.right.printInOrder()

    
if __name__ == "__main__":
    
    root = Tree(15,
                Tree(10,
                     Tree(5),
                     Tree(12)),
                Tree(20,
                     Tree(17),
                     Tree(22)))
    

    root.printInOrder()

    raw_input()