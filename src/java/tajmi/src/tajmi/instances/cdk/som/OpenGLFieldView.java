package tajmi.instances.cdk.som;

import com.sun.opengl.util.Animator;
import java.awt.Frame;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLJPanel;
import org.openscience.cdk.interfaces.IAtomContainer;
import scala.Tuple2;
import tajmi.abstracts.som.ViewField;
import tajmi.som.Field;
import tajmi.som.Position;

/**
 *
 * @author zacharias
 */
public class OpenGLFieldView extends ViewField {

    @Override
    public Object call() {
        Field<FieldModel<IAtomContainer>> f = getField();
        int length = (Integer) f.dimensions()._1();
        int width = (Integer) f.dimensions()._2();
        DisplayField df = new DisplayField(length, width, f);
//        df.setDimensions(length, width);
//        df.setField(f);
        df.displayFrame("SOM");
        return f;

    }

    public class DisplayField implements GLEventListener, MouseListener, MouseMotionListener {

        Random random_gen;
        private int length;
        private int width;
        private Field<FieldModel<IAtomContainer>> field;
        private Map<Integer, Float[]> colorRGBs = new Hashtable<Integer, Float[]>();
        private java.util.List<Integer> gl_list_pointers;

        public DisplayField(int length, int width, Field f) {
            random_gen = new Random(42);
            this.length = length;
            this.width = width;
            this.field = f;
        }

        public void setDimensions(int length, int width) {
            this.length = length;
            this.width = width;
        }

        public void setField(Field<FieldModel<IAtomContainer>> field) {
            this.field = field;
        }

        public Field<FieldModel<IAtomContainer>> getField() {
            return field;
        }

        public void displayFrame(String title) {
            Frame frame = new Frame(title);
            GLCanvas canvas = new GLCanvas();
            canvas.addGLEventListener(this);
            frame.add(canvas);
            frame.setSize(length, width);
            final Animator animator = new Animator(canvas);
            frame.addWindowListener(new WindowAdapter() {

                @Override
                public void windowClosing(WindowEvent e) {
                    // Run this on another thread than the AWT event queue to
                    // make sure the call to Animator.stop() completes before
                    // exiting
                    new Thread(new Runnable() {

                        public void run() {
                            animator.stop();
                            System.exit(0);
                        }
                    }).start();
                }
            });

            frame.setVisible(true);
            animator.start();
        }

        public void init(GLAutoDrawable drawable) {
            GL gl = drawable.getGL();
            gl.setSwapInterval(1);
//
            float pos[] = {5.0f, 5.0f, 10.0f, 0.0f};

            gl.glLightfv(GL.GL_LIGHT0, GL.GL_POSITION, pos, 0);
            gl.glEnable(GL.GL_CULL_FACE);
            gl.glEnable(GL.GL_LIGHTING);
            gl.glEnable(GL.GL_LIGHT0);
            gl.glEnable(GL.GL_DEPTH_TEST);

            /* make the list of points */
            gl_list_pointers = draw_me_dots(gl);
            gl.glEnable(GL.GL_NORMALIZE);
            drawable.addMouseListener(this);
            drawable.addMouseMotionListener(this);
        }

        public void display(GLAutoDrawable drawable) {

            GL gl = drawable.getGL();

            // Special handling for the case where the GLJPanel is translucent
            // and wants to be composited with other Java 2D content
            if ((drawable instanceof GLJPanel) &&
                    !((GLJPanel) drawable).isOpaque() &&
                    ((GLJPanel) drawable).shouldPreserveColorBufferIfTranslucent()) {
                gl.glClear(GL.GL_DEPTH_BUFFER_BIT);
            } else {
                gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
            }

            for (Integer pointer : gl_list_pointers) {
                gl.glCallList(pointer.intValue());
            }

        }

        public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
            GL gl = drawable.getGL();

            float h = (float) height / (float) width;

            gl.glMatrixMode(GL.GL_PROJECTION);

            System.err.println("GL_VENDOR: " + gl.glGetString(GL.GL_VENDOR));
            System.err.println("GL_RENDERER: " + gl.glGetString(GL.GL_RENDERER));
            System.err.println("GL_VERSION: " + gl.glGetString(GL.GL_VERSION));
            gl.glLoadIdentity();
            gl.glFrustum(-1.0f, 1.0f, -h, h, 5.0f, 60.0f);
            gl.glMatrixMode(GL.GL_MODELVIEW);
            gl.glLoadIdentity();
            gl.glTranslatef(0.0f, 0.0f, -50.0f);
        }

        public void displayChanged(GLAutoDrawable drawable, boolean arg1, boolean arg2) {
        }


        private java.util.List<Integer> draw_me_dots(GL gl) {
            Map<Integer, java.util.List<Tuple2<Position, FieldModel<IAtomContainer>>>> assigned_colors = color_me_dots();

            java.util.List<Integer> dot_pointers = new LinkedList();
            for (Integer color_id : assigned_colors.keySet()) {
                int dots = gl.glGenLists(1);
                gl.glNewList(dots, GL.GL_COMPILE);
                Float[] rgb = colorRGBs.get(color_id);
                // FIXME: hack
                if (rgb == null) {
                    rgb = generate_next_color();
                }
                for (Float f : rgb) {
                    System.out.print(f + ", ");
                }
                System.out.println();
                float[] color = {rgb[0], rgb[1], rgb[2]};
                gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT_AND_DIFFUSE, color, 0);
                
                list_me_dots(assigned_colors.get(color_id), gl);
                gl.glEndList();

                dot_pointers.add(dots);
            }
            return dot_pointers;
        }

        private void list_me_dots(java.util.List<Tuple2<Position, FieldModel<IAtomContainer>>> dots, GL gl) {
            gl.glPushMatrix();
            gl.glTranslated((-length / 2) + 0.5, -width / 2, 0);
            gl.glPointSize(10.0f);
            gl.glBegin(gl.GL_POINTS);

            for (Tuple2<Position, FieldModel<IAtomContainer>> model : dots) {
                int x = model._1().x();
                int y = model._1().y();
                gl.glVertex2i(x, y);
            }

            gl.glEnd();
            gl.glPopMatrix();
        }

        public Map color_me_dots() {
            Map<Integer, java.util.List<Tuple2<Position, FieldModel<IAtomContainer>>>> assigned_colors =
                    new HashMap<Integer, java.util.List<Tuple2<Position, FieldModel<IAtomContainer>>>>(field.size());
            Map<String, Integer> modelColors = new HashMap<String, Integer>();

            int color_position = 0;

            for (Tuple2<Position, FieldModel<IAtomContainer>> m : field) {
                if (m._2().getGeneralizeMedian() == null) continue;
                String id = m._2().getGeneralizeMedian().getID();
                if (!modelColors.containsKey(id)) {
                    Float[] new_color = generate_next_color();
                    colorRGBs.put(color_position, new_color);
                    color_position++;
                    
                    assigned_colors.put(color_position, new LinkedList<Tuple2<Position, FieldModel<IAtomContainer>>>());
                    modelColors.put(id, color_position);
                }

                int color_id = modelColors.get(id);
                assigned_colors.get(color_id).add(m);

            }

            return assigned_colors;
        }

        private Float[] generate_next_color() {
            float red = random_gen.nextFloat();// * random_gen.nextInt(235) + 20;
            float green = random_gen.nextFloat();// * random_gen.nextInt(235) + 20;
            float blue = random_gen.nextFloat();// * random_gen.nextInt(235) + 20;

            Float[] color = {red, green, blue};
            return color;
        }

        public void mouseClicked(MouseEvent e) {
        }

        public void mousePressed(MouseEvent e) {
        }

        public void mouseReleased(MouseEvent e) {
        }

        public void mouseEntered(MouseEvent e) {
        }

        public void mouseExited(MouseEvent e) {
        }

        public void mouseDragged(MouseEvent e) {
        }

        public void mouseMoved(MouseEvent e) {
        }
    }
}
