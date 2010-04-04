package com.haojii.notifier.easytv;

public class EasytvItem {

	public EasytvItem(String itemTitle, String itemLink) {
		super();
		this.itemTitle = itemTitle;
		this.itemLink = itemLink;
	}
	String itemTitle;
	String itemLink;
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((itemLink == null) ? 0 : itemLink.hashCode());
		result = prime * result
				+ ((itemTitle == null) ? 0 : itemTitle.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EasytvItem other = (EasytvItem) obj;
		if (itemLink == null) {
			if (other.itemLink != null)
				return false;
		} else if (!itemLink.equals(other.itemLink))
			return false;
		if (itemTitle == null) {
			if (other.itemTitle != null)
				return false;
		} else if (!itemTitle.equals(other.itemTitle))
			return false;
		return true;
	}

}
