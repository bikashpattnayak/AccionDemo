package com.accionlabs.AccionDemo;

import java.util.Random;

public class abc {

	public static void main(String[] args) {

		Random ran = new Random();

		while (true) {
			int x = ran.nextInt(5);
			System.out.println(x);
		}
	}

}
