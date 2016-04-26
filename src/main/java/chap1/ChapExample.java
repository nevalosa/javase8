package chap1;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.function.BiFunction;

public class ChapExample {

	public static void main(String[] args) {

		///////////////// 1.2 Lambda Expression ///////////////////
		// Integer comparator.
		int result1 = Integer.compare("a".length(), "b".length());

		// Customized comparator.
		Comparator<String> comp = (a, b) -> Integer.compare(a.length(), b.length());
		int result2 = comp.compare("a", "b");

		assert result1 == result2;

		// Customized comparator.
		Comparator<String> comp2 = (String a, String b) -> {
			if (a.length() < b.length())
				return -1;
			else if (a.length() > b.length())
				return 1;
			else
				return 0;
		};

		int result3 = comp2.compare("a", "b");
		assert result1 == result3;

		comp2 = (String a, String b) -> {
			if (a == null) {
				// Should not be there if a is null.
				return 0;
			}
			return 1;
		};

		System.out.println(comp2.compare(null, "a"));

		///////////////// 1.3 Functional Interface ///////////////////

		Arrays.sort(new String[] { "a", "b" }, comp);

		BiFunction<String, String, Integer> comp3 = (a, b) -> Integer.compare(a.length(), b.length());
		comp3.apply("a", "b");

		// Arrays.sort(new String[]{"a","b"}, comp3);

		Runnable sleeper1 = () -> {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
		};
		
		Callable<Void> sleeper2 = () -> {
			Thread.sleep(1000);
			return null;
		};
		
		Thread t1 = new Thread(sleeper1);
		t1.start();
		try {
			sleeper2.call();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		///////////////// 1.4 Method Refer ///////////////////
		String[] array = new String[]{"b","a"};
		Arrays.sort(array, String::compareToIgnoreCase);
		assertEquals(array[0], "a");
		assertEquals(array[1], "b");
		Arrays.sort(array, (x,y)->y.compareToIgnoreCase(x));
		assertEquals(array[0], "b");
		assertEquals(array[1], "a");
		Arrays.sort(array, (x,y)->x.compareToIgnoreCase(y));
		assertEquals(array[0], "a");
		assertEquals(array[1], "b");
		
		
		class Greeter {
			
			public void greet(){
				System.out.println("Hello, World!.");
			}
		}
		
		class ConcurrentCreeter extends Greeter{
			public void greet(){
				Thread t = new Thread(super::greet);
				t.start();
			}
		}
		
		///////////////1.6 Parameter's Scope
		List<String> matches = new ArrayList<>();
		for(String a : array){
			new Thread(()->{
				//Non-Thread-safe.
				if(!matches.contains(a)){
					matches.add(a);
				}
			});
		}
	}
}
