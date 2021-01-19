all:
	find -name "*.java" > sources.txt
	mkdir build
	javac @sources.txt -d build
	cp -r resources build

run:
	cd build && java ru/ifmo/rain/naumov/Main

clean:
	rm -rf build
