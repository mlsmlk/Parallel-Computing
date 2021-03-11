package ca.mcgill.ecse420.a2;

public class BakeryLock implements Lock {
	boolean [] flag;
	Label [] label;
	
	public BakeryLock (int n) {
		flag = new boolean [n];
		label = new Label[n];
		for (int i = 0 ; i<n; i++) {
			flag [i] = false;
			label[i] = 0;
		}
	}
	
	@Override
	public void lock() {
		int me =  ThreadID.get();
		flag[i] = true;
		for (int i = 0; i< n; i++) {
			level [me] = i;
			victim[i] = me;
			
		}
		for (int k = 0; k< n; k++) {
			while ((i != me) && flag[i].get() && ((label[i].get() < label[me].get()) || ((label[i].get() == label[me].get()) && i < me))) {}
		}
		
	}
	
	@Override
	public void unlock () {
		flag[ThreadID.get()] = false;
	}
}
