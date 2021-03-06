/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cismap.tools.gui;

import org.apache.log4j.Logger;

import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;

import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.JToggleButton;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.navigator.utils.CidsBeanDropListener;

import de.cismet.cismap.commons.features.AbstractNewFeature;
import de.cismet.cismap.commons.features.SearchFeature;
import de.cismet.cismap.commons.features.SelectFeature;
import de.cismet.cismap.commons.gui.MappingComponent;
import de.cismet.cismap.commons.gui.piccolo.eventlistener.CreateGeometryListenerInterface;
import de.cismet.cismap.commons.gui.piccolo.eventlistener.CreateSearchGeometryListener;
import de.cismet.cismap.commons.gui.piccolo.eventlistener.SelectionListener;

import de.cismet.cismap.navigatorplugin.CidsBeansSearchFeature;
import de.cismet.cismap.navigatorplugin.CidsBeansSelectFeature;

import de.cismet.tools.gui.JPopupMenuButton;

/**
 * DOCUMENT ME!
 *
 * @version  $Revision$, $Date$
 */
public class CidsBeanDropJPopupMenuButton extends JPopupMenuButton implements CidsBeanDropListener, DropTargetListener {

    //~ Static fields/initializers ---------------------------------------------

    private static final Logger LOG = Logger.getLogger(CidsBeanDropJPopupMenuButton.class);
    private static final String uiClassID = "ToggleButtonUI";

    //~ Instance fields --------------------------------------------------------

    private String interactionMode;
    private MappingComponent mappingComponent;
    private String searchName;
    private Icon defaultIcon = null;
    private Icon targetIcon = null;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new CidsBeanDropJPopupMenuButton object.
     */
    public CidsBeanDropJPopupMenuButton() {
    }

    /**
     * Creates a new CidsBeanDropJPopupMenuButton object.
     *
     * @param  interactionMode   DOCUMENT ME!
     * @param  mappingComponent  DOCUMENT ME!
     * @param  searchName        DOCUMENT ME!
     */
    public CidsBeanDropJPopupMenuButton(final String interactionMode,
            final MappingComponent mappingComponent,
            final String searchName) {
        this.interactionMode = interactionMode;
        this.mappingComponent = mappingComponent;
        this.searchName = searchName;

        setModel(new JToggleButton.ToggleButtonModel());
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @Override
    public String getUIClassID() {
        return uiClassID;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  beans  DOCUMENT ME!
     */
    @Override
    public void beansDropped(final ArrayList<CidsBean> beans) {
        mappingComponent.setInteractionMode(interactionMode);
        final CreateGeometryListenerInterface searchListener = ((CreateGeometryListenerInterface)
                mappingComponent.getInputListener(
                    interactionMode));

        if (searchListener instanceof CreateSearchGeometryListener) {
            de.cismet.tools.CismetThreadPool.execute(new javax.swing.SwingWorker<SearchFeature, Void>() {

                    @Override
                    protected SearchFeature doInBackground() throws Exception {
                        Thread.currentThread().setName("CidsBeanDropJPopupMenuButton beansDropped()");
                        final SearchFeature feature = CidsBeansSearchFeature.createFromBeans(beans, interactionMode);
                        feature.setGeometryType(AbstractNewFeature.geomTypes.POLYGON);
                        return feature;
                    }

                    @Override
                    protected void done() {
                        try {
                            final SearchFeature feature = get();
                            if (feature != null) {
                                ((CreateSearchGeometryListener)searchListener).search(feature);
                            }
                        } catch (Exception e) {
                            LOG.error("Exception in Background Thread", e);
                        }
                    }
                });
        } else if (searchListener instanceof SelectionListener) {
            de.cismet.tools.CismetThreadPool.execute(new javax.swing.SwingWorker<SelectFeature, Void>() {

                    @Override
                    protected SelectFeature doInBackground() throws Exception {
                        Thread.currentThread().setName("CidsBeanDropJPopupMenuButton beansDropped()");
                        final SelectFeature feature = CidsBeansSelectFeature.createFromBeans(beans, interactionMode);
                        feature.setGeometryType(AbstractNewFeature.geomTypes.POLYGON);
                        return feature;
                    }

                    @Override
                    protected void done() {
                        try {
                            final SelectFeature feature = get();
                            if (feature != null) {
                                ((SelectionListener)searchListener).select(feature);
                            }
                        } catch (Exception e) {
                            LOG.error("Exception in Background Thread", e);
                        }
                    }
                });
        }

        super.setSelectedIcon(defaultIcon);
        super.setIcon(defaultIcon);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  defaultIcon  DOCUMENT ME!
     */
    @Override
    public void setIcon(final Icon defaultIcon) {
        super.setIcon(defaultIcon);
        this.defaultIcon = defaultIcon;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  targetIcon  DOCUMENT ME!
     */
    public void setTargetIcon(final Icon targetIcon) {
        this.targetIcon = targetIcon;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Icon getTargetIcon() {
        return targetIcon;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  dtde  DOCUMENT ME!
     */
    @Override
    public void dragEnter(final DropTargetDragEvent dtde) {
        if (isSelected()) {
            super.setSelectedIcon(targetIcon);
        } else {
            super.setIcon(targetIcon);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  dte  DOCUMENT ME!
     */
    @Override
    public void dragExit(final DropTargetEvent dte) {
        if (isSelected()) {
            super.setSelectedIcon(defaultIcon);
        } else {
            super.setIcon(defaultIcon);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  dtde  DOCUMENT ME!
     */
    @Override
    public void dragOver(final DropTargetDragEvent dtde) {
        if (isSelected()) {
            super.setSelectedIcon(targetIcon);
        } else {
            super.setIcon(targetIcon);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  dtde  DOCUMENT ME!
     */
    @Override
    public void drop(final DropTargetDropEvent dtde) {
    }

    /**
     * DOCUMENT ME!
     *
     * @param  dtde  DOCUMENT ME!
     */
    @Override
    public void dropActionChanged(final DropTargetDragEvent dtde) {
    }
}
