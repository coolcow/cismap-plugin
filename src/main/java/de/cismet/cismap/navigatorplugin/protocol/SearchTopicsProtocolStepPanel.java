/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cismap.navigatorplugin.protocol;

import org.openide.awt.Mnemonics;
import org.openide.util.NbBundle;

import java.awt.Component;
import java.awt.Dimension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;

import de.cismet.commons.gui.protocol.AbstractProtocolStepPanel;

/**
 * DOCUMENT ME!
 *
 * @author   jruiz
 * @version  $Revision$, $Date$
 */
public class SearchTopicsProtocolStepPanel extends AbstractProtocolStepPanel<SearchTopicsProtocolStep> {

    //~ Static fields/initializers ---------------------------------------------

    private static final Map<String, ImageIcon> ICON_CACHE = new HashMap<String, ImageIcon>();

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JList jList1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane3;
    private org.jdesktop.swingx.JXHyperlink jXHyperlink2;
    private javax.swing.JLabel lblIconNone;
    private javax.swing.JLabel lblTitle;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new SearchTopicsPanel object.
     */
    public SearchTopicsProtocolStepPanel() {
        this(null);
    }

    /**
     * Creates new form MetaSearchProtocolStepPanel.
     *
     * @param  searchTopicsProtocolStep  DOCUMENT ME!
     */
    public SearchTopicsProtocolStepPanel(final SearchTopicsProtocolStep searchTopicsProtocolStep) {
        super(searchTopicsProtocolStep);
        initComponents();

        final List<GeoSearchProtocolStepSearchTopic> sortedTopics = new ArrayList<GeoSearchProtocolStepSearchTopic>();
        if (searchTopicsProtocolStep != null) {
            sortedTopics.addAll(searchTopicsProtocolStep.getSearchTopicInfos());
        }
        Collections.sort(sortedTopics, new Comparator<GeoSearchProtocolStepSearchTopic>() {

                @Override
                public int compare(final GeoSearchProtocolStepSearchTopic o1,
                        final GeoSearchProtocolStepSearchTopic o2) {
                    final String k1 = ((o1 == null) || (o1.getKey() == null)) ? "" : o1.getKey();
                    final String k2 = ((o2 == null) || (o2.getKey() == null)) ? "" : o2.getKey();
                    return k1.compareTo(k2);
                }
            });

        for (final GeoSearchProtocolStepSearchTopic topic : sortedTopics) {
            ((DefaultListModel<GeoSearchProtocolStepSearchTopic>)jList1.getModel()).addElement(topic);
        }

        if (jList1.getModel().getSize() > 0) {
            final int maxSize = 10;
            final JLabel dummy = (JLabel)
                new TopicListCellRenderer().getListCellRendererComponent(
                    jList1,
                    jList1.getModel().getElementAt(0),
                    0,
                    false,
                    false);
            final int height;

            if (jList1.getModel().getSize() > maxSize) {
                height = 4 + (dummy.getPreferredSize().height * maxSize);
            } else {
                height = 4 + (dummy.getPreferredSize().height * jList1.getModel().getSize());
            }

            jScrollPane3.setPreferredSize(new Dimension((int)jScrollPane3.getPreferredSize().getWidth(), height));
        }

        setSearchTopicsPanelVisible(false);
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        lblIconNone = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        lblTitle = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jXHyperlink2 = new org.jdesktop.swingx.JXHyperlink();

        lblIconNone.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblIconNone.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/pluginSearch.gif"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(
            lblIconNone,
            org.openide.util.NbBundle.getMessage(
                SearchTopicsProtocolStepPanel.class,
                "SearchTopicsProtocolStepPanel.lblIconNone.text"));                                         // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(
            jLabel2,
            org.openide.util.NbBundle.getMessage(
                SearchTopicsProtocolStepPanel.class,
                "SearchTopicsProtocolStepPanel.jLabel2.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(
            jLabel3,
            org.openide.util.NbBundle.getMessage(
                SearchTopicsProtocolStepPanel.class,
                "SearchTopicsProtocolStepPanel.jLabel3.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(
            jLabel4,
            org.openide.util.NbBundle.getMessage(
                SearchTopicsProtocolStepPanel.class,
                "SearchTopicsProtocolStepPanel.jLabel4.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(
            lblTitle,
            org.openide.util.NbBundle.getMessage(
                SearchTopicsProtocolStepPanel.class,
                "SearchTopicsProtocolStepPanel.lblTitle.text")); // NOI18N

        setLayout(new java.awt.GridBagLayout());

        jPanel2.setLayout(new java.awt.GridBagLayout());

        jList1.setModel(
            new DefaultListModel<de.cismet.cismap.navigatorplugin.protocol.GeoSearchProtocolStepSearchTopic>());
        jList1.setCellRenderer(new TopicListCellRenderer());
        jScrollPane3.setViewportView(jList1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel2.add(jScrollPane3, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 0);
        add(jPanel2, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(
            jXHyperlink2,
            org.openide.util.NbBundle.getMessage(
                SearchTopicsProtocolStepPanel.class,
                "SearchTopicsProtocolStepPanel.jXHyperlink2.text_hide_multi")); // NOI18N
        jXHyperlink2.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    jXHyperlink2ActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        add(jXHyperlink2, gridBagConstraints);
    } // </editor-fold>//GEN-END:initComponents

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void jXHyperlink2ActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_jXHyperlink2ActionPerformed
        toggleSearchTopicsPanelVisibility();
    }                                                                                //GEN-LAST:event_jXHyperlink2ActionPerformed

    /**
     * DOCUMENT ME!
     */
    private void toggleSearchTopicsPanelVisibility() {
        setSearchTopicsPanelVisible(!jPanel2.isVisible());
    }

    /**
     * DOCUMENT ME!
     *
     * @param  visible  DOCUMENT ME!
     */
    private void setSearchTopicsPanelVisible(final boolean visible) {
        jPanel2.setVisible(visible);

        final int size;
        if (getProtocolStep() != null) {
            if ((getProtocolStep().getSearchTopicInfos() == null)
                        || getProtocolStep().getSearchTopicInfos().isEmpty()) {
                size = 0;
            } else {
                size = getProtocolStep().getSearchTopicInfos().size();
            }

            jXHyperlink2.setEnabled(size > 0);

            if (size == 0) {
                Mnemonics.setLocalizedText(
                    jXHyperlink2,
                    NbBundle.getMessage(
                        SearchTopicsProtocolStepPanel.class,
                        "SearchTopicsProtocolStepPanel.jXHyperlink2.text_empty"));
            } else {
                if (visible) {
                    if (size > 1) {
                        Mnemonics.setLocalizedText(
                            jXHyperlink2,
                            NbBundle.getMessage(
                                SearchTopicsProtocolStepPanel.class,
                                "SearchTopicsProtocolStepPanel.jXHyperlink2.text_hide_multi",
                                String.valueOf(size)));
                    } else {
                        Mnemonics.setLocalizedText(
                            jXHyperlink2,
                            NbBundle.getMessage(
                                SearchTopicsProtocolStepPanel.class,
                                "SearchTopicsProtocolStepPanel.jXHyperlink2.text_hide_single",
                                String.valueOf(size)));
                    }
                } else {
                    if (size > 1) {
                        Mnemonics.setLocalizedText(
                            jXHyperlink2,
                            NbBundle.getMessage(
                                SearchTopicsProtocolStepPanel.class,
                                "SearchTopicsProtocolStepPanel.jXHyperlink2.text_show_multi",
                                String.valueOf(size)));
                    } else {
                        Mnemonics.setLocalizedText(
                            jXHyperlink2,
                            NbBundle.getMessage(
                                SearchTopicsProtocolStepPanel.class,
                                "SearchTopicsProtocolStepPanel.jXHyperlink2.text_show_single",
                                String.valueOf(size)));
                    }
                }
            }
        }

        revalidate();
    }

    /**
     * DOCUMENT ME!
     *
     * @param   iconPath  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private static ImageIcon getImageIcon(final String iconPath) {
        if (!ICON_CACHE.containsKey(iconPath)) {
            ImageIcon imageIcon;
            try {
                imageIcon = new javax.swing.ImageIcon(SearchTopicsProtocolStepPanel.class.getResource(iconPath));
            } catch (final Exception ex) {
                imageIcon = new javax.swing.ImageIcon(SearchTopicsProtocolStepPanel.class.getResource(
                            "/de/cismet/cismap/navigatorplugin/metasearch/search.png"));
            }
            ICON_CACHE.put(iconPath, imageIcon);
        }
        return ICON_CACHE.get(iconPath);
    }

    //~ Inner Classes ----------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    class TopicListCellRenderer extends DefaultListCellRenderer {

        //~ Methods ------------------------------------------------------------

        @Override
        public Component getListCellRendererComponent(final JList<?> list,
                final Object value,
                final int index,
                final boolean isSelected,
                final boolean cellHasFocus) {
            final GeoSearchProtocolStepSearchTopic topic = (GeoSearchProtocolStepSearchTopic)value;
            final JLabel component = (JLabel)super.getListCellRendererComponent(
                    list,
                    value,
                    index,
                    isSelected,
                    cellHasFocus);
            component.setIcon(getImageIcon(topic.getIconName()));
            component.setText(topic.getName());
            return component;
        }
    }
}
