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
        try(RandomAccessFile f = new RandomAccessFile(fname, "rw")) {
          Header h = new Header();
          h.write(f);
          System.out.println("Created index file: " + fname);
        } catch (IOException e) {System.err.println("Something in create went wrong");}
        break;

      case "insert":
        if(args.length != 4) {System.err.println("Usage: insert <file> <key> <value>"); System.exit(1);}
        fname = args[1];
        key =  Long.parseLong(args[2]);
        val = Long.parseLong(args[3]);
        try(RandomAccessFile f = new RandomAccessFile(fname, "rw")) {
          Header h = Header.read(f);
          new BTree(f, h).insert(key, val);
        } catch (IOException e) {System.err.println("Something in insert went wrong");}
        break;

      case "search":
        if(args.length != 3) {System.err.println("Usage: search <file> <key>"); System.exit(1);}
        fname = args[1];
        key = Long.parseLong(args[2]);
        try(RandomAccessFile f = new RandomAccessFile(fname, "rw")) {
          Header h = Header.read(f);
          long[] results = new BTree(f, h).search(key);
          if (results == null) {System.out.println("Key: " + key + " not found");} else {
            System.out.println(Long.toUnsignedString(results[0] + "," + Long.toUnsignedString(results[1])));
          }
        } catch (IOException e) {System.err.println("Something in search went wrong");}
        break;

      case "load":
        if(args.length != 3) {System.err.println("Usage: load <file> <csvfile>"); System.exit(1);}
        if (!new File(csv).esists()) {System.err.println("Csv file: " + csv + " not found"); System.exit(1);}
        fname = args[1];
        csv = args[2];
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
              
            }
          }
        break;

      case "print":
        if(args.length != 2) {System.err.println("Usage: print <file>"); System.exit(1);}
        BTree.fPrint(args[1]);
        break;

      case "extract":
        if(args.length != 3) {System.err.println("Usage: extract <file> <csvfile>"); System.exit(1);}
        BTree.fExtract(args[1], args[2]);
        break;

      default:
        System.err.println("Unknown command: " + args[0]);
        System.exit(1);
    }

  }
}