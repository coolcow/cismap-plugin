/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
/*
 * SearchSearchTopicsDialog.java
 *
 * Created on 30.11.2011, 09:03:40
 */
package de.cismet.cismap.navigatorplugin.metasearch;

import Sirius.navigator.search.dynamic.SearchControlListener;
import Sirius.navigator.search.dynamic.SearchControlPanel;
import Sirius.navigator.ui.ComponentRegistry;

import com.vividsolutions.jts.geom.Geometry;

import org.apache.log4j.Logger;

import org.openide.util.Lookup;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import java.util.Collection;
import java.util.List;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import de.cismet.cids.server.search.MetaObjectNodeServerSearch;
import de.cismet.cids.server.search.SearchResultListener;
import de.cismet.cids.server.search.SearchResultListenerProvider;
import de.cismet.cids.server.search.builtin.FullTextSearch;

import de.cismet.cismap.commons.BoundingBox;
import de.cismet.cismap.commons.CrsTransformer;
import de.cismet.cismap.commons.interaction.CismapBroker;

import de.cismet.cismap.navigatorplugin.protocol.FulltextSearchProtocolStepImpl;

import de.cismet.commons.gui.protocol.ProtocolHandler;

/**
 * DOCUMENT ME!
 *
 * @author   jweintraut
 * @version  $Revision$, $Date$
 */
public class SearchSearchTopicsDialog extends javax.swing.JDialog implements SearchControlListener {

    //~ Static fields/initializers ---------------------------------------------

    private static final Logger LOG = Logger.getLogger(SearchSearchTopicsDialog.class);
    private static SearchSearchTopicsDialog instance;

    //~ Instance fields --------------------------------------------------------

    private final SearchControlPanel pnlSearchCancel;
    private boolean searchRunning = false;
    private final SearchTopicsDialogModel model = new SearchTopicsDialogModel();

//    private SwingWorker searchThread;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClose;
    private javax.swing.JCheckBox chkCaseSensitive;
    private javax.swing.JCheckBox chkHere;
    private javax.swing.Box.Filler gluBottom;
    private javax.swing.Box.Filler gluFiller;
    private javax.swing.Box.Filler gluTop;
    private javax.swing.JLabel lblSearchParameter;
    private javax.swing.JPanel pnlButtons;
    private de.cismet.cismap.navigatorplugin.metasearch.SearchTopicsPanel pnlSearchTopics;
    private javax.swing.JScrollPane scpSearchTopics;
    private javax.swing.JSeparator sepButtons;
    private javax.swing.JSeparator sepSearchTopics;
    private javax.swing.JTextField txtSearchParameter;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form SearchSearchTopicsDialog.
     *
     * @param  parent  DOCUMENT ME!
     * @param  modal   DOCUMENT ME!
     */
    private SearchSearchTopicsDialog(final java.awt.Frame parent, final boolean modal) {
        super(parent, modal);

        initComponents();

        pnlSearchCancel = new SearchControlPanel(this);
        pnlSearchCancel.setEnabled(false);
        pnlButtons.remove(btnClose);
        pnlButtons.add(pnlSearchCancel);
        pnlButtons.add(btnClose);

        final MetaSearchListener metaSearchListener = new MetaSearchAdapter() {

                @Override
                public void topicAdded(final MetaSearchListenerEvent event) {
                    refreshSearchTopics();
                }

                @Override
                public void topicsAdded(final MetaSearchListenerEvent event) {
                    refreshSearchTopics();
                }

                @Override
                public void topicRemoved(final MetaSearchListenerEvent event) {
                    refreshSearchTopics();
                }

                @Override
                public void topicsRemoved(final MetaSearchListenerEvent event) {
                    refreshSearchTopics();
                }
            };

        MetaSearch.instance().addMetaSearchListener(metaSearchListener);

        refreshSearchTopics();

        final EnableSearchListener listener = new EnableSearchListener();
        pnlSearchTopics.registerItemListener(listener);
        txtSearchParameter.getDocument().addDocumentListener(listener);
        chkHere.addItemListener(listener);

        final Dimension searchTopicsDimension = pnlSearchTopics.getPreferredSize();
        final Dimension searchParameterDimension = txtSearchParameter.getPreferredSize();
        setMinimumSize(new Dimension(
                (int)(searchTopicsDimension.getWidth() + searchParameterDimension.getWidth() + 75),
                (int)searchTopicsDimension.getHeight()
                        + 100));
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     */
    private void refreshSearchTopics() {
        pnlSearchTopics.setSearchTopics(MetaSearch.instance().getSearchTopics());
        pack();
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static SearchSearchTopicsDialog instance() {
        if (instance == null) {
            instance = new SearchSearchTopicsDialog(ComponentRegistry.getRegistry().getMainWindow(), true);
            instance.setLocationRelativeTo(null);
        }

        return instance;
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        lblSearchParameter = new javax.swing.JLabel();
        pnlButtons = new javax.swing.JPanel();
        btnClose = new javax.swing.JButton();
        chkCaseSensitive = new javax.swing.JCheckBox();
        sepButtons = new javax.swing.JSeparator();
        txtSearchParameter = new javax.swing.JTextField();
        chkHere = new javax.swing.JCheckBox();
        gluFiller = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0),
                new java.awt.Dimension(0, 0),
                new java.awt.Dimension(0, 32767));
        sepSearchTopics = new javax.swing.JSeparator();
        scpSearchTopics = new javax.swing.JScrollPane();
        pnlSearchTopics = new de.cismet.cismap.navigatorplugin.metasearch.SearchTopicsPanel();
        gluTop = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0),
                new java.awt.Dimension(0, 0),
                new java.awt.Dimension(0, 32767));
        gluBottom = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0),
                new java.awt.Dimension(0, 0),
                new java.awt.Dimension(0, 32767));

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle(org.openide.util.NbBundle.getMessage(
                SearchSearchTopicsDialog.class,
                "SearchSearchTopicsDialog.title")); // NOI18N
        setMinimumSize(new java.awt.Dimension(500, 300));
        setModal(true);
        setPreferredSize(new java.awt.Dimension(500, 300));
        addWindowListener(new java.awt.event.WindowAdapter() {

                @Override
                public void windowClosing(final java.awt.event.WindowEvent evt) {
                    formWindowClosing(evt);
                }
            });
        getContentPane().setLayout(new java.awt.GridBagLayout());

        lblSearchParameter.setText(org.openide.util.NbBundle.getMessage(
                SearchSearchTopicsDialog.class,
                "SearchSearchTopicsDialog.lblSearchParameter.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 2, 5);
        getContentPane().add(lblSearchParameter, gridBagConstraints);

        pnlButtons.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 5, 0));

        btnClose.setText(org.openide.util.NbBundle.getMessage(
                SearchSearchTopicsDialog.class,
                "SearchSearchTopicsDialog.btnClose.text")); // NOI18N
        btnClose.setFocusPainted(false);
        btnClose.setMaximumSize(new java.awt.Dimension(100, 25));
        btnClose.setMinimumSize(new java.awt.Dimension(59, 25));
        btnClose.setPreferredSize(new java.awt.Dimension(100, 25));
        btnClose.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    btnCloseActionPerformed(evt);
                }
            });
        pnlButtons.add(btnClose);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LAST_LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(pnlButtons, gridBagConstraints);

        chkCaseSensitive.setText(org.openide.util.NbBundle.getMessage(
                SearchSearchTopicsDialog.class,
                "SearchSearchTopicsDialog.chkCaseSensitive.text")); // NOI18N
        chkCaseSensitive.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    chkCaseSensitiveActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 2, 5);
        getContentPane().add(chkCaseSensitive, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        getContentPane().add(sepButtons, gridBagConstraints);

        txtSearchParameter.setText(org.openide.util.NbBundle.getMessage(
                SearchSearchTopicsDialog.class,
                "SearchSearchTopicsDialog.txtSearchParameter.text")); // NOI18N
        txtSearchParameter.setMinimumSize(new java.awt.Dimension(250, 25));
        txtSearchParameter.setPreferredSize(new java.awt.Dimension(250, 25));
        txtSearchParameter.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    txtSearchParameterActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(2, 5, 5, 5);
        getContentPane().add(txtSearchParameter, gridBagConstraints);

        chkHere.setText(org.openide.util.NbBundle.getMessage(
                SearchSearchTopicsDialog.class,
                "SearchSearchTopicsDialog.chkHere.text")); // NOI18N
        chkHere.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    chkHereActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(2, 5, 5, 5);
        getContentPane().add(chkHere, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(gluFiller, gridBagConstraints);

        sepSearchTopics.setOrientation(javax.swing.SwingConstants.VERTICAL);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(sepSearchTopics, gridBagConstraints);

        scpSearchTopics.setBorder(null);
        scpSearchTopics.setViewportView(pnlSearchTopics);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(scpSearchTopics, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 0.5;
        getContentPane().add(gluTop, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 0.5;
        getContentPane().add(gluBottom, gridBagConstraints);

        pack();
    } // </editor-fold>//GEN-END:initComponents

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void txtSearchParameterActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_txtSearchParameterActionPerformed
        // Avoid invalid input.
        if (pnlSearchCancel.isEnabled()) {
            pnlSearchCancel.startSearch();
        }
    } //GEN-LAST:event_txtSearchParameterActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnCloseActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_btnCloseActionPerformed
        setVisible(false);
    }                                                                            //GEN-LAST:event_btnCloseActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void formWindowClosing(final java.awt.event.WindowEvent evt) { //GEN-FIRST:event_formWindowClosing
        if (!searchRunning) {
            setVisible(false);
        }
    }                                                                      //GEN-LAST:event_formWindowClosing

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void chkCaseSensitiveActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_chkCaseSensitiveActionPerformed
        model.setCaseSensitiveEnabled(chkCaseSensitive.isSelected());
    }                                                                                    //GEN-LAST:event_chkCaseSensitiveActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void chkHereActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_chkHereActionPerformed
        model.setSearchGeometryEnabled(chkHere.isSelected());
    }                                                                           //GEN-LAST:event_chkHereActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public SearchTopicsDialogModel getModel() {
        return model;
    }

    /**
     * DOCUMENT ME!
     */
    private void switchControls() {
        if (searchRunning) {
            btnClose.setEnabled(false);
            txtSearchParameter.setEnabled(false);
            chkCaseSensitive.setEnabled(false);
            chkHere.setEnabled(false);
        } else {
            btnClose.setEnabled(true);
            txtSearchParameter.setEnabled(true);
            chkCaseSensitive.setEnabled(true);
            chkHere.setEnabled(true);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Geometry createSearchGeometry() {
        Geometry searchGeometry = null;
        if (chkHere.isSelected()) {
            final BoundingBox boundingBox = CismapBroker.getInstance()
                        .getMappingComponent()
                        .getCurrentBoundingBoxFromCamera();
            final int srid = CrsTransformer.extractSridFromCrs(CismapBroker.getInstance().getMappingComponent()
                            .getMappingModel().getSrs().getCode());

            searchGeometry = CrsTransformer.transformToDefaultCrs(boundingBox.getGeometry(srid));
            // Damits auch mit -1 funzt:
            searchGeometry.setSRID(CismapBroker.getInstance().getDefaultCrsAlias());
        }
        return searchGeometry;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public boolean isCaseSensitiveEnabled() {
        return chkCaseSensitive.isSelected();
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public boolean isGeometryEnabled() {
        return chkHere.isSelected();
    }
    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public SearchTopicsPanel getPnlSearchTopics() {
        return pnlSearchTopics;
    }

    @Override
    public MetaObjectNodeServerSearch assembleSearch() {
        final Collection<String> selectedSearchClasses = MetaSearch.instance().getSelectedSearchClassesForQuery();

        LOG.info("Starting search for '" + txtSearchParameter.getText() + "' in '" + selectedSearchClasses
                    + "'. Case sensitive? "
                    + chkCaseSensitive.isSelected() + ", Only in cismap? " + chkHere.isSelected());

        model.setSearchText(txtSearchParameter.getText());

        final Geometry searchGeometry = createSearchGeometry();

        // default search is always present
        final FullTextSearch fullTextSearch = Lookup.getDefault().lookup(FullTextSearch.class);
        fullTextSearch.setSearchText(txtSearchParameter.getText());
        fullTextSearch.setCaseSensitive(chkCaseSensitive.isSelected());
        fullTextSearch.setGeometry(searchGeometry);
        fullTextSearch.setValidClassesFromStrings(selectedSearchClasses);
        if (fullTextSearch instanceof SearchResultListenerProvider) {
            ((SearchResultListenerProvider)fullTextSearch).setSearchResultListener(new SearchResultListener() {

                    @Override
                    public void searchDone(final List results) {
                        if (ProtocolHandler.getInstance().isRecordEnabled()) {
                            ProtocolHandler.getInstance()
                                    .recordStep(
                                        new FulltextSearchProtocolStepImpl(
                                            fullTextSearch,
                                            MetaSearch.instance().getSelectedSearchTopics(),
                                            results));
                        }
                    }
                });
        }
        return fullTextSearch;
    }

    @Override
    public void searchStarted() {
        searchRunning = true;
        switchControls();
    }

    @Override
    public void searchDone(final int results) {
        searchRunning = false;
        switchControls();

        if (results > 0) {
            setVisible(false);
        }
    }

    @Override
    public void searchCanceled() {
        searchRunning = false;
        switchControls();
    }

    @Override
    public boolean suppressEmptyResultMessage() {
        return false;
    }

    //~ Inner Classes ----------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    private class EnableSearchListener implements DocumentListener, ItemListener {

        //~ Methods ------------------------------------------------------------

        @Override
        public void insertUpdate(final DocumentEvent e) {
            enableSearchButton();
        }

        @Override
        public void removeUpdate(final DocumentEvent e) {
            enableSearchButton();
        }

        @Override
        public void changedUpdate(final DocumentEvent e) {
            enableSearchButton();
        }

        @Override
        public void itemStateChanged(final ItemEvent e) {
            enableSearchButton();
        }

        /**
         * DOCUMENT ME!
         */
        private void enableSearchButton() {
            // SearchTopicsPanel updates MetaSearch using an ItemListener, so this check has to wait until
            // SearchTopicsPanel updated MetaSearch

            EventQueue.invokeLater(new Runnable() {

                    @Override
                    public void run() {
                        boolean enableSearchButton = true;
                        enableSearchButton &= (txtSearchParameter.getText() != null)
                                    && (txtSearchParameter.getText().trim().length() > 0);
                        enableSearchButton &= MetaSearch.instance().getSelectedSearchTopics().size() > 0;
                        pnlSearchCancel.setEnabled(enableSearchButton);
                    }
                });
        }
    }
}
