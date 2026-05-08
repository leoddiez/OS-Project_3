import java.io.*;
import java.nio.*;
import java.nio.file.*;

public class BTree {
  byte[] MAGICIAN = "4348PRJ3";

  public static void fCreates(String fname) throws IOException {
    File f = new File(fname);
    if(f.exists()) {throw new RuntimeException("The file already exists");}

    RandomAccessFile file = new RandomAccessFile(f, "rw");
    Header h = new Header();
    write(file,h);

    file.close();
  }

  public static void fInsert(String fname, long key, long val) throws IOException {
    RandomAccessFile f = getIndex(fname);
    Header h = readHead(f);

    if(h.root == 0) {
        Node root = new Node(h.nextBlock);
        h.root = h.nextBlock;
        h.nextBlock++;

        root.key[0] = key;
        root.val[0] = val;
        root.pairs = 1;

        wNode(f, root);
        write(f, h);
        file.close();

        return;
    }

    Node root = rNode(f, h.root);               // I have all of this drawn out on my ipad lol. there is a specific order
    if(root.isFull()) {
        Node nRoot = new Node(h.nextBlock++);
        h.root = nRoot.bID;
        nRoot.child[0] = root.bID;
        root.pID = nRoot.bID;

        wNode(f, root);
        split(root, nRoot, 0, f, h);
        insertMid(nRoot, key, val, f, h);
        wNode(f, nRoot);
        write(f, h);

    } else {
      root = null;
      insertMid(root, key, val, f, h);
      write(f, h);
    }
    file.close();
  }

  static void insertMid(Node node, long key, long val, RandomAccessFile f, Header h) throws IOException {
    int j = (int) node.pairs - 1;
    if (node.isLeaf() { // hnggggggggggggggggggggggggggggg do you guys look at the code lol
 
    })
  }
}