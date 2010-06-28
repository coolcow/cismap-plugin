/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
/*
 * HTMLTest.java
 *
 * Created on 28. Februar 2006, 12:02
 */
package de.cismet.cismap.navigatorplugin;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import java.io.IOException;

import javax.swing.ImageIcon;

/**
 * DOCUMENT ME!
 *
 * @author   thorsten.hell@cismet.de
 * @version  $Revision$, $Date$
 */
public class HTMLTest extends javax.swing.JFrame {

    //~ Static fields/initializers ---------------------------------------------

    /** Use serialVersionUID for interoperability. */
    private static final long serialVersionUID = -9024594576983701911L;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JLabel lblTest;
    private javax.swing.JEditorPane pane;
    private javax.swing.JScrollPane scpPane;
    private javax.swing.JTextField url;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form HTMLTest.
     */
    public HTMLTest() {
        initComponents();
        final Quote q = new Quote("This is a short test"); // NOI18N
        this.getContentPane().add(q);
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        scpPane = new javax.swing.JScrollPane();
        pane = new javax.swing.JEditorPane();
        jLabel2 = new javax.swing.JLabel();
        url = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        lblTest = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();

        jLabel1.setText("jLabel1");

        jTextField1.setText("jTextField1");

        jButton1.setText("jButton1");

        pane.setEditable(false);
        scpPane.setViewportView(pane);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel2.setText(org.openide.util.NbBundle.getMessage(HTMLTest.class, "HTMLTest.jLabel2.text")); // NOI18N

        jButton2.setText(org.openide.util.NbBundle.getMessage(HTMLTest.class, "HTMLTest.jButton2.text")); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    jButton2ActionPerformed(evt);
                }
            });

        lblTest.addMouseListener(new java.awt.event.MouseAdapter() {

                @Override
                public void mouseClicked(final java.awt.event.MouseEvent evt) {
                    lblTestMouseClicked(evt);
                }
            });

        jTextField2.setText("jTextField2");

        jTextField3.setText("jTextField3");

        final org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(
                layout.createSequentialGroup().addContainerGap().add(
                    layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(
                        lblTest,
                        org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
                        236,
                        org.jdesktop.layout.GroupLayout.PREFERRED_SIZE).add(
                        layout.createSequentialGroup().add(jLabel2).addPreferredGap(
                            org.jdesktop.layout.LayoutStyle.RELATED).add(
                            url,
                            org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
                            355,
                            org.jdesktop.layout.GroupLayout.PREFERRED_SIZE).addPreferredGap(
                            org.jdesktop.layout.LayoutStyle.RELATED).add(jButton2).add(25, 25, 25).add(
                            jTextField2,
                            org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
                            org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                            org.jdesktop.layout.GroupLayout.PREFERRED_SIZE).add(21, 21, 21).add(
                            jTextField3,
                            org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
                            org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                            org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))).add(106, 106, 106)));
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(
                layout.createSequentialGroup().addContainerGap().add(
                    lblTest,
                    org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
                    162,
                    org.jdesktop.layout.GroupLayout.PREFERRED_SIZE).add(273, 273, 273).add(
                    layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE).add(jLabel2).add(jButton2).add(
                        url,
                        org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
                        org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                        org.jdesktop.layout.GroupLayout.PREFERRED_SIZE).add(
                        jTextField2,
                        org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
                        org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                        org.jdesktop.layout.GroupLayout.PREFERRED_SIZE).add(
                        jTextField3,
                        org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
                        org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                        org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)).addContainerGap(
                    org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                    Short.MAX_VALUE)));

        pack();
    } // </editor-fold>//GEN-END:initComponents

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void lblTestMouseClicked(final java.awt.event.MouseEvent evt) { //GEN-FIRST:event_lblTestMouseClicked
        final BufferedImage bi = new BufferedImage(pane.getWidth(), pane.getHeight(), BufferedImage.TYPE_INT_ARGB);
        final Image i = bi.getScaledInstance(lblTest.getWidth(), lblTest.getHeight(), bi.SCALE_SMOOTH);
        final Graphics g = bi.getGraphics();
        pane.paint(g);

        lblTest.setIcon(new ImageIcon(i));
    } //GEN-LAST:event_lblTestMouseClicked

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void jButton2ActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_jButton2ActionPerformed
        try {
            pane.setSize(800, 600);
            pane.setPage(url.getText());
            pane.revalidate();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

// TODO add your handling code here:
    } //GEN-LAST:event_jButton2ActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  args  the command line arguments
     */
    public static void main(final String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {

                @Override
                public void run() {
                    new HTMLTest().setVisible(true);
                }
            });
    }
}
