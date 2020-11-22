package eutros.tetrapsi.data.image;

import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.util.math.MathHelper;

import static net.minecraft.client.renderer.texture.NativeImage.*;

public interface Pixel {

    class RGBA implements Pixel {

        private final int r, g, b, a;

        RGBA(int r, int g, int b, int a) {
            this.r = r;
            this.g = g;
            this.b = b;
            this.a = a;
        }

        @Override
        public String toString() {
            return "r: " + r + ", g: " + g + ", b: " + b + ", a: " + a;
        }

        @Override
        public int hashCode() {
            return getCombined(a, b, g, r);
        }

        @Override
        public boolean equals(Object obj) {
            if(obj == this) return true;
            if(obj == null) return false;
            if(obj.getClass() != getClass()) return false;
            RGBA pix = (RGBA) obj;
            return pix.r == r && pix.g == g && pix.b == b && pix.a == a;
        }

        @Override
        public int r() {
            return r;
        }

        @Override
        public int g() {
            return g;
        }

        @Override
        public int b() {
            return b;
        }

        @Override
        public int a() {
            return a;
        }

        private Pixel hsvCache;

        private Pixel asHSV() {
            if(hsvCache == null) {
                double cMax = Math.max(rd(), Math.max(gd(), bd()));
                double cMin = Math.min(rd(), Math.min(gd(), bd()));
                double d = cMax - cMin;
                hsvCache = of(
                        d == 0 ? 0 :
                        cMax == rd() ? (((gd() - bd()) / d) % 6) / 6.0 :
                        cMax == gd() ? (((bd() - rd()) / d) + 2) / 6.0 :
                        (((rd() - gd()) / d) + 4) / 6.0,
                        cMax == 0 ? 0 : d / cMax,
                        cMax
                ).withA(a);
            }
            return hsvCache;
        }

        @Override
        public double h() {
            return asHSV().h();
        }

        @Override
        public double s() {
            return asHSV().s();
        }

        @Override
        public double v() {
            return asHSV().v();
        }

    }

    class HSVA implements Pixel {

        final double h, s, v, a;

        HSVA(double h, double s, double v, double a) {
            this.h = h;
            this.s = s;
            this.v = v;
            this.a = a;
        }

        @Override
        public int hashCode() {
            return ((Double.hashCode(h)
                    * 31 + Double.hashCode(s))
                    * 31 + Double.hashCode(v))
                    * 31 + Double.hashCode(a);
        }

        @Override
        public boolean equals(Object obj) {
            if(obj == this) return true;
            if(obj == null) return false;
            if(obj.getClass() != getClass()) return false;
            HSVA pix = (HSVA) obj;
            return pix.h == h && pix.s == s && pix.v == v && pix.a == a;
        }

        @Override
        public String toString() {
            return "h: " + h + ", s: " + s + ", v: " + v + ", a: " + a;
        }

        private boolean between(double h, double min, double max) {
            return h >= min && h < max;
        }

        private Pixel rgbCache;

        private Pixel asRGB() {
            if(rgbCache == null) {
                double c = v * s;
                double x = c * (1 - Math.abs(((h * 6) % 2) - 1));
                double m = v - c;
                rgbCache = between(h, 0 / 6.0, 1 / 6.0) ? of(c + m, x + m, m, a) :
                           between(h, 1 / 6.0, 2 / 6.0) ? of(x + m, c + m, m, a) :
                           between(h, 2 / 6.0, 3 / 6.0) ? of(m, c + m, x + m, a) :
                           between(h, 3 / 6.0, 4 / 6.0) ? of(m, x + m, c + m, a) :
                           between(h, 4 / 6.0, 5 / 6.0) ? of(x + m, m, c + m, a) :
                           of(c + m, m, x + m, a);
            }
            return rgbCache;
        }

        @Override
        public int r() {
            return asRGB().r();
        }

        @Override
        public int g() {
            return asRGB().g();
        }

        @Override
        public int b() {
            return asRGB().b();
        }

        @Override
        public int a() {
            return asRGB().a();
        }

        @Override
        public double h() {
            return h;
        }

        @Override
        public double s() {
            return s;
        }

        @Override
        public double v() {
            return v;
        }

        @Override
        public double ad() {
            return a;
        }

        @Override
        public Pixel withA(int a) {
            return withA(ad());
        }

        @Override
        public Pixel withA(double a) {
            return new HSVA(h, s, v, a);
        }

    }

    static Pixel of(int r, int g, int b, int a) {
        return new RGBA(r, g, b, a);
    }

    static Pixel of(double r, double g, double b, double a) {
        return of(
                (int) (r * 255),
                (int) (g * 255),
                (int) (b * 255),
                (int) (a * 255)
        );
    }

    static Pixel of(double h, double s, double v) {
        return new HSVA(h, s, v, 1);
    }

    static Pixel of(int rgba) {
        return of(
                getRed(rgba),
                getGreen(rgba),
                getBlue(rgba),
                getAlpha(rgba)
        );
    }

    static Pixel from(NativeImage image, int x, int y) {
        return of(image.getPixelRGBA(x, y));
    }

    default void set(NativeImage image, int x, int y) {
        image.setPixelRGBA(x, y, getRGBA());
    }

    default int getRGBA() {
        return getCombined(a(), b(), g(), r());
    }

    int r();

    int g();

    int b();

    int a();

    double h();

    double s();

    double v();

    default double rd() {
        return r() / 255.0;
    }

    default double gd() {
        return g() / 255.0;
    }

    default double bd() {
        return b() / 255.0;
    }

    default double ad() {
        return a() / 255.0;
    }

    default Pixel withR(int r) {
        return of(r, g(), b(), a());
    }

    default Pixel withG(int g) {
        return of(r(), g, b(), a());
    }

    default Pixel withB(int b) {
        return of(r(), g(), b, a());
    }

    default Pixel withA(int a) {
        return of(r(), g(), b(), a);
    }

    default Pixel withR(double r) {
        return of(r, gd(), bd(), ad());
    }

    default Pixel withG(double g) {
        return of(rd(), g, bd(), ad());
    }

    default Pixel withB(double b) {
        return of(rd(), gd(), b, ad());
    }

    default Pixel withA(double a) {
        return of(rd(), gd(), bd(), a);
    }

    default Pixel withH(double h) {
        return of(h, s(), v()).withA(ad());
    }

    default Pixel withS(double s) {
        return of(h(), s, v()).withA(ad());
    }

    default Pixel withV(double v) {
        return of(h(), s(), v).withA(ad());
    }

    static Pixel lerp(Pixel one, Pixel other, double pct) {
        return of(
                MathHelper.lerp(pct, one.rd(), other.rd()),
                MathHelper.lerp(pct, one.gd(), other.gd()),
                MathHelper.lerp(pct, one.bd(), other.bd()),
                MathHelper.lerp(pct, one.ad(), other.ad())
        );
    }

    static Pixel mul(Pixel one, Pixel other) {
        return of(
                one.rd() * other.rd(),
                one.gd() * other.gd(),
                one.bd() * other.bd(),
                one.ad() * other.ad()
        );
    }

}
