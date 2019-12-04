package com.pb.salesforceserviceapi;

import com.sforce.soap.partner.*;
import com.sforce.soap.partner.sobject.*;
import com.sforce.ws.*;

public class SalesForceCDCPoc
{
  public static void main(String[] args) throws ConnectionException
  {
    ConnectorConfig config = new ConnectorConfig();
    config.setUsername("avinash@pb.com");
    config.setPassword("Sita@123RHoURHLIxGAxyTQAXNX7JJv1");
    config.setAuthEndpoint("https://login.salesforce.com/services/Soap/u/37.0");
    PartnerConnection connection = Connector.newConnection(config);
    System.out.println("auth endpoint: " + config.getAuthEndpoint());
    SObject account = new SObject();
    account.setType("Account");
    account.setField("Name", "My Account");
    connection.create(new SObject[]{account});
  }

}
