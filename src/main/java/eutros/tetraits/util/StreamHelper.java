package eutros.tetraits.util;

import org.apache.commons.lang3.tuple.Pair;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

public class StreamHelper {

    public static <T> Stream<Pair<T, T>> split(Stream<T> stream) {
        return stream.map(t -> Pair.of(t, t));
    }

    public static <A, B, C> Stream<Pair<C, B>> mapFirst(Stream<? extends Map.Entry<A, B>> stream, Function<A, C> mapper) {
        return stream.map(p -> Pair.of(mapper.apply(p.getKey()), p.getValue()));
    }

}
