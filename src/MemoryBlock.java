
public class MemoryBlock {
	/**ID: 109239204
	 * CSE 214 Homework Number 2
	 * @author Shakeeb Saleh
	 * R01
	 */
	private static int currentAddress = 0;
	// Data
	private int memory;
	private int address;
	private boolean allocated = false;
	// Links
	private MemoryBlock next;
	private MemoryBlock prev;
	/**
	 * Constructor
	 */
	public MemoryBlock(){
		
	}
	
	/**
	 * Overloaded constructor 
	 * @param initialMemory - the initial memory of the block
	 */
	public MemoryBlock(int initialMemory){
		this.memory = initialMemory;
		this.allocated = false;
		this.next = null;
		this.prev = null;
	}
	/**
	 * Overloaded Constructor
	 * @param initialMemory - Initial memory of the block
	 * @param alloc - state of allocation, if the block is free or allocated
	 */
	public MemoryBlock(int initialMemory, boolean alloc){
		this.memory = initialMemory;
		this.allocated = alloc;
		this.next = null;
		this.prev = null;
	}

	// Set/get for the Next and Previous
	/**
	 * setNext 
	 * links a memoryblock behind this block
	 * @param newLink - the block to be linked
	 */
	public void setNext(MemoryBlock newLink){
		this.next = newLink;
	}
	/**
	 * getNext
	 * retrieves the memory block behind this block
	 * @return returns the linked block
	 */
	public MemoryBlock getNext(){
		return next;
	}
	/** setPrev
	 * links a memoryblock in front of this block
	 * @param newLink
	 */
	public void setPrev(MemoryBlock newLink){
		this.prev = newLink;
	}
	/** getPrev
	 * retrieves the memory block in front of this block
	 * @return returns the linked block
	 */
	public MemoryBlock getPrev(){
		return prev;
	}
	
	// set/get for the address and static varaible currentAddress
	/**
	 * setcurrentAddress
	 * changes the variable currentaddress, used to address the blocks
	 * @param newCurrentAddress
	 */
	public void setCurrentAddress(int newCurrentAddress){
		currentAddress = newCurrentAddress;
	}
	/**
	 * getCurrentaddress
	 * retrieves the variable currentaddress, used to address new blocks
	 * @return
	 */
	public int getCurrentAddress(){
		return currentAddress;
	}
	/**
	 * setAddress
	 * sets the address of this block
	 * @param newAddress
	 */
	public void setAddress(int newAddress){
		this.address = newAddress;
	}
	/** getAddress
	 * returns the address of this block
	 * @return
	 */
	public int getAddress(){
		return address;
	}
	
	// set/get for the Memory
	/**
	 * setMemory
	 * sets the memory of the block
	 * @param newMemory
	 */
	public void setMemory(int newMemory){
		this.memory = newMemory;
	}
	/**
	 * getMemory
	 * returns the memory of the block
	 * @return
	 */
	public int getMemory(){
		return memory;
	}
	
	// set/get for if allocated or not
	/**
	 * getallocated
	 * returns the status of allocation, either free, or allocated
	 * @return
	 */
	public boolean getAllocated(){
		return allocated;
	}
	/**
	 * free
	 * sets the variable allocated to false, freeing the block
	 */
	public void free(){
		allocated = false;
	}
	/**
	 * allocate
	 * allocates the block
	 */
	public void allocate(){
		allocated = true;
	}
	

}
