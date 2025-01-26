public class BitSetTest{
	
	public static void main(String[] args){
		BitSetImpl bs = new BitSetImpl(9);
		BitSetFullAccess lappen = bs;
		lappen.expand(2);
		lappen.set(10);
		lappen.clear(5);
		System.out.println(lappen.get(5));
		System.out.println(lappen.getSize());
	}
}