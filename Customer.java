import java.util.*;
class Customer
{

      long customerId;
      boolean isPrime;
      Vector<Long> productsPurchased;     //vector of all the products customer purchased
      Set<Long> waitingFor;               //set of products customer is waiting to be notified on

      public Customer(long id, boolean isPrime)
      {
          this.customerId = id;
          this.isPrime = isPrime;
          this.productsPurchased = new Vector<Long>();
          this.waitingFor = new HashSet<Long>();
      }
}
