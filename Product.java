import java.util.*;
class notifyTimeStamp
{
    long id;
    int timeStamp;
}

/*
notifyQueue is a wrapper class for the 2 queues used, primeCustomers and nonPrimeCustomers that need to be notified for the particular product
Such design is used to easily retrieve prime customes and non-prime customes as and when required
This design helps us to easily implement SCHEME_PRIME_FIFO as we have prime customers in a separate queues
For the case of SCHEME_FIFO a timeStamp is managed and used to retrieve customers in FIFO order
Lower timeStamp means older customer in the queue
*/
class notifyQueue
{
    int timeStamp;
    LinkedList<notifyTimeStamp> primeCustomers;
    LinkedList<notifyTimeStamp> nonPrimeCustomers;

    public notifyQueue()
    {
        this.timeStamp = 0;
        primeCustomers = new LinkedList<notifyTimeStamp>();
        nonPrimeCustomers = new LinkedList<notifyTimeStamp>();
    }

    //attach timeStamp to customerID and enqueue
    public void enqueuePrimeCustomer(long customerID)
    {
        notifyTimeStamp temp = new notifyTimeStamp();;
        temp.timeStamp = timeStamp++;
        temp.id = customerID;
        primeCustomers.add(temp);
    }

    //attach timeStamp to customerID and enqueue
    public void enqueueNonPrimeCustomer(long customerID)
    {
        notifyTimeStamp temp = new notifyTimeStamp();
        temp.timeStamp = timeStamp++;
        temp.id = customerID;
        nonPrimeCustomers.add(temp);
    }

    public long dequeuePrimeCustomer()
    {
        if(primeCustomers.size() == 0)
            return -1;
        notifyTimeStamp temp = primeCustomers.remove();
        return temp.id;
    }

    public long dequeueNonPrimeCustomer()
    {
        if(nonPrimeCustomers.size() == 0)
            return -1;
        notifyTimeStamp temp = nonPrimeCustomers.remove();
        return temp.id;
    }

    //Check heads of the prime and non-prime queues and dequeue element that has lower timeStamp
    public long dequeueCustomer()
    {
        //If both prime and non-prime queues  are non-empty, check timeStamp to dequeue
        if(primeCustomers.size() != 0 && nonPrimeCustomers.size() != 0)
        {
              notifyTimeStamp prime = primeCustomers.peek();
              notifyTimeStamp nonPrime = nonPrimeCustomers.peek();
              if(prime.timeStamp < nonPrime.timeStamp)
              {
                  primeCustomers.remove();
                  return prime.id;
              }
              else
              {
                  nonPrimeCustomers.remove();
                  return nonPrime.id;
              }
        }

        //if prime queue is non empty, dequeue from prime queue
        else if(primeCustomers.size() != 0)
        {
            notifyTimeStamp prime = primeCustomers.remove();
            return prime.id;
        }

        //if non-prime queue is non empty, dequeue from non-prime queue
        else if(nonPrimeCustomers.size() != 0)
        {
            notifyTimeStamp nonPrime = nonPrimeCustomers.remove();
            return nonPrime.id;
        }

        //if bothe the queues are empty, return -1
        return -1;
    }
}


class Product
{
  long productId;
  notifyQueue notify;                         //notification queue wrapper
  Vector<Long> purchasedByCustomers;          //vector to store all customers purchased this product

  public Product(long productId)
  {
      notify = new notifyQueue();
      this.productId = productId;
      this.purchasedByCustomers = new Vector<Long>();
  }
}
