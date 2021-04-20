There are several test documents used for testing:
assignment1.legv8asm,
proj2Test.legv8asm,
proj2Test2.legv8asm

The machine codes for all of these are already available with the suffix ".machine"

To begin testing first make sure that legv8emul is executable.

Make legv8emul executable by "chmod +x legv8emul" and pressing enter in the terminal if its not already.

To build the (disassembler) application open the terminal and type:

->  "sh build.sh"
->  press Enter

To run the disassembler on a test file, you'll need to convert the test file into machine code - binary file (file with .machine ending):

-> "./legv8emul <test_file_name> -a"
->  press Enter
NOTE: It is assumed here that the test_file is in the same folder as the emulator and the disassembler

To run the disassembler on the machine code (binary file) enter in the terminal:

->  "sh run.sh <test_file_name.machine>"
->  press Enter

The terminal output can now be compared with the original test file.


If instead you would like to save the disassembler output in a file, enter in the terminal:

->  "sh run.sh test_file_name.machine > filename.legv8asm" -> then press Enter


Compare the test_file_name with filename.legv8 for correctness


Logic for the disassembler code:
The disassembler.java goes through the disassembly process in four parts:
1/ First we convert a binary file into arraylist of string of 0s and 1s - each containing 32 characters
2/ Second, we go over each instruction and parse it into different arrays - representing values of different registers etc.
3/ Next, we go over all the instructions again and look for instances where labels are called and create an array list of all the unique places where labels can be placed
4/ Finally, we print correctly formatted assembly code - that can be easily run on legv8emul or converted to machine code to check for correctness
