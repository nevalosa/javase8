package chap3;

import java.util.Comparator;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.IntConsumer;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Test;

public class Lambda {

	Logger logger = Logger.getLogger("Lambda");
	
	@Test
	public void chap3_1(){
		int x= 0, y = 0;
		info(logger, ()-> "x:"+x+", y:"+y);
	}
	
	private static void info(Logger logger, Supplier<String> message){
		if(logger.isLoggable(Level.INFO)){
			logger.info(message.get());
		}
	}
	
	@Test
	public void chap3_2(){
		repeat(10, i -> System.out.println("Countdown:"+ (9-i)));
	}
	
	private static void repeat(int n, IntConsumer action){
		for(int i = 0; i < n;  i++) action.accept(i);
	}
	
	@Test
	public void chap3_3(){
		
		BiFunction<String, String, Integer> bif = (x, y) -> x.length() + y.length();
		String[] string = new String[]{"A", "B"};
		addSize(string, bif);
		
		Comparator<String> cmp = Comparator.comparingInt(String::length).thenComparing(String.CASE_INSENSITIVE_ORDER);
		cmp.compare("A", "BC");

	}
	
	private static void addSize(String[] str, BiFunction<String, String, Integer> bifun){
		System.out.println(bifun.apply(str[0], str[1]));
		Function<String, Integer> f = (x) -> x.length(); 
		for(int i = 0; i < str.length; i++){
		}
	}
	
	private void chap3_4(){
		
	}
}
