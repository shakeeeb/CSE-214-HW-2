
public class UnoptimallyAllocatedMemoryException extends Exception {
	public UnoptimallyAllocatedMemoryException(){
		super();
	}
	public UnoptimallyAllocatedMemoryException(String message){
		super(message);
	}
	
	public UnoptimallyAllocatedMemoryException(String message, Throwable cause){
		super(message, cause);
	}
	public UnoptimallyAllocatedMemoryException(Throwable cause){
		super(cause);
	}
}
