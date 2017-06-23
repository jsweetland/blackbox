package com.blackbox.common.helpers;

public interface DelegateRunner {
	public void success();
	public void timeoutReached();
	public boolean isSuccessful();
}