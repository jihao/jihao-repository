package cn.heapstack.demo;

import java.util.Random;

public class ConcreteCharacterGenerateThread extends
		ConcreteCharacterGenerator implements Runnable {

	@Override
	public void run() {
		while(true)
		{
			Random r = new Random();
			// from 32 - 126
			int c = r.nextInt(126);
			while(c<32)
				c = r.nextInt(126);
			System.out.println(":"+(char)c);
			this.fireNextCharacter(new CharacterSource((char) c));
			
			try {
				Thread.sleep(c*10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

}
