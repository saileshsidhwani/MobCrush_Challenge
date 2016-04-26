import java.util.*;

interface ProductNotifier
{
    final int SCHEME_FIFO=0;
    final int SCHEME_PRIME_FIFO=1;;
    Vector<Long> getCustomersToNotify(long productId, int scheme, int numCustomersToBeNotified);
    void notifyMe(long productId, Customer customer);
}


/*
Notify class implements the interface Product Notifier
*/
class Notify implements ProductNotifier
{

    public Vector<Long> getCustomersToNotify(long productId, int scheme, int numCustomersToBeNotified)
    {
        Vector<Long> vec = new Vector<Long>();

        //Check if productId is a valid productId i.e. it is present in product database
        if(!Main.products.containsKey(productId))
        {
            System.out.println("\tInvalid Product id");
            return vec;
        }

        Product p = Main.products.get(productId);
        if(scheme == SCHEME_FIFO)
        {
            //Iterate through the queue until numCustomersToBeNotified is reached
            for(int i=0; i< numCustomersToBeNotified; i++)
            {
                //dequeue the Customer and add it to return vector
                long cust = p.notify.dequeueCustomer();
                if(cust == -1)
                {
                    return vec;   //if queue is empty, return the vector in current state as no more customers waiting to be notified
                }
                vec.add(cust);
            }
        }
        else if(scheme == SCHEME_PRIME_FIFO)
        {
            int i = 0;

            //First dequeue all the prime memebers and add to return vector and
            //if more notifications required, dequeue non-prime memebrs and add to return vector
            for(; i< numCustomersToBeNotified; i++)
            {
                long cust = p.notify.dequeuePrimeCustomer();
                if(cust == -1)
                    break;        //-1 means prime queue empty
                vec.add(cust);
            }

            for(;i<numCustomersToBeNotified;i++)
            {
                long cust = p.notify.dequeueNonPrimeCustomer();
                if(cust == -1)
                    break;      //-1 means queue empty
                vec.add(cust);
            }
        }
        return vec;
    }


    public void notifyMe(long productId, Customer customer)
    {
        //Check if productId is a valid productId i.e. it is present in product database
        if(!Main.products.containsKey(productId))
        {
            System.out.println("\tInvalid product");
            return;
        }

        //Check if customer is not already waiting for notification for "productId"
        //If not then enqueue custome to apprpriete notification queue based on customers isPrime value
        if(!customer.waitingFor.contains(productId))
        {
            customer.waitingFor.add(productId);
            Product p = Main.products.get(productId);
            if(customer.isPrime)
                p.notify.enqueuePrimeCustomer(customer.customerId);
            else
                p.notify.enqueueNonPrimeCustomer(customer.customerId);
        }

    }

}
