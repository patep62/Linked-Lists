public class SLLSet {
    private int size;
    private SLLNode head;

    public SLLSet() {
    	
    	this.size = 0; //Set size to zero.
    	this.head = null; //Since there are no nodes, have the header point to nothing.
    	
        
    }

    public SLLSet(int[] sortedArray) {
    	
    	this.size = sortedArray.length; //set the size.
    	
    	//I chose to construct the linked list from back to front. This is way easier then front to back because of where the null pointer is.
    	//If start at the front, we need to point to the next node, which points to the next node which points to the next and so on.
    	//This means we would have to so it all on one line which would be very messy and long.
    	//If we start from the back, we know it points to nothing, and then the 2nd last, points to the last which we know.
    	
    	int lastIndex = sortedArray.length - 1; //Index that represents the last node in the LL.
    	SLLNode last = new SLLNode(sortedArray[lastIndex], null); //A temporary node variable to store the previous node. 
    															  //We will use this to set the pointer of the current node to point to the last node.

    	for(int i = lastIndex - 1; i > 0; i--) { //Start at end of the LL and iterate to the start.
    		
    		SLLNode current = new SLLNode(sortedArray[i], last); //Add the required node to represent the integer in sortedArray.
    		last = current; //Store the node we just made in last, so that in the next iteration, we can point to this.
    	
    	}
    	
    	this.head = new SLLNode(sortedArray[0], last); //Now that we have all the nodes pointing to the right nodes, head is easy to create.
    	
    }

    public int getSize() {
		
        return this.size;
    }

    public SLLSet copy() {
        
    	SLLNode index; //Initialize an index node to let us iterate through the LL.
    	int[] list;
    	list = new int[this.size]; //Initialize a list that will represent the copy of the new LL we will make.
    	int i = 0;
    	index = this.head;
    	
    	while(index != null) { //Iterate through LL until we hit the null pointer which is the last node.
    		
    		list[i] = index.value; //Get the value of each node, and append it to the list we made.
    		index = index.next;	   //Shift the index over to the next node.
    		i += 1;				   //Increase i so that next time we append to the list, it will be in the correct spot.
    		
    	}
    	
    	SLLSet SLLSet_copy = new SLLSet(list); //And since the constructor we made earlier takes an array, we can just use that and the list we made.

        return SLLSet_copy;
    }

    public boolean isIn(int v) {
        
    	SLLNode index; //Iterating variable.
    	index = this.head;
    	
    	while(index != null) {
    		
    		if(v == index.value) { //Check the value in each node.
    			
    			return true;
    			
    		}
    		
    		index = index.next;
    		
    	}

        return false; //If we are at this point, it means that none of the values have matched, and so return false.
        
    }

    public void add(int v) {
    	
    	
    	if(this.isIn(v) == false) { //First check to make sure v is not in the LL.
    		
	    	SLLNode current;     //Similar to how we did the constructor, we have a current and a last variable to iterate through and save nodes.
	    	current = this.head; //Since current will represent the current node, we set to the head because that is the first node.
	    	int tempSize = this.size; //This variable is just used as a checker to ensue we don't add things twice.
	    	
	    	if (this.head.value > v) //Check the case where v is the smallest value in the LL, so it goes in front.
	    	{
	    		this.head = new SLLNode(v, this.head); //Change the head to the new node with value v, because v is now the first node.
	    		this.size += 1;						   //increase size since we added a new node.
	    	}
	    	
	    	SLLNode last = this.head; //Initialize last. 
	    	
	    	if(this.size == tempSize) { //Before iterating through the list, check to make sure the value has not already been placed.
	    				
		    	while(current != null) {
		    		
		    		if(current.value > v) { //Iterate through until we find a value greater then v. v should then go before current when found.
		    			
		    			SLLNode insert = new SLLNode(v, current); //Create the new node, which points to current, because it is less then current.
		    			last.next = insert;  //Since we are also always storing the previous node, set that pointer to point to the node we just created.
		    			this.size += 1;      //Increase size.
		    			break;				 //We have to break here because every value after current will also be greater then v.	
		    		}
		    		
		    		last = current; //Store last before moving to next iteration.
		    		current = current.next;
		    	
		    	}    
	    	}
	    	
	    	if(this.size == tempSize) { //The final case where the last v is the greatest value in the LL and thus it has to go at the end.
	    		
	    		SLLNode insert = new SLLNode(v, null); //Create the new node, pointing to null, because its the last node.
	    		last.next = insert; //Since we should have the last node stored from the above loop, we can set its pointer to point to this node.
	    		this.size += 1;
	    	}
	    	
    	}
      	
    }

    public void remove(int v) {
    	
    	if(this.isIn(v)) { //Check to make sure v is in the LL.
    		
    		SLLNode current = this.head; //Just like what we did above, we need a current and a last node.
    		SLLNode last = this.head;
    		
    		if(this.head.value == v) { //Check the case where the first value in the LL is the target to be removed.
    			
    			this.head = this.head.next; //All we need to do to remove it is to change the head to next node, effectively skipping the first node.
    			this.size -= 1; //Decrease the size by one since we removed a node.
    			
    		}
    		
    		else { //This only runs if the first test failed, meaning the target is somewhere else in the LL.
	    		while(current != null) {
	    			
	    			if(current.value == v) { //Iterate through until we find v.
	    				
	    				last.next = current.next; //Set the last node to point to the node after the current node, effectively skipping the current node.
	    				this.size -= 1;
	    				
	    			}
	    			
	    			last = current;
	    			current = current.next;
	    			
	    		}
    		}
    	}
    }

    public SLLSet union(SLLSet s) {
        
    	
    	if (this.size == 0 || s.size == 0) { //Check the case where one of the lists are empty. 
        
            if (this.size == 0){
            	
                return s.copy(); //Return the copy of the list that is not empty.
                
            }
            else {
            
                return this.copy();
                
            }
        }
    	
    	SLLNode index = this.head;
    	
    	while(index != null) {
    		
    		s.add(index.value); //Iterate through each value in this LL and use add function to add to the s LL. 
    		index = index.next;
    		
    	}
    	
        return s;
    }

    public SLLSet intersection(SLLSet s) {
    	
    	SLLNode index = s.head;
    	while(index != null) {
    		
    		if(this.isIn(index.value) == false) { //Iterate through each node in s LL and remove every node that does not exist in this LL.
    			
    			s.remove(index.value);
    			
    		}
    		
    		index = index.next;
    		
    	}

        return s;
    }

    public SLLSet difference(SLLSet s) {
    	
    	SLLNode index = this.head;
    	while(index != null) {
    		
    		if(s.isIn(index.value) == true) { //Iterate through each node in this LL and remove every node that exists in s LL.
    			
    			this.remove(index.value);
    			
    		}
    		
    		index = index.next;
    		
    	}

        return this;
    }

    public static SLLSet union(SLLSet[] sArray) {
    	
 
        SLLSet ss = new SLLSet();

        int i;
        for(i = 0; i < sArray.length; i++) { //Iterate through the array of LL's.
        	
        	ss = ss.union(sArray[i]); //Union each array with s.
        	
        }
                
        return ss;
    }

    @Override
    public String toString() {
    	
    	String output = new String();
    	SLLNode index;
    	index = this.head;
    	
    	while(index != null) {
    		
    		
    		output += index.value;
    		
    		if(index.next != null) {
    			
    			output += ", ";
    		}
    		
    		index = index.next;
    		
    	}
    	
        return output;
        
    }
}
