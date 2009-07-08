package cn.heapstack.demo;

import java.util.EventObject;

public class CharacterEvent extends EventObject {

	public CharacterEvent(CharacterSource source) {
		super(source);
	}

	@Override
	public CharacterSource getSource() {
		return (CharacterSource)super.getSource();
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 6832373897895044358L;

}
