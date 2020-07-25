package eutros.tetraits.util;

import com.google.common.collect.ImmutableMap;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.spi.AbstractLogger;

import java.util.ArrayDeque;
import java.util.Map;
import java.util.Queue;
import java.util.function.Consumer;

public class TextComponentLogger extends AbstractLogger {

    private static final Map<Level, TextFormatting> styleMap = ImmutableMap.<Level, TextFormatting>builder()
            .put(Level.TRACE, TextFormatting.WHITE)
            .put(Level.DEBUG, TextFormatting.DARK_AQUA)
            .put(Level.INFO, TextFormatting.YELLOW)
            .put(Level.WARN, TextFormatting.GOLD)
            .put(Level.ERROR, TextFormatting.RED)
            .put(Level.FATAL, TextFormatting.DARK_RED)
            .build();

    private final Logger delegate;

    private Queue<ITextComponent> elements = new ArrayDeque<>();
    private boolean recording = false;

    public void record() {
        recording = true;
    }

    public void forEach(Consumer<ITextComponent> consumer) {
        ITextComponent element;
        while(true) {
            element = elements.poll();
            if(element == null) break;
            consumer.accept(element);
        }
        recording = false;
    }

    public TextComponentLogger(Logger delegate) {
        this.delegate = delegate;
    }

    @Override
    public void logMessage(String fqcn, Level level, Marker marker, Message message, Throwable t) {
        if(recording) {
            elements.add(new StringTextComponent(message.getFormattedMessage())
                    .applyTextStyle(styleMap.getOrDefault(level, TextFormatting.WHITE)));
        }
        delegate.log(level, marker, message, t);
    }

    @Override
    public Level getLevel() {
        return delegate.getLevel();
    }

    @Override
    public boolean isEnabled(Level level, Marker marker, Message message, Throwable t) {
        return getLevel().intLevel() >= level.intLevel();
    }

    @Override
    public boolean isEnabled(Level level, Marker marker, CharSequence message, Throwable t) {
        return getLevel().intLevel() >= level.intLevel();
    }

    @Override
    public boolean isEnabled(Level level, Marker marker, Object message, Throwable t) {
        return getLevel().intLevel() >= level.intLevel();
    }

    @Override
    public boolean isEnabled(Level level, Marker marker, String message, Throwable t) {
        return getLevel().intLevel() >= level.intLevel();
    }

    @Override
    public boolean isEnabled(Level level, Marker marker, String message) {
        return getLevel().intLevel() >= level.intLevel();
    }

    @Override
    public boolean isEnabled(Level level, Marker marker, String message, Object... params) {
        return getLevel().intLevel() >= level.intLevel();
    }

    @Override
    public boolean isEnabled(Level level, Marker marker, String message, Object p0) {
        return getLevel().intLevel() >= level.intLevel();
    }

    @Override
    public boolean isEnabled(Level level, Marker marker, String message, Object p0, Object p1) {
        return getLevel().intLevel() >= level.intLevel();
    }

    @Override
    public boolean isEnabled(Level level, Marker marker, String message, Object p0, Object p1, Object p2) {
        return getLevel().intLevel() >= level.intLevel();
    }

    @Override
    public boolean isEnabled(Level level, Marker marker, String message, Object p0, Object p1, Object p2, Object p3) {
        return getLevel().intLevel() >= level.intLevel();
    }

    @Override
    public boolean isEnabled(Level level, Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4) {
        return getLevel().intLevel() >= level.intLevel();
    }

    @Override
    public boolean isEnabled(Level level, Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5) {
        return getLevel().intLevel() >= level.intLevel();
    }

    @Override
    public boolean isEnabled(Level level, Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6) {
        return getLevel().intLevel() >= level.intLevel();
    }

    @Override
    public boolean isEnabled(Level level, Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7) {
        return getLevel().intLevel() >= level.intLevel();
    }

    @Override
    public boolean isEnabled(Level level, Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8) {
        return getLevel().intLevel() >= level.intLevel();
    }

    @Override
    public boolean isEnabled(Level level, Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9) {
        return getLevel().intLevel() >= level.intLevel();
    }

}
