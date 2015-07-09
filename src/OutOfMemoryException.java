
public class OutOfMemoryException extends Exception{
	public OutOfMemoryException(){
		super();
	}
	public OutOfMemoryException(String message){
		super(message);
	}
	
	public OutOfMemoryException(String message, Throwable cause){
		super(message, cause);
	}
	public OutOfMemoryException(Throwable cause){
		super(cause);
	}

}
