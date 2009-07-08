package cn.heapstack.demo;


public class Main {

	

	public static void main(String[] args)
	{		
		PrintingThread pthread = new PrintingThread();
		
		ICharacterEventGenerator generator = new ConcreteCharacterGenerator();
		generator.addCharacterEventListener(pthread);
		generator.fireNextCharacter(new CharacterSource('a'));
		
		ConcreteCharacterGenerateThread g2 = new ConcreteCharacterGenerateThread();
		g2.addCharacterEventListener(pthread);
		new Thread(g2).start();
		new Thread(pthread).start();
		
	}



}
class PrintingThread implements Runnable, ICharacterEventListener
{
	
	private String currentChar = "";

	public String getCurrentChar() {
		return currentChar;
	}

	public void setCurrentChar(String currentChar) {
		this.currentChar = currentChar;
	}
	

	@Override
	public void run() {
		while(true)
		{
			//synchronized (currentChar) {
				System.out.println(getCurrentChar());
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			//}
		}
	}
	
	@Override
	public void newCharacter(CharacterEvent ce) {
		currentChar = String.valueOf( ce.getSource().getCharacter() );		
	}
	
}