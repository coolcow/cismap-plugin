/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.tools.gui;
/*
 * Copyright 2004 Sun Microsystems, Inc., 4150 Network Circle,
 * Santa Clara, California 95054, U.S.A. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 *
 *
 *
 *
 */

import org.jdesktop.swingx.VerticalLayout;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Composite;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

/**
 * <code>JXCollapsiblePane</code> provides a component which can collapse or expand its content area with animation and
 * fade in/fade out effects. It also acts as a standard container for other Swing components.
 *
 * <p>In this example, the <code>JXCollapsiblePane</code> is used to build a Search pane which can be shown and hidden
 * on demand.</p>
 *
 * <pre>
   <code>
   JXCollapsiblePane cp = new JXCollapsiblePane();

   // JXCollapsiblePane can be used like any other container
   cp.setLayout(new BorderLayout());

   // the Controls panel with a textfield to filter the tree
   JPanel controls = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 0));
   controls.add(new JLabel("Search:"));
   controls.add(new JTextField(10));
   controls.add(new JButton("Refresh"));
   controls.setBorder(new TitledBorder("Filters"));
   cp.add("Center", controls);

   JXFrame frame = new JXFrame();
   frame.setLayout(new BorderLayout());

   // Put the "Controls" first
   frame.add("North", cp);

   // Then the tree - we assume the Controls would somehow filter the tree
   JScrollPane scroll = new JScrollPane(new JTree());
   frame.add("Center", scroll);

   // Show/hide the "Controls"
   JButton toggle = new JButton(cp.getActionMap().get(JXCollapsiblePane.TOGGLE_ACTION));
   toggle.setText("Show/Hide Search Panel");
   frame.add("South", toggle);

   frame.pack();
   frame.setVisible(true);
   </code>
 * </pre>
 *
 * <p>The <code>JXCollapsiblePane</code> has a default toggle action registered under the name {@link #TOGGLE_ACTION}.
 * Bind this action to a button and pressing the button will automatically toggle the pane between expanded and
 * collapsed states. Additionally, you can define the icons to use through the {@link #EXPAND_ICON} and
 * {@link #COLLAPSE_ICON} properties on the action. Example</p>
 *
 * <pre>
   <code>
   // get the built-in toggle action
   Action toggleAction = collapsible.getActionMap().
     get(JXCollapsiblePane.TOGGLE_ACTION);

   // use the collapse/expand icons from the JTree UI
   toggleAction.putValue(JXCollapsiblePane.COLLAPSE_ICON,
                         UIManager.getIcon("Tree.expandedIcon"));
   toggleAction.putValue(JXCollapsiblePane.EXPAND_ICON,
                         UIManager.getIcon("Tree.collapsedIcon"));
   </code>
 * </pre>
 *
 * <p>Note: <code>JXCollapsiblePane</code> requires its parent container to have a {@link java.awt.LayoutManager} using
 * {@link #getPreferredSize()} when calculating its layout (example {@link org.jdesktop.swingx.VerticalLayout},
 * {@link java.awt.BorderLayout}).</p>
 *
 * <p>If you want to use the panel with horizontal collapsing. Just do this:</p>
 *
 * <pre>
   <code>
   jXCollapsiblePanelInstance.setOrientation(JXCollapsiblePane.HORIZONTAL_ORIENTATION);
   </code>


   @author rbair (from the JDNC project)
   @author <a href="mailto:fred@L2FProd.com">Frederic Lavigne</a>
   @author thorsten DOT hell AT cismet DOT de (added horizontal collapse)
   @javabean.attribute name="isContainer"
            value="Boolean.TRUE"
            rtexpr="true"
   @javabean.attribute name="containerDelegate"
            value="getContentPane"
   @javabean.class name="JXCollapsiblePane"
            shortDescription="A pane which hides its content with an animation."
            stopClass="java.awt.Component"
 </pre>
 *
 * @version  $Revision$, $Date$
 */
public class JXCollapsiblePane extends JPanel {

    //~ Static fields/initializers ---------------------------------------------

    /** Use serialVersionUID for interoperability. */
    private static final long serialVersionUID = -745515378817550094L;

    /**
     * Used when generating PropertyChangeEvents for the "animationState" property. The PropertyChangeEvent will takes
     * the following different values for {@link PropertyChangeEvent#getNewValue()}:
     *
     * <ul>
     *   <li><code>reinit</code> every time the animation starts</li>
     *   <li><code>expanded</code> when the animation ends and the pane is expanded</li>
     *   <li><code>collapsed</code> when the animation ends and the pane is collapsed</li>
     * </ul>
     */
    public static final String ANIMATION_STATE_KEY = "animationState";               // NOI18N

    /**
     * JXCollapsible has a built-in toggle action which can be bound to buttons. Accesses the action through <code>
     * collapsiblePane.getActionMap().get(JXCollapsiblePane.TOGGLE_ACTION)</code>.
     */
    public static final String TOGGLE_ACTION = "toggle";                                                     // NOI18N

    /**
     * The icon used by the "toggle" action when the JXCollapsiblePane is expanded, i.e the icon which indicates the
     * pane can be collapsed.
     */
    public static final String COLLAPSE_ICON = "collapseIcon"; // NOI18N

    /**
     * The icon used by the "toggle" action when the JXCollapsiblePane is collapsed, i.e the icon which indicates the
     * pane can be expanded.
     */
    public static final String EXPAND_ICON = "expandIcon"; // NOI18N

    /** Use this constant if you like the old vertical collapsing behaviour. */
    public static final String VERTICAL_ORIENTATION = "VERTICAL";           // NOI18N

    /** Use this constant if you want to collapse the component horizontally. */
    public static final String HORIZONTAL_ORIENTATION = "HORIZONTAL";        // NOI18N

    //~ Instance fields --------------------------------------------------------

    /** Indicates whether the component is collapsed or expanded. */
    private boolean collapsed = false;

    /** Timer used for doing the transparency animation (fade-in). */
    private Timer animateTimer;
    private AnimationListener animator;
    private int currentHeightOrWidth = -1;
    private WrapperContainer wrapper;
    private boolean useAnimation = true;
    private AnimationParams animationParams;

    /** Indicates whether the component is collapsed horizontally or vertically. */
    private String orientation = VERTICAL_ORIENTATION;

    //~ Constructors -----------------------------------------------------------

    /**
     * Constructs a new JXCollapsiblePane with a {@link JPanel} as content pane and a vertical {@link VerticalLayout}
     * with a gap of 2 pixels as layout manager.
     */
    public JXCollapsiblePane() {
        super.setLayout(new BorderLayout(0, 0));

        final JPanel panel = new JPanel();
        panel.setLayout(new VerticalLayout(2));
        setContentPane(panel);

        animator = new AnimationListener();
        setAnimationParams(new AnimationParams(30, 8, 0.01f, 1.0f));

        // add an action to automatically toggle the state of the pane
        getActionMap().put(TOGGLE_ACTION, new ToggleAction());
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * Sets the collapsing orientation of this JXCollapsiblePane.
     *
     * @param   orientation  DOCUMENT ME!
     *
     * @throws  IllegalArgumentException  if the orientation string does not match the existing constants
     *
     * @see     #HORIZONTAL_ORIENTATION
     * @see     #VERTICAL_ORIENTATION
     */

    public void setOrientation(final String orientation) {
        if (!orientation.equals(JXCollapsiblePane.HORIZONTAL_ORIENTATION)
                    && !orientation.equals(JXCollapsiblePane.VERTICAL_ORIENTATION)) {
            throw new IllegalArgumentException("orientation must be either " // NOI18N
                        + "JXCollapsiblePane.HORIZONTAL_ORIENTATION or " // NOI18N
                        + "JXCollapsiblePane.VERTICAL_ORIENTATION");     // NOI18N
        }
        this.orientation = orientation;
    }

    /**
     * Sets the content pane of this JXCollapsiblePane. Components must be added to this content pane, not to the
     * JXCollapsiblePane.
     *
     * @param   contentPanel  DOCUMENT ME!
     *
     * @throws  IllegalArgumentException  if contentPanel is null
     */
    public void setContentPane(final Container contentPanel) {
        if (contentPanel == null) {
            throw new IllegalArgumentException("Content pane can't be null"); // NOI18N
        }

        if (wrapper != null) {
            // these next two lines are as they are because if I try to remove
            // the "wrapper" component directly, then super.remove(comp) ends up
            // calling remove(int), which is overridden in this class, leading to
            // improper behavior.
            assert super.getComponent(0) == wrapper;
            super.remove(0);
        }
        wrapper = new WrapperContainer(contentPanel);
        super.addImpl(wrapper, BorderLayout.CENTER, -1);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  the content pane
     */
    public Container getContentPane() {
        return wrapper.c;
    }

    /**
     * Overriden to redirect call to the content pane.
     *
     * @param  mgr  DOCUMENT ME!
     */
    @Override
    public void setLayout(final LayoutManager mgr) {
        // wrapper can be null when setLayout is called by "super()" constructor
        if (wrapper != null) {
            getContentPane().setLayout(mgr);
        }
    }

    /**
     * Overriden to redirect call to the content pane.
     *
     * @param  comp         DOCUMENT ME!
     * @param  constraints  DOCUMENT ME!
     * @param  index        DOCUMENT ME!
     */
    @Override
    protected void addImpl(final Component comp, final Object constraints, final int index) {
        getContentPane().add(comp, constraints, index);
    }

    /**
     * Overriden to redirect call to the content pane.
     *
     * @param  comp  DOCUMENT ME!
     */
    @Override
    public void remove(final Component comp) {
        getContentPane().remove(comp);
    }

    /**
     * Overriden to redirect call to the content pane.
     *
     * @param  index  DOCUMENT ME!
     */
    @Override
    public void remove(final int index) {
        getContentPane().remove(index);
    }

    /**
     * Overriden to redirect call to the content pane.
     */
    @Override
    public void removeAll() {
        getContentPane().removeAll();
    }

    /**
     * If true, enables the animation when pane is collapsed/expanded. If false, animation is turned off.
     *
     * <p>When animated, the <code>JXCollapsiblePane</code> will progressively reduce (when collapsing) or enlarge (when
     * expanding) the height or width of its content area until it becomes 0 or until it reaches the preferred height or
     * width of the components it contains. The transparency of the content area will also change during the animation.
     * </p>
     *
     * <p>If not animated, the <code>JXCollapsiblePane</code> will simply hide (collapsing) or show (expanding) its
     * content area.</p>
     *
     * @param  animated  DOCUMENT ME!
     *
     * @javabean.property
     *           bound     = "true"
     *           preferred = "true"
     */
    public void setAnimated(final boolean animated) {
        if (animated != useAnimation) {
            useAnimation = animated;
            firePropertyChange("animated", !useAnimation, useAnimation); // NOI18N
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @return  true if the pane is animated, false otherwise
     *
     * @see     #setAnimated(boolean)
     */
    public boolean isAnimated() {
        return useAnimation;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  true if the pane is collapsed, false if expanded
     */
    public boolean isCollapsed() {
        return collapsed;
    }

    /**
     * Expands or collapses this <code>JXCollapsiblePane</code>.
     *
     * <p>If the component is collapsed and <code>val</code> is false, then this call expands the JXCollapsiblePane,
     * such that the entire JXCollapsiblePane will be visible. If {@link #isAnimated()} returns true, the expansion will
     * be accompanied by an animation.</p>
     *
     * <p>However, if the component is expanded and <code>val</code> is true, then this call collapses the
     * JXCollapsiblePane, such that the entire JXCollapsiblePane will be invisible. If {@link #isAnimated()} returns
     * true, the collapse will be accompanied by an animation.</p>
     *
     * @param  val  DOCUMENT ME!
     *
     * @see    #isAnimated()
     * @see    #setAnimated(boolean)
     *
     * @javabean.property
     *           bound     = "true"
     *           preferred = "true"
     */
    public void setCollapsed(final boolean val) {
        if (collapsed != val) {
            collapsed = val;
            if (isAnimated()) {
                if (collapsed) {
                    if (!orientation.equals(JXCollapsiblePane.HORIZONTAL_ORIENTATION)) {
                        setAnimationParams(new AnimationParams(30, Math.max(8, wrapper.getHeight() / 10), 1.0f, 0.01f));
                        animator.reinit(wrapper.getHeight(), 0);
                        animateTimer.start();
                    } else {
                        setAnimationParams(new AnimationParams(30, Math.max(8, wrapper.getWidth() / 10), 1.0f, 0.01f));
                        animator.reinit(wrapper.getWidth(), 0);
                        animateTimer.start();
                    }
                } else {
                    if (!orientation.equals(JXCollapsiblePane.HORIZONTAL_ORIENTATION)) {
                        setAnimationParams(new AnimationParams(
                                30,
                                Math.max(8,
                                    getContentPane().getPreferredSize().height
                                            / 10),
                                0.01f,
                                1.0f));
                        animator.reinit(wrapper.getHeight(), getContentPane().getPreferredSize().height);
                        animateTimer.start();
                    } else {
                        setAnimationParams(new AnimationParams(
                                30,
                                Math.max(8,
                                    getContentPane().getPreferredSize().width
                                            / 10),
                                0.01f,
                                1.0f));
                        animator.reinit(wrapper.getWidth(), getContentPane().getPreferredSize().width);
                        animateTimer.start();
                    }
                }
            } else {
                wrapper.c.setVisible(!collapsed);
                invalidate();
                doLayout();
            }
            repaint();

            firePropertyChange("collapsed", !collapsed, collapsed); // NOI18N
        }
    }

    @Override
    public Dimension getMinimumSize() {
        return getPreferredSize();
    }

    /**
     * The critical part of the animation of this <code>JXCollapsiblePane</code> relies on the calculation of its
     * preferred size. During the animation, its preferred size (specially its height or width ) will change, when
     * expanding, from 0 to the preferred size of the content pane, and the reverse when collapsing.
     *
     * @return  this component preferred size
     */
    @Override
    public Dimension getPreferredSize() {
        /*
         * The preferred size is calculated based on the current position of the component in its animation sequence. If
         * the Component is expanded, then the preferred size will be the preferred size of the top component plus the
         * preferred size of the embedded content container. <p>However, if the scroll up is in any state of animation,
         * the heightor width  component of the preferred size will be the current height or width  of the component (as
         * contained in the currentHeightOrWidth variable)
         */
        Dimension dim;
        if (!isAnimated()) {
            if (getContentPane().isVisible()) {
                dim = getContentPane().getPreferredSize();
            } else {
                dim = super.getPreferredSize();
            }
        } else {
            dim = new Dimension(getContentPane().getPreferredSize());
            if (!getContentPane().isVisible() && (currentHeightOrWidth != -1)) {
                if (!orientation.equals(JXCollapsiblePane.HORIZONTAL_ORIENTATION)) {
                    dim.height = currentHeightOrWidth;
                } else {
                    dim.width = currentHeightOrWidth;
                }
            }
        }
        return dim;
    }

    /**
     * Sets the parameters controlling the animation.
     *
     * @param   params  DOCUMENT ME!
     *
     * @throws  IllegalArgumentException  if params is null
     */
    private void setAnimationParams(final AnimationParams params) {
        if (params == null) {
            throw new IllegalArgumentException(
                "params can't be null");
        } // NOI18N
        if (animateTimer != null) {
            animateTimer.stop();
        }
        animationParams = params;
        animateTimer = new Timer(animationParams.waitTime, animator);
        animateTimer.setInitialDelay(0);
    }

    /**
     * DOCUMENT ME!
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        final org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(0, 116, Short.MAX_VALUE));
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(0, 39, Short.MAX_VALUE));
    } // </editor-fold>//GEN-END:initComponents

    //~ Inner Interfaces -------------------------------------------------------

    /**
     * Tagging interface for containers in a JXCollapsiblePane hierarchy who needs to be revalidated
     * (invalidate/validate/repaint) when the pane is expanding or collapsing. Usually validating only the parent of the
     * JXCollapsiblePane is enough but there might be cases where the parent parent must be validated.
     *
     * @version  $Revision$, $Date$
     */
    public static interface JCollapsiblePaneContainer {

        //~ Methods ------------------------------------------------------------

        /**
         * DOCUMENT ME!
         *
         * @return  DOCUMENT ME!
         */
        Container getValidatingContainer();
    }

    //~ Inner Classes ----------------------------------------------------------

    /**
     * Toggles the JXCollapsiblePane state and updates its icon based on the JXCollapsiblePane "collapsed" status.
     *
     * @version  $Revision$, $Date$
     */
    private class ToggleAction extends AbstractAction implements PropertyChangeListener {

        //~ Static fields/initializers -----------------------------------------

        /** Use serialVersionUID for interoperability. */
        private static final long serialVersionUID = 2304854314164808786L;

        //~ Constructors -------------------------------------------------------

        /**
         * Creates a new ToggleAction object.
         */
        public ToggleAction() {
            super(TOGGLE_ACTION);
            // the action must track the collapsed status of the pane to update its
            // icon
            JXCollapsiblePane.this.addPropertyChangeListener("collapsed", this); // NOI18N
        }

        //~ Methods ------------------------------------------------------------

        @Override
        public void putValue(final String key, final Object newValue) {
            super.putValue(key, newValue);
            if (EXPAND_ICON.equals(key) || COLLAPSE_ICON.equals(key)) {
                updateIcon();
            }
        }
        @Override
        public void actionPerformed(final ActionEvent e) {
            setCollapsed(!isCollapsed());
        }
        @Override
        public void propertyChange(final PropertyChangeEvent evt) {
            updateIcon();
        }
        /**
         * DOCUMENT ME!
         */
        void updateIcon() {
            if (isCollapsed()) {
                putValue(SMALL_ICON, getValue(EXPAND_ICON));
            } else {
                putValue(SMALL_ICON, getValue(COLLAPSE_ICON));
            }
        }
    }

    /**
     * Parameters controlling the animations.
     *
     * @version  $Revision$, $Date$
     */
    private static class AnimationParams {

        //~ Instance fields ----------------------------------------------------

        final int waitTime;
        final int delta;
        final float alphaStart;
        final float alphaEnd;

        //~ Constructors -------------------------------------------------------

        /**
         * Creates a new AnimationParams object.
         *
         * @param  waitTime    the amount of time in milliseconds to wait between calls to the animation thread
         * @param  delta       the delta to inc/dec the size of the scroll up by
         * @param  alphaStart  the starting alpha transparency level
         * @param  alphaEnd    the ending alpha transparency level
         */
        public AnimationParams(final int waitTime, final int delta, final float alphaStart, final float alphaEnd) {
            this.waitTime = waitTime;
            this.delta = delta;
            this.alphaStart = alphaStart;
            this.alphaEnd = alphaEnd;
        }
    }

    /**
     * This class actual provides the animation support for scrolling up/down this component. This listener is called
     * whenever the animateTimer fires off. It fires off in response to scroll up/down requests. This listener is
     * responsible for modifying the size of the content container and causing it to be repainted.
     *
     * @author   Richard Bair
     * @version  $Revision$, $Date$
     */
    private final class AnimationListener implements ActionListener {

        //~ Instance fields ----------------------------------------------------

        /** Mutex used to ensure that the startHeight/finalHeight are not changed during a repaint operation. */
        private final Object ANIMATION_MUTEX = "Animation Synchronization Mutex";                            // NOI18N
        /**
         * This is the starting height or width when animating. If > finalHeight, then the animation is going to be to
         * scroll up the component. If it is < then finalHeight, then the animation will scroll down the component.
         */
        private int startHeightOrWidth = 0;
        /** This is the final height or width that the content container is going to be when scrolling is finished. */
        private int finalHeightOrWidth = 0;
        /** The current alpha setting used during "animation" (fade-in/fade-out). */
        private float animateAlpha = 1.0f;

        //~ Methods ------------------------------------------------------------

        @Override
        public void actionPerformed(final ActionEvent e) {
            /*
             * Pre-1) If startHeight == finalHeight, then we're done so stop the timer 1) Calculate whether we're
             * contracting or expanding. 2) Calculate the delta (which is either positive or negative, depending on the
             * results of (1)) 3) Calculate the alpha value 4) Resize the ContentContainer 5) Revalidate/Repaint the
             * content container
             */
            synchronized (ANIMATION_MUTEX) {
                if (startHeightOrWidth == finalHeightOrWidth) {
                    animateTimer.stop();
                    animateAlpha = animationParams.alphaEnd;
                    // keep the content pane hidden when it is collapsed, other it may
                    // still receive focus.
                    if (finalHeightOrWidth > 0) {
                        wrapper.showContent();
                        validate();
                        JXCollapsiblePane.this.firePropertyChange(ANIMATION_STATE_KEY, null,
                            "expanded");  // NOI18N
                        return;
                    } else {
                        JXCollapsiblePane.this.firePropertyChange(ANIMATION_STATE_KEY, null,
                            "collapsed"); // NOI18N
                    }
                }

                final boolean contracting = startHeightOrWidth > finalHeightOrWidth;
                final int delta = contracting ? (-1 * animationParams.delta) : animationParams.delta;
                int newHeightOrWidth;
                if (!JXCollapsiblePane.this.orientation.equals(JXCollapsiblePane.HORIZONTAL_ORIENTATION)) {
                    newHeightOrWidth = wrapper.getHeight() + delta;
                } else {
                    newHeightOrWidth = wrapper.getWidth() + delta;
                }
                if (contracting) {
                    if (newHeightOrWidth < finalHeightOrWidth) {
                        newHeightOrWidth = finalHeightOrWidth;
                    }
                } else {
                    if (newHeightOrWidth > finalHeightOrWidth) {
                        newHeightOrWidth = finalHeightOrWidth;
                    }
                }
                if (!JXCollapsiblePane.this.orientation.equals(JXCollapsiblePane.HORIZONTAL_ORIENTATION)) {
                    animateAlpha = (float)newHeightOrWidth / (float)wrapper.c.getPreferredSize().height;
                } else {
                    animateAlpha = (float)newHeightOrWidth / (float)wrapper.c.getPreferredSize().width;
                }

                Rectangle bounds = wrapper.getBounds();
                int oldHeightOrWidth;
                if (!JXCollapsiblePane.this.orientation.equals(JXCollapsiblePane.HORIZONTAL_ORIENTATION)) {
                    oldHeightOrWidth = bounds.height;
                    bounds.height = newHeightOrWidth;
                } else {
                    oldHeightOrWidth = bounds.width;
                    bounds.width = newHeightOrWidth;
                }

                wrapper.setBounds(bounds);
                bounds = getBounds();
                if (!JXCollapsiblePane.this.orientation.equals(JXCollapsiblePane.HORIZONTAL_ORIENTATION)) {
                    bounds.height = (bounds.height - oldHeightOrWidth) + newHeightOrWidth;
                    currentHeightOrWidth = bounds.height;
                } else {
                    bounds.width = (bounds.width - oldHeightOrWidth) + newHeightOrWidth;
                    currentHeightOrWidth = bounds.width;
                }
                setBounds(bounds);
                startHeightOrWidth = newHeightOrWidth;

                // it happens the animateAlpha goes over the alphaStart/alphaEnd range
                // this code ensures it stays in bounds. This behavior is seen when
                // component such as JTextComponents are used in the container.
                if (contracting) {
                    // alphaStart > animateAlpha > alphaEnd
                    if (animateAlpha < animationParams.alphaEnd) {
                        animateAlpha = animationParams.alphaEnd;
                    }
                    if (animateAlpha > animationParams.alphaStart) {
                        animateAlpha = animationParams.alphaStart;
                    }
                } else {
                    // alphaStart < animateAlpha < alphaEnd
                    if (animateAlpha > animationParams.alphaEnd) {
                        animateAlpha = animationParams.alphaEnd;
                    }
                    if (animateAlpha < animationParams.alphaStart) {
                        animateAlpha = animationParams.alphaStart;
                    }
                }
                wrapper.alpha = animateAlpha;

                validate();
            }
        }

        /**
         * DOCUMENT ME!
         */
        void validate() {
            Container parent = SwingUtilities.getAncestorOfClass(
                    JCollapsiblePaneContainer.class,
                    JXCollapsiblePane.this);
            if (parent != null) {
                parent = ((JCollapsiblePaneContainer)parent).getValidatingContainer();
            } else {
                parent = getParent();
            }

            if (parent != null) {
                if (parent instanceof JComponent) {
                    ((JComponent)parent).revalidate();
                } else {
                    parent.invalidate();
                }
                parent.doLayout();
                parent.repaint();
            }
        }

        /**
         * Reinitializes the timer for scrolling up/down the component. This method is properly synchronized, so you may
         * make this call regardless of whether the timer is currently executing or not.
         *
         * @param  startHeightOrWidth  DOCUMENT ME!
         * @param  stopHeightOrWidth   DOCUMENT ME!
         */
        public void reinit(final int startHeightOrWidth, final int stopHeightOrWidth) {
            synchronized (ANIMATION_MUTEX) {
                JXCollapsiblePane.this.firePropertyChange(ANIMATION_STATE_KEY, null,
                    "reinit"); // NOI18N
                this.startHeightOrWidth = startHeightOrWidth;
                this.finalHeightOrWidth = stopHeightOrWidth;
                animateAlpha = animationParams.alphaStart;
                currentHeightOrWidth = -1;
                wrapper.showImage();
            }
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    private final class WrapperContainer extends JPanel {

        //~ Static fields/initializers -----------------------------------------

        /** Use serialVersionUID for interoperability. */
        private static final long serialVersionUID = 3770815644862696234L;

        //~ Instance fields ----------------------------------------------------

        float alpha = 1.0f;
        private BufferedImage img;
        private Container c;

        //~ Constructors -------------------------------------------------------

        /**
         * Creates a new WrapperContainer object.
         *
         * @param  c  DOCUMENT ME!
         */
        public WrapperContainer(final Container c) {
            super(new BorderLayout());
            this.c = c;
            add(c, BorderLayout.CENTER);

            // we must ensure the container is opaque. It is not opaque it introduces
            // painting glitches specially on Linux with JDK 1.5 and GTK look and feel.
            // GTK look and feel calls setOpaque(false)
            if ((c instanceof JComponent) && !((JComponent)c).isOpaque()) {
                ((JComponent)c).setOpaque(true);
            }
        }

        //~ Methods ------------------------------------------------------------

        /**
         * DOCUMENT ME!
         */
        public void showImage() {
            // render c into the img
            makeImage();
            c.setVisible(false);
        }

        /**
         * DOCUMENT ME!
         */
        public void showContent() {
            currentHeightOrWidth = -1;
            c.setVisible(true);
        }

        /**
         * DOCUMENT ME!
         */
        void makeImage() {
            // if we have no image or if the image has changed
            if (!orientation.equals(JXCollapsiblePane.HORIZONTAL_ORIENTATION)) {
                if ((getGraphicsConfiguration() != null) && (getWidth() > 0)) {
                    final Dimension dim = c.getPreferredSize();
                    // width and height must be > 0 to be able to create an image
                    if (dim.height > 0) {
                        img = getGraphicsConfiguration().createCompatibleImage(getWidth(), dim.height);
                        c.setSize(getWidth(), dim.height);
                    } else {
                        img = null;
                    }
                }
            } else {
                if ((getGraphicsConfiguration() != null) && (getHeight() > 0)) {
                    final Dimension dim = c.getPreferredSize();
                    // width and height must be > 0 to be able to create an image
                    if (dim.width > 0) {
                        img = getGraphicsConfiguration().createCompatibleImage(dim.width, getHeight());
                        c.setSize(dim.width, getHeight());
                    }
                }
            }
            if (img != null) {
                c.paint(img.getGraphics());
            }
        }

        @Override
        public void paintComponent(final Graphics g) {
            if (!useAnimation || c.isVisible()) {
                super.paintComponent(g);
            } else {
                // within netbeans, it happens we arrive here and the image has not been
                // created yet. We ensure it is.
                if (img == null) {
                    makeImage();
                }
                // and we paint it only if it has been created and only if we have a
                // valid graphics
                if ((g != null) && (img != null)) {
                    // draw the image with y being height - imageHeight
                    if (!orientation.equals(JXCollapsiblePane.HORIZONTAL_ORIENTATION)) {
                        g.drawImage(img, 0, getHeight() - img.getHeight(), null);
                    } else {
                        g.drawImage(img, getWidth() - img.getWidth(), 0, null);
                    }
                }
            }
        }

        @Override
        public void paint(final Graphics g) {
            final Graphics2D g2d = (Graphics2D)g;
            final Composite oldComp = g2d.getComposite();
            final Composite alphaComp = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                    alpha);
            g2d.setComposite(alphaComp);
            super.paint(g2d);
            g2d.setComposite(oldComp);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

}
