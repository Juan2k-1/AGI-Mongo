package com.uhu.AGI.model;

/**
 *
 * @author juald
 */
public class InvoiceDTO
{

    private String name;
    private String address;
    private String businessId;
    private InvoiceDetailsDTO invoiceDetails;

    // Getters y Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public InvoiceDetailsDTO getInvoiceDetails() {
        return invoiceDetails;
    }

    public void setInvoiceDetails(InvoiceDetailsDTO invoiceDetails) {
        this.invoiceDetails = invoiceDetails;
    }

}
