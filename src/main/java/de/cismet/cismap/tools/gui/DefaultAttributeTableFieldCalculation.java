/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.cismet.cismap.tools.gui;

import java.util.List;

import de.cismet.cismap.cidslayer.FieldCalculatorDialog;

import de.cismet.cismap.commons.features.FeatureServiceFeature;
import de.cismet.cismap.commons.featureservice.AbstractFeatureService;
import de.cismet.cismap.commons.featureservice.FeatureServiceAttribute;
import de.cismet.cismap.commons.gui.attributetable.AttributeTable;
import de.cismet.cismap.commons.gui.attributetable.AttributeTableFieldCalculation;

import de.cismet.tools.gui.StaticSwingTools;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
@org.openide.util.lookup.ServiceProvider(service = AttributeTableFieldCalculation.class)
public class DefaultAttributeTableFieldCalculation implements AttributeTableFieldCalculation {

    //~ Methods ----------------------------------------------------------------

    @Override
    public boolean openPanel(final AttributeTable table,
            final AbstractFeatureService service,
            final FeatureServiceAttribute attribute,
            final List<FeatureServiceFeature> featureList) {
        final FieldCalculatorDialog dialog = new FieldCalculatorDialog(table,
                true,
                service,
                attribute,
                featureList);

        dialog.setSize(540, 500);
        StaticSwingTools.showDialog(dialog);

        return dialog.isCalculationStarted();
    }
}
