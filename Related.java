import java.util.*;
interface RelatedProducts
{
    void registerPurchase(long customerID, long productId);
    Vector<Long> getRelatedProducts(long customerID, long productId, int numProducts);
    long getRelatedCustomer(long customerID, long productId);
}

/*
Related  class implements RelatedProducts interface
*/
class Related implements RelatedProducts
{

  /*
  Checks if the given customerID and productId are valid
  */
  public boolean checkValidity(long customerID, long productId)
  {
      if(Main.customers.containsKey(customerID) && Main.products.containsKey(productId))
      {
        return true;
      }
      System.out.println("\nNot a valid customer id: "+ customerID + " or productid: " + productId);
      return false;
  }

  /*
  If valid then add "customerID" to "purchasedByCustomers" vector of product class and
  add "productId" to "productsPurchased" vector of Customer class
  */
  public void registerPurchase(long customerID, long productId)
  {
      Customer c;
      Product p;
      if(checkValidity(customerID, productId))
      {
          c = Main.customers.get(customerID);
          if(!c.productsPurchased.contains(productId))
              c.productsPurchased.add(productId);

          p = Main.products.get(productId);
          if(!p.purchasedByCustomers.contains(customerID))
              p.purchasedByCustomers.add(customerID);
      }
  }

  /*
  If the customerID has purchased productId then return other products purchased by the
  customer as related products
  If customerID has not purchased productId, no related products
  */
  public Vector<Long> getRelatedProducts(long customerID, long productId, int numProducts)
  {
      Vector<Long> vec = new Vector<Long>();
      Customer c;
      Product p;

      if(checkValidity(customerID, productId))
      {
          c = Main.customers.get(customerID);
          p = Main.products.get(productId);
          if(c.productsPurchased.contains(productId))
          {
              int count = 0;
              int i = 0;
              while(count < numProducts  && i < c.productsPurchased.size())
              {
                  //Dont add the same product in RelatedProducts
                  if(c.productsPurchased.get(i) != productId)
                  {
                    vec.add(c.productsPurchased.get(i));
                    count++;
                  }
                  i++;
              }
          }
          else
          {
              System.out.println("\tNo Releted products found");
          }
      }
      return vec;
  }

  /*
  If productId is purchased by customerId, then return a customerId who as
  also purchased productId as relatedCustomer
  If not then there are no relatedCustomer for this productId
  */
  public long getRelatedCustomer(long customerID, long productId)
  {
      long relatedCustomer = -1;
      Customer c;
      Product p;

      if(checkValidity(customerID, productId))
      {
          c = Main.customers.get(customerID);
          p = Main.products.get(productId);
          if(p.purchasedByCustomers.contains(customerID))
          {

              for(int i=0; i<p.purchasedByCustomers.size(); i++)
              {
                  //Dont return same customerID
                  if(p.purchasedByCustomers.get(i) != customerID)
                  {
                      relatedCustomer = p.purchasedByCustomers.get(i);
                      return relatedCustomer;
                  }
              }
        }
        if(relatedCustomer == -1)
          System.out.println("\tNo Releted customers found");
      }
      return relatedCustomer;
  }
}
