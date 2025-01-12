package com.uhu.AGI.services.test;

import com.uhu.AGI.model.Business;
import com.uhu.AGI.services.BusinessService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.util.Assert;

/**
 *
 * @author Juan Alberto Domínguez Vázquez
 */
@SpringBootTest
public class BusinessTest
{

    @Autowired
    private BusinessService businessService;

    private Business business;

    @BeforeEach
    public void setUp() 
    {       
        business = new Business();
        business.setId("1");
        business.setBusinessId("2");
        business.setName("Test Business");
        business.setCity("Test City");
        business.setState("TX");
        business.setAddress("Calle de prueba 1");
        business.setPostalCode("12345");
        business.setLatitude(10.0);
        business.setLongitude(20.0);
        business.setStars(4.5);
        business.setReviewCount(100);
        business.setIsOpen(1);
    }
    
    @Test
    @Rollback
    public void testSaveBusiness()
    {   
        businessService.saveBusiness(business);
        Assert.isTrue(this.businessService.findAllBusiness().contains(business), "");
    }

    @Test
    @Rollback
    public void testUpdateBusiness()
    {
        business.setName("Updated Business");
        businessService.updateBusiness(business);
        Assert.isTrue(this.business.getName().equalsIgnoreCase("Updated Business"), "");
    }

    @Test
    @Rollback
    public void testDeleteBusiness()
    {
        businessService.deleteBusiness(business);
        Assert.isTrue(!this.businessService.findAllBusiness().contains(business), "");
    }
    
    @Test
    public void testFindAllBusiness() {
        List<Business> busineses = this.businessService.findAllBusiness();
        System.out.println("CANTIDAD DE NEGOCIOS: " + busineses.size());
    }
}
