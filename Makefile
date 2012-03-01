# Simple Makefile for unix.
#


all: run-package

package:
	mvn package

dist:
	mkdir -p dist/
	cp $(HOME)/.m2/repository/com/google/code/gson/gson/2.1/gson-2.1.jar \
		terentatek/target/terentatek-0.0.1-SNAPSHOT.jar \
		sncli/target/sncli-0.0.1-SNAPSHOT.jar \
		dist/

# don't build, just run
run:
	(cd dist/; java -ea -jar sncli-0.0.1-SNAPSHOT.jar $(ARGS))

# full build->run
run-package: package dist run

clean:
	mvn clean
	rm -rf dist/*

test:
	mvn test

.PHONY: package dist run run-package clean test
