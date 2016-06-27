package br.ufc.sice.network;

public class Answer {

	public enum Cod{SUCCESS, ERROR, INTERNAL_ERROR};

    private Cod result;
    private String message;
    private Object object;
	
	public Answer(){
		
	}
	
	public void load(){
		this.result = Cod.SUCCESS;
		this.message = null;
	}
	
	public Answer(Object object){
		load();
		
		this.object = object;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public Cod getResult() {
		return result;
	}

	public void setResult(Cod result) {
		this.result = result;
	}
		
}
