/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
/*
 * A4H.java
 *
 * Created on 11. Juli 2006, 12:19
 */
package de.cismet.cismap.printing.templateinscriber;

import java.util.HashMap;

import de.cismet.cismap.commons.gui.printing.AbstractPrintingInscriber;

/**
 * DOCUMENT ME!
 *
 * @author   thorsten.hell@cismet.de
 * @version  $Revision$, $Date$
 */
public class A4H extends AbstractPrintingInscriber {

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JTextField txtZeile1;
    private javax.swing.JTextField txtZeile2;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form A4H.
     */
    public A4H() {
        initComponents();
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * This Method should return the values in the Form<br>
     * key: placeholderName value: value
     *
     * @return  DOCUMENT ME!
     */
    @Override
    public HashMap<String, String> getValues() {
        final HashMap<String, String> hm = new HashMap<String, String>();
        hm.put("Ueberschrift", txtZeile1.getText()); // NOI18N
        hm.put("Unterschrift", txtZeile2.getText()); // NOI18N
        return hm;
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        jLabel1 = new javax.swing.JLabel();
        txtZeile1 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtZeile2 = new javax.swing.JTextField();

        jLabel1.setText(org.openide.util.NbBundle.getMessage(A4H.class, "A4H.jLabel1.text")); // NOI18N

        txtZeile1.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    txtZeile1ActionPerformed(evt);
                }
            });

        jLabel2.setText(org.openide.util.NbBundle.getMessage(A4H.class, "A4H.jLabel2.text")); // NOI18N

        final org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(
                layout.createSequentialGroup().addContainerGap().add(
                    layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(
                        layout.createSequentialGroup().add(jLabel1).addPreferredGap(
                            org.jdesktop.layout.LayoutStyle.RELATED).add(
                            txtZeile1,
                            org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                            101,
                            Short.MAX_VALUE)).add(
                        layout.createSequentialGroup().add(jLabel2).addPreferredGap(
                            org.jdesktop.layout.LayoutStyle.RELATED).add(
                            txtZeile2,
                            org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                            101,
                            Short.MAX_VALUE))).addContainerGap()));
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(
                layout.createSequentialGroup().addContainerGap().add(
                    layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE).add(jLabel1).add(
                        txtZeile1,
                        org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
                        org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                        org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)).addPreferredGap(
                    org.jdesktop.layout.LayoutStyle.RELATED).add(
                    layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE).add(jLabel2).add(
                        txtZeile2,
                        org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
                        org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                        org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)).addContainerGap(
                    org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                    Short.MAX_VALUE)));
    } // </editor-fold>//GEN-END:initComponents

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void txtZeile1ActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_txtZeile1ActionPerformed
    }                                                                             //GEN-LAST:event_txtZeile1ActionPerformed
}
