/*
 * An XML document type.
 * Localname: AttributeAllowedValue
 * Namespace: http://core.cobielite.cobie.erdc.org
 * Java type: org.erdc.cobie.cobielite.core.AttributeAllowedValueDocument
 *
 * Automatically generated - do not modify.
 */
package org.erdc.cobie.cobielite.core.impl;
/**
 * A document containing one AttributeAllowedValue(@http://core.cobielite.cobie.erdc.org) element.
 *
 * This is a complex type.
 */
public class AttributeAllowedValueDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements org.erdc.cobie.cobielite.core.AttributeAllowedValueDocument
{
    private static final long serialVersionUID = 1L;
    
    public AttributeAllowedValueDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName ATTRIBUTEALLOWEDVALUE$0 = 
        new javax.xml.namespace.QName("http://core.cobielite.cobie.erdc.org", "AttributeAllowedValue");
    
    
    /**
     * Gets the "AttributeAllowedValue" element
     */
    public java.lang.String getAttributeAllowedValue()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ATTRIBUTEALLOWEDVALUE$0, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "AttributeAllowedValue" element
     */
    public org.erdc.cobie.cobielite.core.CobieTextSimpleType xgetAttributeAllowedValue()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.erdc.cobie.cobielite.core.CobieTextSimpleType target = null;
            target = (org.erdc.cobie.cobielite.core.CobieTextSimpleType)get_store().find_element_user(ATTRIBUTEALLOWEDVALUE$0, 0);
            return target;
        }
    }
    
    /**
     * Sets the "AttributeAllowedValue" element
     */
    public void setAttributeAllowedValue(java.lang.String attributeAllowedValue)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ATTRIBUTEALLOWEDVALUE$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(ATTRIBUTEALLOWEDVALUE$0);
            }
            target.setStringValue(attributeAllowedValue);
        }
    }
    
    /**
     * Sets (as xml) the "AttributeAllowedValue" element
     */
    public void xsetAttributeAllowedValue(org.erdc.cobie.cobielite.core.CobieTextSimpleType attributeAllowedValue)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.erdc.cobie.cobielite.core.CobieTextSimpleType target = null;
            target = (org.erdc.cobie.cobielite.core.CobieTextSimpleType)get_store().find_element_user(ATTRIBUTEALLOWEDVALUE$0, 0);
            if (target == null)
            {
                target = (org.erdc.cobie.cobielite.core.CobieTextSimpleType)get_store().add_element_user(ATTRIBUTEALLOWEDVALUE$0);
            }
            target.set(attributeAllowedValue);
        }
    }
}
