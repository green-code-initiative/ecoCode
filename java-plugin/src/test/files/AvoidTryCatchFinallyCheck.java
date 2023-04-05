class AvoidUsingFinallyInTryCatchCheckClass{
	public void  inverse(int x) {
		if (!x) {
			System.out.println("Division par zero");
		}
		return 1/x;
	}

	public void function() {
        try {// Noncompliant {{Avoid using try-catch-finally}}
			System.out.println(inverse(1)+"\n");
		} catch (Exception e) {
			System.out.println("Exception reçue : "+ e.printStackTrace()+ "\n");
		}
		finally {
			System.out.println("Première fin.\n");
		}
	}
	public void functionEncore() {
        try {// Noncompliant {{Avoid using try-catch-finally}}
			System.out.println(inverse(1)+"\n");
		} catch (Exception e) {
			System.out.println("Exception reçue : "+ e.printStackTrace()+ "\n");
		}
	}

}