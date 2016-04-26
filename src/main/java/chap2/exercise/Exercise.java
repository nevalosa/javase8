package chap2.exercise;

import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import org.junit.Test;

public class Exercise {
	
	@Test
	public void execise1() throws InterruptedException, ExecutionException{
		final List<String> words = Stream.of("a", "bb", "ccc", "dddd").collect(toList());
		int size = words.size();
		int start = 0;
		int end = words.size() - 1;
		int p = Runtime.getRuntime().availableProcessors();
//		if(p>= size){
//			end = 1;
//		}
		Callable<Integer> call = () -> {int c = 0; for(int i = start; i <=end; i++)if(words.get(i).length() > 2) c++; return c; };
		ExecutorService executor = Executors.newCachedThreadPool();
		Future<Integer> future = executor.submit(call);
		System.out.println(future.get());
		executor.shutdown();
	}
	
	@Test
	public void execise2(){
		
		final AtomicInteger counter = new AtomicInteger(0);
		Stream<String> longest = Stream.of("dddd", "a", "bb", "ccc", "ggggggg", "eeeee", "ffffff").sorted((t1, t2) -> t1.length() - t2.length()).filter( (t) -> counter.getAndIncrement()< 5 );
		assertEquals(longest.count(), 5);
	}
	
	@Test
	public void execise3() throws IOException{
		
		String contents = new String(Files.readAllBytes(Paths.get("D:\\git\\javase8.git\\book-javase8\\src\\main\\java\\chap2\\War and Peace - Leo Tolstoy.txt")), StandardCharsets.UTF_8);
		List<String> words = Arrays.asList(contents.split("[\\P{L}]+"));
		
		long t1, t2;
		long time = System.nanoTime();
		long c1 = words.parallelStream().filter(w -> w.length() > 5).count();
		t1 = System.nanoTime() - time;
		
		time = System.nanoTime();
		long c2 = words.stream().filter(w -> w.length() > 5).count();
		t2 = System.nanoTime() - time;
		System.out.println(String.format("t1 = %s, t2 = %s, c1 = %s, c2 = %s", t1, t2, c1, c2));
		assertTrue(t1 > t2);
		assertEquals(c1, c2);

	}
}
