
# Ciel Park
# ccp7gcp
# 10-18-21
# Makefile

#CXXFLAGS tells the compiler which flags to compile with (-Wall, -g, -o, etc.)
CXXFLAGS = -Wall -O2

# CXX tells your computer which compiler to use, this is typically just gcc or clang
CXX = clang++

# You can make any variable you'd like in a Makefile, so we typically make one called OFILES, which will have the name of every cpp file required to compile your code. HOWEVER, the extension will NOT be cpp, it will be o. For example, TreeCalcTest.o instead of TreeCalcTest.cpp
OFILES = wordPuzzle.o timer.o hashTable.o

# Suffix rules just explain how to convert from one filetype to another. For this class it will always be .cpp to .o.
.SUFFIXES: .o .cpp

# Think of Makefiles as a list of targets, each target tells the computer to run some set of commands. To differentiate between targets, we give each one a name. Since these targets are not typically the names of actual files, we tell Make this by using PHONY (the targets are "phony" in this sense).
.PHONY: main clean

# This is a target, it has a name (main), and a list of files that it depends on (OFILES in this case). To access the variable OFILES, we must wrap it in parenthesis and put a $ at the front. 
# If any of the OFILES change, every command within this target will run. In this case, it will just compile our programs
main: $(OFILES)
	$(CXX) $(CXXFLAGS) $(OFILES)

# This target does not depend on any files. When it is run, it deletes all files used in compilation (.o files and a.out)
clean:
	-rm *.o a.out

# The remainder of the cpp file can be used to tell make how your cpp files are dependent on one another. If a cpp file changes, this dependency list will tell make which files need to be recompiled as a result. This way you aren't recompiling every single cpp file whenever you run clang. You generate this list by running clang++ -MM *.cpp, then copying it below.

getWordInGrid.o: getWordInGrid.cpp
hashTable.o: hashTable.cpp hashTable.h
primenumber.o: primenumber.cpp
timer.o: timer.cpp timer.h
timer_test.o: timer_test.cpp timer.h
wordPuzzle.o: wordPuzzle.cpp hashTable.h timer.h
