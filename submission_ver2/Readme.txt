There is one test document used for testing: assignment1.machine

To build the application open the terminal and type:

->  "sh build.sh"

press Enter.

To run the disassembler on our test file "assignment1" and save it in "filename" enter in the terminal:

->  "sh run.sh assignment1.machine > filename" -> then press Enter

Testing:

Make legv8emul executable by "chmod +x legv8emul" if its not already.

run the emulator with our "filename" by typing in terminal:

->  "./legv8emul filename -a" -> press Enter

this will create a copy "filename.machine"

now we disassemble it and save it in "filename.copy" by typing in terminal:

->  "sh run.sh filename.machine > filename.copy" -> press Enter

to check the difference between the two type:

->  "diff filename filename.copy"  -> press Enter

No result means they're exactly the same so the test has been successful.
