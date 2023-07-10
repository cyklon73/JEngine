package de.cyklon.jengine.math;

public class Size {

    private double width, height;

    public Size() {
        this(0, 0);
    }

    public Size(double width, double height) {
        this.width = width;
        this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void set(double width, double height) {
        this.height = height;
        this.width = width;
    }

    public void add(double width, double height) {
        this.width += width;
        this.height += height;
    }

    public void subtract(double width, double height) {
        this.width -= width;
        this.height -= height;
    }

    public void times(double width, double height) {
        this.width *= width;
        this.height *= height;
    }

    public void divide(double width, double height) {
        this.width /= width;
        this.height /= height;
    }

    @Override
    public Size clone() {
        return new Size(width, height);
    }
}
