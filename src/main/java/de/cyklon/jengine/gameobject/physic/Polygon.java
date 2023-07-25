package de.cyklon.jengine.gameobject.physic;


import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class Polygon extends JFrame {

    public static void main(String[] args) {
        Polygon renderer = new Polygon();
        renderer.setVisible(true);
    }

    private int[] xPoints;
    private int[] yPoints;
    private int numPoints;

    private int[] triangleXPoints;
    private int[] triangleYPoints;
    private int numTriangles;

    public Polygon() {
        setTitle("Polygon Renderer");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        int radius = 100;
        int centerX = 150;
        int centerY = 150;
        numPoints = 100; // Anzahl der Punkte (hier verwenden wir 360 Punkte für eine bessere Approximation)

        xPoints = new int[numPoints];
        yPoints = new int[numPoints];

        // Berechne die x- und y-Positionen für den Kreis
        for (int i = 0; i < numPoints; i++) {
            double angle = 2 * Math.PI * i / numPoints;
            xPoints[i] = (int) (centerX + radius * Math.cos(angle));
            yPoints[i] = (int) (centerY + radius * Math.sin(angle));
        }

        triangleXPoints = new int[3 * (numPoints - 2)]; // Maximal mögliche Anzahl an Dreiecken
        triangleYPoints = new int[3 * (numPoints - 2)];
        numTriangles = 0;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;


        // Zeichne das Polygon
        g2d.drawPolygon(xPoints, yPoints, numPoints);

        List<Point> poly = new ArrayList<>();

        for (int i = 0; i < xPoints.length; i++) {
            poly.add(new Point(xPoints[i], yPoints[i]));
        }

        poly = triangulatePolygon(poly);

        for (int i = 0; i < poly.size(); i+=3) {
            Point f = poly.get(i);
            Point s = poly.get(i+1);
            Point t = poly.get(i+2);
            g2d.drawPolygon(new int[] {f.x, s.x, t.x}, new int[] {f.y, s.y, t.y}, 3);
        }
    }


    public static List<Point> triangulatePolygon(List<Point> polygon) {
        List<Point> triangles = new ArrayList<>();

        // Prüfe, ob das Polygon gültig ist und mindestens 3 Ecken hat
        if (polygon == null || polygon.size() < 3) {
            throw new IllegalArgumentException("Ungültiges Polygon");
        }

        // Kopiere das Polygon, um es zu bearbeiten
        List<Point> remainingPoints = new ArrayList<>(polygon);

        while (remainingPoints.size() > 3) {
            // Suche eine "Ohr"-Spitze (eine konvexe Ecke, die kein anderes Polygonknoten im Inneren enthält)
            int earIndex = findEar(remainingPoints);

            // Dreieck für die gefundene Ohr-Spitze hinzufügen
            Point ear = remainingPoints.get(earIndex);
            Point prev = remainingPoints.get((earIndex + remainingPoints.size() - 1) % remainingPoints.size());
            Point next = remainingPoints.get((earIndex + 1) % remainingPoints.size());

            triangles.add(prev);
            triangles.add(ear);
            triangles.add(next);

            // Entferne die Ohrspitze aus der Liste der verbleibenden Punkte
            remainingPoints.remove(earIndex);
        }

        // Füge das letzte verbleibende Dreieck hinzu (die letzten drei Punkte im Polygon)
        triangles.addAll(remainingPoints);

        return triangles;
    }

    private static int findEar(List<Point> points) {
        int n = points.size();
        for (int i = 0; i < n; i++) {
            Point prev = points.get((i + n - 1) % n);
            Point current = points.get(i);
            Point next = points.get((i + 1) % n);

            // Prüfe, ob die aktuelle Ecke eine konvexe Ecke ist
            if (isConvex(prev, current, next)) {
                boolean isEar = true;
                // Prüfe, ob sich andere Punkte im Inneren des gefundenen Dreiecks befinden
                for (int j = 0; j < n; j++) {
                    if (j != i && j != (i + n - 1) % n && j != (i + 1) % n) {
                        Point point = points.get(j);
                        if (isInsideTriangle(prev, current, next, point)) {
                            isEar = false;
                            break;
                        }
                    }
                }
                if (isEar) {
                    return i;
                }
            }
        }
        throw new IllegalArgumentException("Keine Ohr-Spitze gefunden");
    }

    private static boolean isConvex(Point a, Point b, Point c) {
        // Kreuzprodukt zweier Vektoren: ab x bc
        return (b.x - a.x) * (c.y - b.y) - (c.x - b.x) * (b.y - a.y) >= 0;
    }

    private static boolean isInsideTriangle(Point a, Point b, Point c, Point p) {
        // Punkt-in-Dreieck-Test
        double areaABC = 0.5 * Math.abs(a.x * (b.y - c.y) + b.x * (c.y - a.y) + c.x * (a.y - b.y));
        double areaPBC = 0.5 * Math.abs(p.x * (b.y - c.y) + b.x * (c.y - p.y) + c.x * (p.y - b.y));
        double areaPCA = 0.5 * Math.abs(p.x * (c.y - a.y) + c.x * (a.y - p.y) + a.x * (p.y - c.y));
        double areaPAB = 0.5 * Math.abs(p.x * (a.y - b.y) + a.x * (b.y - p.y) + b.x * (p.y - a.y));

        double triangleAreaSum = areaPBC + areaPCA + areaPAB;

        return Math.abs(triangleAreaSum - areaABC) <= 1e-9; // Toleranz für Gleitkommavergleiche
    }

    private void triangulate() {
        // Kopiere die ursprünglichen Punkte in temporäre Arrays
        int[] xCopy = Arrays.copyOf(xPoints, numPoints);
        int[] yCopy = Arrays.copyOf(yPoints, numPoints);

        numTriangles = 0;

        // Dreiecke aufteilen, bis das Polygon vollständig trianguliert ist
        while (numPoints > 3) {
            for (int i = 0; i < numPoints; i++) {
                int prevIndex = (i + numPoints - 1) % numPoints;
                int nextIndex = (i + 1) % numPoints;

                // Überprüfe, ob der Winkel an der aktuellen Ecke konvex ist
                if (isConvex(xCopy[prevIndex], yCopy[prevIndex], xCopy[i], yCopy[i], xCopy[nextIndex], yCopy[nextIndex])) {
                    // Überprüfe, ob sich die Ecke im Polygon befindet (nicht im Dreieck)
                    boolean isEar = true;
                    for (int j = 0; j < numPoints; j++) {
                        if (j != prevIndex && j != i && j != nextIndex && isPointInTriangle(xCopy[j], yCopy[j], xCopy[prevIndex], yCopy[prevIndex], xCopy[i], yCopy[i], xCopy[nextIndex], yCopy[nextIndex])) {
                            isEar = false;
                            break;
                        }
                    }

                    // Wenn es ein Ohr ist, füge das Dreieck hinzu und entferne die Ecke
                    if (isEar) {
                        triangleXPoints[numTriangles * 3] = xCopy[prevIndex];
                        triangleYPoints[numTriangles * 3] = yCopy[prevIndex];
                        triangleXPoints[numTriangles * 3 + 1] = xCopy[i];
                        triangleYPoints[numTriangles * 3 + 1] = yCopy[i];
                        triangleXPoints[numTriangles * 3 + 2] = xCopy[nextIndex];
                        triangleYPoints[numTriangles * 3 + 2] = yCopy[nextIndex];
                        numTriangles++;

                        // Entferne die Ecke
                        for (int j = i; j < numPoints - 1; j++) {
                            xCopy[j] = xCopy[j + 1];
                            yCopy[j] = yCopy[j + 1];
                        }
                        numPoints--;
                        break;
                    }
                }
            }
        }

        // Füge das letzte Dreieck hinzu (das restliche Polygon)
        triangleXPoints[numTriangles * 3] = xCopy[0];
        triangleYPoints[numTriangles * 3] = yCopy[0];
        triangleXPoints[numTriangles * 3 + 1] = xCopy[1];
        triangleYPoints[numTriangles * 3 + 1] = yCopy[1];
        triangleXPoints[numTriangles * 3 + 2] = xCopy[2];
        triangleYPoints[numTriangles * 3 + 2] = yCopy[2];
        numTriangles++;
    }

    private boolean isConvex(int x1, int y1, int x2, int y2, int x3, int y3) {
        int crossProduct = (x2 - x1) * (y3 - y2) - (y2 - y1) * (x3 - x2);
        return crossProduct >= 0;
    }

    private boolean isPointInTriangle(int x, int y, int x1, int y1, int x2, int y2, int x3, int y3) {
        int sign1 = (x - x2) * (y1 - y2) - (x1 - x2) * (y - y2);
        int sign2 = (x - x3) * (y2 - y3) - (x2 - x3) * (y - y3);
        int sign3 = (x - x1) * (y3 - y1) - (x3 - x1) * (y - y1);
        return (sign1 >= 0 && sign2 >= 0 && sign3 >= 0) || (sign1 <= 0 && sign2 <= 0 && sign3 <= 0);
    }


}
