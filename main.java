import java.io.*;
import java.nio.*;
import java.nio.file.*;

public class P3 {

  public static void main(String[] args) {
    String fname, csv;
    long key, val;
    if(args.length < 1) {
      System.err.println("Unknown command");
      System.exit(1);
    }
    switch (args[0].toLowerCase()) { // I have no idea if this is allowed yet, or if they use capital letters i reject, but imma just convert for my sanity
      case "create":
        if(args.length != 2) {System.err.println("Usage: create <file>"); System.exit(1);}
        fname = args[1];
        if (!new File(fname).exists()) {System.err.println("File: " + fname + " not found/valid"); System.exit(1);}
        try(RandomAccessFile f = new RandomAccessFile(fname, "rw")) {
          Header h = new Header();
          h.write(f);
          System.out.println("Created index file: " + fname);
        } catch (IOException e) {System.err.println("Erorr in create: " + e.getMessage()); System.exit(1);}
        break;

      case "insert":
        if(args.length != 4) {System.err.println("Usage: insert <file> <key> <value>"); System.exit(1);}
        fname = args[1];
        if (!new File(fname).exists()) {System.err.println("File: " + fname + " not found/valid"); System.exit(1);}
        key =  Long.parseUnsignedLong(args[2]);
        val = Long.parseUnsignedLong(args[3]);
        try(RandomAccessFile f = new RandomAccessFile(fname, "rw")) {
          Header h = Header.read(f);
          new BTree(f, h).insert(key, val);
        } catch (IOException e) {System.err.println("Erorr in insert: " + e.getMessage()); System.exit(1);}
        break;

      case "search":
        if(args.length != 3) {System.err.println("Usage: search <file> <key>"); System.exit(1);}
        fname = args[1];
        if (!new File(fname).exists()) {System.err.println("File: " + fname + " not found/valid"); System.exit(1);}
        key = Long.parseUnsignedLong(args[2]);
        try(RandomAccessFile f = new RandomAccessFile(fname, "rw")) {
          Header h = Header.read(f);
          long[] results = new BTree(f, h).search(key);
          if (results == null) {
            System.out.println("Key: " + Long.toUnsignedString(key) + " not found/valid");
          } else {
            System.out.println(Long.toUnsignedString(results[0]) + "," + Long.toUnsignedString(results[1]));
          }
        } catch (IOException e) {System.err.println("Erorr in search: " + e.getMessage()); System.exit(1);}
        break;

      case "load":
        if(args.length != 3) {System.err.println("Usage: load <file> <csvfile>"); System.exit(1);}
        fname = args[1];
        if (!new File(fname).exists()) {System.err.println("File: " + fname + " not found/valid"); System.exit(1);}
        csv = args[2];
        if (!new File(csv).exists()) {System.err.println("Csv file: " + csv + " not found/valid"); System.exit(1);}
        try(RandomAccessFile f = new RandomAccessFile(fname, "rw");
          BufferedReader br = new BufferedReader(new FileReader(csv))) {
            Header h = Header.read(f);
            BTree tree = new BTree(f, h);
            int cnt = 0;
            String parse;
            while((parse = br.readLine()) != null) {
              cnt++;
              parse = parse.trim();
              if (parse.isEmpty()) {continue;}
              String[] parts = parse.split(",");
              if(parts.length <2) {System.err.println("Warning: Skipping line " + cnt); continue;}
              try {
                tree.insert(Long.parseUnsignedLong(parts[0].trim()), Long.parseUnsignedLong(parts[1].trim()));
              } catch (NumberFormatException e) {System.err.println("Warning: Line skipped with bad numbers " + cnt);}
            }
            System.out.println(csv + " loaded");
          } catch (IOException e) {System.err.println("Erorr in load: " + e.getMessage()); System.exit(1);}
        break;

      case "print":
        if(args.length != 2) {System.err.println("Usage: print <file>"); System.exit(1);}
        fname = args[1];
        if (!new File(fname).exists()) {System.err.println("File: " + fname + " not found/valid"); System.exit(1);}
        try(RandomAccessFile f = new RandomAccessFile(fname, "rw")) {
          Header h = Header.read(f);
          if(h.root == 0) {System.out.println("...aint nuthin in the index bro..."); break;}
          BTree tree = new BTree(f, h);
          tree.inorder((k, v) -> {
            System.out.println(Long.parseUnsignedLong(k) + "," + Long.parseUnsignedLong(v));
          });
        } catch (IOException e) {System.err.println("Erorr in print: " + e.getMessage()); System.exit(1);}
        break;

      case "extract":
        if(args.length != 3) {System.err.println("Usage: extract <file> <csvfile>"); System.exit(1);}
        fname = args[1];
        if (!new File(fname).exists()) {System.err.println("File: " + fname + " not found/valid"); System.exit(1);}
        csv = args[2];
        if (!new File(csv).exists()) {System.err.println("File: " + csv + " not found/valid"); System.exit(1);}        
        try(RandomAccessFile f = new RandomAccessFile(fname, "rw")) {
          Header h = Header.read(f);
          BTree tree = new BTree(f, h);
          PrintWriter pw = new PrintWriter(new FileWriter(csv));
          tree.inorder((k, v) -> pw.println(Long.toUnsignedString(k) + "," + Long.toUnsignedString(v)));
          System.out.println(csv + "... extracted to this location");
        } catch (IOException e) {System.err.println("Erorr in extract: " + e.getMessage()); System.exit(1);}
        break;

      default:
        System.err.println("Unknown command: " + args[0]);
        System.exit(1);
    }

  }
}