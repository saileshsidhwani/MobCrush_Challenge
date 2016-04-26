import java.util.*;

/*
Class contains the main function and performes all the tests
This class also maintains the database for all the products and the customers
*/
class Main
{
  //Database of all the customers and products accesible by their respective ids. Used "HashMap" for fast retreval
  static HashMap<Long, Customer> customers = new HashMap<Long,Customer>();
  static HashMap<Long, Product> products = new HashMap<Long,Product>();

  public static void main(String args[])
  {

    //Create non-prime customers with ids 1-5 and insert in Customer DataBase(HashMap)
    for(int i=1;i<=5;i++)
    {
        System.out.println("Customer id= "+i+" and isPrime= " + false);
        Customer c = new Customer(i,false);
        customers.put((long)i,c);
    }

    //Create Prime customers with ids 6-10 and insert in Customer DataBase(HashMap)
    for(int i=6;i<=10;i++)
    {
        System.out.println("Customer id= "+i+" and isPrime= " + true);
        Customer c = new Customer(i,true);
        customers.put((long)i,c);
    }

    //Create products with ids 100-115 and insert in Product DataBase(HashMap)
    for(int i=100; i<=115; i++)
    {
        Product p = new Product(i);
        products.put((long)i,p);
    }

    Notify n = new Notify();

    //Call notifyMe for all the customers on Product 100 and 101
    for(int i=1;i<=10;i++)
    {
        n.notifyMe(100,customers.get((long)i));
        n.notifyMe(101,customers.get((long)i));
        n.notifyMe(100,customers.get((long)i));
    }

    //Calling getCustomersToNotify to test
    System.out.println("\n\nTesting Notify.....");
    System.out.println("\tNotify 7 customers for Product id 100 with scheme SCHEME_PRIME_FIFO: "+n.getCustomersToNotify(100,1,7)+ "\n");

    //Calling getCustomersToNotify to test
    System.out.println("\tNotify 7 customers for Product id 101 with scheme SCHEME_FIFO: "+n.getCustomersToNotify(101,0,7) + "\n");


    Related r = new Related();

    //Customer 2 and 4 purchase all products
    for(int i=100; i<=115; i++)
    {
        r.registerPurchase(2,i);
        r.registerPurchase(4,i);
    }


    //Test by getting related products for a few products and different customer ids 2 and 10
    System.out.println("\n\nTesting Related Products.....");
    System.out.println("\tRelated Products for Customer 2, Product 100 with numProduts=5: "+r.getRelatedProducts(2,100,5) + "\n");
    System.out.println("\tRelated Products for Customer 10, Product 102 with numProduts=4: "+r.getRelatedProducts(10,102,4) + "\n");

    //Test by getting related customers for a few customers and different product ids 102 and 100
    System.out.println("\n\nTesting Related Customers.....");
    System.out.println("\tRelated Customers for Customer 3 Product 102: "+r.getRelatedCustomer(3,102) + "\n");
    System.out.println("\tRelated Customers for Customer 2 Product 100: "+r.getRelatedCustomer(2,100) + "\n");

  }
}
