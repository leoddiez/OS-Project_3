import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Header {
    long root;
    long nextBlock;
    static final long MAGICIAN = 0x3433343850524A33L;
    static final int BLOCK_SIZE = 512;

    public Header() {
        root = 0;
        nextBlock = 1;
    }

    static Header read(RandomAccessFile f) throws IOException {
      byte[] blk = new byte[BLOCK_SIZE];
      f.seek(0);
      f.readFully(blk);
      ByteBuffer b = ByteBuffer.wrap(blk).order(ByteOrder.BIG_ENDIAN);
      long magic_mike_yall = b.getLong();
      if (magic_mike_yall != MAGICIAN) {throw new IOException("Non-valid index file");}
      Header h = new Header();
      h.root = b.getLong();
      h.nextBlock = b.getLong();
      return h;
    }

    void write(RandomAccessFile f) throws IOException {
      byte[] blk = new byte[BLOCK_SIZE];
      ByteBuffer b = ByteBuffer.wrap(blk).order(ByteOrder.BIG_ENDIAN);
      b.putLong(MAGICIAN);
      b.putlong(root);
      b.putLong(nextBlock);
      f.seek(0);
      f.write(blk);
    }

    //i'll write read and write header soon...i dont wanna face those demons yet
  }