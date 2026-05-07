
  public class Header {
    long root;
    long nextBlock;
    byte[] MAGICIAN = "4348PRJ3";

    public Header() {
        root = 0;
        nextBlock = 1;
    }
  }