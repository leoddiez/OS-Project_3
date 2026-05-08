import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Node {
    static final int MIN_DEGREE = 10;
    static final int MAX_KEYS = 19;
    static final int MAX_CHILD = 20;
    
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

    public boolean isFull() { return pairs == MAX_KEYS;}
    public boolean isLeaf() { return child[0] == 0;}

    static Node fBlk(byte[] block) {
      ByteBuffer b = ByteBuffer.wrap(block).order(ByteOrder.BIG_ENDIAN);
      long bID = b.getLong();
      Node n = new Node(bID);
      n.pID = b.getLong();
      n.pairs = b.getLong();
      for (int j  = 0; j < MAX_KEYS; j++) { n.keys[j] = b.getLong();}
      for (int j  = 0; j < MAX_KEYS; j++) { n.val[j] = b.getLong();}
      for (int j  = 0; j < MAX_CHILD; j++) { n.child[j] = b.getLong();}
      return n;
    }

    byte[] tBlk() {
      byte[] block = new byte[Header.BLOCK_SIZE];
      ByteBuffer b = ByteBuffer.wrap(block).order(ByteOrder.BIG_ENDIAN);
      b.putLong(bID);
      b.putLong(pID);
      b.putLong(pairs);
      for (int j  = 0; j < MAX_KEYS; j++) { b.putLong(bID);}
      for (int j  = 0; j < MAX_KEYS; j++) { b.putLong(pID);}
      for (int j  = 0; j < MAX_CHILD; j++) { b.putLong(pairs);}
      return block;
    }

  }