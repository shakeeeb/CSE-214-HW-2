
public class AlreadyFreeException extends Exception{
	public AlreadyFreeException(){
		super();
	}
	public AlreadyFreeException(String message){
		super(message);
	}
	
	public AlreadyFreeException(String message, Throwable cause){
		super(message, cause);
	}
	public AlreadyFreeException(Throwable cause){
		super(cause);
	}

}
