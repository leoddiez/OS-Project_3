# Files
- **Main/P3:** Driver program, this is what takes in and processes the command arg line and properly sends it to it's prompted function. All input is convert to lowercase, and the arg[0] (the command) is ran based off a switch case.
- **Header:** Contains a read and write function. The read function reads the entire block, and checks if the file starts/contains the magic header (if it doesnt it's invalid), and it continues to read the rest of the block, assigning bytes.
            The write function is similar except it is assigning the header to the file
- **Node:** Holds the key,val pair and the parent child nodes that go into the btree. It has a constructor, and two functions to check if the max keys have been reached, and if something is a child node. The other two functions are to and from block.
          toblock converts the node into a 512 byte array (perserving order) so it can be written into the file, fromblock does the exact opposite, making it a node again
- **BTree:** The main chunk of code, contains a read and write node function, where read calculates the blocks postion and calls to retrieve it, and write does the opposite. The search function recursivly searches for the node given a key, insert recursively looks for a place to
           insert the node into the tree, being mindful or the max keys/children, if the root becomes full the function split gets called, which searches for a node to split the tree on, and goes on into middle insert function, which checks if something is a leaf/child or parent
           ,and inserts from there. SPlit is not recursive (technically) but it loops in a sense becuase it can be passed between itself and middle insert until all splitting is done.
           The inorder function is basically DFS algorithm
           KV interface is so that I could use lambda for the (k,v) and not hardcode anything when searching .idx or .csv for the points/nodes.

# Program Execution
Run:
javac P3.java Header.java Node.java BTree.java
java P3 <command> <args>
