package cn.heapstack.demo;

public interface ICharacterEventGenerator {

	public void fireNextCharacter(CharacterSource cs);
	public void addCharacterEventListener(ICharacterEventListener listener);
	public void removeCharacterEventListener(ICharacterEventListener listener);
}
