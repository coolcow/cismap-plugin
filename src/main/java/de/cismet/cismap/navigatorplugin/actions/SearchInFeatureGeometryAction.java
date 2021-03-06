/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
/*
 *  Copyright (C) 2011 thorsten
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.cismet.cismap.navigatorplugin.actions;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.MultiLineString;
import com.vividsolutions.jts.geom.MultiPoint;
import com.vividsolutions.jts.geom.MultiPolygon;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;

import org.openide.util.NbBundle;
import org.openide.util.lookup.ServiceProvider;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;

import de.cismet.cismap.commons.features.AbstractNewFeature;
import de.cismet.cismap.commons.features.CommonFeatureAction;
import de.cismet.cismap.commons.features.Feature;
import de.cismet.cismap.commons.features.SearchFeature;
import de.cismet.cismap.commons.gui.MappingComponent;
import de.cismet.cismap.commons.gui.piccolo.eventlistener.AbstractCreateSearchGeometryListener;
import de.cismet.cismap.commons.interaction.CismapBroker;

/**
 * DOCUMENT ME!
 *
 * @author   thorsten
 * @version  $Revision$, $Date$
 */
@ServiceProvider(service = CommonFeatureAction.class)
public class SearchInFeatureGeometryAction extends AbstractAction implements CommonFeatureAction {

    //~ Instance fields --------------------------------------------------------

    Feature f = null;

    private final transient org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(this.getClass());

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new DuplicateGeometryFeatureAction object.
     */
    public SearchInFeatureGeometryAction() {
        super(NbBundle.getMessage(
                SearchInFeatureGeometryAction.class,
                "SearchInFeatureGeometryAction.SearchInFeatureGeometryAction()"));
        super.putValue(
            Action.SMALL_ICON,
            new javax.swing.ImageIcon(getClass().getResource("/de/cismet/cismap/navigatorplugin/actions/search.png")));
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    public int getSorter() {
        return 1;
    }

    @Override
    public Feature getSourceFeature() {
        return f;
    }

    @Override
    public boolean isActive() {
        return true;
    }

    @Override
    public void setSourceFeature(final Feature source) {
        f = source;
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        de.cismet.tools.CismetThreadPool.execute(new javax.swing.SwingWorker<Void, Void>() {

                @Override
                protected Void doInBackground() throws Exception {
                    Thread.currentThread().setName("SearchInFeatureGeometryAction");

                    final Geometry geom = (Geometry)f.getGeometry().clone();
                    final SearchFeature sf = new SearchFeature(geom, MappingComponent.CREATE_SEARCH_POLYGON);
                    if ((geom instanceof LineString) || (geom instanceof MultiLineString)) {
                        sf.setGeometryType(AbstractNewFeature.geomTypes.LINESTRING);
                    } else if (geom instanceof Polygon) {
                        sf.setGeometryType(AbstractNewFeature.geomTypes.POLYGON);
                    } else if (geom instanceof MultiPolygon) {
                        sf.setGeometryType(AbstractNewFeature.geomTypes.MULTIPOLYGON);
                    } else if ((geom instanceof Point) || (geom instanceof MultiPoint)) {
                        sf.setGeometryType(AbstractNewFeature.geomTypes.POINT);
                    } else {
                        sf.setGeometryType(AbstractNewFeature.geomTypes.UNKNOWN);
                    }

                    // CismapBroker.getInstance().getMappingComponent().getFeatureCollection().addFeature(sf);

                    ((AbstractCreateSearchGeometryListener)CismapBroker.getInstance().getMappingComponent()
                                .getInputListener(
                                    sf.getInteractionMode())).search(sf);
                    return null;
                }

                @Override
                protected void done() {
                }
            });
    }
}
