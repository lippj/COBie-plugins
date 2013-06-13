/*
 * An XML document type.
 * Localname: Connections
 * Namespace: http://core.cobielite.cobie.erdc.org
 * Java type: org.erdc.cobie.cobielite.core.ConnectionsDocument
 *
 * Automatically generated - do not modify.
 */
package org.erdc.cobie.cobielite.core.impl;
/**
 * A document containing one Connections(@http://core.cobielite.cobie.erdc.org) element.
 *
 * This is a complex type.
 */
public class ConnectionsDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements org.erdc.cobie.cobielite.core.ConnectionsDocument
{
    private static final long serialVersionUID = 1L;
    
    public ConnectionsDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName CONNECTIONS$0 = 
        new javax.xml.namespace.QName("http://core.cobielite.cobie.erdc.org", "Connections");
    
    
    /**
     * Gets the "Connections" element
     */
    public org.erdc.cobie.cobielite.core.ConnectionCollectionType getConnections()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.erdc.cobie.cobielite.core.ConnectionCollectionType target = null;
            target = (org.erdc.cobie.cobielite.core.ConnectionCollectionType)get_store().find_element_user(CONNECTIONS$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "Connections" element
     */
    public void setConnections(org.erdc.cobie.cobielite.core.ConnectionCollectionType connections)
    {
        generatedSetterHelperImpl(connections, CONNECTIONS$0, 0, org.apache.xmlbeans.impl.values.XmlObjectBase.KIND_SETTERHELPER_SINGLETON);
    }
    
    /**
     * Appends and returns a new empty "Connections" element
     */
    public org.erdc.cobie.cobielite.core.ConnectionCollectionType addNewConnections()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.erdc.cobie.cobielite.core.ConnectionCollectionType target = null;
            target = (org.erdc.cobie.cobielite.core.ConnectionCollectionType)get_store().add_element_user(CONNECTIONS$0);
            return target;
        }
    }
}
