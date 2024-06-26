package org.spacehq.mc.protocol.data.message;

import java.util.ArrayList;
import java.util.List;

public class MessageStyle implements Cloneable {

	private static final MessageStyle DEFAULT = new MessageStyle();

	private ChatColor color = ChatColor.WHITE;
	private List<ChatFormat> formats = new ArrayList<ChatFormat>();
	private ClickEvent click;
	private HoverEvent hover;
	private String insertion;
	private MessageStyle parent = DEFAULT;

	public boolean isDefault() {
		return this.equals(DEFAULT);
	}

	public ChatColor getColor() {
		return this.color;
	}

	public MessageStyle setColor(ChatColor color) {
		this.color = color;
		return this;
	}

	public List<ChatFormat> getFormats() {
		return new ArrayList<ChatFormat>(this.formats);
	}

	public MessageStyle setFormats(List<ChatFormat> formats) {
		this.formats = new ArrayList<ChatFormat>(formats);
		return this;
	}

	public ClickEvent getClickEvent() {
		return this.click;
	}

	public MessageStyle setClickEvent(ClickEvent event) {
		this.click = event;
		return this;
	}

	public HoverEvent getHoverEvent() {
		return this.hover;
	}

	public MessageStyle setHoverEvent(HoverEvent event) {
		this.hover = event;
		return this;
	}

	public String getInsertion() {
		return this.insertion;
	}

	public MessageStyle setInsertion(String insertion) {
		this.insertion = insertion;
		return this;
	}

	public MessageStyle getParent() {
		return this.parent;
	}

	protected MessageStyle setParent(MessageStyle parent) {
		if (parent == null) {
			parent = DEFAULT;
		}

		this.parent = parent;
		return this;
	}

	public MessageStyle addFormat(ChatFormat format) {
		this.formats.add(format);
		return this;
	}

	public MessageStyle removeFormat(ChatFormat format) {
		this.formats.remove(format);
		return this;
	}

	public MessageStyle clearFormats() {
		this.formats.clear();
		return this;
	}

	@Override
	public String toString() {
		return "MessageStyle{color=" + this.color + ",formats=" + this.formats + ",clickEvent=" + this.click + ",hoverEvent=" + this.hover + ",insertion=" + this.insertion + "}";
	}

	@Override
	public MessageStyle clone() {
		return new MessageStyle().setParent(this.parent).setColor(this.color).setFormats(this.formats).setClickEvent(this.click != null ? this.click.clone() : null).setHoverEvent(this.hover != null ? this.hover.clone() : null).setInsertion(this.insertion);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		MessageStyle style = (MessageStyle) o;

		if (click != null ? !click.equals(style.click) : style.click != null) return false;
		if (color != style.color) return false;
		if (!formats.equals(style.formats)) return false;
		if (hover != null ? !hover.equals(style.hover) : style.hover != null) return false;
		if (insertion != null ? !insertion.equals(style.insertion) : style.insertion != null) return false;
        return parent.equals(style.parent);
    }

	@Override
	public int hashCode() {
		int result = color != null ? color.hashCode() : 0;
		result = 31 * result + formats.hashCode();
		result = 31 * result + (click != null ? click.hashCode() : 0);
		result = 31 * result + (hover != null ? hover.hashCode() : 0);
		result = 31 * result + (insertion != null ? insertion.hashCode() : 0);
		result = 31 * result + parent.hashCode();
		return result;
	}

}