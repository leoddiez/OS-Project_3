public class Node {
    long bID;
    long pID;
    long pairs;
    long[] keys = new long[MAX_KEYS];
    long[] val = new long[MAX_KEYS];
    long[] child = new long[MAX_CHILD];

    public Node(long bID) {
      this.bID = bID;
      this.pID = 0;
      this.pairs = 0;
    }

    public boolean isFull() { return pairs == 19;}
    public boolean isLeaf() { return chil[0] == 0;}

  }