package chap1.exercise;

import chap1.Collection2;
import chap1.MyArray;
import chap1.RunnableEx;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static chap1.RunnableEx.uncheck;
import static chap1.RunnableEx.andThen;

import java.io.File;
import java.io.FileFilter;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;




public class Exercise{
	
	@Test
	public void exercise1(){
		String[] ss = new String[]{"B", "A", "C"};
		Arrays.sort(ss, (s,t) -> s.compareTo(t) );
		
		String[] ex = new String[]{"A", "B", "C"};
		assertArrayEquals(ss, ex);
		//Sort and Comparator share same thread.
	}
	
	@Test
	public void exercise2(){
		
		File file = new File("D:\\tmp");
		
		File[] fs = file.listFiles(new FileFilter(){
			public boolean accept(File f) {
				return f.isDirectory();
			}
		});
		
		File[] ex1 = file.listFiles( (File f) -> {return f.isDirectory();} );
		assertArrayEquals(fs, ex1);
		
		File[] ex2 = file.listFiles( f -> f.isDirectory() );
		assertArrayEquals(fs, ex2);
	}
	
	
	@Test
	public void exercise3(){
		String suffix = ".docx";
		File file = new File("D:\\tmp");
		
		File[] ex = file.listFiles((f, s) -> s.endsWith(suffix));
		assertNotNull(ex);
		assertTrue(ex[0].getName().endsWith(suffix));
	}
	
	@Test
	public void exercise4(){
		
		File[] files = new File[]{new File("D:\\tmp\\dir1"), new File("D:\\tmp\\dir2\\d2.txt"), new File("D:\\tmp\\dir1\\d1.txt"), new File("D:\\tmp\\")};
		Arrays.sort(files, (f1, f2) -> f1.compareTo(f2)); 
		System.out.println("exercise 4:"+Arrays.toString(files));
	}
	
	@Test
	public void exercise5(){
		RunnableEx exe = () -> {System.out.println("exercise 5"); Thread.sleep(1000);};
		uncheck(exe).run();
	}
	
	@Test
	public void exercise6(){
		new Thread(uncheck(() -> {System.out.println("exercise 6"); Thread.sleep(1000);})).start();
	}
	
	@Test
	public void exercise7(){
		PrintStream out = System.out;
		new Thread(andThen(() -> out.println("exercise 7 r1"), ()->out.println("exercise 7 r2"))).start();;
	}
	
	@Test
	public void exercise8(){
		
		String[] ss = {"B", "A", "C"};
		List<Runnable> runners = new ArrayList<Runnable>();
		for(String s : ss){
			runners.add(() -> System.out.println(s));
		}
		
		for(Runnable r : runners){
			r.run();
		}
	}
	
	@Test
	public void exercise9(){
		Collection2<Integer> ints = new MyArray<>();
		ints.add(3);ints.add(1);ints.add(2);ints.add(4);
		ints.forEachIf(t ->System.out.println(t), t -> t > 2);
	}
	
	@Test
	public void exercise10(){

	}
	
	@Test
	public void exercise11(){
		//See package exercise11;
	}
	
	@Test
	public void exercise12(){
		//TODO
	}	
}