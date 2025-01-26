/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.0.2-b01-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2006.08.29 at 09:53:40 AM CEST 
//


package primer;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}purchaseOrder" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "purchaseOrder"
})
@XmlRootElement(name = "purchaseOrders")
public class PurchaseOrders {

    /**
     * This list is not a real list; instead it will notify a listener whenever
     * JAXB has unmarshalled the next purchase order.
     */
    protected List<PurchaseOrderType> purchaseOrder;

    /**
     * Install a listener for purchase orders on this object. If l is null, the listener
     * is removed again.
     */
    public void setPurchaseOrderListener(final Listener l) {
        purchaseOrder = (l == null) ? null : new ArrayList<PurchaseOrderType>() {
            public boolean add(PurchaseOrderType o) {
                l.handlePurchaseOrder(PurchaseOrders.this, o);
                return false;
            }
        };
    }

    /**
     * This listener is invoked every time a new purchase order is unmarshalled.
     */
    public static interface Listener {
        void handlePurchaseOrder(PurchaseOrders purchaseOrders, PurchaseOrderType purchaseOrder);
    }
}
