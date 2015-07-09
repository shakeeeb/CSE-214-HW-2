import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**ID: 109239204
 * CSE 214 Homework Number 2
 * @author Shakeeb Saleh
 * R01
 */

/**
 * this is pretty much just gui
 * @author shakeeb saleh
 *
 */
public class MemoryManager extends JFrame {
	JButton addMemory = new JButton("Add Memory");
	JButton displayContents = new JButton("Display the Contents of Main Memory");
	JButton mAlloc = new JButton("Allocate Memory");
	JButton free = new JButton("Free Memory");
	JPanel radioButtonHolder = new JPanel();
	JRadioButton firstFit = new JRadioButton("First Fit");
	JRadioButton bestFit = new JRadioButton("Best fit");
	JButton getAvailableMemory = new JButton("Get Available Memory");
	JButton getAllocatedMemory = new JButton("Get Allocated Memory");
	JButton getLargestBlock = new JButton("get Largest Block size");
	JButton exit = new JButton("Exit Program");
	ButtonGroup group = new ButtonGroup();
	
	MainMemory memory = new MainMemory();
	
	public MemoryManager(){
		this.setSize(200,600);
		this.setLayout(new GridLayout(9,1));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		group.add(firstFit);
		group.add(bestFit);
		radioButtonHolder.add(firstFit);
		radioButtonHolder.add(bestFit);
		
		this.add(addMemory);this.add(displayContents);this.add(mAlloc);
		this.add(free);this.add(radioButtonHolder);this.add(getAvailableMemory);
		this.add(getAllocatedMemory); this.add(getLargestBlock); this.add(exit);
		
		addMemory.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){activate(1);}});
		displayContents.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){activate(2);}});
		mAlloc.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){activate(3);}});
		free.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){activate(4);}});
		getAvailableMemory.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){activate(5);}});
		getAllocatedMemory.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){activate(6);}});
		getLargestBlock.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){activate(7);}});
		exit.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){activate(8);}});
		
		firstFit.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){flipper(false);}});
		bestFit.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){flipper(true);}});
		
		this.setVisible(true);
	}
	
	public void flipper(boolean allocMethod){
		if (allocMethod == false){
			memory.setAllocationMethod(allocMethod);
		} else {memory.setAllocationMethod(allocMethod);}
	}
	
	public void activate(int option){
		switch(option){
		case 1: // addMemory
			int size = Integer.parseInt(JOptionPane.showInputDialog("Please input the amount you would like to add: "));
			memory.addMemory(size);
			JOptionPane.showMessageDialog(null," "+  size + " bytes of memory have been added");
			break;
		case 2: // Display Contents
			{String address = "Address"; String status = "Status"; String siz = "Size";
			String header = String.format("%-10s%-20s%-5s", address, status, siz);
			String linebreak = "------------------------------------\n";
			JOptionPane.showMessageDialog(null, header + "\n" + linebreak + memory.toString());}
			break;
		case 3: // Allocate Memory
			try {int bytes = Integer.parseInt(JOptionPane.showInputDialog("Enter the number of Bytes to allocate: "));
			int addy = memory.mAlloc(bytes);
			JOptionPane.showMessageDialog(null, "" + bytes + " bytes have been Allocated to Memory, at Address: " + addy);
			} catch(OutOfMemoryException o){JOptionPane.showMessageDialog(null, "There isnt enough room on the Disc");}
			catch(UnoptimallyAllocatedMemoryException u){JOptionPane.showMessageDialog(null, "There isnt a free block that can accompany that much data");}
			break;
		case 4: // Free
			try{int addr = Integer.parseInt(JOptionPane.showInputDialog("Enter the address of the Memory Block you wish to free: "));
			memory.free(addr);
			JOptionPane.showMessageDialog(null, "freed the block at address " + addr );
			} catch(BlockNotFoundException b){JOptionPane.showMessageDialog(null, "A block with this address does not exist");}
			catch (AlreadyFreeException a){JOptionPane.showMessageDialog(null, "The Block at this address is already free");} 
			break;
		case 5: // get available memory
			int result = memory.totalFree();
			JOptionPane.showMessageDialog(null, "there are "+ result + " bytes of Memory available for use.");
			break;
		case 6: // get allocated memory
			int res = memory.totalAllocated();
			JOptionPane.showMessageDialog(null, "there are "+ res + " bytes of Memory allocated");
			break;
		case 7:  // get largest block
			int ress = memory.largestBlock();
			JOptionPane.showMessageDialog(null, "the largest free Block is "+ ress +" bytes large");
			break;
		case 8: // exit
			System.exit(0);
			break;
		}
	}
	
	public static void main(String[] args){
		MemoryManager manager = new MemoryManager();
	}

}
