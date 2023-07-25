package de.cyklon.jengine.render;

public abstract class JEngineIcon {

    public abstract int size();
    public abstract int x();
    public abstract int y();
    protected abstract Style style();
    protected abstract Variant variant();

    public int getStyle() {
        return style().getType();
    }

    public int getVariant() {
        return variant().getType();
    }

    protected int width() {
        return size();
    }

    protected int height() {
        return size()/2;
    }

    protected enum Style {
        BRIGHT(0),
        DARK(1);

        private final int type;

        Style(int type) {
            this.type = type;
        }

        public int getType() {
            return type;
        }
    }

    protected enum Variant {
        DEFAULT(0);

        private final int type;

        Variant(int type) {
            this.type = type;
        }

        public int getType() {
            return type;
        }
    }

}
