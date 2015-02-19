#ifndef _UNARY_OPERATION_HH__
#define _UNARY_OPERATION_HH__

#include "operation.hh"

class UnaryOperationError {};

class UnaryOperation: public Operation {

protected:
	virtual double op(double val) const = 0;

public:
	UnaryOperation(const std::string& token);
	
	void eval(Context&) const;
	
};

class NegateOperation: public UnaryOperation {

protected:
	double op(double val) const;

public:
	NegateOperation();

};

class SqOperation: public UnaryOperation {

protected:
	double op(double val) const;

public:
	SqOperation();
};

#endif 
