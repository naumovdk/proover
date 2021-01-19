all:
	find -name "*.java" > sources.txt
	mkdir build
	javac @sources.txt -d build
clean:
	rm -rf build
run:
	cd build && java ru/ifmo/rain/naumov/Main