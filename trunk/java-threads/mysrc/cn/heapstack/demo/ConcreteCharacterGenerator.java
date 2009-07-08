package cn.heapstack.demo;

import java.util.Vector;



public class ConcreteCharacterGenerator implements
		ICharacterEventGenerator {

	private Vector<ICharacterEventListener> listeners = new Vector<ICharacterEventListener>();
	

	@Override
	public void addCharacterEventListener(ICharacterEventListener listener) {
		listeners.add(listener);
	}

	@Override
	public void removeCharacterEventListener(ICharacterEventListener listener) {
		listeners.remove(listener);
		
	}

	@Override
	public void fireNextCharacter(CharacterSource cs) {
		CharacterEvent ce = new CharacterEvent(cs);
		for (ICharacterEventListener listener : listeners) {
			listener.newCharacter(ce);
		}
	}		

}
