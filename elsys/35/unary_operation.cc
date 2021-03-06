#include "unary_operation.hh"
#include "context.hh"

UnaryOperation::UnaryOperation(const std::string& token)
: Operation("unary", token)
{}


void
UnaryOperation::eval(Context& context) const {
	if(context.data_size()< 1) {
		// throw error!!!!
		throw UnaryOperationError();
	}
	
	double val = context.data_top();
	context.data_pop();

	double res = op(val);
	
	context.data_push(res);
}

NegateOperation::NegateOperation()
: UnaryOperation("!")
{}

double 
NegateOperation::op(double val) const {
	return -val;
}

SqOperation::SqOperation()
: UnaryOperation("^")
{}

double
SqOperation::op(double val) const {
	return val * val;
}

DupOperation::DupOperation()
: UnaryOperation("&")
{}

double
DupOperation::op(double val) const {
	return val;
}

