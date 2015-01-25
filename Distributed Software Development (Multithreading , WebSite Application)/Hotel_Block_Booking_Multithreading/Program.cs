//Assignment 2 done by 
//Girish Dasarathy(1207555332)-> Owner for travel agency , main and Buffer(50%)
//Niranjan Krishna Ravichandran (1207518529) -> Owner for Hotel Supplier and Buffer(50%)
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading;

namespace Hotel_Block_Booking
{
    public delegate void pricecutevent(Int32 price); //Creation of a delegate and defining a price cut event.
    public class HotelSupplier
    {
        public static Random rng = new Random();
        public static AutoResetEvent pricecutevent = new AutoResetEvent(false); //Creation of price cut event which is an auto resent event.
        public static event pricecutevent pricecut;//Creation of an event object.
        public static DateTime final_date_time;

        //Defining the prices for Hotel1 ,Hotel2 and Hotel3 for all seven days in a week.

        public static Int32[] hotel_1_price = new Int32[] { 10, 9, 8, 10, 11, 13, 15 };
        public static Int32[] hotel_2_price = new Int32[] { 7, 8, 6, 5, 11, 10, 12 };
        public static Int32[] hotel_3_price = new Int32[] { 11, 12, 13, 13, 16, 17, 18 };
        
        //Defining the initial quantity of rooms for Hotel1 ,Hotel2 and Hotel3
        public static Int32[] quantity = new Int32[] { 20, 10, 12 };

        //Defining the locaton charges of rooms for Hotel1 ,Hotel2 and Hotel3
        public Int32[] locationCharge = new Int32[] { 2, 5, 7 };

        public static Int32 Day = 0;
        public static Int32 count = 0;
        public static Boolean flag = true;

        //Function that returns the current minimum price of the three hotels.
        public Int32 get_price()
        {
            int minprice = 0;
            minprice = Math.Min(hotel_1_price[Day], Math.Min(hotel_2_price[Day], hotel_3_price[Day]));
            return minprice;

        }

        //Function that gets a price value as input and changes the price according to the pricing model.
        //Pricing Model : Predefined values been set for every hotel for each day of a week.The model selects the minimum price among the 3 hotels at every instant.
        //For weekends the price is higher and weekdays it is lower.
        public static void change_price(Int32 price)
        {
            HotelSupplier.pricecutevent.WaitOne();

            object lock_check = new Object();
            lock (lock_check)
            {
                switch (Thread.CurrentThread.Name)
                {
                    case "1":
                        if (price < hotel_1_price[Day])
                        {
                            if (pricecut != null)
                                pricecut(price);

                            hotel_1_price[Day] = price;
                        }
                        break;

                    case "2":
                        if (price < hotel_2_price[Day])
                        {
                            if (pricecut != null)
                                pricecut(price);

                            hotel_2_price[Day] = price;
                        }
                        break;

                    case "3":
                        if (price < hotel_3_price[Day])
                        {
                            if (pricecut != null)
                                pricecut(price);

                            hotel_3_price[Day] = price;
                        }
                        break;

                }
            }

        }

        public void hotel_supplier_function()
        {
            Int32 p = 0;
            do
            {
                Thread.Sleep(500);
                switch (Thread.CurrentThread.Name)
                {
                        //Check if the given day of the week and generate the change in price accordingly.s
                    case "1":
                        if ((Day == 5 || Day == 6) && hotel_1_price[Day] != 100)

                            p = rng.Next(hotel_1_price[Day] - 1, 16);

                        else if ((Day != 5 && Day != 6) && hotel_1_price[Day] != 100)
                            p = rng.Next(hotel_1_price[Day] - 1, 11);
                        break;

                    case "2":
                        if ((Day == 5 || Day == 6) && hotel_2_price[Day] != 100)

                            p = rng.Next(hotel_2_price[Day] - 1, 15);

                        else if ((Day != 5 && Day != 6) && hotel_2_price[Day] != 100)
                            p = rng.Next(hotel_2_price[Day] - 1, 10);
                        break;

                    case "3":
                        if ((Day == 5 || Day == 6) && hotel_3_price[Day] != 100)
                            p = rng.Next(hotel_3_price[Day] - 1, 20);
                        else if ((Day != 5 && Day != 6) && hotel_3_price[Day] != 100)
                            p = rng.Next(hotel_3_price[Day] - 1, 14);
                        break;
                }

                HotelSupplier.change_price(p);
            } while (count < 10);
            Console.WriteLine("10 EVENTS HAVE BEEN GENERATED .NO MORE PRICE CUT EVENTS!!! BOOKNG CLOSED!THANK YOU!!");
            retailer ret_obj = new retailer();
            HotelSupplier.pricecut -= new pricecutevent(ret_obj.hotels_available);//Removing the event subscripton method from the delegate once 10 price cuts are over.
            Thread.CurrentThread.Abort();
        }

        //Function to recieve the order object from the decoder.
        public void get_order_string(string order_string)
        {
            string encoded_order_string = order_string;
            decoder dec = new decoder();

            Object dec_receive = dec.receive_encoded_string(encoded_order_string);
            //Split the order object and store it as an array of strings.
            string[] order_values = dec_receive.ToString().Split('/');

            //Function to process the order.
            order_processing(order_values);
        }
        public void order_processing(string[] order_values)
        {


            Int32 hotel_price = Convert.ToInt32(order_values[0]);
            double final_price = 0;
            double tax = 0;

            Int32 user_quantity = Convert.ToInt32(order_values[1]);
            string supplier_name = order_values[3];

            string retailer_name = order_values[2];
            object obj = new object();
            //Releases the threads where Wait One is called.s
            pricecutevent.Set();
            Console.WriteLine("\nENTER THE CREDIT CARD NUMBER for the travel agency {0} :", Thread.CurrentThread.Name);
            Int32 card = Convert.ToInt32(Console.ReadLine());

            lock (obj)//Ensure that only one thread is processed .Lock mechanism implemented.
            {
                switch (retailer_name)
                {
                    case "1":
                        int credit = retailer.credit_card_number[0];
                        //The credit card number given by the user should be with in 5000 and 7000.It should exactly be same as the registered card numbers 
                        //stored in credit_card_number array.
                        if (!(credit > 5000 && credit < 7000 && credit == card))
                        {
                            Console.WriteLine("Invalid card details - Retailer thread aborted!!Please order again!\n" + Thread.CurrentThread.Name);
                            Thread.CurrentThread.Abort();

                        }
                        break;

                    case "2": credit = retailer.credit_card_number[1];
                        if (!(credit > 5000 && credit < 7000 && credit == card))
                        {
                            Console.WriteLine("Invalid card details - Retailer thread aborted!!Please order again!\n" + Thread.CurrentThread.Name);
                            Thread.CurrentThread.Abort();

                        }
                        break;
                    case "3": credit = retailer.credit_card_number[2];
                        if (!(credit > 5000 && credit < 7000 && credit == card))
                        {
                            Console.WriteLine("Invalid card details - Retailer thread aborted!!Please order again!\n" + Thread.CurrentThread.Name);
                            Thread.CurrentThread.Abort();

                        }
                        break;
                    case "4": credit = retailer.credit_card_number[3];
                        if (!(credit > 5000 && credit < 7000 && credit == card))
                        {
                            Console.WriteLine("Invalid card details - Retailer thread aborted!!Please order again!\n" + Thread.CurrentThread.Name);
                            Thread.CurrentThread.Abort();

                        }
                        break;
                    case "5": credit = retailer.credit_card_number[4];
                        if (!(credit > 5000 && credit < 7000 && credit == card)) // card is the user given inut. credit is the card number registered with the supplier.
                        {
                            Console.WriteLine("Invalid card details - Retailer thread aborted!!Please order again!\n" + Thread.CurrentThread.Name);
                            Thread.CurrentThread.Abort();

                        }
                        break;

                }
            }
            Int32 minprice = 0;
            retailer ret = new retailer();
            //Rooms available in any hotel becomes 0,then the supplier is not considered any more for booking of the rooms.
            //Implemented by replacing the price of the hotel room to the most maximum value for which rooms availale is 0.
            if (HotelSupplier.quantity[0] == 0 || HotelSupplier.quantity[1] == 0 || HotelSupplier.quantity[2] == 0 || flag == false)
            {
                if (HotelSupplier.quantity[0] == 0 || HotelSupplier.quantity[0] < user_quantity)
                {
                    minprice = Math.Min(HotelSupplier.hotel_2_price[HotelSupplier.Day], HotelSupplier.hotel_3_price[HotelSupplier.Day]);

                }
                else
                    if (HotelSupplier.quantity[1] == 0 || HotelSupplier.quantity[1] < user_quantity)
                        minprice = Math.Min(HotelSupplier.hotel_1_price[HotelSupplier.Day], HotelSupplier.hotel_3_price[HotelSupplier.Day]);
                    else
                        if (HotelSupplier.quantity[2] == 0 || HotelSupplier.quantity[0] < user_quantity)
                            minprice = Math.Min(HotelSupplier.hotel_1_price[HotelSupplier.Day], HotelSupplier.hotel_2_price[HotelSupplier.Day]);

                if (minprice == HotelSupplier.hotel_1_price[HotelSupplier.Day])
                    supplier_name = "Hotel1";
                else
                    if (minprice == HotelSupplier.hotel_2_price[HotelSupplier.Day])
                        supplier_name = "Hotel2";

                    else
                        if (minprice == HotelSupplier.hotel_3_price[HotelSupplier.Day])
                            supplier_name = "Hotel3";
            }

            
            switch (supplier_name)
            {

                case "Hotel1":
                    if (user_quantity <= HotelSupplier.quantity[0] && HotelSupplier.quantity[0] >= 0)
                    {
                        //Calculate the total cost of the rooms booked and deduct the booked rooms from the available set of rooms for each hotel.
                        HotelSupplier.quantity[0] = HotelSupplier.quantity[0] - user_quantity;
                        //10 % of the total price is computed as tax.
                        tax = (hotel_price * user_quantity) * 0.1;
                        //The final price is the sum of total price , tax and location charges.
                        final_price = (hotel_price * user_quantity) + tax + locationCharge[0];
                        ret.receive_confirmation(retailer_name, hotel_price, supplier_name, HotelSupplier.quantity[0], user_quantity, final_price);
                        final_date_time = DateTime.Now;
                        break;

                    }

                    else
                    {
                        Console.WriteLine("Rooms not Available in Hotel 1 ,Searching in other hotels");
                        //Increasing the hotel price to a maximum value when no rooms are available.
                        if (HotelSupplier.quantity[0] == 0)
                            HotelSupplier.hotel_1_price[HotelSupplier.Day] = 100;
                        flag = false;
                        break;
                    }


                case "Hotel2":
                    if (user_quantity <= HotelSupplier.quantity[1] && HotelSupplier.quantity[1] >= 0)
                    {
                        //Calculate the total cost of the rooms booked and deduct the booked rooms from the available set of rooms for each hotel.
                        HotelSupplier.quantity[1] = HotelSupplier.quantity[1] - user_quantity;
                        //10 % of the total price is computed as tax.
                        tax = (hotel_price * user_quantity) * 0.1;
                        //The final price is the sum of total price , tax and location charges.
                        final_price = (hotel_price * user_quantity) + tax + locationCharge[1];
                        ret.receive_confirmation(retailer_name, hotel_price, supplier_name, HotelSupplier.quantity[1], user_quantity, final_price);
                        final_date_time = DateTime.Now;
                        break;

                    }
                    else
                    {
                        Console.WriteLine("Rooms not Available in Hotel 2,Searching in other hotels ");
                        //Increasing the hotel price to a maximum value when no rooms are available.
                        if (HotelSupplier.quantity[1] == 0)
                            HotelSupplier.hotel_2_price[HotelSupplier.Day] = 100;
                        flag = false;
                        break;
                    }


                case "Hotel3":

                    if (user_quantity <= HotelSupplier.quantity[2] && HotelSupplier.quantity[2] >= 0)
                    {
                        //Calculate the total cost of the rooms booked and deduct the booked rooms from the available set of rooms for each hotel.
                        HotelSupplier.quantity[2] = HotelSupplier.quantity[2] - user_quantity;
                        //10 % of the total price is computed as tax.
                        tax = (hotel_price * user_quantity) * 0.1;
                        //The final price is the sum of total price , tax and location charges.
                        final_price = (hotel_price * user_quantity) + tax + locationCharge[2];
                        ret.receive_confirmation(retailer_name, hotel_price, supplier_name, HotelSupplier.quantity[2], user_quantity, final_price);
                        final_date_time = DateTime.Now;
                        break;
                    }
                    else
                    {
                        Console.WriteLine("Rooms not Available in Hotel 3,Searching in other hotels ");
                        //Increasing the hotel price to a maximum value when no rooms are available.
                        if (HotelSupplier.quantity[2] == 0)
                            HotelSupplier.hotel_3_price[HotelSupplier.Day] = 100;
                        flag = false;
                        break;
                    }

            }

        }


    }

    //Class for Hotel Agency
    public class retailer
    {

        public static Int32[] credit_card_number = { 5500, 6425, 6235, 5983, 5412 };
        HotelSupplier supplier_obj = new HotelSupplier();
        MultiCellBuffer multi = new MultiCellBuffer();
        public static DateTime date_time;
        string supplier_name;
        public static int temp = 0;
        order test = new order();


        public void retailerfunc()
        {
            Int32 card_num;
            Int32 user_quantity = 0;

            while (HotelSupplier.count < 10)
            {
                Thread.Sleep(2000);
                //Generates the user required rooms for a hotel.
                user_quantity = HotelSupplier.rng.Next(1, 5);

                //Function to get the latest minimum price.
                Int32 p = supplier_obj.get_price();
                if (p == HotelSupplier.hotel_1_price[HotelSupplier.Day])
                    supplier_name = "Hotel1";
                else if (p == HotelSupplier.hotel_2_price[HotelSupplier.Day])
                    supplier_name = "Hotel2";
                else
                    supplier_name = "Hotel3";

                
                switch (supplier_name)
                {

                    case "Hotel1":
                        //Recieve the order object as string from the Encoder.
                        string g = test.recieve_from_agency(p, user_quantity, supplier_name, Thread.CurrentThread.Name);
                        date_time = DateTime.Now;
                        //Function to set the values for MultiCellbuffer.
                        multi.setOneCell(g);
                        break;



                    case "Hotel2":
                        //Recieve the order object as string from the Encoder.
                        g = test.recieve_from_agency(p, user_quantity, supplier_name, Thread.CurrentThread.Name);
                        date_time = DateTime.Now;
                        //Function to set the values for MultiCellbuffer.
                        multi.setOneCell(g);
                        break;

                    case "Hotel3":
                        //Recieve the order object as string from the Encoder.
                        g = test.recieve_from_agency(p, user_quantity, supplier_name, Thread.CurrentThread.Name);
                        date_time = DateTime.Now;
                        //Function to set the values for MultiCellbuffer.
                        multi.setOneCell(g);
                        break;
                }


            }

        }

        //Function to receive the order confirmaton from the hotel supplier.
        public void receive_confirmation(string retailer_name, Int32 hotel_price, string supplier_name, Int32 remaining_quantity, Int32 user_quantity, double final_price)
        {
            DateTime final_date_time = DateTime.Now;
            Console.WriteLine(" Order processed for Travel Agency :{0}.\n Number Of Rooms Booked :{1} in the hotel {2}.\n The total booking charge is : {3}.\n Remaining rooms available in the hotel : {4}", retailer_name, user_quantity, supplier_name, final_price, remaining_quantity);
            Console.WriteLine("\n Order Duration" + Math.Round((final_date_time - retailer.date_time).TotalSeconds, 2) + " seconds");
            Console.WriteLine(" ***********************************************************************");
        }

        //Event subscripton method that informs the travel agencies that a low price cut event is generated.
        public void hotels_available(Int32 p)
        {
            object lock_check = new Object();
            HotelSupplier.count++;

            lock (lock_check)
            {
                Console.WriteLine(" ***********************************************************************");
                Console.WriteLine("Hotel {0} has emitted a low price event for {1} dollars.This is event {2}.", Thread.CurrentThread.Name, p, HotelSupplier.count);
                Console.WriteLine(" ***********************************************************************");
            }

        }
    }

    //Class for order
    public class order
    {
        private static Int32 hotel_price_recieved = 0;
        private static Int32 qty_recieved = 0;
        private static string supplier_name_recieved;
        private static string retailer_name_recieved;


        public string recieve_from_agency(Int32 hotel_price, Int32 qty, string supplier_name, string retailer_name)
        {
            set_hotel_price(hotel_price);
            set_qty(qty);
            set_supplier_name(supplier_name);
            set_retailer_name(retailer_name);
            //Send the order object to the Encoder.
            encoder enc = new encoder(hotel_price_recieved, qty_recieved, supplier_name_recieved, retailer_name_recieved);
            return enc.ToString();

        }

        //Set methods for each order attribute-price,quantity,supplier and retailer name.
        public void set_hotel_price(Int32 price)
        {
            hotel_price_recieved = price;
        }
        public void set_qty(Int32 quantity)
        {
            qty_recieved = quantity;
        }
        public void set_supplier_name(string supplier_name)
        {
            supplier_name_recieved = supplier_name;
        }
        public void set_retailer_name(string retailer_name)
        {
            retailer_name_recieved = retailer_name;
        }

    }

    public class encoder
    {

        //Initialising the variables that can get and set values.
        public Int32 hotel_p { get; set; }
        public Int32 quantity { get; set; }

        public string retailer { get; set; }
        public string supplier { get; set; }

        public encoder(Int32 p, Int32 user_quantity, string retailer_name, string supplier_name)
        {
            hotel_p = p;
            quantity = user_quantity;
            retailer = retailer_name;
            supplier = supplier_name;
        }

        //The multiple values of an order are converted to a single string 
        public override string ToString()
        {
            return this.hotel_p.ToString() + "/" + this.quantity.ToString() + "/" + this.supplier + "/" + this.retailer;

        }

    }

    //Class for decoder.
    public class decoder
    {

        HotelSupplier obj = new HotelSupplier();

        //Receive the encoded string from the multi cell buffer , convert to an object and send it back to supplier.
        public object receive_encoded_string(string encoded_string)
        {
            string encoded_string_recieved;
            encoded_string_recieved = encoded_string;
            Object decoded_object = (Object)encoded_string_recieved;
            return decoded_object;
        }

    }

    //Class for MulticellBuffer which has 3 buffer locations.
    public class MultiCellBuffer
    {
        private static Semaphore semaphore = new Semaphore(3, 3);
        object obj = new object();
        HotelSupplier supplier = new HotelSupplier();
        //Creating a reader writer lock.
        ReaderWriterLock rw = new ReaderWriterLock();
        String[] buffer = new String[100];
        static Int32 i = 1;

        string s;
        public void setOneCell(string order_string)
        {

            Console.WriteLine("{0} is waiting in line... ", Thread.CurrentThread.Name);
            semaphore.WaitOne();
            Console.WriteLine("{0} enters the buffer! ", Thread.CurrentThread.Name);


            Thread.Sleep(500);

            lock (obj)
            {

                rw.AcquireWriterLock(300);
                buffer[i] = order_string;//Adding the order object to the buffer.
                i++;
                rw.ReleaseWriterLock();
                s = getOneCell();//Read the order object from the buffer.
                rw.ReleaseReaderLock();
                supplier.get_order_string(s);

            }

        }

        //Function to read the order object from the buffer.
        public string getOneCell()
        {
            rw.AcquireReaderLock(300);
            Console.WriteLine("{0} is leaving the buffer :{1}", Thread.CurrentThread.Name, semaphore.Release());
            return buffer[i - 1];
        }

    }
    class Program
    {
        static void Main(string[] args)
        {

            
            //Creating an object for Hotel1 and create a thread that changes the hotel price and emits an event.
            HotelSupplier hotel1 = new HotelSupplier();
            Thread first_hotel = new Thread(new ThreadStart(hotel1.hotel_supplier_function));
            first_hotel.Name = "1".ToString();
            first_hotel.Start();

            //Creating an object for Hotel2 and create a thread that changes the hotel price and emits an event.
            HotelSupplier hotel2 = new HotelSupplier();
            Thread second_hotel = new Thread(new ThreadStart(hotel2.hotel_supplier_function));
            second_hotel.Name = "2".ToString();
            second_hotel.Start();

            //Creating an object for Hotel3 and create a thread that changes the hotel price and emits an event.
            HotelSupplier hotel3 = new HotelSupplier();
            Thread third_hotel = new Thread(new ThreadStart(hotel3.hotel_supplier_function));
            third_hotel.Name = "3".ToString();
            third_hotel.Start();

            //Creating object for the travel agents
            retailer travel_agents = new retailer();
            
            //The event subscripton method emits an event after each price cut event is added to the event container.
            HotelSupplier.pricecut += new pricecutevent(travel_agents.hotels_available);

            //Creates 5 travl agency threads.
            Thread[] retailers = new Thread[5];
            Console.WriteLine(" ***********************************************************************");
            Console.WriteLine("Please enter the credit card number when prompted that is registered with the Hotel Supplier. \n The list of credit card numbers are Retailer1->5500 ,Retailer2->6425, Retailer3->6235, Retailer4->5983, Retailer5->5412 \n ");


            Console.WriteLine(" ***********************************************************************");
            Console.WriteLine("\nSEMAPHORE IMPLEMENTATION-3 THREADS AT A TIME IN THE BUFFER!\n");
            Console.WriteLine(" ***********************************************************************");

            Console.WriteLine(" ***********************************************************************");
            Console.WriteLine("Please enter the day of week to proceed with the booking - 1-Monday 2-Tuesdsay 3-Wednesday 4-Thursday 5-Friday 6-Satday 7-Sunday");
            Console.WriteLine(" ***********************************************************************");
           
            Int32 day = Convert.ToInt32(Console.ReadLine());

            for (Int32 i = 0; i < 5; i++)
            {
                retailers[i] = new Thread(new ThreadStart(travel_agents.retailerfunc));
                retailers[i].Name = (i + 1).ToString();
                retailers[i].Start();
            }


        }
    }
}
