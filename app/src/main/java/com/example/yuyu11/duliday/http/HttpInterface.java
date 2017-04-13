package com.example.yuyu11.duliday.http;

public interface HttpInterface<T>{
	public void ok(T t);
	public void erro(int code);
}
