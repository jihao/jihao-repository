package cn.heapstack.demo;

import java.util.EventListener;

public interface ICharacterEventListener extends EventListener {

	public void newCharacter(CharacterEvent ce);
	
}
