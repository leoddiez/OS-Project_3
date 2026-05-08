import java.io.*;
import java.nio.*;
import java.nio.file.*;

public class BTree {
  private final RandomAccessFile f;
  private final Header h;

  BTree(RandomAccessFile f, Header h) {
    this.f = f;
    this.h = h;
  }

  long alloB() throws IOException {
    long ID = h.nextBlock++;
    h.write(f);
    return ID;
  }

  Node rNode(long ID) throws IOException {
    byte[] b = new byte[Header.BLOCK_SIZE];
    f.seek(ID * Header.BLOCK_SIZE);
    f.readFully(b);
    return Node.fBlk(b);
  }

  void wNode(Node n) throws IOException {
    f.seek(n.bID * Header.BLOCK_SIZE);
    f.write(n.tBlk());
  }

  public long[] search(long key) throws IOException { //helper function <3
    if (h.root == 0) {return null;}
    return search(h.root, key);
  }

  long[] search(long nID, long k) throws IOException {
    Node n = rNode(nID);
    while(i < n.pairs && k > n.keys[i]) {i++;}
    if (i < n.pairs && k == n.keys[i]) {return new long[] {n.keys[i], n.val[i]};} 
    if (n.isLeaf()) {return null;}
    long cID = n.child[i]; 
    n = null;
    return search(cID, k);
  }

  public void insert(long key, long val) throws IOException {
    if (h.root == 0) {
        Node root = new Node(alloB());
        h.root = root.bID;
        root.key[0] = key;
        root.val[0] = val;
        root.pairs = 1;
        wNode(root);
        h.write(f);
        file.close();

        return;
    }

    Node root = rNode(h.root);               // I have all of this drawn out on my ipad lol. there is a specific order
    if (root.isFull()) {
        Node nRoot = new Node(alloB());
        h.root = nRoot.bID;
        nRoot.child[0] = root.bID;
        root.pID = nRoot.bID;

        wNode(root);
        split(root, nRoot, 0);
        insertMid(nRoot, key, val);
        wNode(nRoot);

    } else {
      //root = null;? dunno if needed
      insertMid(root, key, val);
    }
    h.write(f);
  }

  void insertMid(Node n, long k, long v) throws IOException {
    int i = (int) n.pairs - 1;
    if (n.isLeaf()) { 
      for( ; i >= 0 && k < n.keys[i]; i--) {
        n.keys[i + 1] = n.keys[i];
        n.val[i + 1] = n.val[i];
      }

      if(i >= 0 && n.keys[i] == k) {
        System.err.println("Error: key already exists");
        return;
      }
      n.keys[i + 1] = k;
      n.val[i + 1] = v;
      n.pairs++;
      wNode(n);

    } else {
      while (i >= 0 && k < n.keys[i]) {i--;}
      if(i >= 0 && n.keys[i] == k) {
        System.err.println("Error: key already exists");
        return;
      }
      i++;
      Node c = rNode(n.c[j]);
      if (c.isFull()) {
        split(c, n, i);
        if (k > n.keys[i]) {i++;}
        else if (k == n.keys[i]) {
          System.err.println("Error: key already exists");
          return;
        }
      c = null;
      c = rNode(n.c[i]);
      }
      n = null;
      insertMid(c, k, v);
    }
  }

  void split(Node c, Node p, int idx) throws IOException { // FAAAHHHHHHH
    return;
  }

  public interface KV { void visit(long k, long v) throws IOException; }

  public void inorder(KV point) throws IOException { if(h.root != 0) {inorder(h.root, point);}}

  void inorder(long nID, KV point) throws IOException {
    Node n = rNode(nID);
    long[] keys = n.keys.clone();
    long[] val = n.val.clone();
    long[] cID = n.child.clone();
    long pairs = n.pairs;
    n = null;

    for(int i = 0; i < pairs; i++) {
      if (cID[i] != 0) {inorder(cID[i], point);}
      point.visit(keys[i], val[i]);
    }

    if(cID[(int) pairs != 0]) {inorder(cID[(int) pairs], point);}

  }
}