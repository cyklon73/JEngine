package de.cyklon.jengine.render;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.*;
import java.awt.image.renderable.RenderableImage;
import java.text.AttributedCharacterIterator;
import java.util.HashMap;
import java.util.Map;

public final class EmptyGraphics extends Graphics2D {
    @Override
    public void draw(Shape s) {

    }

    @Override
    public boolean drawImage(Image img, AffineTransform xform, ImageObserver obs) {
        return false;
    }

    @Override
    public void drawImage(BufferedImage img, BufferedImageOp op, int x, int y) {

    }

    @Override
    public void drawRenderedImage(RenderedImage img, AffineTransform xform) {

    }

    @Override
    public void drawRenderableImage(RenderableImage img, AffineTransform xform) {

    }

    @Override
    public void drawString(String str, int x, int y) {

    }

    @Override
    public void drawString(String str, float x, float y) {

    }

    @Override
    public void drawString(AttributedCharacterIterator iterator, int x, int y) {

    }

    @Override
    public boolean drawImage(Image img, int x, int y, ImageObserver observer) {
        return false;
    }

    @Override
    public boolean drawImage(Image img, int x, int y, int width, int height, ImageObserver observer) {
        return false;
    }

    @Override
    public boolean drawImage(Image img, int x, int y, Color bgcolor, ImageObserver observer) {
        return false;
    }

    @Override
    public boolean drawImage(Image img, int x, int y, int width, int height, Color bgcolor, ImageObserver observer) {
        return false;
    }

    @Override
    public boolean drawImage(Image img, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2, ImageObserver observer) {
        return false;
    }

    @Override
    public boolean drawImage(Image img, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2, Color bgcolor, ImageObserver observer) {
        return false;
    }

    @Override
    public void dispose() {

    }

    @Override
    public void drawString(AttributedCharacterIterator iterator, float x, float y) {

    }

    @Override
    public void drawGlyphVector(GlyphVector g, float x, float y) {

    }

    @Override
    public void fill(Shape s) {

    }

    @Override
    public boolean hit(Rectangle rect, Shape s, boolean onStroke) {
        return false;
    }

    @Override
    public GraphicsConfiguration getDeviceConfiguration() {
        return new GraphicsConfiguration() {
            @Override
            public GraphicsDevice getDevice() {
                final GraphicsConfiguration config = this;
                return new GraphicsDevice() {
                    @Override
                    public int getType() {
                        return 0;
                    }

                    @Override
                    public String getIDstring() {
                        return "";
                    }

                    @Override
                    public GraphicsConfiguration[] getConfigurations() {
                        return new GraphicsConfiguration[0];
                    }

                    @Override
                    public GraphicsConfiguration getDefaultConfiguration() {
                        return config;
                    }
                };
            }

            @Override
            public ColorModel getColorModel() {
                return new ColorModel(8) {
                    @Override
                    public int getRed(int pixel) {
                        return 0;
                    }

                    @Override
                    public int getGreen(int pixel) {
                        return 0;
                    }

                    @Override
                    public int getBlue(int pixel) {
                        return 0;
                    }

                    @Override
                    public int getAlpha(int pixel) {
                        return 0;
                    }
                };
            }

            @Override
            public ColorModel getColorModel(int transparency) {
                return getColorModel();
            }

            @Override
            public AffineTransform getDefaultTransform() {
                return new AffineTransform();
            }

            @Override
            public AffineTransform getNormalizingTransform() {
                return getDefaultTransform();
            }

            @Override
            public Rectangle getBounds() {
                return new Rectangle(10, 10, 10, 10);
            }
        };
    }

    @Override
    public void setComposite(Composite comp) {

    }

    @Override
    public void setPaint(Paint paint) {

    }

    @Override
    public void setStroke(Stroke s) {

    }

    @Override
    public void setRenderingHint(RenderingHints.Key hintKey, Object hintValue) {

    }

    @Override
    public Object getRenderingHint(RenderingHints.Key hintKey) {
        return new Object();
    }

    @Override
    public void setRenderingHints(Map<?, ?> hints) {

    }

    @Override
    public void addRenderingHints(Map<?, ?> hints) {

    }

    @Override
    public RenderingHints getRenderingHints() {
        return new RenderingHints(new HashMap<>());
    }

    @Override
    public Graphics create() {
        return this;
    }

    @Override
    public void translate(int x, int y) {

    }

    @Override
    public Color getColor() {
        return Color.BLACK;
    }

    @Override
    public void setColor(Color c) {

    }

    @Override
    public void setPaintMode() {

    }

    @Override
    public void setXORMode(Color c1) {

    }

    @Override
    public Font getFont() {
        return new Font("Arial", Font.PLAIN, 12);
    }

    @Override
    public void setFont(Font font) {

    }

    @Override
    public FontMetrics getFontMetrics(Font f) {
        return new FontMetrics(getFont()) {
            @Override
            public Font getFont() {
                return EmptyGraphics.this.getFont();
            }
        };
    }

    @Override
    public Rectangle getClipBounds() {
        return new Rectangle(10, 10, 10, 10);
    }

    @Override
    public void clipRect(int x, int y, int width, int height) {

    }

    @Override
    public void setClip(int x, int y, int width, int height) {

    }

    @Override
    public Shape getClip() {
        return new Shape() {
            @Override
            public Rectangle getBounds() {
                return new Rectangle(10, 10, 10, 10);
            }

            @Override
            public Rectangle2D getBounds2D() {
                return new Rectangle(10, 10, 10, 10);
            }

            @Override
            public boolean contains(double x, double y) {
                return false;
            }

            @Override
            public boolean contains(Point2D p) {
                return false;
            }

            @Override
            public boolean intersects(double x, double y, double w, double h) {
                return false;
            }

            @Override
            public boolean intersects(Rectangle2D r) {
                return false;
            }

            @Override
            public boolean contains(double x, double y, double w, double h) {
                return false;
            }

            @Override
            public boolean contains(Rectangle2D r) {
                return false;
            }

            @Override
            public PathIterator getPathIterator(AffineTransform at) {
                return new PathIterator() {
                    @Override
                    public int getWindingRule() {
                        return 0;
                    }

                    @Override
                    public boolean isDone() {
                        return false;
                    }

                    @Override
                    public void next() {

                    }

                    @Override
                    public int currentSegment(float[] coords) {
                        return 0;
                    }

                    @Override
                    public int currentSegment(double[] coords) {
                        return 0;
                    }
                };
            }

            @Override
            public PathIterator getPathIterator(AffineTransform at, double flatness) {
                return getPathIterator(getDeviceConfiguration().getDefaultTransform());
            }
        };
    }

    @Override
    public void setClip(Shape clip) {

    }

    @Override
    public void copyArea(int x, int y, int width, int height, int dx, int dy) {

    }

    @Override
    public void drawLine(int x1, int y1, int x2, int y2) {

    }

    @Override
    public void fillRect(int x, int y, int width, int height) {

    }

    @Override
    public void clearRect(int x, int y, int width, int height) {

    }

    @Override
    public void drawRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) {

    }

    @Override
    public void fillRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) {

    }

    @Override
    public void drawOval(int x, int y, int width, int height) {

    }

    @Override
    public void fillOval(int x, int y, int width, int height) {

    }

    @Override
    public void drawArc(int x, int y, int width, int height, int startAngle, int arcAngle) {

    }

    @Override
    public void fillArc(int x, int y, int width, int height, int startAngle, int arcAngle) {

    }

    @Override
    public void drawPolyline(int[] xPoints, int[] yPoints, int nPoints) {

    }

    @Override
    public void drawPolygon(int[] xPoints, int[] yPoints, int nPoints) {

    }

    @Override
    public void fillPolygon(int[] xPoints, int[] yPoints, int nPoints) {

    }

    @Override
    public void translate(double tx, double ty) {

    }

    @Override
    public void rotate(double theta) {

    }

    @Override
    public void rotate(double theta, double x, double y) {

    }

    @Override
    public void scale(double sx, double sy) {

    }

    @Override
    public void shear(double shx, double shy) {

    }

    @Override
    public void transform(AffineTransform Tx) {

    }

    @Override
    public void setTransform(AffineTransform Tx) {

    }

    @Override
    public AffineTransform getTransform() {
        return getDeviceConfiguration().getDefaultTransform();
    }

    @Override
    public Paint getPaint() {
        return new Paint() {
            @Override
            public PaintContext createContext(ColorModel cm, Rectangle deviceBounds, Rectangle2D userBounds, AffineTransform xform, RenderingHints hints) {
                return new PaintContext() {
                    @Override
                    public void dispose() {

                    }

                    @Override
                    public ColorModel getColorModel() {
                        return getDeviceConfiguration().getColorModel();
                    }

                    @Override
                    public Raster getRaster(int x, int y, int w, int h) {
                        return new ProtRaster(new SampleModel(1, 1, 1, 1) {
                            @Override
                            public int getNumDataElements() {
                                return 0;
                            }

                            @Override
                            public Object getDataElements(int x, int y, Object obj, DataBuffer data) {
                                return new Object();
                            }

                            @Override
                            public void setDataElements(int x, int y, Object obj, DataBuffer data) {

                            }

                            @Override
                            public int getSample(int x, int y, int b, DataBuffer data) {
                                return 0;
                            }

                            @Override
                            public void setSample(int x, int y, int b, int s, DataBuffer data) {

                            }

                            @Override
                            public SampleModel createCompatibleSampleModel(int w, int h) {
                                return this;
                            }

                            @Override
                            public SampleModel createSubsetSampleModel(int[] bands) {
                                return this;
                            }

                            @Override
                            public DataBuffer createDataBuffer() {
                                return new DataBuffer(1, 1, 1) {
                                    @Override
                                    public int getElem(int bank, int i) {
                                        return 0;
                                    }

                                    @Override
                                    public void setElem(int bank, int i, int val) {

                                    }
                                };
                            }

                            @Override
                            public int[] getSampleSize() {
                                return new int[0];
                            }

                            @Override
                            public int getSampleSize(int band) {
                                return 0;
                            }
                        }, new Point(x, y));
                    }
                };
            }

            @Override
            public int getTransparency() {
                return 0;
            }
        };
    }

    @Override
    public Composite getComposite() {
        return new Composite() {
            @Override
            public CompositeContext createContext(ColorModel srcColorModel, ColorModel dstColorModel, RenderingHints hints) {
                return new CompositeContext() {
                    @Override
                    public void dispose() {

                    }

                    @Override
                    public void compose(Raster src, Raster dstIn, WritableRaster dstOut) {

                    }
                };
            }
        };
    }

    @Override
    public void setBackground(Color color) {

    }

    @Override
    public Color getBackground() {
        return Color.BLACK;
    }

    @Override
    public Stroke getStroke() {
        return new Stroke() {
            @Override
            public Shape createStrokedShape(Shape p) {
                return p;
            }
        };
    }

    @Override
    public void clip(Shape s) {

    }

    @Override
    public FontRenderContext getFontRenderContext() {
        return new FRC();
    }

    private static class FRC extends FontRenderContext {
        public FRC() {
            super();
        }
    }

    private static class ProtRaster extends Raster {

        protected ProtRaster(SampleModel sampleModel, Point origin) {
            super(sampleModel, origin);
        }
    }
}
