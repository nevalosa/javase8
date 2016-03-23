package chap1.exercise11;


public class ExtendInterface implements IInterface, JInterface, IStatic, JDefault{
	
	public void f(){
		System.out.println("Override.");
	}
	
	public static void callMe(IInterface me){
		me.f();
	}
	
	public static void callMe(JInterface me){
		me.f();
	}
	
	public static void callMe(IStatic me){
		IStatic.f();
	}
	
	public static void callMe(JDefault me){
		me.f();
	}
	
	public static void main(String[] args) {
		
		IInterface i = () -> System.out.println("Interface I");
		callMe(i);
		
		JInterface j = () -> System.out.println("Interface J");
		callMe(j);
		
		IInterface extI = new ExtendInterface();
		callMe(extI);
		
		JInterface extJ = new ExtendInterface();
		callMe(extJ);
		
		JDefault extD = new ExtendInterface();
		callMe(extD);
		
		IStatic extS = new ExtendInterface();
		callMe(extS);
	}
}