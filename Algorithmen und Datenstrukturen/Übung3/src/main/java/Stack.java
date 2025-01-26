class Stack{

   private int topindex =-1;
   private int size;
   private char[] arr;

   Stack(int x) {
      this.size = x;
      this.arr = new char[this.size];
   }

   public void push (char x){
      if (isFull()){
         System.out.println("StackIstVoll");
      }else{
         topindex++;
         this.arr[topindex] = x;
      }
   }
   public char pop () {
      if (!isEmpty()) {
         char topEl = this.arr[this.topindex];
         this.topindex--;
         return topEl;
      }
      return 'a';
   }
   public int size () {
      return this.size;
   }

   public boolean isEmpty () {
      return this.topindex == -1;
   }

   public boolean isFull () {
      return this.topindex==this.size-1;
   }

}
