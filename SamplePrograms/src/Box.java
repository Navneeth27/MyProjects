
 class Box {
 double length; 
 double width;
 double breadth;
 private double volume()
 {
	return length*width*breadth; 
 }
 
 
	public static void main(String[] args) {
		Box b=new Box();
		b.length=10;
		b.width=10;
		b.breadth=10;
		double volume= b.breadth*b.length*b.width;
		System.out.println("The volume:"+volume);
	}
 }