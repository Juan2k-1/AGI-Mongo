package com.uhu.AGI.model;

import java.util.List;

/**
 *
 * @author juald
 */
public class InvoiceDetailsDTO
{

    private String invoiceId;
    private List<String> invoiceDates;

    // Getters y Setters
    public String getInvoiceId()
    {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId)
    {
        this.invoiceId = invoiceId;
    }

    public List<String> getInvoiceDates()
    {
        return invoiceDates;
    }

    public void setInvoiceDates(List<String> invoiceDates)
    {
        this.invoiceDates = invoiceDates;
    }
}
