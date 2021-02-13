import java.util.*;
import java.text.*;
import java.io.*;

public class UserInterface {
	private static UserInterface userInterface;
	private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	private static Client client;
	private ClientList clientList;
	private ProductList productList;
	private SupplierList supplierList;
	private static final int EXIT = 0;
	private static final int ADD_PRODUCT = 1;
	private static final int ADD_CLIENT = 2;
	private static final int ADD_SUPPLIER = 3;
	private static final int ADD_TO_CART = 4;
	private static final int MENU = 5;
	
	
	private UserInterface() {
		supplierList = SupplierList.instance();
		productList = ProductList.instance();
		clientList = ClientList.instance();
	}

	public static UserInterface instance() {
		if (userInterface == null) {
			return userInterface = new UserInterface();
		} else {
			return userInterface;
		}
	}
	
	public String getToken(String prompt) {
    		do {
      			try {
        			System.out.println(prompt);
        			String line = reader.readLine();
        			StringTokenizer tokenizer = new StringTokenizer(line,"\n\r\f");
        			if (tokenizer.hasMoreTokens()) {
          			return tokenizer.nextToken();
        			}
      			} catch (IOException ioe) {
        			System.exit(0);
      			}
    		} while (true);
  	}
	
	public int getCommand() {
		do {
			try {
				int value = Integer.parseInt(getToken("Enter a command. [Use command " + MENU + " for a list of commands]"));
				if (value >= EXIT && value <= MENU) {
					return value;
				}
			} catch (NumberFormatException nfe) {
				System.out.println("Please enter a number");
			}
		} while (true);
	}
	
	public void menu() {
		System.out.println("Enter the number corresponding to the desired command:");
		System.out.println("Exit: " + EXIT);
		System.out.println("Add a Product: " + ADD_PRODUCT);
		System.out.println("Add a Client: " + ADD_CLIENT);
		System.out.println("Add a Supplier: " + ADD_SUPPLIER);
		System.out.println("Add to Cart: " + ADD_TO_CART);
		System.out.println("Menu: " + MENU);
	}
	
	public void addProduct() {
		boolean successful;
		String productName = getToken("Enter the product name: ");
		int productQuantity = Integer.parseInt(getToken("Enter the product quantity: "));
		String productID = getToken("Enter the product ID: ");
		int productPrice = Integer.parseInt(getToken("Enter the product price: "));

		Product newProduct = new Product(productName, productID, productQuantity, productPrice);
		successful = productList.insertProduct(newProduct);
		
		if (successful) {
			System.out.println("Product with details (" + newProduct.toString() + ") added successfully");
		} else {
			System.out.println("Issue adding product!");
		}
	}
	
	public void addClient() {
		boolean successful;
		String clientName = getToken("Enter the client's name: ");
		int clientID = Integer.parseInt(getToken("Enter the client's ID: "));
		String clientAddress = getToken("Enter the client's address: ");

		Client newClient = new Client(clientName, clientID, clientAddress); //DOESNT WANT TO CREATE THE CLIENT OR ADD TO LIST
		successful = clientList.insertMember(newClient);

		if (successful) {
			System.out.println("Client with details (" + newClient.toString() + ") added successfully");
		} else {
			System.out.println("Issue adding client!");
		}
		
	}
	
	public void addSupplier() {
		boolean successful;
		int supplierID = Integer.parseInt(getToken("Enter the supplier ID: "));
		String supplierName = getToken("Enter the supplier name:");

		Supplier newSupplier = new Supplier(supplierID, supplierName);

		successful = supplierList.insertSupplier(newSupplier);
		if (successful) {
			System.out.println("Supplier with name " + newSupplier.get_name() + " and ID " + newSupplier.get_ID() + " added successfully");
		} else {
			System.out.println("Issue adding supplier!");
		}
	}
	
	public void addToCart() { //CURRENTLY BROKEN
		String productToAddID = getToken("Please enter the product ID string you wish to add: ");
		Product productToAdd = productList.search(productToAddID);
		int quant = Integer.parseInt(getToken("Enter the quantity to be added to the cart: "));
		client.AddToCart(productToAdd, quant);
	}
	
	public void process() {
		int command;
		menu();
		while ((command = getCommand()) != EXIT) {
			switch (command) {
				case ADD_PRODUCT:
					addProduct();
					break;
				case ADD_CLIENT:
					addClient();
					break;
				case ADD_SUPPLIER:
					addSupplier();
					break;
				case ADD_TO_CART:
					addToCart();
					break;
				case MENU:
					menu();
					break;
				default:
					System.out.println("Not a valid command");
					command = EXIT;
			}
		}
	}
					
	public static void main(String[] args) {
		UserInterface.instance().process();
	}
}
