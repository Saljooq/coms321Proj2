There are several test documents used for testing: assignment1.machine, proj2Test.legv8asm, proj2Test2.legv8asm

To build the application open the terminal and type:

->  "sh build.sh"

press Enter.

Make legv8emul executable by "chmod +x legv8emul" if its not already.

To run the disassembler on a test file first create the assembled binary (file with .machine ending):

-> ./legv8emul <test file name> -a

To run the disassembler on the assembled binary enter in the terminal:

-> "sh run.sh <test file .machine>"

The terminal output can now be compared with the original test file.


If instead you would like to save the disassembler output in a file follow the instructions below.

To run the disassembler on our test file "assignment1" and save it in "filename" enter in the terminal:

->  "sh run.sh assignment1.machine > filename" -> then press Enter

Testing:

run the emulator with our "filename" by typing in terminal:

->  "./legv8emul filename -a" -> press Enter

this will create a copy "filename.machine"

now we disassemble it and save it in "filename.copy" by typing in terminal:

->  "sh run.sh filename.machine > filename.copy" -> press Enter

to check the difference between the two type:

->  "diff filename filename.copy"  -> press Enter

No result means they're exactly the same so the test has been successful.
