package chap1;

import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;

public interface Collection2<T> extends Collection<T> {

	default void forEachIf(Consumer<T> action, Predicate<T> filter) {

		Objects.requireNonNull(action);
		Objects.requireNonNull(filter);
		final Iterator<T> each = iterator();
		while (each.hasNext()) {
			T t = each.next();
			if (filter.test(t)) {
				action.accept(t);
			}
		}
	}
}
