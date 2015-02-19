all: run

operation.o: operation.cc operation.hh
context.o: context.cc context.hh
binary_operation.o: binary_operation.cc binary_operation.hh operation.hh context.hh
postfix.o: postfix.cc postfix.hh context.hh operation.hh
main.o: main.cc postfix.hh context.hh binary_operation.hh
unary_operation.o: unary_operation.cc unary_operation.hh operation.hh context.hh

run: operation.o context.o binary_operation.o postfix.o unary_operation.o main.o
	g++ operation.o context.o binary_operation.o unary_operation.o postfix.o main.o -o run


clean:
	rm -f *~ *.o run
	
