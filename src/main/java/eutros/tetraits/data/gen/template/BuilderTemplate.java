package eutros.tetraits.data.gen.template;

import java.util.LinkedList;
import java.util.function.Function;

public interface BuilderTemplate<T> {

    T apply(Pattern pattern);

    class TemplateBuilder<B> {

        private final LinkedList<Function<Pattern, Function<B, B>>> handlers;
        private Function<Pattern, B> starter;

        private TemplateBuilder() {
            handlers = new LinkedList<>();
        }

        public static <B> TemplateBuilder<B> create() {
            return new TemplateBuilder<>();
        }

        public TemplateBuilder<B> append(TemplateBuilder<B> other) {
            handlers.addAll(other.handlers);
            return this;
        }

        private TemplateBuilder(TemplateBuilder<B> other) {
            this.handlers = new LinkedList<>(other.handlers);
            this.starter = other.starter;
        }

        public TemplateBuilder<B> handle(Function<Pattern, Function<B, B>> handler) {
            handlers.add(handler);
            return this;
        }

        public TemplateBuilder<B> copy() {
            return new TemplateBuilder<>(this);
        }

        public TemplateBuilder<B> starter(Function<Pattern, B> starter) {
            this.starter = starter;
            return this;
        }

        public <T> BuilderTemplate<T> build(Function<B, T> finisher) {
            if(starter == null) throw new IllegalArgumentException("Starter not supplied!");
            return new BuilderTemplate<T>() {
                @Override
                public T apply(Pattern pattern) {
                    B b = starter.apply(pattern);
                    for(Function<Pattern, Function<B, B>> handler : handlers) {
                        b = handler.apply(pattern).apply(b);
                    }
                    return finisher.apply(b);
                }

                @Override
                public String toString() {
                    return handlers.toString();
                }
            };
        }

    }

}
