public class Bruch{
	
	private int zaehler, nenner;
	
	Bruch(int zaehler, int nenner){
		if (nenner == 0){
			System.out.println("Fehlerhafter Bruch");
			this.nenner = 1;
			this.zaehler = zaehler;
		}else{
			this.nenner = nenner;
			this.zaehler = zaehler;
		}
	}
	
	public int getZaehler(){
		return this.zaehler;
	}
	public int getNenner(){
		return this.nenner;
	}
	
	public void scalmult(int scal){
		this.nenner *= scal;
		this.zaehler *= scal;
	}
	
	public void mult(Bruch fak){
		this.zaehler *= fak.zaehler;
		this.nenner *= fak.nenner;
	}
	
	public void div(Bruch fak){
		int tempzaehler = this.zaehler*fak.nenner;
		int tempnenner = this.nenner*fak.zaehler;
		this.zaehler = tempzaehler;
		this.nenner = tempnenner;
	}
	
	public void printBruch(){
		System.out.println(this.zaehler+ "/" +this.nenner);
	}
}