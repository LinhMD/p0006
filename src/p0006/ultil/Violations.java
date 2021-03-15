package p0006.ultil;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.HashMap;
import java.util.Set;

public class Violations<T> extends HashMap<String, String> {
	private static final Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();

	private boolean error = true;
	private T t;

	public Violations(){

	}

	public Violations(T t){
		if((this.t = t) == null) return;

		this.evaluate();
	}

	public boolean isError() {
		return error;
	}

	public T getEvaluateObject() {
		return t;
	}

	private void evaluate(){
		Set<ConstraintViolation<T>> validate = VALIDATOR.validate(t);
		if(!validate.isEmpty())
			for (ConstraintViolation<T> violation : validate)
				this.put(violation.getPropertyPath().toString(), violation.getMessage());
		else
			this.error = false;
	}

	public void evaluate(T t){
		if (t == null) return;
		this.t = t;
		this.evaluate();
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		for (String value : this.values()) {
			s.append(value).append("\n");
		}
		return s.toString();
	}

}
