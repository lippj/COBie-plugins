package org.erdc.cobie.shared.merge;

import org.erdc.cobie.shared.COBIESheetDictionary;
import org.erdc.cobie.sheetxmldata.COBIEDocument;

//merges based on merge rules of type T 
public abstract class COBieMergeItem<T> implements Comparable<COBieMergeItem<T>>

{

    protected COBIEDocument cobie;
    protected COBIESheetDictionary cobieSheetDictionary;
    protected T mergeRules;

    public COBieMergeItem(COBIEDocument cobie, T mergeRules)
    {
        this.cobie = cobie;
        this.mergeRules = mergeRules;
        init();
    }

    public final COBIEDocument getCobie()
    {
        return cobie;
    }

    public final COBIESheetDictionary getCobieSheetDictionary()
    {
        return cobieSheetDictionary;
    }

    public final T getMergeRules()
    {
        return mergeRules;
    }

    private void init()
    {
        cobieSheetDictionary = new COBIESheetDictionary(cobie);
    }

}
