import java.util.Scanner;
public class CoffeePayment {
	private static double[] accounts;
	public static void main(String[] args) {
		//Expect no of people and lines of input in args
		Scanner snr = new Scanner(System.in); 
		if(args.length < 2)
		{
			System.err.println("Expected at 2 args.");
			System.exit(1);
		}
		int grpSize = Integer.parseInt(args[0]), n = Integer.parseInt(args[1]);
		accounts = new double[grpSize];
		//Be default we believe always person with id = 0 or first person pays
		//before any transactions
		//We also assume that we must know the payer before any transactions
		//After a transaction, the next payer is updated, based on accounts
		int nextPayer = determinePayer();
		System.out.println("Next Payer is: " + nextPayer);
		while(n-- > 0)
		{
			String l = snr.nextLine();
			String[] vals = l.split("\\s+");//split string on whitespace
			for(int i=0;i<vals.length;i = i + 2)
			{
				int coworkerId = Integer.parseInt(vals[i]);
				double orderAmt = Double.parseDouble(vals[i + 1]);
				if(coworkerId != nextPayer)
				{
					accounts[nextPayer] += orderAmt;
					accounts[coworkerId] -= orderAmt;
				}
			}
			//Find next payer
			nextPayer = determinePayer();
			System.out.println("Next Payer is: " + nextPayer);
		}
		displayBalances();
	}
	public static void displayBalances() {
		int idx = 0;
		System.out.println();
		System.out.println("-------------Total Balances--------------");
		for(double x : accounts)
		{
			if(x < 0)
			{
				System.out.printf("Coworker #%d owes $%.2f.\n", idx, Math.abs(accounts[idx]));
			}else {
				System.out.printf("Coworker #%d is owed $%.2f.\n", idx, Math.abs(accounts[idx]));
			}
			idx++;
		}
	}
	private static int determinePayer() {
		double minExp = 0;
		int payerId = 0;
		int idx = 0;
		for(double x : accounts)
		{
			if(x < minExp) {
				minExp = x;
				payerId = idx;
			}
			idx++;
		}
		return payerId;
	}
}
