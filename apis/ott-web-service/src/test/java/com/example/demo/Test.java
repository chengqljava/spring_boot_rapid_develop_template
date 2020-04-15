package com.example.demo;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;

public class Test {
	public static void main(String[] args) {
		// 创建动态客户端
		JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
		try {
			Client client = dcf.createClient("http://127.0.0.1:9002/vguard/ws/RTDWebService?wsdl");

			Object[] objects = new Object[0];

			client.invoke("test");
			System.out.println("返回数据:" + objects[0]);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}
	}
}
