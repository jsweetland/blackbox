package com.blackbox.common.helpers;

import java.util.concurrent.TimeUnit;

public class RunWithTimeout {
	public static boolean waitFor(int secondsToWait, DelegateRunner delegate) {
		int secondsElapsed = 0;
		
		while(!delegate.isSuccessful() && secondsElapsed < secondsToWait) {
	    	try { TimeUnit.SECONDS.sleep(1); } catch(InterruptedException ex) { Thread.currentThread().interrupt(); }
			secondsElapsed++;
		}
		
		if(secondsElapsed < secondsToWait) {
			delegate.success();
			return true;
		} else {
			delegate.timeoutReached();
			return false;
		}
	}
}