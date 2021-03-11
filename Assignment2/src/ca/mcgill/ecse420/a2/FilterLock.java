package ca.mcgill.ecse420.a2;

public class FilterLock implements Lock {
	int [] level;
	int [] victim;
	
	public FilterLock (int n) {
		level = new int [n];
		victim =new int[n];
		for (int i = 0 ; i<n; i++) {
			level [i] = 0;
		}
	}
	
	@Override
	public void lock() {
		int me =  ThreadID.get();
		for (int i = 0; i< n; i++) {
			level [me] = i;
			victim[i] = me;
		}
		for (int k = 0; k< n; k++) {
			while ((k != me) (level[k] >= i && victim[i] == me)) {}
		}
		
	}
	
	@Override
	public void unlock () {
		int me = ThreadID.get();
		level[me] = 0;
	}
}
