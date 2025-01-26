public class BitSetImpl implements BitSetReadOnly, BitSetFullAccess{
	
	private boolean[] bits;
	private int size;
	
	public BitSetImpl(int size){
		this.size = size;
		this.bits = new boolean[this.size];
		for (int i = 0; i <size; i++){
			this.bits[i] = false;
		}
	}
	
	public boolean get(int index){
		return this.bits[index];
	}
	
	public int getSize(){
		return this.size;
	}
	
	public void expand(int n){
		boolean[] temp = new boolean[this.size+n];
		for (int i= 0;i< size; i++){
			temp[i] = this.bits[i];
		}
		for (int i = size; i< size+n; i++){
			temp[i] = false;
		}
		this.bits = new boolean[this.size+n];
		for (int i = 0; i<this.size+n; i++){
			this.bits[i] = temp[i]; 
		}
			
		this.size += n;
	}
	
	public void set(int index){
		this.bits[index] = true;
	}
	
	public void clear(int index){
		boolean[] temp = new boolean[this.size-1];
		for (int i = 0; i<index; i++){
			temp[i] = this.bits[i];
		}
		for (int i = index+1; i<size-1; i++){
			temp[i] = this.bits[i];
		}
		this.bits = new boolean[this.size-1];
		for (int i = 0; i<this.size-1; i++){
			this.bits[i] = temp[i]; 
		}
		this.size+= -1;
	}
	
	public void flip(int index){
		if(this.bits[index]){
			this.bits[index] = false;
		}else{
			this.bits[index] = true;
		}
	}
	
}