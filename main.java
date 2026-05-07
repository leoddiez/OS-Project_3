import java.io.*;
import java.nio.*;
import java.nio.file.*;

public class P3 {

  public static void main(String[] args) {
    if(args.length < 1) {
      System.err.println("Unknown command");
      System.exit(1);
    }
    switch (args[0].toLowerCase()) { // I have no idea if this is allowed yet, or if they use capital letters i reject, but imma just convert for my sanity
      case "create":
        if(args.length < 2) {System.err.println("Usage: create <file>"); System.exit(1);}
        fCreate(args[1]);
        break;

      case "insert":
        if(args.length < 4) {System.err.println("Usage: insert <file> <key> <value>"); System.exit(1);}
        fInsert(args[1], args[2], args[3]);
        break;

      case "search":
        if(args.length < 3) {System.err.println("Usage: search <file> <key>"); System.exit(1);}
        fSearch(args[1], args[2]);
        break;

      case "load":
        if(args.length < 3) {System.err.println("Usage: load <file> <csvfile>"); System.exit(1);}
        fLoad(args[1], args[2]);
        break;

      case "print":
        if(args.length < 2) {System.err.println("Usage: print <file>"); System.exit(1);}
        fPrint(args[1]);
        break;

      case "extract":
        if(args.length < 3) {System.err.println("Usage: extract <file> <csvfile>"); System.exit(1);}
        fExtract(args[1], args[2]);
        break;

      default:
        System.err.println("Unknown command: " + args[0]);
        System.exit(1);
    }

  }
}