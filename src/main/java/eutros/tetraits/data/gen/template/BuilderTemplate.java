package eutros.tetraits.data.gen.template;

import java.util.LinkedList;
import java.util.function.Function;

public interface BuilderTemplate<T> {

    T apply(Pattern pattern);

    class TemplateBuilder<B> {

        private final LinkedList<Function<Pattern, Function<B, B>>> handlers;

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
        }

        public TemplateBuilder<B> handle(Function<Pattern, Function<B, B>> handler) {
            handlers.add(handler);
            return this;
        }

        public TemplateBuilder<B> copy() {
            return new TemplateBuilder<>(this);
        }


        public <T> BuilderTemplate<T> build(Function<Pattern, B> starter, Function<Pattern, Function<B, T>> finisher) {
            return pattern -> {
                B b = starter.apply(pattern);
                for(Function<Pattern, Function<B, B>> handler : handlers) {
                    b = handler.apply(pattern).apply(b);
                }
                return finisher.apply(pattern).apply(b);
            };
        }

    }

}
