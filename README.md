**Checkout Kata**

## Implement the code for a supermarket checkout that calculates the total price of a number of items.

In a normal supermarket, items are identified by ‘stock keeping units’ or ‘SKUs’. In our store, we will use
individual letters of the alphabet, A, B, C etc, as the SKUs. Our goods are priced individually. In addition,
some items are multipriced: buy n of them and which will cost you y. For example, item A might cost 50
pence individually but this week we have a special offer where you can buy 3 As for £1.30.

#### This week’s prices are the following:

- Item A | Unit Price: 50 | Special Price: 3 for 130
- Item B | Unit Price: 30 | Special Price: 2 for 45
- Item C | Unit Price: 20 
- Item D | Unit Price: 15

Our checkout accepts items in any order so if we scan a B, then an A, then another B, we will recognise
the two B’s and price them at 45 (for a total price so far of 95).

Extra points: Because the pricing changes frequently we will need to be able to pass in a set of pricing
rules each time we start handling a checkout transaction.

### Approach 
- The Design Pattern used in this application followed a TDD Approach with the use of Object-Oriented design principles known as SOLID
    - within Solid patterns This application makes use of Single Responsibility Principle which gives the quality assurance that the Open-Close Principle is also met
    - The application also enforce the use of loose coupling and high cohesion
    - The application also enforces encapsulation, Overloading, and Composition through a HAS-A relationships between entities 
        - i.e (between Product : StockItems) One to Many Relationship
    - Enforces the use Functional Programming and Lambda Functions
    - With the use OOP approach opens flexibility for reusable code and possible future migrations 
    -   i.e(making use of constants to make it easier for future change to a final variable in one location)
    -   The logic, presentation entities and views are well separated and managed
        
### Application
- This is a standalone console application with no UI
- The UI Class is the runner class which contains static main function
- Extensive use of assertions within Unit test, ensures to test the classes and its methods
    - the CheckoutTest Class handles the different edge cases
- Classes like Checkout and Basket class consists of operations which occurs in a real world 
    - i.e: a shopping cart with a list of items and the no of occurrence including returning the total price of each item occurrence in the basket.
- To run the application - I have added a jar file including the unit tests
    - it uses core Java with no third-Party libraries, it should be straightforward to spin up on a local machine

### Application Functionality
- To keep track of operations made between menu option 2 and menu option, it stores the wholesale and inventory list in a file located under resources
    - Unit tests also has its files for both the Inventory and Wholesale list
- There are three menu options on the initial run of the application: 
    - To do shopping by adding items into the cart and a receipt is generated
        - validator - ensures to validate input with an existing Sku/itemName
    - To add or remove a stock item, once operation is completed it is updated in the file, making it reusable
          - It also avoids duplicate item name (SKUs) to be added in the inventory list 
    - To add or remove a Special Pricing rule for an item, once operation is completed it is updated in the file, making it reusable
        - It avoids duplicate Pricing rule with same itemName and no of item on offer
  

