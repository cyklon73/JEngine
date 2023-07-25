package de.cyklon.jengine.math;

public class Matrix {

    public Vector row1 = new Vector();
    public Vector row2 = new Vector();

    /**
     * Default constructor matrix [(0,0),(0,0)] by default.
     */
    public Matrix() {
    }

    /**
     * Constructs and sets the matrix up to be a rotation matrix that stores the angle specified in the matrix.
     * @param radians The desired angle of the rotation matrix
     */
    public Matrix(double radians) {
        this.set(radians);
    }

    /**
     * Sets the matrix up to be a rotation matrix that stores the angle specified in the matrix.
     * @param radians The desired angle of the rotation matrix
     */
    public void set(double radians) {
        double c = StrictMath.cos(radians);
        double s = StrictMath.sin(radians);

        row1.setX(c);
        row1.setY(-s);
        row2.setX(s);
        row2.setY(c);
    }

    /**
     * Sets current object matrix to be the same as the supplied parameters matrix.
     * @param m Matrix to set current object to
     */
    public void set(Matrix m) {
        row1.setX(m.row1.getX());
        row1.setY(m.row1.getY());
        row2.setX(m.row2.getX());
        row2.setY(m.row2.getY());
    }

    public Matrix transpose() {
        Matrix mat = new Matrix();
        mat.row1.setX(row1.getX());
        mat.row1.setY(row2.getX());
        mat.row2.setX(row1.getY());
        mat.row2.setY(row2.getY());
        return mat;
    }

    public Vector mul(Vector v) {
        double x = v.getX();
        double y = v.getY();
        v.setX(row1.getX() * x + row1.getY() * y);
        v.setY(row2.getX() * x + row2.getY() * y);
        return v;
    }

    public Vector mul(Vector v, Vector out) {
        out.setX(row1.getX() * v.getX() + row1.getY() * v.getY());
        out.setY(row2.getX() * v.getX() + row2.getY() * v.getY());
        return out;
    }

    public static void main(String[] args) {
        Vector test = new Vector(5, 0);
        Matrix m = new Matrix();
        m.set(0.5);
        m.mul(test);
        System.out.println(test);
    }

    @Override
    public String toString() {
        return row1 + "\n" + row2;
    }
}
