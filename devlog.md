# Session 1: May 6th 2026 07:48
This program will be about creating and managing index files.
**Input Note:** taken in as a command-line argument, and WILL handle user input error. **NEVER HAVE MORE THAN 3 NODES IN MEMORY AT A TIME**

*Commands:*
- create: new index file, first arg after it is the name, if it already exists it fails w/ error and curr file is untouched
- insert: first arg after is index file name, if DNE or not valid, exit w/ error, else, next 2 args are the key and the value (signed int is fine in java), then insert into the B-tree
- search: first arg after is index file name, if DNE or not valid, exit w/ error, else, next arg is the key (signed int is fine in java), search index, if found print key/value pair, else, error msg
- load: first arg after is index file name, if DNE or not valid, exit w/ error, else, next arg is the csv file, if DNE, exit w/ error msg, else, each line of the file is a comma separated key/value pair. Read the file, inserting each pair as above with the insert command
- print: first arg after is index file name, if DNE or not valid, exit w/ error, else, print every key/value pair in the index to standard (console) output
- extract: first arg after is index file name, if DNE or not valid, exit w/ error, else, next arg is a filename, if DNE, exit w/ error msg, else, THE FILE SHOULD REMAIN UNMODIFIED, save every key/value pair as comma separated pairs to aforementioned file(name)

*Index File:*
- Split into blocks of 512 bytes
  - Each node holds one block (512 bytes)
- File header uses ENTIRE first block
- New nodes append
- Empty space in blocks we reamin unused (thank god for me)
- All nums stores as 8-byte ints, big endian byte order
- Each block has a block ID deter. by order in the file, starting at 0

  > *Header Format:*
- *can* be maintained in mem, by needs to be insync w/ file. Header has the following fields:
  - 8-bytes: The magic number “4348PRJ3” (as a sequence of ASCII values)
  - 8-bytes: The id of the block containing the root node. This field is zero if the tree is empty.
  - 8-bytes: The id of the next block to be added to the file. This is the next location for a new node.
  - The remaining bytes are unused.

  > *B-Tree*
- have a **minimal degree 10**. gives 19 key/val pairs, and 20 child pointers.
- Each node in a single block w/ some header info. Node fields IN ORDER:
  - 8-bytes: The block id this node is stored in.
  - 8-bytes: The block id this nodes parent is located. If this node is the root, then this field is zero.
  - 8-bytes: Number of key/value pairs currently in this node.
  - 152-bytes: A sequence of 19 64-bit keys
  - 152-bytes: A sequence of 19 64-bit values
  - 160-bytes: A sequence of 20 64-bit offsets. These block ids are the child pointers for this node.
If a child is a leaf node, the corresponding id will be zero.
  - Remaining bytes are unused.

*NOTE: sequence of keys, vals and c_pntrs correspond to one another. i.e. key 0 corr to first val, and the first c_pntr is the one containing all enteries w/ a key LESS THAN the first key, and so on (it's organized like a normal b-tree girl lol)

Choices: Im 100% doing this one in java because it's easier for this, and I have done something similar before to this in that language. It also makes it a little nicer so I don't have to think as hard about the big endian thing. Im gonna start by writing main and then probably the classes I need. I know the commands will be voids and will take up a good chunk of code, so I'm going to focus that after I write those two out.

# Session 2 May 7th 2026 17:00
I am going to try and finish my code today. I need to made the read a write for the header, and all the command functions. I'm changing my format becuase I dont like having everything in one file, so i'm separating everything, having a node, header, main, and btree file.

- @ 17:40
  in my main file i have not wrapped the main section in a try-catch block...i do not know if that will cause me problems lated in runtime but we will see. if it does i will add it in. it has caused me issues before in previous projects lol.

- @ 18:06
  i'm realizing insert is going to take a lot more thinking than i initially thought bc of the splitting once the root/node gets full....so i gotta add that too.

- @ 19:25
  decided to get it over with and did the header read and write. i had to look up a bunch of stuff becuase i never worked with random access files before, so that was fun. it was just slightly more different than writing to a normal file, but apart from that it was okay. i mainly did it because i wanted to stop writing the insert function.

- @ 23:38
  finally finished main, gonna work on the node class, and then tackle the rest of btree the best i can.
