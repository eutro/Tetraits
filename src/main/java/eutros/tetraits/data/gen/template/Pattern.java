package eutros.tetraits.data.gen.template;

import java.util.*;

public class Pattern {

    private Map<IKey<?>, Object> properties;

    private Pattern(Pattern pattern) {
        properties = new IdentityHashMap<>(pattern.properties);
    }

    private Pattern() {
        properties = new HashMap<>();
    }

    public static Pattern create() {
        return new Pattern();
    }

    public <T> T get(IKey<T> key) {
        if(!properties.containsKey(key)) throw new NoSuchElementException(key + " in " + this);
        return key.valClass().cast(properties.get(key));
    }

    public Pattern copy() {
        return new Pattern(this);
    }

    public Pattern merge(Pattern other) {
        properties.putAll(other.properties);
        return this;
    }

    public <T> Pattern property(IKey<T> key, T val) {
        properties.put(key, val);
        return this;
    }

    @Override
    public String toString() {
        Iterator<Map.Entry<IKey<?>, Object>> i = properties.entrySet().iterator();
        if(!i.hasNext()) return "{}";

        StringBuilder sb = new StringBuilder("{");
        for(; ; ) {
            Map.Entry<IKey<?>, Object> e = i.next();
            Object value = e.getValue();
            sb.append(e.getKey())
                    .append('=')
                    .append(value == this ? "(this Map)" : value);
            if(!i.hasNext()) return sb.append('}').toString();
            sb.append(", ");
        }
    }

    public interface IKey<T> {

        Class<T> valClass();

        static <T> IKey<T> of(Class<T> valClass, String name) {
            return new IKey<T>() {
                @Override
                public Class<T> valClass() {
                    return valClass;
                }

                @Override
                public String toString() {
                    return name;
                }
            };
        }

        @SuppressWarnings("unchecked")
        static <T extends C, C> IKey<T> ofUnchecked(Class<C> valClass, String name) {
            return (IKey<T>) of(valClass, name);
        }

    }

}
