# Simple Makefile for unix.
#

MVN = mvn $(mvnflags)
skiptests ?= false
# mvnflags = 
mvnflags = -Dmaven.test.skip=$(skiptests) -q
ARGS=debug

all: run-package

package:
	$(MVN) package

dist:
	mkdir -p dist/
	cp $(HOME)/.m2/repository/com/google/code/gson/gson/2.1/gson-2.1.jar \
		$(HOME)/.m2/repository/net/iharder/base64/2.3.8/base64-2.3.8.jar \
		terentatek/target/terentatek-0.0.1-SNAPSHOT.jar \
		sncli/target/sncli-0.0.1-SNAPSHOT.jar \
		dist/

# don't build, just run
run:
	(cd dist/; java -ea -jar sncli-0.0.1-SNAPSHOT.jar $(ARGS))

# full build->run
run-package: package dist run

clean:
	$(MVN) clean
	rm -rf dist/*

test:
	$(MVN) test

compile:
	$(MVN) compile

.PHONY: package dist run run-package clean test
