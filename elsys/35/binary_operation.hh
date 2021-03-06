#ifndef BINARY_OPERATION_HH__
#define BINARY_OPERATION_HH__

#include "operation.hh"

class BinaryOperationError {};
class DivError {};


class BinaryOperation: public Operation {

protected:
	virtual double op(double a1, double a2) const = 0;

public:
	BinaryOperation(const std::string& token);
	
	void eval(Context&) const;

};


class PlusOperation: public BinaryOperation {

protected:
	double op(double a1, double a2) const;

public:
	PlusOperation();

};

class MinusOperation: public BinaryOperation {
	
protected:
	double op(double a1, double a2) const;

public:
	MinusOperation();

};

class MulOperation: public BinaryOperation {
	
protected:
	double op(double a1, double a2) const;

public:
	MulOperation();

};

class DivOperation: public BinaryOperation {

protected:
	double op(double a1, double a2) const;

public:
	DivOperation();
};

#endif
