class AvoidUsingFinallyInTryCatchCheckClass{
	public void  inverse(int x) {
		if (!x) {
			System.out.println("Division par zero");
		}
		return 1/x;
	}
	public void function() {
		try {
			System.out.println(inverse(1)+"\n");
		} catch (Exception e) {
			System.out.println("Exception reçue : "+ e.printStackTrace()+ "\n");
		}
		finally {// Noncompliant {{Avoid using try-catch-finally}}
			System.out.println("Première fin.\n");
		}

		try {
			System.out.println(inverse(2)+"\n");
		} catch (Exception e) {
			System.out.println("Exception reçue : "+ e.printStackTrace()+ "\n");
		}

	}
}