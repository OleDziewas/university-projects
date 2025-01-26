public interface BitSetFullAccess extends BitSetReadOnly{
	public void expand(int n);
	public void set(int index);
	public void clear(int index);
	public void flip(int index);
}