package chap2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static java.util.stream.Collectors.*;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.IntSummaryStatistics;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

public class StreamIntro {

	@Test
	public void chap2_2() {
		List<String> words = new ArrayList<String>();
		words.add("Hello");
		words.add("World!");

		long count = words.stream().filter(w -> w.length() > 5).count();
		assertEquals(1, count);

		count = words.parallelStream().filter(w -> w.length() > 5).count();
		assertEquals(1, count);

		Stream<String> wstream = Stream.of("Hello, World!".split("[,]"))
				.parallel();
		count = wstream.filter(w -> w.length() > 4).count();
		assertEquals(2, count);

		Stream<String> echo = Stream.generate(() -> "Hello, World!");
		assertTrue(echo.findAny().get().equals("Hello, World!"));

		Stream<UUID> uuid = Stream.generate(() -> UUID.randomUUID());
		assertNotNull(uuid.findAny().get().toString());

		try {
			uuid.findAny().get().toString();
			fail("Stream closed.");
		} catch (Exception e) {
			System.out.println("Stream closed.");
		}

		Optional<UUID> option = Stream.generate(() -> UUID.randomUUID())
				.findAny();
		assertEquals(option.get().toString(), option.get().toString());
	}

	public static Stream<Character> characterStream(String s) {
		Objects.requireNonNull(s);
		return s.chars().mapToObj((t) -> Character.valueOf((char) t));
	}

	@Test
	public void chap2_3() {

		PrintStream out = System.out;
		Stream<String> words = Stream.of("Hello,World!".split("[,|!|]"))
				.parallel();
		Stream<Stream<Character>> result = words.map(w -> characterStream(w));
		result.iterator().forEachRemaining(s -> {
			s.iterator().forEachRemaining(t -> out.print(t));
			out.println();
		});
		Stream<Character> flatResult = words.flatMap(w -> characterStream(w));
	}

	@Test
	public void chap2_4() {

		Stream<Character> combine = Stream.concat(characterStream("Hello"),
				characterStream("World"));
		Stream<Character> ex = characterStream("HelloWorld");

		BiConsumer<Character, Character> bi = (a, b) -> assertEquals(a, b);

		BiConsumer<Stream<Character>, Stream<Character>> stream = (a, b) -> {
			Iterator<Character> ia = a.iterator();
			Iterator<Character> ib = b.iterator();
			while (ia.hasNext() && ib.hasNext()) {
				bi.accept(ia.next(), ib.next());
			}
		};

		stream.accept(combine, ex);

		Object[] array = Stream.iterate(1.0, p -> p * 2)
				.peek(e -> System.out.println("Feeching " + e)).limit(20)
				.toArray();
		assertEquals(array.length, 20);
	}

	@Test
	public void chap2_5() {

		Stream<String> uniqWords = Stream.of("A", "B", "C", "A", "C", "D")
				.distinct();
		assertEquals(uniqWords.count(), 4);
		Stream<String> longestFirst = Stream.of("1", "22", "333", "4444",
				"55555", "666666").sorted(
				Comparator.comparing(String::length).reversed());
		assertEquals(longestFirst.findFirst().get(), "666666");
	}

	@Test
	public void chap2_6() {
		Stream<String> words = Stream.of("A", "B", "C", "A", "C", "D");
		Optional<String> largest = words.max(String::compareToIgnoreCase);
		assertEquals(largest.get(), "D");
	}

	@Test
	public void chap2_7() {
		Optional<String> a = Optional.of("A");
		assertEquals(a.get().charAt(0), 'A');
		Optional.of("A").ifPresent(string -> System.out.println(string));

		Optional<Integer> intOpt = opstr("3").flatMap(StreamIntro::opnum);
		assertEquals(intOpt.get().intValue(), 3);
	}

	public static Optional<String> opstr(String number) {

		return Optional.of(number);
	}

	public static Optional<Integer> opnum(String number) {
		try {
			Integer ints = Integer.valueOf(number);
			return Optional.of(ints);
		} catch (Exception e) {
			return Optional.empty();
		}
	}

	public static <T extends Comparable<T>> Optional<T> max(Stream<T> streamT) {

		return streamT.reduce((t1, t2) -> t1.compareTo(t2) > 0 ? t1 : t2);
	}

	@Test
	public void chap2_8() {

		Stream<Integer> ints = Stream.of(9, 1, 2, 4, 8);
		Optional<Integer> intOpt = max(ints);
		assertEquals(intOpt.get().intValue(), 9);

		Stream<String> string = Stream.of("A", "B", "Z");
		Optional<String> stringOpt = max(string);
		assertEquals(stringOpt.get(), "Z");
		
		Stream<Integer> s = Stream.of(1, 2, 3, 4, 5);
		Optional<Integer> opt = s.reduce((x, y) -> x * y);
		assertEquals(opt.get().intValue(), 120);

		Stream<Integer> ss = Stream.of(1, 2, 3, 4, 5);
		String sss = ss.reduce("", (x, y) -> x + y, (a, b) -> String.valueOf(a) + String.valueOf(b));
		assertEquals(sss, "12345");
	}

	@Test
	public void chap2_9() {
		TreeSet<Integer> set = Stream.of(1,2,3,4,3,2,4,2,1).collect(Collectors.toCollection(TreeSet::new));
		assertEquals(set.size(), 4);
	}
	
	@Test
	public void chap_2_10(){
		Stream<Locale> locales = Stream.of(Locale.getAvailableLocales());
//		Map<String, Set<String>> cls = locales.collect(
//				Collectors.toMap(
//						l -> l.getDisplayContry(), 
//						l -> Collections.singleton(l.getDispalayLanguage()),
//						(a,b) -> {
//							Set<String> r = new HashSet<>(a);
//							r.addAll(b);
//							return r;
//							}
//						)
//				);
		
	}

	@Test
	public void chap_2_11(){
		
		Stream<Locale> locales = Stream.of(Locale.getAvailableLocales());
		Map<String, List<Locale>> ctc= locales.collect(groupingBy(Locale::getCountry));
		Map<Boolean, List<Locale>> partition = Stream.of(Locale.getAvailableLocales()).collect(partitioningBy(l -> l.getLanguage().equals("en")));
		partition.get(true);
		Map<String, Set<Locale>> cset = Stream.of(Locale.getAvailableLocales()).collect(groupingBy(Locale::getCountry,toSet()));
		IntSummaryStatistics sint = Stream.of(1,2,3,4,5).collect(IntSummaryStatistics::new, IntSummaryStatistics::accept, IntSummaryStatistics::combine);
		assertEquals(sint.getAverage(), 3, 0);
		assertEquals(sint.getMax(), 5, 0);
		assertEquals(sint.getMin(), 1, 0);
		assertEquals(sint.getSum(), 15, 0);
	}
}
