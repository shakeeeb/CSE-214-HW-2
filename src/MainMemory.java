import javax.swing.JOptionPane;
/**ID: 109239204
 * CSE 214 Homework Number 2
 * @author Shakeeb Saleh
 * R01
 */
public class MainMemory {
	private boolean AllocationMethod; // false is first fit, true is best fit
	
	private MemoryBlock head;
	private MemoryBlock tail;
	/**
	 * Constructor
	 * Constructs a linkedlist of one memory block, sie 1000, free
	 * sets it as the head and as the tail
	 * sets the address to 0
	 */
	public MainMemory(){
		// creates a linkedlist of one memory block
		MemoryBlock initial = new MemoryBlock(1000, false);
		initial.setAddress(0);
		head = initial;
		tail = initial;
	}
	/**
	 * mAlloc
	 * allocates a memory block
	 * the style of search for the allocation method depends on the boolean variable allocation method
	 * if the memory used is exact, the block is filled
	 * otherwise, the block is split
	 * to view specific, please read the program comments
	 * @param memoryUsed - amount of memory wished to allocate
	 * @return returns the address the memory was allocated to, 
	 */
	public int mAlloc(int memoryUsed) throws OutOfMemoryException, UnoptimallyAllocatedMemoryException{
	
			if(memoryUsed > this.totalFree()){
				throw new OutOfMemoryException();
			} 
			if(memoryUsed > this.largestBlock()){
				throw new UnoptimallyAllocatedMemoryException();
			}
		MemoryBlock nodePtr = new MemoryBlock();
		nodePtr = head;
		// nodePtr changes accordingly; if Allocation method is true, best fit, else first fit
		MemoryBlock currentClosest = new MemoryBlock();
		if(AllocationMethod){
			int closest = 1000; 
			while(nodePtr.getAllocated()){ // block is free 
				if((nodePtr.getMemory() >= memoryUsed)){ // block is larger
				if ((nodePtr.getMemory() - memoryUsed) < closest){	// if blocks memory difference is smaller
					closest = nodePtr.getMemory() - memoryUsed;
					currentClosest = nodePtr;
				}} nodePtr = nodePtr.getNext();//System.out.print(nodePtr.getMemory()+"\n");System.out.print(nodePtr.getAddress()+ "\n");
			} /*nodePtr = currentClosest*/;System.out.print(currentClosest.getMemory()+"\n");System.out.print(currentClosest.getAddress()+ "\n");
		} else{
		while(nodePtr.getAllocated() != false){//stops when nodeptr is free
			nodePtr = nodePtr.getNext();
		}}
		
		if(nodePtr.getMemory() == memoryUsed){ 
			// fill the block
			nodePtr.allocate(); return nodePtr.getAddress();
		} else if (memoryUsed < nodePtr.getMemory() && nodePtr.getNext() == null){ // if this node is the last node
			//split the block
			MemoryBlock newNode = new MemoryBlock(nodePtr.getMemory() - memoryUsed);
			newNode.setPrev(nodePtr);
			newNode.setCurrentAddress(newNode.getCurrentAddress()+newNode.getMemory());// revising the static variable currentaddress
			newNode.setAddress(newNode.getCurrentAddress());// applying the said address
			nodePtr.setMemory(memoryUsed);
			nodePtr.setNext(newNode);
			nodePtr.allocate(); 
			tail = newNode; return nodePtr.getAddress();
		} else if(memoryUsed < nodePtr.getMemory() && nodePtr.getNext() != null){ // if this node is not the last node
			MemoryBlock newNode = new MemoryBlock(nodePtr.getMemory() - memoryUsed);
			newNode.setPrev(nodePtr);
			newNode.setCurrentAddress(newNode.getCurrentAddress()+newNode.getMemory());// revising the static variable currentaddress
			newNode.setAddress(newNode.getCurrentAddress());// applying the said address
			nodePtr.setMemory(memoryUsed);
			newNode.setNext(nodePtr.getNext());
			nodePtr.getNext().setPrev(newNode);
			nodePtr.setNext(newNode);
			nodePtr.allocate(); return nodePtr.getAddress();
		} else {
			throw new OutOfMemoryException();
		}
	
	}
	/**
	 * free
	 * frees a block in memory
	 * if a free block is adjacent to other free blocks
	 * they merge into one block
	 * for more details, please see the program comments
	 * @param addr - address of the block to be freed
	 */
	public void free(int addr) throws BlockNotFoundException, AlreadyFreeException{
		MemoryBlock nodePtr = new MemoryBlock();
		nodePtr = head; int mark = 0;
		while(nodePtr != null) { 
			if (nodePtr.getAddress() == addr){mark = 1;}
			nodePtr = nodePtr.getNext();		
		} if (mark == 0){throw new BlockNotFoundException(); } 
		nodePtr = head;
		while(nodePtr.getAddress() != addr){
		 nodePtr = nodePtr.getNext(); }
		MemoryBlock newNode = new MemoryBlock();
/* Big if */if((nodePtr.getPrev() != null) && !(nodePtr.getPrev().getAllocated()) && !(nodePtr.getNext().getAllocated())){ // if both the previous block exists and its free, and the next block is free
			// merge this node with BOTH the nodes that are its direct neighbors
				int theAddress = nodePtr.getAddress();
				int theMemory = nodePtr.getMemory() + nodePtr.getNext().getMemory() + nodePtr.getPrev().getMemory();
				newNode.setMemory(theMemory);
				newNode.setAddress(theAddress);
				if(nodePtr.getPrev().getPrev() != null){ // if the node before the node behind this node exists
					newNode.setPrev(nodePtr.getPrev().getPrev()); // then link it to this node
					if(nodePtr.getNext().getNext() != null){ // if the node after the node in front of this node exists
						newNode.setNext(nodePtr.getNext().getNext()); // then link it to this node as well
						nodePtr.getNext().getNext().setPrev(newNode); // sever the link made by 2x after node
					} else {
						newNode.setNext(null); // if it doesnt exist then null
						tail = newNode;
					} nodePtr.getPrev().getPrev().setNext(newNode); // then sever the link made by 2x before node
				
				} else if(nodePtr.getNext().getNext() != null) { // if the node after the node in front of this node exists
					newNode.setNext(nodePtr.getNext().getNext()); // so in this case, the node before the node behind this node will definitely not exist
						newNode.setPrev(null); // because of the if statement
						head = newNode;
					} //nodePtr.getNext().getNext().setPrev(newNode);
/* Big if */} else if (!(nodePtr.getNext().getAllocated())) { // only the next block is free
				int theAddress = nodePtr.getAddress();
				int theMemory = nodePtr.getMemory() + nodePtr.getNext().getMemory();
				newNode.setMemory(theMemory);
				newNode.setAddress(theAddress);
				if (nodePtr.getNext().getNext() != null){ // if the 2x next block is not null, do this
					newNode.setNext(nodePtr.getNext().getNext());
					nodePtr.getNext().getNext().setPrev(newNode);
				} else {
					newNode.setNext(null);
					tail = newNode;	
				} 
/*Big If*/	} else if ((nodePtr.getPrev() != null) && !(nodePtr.getPrev().getAllocated())){ // if only the previous block is free
				int theAddress = nodePtr.getAddress();
				int theMemory = nodePtr.getMemory() + nodePtr.getPrev().getMemory();
				newNode.setMemory(theMemory);
				newNode.setAddress(theAddress);
				if(nodePtr.getPrev().getPrev() != null){ // if the 2x before block is not null, do this
					newNode.setPrev(newNode.getPrev().getPrev());
					nodePtr.getPrev().getPrev().setNext(newNode);
				} else {
					newNode.setPrev(null);
					newNode.setNext(nodePtr.getNext());
					nodePtr.getNext().setPrev(newNode);
					head = newNode;
				}
/*Big If*/  } else if (!(nodePtr.getAllocated())) {
				throw new AlreadyFreeException();
/*end of big If*/} else {
				nodePtr.free();
			}
	}
	
	/**
	 * AddMemory
	 * adds memory into the system
	 * @param size - amount of memory that goes into the system
	 */
	public void addMemory (int size){
		if (!(tail.getAllocated())){
			tail.setMemory(tail.getMemory()+ size);
		} else {
			MemoryBlock newNode = new MemoryBlock(size);
			tail.setNext(newNode);
			newNode.setPrev(tail);
			tail.setCurrentAddress(tail.getCurrentAddress() + size);
			newNode.setAddress(newNode.getCurrentAddress());
			tail = newNode;
		}
			
	}
	/**
	 * totalFree
	 * returns the total free memory
	 * @return total free memory
	 */
	public int totalFree(){
		MemoryBlock nodePtr = new MemoryBlock();
		nodePtr = head; int result = 0;
		while(nodePtr != null){
			if (!(nodePtr.getAllocated())){
				result += nodePtr.getMemory();
			} nodePtr = nodePtr.getNext();
		} return result;
	}
	/**
	 * totalAllocated
	 * returns the total allocated memory
	 * @return
	 */
	public int totalAllocated(){
		MemoryBlock nodePtr = new MemoryBlock();
		nodePtr = head; int result = 0;
		while(nodePtr != null){
			if (nodePtr.getAllocated()){
				result += nodePtr.getMemory();
			} nodePtr = nodePtr.getNext();
		} return result;
	}
	/**
	 * largestBlock
	 * returns the address of the largest free MemoryBlock available
	 * @return
	 */
	public int largestBlock(){
		MemoryBlock nodePtr = new MemoryBlock();
		nodePtr = head; int maximum = 0;
		while(nodePtr != null ){
			if(!(nodePtr.getAllocated())){
			if (nodePtr.getMemory() > maximum){
				maximum = nodePtr.getMemory();
			}} nodePtr = nodePtr.getNext(); 
		} return maximum;
	}
	/**SetAllocationmethod
	 * changes the allocation method
	 * @param value - switches the allocationmethod from true to false
	 */
	public void setAllocationMethod(boolean value){
		AllocationMethod = value;
	}
	/**
	 * get allocationmethod
	 * returns the allocationmethod
	 * @return
	 */
	public boolean getAllocationMethod(){
		return AllocationMethod;
	}
	/**
	 * Tostring
	 * returns the linkedlist of mainmemory as a string value
	 */
	public String toString(){
		String result = "";
		MemoryBlock nodePtr = new MemoryBlock();
		nodePtr = head;
		int address;
		String alloc = "";
		int size;
		while(nodePtr != null){
			address = nodePtr.getAddress();
			if(nodePtr.getAllocated()){
				alloc = "Allocated";
			} else { alloc = "Free"; }
			size = nodePtr.getMemory();
			String M1 = String.format("%-10d%-20s%-5d\n", address, alloc, size);
			result += M1; 
			nodePtr = nodePtr.getNext();
		} return result;
	}
	

}
