package com.carRent;



import java.util.*;

 class CarRentalSystemApp {
	private String carId;
	private String model;
	private String brand;
	private double basePrice;
	private boolean isAvailable;
	//constructor is used in alter to creating another method and invoke also
	public CarRentalSystemApp(String carId, String model, String brand, double basePrice ) {
		super();
		this.carId = carId;
		this.model = model;
		this.brand = brand;
		this.basePrice = basePrice;
		this.isAvailable = true;
	}
	public String getCarId() {
		return carId;
	}

	public String getModel() {
		return model;
	}
	
	public String getBrand() {
		return brand;
	}
	
	public double getBasePrice() {
		return basePrice;
	}

	public boolean isAvailable() {
		return isAvailable;
	}
	
	public void rent() {
		isAvailable = false;
	}
	
	public void returnCar() {
		isAvailable = true;
	}
	
	public double calculatePrice(int rentalDays) {
		return rentalDays*basePrice;
	}
}
	class Customer{
		private String customerId;
		private String name;
		public Customer(String customerId, String name) {
			super();
			this.customerId = customerId;
			this.name = name;
		}
		public String getCustomerId() {
			return customerId;
		}
		
		public String getName() {
			return name;
		}
		
		
		
	}
	
	class Rental{
		private CarRentalSystemApp car;
		private Customer customer;
		private int days;
		public Rental(CarRentalSystemApp car, Customer customer, int days) {
			super();
			this.car = car;
			this.customer = customer;
			this.days = days;
		}
		public CarRentalSystemApp getCar() {
			return car;
		}
		
		public Customer getCustomer() {
			return customer;
		}
		
		public int getDays() {
			return days;
		}
			
	}
	
	class RentalApp{
		private List<CarRentalSystemApp> cars;
		private List<Customer> customers;
		private List<Rental> rentals;
		
		
		public RentalApp() {
			cars = new ArrayList<>();
			customers = new ArrayList<>();
			rentals = new ArrayList<>();
			
		}

		void addCar(CarRentalSystemApp car) {
			cars.add(car);
		}
		void addCustomer(Customer customer) {
			customers.add(customer);
		}
		
		public void rentCar(CarRentalSystemApp car, Customer customer, int days) {
			if(car.isAvailable()) {
				car.rent();
				rentals.add(new Rental(car, customer, days));
			}
			else {
				System.out.println("Car is not available for rent");
			}
			
		}
		
		public void returnCar(CarRentalSystemApp car) {
			car.returnCar();
			Rental rentalToRemove = null;
			for(Rental rental: rentals) {
				if(rental.getCar()== car) {
					rentalToRemove= rental;
					break;
				}
			}
			if(rentalToRemove != null) {
				rentals.remove(rentalToRemove);
				System.out.println("Car returned successfully");
			}
			else {
				System.out.println("Car was not rented");
			}
			
		}
		
		public void menu(){
			Scanner scan = new Scanner (System.in);
			while(true) {
				System.out.println("********* CAR RENTAL SYSTEM ********");
				System.out.println("press 1 to Rent a car");
				System.out.println("press 2 to Return a car");
				System.out.println("press 3 to exit");
				
				int choice=scan.nextInt();
				scan.nextLine();
				if(choice ==1) {
					System.out.println("\n May I know your name \n");
					String customerName = scan.nextLine();
					System.out.println("\n Available Cars");
					for(CarRentalSystemApp car : cars) {
						if(car.isAvailable()) {
							System.out.println(car.getCarId() + " - "+ car.getBrand()+" "+car.getModel());
						}
					}
					System.out.println("\n Enter the car ID you want to rent: ");
					String carId = scan.nextLine();
					
					
					System.out.println("Enter the number of days for rental: ");
					int rentalDays =scan.nextInt();
					scan.nextLine();
					
					Customer newCustomer = new Customer("CUS" + (customers.size()+1), customerName);
					addCustomer(newCustomer);
					CarRentalSystemApp selectedCar = null;
					for(CarRentalSystemApp car : cars) {
						if(car.getCarId().equals(carId) && car.isAvailable()) {
							selectedCar = car;
							break;
						}
					}
					
					if(selectedCar != null) {
						double totalPrice = selectedCar.calculatePrice(rentalDays);
						System.out.println("\n RENTAL INFORMATION \n");
						System.out.println("Customer ID: "+ newCustomer.getCustomerId());
						System.out.println("Customer Name: "+newCustomer.getName());
						System.out.println("Car: "+ selectedCar.getBrand()+" "+ selectedCar.getModel());
						System.out.println("Rental Days: "+rentalDays);
						System.out.printf("Total Price: $%.2f%n", totalPrice);
						
						System.out.print("\n Confirm rental (Y/N): ");
						String confirm = scan.nextLine();
						
						if(confirm.equalsIgnoreCase("Y")) {
							rentCar(selectedCar, newCustomer, rentalDays);
							System.out.println("\nCar rented successfully");
						} else {
							System.out.println("\nRental cancelled");
						}
					}else {
						System.out.println("\nInvalid car selection or car not available for rent.");
					}
					
				}
				else if(choice ==2) {
					System.out.println("\n RETURN CAR\n");
					System.out.println("Enter the car ID you want to return: ");
					String carId = scan.nextLine();
					
					CarRentalSystemApp carToReturn = null;
					for(CarRentalSystemApp car : cars) {
						if(car.getCarId().equals(carId) && !car.isAvailable()) {
							carToReturn = car;
							break;
						}
					}
					if(carToReturn != null) {
						Customer customer = null;
						for (Rental rental : rentals) {
							if(rental.getCar() == carToReturn) {
								customer = rental.getCustomer();
								break;
							}
						}
						
						if(customer != null) {
							returnCar(carToReturn);
							System.out.println("Car returned successfully by "+ customer.getName());
						} else {
							System.out.println("Car was not rented or rental information is missing.");
						}
					}else {
						System.out.println("Invalid car ID or car is not rented.");
					}
				}else if(choice == 3) {
					break;
				}
				else {
					System.out.println("Invalid choice. Plese enter a valid option.");
				}
			}
			System.out.println("\nThank you for using the Car Rental System");
		}
	}
	
//	46:38
	public class Main{
		public static void main(String[] args) {
			RentalApp rentalSystem = new RentalApp();
			CarRentalSystemApp car1 =new CarRentalSystemApp("C001", "Toyota", "Camry", 70.0);
			CarRentalSystemApp car2 =new CarRentalSystemApp("C002", "Honda", "accord", 90.0);
			rentalSystem.addCar(car1);
			rentalSystem.addCar(car2);
			rentalSystem.menu();
		}
	}



